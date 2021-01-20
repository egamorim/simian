package com.egamorim.simian.util;

import java.util.Arrays;
import java.util.regex.Pattern;

public class SimianUtil {

    private static String[] simianSequence = {"AAAA", "TTTT", "CCCC", "GGGG"};
    private static Pattern unacceptableCharsPattern = Pattern.compile("[^AaTtCcGg]");

    public boolean checkHorizontalSimianSequence(String[] dna) {
        for(String seq: dna) {
            for(String sim: simianSequence) {
                if(seq.contains(sim)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkVerticalSimianSequence(String[] dna) {
        String[] sequence = dna;
        for(int index = 0; index < sequence.length; index++){
            String current = null;
            int currentCount = 0;
            for(String seq: sequence) {
                if(String.valueOf(seq.charAt(index)).equalsIgnoreCase(current)) {
                    currentCount++;
                } else {
                    currentCount = 1;
                    current = String.valueOf(seq.charAt(index));
                }
                if(currentCount >= 4) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkDiagonalFromBottomSimianSequence(String[] dna) {
        int sequenceSize = dna.length;
        int totalInteractions = (sequenceSize * 2) -1;
        int line = sequenceSize - 1;
        int col = 0;

        String current = null;
        int currentCount = 0;
        int counterIncrLine = 0;
        int counterIncrCol = 1;

        for(int count = 1; count <= totalInteractions; count++) {
            do{
                if(line >= 0) {
                    String seq = dna[line];
                    String value = String.valueOf(seq.charAt(col));

                    if(value.equalsIgnoreCase(current)) {
                        currentCount++;
                    } else {
                        currentCount = 1;
                        current = value;
                    }
                    if(currentCount >= 4) {
                        return true;
                    }
                }
                line--;
                col--;
            } while (col >= 0);

            if(counterIncrCol >= sequenceSize) {
                counterIncrLine = sequenceSize - 1;
                counterIncrCol = sequenceSize;
            } else {
                counterIncrLine++;
                counterIncrCol++;
            }
            col = col + counterIncrCol;
            line = line + counterIncrLine;
        }
        return false;
    }

    public boolean checkDiagonalFromTopSimianSequence(String[] dna) {
        int sequenceSize = dna.length;
        int totalInteractions = (sequenceSize * 2) -1;
        int line = 0;
        int col = 0;

        String current = null;
        int currentCount = 0;
        int counterIncrLine = 0;
        int counterIncrCol = 1;

        for(int count = 1; count <= totalInteractions; count++) {
            do{
                if(line < sequenceSize) {
                    String seq = dna[line];
                    String value = String.valueOf(seq.charAt(col));
                    if(value.equalsIgnoreCase(current)) {
                        currentCount++;
                    } else {
                        currentCount = 1;
                        current = value;
                    }
                    if(currentCount >= 4) {
                        return true;
                    }
                }
                line++;
                col--;
            } while (col >= 0);

            if(counterIncrCol >= sequenceSize) {
                counterIncrLine = sequenceSize - 1;
                counterIncrCol = sequenceSize;
            } else {
                counterIncrLine++;
                counterIncrCol++;
            }
            col = col + counterIncrCol;
            line = line - counterIncrLine;
        }
        return false;
    }

    public void validateDNASequence(String[] sequence) {
        this.validateSquareMatrix(sequence);
        this.validateMatrixElements(sequence);
    }

    private void validateSquareMatrix(String[] sequence) {
        Arrays.stream(sequence)
            .forEach(d -> {
                if(d.length() != sequence.length) {
                    throw new InvalidDNASequenceException("The DNA sequence must be a square matrix. The provided matrix has at least one line with a different number of columns.");
                }
            });
    }

    private void validateMatrixElements(String[] sequence) {
        if(Arrays.stream(sequence)
            .anyMatch(dna -> unacceptableCharsPattern.matcher(dna).find())) {
            throw new InvalidDNASequenceException("The provided DNA sequence has at least one invalid character. Acceptable characters are: A - T - C - G");
        }
    }
}
