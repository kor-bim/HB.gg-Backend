package com.bim.hbgg.service;

import com.bim.hbgg.domain.Summoner;
import com.bim.hbgg.dto.AccountDto;
import com.bim.hbgg.repository.SummonerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class SummonerService {

    private static final Logger logger = LoggerFactory.getLogger(SummonerService.class);

    private final SummonerRepository summonerRepository;
    private final RestTemplate restTemplate;

    @Value("${riot.api.key}")
    private String apiKey;

    @Autowired
    public SummonerService(SummonerRepository summonerRepository, RestTemplate restTemplate) {
        this.summonerRepository = summonerRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public Summoner getOrCreateSummoner(String gameName, String tagLine) {
        Optional<Summoner> existingSummoner = summonerRepository.findByGameNameAndTagline(gameName, tagLine);
        return existingSummoner.orElseGet(() -> {
            AccountDto accountDto = fetchAccountFromRiotApi(gameName, tagLine);
            Summoner newSummoner = new Summoner();
            newSummoner.setPuuid(accountDto.getPuuid());
            newSummoner.setGameName(accountDto.getGameName());
            newSummoner.setTagline(accountDto.getTagLine());
            summonerRepository.save(newSummoner);
            return newSummoner;
        });
    }

    private AccountDto fetchAccountFromRiotApi(String gameName, String tagLine) {
        String url = String.format("https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/%s/%s", gameName, tagLine);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        logger.debug("Requesting Riot API for gameName: {}, tagLine: {}", gameName, tagLine);
        ResponseEntity<AccountDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, AccountDto.class);
        logger.debug("Response from Riot API: {}", response);

        return response.getBody();
    }
}
