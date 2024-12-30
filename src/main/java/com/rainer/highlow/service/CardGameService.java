package com.rainer.highlow.service;


import com.rainer.highlow.cardClass.CardGameLogic;
import com.rainer.highlow.entity.Score;
import com.rainer.highlow.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.smartcardio.Card;

@Service
public class CardGameService {

    @Autowired
    private ScoreRepository scoreRepository;

    public int generateCard() {
        int rng;
        do {
            rng = (int)Math.round(Math.random() * 100) % 10;

        } while (rng <= 1);

        return rng;

    }

    public boolean comparison(boolean comparable, CardGameLogic cardGameLogic, long startTime) {
        if(comparable) {
            int score = cardGameLogic.getPoints();
            cardGameLogic.setPoints(score + 1);
            return true;
        } else {
            int lives = cardGameLogic.getLives();
            cardGameLogic.setLives(lives - 1);

            if (cardGameLogic.getLives() <= 0) {
                savePlayerWithScore(cardGameLogic, startTime);
            }


            return false;
        }
    }

    private void savePlayerWithScore(CardGameLogic cardGameLogic, long startTime) {
        cardGameLogic.setLives(0);
        cardGameLogic.setLost(true);

        Score newScore = new Score();
        newScore.setScore(cardGameLogic.getPoints());

        long endTime = System.currentTimeMillis();
        long totalTime = ((endTime - startTime) / 1000);
        newScore.setTotalTime(totalTime);

        newScore.setPlayer(cardGameLogic.getPlayer());
        scoreRepository.save(newScore);
    }

    public void gameCheckExceptions(CardGameLogic cardGameLogic,long currentTime, long timerBegins, long startTime) {
        if(cardGameLogic.getLives() <= 0) {
            throw new RuntimeException("Game over");
        }
        if (currentTime > timerBegins + 10 * 1000) {
            savePlayerWithScore(cardGameLogic, startTime);
            throw new RuntimeException("Time is over");

        }
    }
}

