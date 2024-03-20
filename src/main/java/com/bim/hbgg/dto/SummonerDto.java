package com.bim.hbgg.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SummonerDto {
    private String id;
    private String summonerId;
    private String acctId;
    private String tagLine;
    private String name;
    private String internalName;
    private String profileImageUrl;
    private Integer level;
    private Date updateAt;
    private Date createAt;
}
