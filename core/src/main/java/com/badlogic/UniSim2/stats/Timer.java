package com.badlogic.UniSim2.stats;

import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.Gdx;

/**
 * A timer which can count up from 0 seconds to 5 minutes. Will hold the
 * current elapsed time and can be {@link #update() updated} until the
 * max time is reached.
 */
public class Timer {
    private float timeLeft;
    private final float endTime;
    private boolean reachedMaxTime;
    private float startTime;

    public Timer() {
        this.endTime = Consts.MAX_TIME;
        this.startTime = Consts.STARTING_SECONDS;
        this.timeLeft = this.startTime;
        reachedMaxTime = false;
    }

    /**
     * Update the timer by the amount of time since the last frame and checks
     * whether the time has reached its maximum time limit.
     */
    public void update() {
        // Checks if the time has reached the limit
        if (timeLeft > endTime) {
            // If not it updates the time
            timeLeft -= Gdx.graphics.getDeltaTime();
        }

        if (timeLeft <= endTime) {
            reachedMaxTime = true;
        }
    }

    public float getTimeLeft(){
        return timeLeft;
    }

    public float getElapsedTime(){
        return startTime - timeLeft;
    }

    public boolean hasReachedMaxTime(){
        return reachedMaxTime;
    }
}

