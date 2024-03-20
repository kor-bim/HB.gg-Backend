package com.bim.hbgg.repository;

import com.bim.hbgg.domain.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface SummonerRepository extends JpaRepository<Summoner, UUID> {
    Optional<Summoner> findByGameNameAndTagline(String gameName, String tagline);
}
