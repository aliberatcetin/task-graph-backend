package com.example.xoxbackend.service;

import com.example.xoxbackend.domain.response.CompetitionResponse;
import com.example.xoxbackend.model.Rank;
import com.example.xoxbackend.model.TeamMatching;
import com.example.xoxbackend.repository.TeamMatchingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompetitionService {

    private final TeamMatchingRepository teamMatchingRepository;

    public List<CompetitionResponse> createFootballCompetition(int boardSize, Rank rank, String leagueId) {

        float minElo = rank.getElo();
        float maxElo = minElo + 50;
        switch (rank) {
            case EASY -> maxElo = Float.MAX_VALUE;
            case MEDIUM -> maxElo = Rank.EASY.getElo();
            case HARD -> maxElo = Rank.MEDIUM.getElo();
        }
        var teamMatchings = teamMatchingRepository.getTeamMatchings(minElo, maxElo, leagueId, boardSize);
        return teamMatchings.stream().map(CompetitionResponse ::convertFromTeamMatchingModel).collect(Collectors.toList());
    }

}
