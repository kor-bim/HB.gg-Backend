package com.bim.hbgg.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "league")
public class League {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "summoner_id")
    private String summonerId;

    @Column(name = "queue_type")
    private String queueType;

    @Column(name = "tier")
    private String tier;

    @Column(name = "rank")
    private String rank;

    @Column(name = "league_point")
    private Integer leaguePoint;

    @Column(name = "win")
    private Integer win;

    @Column(name = "lose")
    private Integer lose;
}
