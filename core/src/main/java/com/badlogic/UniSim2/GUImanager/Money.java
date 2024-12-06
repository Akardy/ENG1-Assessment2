package com.badlogic.UniSim2.GUImanager;

import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.Gdx;

/**
 * Class controlling the money variable, stat shown on the top right
 * of the game screen and can be {@link #update() updated}.
 */
public class Money {
    private float startingMoney;

    public Money() {
        this.startingMoney = 25000;
    }

    /**
     * Update the money value by the amount spent or gained.
     */
    public void update() {
        startingMoney += 0;
    }

    public float getMoney(){
        return startingMoney;
    }
}
