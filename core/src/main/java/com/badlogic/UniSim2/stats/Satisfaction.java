package com.badlogic.UniSim2.stats;

import com.badlogic.UniSim2.resources.Consts;

/**
 * Class controlling the satisfaction variable, stat shown on the top right
 * of the game screen and can be {@link #update() updated}.
 */
public class Satisfaction {
    private float startingSatis;

    private float decay;

    public Satisfaction() {

        this.startingSatis = Consts.STARTING_SATISFACTION;
        this.decay = Consts.STARTING_DECAY;
    }

    public float getSatis(){
        String startingSatis2dp = String.format("%.2f", startingSatis);
        return Float.parseFloat(startingSatis2dp);
    }
    public void incrementDecay(){
        decay -= Consts.INCREMENT_DECAY_AMOUNT;
    }
    public void decay(){startingSatis = Math.max(startingSatis + decay, 0);}

    public void increaseSatis(float amount){startingSatis = Math.min(startingSatis + amount, 100);}

    public void decreaseSatis(float amount){startingSatis = Math.max(startingSatis - amount, 0);}

    @Override
    public String toString() {
        String output = String.format("%.2f", startingSatis);
        return output + "%";
    }
}
