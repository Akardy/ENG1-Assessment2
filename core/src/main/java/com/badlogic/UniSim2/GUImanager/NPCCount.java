package com.badlogic.UniSim2.GUImanager;

import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.Gdx;

/**
 * Class controlling the number of students variable, stat shown on the
 * bottom left of the game screen and can be {@link #update() updated}.
 */
public class NPCCount {
    private int startingNum;

    public NPCCount() {
        this.startingNum = 5;
    }

    /**
     * Update the number of NPCs.
     */
    public void update() {
        startingNum += 0;
    }

    public int getNum(){
        return startingNum;
    }
}
