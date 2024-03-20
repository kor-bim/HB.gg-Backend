package com.bim.hbgg.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "summoner")
public class Summoner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "summoner_id")
    private String summonerId;

    @Column(name = "acct_id")
    private String acctId;

    @Column(name = "puuid")
    private String puuid;

    @Column(name = "tagline")
    private String tagline;

    @Column(name = "name")
    private String name;

    @Column(name = "game_name")
    private String gameName;

    @Column(name = "internal_name")
    private String internalName;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "level")
    private Integer level;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
