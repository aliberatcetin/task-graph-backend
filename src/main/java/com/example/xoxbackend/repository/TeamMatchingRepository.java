package com.example.xoxbackend.repository;


import com.example.xoxbackend.model.TeamMatching;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import lombok.RequiredArgsConstructor;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

@Repository
@RequiredArgsConstructor
public class TeamMatchingRepository {

    private final MongoCollection<TeamMatching> playerMongoCollection;


    public List<TeamMatching> getTeamMatchings(float minElo, float maxElo, String leagueId, int numberOfMatchingsReturned) {

        Bson filter = and(gt("elo", minElo), lt("elo", maxElo));

        if (leagueId != null) {
            filter = and(filter,
                    or(eq("team1_league", new ObjectId(leagueId)), eq("team2_league", new ObjectId(leagueId))));
        }

        List<TeamMatching> teamMatchingList = new ArrayList<>();

        playerMongoCollection.aggregate(Arrays.asList(Aggregates.match(filter), Aggregates.sample(numberOfMatchingsReturned))).forEach(teamMatchingList::add);

        return teamMatchingList;
    }
}
