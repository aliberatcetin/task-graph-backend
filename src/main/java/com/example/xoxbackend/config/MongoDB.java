package com.example.xoxbackend.config;

import com.example.xoxbackend.model.Player;
import com.example.xoxbackend.model.TeamMatching;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoDB {

    @Value("${mongodb.connection-string}")
    private String mongodbConnectionString;


    @Value("${mongodb.database}")
    private String database;

    @Value("${mongodb.collection.player}")
    private String playerCollection;
    @Value("${mongodb.collection.team-matching}")
    private String teamMatchingCollection;

    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(mongodbConnectionString);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoDatabase footballDatabase(MongoClient mongoClient) {
        CodecRegistry pojoCodecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        return mongoClient.getDatabase(database).withCodecRegistry(pojoCodecRegistry);
    }

    @Bean
    public MongoCollection<Player> playerMongoCollection(MongoDatabase footballDatabase) {
        return footballDatabase.getCollection(playerCollection, Player.class);
    }

    @Bean
    public MongoCollection<Document> doc(MongoDatabase footballDatabase) {
        return footballDatabase.getCollection(teamMatchingCollection);
    }

    @Bean
    public MongoCollection<TeamMatching> teamMatchingMongoCollection(MongoDatabase footballDatabase) {
        return footballDatabase.getCollection(teamMatchingCollection, TeamMatching.class);
    }


}
