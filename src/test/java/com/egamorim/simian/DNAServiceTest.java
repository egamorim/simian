package com.egamorim.simian;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.egamorim.simian.model.DNA;
import com.egamorim.simian.model.DNARepository;
import com.egamorim.simian.model.DNAType;
import com.egamorim.simian.service.DNAService;
import com.egamorim.simian.service.dto.StatsDTO;
import com.egamorim.simian.util.SimianUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DNAServiceTest {

    @Mock
    SimianUtil simianUtil;

    @Mock
    DNARepository dnaRepository;

    @Test
    void verifyDNA_Horizontal_Simian() {
        when(simianUtil.checkHorizontalSimianSequence(any())).thenReturn(true);

        DNAService service = new DNAService(simianUtil, dnaRepository);
        DNAType dnaType = service.verifyDNA(any());

        Assertions.assertThat(dnaType).isEqualTo(DNAType.SIMIAN);

    }

    @Test
    void verifyDNA_Vertical_Simian() {
        when(simianUtil.checkHorizontalSimianSequence(any())).thenReturn(false);
        when(simianUtil.checkVerticalSimianSequence(any())).thenReturn(true);

        DNAService service = new DNAService(simianUtil, dnaRepository);
        DNAType dnaType = service.verifyDNA(any());

        Assertions.assertThat(dnaType).isEqualTo(DNAType.SIMIAN);

    }

    @Test
    void verifyDNA_DiagonalFromBottom_Simian() {
        when(simianUtil.checkHorizontalSimianSequence(any())).thenReturn(false);
        when(simianUtil.checkVerticalSimianSequence(any())).thenReturn(false);
        when(simianUtil.checkDiagonalFromBottomSimianSequence(any())).thenReturn(true);

        DNAService service = new DNAService(simianUtil, dnaRepository);
        DNAType dnaType = service.verifyDNA(any());

        Assertions.assertThat(dnaType).isEqualTo(DNAType.SIMIAN);

    }

    @Test
    void verifyDNA_DiagonalFromTop_Simian() {
        when(simianUtil.checkHorizontalSimianSequence(any())).thenReturn(false);
        when(simianUtil.checkVerticalSimianSequence(any())).thenReturn(false);
        when(simianUtil.checkDiagonalFromBottomSimianSequence(any())).thenReturn(false);
        when(simianUtil.checkDiagonalFromTopSimianSequence(any())).thenReturn(true);

        DNAService service = new DNAService(simianUtil, dnaRepository);
        DNAType dnaType = service.verifyDNA(any());

        Assertions.assertThat(dnaType).isEqualTo(DNAType.SIMIAN);

    }

    @Test
    void verifyDNA_Human() {
        when(simianUtil.checkHorizontalSimianSequence(any())).thenReturn(false);
        when(simianUtil.checkVerticalSimianSequence(any())).thenReturn(false);
        when(simianUtil.checkDiagonalFromBottomSimianSequence(any())).thenReturn(false);
        when(simianUtil.checkDiagonalFromTopSimianSequence(any())).thenReturn(false);

        DNAService service = new DNAService(simianUtil, dnaRepository);
        DNAType dnaType = service.verifyDNA(any());

        Assertions.assertThat(dnaType).isEqualTo(DNAType.HUMAN);

    }

    @Test
    void getDNAStats_Success() {
        DNAService service = new DNAService(simianUtil, dnaRepository);
        when(dnaRepository.findAll()).thenReturn(mockedDNAList());

        BigDecimal expectedRatio = new BigDecimal(40);
        expectedRatio = expectedRatio.setScale(2, RoundingMode.HALF_EVEN);
        StatsDTO dnaStats = service.getDNAStats();
        Assertions.assertThat(dnaStats.getRatio()).isEqualTo(expectedRatio);
    }

    List<DNA> mockedDNAList() {
        List<DNA> list = new ArrayList<>();
        for(int count = 0; count < 40; count ++){
            list.add(DNA.builder().type(DNAType.SIMIAN).build());
        }
        for(int count = 0; count < 100; count ++){
            list.add(DNA.builder().type(DNAType.HUMAN).build());
        }
        return list;
    }
}
