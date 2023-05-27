package com.example.xoxbackend.model;

public enum Rank {
    EASY(2200.0f),
    MEDIUM(1200.0f),
    HARD(0.0f);

    private final float elo;

    Rank(final float newValue) {
        elo = newValue;
    }

    public float getElo() {
        return elo;
    }
}
