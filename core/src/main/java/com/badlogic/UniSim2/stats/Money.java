package com.badlogic.UniSim2.stats;

import com.badlogic.UniSim2.resources.Consts;

/**
 * Class controlling the money variable, stat shown on the top right
 * of the game screen and can be {@link #increaseMoney(float)  updated}.
 */
public class Money {
    private float money;

    public Money() {
        this.money = Consts.STARTING_MONEY;
    }

    /**
     * Update the money value by the amount spent or gained.
     */

    public float getMoney(){
        return money;
    }

    public boolean reduceMoney(float amount){
        if (money - amount < 0) {
            return false;
        }else {
            money -= amount;
            return true;
        }
    }

    public void increaseMoney(float amount){
        money += amount;}
}
