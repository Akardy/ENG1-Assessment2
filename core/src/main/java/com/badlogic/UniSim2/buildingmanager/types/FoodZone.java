package com.badlogic.UniSim2.buildingmanager.types;

import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.resources.*;
import com.badlogic.gdx.graphics.Texture;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A building which represents a place where students can eat food.
 * @see Building
 */
public class FoodZone extends Building{

    private final float incomePerStudent;
    private final int capacity;

    private final float satisfactionPerStudent;

    private final int moneyGenerated;

    /**
     * Constructs a new FoodZone building.
     *
     * @param placedTexture        texture when the food zone is placed on the map
     * @param collisionTexture     texture used when the food zone collides with another object
     * @param draggingTexture      texture used when dragging the food zone for placement
     * @param width                the width of the food zone
     * @param height               the height of the food zone
     * @param cost                 the cost to place the food zone
     * @param name                 the name of the food zone
     * @param type                 the building type enum for food zones
     * @param capacity             the maximum number of students the food zone can accommodate
     * @param incomePerStudent     the income generated per student visiting the food zone per 10 seconds
     * @param satisfactionPerStudent the satisfaction earned per student visiting the food zone per 10 seconds
     */

    public FoodZone(Texture placedTexture, Texture collisionTexture, Texture draggingTexture,
                    int width, int height, float cost, String name, BuildingTypes type, int capacity,
                    float incomePerStudent, float satisfactionPerStudent) {
        super(
            placedTexture,
            collisionTexture,
            draggingTexture,
            width,
            height,
            cost,
            name,
            type,
            0
        );
        this.moneyGenerated = 0;
        this.capacity = capacity;
        this.incomePerStudent = incomePerStudent;
        this.satisfactionPerStudent = satisfactionPerStudent;

    }
    public int getCapacity(){
        return capacity;
    }
    public float getIncome(){
        return incomePerStudent;
    }
    public float getSatisfaction(){
        return satisfactionPerStudent;
    }
    /**
     * Returns a formatted string containing detailed statistics about the food zone
     *
     * @return a formatted string with the food zone's statistics.
     */
    public String getStats(){
        String formattedValue = String.format("%.4f", satisfactionPerStudent); // needed otherwise gets 5.0E-X format
        return "Building: " + getType() + "\nMoney earned: " + getMoneyGenerated() + "\nSatisfaction earned: " + getSatisfactionGenerated() + "%" +
            "\nCapacity: " + capacity + "\nHow many students use: " + getHowFull() +
            "\nIncome per student per 10s: " + incomePerStudent + "\nSatisfaction per student per 10s: " + formattedValue;
    }
}
