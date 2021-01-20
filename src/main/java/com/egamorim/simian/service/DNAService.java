package com.egamorim.simian.service;

import com.egamorim.simian.model.DNA;
import com.egamorim.simian.model.DNARepository;
import com.egamorim.simian.model.DNAType;
import com.egamorim.simian.service.dto.StatsDTO;
import com.egamorim.simian.util.SimianUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class DNAService {

    private static final int INITIAL_RATIO_ALL_SIMIAN = 100;
    private final SimianUtil simianUtil;
    private final DNARepository dnaRepository;

    public DNAService(SimianUtil simianUtil, DNARepository dnaRepository) {
        this.simianUtil = simianUtil;
        this.dnaRepository = dnaRepository;
    }

    public DNAType verifyDNA(String[] sequence) {

        simianUtil.validateDNASequence(sequence);
        DNAType dnaType = this.isSimian(sequence) ? DNAType.SIMIAN : DNAType.HUMAN;
        DNA dna = DNA.builder()
            .sequence(sequence)
            .type(dnaType)
            .build()
            .setHash();
        dnaRepository.save(dna);

        return dnaType;
    }

    public StatsDTO getDNAStats() {
        List<DNA> allDNA = this.dnaRepository.findAll();
        AtomicInteger countHuman = new AtomicInteger();
        AtomicInteger countSimian = new AtomicInteger();
        BigDecimal ratio = new BigDecimal(INITIAL_RATIO_ALL_SIMIAN);

        allDNA.forEach(dna -> {
            if(dna.getType() == DNAType.SIMIAN) {
                countSimian.getAndIncrement();
            } else {
                countHuman.getAndIncrement();
            }
        });

        int simians = countSimian.get();
        int humans = countHuman.get();

        if(humans != 0) {
            float percentage = (simians * 100f) / humans;
            ratio = new BigDecimal(percentage);
        }

        ratio = ratio.setScale(2, RoundingMode.HALF_EVEN);
        return StatsDTO.builder()
            .countHumanDNA(humans)
            .countSimianDNA(simians)
            .ratio(ratio)
            .build();
    }
    private boolean isSimian(String[] dna) {

        return simianUtil.checkHorizontalSimianSequence(dna) ||
                simianUtil.checkVerticalSimianSequence(dna) ||
                simianUtil.checkDiagonalFromBottomSimianSequence(dna) ||
                simianUtil.checkDiagonalFromTopSimianSequence(dna);
    }

}
