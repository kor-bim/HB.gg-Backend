package com.bim.hbgg.controller;

import com.bim.hbgg.domain.Summoner;
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

    @GetMapping("/{gameName}/{tagLine}")
    public ResponseEntity<Summoner> getSummoner(@PathVariable String gameName, @PathVariable String tagLine) {
        Summoner summoner = summonerService.getOrCreateSummoner(gameName, tagLine);
        return ResponseEntity.ok(summoner);
    }
}
