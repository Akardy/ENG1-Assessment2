package com.badlogic.UniSim2.stats;

import com.badlogic.UniSim2.resources.Consts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    private float expected;
    private Money money;

    @BeforeEach
    void setUp() {
        money = new Money();
    }

    @Test
    void increaseMoneyTest(){
        expected = Consts.STARTING_MONEY + 5000;
        money.increaseMoney(5000);
        assertEquals(expected, money.getMoney(), "Incorrect money should be " + expected);
    }

    @Test
    void decreaseMoneyTest(){
        expected = Consts.STARTING_MONEY - 5000;
        assertTrue(money.reduceMoney(5000));
        assertEquals(expected, money.getMoney(), "Incorrect money should be " + expected);
        assertFalse(money.reduceMoney(10000000));
        assertEquals(expected, money.getMoney(), "Incorrect money should be " + expected);
        assertTrue(money.reduceMoney(expected));
        expected = 0;
        assertEquals(expected, money.getMoney(), "Incorrect money should be " + expected);
    }

}
