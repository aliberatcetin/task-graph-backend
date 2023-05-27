package com.example.xoxbackend.service;

import com.example.xoxbackend.model.Rank;
import com.example.xoxbackend.model.TeamMatching;
import com.example.xoxbackend.repository.TeamMatchingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompetitionService {

    private final TeamMatchingRepository teamMatchingRepository;

    public List<TeamMatching> createCompetition(int boardSize, Rank rank, String leagueId) {

        float minElo = rank.getElo();
        float maxElo = minElo + 50;
        switch (rank) {
            case EASY -> maxElo = Float.MAX_VALUE;
            case MEDIUM -> maxElo = Rank.EASY.getElo();
            case HARD -> maxElo = Rank.MEDIUM.getElo();
        }
        return teamMatchingRepository.getTeamMatchings(minElo, maxElo, leagueId, boardSize);
    }

}
