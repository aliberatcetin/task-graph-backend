package com.example.xoxbackend.domain.response;

import com.example.xoxbackend.model.Player;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@Builder
public class PlayerResponse {

    private String id;

    private String name;

    private String foot;

    private String imageUrl;

    private String nationality;


    public static PlayerResponse convertFromPlayer(Player player) {

        return PlayerResponse.builder()
                .id(player.getId().toHexString())
                .name(player.getName())
                .foot(player.getFoot())
                .imageUrl(player.getImage_url())
                .nationality(player.getNationality())
                .build();
    }

}
