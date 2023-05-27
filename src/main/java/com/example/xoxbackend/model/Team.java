package com.example.xoxbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Team {

    private String name;

    @JsonProperty("transfermarkt_id")
    private String transfermarktId;

    @JsonProperty("_id")
    private String id;

}
