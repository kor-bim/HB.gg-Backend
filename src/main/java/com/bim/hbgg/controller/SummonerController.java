package com.bim.hbgg.controller;

import com.bim.hbgg.dto.SummonerLeagueDto;
import com.bim.hbgg.service.SummonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/summoners")
public class SummonerController {

    private final SummonerService summonerService;

    @Autowired
    public SummonerController(SummonerService summonerService) {
        this.summonerService = summonerService;
    }

    @GetMapping("/{summonerName}/{tagLine}")
    public ResponseEntity<SummonerLeagueDto> getSummoner(@PathVariable String summonerName, @PathVariable String tagLine) {
        SummonerLeagueDto summonerLeagueDto = summonerService.getOrCreateSummoner(summonerName, tagLine);
        return ResponseEntity.ok(summonerLeagueDto);
    }
}
