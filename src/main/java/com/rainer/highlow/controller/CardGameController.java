package com.rainer.highlow.controller;

import com.rainer.highlow.cardClass.CardGameLogic;
import com.rainer.highlow.entity.Player;
import com.rainer.highlow.entity.Score;
import com.rainer.highlow.repository.PlayerRepository;
import com.rainer.highlow.repository.PlayerXRepository;
import com.rainer.highlow.repository.ScoreRepository;
import com.rainer.highlow.service.CardGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private ScoreRepository scoreRepository;

    CardGameLogic cardGameLogic = new CardGameLogic();
    long startTime, timerBegins, timerBegins_out;


    @GetMapping("generate-card")
    public int generateBaseCard() {
        return cardGameService.generateCard();
    }

    @GetMapping("game-state")
    public CardGameLogic gameState() {
        return cardGameLogic;
    }

    @GetMapping("start-round-request")
    public CardGameLogic getGameStateStart(@RequestParam String playerName) {
        if (playerName.length() <= 3) {
            throw new IllegalArgumentException("Player name must be at least 3 characters") ;
        }

        cardGameLogic = new CardGameLogic();
        Player player = new Player();

        if (playerRepository.findByName(playerName) != null) {
            player = playerRepository.findByName(playerName);
        } else {
            player.setName(playerName);
            player = playerRepository.save(player);
        }

        cardGameLogic.setPlayer(player);
        startTime = System.currentTimeMillis();
        timerBegins = System.currentTimeMillis();
        timerBegins_out = System.currentTimeMillis();
        return cardGameLogic;
    }



    @GetMapping("lower")
    public boolean lower(@RequestParam int currentNumber, @RequestParam int newNumber) {
        long currentTime = System.currentTimeMillis();
        cardGameService.gameCheckExceptions(cardGameLogic, currentTime, timerBegins, startTime);
        timerBegins = currentTime;
        return cardGameService.comparison(currentNumber > newNumber, cardGameLogic, startTime);
    }



    @GetMapping("higher")
    public boolean higher(@RequestParam int currentNumber, @RequestParam int newNumber) {
        long currentTime = System.currentTimeMillis();
        cardGameService.gameCheckExceptions(cardGameLogic, currentTime, timerBegins, startTime);
        timerBegins = currentTime;
        return cardGameService.comparison(currentNumber < newNumber, cardGameLogic, startTime);
    }


    @GetMapping("equal")
    public boolean equals(@RequestParam int currentNumber, @RequestParam int newNumber) {
        long currentTime = System.currentTimeMillis();
        cardGameService.gameCheckExceptions(cardGameLogic, currentTime, timerBegins, startTime);
        timerBegins = currentTime;
        return cardGameService.comparison(currentNumber == newNumber, cardGameLogic, startTime);
    }



    @GetMapping("list-players")
    public List<Player> listPlayers() {
        return playerRepository.findAll();
    }

    @GetMapping("list-score-points")
    public List<Score> listScore() {
        return scoreRepository.findByOrderByScoreDesc();
    }

    @GetMapping("list-score-time-played")
    public List<Score> listDuration() {
        return scoreRepository.findByOrderByTotalTimeDesc();
    }

    @GetMapping("show-player/{playerName}")
    public List<Score> showPlayer(@PathVariable String playerName) {
        if (scoreRepository.findByPlayer_Name(playerName) != null) {
            System.out.println("Player " + playerName + " has been found.");
        } else {
            throw new RuntimeException("Player not found");
        }

        return scoreRepository.findByPlayer_Name(playerName);
    }





//    @PostMapping("add-player")
//    public List<Player> addPlayer(@RequestBody Player player) {
//        if (player.getName() != null && player.getName().length() <= 2) {
//            throw new RuntimeException("Name is too short, must be at least 3 characters");
//        }
//
//        playerRepository.save(player);
//        return playerRepository.findAll();
//    }
}
