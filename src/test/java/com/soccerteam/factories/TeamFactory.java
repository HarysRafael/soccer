package com.soccerteam.factories;

import com.soccerteam.model.PlayerItem;
import com.soccerteam.model.Team;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamFactory {

    public static Team getCreateUpdateTeam() {
        return Team.builder()
                .creationDateTeam(LocalDate.of(1988, Month.MARCH, 20))
                .headUniformColor("Red")
                .reserveUniformColor("Black")
                .name("Milan")
                .activate(true)
                .build();

    }

    public static Team getExistsTeam() {
        return Team.builder()
                .id("1")
                .creationDateTeam(LocalDate.of(1988, Month.MARCH, 20))
                .headUniformColor("Red")
                .reserveUniformColor("Black")
                .name("Milan")
                .activate(true)
                .build();

    }

    public static Team teamWithPlayers() {
        List<PlayerItem> playerItems = new ArrayList<PlayerItem>(Arrays.asList(PlayerItem.builder()
                .id("1")
                .name("Neymar Jr")
                .skillLevel(92)
                .salary((long) 40000000)
                .captain(true)
                .build()));

        return Team.builder()
                .id("1")
                .creationDateTeam(LocalDate.of(1988, Month.MARCH, 20))
                .headUniformColor("Red")
                .reserveUniformColor("Black")
                .name("Milan")
                .activate(true)
                .players(playerItems)
                .build();

    }
}
