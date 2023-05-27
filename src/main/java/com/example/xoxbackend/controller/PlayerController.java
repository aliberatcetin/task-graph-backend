package com.example.xoxbackend.controller;

import com.example.xoxbackend.domain.response.PlayerResponse;
import com.example.xoxbackend.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/{playerId}")
    public PlayerResponse getPlayerById(@PathVariable String playerId) {
        return playerService.getPlayerById(playerId);
    }

    @GetMapping("/search-player")
    public List<PlayerResponse> searchPlayer(@RequestParam String searchKeyword) {
        return playerService.searchPlayer(searchKeyword);
    }
}
