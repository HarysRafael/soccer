package com.soccerteam.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
@Builder
public class Team {
    @Id
    private String id;
    private String name;
    private String headUniformColor;
    private String reserveUniformColor;
    private LocalDate creationDateTeam;
    private List<PlayerItem> players = new ArrayList<>();
    private boolean activate = true;
    @Version
    private Long version;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void deactivate() {
        this.activate = false;

    }

    public boolean hasPlayerItem(String playerId) {
        return this.players.stream().anyMatch(item -> item.getId().equals(playerId));

    }

    public void removeCaptain() {
        players.stream().filter(PlayerItem::getCaptain).findFirst().ifPresent(item -> item.setCaptain(false));

    }

    public void addCaptain(String playerId) {
        players.stream().filter(playerItem -> playerItem.getId().equals(playerId)).findFirst().ifPresent(item -> item.setCaptain(true));

    }

    public void insertPlayer(PlayerItem player) {
        players.add(player);

    }

    public void updateTeam(Team team){
        this.name = team.getName();
        this.headUniformColor=team.getHeadUniformColor();
        this.reserveUniformColor=team.getReserveUniformColor();

    }

    public void removePlayer(String id) {
        this.players.removeIf(playerItem -> playerItem.getId().equals(id));

    }

}
