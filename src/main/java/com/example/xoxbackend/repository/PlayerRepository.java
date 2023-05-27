package com.example.xoxbackend.repository;

import com.example.xoxbackend.model.Player;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.TextSearchOptions;
import lombok.RequiredArgsConstructor;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Repository
@RequiredArgsConstructor
public class PlayerRepository {
    private final MongoCollection<Player> playerMongoCollection;

    public Player getPlayerById(String id) {

        return playerMongoCollection.find(eq("_id", new ObjectId(id))).first();
    }

    public List<Player> searchPlayer(String searchKeyword) {
        TextSearchOptions options = new TextSearchOptions().caseSensitive(false).language("english");
        var filter = Filters.text(searchKeyword,options);
       return playerMongoCollection.find(filter).into(new ArrayList<>());

    }
}
