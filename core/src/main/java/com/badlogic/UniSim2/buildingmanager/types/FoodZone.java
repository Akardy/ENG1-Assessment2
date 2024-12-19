package com.badlogic.UniSim2.buildingmanager.types;

import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.resources.*;
import com.badlogic.gdx.graphics.Texture;

/**
 * A building which represents a place where students can eat food.
 * @see Building
 */
public class FoodZone extends Building{

    private final float incomePerStudent;
    private final int capacity;

    private final float satisfactionPerStudent;

    private final int moneyGenerated;

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
            type
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
}
