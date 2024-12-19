package com.rainer.highlow.controller;

import com.rainer.highlow.cardClass.CardGameLogic;
import com.rainer.highlow.entity.Player;
import com.rainer.highlow.entity.PlayerComplex;
import com.rainer.highlow.entity.Score;
import com.rainer.highlow.repository.PlayerRepository;
import com.rainer.highlow.repository.PlayerXRepository;
import com.rainer.highlow.service.CardGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;


@CrossOrigin("http://localhost:3000")
@RestController
public class CardGameController {

    @Autowired
    CardGameService cardGameService;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerXRepository playerXRepository;

    @GetMapping("generateCard")
    public int generateBaseCard() {
        return cardGameService.generateCard();
    }

    @GetMapping("StartRoundRequest")
    public CardGameLogic getGameStateStart() {
        // start timer here
        CardGameLogic cardGameLogic = new CardGameLogic();
        return cardGameLogic;
    }

    @GetMapping("gameOverCheck")
    public boolean gameOverCheck(@RequestParam boolean isLost) {
        return isLost;
    }


    @PutMapping("gameState")
    public CardGameLogic updateGameState(@RequestBody CardGameLogic cardGameLogic) {
        if(cardGameLogic.getLives() <= 0) {
            cardGameLogic.setLives(0);
            cardGameLogic.setLost(true);
        }
        return cardGameLogic;
    }

    // Backend timer

    @GetMapping("lower")
    public boolean lower(@RequestParam int currentNumber, @RequestParam int newNumber) {
        if(currentNumber > newNumber) {
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("higher")
    public boolean higher(@RequestParam int currentNumber, @RequestParam int newNumber) {
        if(currentNumber < newNumber) {
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("equal")
    public boolean equals(@RequestParam int currentNumber, @RequestParam int newNumber) {
        if(currentNumber == newNumber) {
            return true;
        } else {
            return false;
        }
    }


    @GetMapping("list-players")
    public List<Player> listPlayers() {
        return playerRepository.findAllByOrderByScore_ScoreAscNameAsc();
    }

    @GetMapping("list-players-x")
    public List<PlayerComplex> listPlayersX() {
        return playerXRepository.findAllByOrderByScore_ScoreAscNameAsc();
    }

    // New Name
    @PostMapping("add-player")
    public List<Player> addPlayer(@RequestBody Player player) {
        if (player.getName() != null && player.getName().length() <= 2) {
            throw new RuntimeException("Name is too short, must be at least 3 characters");
        }

        Score score = player.getScore();
        score.setCurrentTimeOfDay(LocalTime.now());

        player.setScore(score);
        playerRepository.save(player);

        return playerRepository.findAll();
    }

    @PostMapping("add-player-x")
    public List<PlayerComplex> addPlayerX(@RequestBody PlayerComplex playerX) {
        if (playerX.getName() != null && playerX.getName().length() <= 2) {
            throw new RuntimeException("Name is too short, must be at least 3 characters");
        }

        for (Score score : playerX.getScore()) {
            score.setCurrentTimeOfDay(LocalTime.now());
        }

        playerXRepository.save(playerX);
        return playerXRepository.findAll();
    }

    @PutMapping("update-player-x")
    public List<PlayerComplex> updatePlayerX(@RequestBody PlayerComplex playerX) {
        if (playerXRepository.findById(playerX.getId()).isPresent()) {

            // get list
            // get all previous data
            // add new score
            // add new time
            List<Score> oldScores = playerXRepository.findById(playerX.getId()).get().getScore();
            Score newScore = new Score();
            // add new current time
            newScore.setCurrentTimeOfDay(LocalTime.now());
            newScore.setScore(100);
            oldScores.add(newScore);

            playerX.setScore(oldScores);

            playerXRepository.save(playerX);
        }
        return playerXRepository.findAll();
    }

}
