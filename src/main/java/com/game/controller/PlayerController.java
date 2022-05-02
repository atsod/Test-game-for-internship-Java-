package com.game.controller;

import com.game.entity.*;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/rest")
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(value = "/players")
    public ResponseEntity<List<PlayerDTO>> getAllPlayers(PlayerPage playerPage,
                                                         PlayerSearchCriteria playerSearchCriteria) {
        return new ResponseEntity<>(playerService.getPlayers(playerPage, playerSearchCriteria), HttpStatus.OK);
    }

    @GetMapping(value = "/players/count")
    public ResponseEntity<?> getCount(PlayerSearchCriteria playerSearchCriteria) {
        Integer intResponse = playerService.getCount(playerSearchCriteria);
        return new ResponseEntity<>(intResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/players")
    public ResponseEntity<?> createPlayer(@RequestBody PlayerDTO player) {
        if(player.getName() == null
        || player.getTitle() == null || player.getTitle().length() > 30
        || player.getRace() == null
        || player.getProfession() == null
        || player.getBirthday() == null
        || player.getExperience() == null
        || player.getExperience() < 0 || player.getExperience() > 10000000)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(player.getBanned() == null) player.setBanned(false);

        int level = (int) ((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100);
        player.setLevel(level);

        Integer untilNextLevel = 50 * (level + 1) * (level + 2) - player.getExperience();
        player.setUntilNextLevel(untilNextLevel);

        PlayerDTO playerResponse = playerService.createPlayer(player);
        return new ResponseEntity<>(playerResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/players/{id}")
    public ResponseEntity<?> getPlayer(@PathVariable(name = "id") Long id) {
        if(id <= 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        PlayerDTO playerResponse = playerService.getPlayer(id);
        return playerResponse != null
                ? new ResponseEntity<>(playerResponse, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/players/{id}")
    public ResponseEntity<?> updatePlayer(@PathVariable(value = "id") Long id,
                                          @RequestBody PlayerDTO player) {

        if(player.getTitle() != null && player.getTitle().length() > 30
                || player.getExperience() != null && (player.getExperience() < 0 || player.getExperience() > 10000000)
                || id == null || id <= 0
                || player.getBirthday() != null && player.getBirthday().getTime() < 0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        PlayerDTO playerResult = playerService.getPlayer(id);
        if(playerResult == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if(player.getName() != null) playerResult.setName(player.getName());
        if(player.getTitle() != null) playerResult.setTitle(player.getTitle());
        if(player.getRace() != null) playerResult.setRace(player.getRace());
        if(player.getProfession() != null) playerResult.setProfession(player.getProfession());
        if(player.getBirthday() != null) playerResult.setBirthday(player.getBirthday());
        if(player.getBanned() != null) playerResult.setBanned(player.getBanned());

        int level = -1;
        if(player.getExperience() != null) {
            playerResult.setExperience(player.getExperience());
            level = (int) ((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100);
            playerResult.setLevel(level);
        }

        int untilNextLevel = -1;
        if(level != -1) untilNextLevel = 50 * (level + 1) * (level + 2) - player.getExperience();
        if(untilNextLevel != -1) playerResult.setUntilNextLevel(untilNextLevel);

        PlayerDTO playerResponse = playerService.updatePlayer(playerResult);
        return new ResponseEntity<>(playerResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/players/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable (value = "id") Long id) {
        if(id <= 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(playerService.getPlayer(id) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        playerService.deletePlayer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
