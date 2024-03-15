package com.example.graphbackend.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class Relation {
    private String source;
    private String target;
    private String type = "empty";
    @Builder.Default
    private String handleText = "DEPENDENT_TO";
}
