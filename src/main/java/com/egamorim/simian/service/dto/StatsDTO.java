package com.egamorim.simian.service.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StatsDTO {

    private int countSimianDNA;
    private int countHumanDNA;
    private BigDecimal ratio;

}
