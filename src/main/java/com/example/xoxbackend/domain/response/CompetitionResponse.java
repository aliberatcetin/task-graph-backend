package com.example.xoxbackend.domain.response;

import com.example.xoxbackend.model.TeamMatching;
import com.example.xoxbackend.model.vertex.TeamVertex;
import com.example.xoxbackend.model.vertex.Vertex;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompetitionResponse {

    private Vertex vertex1;
    private Vertex vertex2;
    private Float elo;
    private List<String> edges;

    public static CompetitionResponse convertFromTeamMatchingModel(TeamMatching teamMatching) {

        var vertex1 = new TeamVertex(teamMatching.getTeam1().toString(), teamMatching.getTeam1_league().toString());
        var vertex2 = new TeamVertex(teamMatching.getTeam2().toString(), teamMatching.getTeam2_league().toString());

        return CompetitionResponse.builder()
                .vertex1(vertex1)
                .vertex2(vertex2)
                .elo(teamMatching.getElo())
                .edges(teamMatching.getCommon_players())
                .build();
    }
}
