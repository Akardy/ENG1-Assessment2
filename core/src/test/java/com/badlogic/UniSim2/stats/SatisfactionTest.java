package com.badlogic.UniSim2.stats;

import com.badlogic.UniSim2.resources.Consts;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SatisfactionTest {

    private Satisfaction satisfaction;

    @BeforeEach
    void setUp() {
        satisfaction = new Satisfaction();
    }

    @Test
    void increaseSatisfactionTest() {
        satisfaction.increaseSatis(10);
        assertEquals(Consts.STARTING_SATISFACTION + 10, satisfaction.getSatis(),
            "satisfaction should be " + (Consts.STARTING_SATISFACTION + 10));
        satisfaction.increaseSatis(60);
        assertEquals(100, satisfaction.getSatis(), "Satisfaction Should be 100");
    }

    @Test
    void decreaseSatisfactionTest() {
        satisfaction.decreaseSatis(10);
        assertEquals(Consts.STARTING_SATISFACTION - 10, satisfaction.getSatis(),
            "Satisfaction Should be " + (Consts.STARTING_SATISFACTION - 10));
        satisfaction.decreaseSatis(60);
        assertEquals(0, satisfaction.getSatis(), "Satisfaction Should be 0");
    }

    @Test
    void decayTest() {
        float expectedAmount;
        satisfaction.decay();
        expectedAmount = Consts.STARTING_SATISFACTION + Consts.STARTING_DECAY;
        assertEquals(expectedAmount, satisfaction.getSatis(), "Satisfaction Should be " + expectedAmount);
        satisfaction.incrementDecay();
        satisfaction.decay();
        expectedAmount = expectedAmount + Consts.STARTING_DECAY - Consts.INCREMENT_DECAY_AMOUNT;
        assertEquals(expectedAmount, satisfaction.getSatis(), "Satisfaction Should be " + expectedAmount);
    }

    @AfterEach
    void toStingTest(){
        String expected = String.format("%.2f", satisfaction.getSatis()) + "%";
        assertEquals(expected, satisfaction.toString(), "Satisfaction String Should be " + expected);
    }

}
