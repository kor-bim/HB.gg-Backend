package com.bim.hbgg.service;

import com.bim.hbgg.dto.AccountDTO;
import com.bim.hbgg.dto.LeagueEntryDTO;
import com.bim.hbgg.dto.SummonerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RiotApiService {
    private static final Logger logger = LoggerFactory.getLogger(RiotApiService.class);

    private final RestTemplate restTemplate;

    @Value("${riot.api.key}")
    private String apiKey;

    @Autowired
    public RiotApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

     public AccountDTO fetchAccountFromRiotApi(String gameName, String tagLine) {
        String url = String.format("https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/%s/%s", gameName, tagLine);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        logger.debug("Requesting Riot Account API for gameName: {}, tagLine: {}", gameName, tagLine);
        ResponseEntity<AccountDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, AccountDTO.class);
        logger.debug("Response from Riot Account API: {}", response);

        return response.getBody();
    }

    public SummonerDTO fetchSummonerFromRiotApi(String puuid) {
        String url = String.format("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/%s", puuid);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        logger.debug("Requesting Riot Summoner API for puuid: {}", puuid);
        ResponseEntity<SummonerDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, SummonerDTO.class);
        logger.debug("Response from Riot Summoner API: {}", response);

        return response.getBody();
    }

    public List<LeagueEntryDTO> fetchLeagueFromRiotApi(String summonerId) {
        String url = String.format("https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/%s", summonerId);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        logger.debug("Requesting Riot League API for summonerId: {}", summonerId);

        ResponseEntity<List<LeagueEntryDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                });
        logger.debug("Response from Riot League API: {}", response);

        return response.getBody();
    }
}
