package com.badlogic.UniSim2.GUImanager;

import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.Gdx;
import java.util.concurrent.TimeUnit;

/**
 * Class controlling the money variable, stat shown on the top right
 * of the game screen and can be {@link #loan() updated}.
 */
public class Money {
    private float startingMoney;

    public Money() {
        this.startingMoney = 900000;
    }

    /**
     * Update the money value by the amount spent or gained.
     */
    public void loan() {
        startingMoney += 900000;
    }

    public float getMoney(){
        return startingMoney;
    }
}
