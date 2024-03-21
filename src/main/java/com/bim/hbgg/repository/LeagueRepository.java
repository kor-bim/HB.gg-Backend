package com.bim.hbgg.repository;

import com.bim.hbgg.domain.League;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LeagueRepository extends JpaRepository<League, UUID> {
}
