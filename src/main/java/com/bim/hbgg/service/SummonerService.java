package com.bim.hbgg.service;

import com.bim.hbgg.domain.League;
import com.bim.hbgg.domain.Summoner;
import com.bim.hbgg.dto.AccountDTO;
import com.bim.hbgg.dto.LeagueEntryDTO;
import com.bim.hbgg.dto.SummonerDTO;
import com.bim.hbgg.dto.SummonerLeagueDto;
import com.bim.hbgg.repository.LeagueRepository;
import com.bim.hbgg.repository.SummonerRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SummonerService {
    private static final Logger logger = LoggerFactory.getLogger(RiotApiService.class);
    private final SummonerRepository summonerRepository;
    private final LeagueRepository leagueRepository;
    private final RiotApiService riotApiService;

    @Autowired
    public SummonerService(SummonerRepository summonerRepository, LeagueRepository leagueRepository, RiotApiService riotApiService) {
        this.summonerRepository = summonerRepository;
        this.leagueRepository = leagueRepository;
        this.riotApiService = riotApiService;
    }

    @Transactional
    public SummonerLeagueDto getOrCreateSummoner(String summonerName, String tagLine) {
        Optional<Summoner> existingSummoner = summonerRepository.findByTagLineAndSummonerName(tagLine, summonerName);
        SummonerLeagueDto summonerLeagueDto = new SummonerLeagueDto();
        existingSummoner.orElseGet(() -> {
            AccountDTO accountDto = riotApiService.fetchAccountFromRiotApi(summonerName, tagLine);

            Summoner newSummoner = new Summoner();

            if (!accountDto.getPuuid().isEmpty()) {
                SummonerDTO summonerDto = riotApiService.fetchSummonerFromRiotApi(accountDto.getPuuid());

                newSummoner.setSummonerId(summonerDto.getId());
                newSummoner.setAccountId(summonerDto.getAccountId());
                newSummoner.setPuuid(accountDto.getPuuid());
                newSummoner.setProfileIconId(summonerDto.getProfileIconId());
                newSummoner.setSummonerName(accountDto.getGameName());
                newSummoner.setTagLine(accountDto.getTagLine());
                newSummoner.setInternalName(summonerDto.getName());
                newSummoner.setSummonerLevel(summonerDto.getSummonerLevel());
                newSummoner.setRevisionDate(summonerDto.getRevisionDate());

                summonerLeagueDto.setSummoner(newSummoner);
                summonerRepository.save(newSummoner);

                if (!summonerDto.getId().isEmpty()) {

                    List<LeagueEntryDTO> leagueDtos = riotApiService.fetchLeagueFromRiotApi(summonerDto.getId());

                    for (LeagueEntryDTO leagueDto : leagueDtos) {
                        League newLeague = new League();
                        newLeague.setId(leagueDto.getLeagueId());
                        newLeague.setSummonerId(leagueDto.getSummonerId());
                        newLeague.setQueueType(leagueDto.getQueueType());
                        newLeague.setTier(leagueDto.getTier());
                        newLeague.setRank(leagueDto.getRank());
                        newLeague.setLeaguePoint(leagueDto.getLeaguePoints());
                        newLeague.setWin(leagueDto.getWins());
                        newLeague.setLose(leagueDto.getLosses());

                        summonerLeagueDto.setLeagueEntry(newLeague);

                        leagueRepository.save(newLeague);
                    }
                }
            }


            return newSummoner;
        });

        return summonerLeagueDto;
    }
}
