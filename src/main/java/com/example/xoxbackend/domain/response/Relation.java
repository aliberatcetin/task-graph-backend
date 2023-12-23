package com.example.xoxbackend.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Relation {
    private String source;
    private String target;
    private String type = "empty";
}
