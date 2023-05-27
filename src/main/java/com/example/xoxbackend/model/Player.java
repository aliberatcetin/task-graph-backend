package com.example.xoxbackend.model;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class Player {
    private ObjectId id;

    private String name;

    private String transfermarkt_id;

    private String foot;

    private String image_url;

    private String nationality;
}
