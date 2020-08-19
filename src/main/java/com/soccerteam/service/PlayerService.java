package com.soccerteam.service;

import com.soccerteam.exception.PlayerAlreadyExistsException;
import com.soccerteam.exception.PlayerNotFoundException;
import com.soccerteam.model.Player;
import com.soccerteam.model.Team;
import com.soccerteam.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamService teamService;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, @Lazy TeamService teamService) {
        this.playerRepository = playerRepository;
        this.teamService = teamService;

    }

    public Player create(Player player) {
        playerRepository.findByNameAndActivateTrue(player.getName()).ifPresent(player1 -> {
            throw new PlayerAlreadyExistsException("Player Already Exists.");
        });
        return playerRepository.save(player);

    }

    public Player update(Player player, String playerId) {
        Player updatedPlayer = this.getPlayerById(playerId);
        updatedPlayer.updatePlayer(player);
        Team team = teamService.removePlayerByTeam(playerId);
        teamService.updateTeamByPlayer(updatedPlayer.getId(), team.getId());
        return playerRepository.save(updatedPlayer);

    }

    public void delete(String playerId) {
        Player player = this.getPlayerById(playerId);
        teamService.removePlayerByTeam(playerId);
        player.deactivate();
        playerRepository.save(player);

    }

    public List<Player> seekTopPlayers(Long limit) {
        List<Player> players = getAll();
        return players.stream().sorted(
                Comparator.comparingInt(Player::getSkillLevel).reversed()
        ).limit(limit).collect(Collectors.toList());

    }

    public List<Player> allPlayersFromSkill() {
        List<Player> players = getAll();
        return players.stream().sorted(
                Comparator.comparingInt(Player::getSkillLevel).reversed()
        ).limit(players.size()).collect(Collectors.toList());

    }

    public List<Player> getAll() {
        return playerRepository.findAll();

    }

    public Player getPlayerByName(String name) {
        return playerRepository.findByNameAndActivateTrue(name).orElseThrow(PlayerNotFoundException::new);

    }

    public Player getPlayerById(String id) {
        return playerRepository.findByIdAndActivateTrue(id).orElseThrow(PlayerNotFoundException::new);

    }

}
