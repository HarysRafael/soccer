package com.soccerteam.service;

import com.soccerteam.controller.vo.TeamVO;
import com.soccerteam.exception.PlayerNotFoundException;
import com.soccerteam.exception.TeamAlreadyExistsException;
import com.soccerteam.exception.TeamAlreadyHasThisPlayerException;
import com.soccerteam.exception.TeamNotFoundException;
import com.soccerteam.model.Player;
import com.soccerteam.model.PlayerItem;
import com.soccerteam.model.Team;
import com.soccerteam.repository.TeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerService playerService;
    private final ModelMapper modelMapper;

    @Autowired
    public TeamService(TeamRepository teamRepository, ModelMapper modelMapper, PlayerService playerService, ModelMapper modelMapper1) {
        this.teamRepository = teamRepository;
        this.playerService = playerService;
        this.modelMapper = modelMapper;

    }

    public Team create(Team team) {
        teamRepository.findByNameAndActivateTrue(team.getName()).ifPresent(team1 -> {
            throw new TeamAlreadyExistsException("Team Already Exists.");
        });
        return teamRepository.save(team);

    }

    public Team updateTeamByPlayer(String teamId, String playerId) {
        Team team = getTeamById(teamId);
        Player player = playerService.getPlayerById(playerId);
        PlayerItem playerItem = modelMapper.map(player, PlayerItem.class);
        if (team.hasPlayerItem(playerId)) throw new TeamAlreadyHasThisPlayerException();
        team.insertPlayer(playerItem);
        return teamRepository.save(team);

    }

    public Optional<PlayerItem> findCaptain(String teamId) {
        Team team = getTeamById(teamId);
        return team.getPlayers().stream().filter(playerItem -> playerItem.getCaptain()).findFirst();

    }

    public Team teamCapitain(String playerId, String teamId) {
        Team team = getTeamById(teamId);
        if (!team.hasPlayerItem(playerId)) throw new PlayerNotFoundException();
        team.removeCaptain();
        team.addCaptain(playerId);
        return teamRepository.save(team);

    }

    public Team removePlayerByTeam(String playerId) {
        Team team = findTeamByPlayer(playerId);
        team.removePlayer(playerId);
        return teamRepository.save(team);

    }

    public Team updateTeam(Team team, String id) {
        Team updatedTeam = getTeamById(id);
        updatedTeam.updateTeam(team);
        return teamRepository.save(updatedTeam);

    }

    public Team findTeamByPlayer(String playerId) {
        Team team = teamRepository.findByPlayersId(playerId).orElseThrow(TeamNotFoundException::new);
        return team;

    }

    public TeamVO uniformVerification(String localTeamId, String outsideTeamId) {
        Team localTeam = getTeamById(localTeamId);
        Team outsideTeam = getTeamById(outsideTeamId);
        TeamVO teamVO = new TeamVO();
        teamVO.setLocalTeam(localTeam);
        teamVO.setVisitorTeam(localTeam, outsideTeam);
        return teamVO;

    }

    public PlayerItem highestSalaryPlayer(String id) {
        Team team = getTeamById(id);
        return team.getPlayers().stream().filter(player -> player.getId().equals(player.getId()))
                .max(Comparator.comparingLong(PlayerItem::getSalary)).get();

    }

    public PlayerItem bestTeamPlayer(String id) {
        Team team = getTeamById(id);
        return team.getPlayers().stream().filter(player -> player.getId().equals(player.getId()))
                .max(Comparator.comparingInt(PlayerItem::getSkillLevel)).get();

    }

    public Team deleteTeam(String id) {
        Team team = this.getTeamById(id);
        team.deactivate();
        return teamRepository.save(team);

    }

    public List<Team> getAll() {
        return teamRepository.findAll();

    }

    public Team getTeamByName(String name) {
        return teamRepository.findByNameAndActivateTrue(name).orElseThrow(TeamNotFoundException::new);

    }

    public Team getTeamById(String id) {
        return teamRepository.findByIdAndActivateTrue(id).orElseThrow(TeamNotFoundException::new);

    }

}
