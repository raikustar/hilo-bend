package com.rainer.highlow.cardClass;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter

public class CardGameLogic {
    private int lives = 3;
    private int points = 0;
    private boolean lost = false;

}
