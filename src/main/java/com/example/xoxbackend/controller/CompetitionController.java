package com.example.xoxbackend.controller;


import com.example.xoxbackend.domain.response.CompetitionResponse;
import com.example.xoxbackend.model.Rank;
import com.example.xoxbackend.model.TeamMatching;
import com.example.xoxbackend.service.CompetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/competition")
public class CompetitionController {

    private final CompetitionService competitionService;

    @GetMapping(value = "/create-football-competition")
    public List<CompetitionResponse> createFootballCompetition(@RequestParam int boardSize, @RequestParam Rank rank, @RequestParam(required = false) String leagueId) {
        return competitionService.createFootballCompetition(boardSize, rank, leagueId);
    }
}
