package com.example.xoxbackend.model.vertex;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamVertex extends Vertex{

    private String teamId;
    private String leagueId;

    public TeamVertex(String teamId, String leagueId) {
        super(VertexType.TEAM);
        this.teamId = teamId;
        this.leagueId = leagueId;
    }
}
