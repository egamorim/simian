package com.egamorim.simian.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Document
public class DNA {

    @Id
    private String id;
    private DNAType type;
    private String[] sequence;
    @Indexed(unique=true)
    private String hash;

    public DNA setHash() {
        if(this.sequence != null && this.sequence.length > 0) {
            this.hash = "";
            for(String s: this.sequence) {
                this.hash += s;
            }
        }
        return this;
    }
}
