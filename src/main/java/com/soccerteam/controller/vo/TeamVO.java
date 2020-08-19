package com.soccerteam.controller.vo;

import com.soccerteam.model.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamVO {

    private String nameLocalTeam;
    private String uniformLocalTeam;
    private String nameVisitorTeam;
    private String uniformVisitorTeam;

    public void setLocalTeam(Team team){
        setNameLocalTeam(team.getName());
        setUniformLocalTeam(team.getHeadUniformColor());

    }

    public void setVisitorTeam(Team localTeam, Team visitorTeam){
        setNameVisitorTeam(visitorTeam.getName());
        if (visitorTeam.getHeadUniformColor().equals(localTeam.getHeadUniformColor())) {
            setUniformVisitorTeam(visitorTeam.getReserveUniformColor());
        } else {
            setUniformVisitorTeam(visitorTeam.getHeadUniformColor());
        }

    }

}
