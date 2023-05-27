package com.example.xoxbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class TeamMatching {
    private ObjectId team1;
    private ObjectId team2;

    private float elo;

    @JsonProperty("commonPlayers")
    private List<String> common_players;

    @JsonProperty("team1League")
    private ObjectId team1_league;

    @JsonProperty("team2League")
    private ObjectId team2_league;

    @JsonProperty("_id")
    private ObjectId id;
}
