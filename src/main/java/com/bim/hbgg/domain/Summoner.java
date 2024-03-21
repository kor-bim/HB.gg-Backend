package com.bim.hbgg.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "summoner")
public class Summoner {

    @Id
    @Column(name = "id")
    private String summonerId;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "puuid")
    private String puuid;

    @Column(name = "profile_icon_id")
    private Integer profileIconId;

    @Column(name = "summoner_name")
    private String summonerName;

    @Column(name = "tag_line")
    private String tagLine;

    @Column(name = "internal_name")
    private String internalName;

    @Column(name = "summoner_level")
    private Long summonerLevel;

    @Column(name = "revision_date")
    private Long revisionDate;
}
