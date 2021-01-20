package com.egamorim.simian;

import com.egamorim.simian.util.InvalidDNASequenceException;
import com.egamorim.simian.util.SimianUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimianUtilTest {

    @Test
    void checkHorizontalSimianSequence_Simian() {

        String [] dna = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
        SimianUtil util = new SimianUtil();
        boolean isSimian = util.checkHorizontalSimianSequence(dna);
        Assertions.assertThat(isSimian).isTrue();

    }

    @Test
    void checkHorizontalSimianSequence_Human() {

        String [] dna = {"CTGAGA", "CTGAGC", "TATTGT", "AGACGG", "CCGCTA", "TCACTG"};
        SimianUtil util = new SimianUtil();
        boolean isSimian = util.checkHorizontalSimianSequence(dna);
        Assertions.assertThat(isSimian).isFalse();

    }

    @Test
    void checkVerticalSimianSequence_Simian() {

        String [] dna = {"CTGAGA", "CTGAGC", "TTATGT", "ATACGG", "CCGCTA", "TCACTG"};
        SimianUtil util = new SimianUtil();
        boolean isSimian = util.checkVerticalSimianSequence(dna);
        Assertions.assertThat(isSimian).isTrue();

    }

    @Test
    public void checkDiagonalFromBottomSimianSequence_Simian() {
        String [] dna = {"GTGAGA", "CCGGGC", "TAATGT", "ATACAG", "CCTCCA", "TCATTG"};
        SimianUtil util = new SimianUtil();
        boolean isSimian = util.checkDiagonalFromBottomSimianSequence(dna);
        Assertions.assertThat(isSimian).isTrue();

    }


    @Test
    void checkDiagonalFromTopSimianSequence_Simian() {
        String[] dna = {"GTGAGATA", "CCTGGCGC", "TAATGTGG", "ATACAGTT", "CCTCCAAT", "TCATTGTA", "GCTACTCT", "TCCTTAGA"};
        SimianUtil util = new SimianUtil();
        boolean isSimian = util.checkDiagonalFromTopSimianSequence(dna);
        Assertions.assertThat(isSimian).isTrue();

    }

    @Test
    void validateDNASequence_NotSquareMatrix_Error() {
        String[] dna = {"GTGAGATA", "CCTGGCGC", "TAATGTGG", "ATACAGTTA", "CCTCCAAT", "TCATTGTA", "GCTACTCT", "TCCTTAGA"};
        SimianUtil util = new SimianUtil();

        Assertions.assertThatThrownBy(() -> {
            util.validateDNASequence(dna);
        }).isInstanceOf(InvalidDNASequenceException.class)
            .hasMessageContaining("The DNA sequence must be a square matrix");
    }

    @Test
    void validateDNASequence_InvalidCharacter_Error() {
        String[] dna = {"GTGAGATA", "CCTGGCGC", "TAATGTGG", "ATACAGTZ", "CCTCCAAT", "TCATTGTA", "GCTACTCT", "TCCTTAGA"};
        SimianUtil util = new SimianUtil();

        Assertions.assertThatThrownBy(() -> {
            util.validateDNASequence(dna);
        }).isInstanceOf(InvalidDNASequenceException.class)
            .hasMessageContaining("The provided DNA sequence has at least one invalid character");
    }

    @Test
    void validateDNASequence_ValidSequence_NoExceptionsMustBeThrew_Success() {
        String[] dna = {"GTGAGATA", "CCTGGCGC", "TAATGTGG", "ATACAGTC", "CCTCCAAT", "TCATTGTA", "GCTACTCT", "TCCTTAGA"};
        SimianUtil util = new SimianUtil();

        util.validateDNASequence(dna);
    }

}
