package com.soccerteam.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerResponseDTO {
    private String id;
    private String team;
    private String name;
    private Integer skillLevel;
    private Long salary;
    private LocalDate birthDate;

}
