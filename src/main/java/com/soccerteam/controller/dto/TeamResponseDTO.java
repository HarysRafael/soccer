package com.soccerteam.controller.dto;

import com.soccerteam.model.PlayerItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeamResponseDTO {
    private String id;
    private String name;
    private String headUniformColor;
    private String reserveUniformColor;
    private LocalDate creationDateTeam;
    private List<PlayerItem> players;

}
