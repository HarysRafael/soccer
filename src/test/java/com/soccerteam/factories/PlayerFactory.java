package com.soccerteam.factories;

import com.soccerteam.model.Player;

import java.time.LocalDate;

public class PlayerFactory {

    public static Player getCreateUpdatePlayer() {
        return Player.builder()
                .name("Neymar Jr")
                .skillLevel(92)
                .salary((long) 40000000)
                .birthDate(LocalDate.of(1994, 04, 03))
                .build();
    }

    public static Player getExistsPlayer() {
        return Player.builder()
                .id("1")
                .name("Neymar Jr")
                .skillLevel(92)
                .salary((long) 40000000)
                .birthDate(LocalDate.of(1994, 04, 03))
                .build();
    }
}
