package com.rainer.highlow.service;


import org.springframework.stereotype.Service;

@Service
public class CardGameService {
    public int generateCard() {
        int rng;
        do {
            rng = (int)Math.round(Math.random() * 100) % 10;

        } while (rng == 1);

        return rng;

    }

}
