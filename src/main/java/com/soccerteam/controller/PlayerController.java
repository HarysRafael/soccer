package com.soccerteam.controller;

import com.soccerteam.controller.dto.PlayerDTO;
import com.soccerteam.controller.dto.PlayerResponseDTO;
import com.soccerteam.model.Player;
import com.soccerteam.service.PlayerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;
    private final ModelMapper modelMapper;

    @Autowired
    public PlayerController(PlayerService playerService, ModelMapper modelMapper) {
        this.playerService = playerService;
        this.modelMapper = modelMapper;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerResponseDTO create(@RequestBody @Valid PlayerDTO playerDTO) {
        Player player = modelMapper.map(playerDTO, Player.class);
        Player createdPlayer = playerService.create(player);
        return modelMapper.map(createdPlayer, PlayerResponseDTO.class);

    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public PlayerResponseDTO findByName(@RequestParam String name) {
        Player player = playerService.getPlayerByName(name);
        return modelMapper.map(player, PlayerResponseDTO.class);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PlayerResponseDTO> getAllPlayers() {
        return playerService.getAll().stream()
                .map(player -> {
                    return modelMapper.map(player, PlayerResponseDTO.class);
                })
                .collect(Collectors.toList());

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerResponseDTO update(@PathVariable String id, @RequestBody PlayerDTO playerDTO) {
        Player player = modelMapper.map(playerDTO, Player.class);
        Player updatedPlayer = playerService.update(player, id);
        return modelMapper.map(updatedPlayer, PlayerResponseDTO.class);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id) {
        playerService.delete(id);

    }

    @GetMapping("/bestplayers")
    @ResponseStatus(HttpStatus.OK)
    public List<PlayerResponseDTO> seekTopPlayers(@RequestParam Long limit) {
        return playerService.seekTopPlayers(limit).stream()
                .map(player -> {
                    return modelMapper.map(player, PlayerResponseDTO.class);
                })
                .collect(Collectors.toList());

    }

    @GetMapping("/bylevel")
    @ResponseStatus(HttpStatus.OK)
    public List<PlayerResponseDTO> allPlayersFromSkill() {
        return playerService.allPlayersFromSkill().stream()
                .map(player -> {
                    return modelMapper.map(player, PlayerResponseDTO.class);
                })
                .collect(Collectors.toList());

    }

}
