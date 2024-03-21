package com.bim.hbgg.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class MiniSeriesDTO {
    private Integer losses;
    private String progress;
    private Integer target;
    private Integer wins;
}