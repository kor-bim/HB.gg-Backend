package com.bim.hbgg.controller;

import com.bim.hbgg.domain.Summoner;
import com.bim.hbgg.service.SummonerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SummonerController.class)
public class SummonerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SummonerService summonerService;

    @Test
    public void testGetSummoner() throws Exception {
        Summoner summoner = new Summoner();
        summoner.setGameName("SummonerName");
        summoner.setTagline("EUW");

        given(summonerService.getOrCreateSummoner("SummonerName", "EUW")).willReturn(summoner);

        mockMvc.perform(get("/summoners/SummonerName/EUW")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameName").value("SummonerName"))
                .andExpect(jsonPath("$.tagline").value("EUW"));
    }
}
