package com.badlogic.UniSim2.buildingmanager.types;

import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.resources.*;
import com.badlogic.gdx.graphics.Texture;

/**
 * A building which represents a recreational building where students can have
 * fun.
 * @see Building
 */
public class Recreational extends Building{



    private final int capacity;
    private final float satisfactionPerStudent;
    private final float originSatisfactionPerStudent;



    public Recreational(Texture placedTexture, Texture collisionTexture, Texture draggingTexture,
                        int width, int height, float cost, String name, BuildingTypes type, int capacity,
                        float satisfactionPerStudent) {
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
        this.capacity = capacity;
        this.originSatisfactionPerStudent = satisfactionPerStudent;
        this.satisfactionPerStudent = calculateDiscountRate();

    }
    public float calculateDiscountRate(){
        return originSatisfactionPerStudent;
    }
    public int getCapacity(){
        return capacity;
    }
    public float getSatisfaction(){
        return satisfactionPerStudent;
    }

    public String getStats(){ // TODO: ADD HOW CLOSE IS NEAREST BUILDING
        return "Building: " + getType() + "\nSatisfaction earned: " + getSatisfactionGenerated() + "%" +
            "\nCapacity: " + capacity + "\nHow many students use: " + getHowFull() +
            "\nSatisfaction per student per 10s: " + satisfactionPerStudent;
    }
}
