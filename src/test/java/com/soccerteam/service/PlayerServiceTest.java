package com.soccerteam.service;

import com.soccerteam.exception.PlayerNotFoundException;
import com.soccerteam.factories.PlayerFactory;
import com.soccerteam.model.Player;
import com.soccerteam.repository.PlayerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
public class PlayerServiceTest {

    @InjectMocks
    private PlayerService playerService;
    private PlayerFactory playerFactory;

    @Mock
    private PlayerRepository playerRepository;

    @Test
    @DisplayName("Create a player and return a created player")
    public void createAndReturnAPlayer() {
        Player player = playerFactory.getCreateUpdatePlayer();
        Player getPlayer = playerFactory.getExistsPlayer();

        Mockito.when(playerRepository.findByNameAndActivateTrue(player.getName())).thenReturn(Optional.empty());
        Mockito.when(playerRepository.save(player)).thenReturn(getPlayer);

        Player createdPlayer = playerService.create(player);

        Assertions.assertThat(createdPlayer.getName()).isEqualTo(player.getName());
        Assertions.assertThat(createdPlayer.getSkillLevel()).isEqualTo(player.getSkillLevel());
        Assertions.assertThat(createdPlayer.getSalary()).isEqualTo(player.getSalary());
        Assertions.assertThat(createdPlayer.getBirthDate()).isEqualTo(player.getBirthDate());

    }

    @Test
    @DisplayName("Find player by name")
    public void findPlayerByName() {
        Player getPlayer = playerFactory.getExistsPlayer();

        Mockito.when(playerRepository.findByNameAndActivateTrue(getPlayer.getName())).thenReturn(Optional.of(getPlayer));

        Player playerFound = playerService.getPlayerByName(getPlayer.getName());

        Assertions.assertThat(playerFound).isEqualTo(getPlayer);

    }

    @Test
    @DisplayName("Find player by Id")
    public void findPlayerById() {
        Player getPlayer = playerFactory.getExistsPlayer();

        Mockito.when(playerRepository.findByIdAndActivateTrue(getPlayer.getId())).thenReturn(Optional.of(getPlayer));

        Player playerFound = playerService.getPlayerById(getPlayer.getId());

        Assertions.assertThat(playerFound).isEqualTo(getPlayer);

    }

    @Test
    @DisplayName("Returns Exception when player doesn't exists searching by id")
    public void exceptionWhenCantFindPlayerById() {
        Player getPlayer = playerFactory.getExistsPlayer();

        Mockito.when(playerRepository.findByIdAndActivateTrue(getPlayer.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> playerService.getPlayerById(getPlayer.getId()))
                .isInstanceOf(PlayerNotFoundException.class);

    }

    @Test
    @DisplayName("Returns Exception when player doesn't exists searching by name")
    public void exceptionWhenCantFindPlayerByName() {
        Player getPlayer = playerFactory.getExistsPlayer();

        Mockito.when(playerRepository.findByNameAndActivateTrue(getPlayer.getName())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> playerService.getPlayerByName(getPlayer.getName()))
                .isInstanceOf(PlayerNotFoundException.class);

    }

}



