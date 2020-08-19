package com.soccerteam.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerItem {
    private String id;
    private String name;
    private Long salary;
    private Integer skillLevel;
    private boolean captain=false;

    public boolean getCaptain(){
        return captain;
    }

}
