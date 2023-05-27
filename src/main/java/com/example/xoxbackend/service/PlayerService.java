package com.example.xoxbackend.service;

import com.example.xoxbackend.domain.response.PlayerResponse;
import com.example.xoxbackend.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerResponse getPlayerById(String playerId) {
        var player = playerRepository.getPlayerById(playerId);
        return PlayerResponse.convertFromPlayer(player);
    }

    public List<PlayerResponse> searchPlayer(String searchKeyword) {
        var player = playerRepository.searchPlayer(searchKeyword);
        return player.stream().map(PlayerResponse::convertFromPlayer).collect(Collectors.toList());
    }
}
