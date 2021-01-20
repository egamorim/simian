package com.egamorim.simian.api;

import com.egamorim.simian.model.DNAType;
import com.egamorim.simian.service.DNAService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simian")
public class SimianController {

    private final DNAService simianService;

    public SimianController(DNAService simianService) {
        this.simianService = simianService;
    }

    @PostMapping
    public ResponseEntity checkSimian(@RequestBody SimianRequest simianRequest) {

        String[] dna = simianRequest.getDna();
        DNAType dnaType = simianService.verifyDNA(dna);
        return dnaType == DNAType.SIMIAN ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/stats")
    public ResponseEntity getStats() {
        return ResponseEntity.ok(simianService.getDNAStats());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
