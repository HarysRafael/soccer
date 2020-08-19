package com.soccerteam.controller;

import com.soccerteam.controller.dto.TeamDTO;
import com.soccerteam.controller.dto.TeamResponseDTO;
import com.soccerteam.controller.dto.PlayerIdDTO;
import com.soccerteam.controller.vo.TeamVO;
import com.soccerteam.model.PlayerItem;
import com.soccerteam.model.Team;
import com.soccerteam.service.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;
    private final ModelMapper modelMapper;

    @Autowired
    public TeamController(TeamService teamService, ModelMapper modelMapper) {
        this.teamService = teamService;
        this.modelMapper = modelMapper;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamResponseDTO create(@RequestBody TeamDTO teamDTO) {
        Team team = modelMapper.map(teamDTO, Team.class);
        Team createdTeam = teamService.create(team);
        return modelMapper.map(createdTeam, TeamResponseDTO.class);

    }

    @PostMapping("/{teamId}/captain")
    @ResponseStatus(HttpStatus.CREATED)
    public TeamResponseDTO teamCaptain(@RequestBody PlayerIdDTO playerIdDTO, @PathVariable String teamId) {
        Team teamWithCaptain = teamService.teamCapitain(playerIdDTO.getId(), teamId);
        return modelMapper.map(teamWithCaptain, TeamResponseDTO.class);

    }

    @DeleteMapping("/{teamid}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String teamId) {
        teamService.deleteTeam(teamId);

    }

    @PutMapping("/{teamId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TeamResponseDTO update(@RequestBody TeamDTO teamDTO, @PathVariable String teamId) {
        Team team = modelMapper.map(teamDTO, Team.class);
        Team updatedTeam = teamService.updateTeam(team, teamId);
        return modelMapper.map(updatedTeam, TeamResponseDTO.class);

    }

    @PostMapping("/{teamId}/player")
    @ResponseStatus(HttpStatus.CREATED)
    public TeamResponseDTO insertPlayerInTeam(@RequestBody PlayerIdDTO playerIdDTO, @PathVariable String teamId) {
        Team playerInsertedTeam = teamService.updateTeamByPlayer(teamId, playerIdDTO.getId());
        return modelMapper.map(playerInsertedTeam, TeamResponseDTO.class);

    }

    @GetMapping("/{teamId}/captain")
    @ResponseStatus(HttpStatus.OK)
    public Optional<PlayerItem> getCaptain(@PathVariable String teamId) {
        return teamService.findCaptain(teamId);

    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<TeamResponseDTO> getAllTeams() {
        return teamService.getAll().stream()
                .map(team -> {
                    return modelMapper.map(team, TeamResponseDTO.class);
                })
                .collect(Collectors.toList());

    }

    @GetMapping("/search/")
    @ResponseStatus(HttpStatus.OK)
    public TeamResponseDTO findTeamName(@RequestParam String name) {
        Team team = teamService.getTeamByName(name);
        return modelMapper.map(team, TeamResponseDTO.class);

    }

    @GetMapping("/{id}/bestplayer")
    @ResponseStatus(HttpStatus.OK)
    public PlayerItem bestTeamPlayer(@PathVariable String id) {
        return teamService.bestTeamPlayer(id);

    }

    @GetMapping("/{id}/salaryplayer")
    @ResponseStatus(HttpStatus.OK)
    public PlayerItem highestSalaryPlayer(@PathVariable String id) {
        return teamService.highestSalaryPlayer(id);

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TeamResponseDTO searchPlayersByTeamId(@PathVariable String id) {
        Team team = teamService.getTeamById(id);
        return modelMapper.map(team, TeamResponseDTO.class);

    }

    @GetMapping("/{localTeamId}/team-outside/{outsideTeamId}")
    @ResponseStatus(HttpStatus.OK)
    public TeamVO lookForShirtColor(@PathVariable String localTeamId, @PathVariable String outsideTeamId) {
        return teamService.uniformVerification(localTeamId, outsideTeamId);

    }

}
