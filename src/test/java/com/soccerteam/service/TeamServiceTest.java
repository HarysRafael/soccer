package com.soccerteam.service;

import com.soccerteam.exception.TeamAlreadyExistsException;
import com.soccerteam.exception.TeamNotFoundException;
import com.soccerteam.factories.TeamFactory;
import com.soccerteam.model.Team;
import com.soccerteam.repository.TeamRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class TeamServiceTest {

    @InjectMocks
    private TeamService teamService;
    private TeamFactory teamFactory;

    @Mock
    private TeamRepository teamRepository;

    @Test
    @DisplayName("Creates a team and returns a created team")
    public void createAndReturnATeam() {
        Team team = teamFactory.getCreateUpdateTeam();
        Team getTeam = teamFactory.getExistsTeam();

        when(teamRepository.findByNameAndActivateTrue(team.getName())).thenReturn(Optional.empty());
        when(teamRepository.save(team)).thenAnswer(AdditionalAnswers.returnsFirstArg());

        Team createdTeam = teamService.create(team);

        assertThat(createdTeam.getName()).isEqualTo(team.getName());
        assertThat(createdTeam.getHeadUniformColor()).isEqualTo(team.getHeadUniformColor());
        assertThat(createdTeam.getReserveUniformColor()).isEqualTo(team.getReserveUniformColor());
        assertThat(createdTeam.getCreationDateTeam()).isEqualTo(team.getCreationDateTeam());

    }

    @Test
    @DisplayName("Returns exception when find team with a same name")
    public void exceptionWhenFindTeamWithASameName() {
        Team team = teamFactory.getCreateUpdateTeam();

        when(teamRepository.findByNameAndActivateTrue(team.getName())).thenReturn(Optional.of(team));

        assertThatThrownBy(() -> teamService.create(team))
                .isInstanceOf(TeamAlreadyExistsException.class);

    }

    @Test
    @DisplayName("Update a team and returns a updated team")
    public void updateAndReturnATeam() {
        Team createUpdateTeam = teamFactory.getCreateUpdateTeam();
        Team existsTeam = teamFactory.getExistsTeam();

        when(teamRepository.findByIdAndActivateTrue(existsTeam.getId())).thenReturn(Optional.of(existsTeam));
        when(teamRepository.save(Mockito.any(Team.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        Team finalUpdateTeam = teamService.updateTeam(createUpdateTeam, existsTeam.getId());

        assertThat(finalUpdateTeam.getName()).isEqualTo(createUpdateTeam.getName());
        assertThat(finalUpdateTeam.getHeadUniformColor()).isEqualTo(createUpdateTeam.getHeadUniformColor());
        assertThat(finalUpdateTeam.getReserveUniformColor()).isEqualTo(createUpdateTeam.getReserveUniformColor());
        assertThat(finalUpdateTeam.getCreationDateTeam()).isEqualTo(createUpdateTeam.getCreationDateTeam());

    }

    @Test
    @DisplayName("Returns Exception when team doesn't exists searching by id")
    public void exceptionWhenCantFindTeamById() {
        Team getTeam = teamFactory.getExistsTeam();

        when(teamRepository.findByIdAndActivateTrue(getTeam.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> teamService.getTeamById(getTeam.getId()))
                .isInstanceOf(TeamNotFoundException.class);

    }

    @Test
    @DisplayName("Returns Exception when team doesn't exists searching by name")
    public void exceptionWhenCantFindTeamByName() {
        Team getTeam = teamFactory.getExistsTeam();

        when(teamRepository.findByNameAndActivateTrue(getTeam.getName())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> teamService.getTeamByName(getTeam.getName()))
                .isInstanceOf(TeamNotFoundException.class);

    }

    @Test
    @DisplayName("Delete team and returns deleted team")
    public void returnsDeletedTeam() {
        Team getTeam = teamFactory.getExistsTeam();

        when(teamRepository.findByIdAndActivateTrue(getTeam.getId())).thenReturn(Optional.of(getTeam));
        when(teamRepository.save(getTeam)).thenAnswer(AdditionalAnswers.returnsFirstArg());

        Team finalDeletedTeam = teamService.deleteTeam(getTeam.getId());

        assertThat(finalDeletedTeam.isActivate()).isFalse();
        verify(teamRepository, times(1)).save(getTeam);

    }

    @Test
    @DisplayName("Return team by name")
    public void returnsTeamByName() {
        Team getTeam = teamFactory.getExistsTeam();

        when(teamRepository.findByNameAndActivateTrue(getTeam.getName())).thenReturn(Optional.of(getTeam));

        Team teamFound = teamService.getTeamByName(getTeam.getName());

        assertThat(teamFound).isNotNull();
        assertThat(getTeam).isEqualTo(teamFound);

    }

    @Test
    @DisplayName("Return team by id")
    public void returnsTeamById() {
        Team getTeam = teamFactory.getExistsTeam();

        when(teamRepository.findByIdAndActivateTrue(getTeam.getId())).thenReturn(Optional.of(getTeam));

        Team teamFound = teamService.getTeamById(getTeam.getId());

        assertThat(getTeam).isEqualTo(teamFound);

    }

    @Test
    @DisplayName("Returns a team with a removed player")
    public void returnsTeamWithoutPlayer() {
        Team team = teamFactory.teamWithPlayers();
        String playerId = team.getPlayers().get(0).getId();

        when(teamRepository.findByPlayersId(playerId)).thenReturn(Optional.of(team));
        when(teamRepository.save(Mockito.any(Team.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        Team teamWithoutPlayer = teamService.removePlayerByTeam(playerId);

        assertThat(teamWithoutPlayer.getPlayers()).hasSize(0);
        assertThat(teamWithoutPlayer.getPlayers().stream().anyMatch(player -> team.getId() == "1")).isFalse();

    }

    @Test
    @DisplayName("Returns a list of team")
    public void returnsTeamList() {
        List<Team> teamList = new ArrayList<Team>();
        Team team = teamFactory.getExistsTeam();
        teamList.add(team);

        when(teamRepository.findByIdAndActivateTrue(team.getId())).thenReturn(Optional.of(team));
        when(teamRepository.save(teamList.get(0))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        teamList = teamService.getAll();

        assertThat(teamList).hasSize(1);

    }

}
