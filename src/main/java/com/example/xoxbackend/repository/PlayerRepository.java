package com.example.xoxbackend.repository;

import com.example.xoxbackend.model.Player;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;

@Repository
@RequiredArgsConstructor
public class PlayerRepository {
    private final MongoCollection<Player> playerMongoCollection;

    public Player getPlayerById(String id) {

        return playerMongoCollection.find(eq("_id", new ObjectId(id))).first();
    }
}
