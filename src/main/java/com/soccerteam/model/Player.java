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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Player {
    @Id
    private String id;
    private String name;
    private Integer skillLevel;
    private Long salary;
    private LocalDate birthDate;
    private boolean activate=true;
    @Version
    private Long version;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void deactivate(){
        this.activate=false;

    }

    public void updatePlayer(Player player){
        this.name=player.getName();
        this.skillLevel=player.getSkillLevel();
        this.salary=player.getSalary();

    }

}
