package com.bim.hbgg.dto;

import com.bim.hbgg.domain.League;
import com.bim.hbgg.domain.Summoner;
import lombok.Data;

@Data
public class SummonerLeagueDto {
    private Summoner summoner;
    private League leagueEntry;
}
