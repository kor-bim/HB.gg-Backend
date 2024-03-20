package com.bim.hbgg.service;

import com.bim.hbgg.domain.Summoner;
import com.bim.hbgg.dto.AccountDto;
import com.bim.hbgg.repository.SummonerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SummonerServiceTest {

    @Mock
    private SummonerRepository summonerRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SummonerService summonerService;

    @Test
    public void whenSummonerExists_thenReturnsExistingSummoner() {
        // Given
        String gameName = "윤한빈";
        String tagLine = "3257";
        Summoner existingSummoner = new Summoner();
        existingSummoner.setGameName(gameName);
        existingSummoner.setTagline(tagLine);
        when(summonerRepository.findByGameNameAndTagline(gameName, tagLine)).thenReturn(Optional.of(existingSummoner));

        // When
        Summoner result = summonerService.getOrCreateSummoner(gameName, tagLine);

        // Then
        assertNotNull(result);
        assertEquals(gameName, result.getGameName());
        assertEquals(tagLine, result.getTagline());
        verify(summonerRepository, never()).save(any(Summoner.class));
    }

    @Test
    public void whenSummonerDoesNotExist_thenFetchesFromRiotApiAndSaves() {
        // Given
        String gameName = "윤한빈";
        String tagLine = "3257";
        when(summonerRepository.findByGameNameAndTagline(gameName, tagLine)).thenReturn(Optional.empty());

        AccountDto accountDto = new AccountDto();
        accountDto.setGameName(gameName);
        accountDto.setTagLine(tagLine);
        accountDto.setPuuid("unique-puuid");
        ResponseEntity<AccountDto> responseEntity = new ResponseEntity<>(accountDto, HttpStatus.OK);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(AccountDto.class))
        ).thenReturn(responseEntity);

        // When
        Summoner result = summonerService.getOrCreateSummoner(gameName, tagLine);

        // Then
        assertNotNull(result);
        assertEquals(gameName, result.getGameName());
        assertEquals(tagLine, result.getTagline());
        assertNotNull(result.getPuuid());
        verify(summonerRepository, times(1)).save(any(Summoner.class));
    }
}
