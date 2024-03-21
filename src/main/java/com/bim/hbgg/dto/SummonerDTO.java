package com.bim.hbgg.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class SummonerDTO {
    private String id;
    private String accountId;
    private String puuid;
    private Integer profileIconId;
    private Long summonerLevel;
    private String name;
    private Long revisionDate;
}
