package com.badlogic.UniSim2.GUImanager;

import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.Gdx;

/**
 * Class controlling the satisfaction variable, stat shown on the top right
 * of the game screen and can be {@link #update() updated}.
 */
public class Satisfaction {
    private float startingSatis;

    public Satisfaction() {
        this.startingSatis = 40;
    }

    /**
     * Update the satisfaction value.
     */
    public void update() {
        startingSatis += 0;
    }

    public float getSatis(){
        return startingSatis;
    }
}
