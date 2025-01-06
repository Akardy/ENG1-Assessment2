package com.badlogic.UniSim2.stats;

/**
 * Class controlling the satisfaction variable, stat shown on the top right
 * of the game screen and can be {@link #update() updated}.
 */
public class Satisfaction {
    private float startingSatis;

    private float decay;

    public Satisfaction() {

        this.startingSatis = 40;
        this.decay = -0.2f;
    }

    /**
     * Update the satisfaction value.
     */
    public void update() {
        startingSatis += 0;
    }

    public float getSatis(){
        String startingSatis2dp = String.format("%.2f", startingSatis);
        return Float.parseFloat(startingSatis2dp);
    }
    public void incrementDecay(){
        decay -= 0.1f;

    }
    public void decay(){startingSatis = Math.max(startingSatis + decay, 0);}

    public void increaseSatis(float amount){startingSatis = Math.min(startingSatis + amount, 100);}

    public void decreaseSatis(float amount){startingSatis = Math.max(startingSatis - amount, 0);}
}
