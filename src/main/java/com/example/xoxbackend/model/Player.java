package com.example.xoxbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Player {
    private String name;

    @JsonProperty("transfermarkt_id")
    private String transfermarktId;

    @JsonProperty("_id")
    private String id;
}
