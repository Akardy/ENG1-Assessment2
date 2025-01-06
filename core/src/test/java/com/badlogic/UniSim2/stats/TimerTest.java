package com.badlogic.UniSim2.stats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TimerTest {
    Timer timer;

    @BeforeEach
    void setUp() {
        timer = new Timer();
        Gdx.graphics = mock(Graphics.class);
    }

    @Test
    void testTimerUpdate() {
        when(Gdx.graphics.getDeltaTime()).thenReturn(1.0f);
        timer.update();
        assertEquals(299, timer.getTimeLeft());
    }

    @Test
    void testTimerStarts(){
        assertEquals(300, timer.getTimeLeft());
        assertEquals(0, timer.getElapsedTime());
    }

    @Test
    void testTimerStops(){
        when(Gdx.graphics.getDeltaTime()).thenReturn(300f);
        timer.update();
        assertTrue(timer.hasReachedMaxTime());
    }
}
