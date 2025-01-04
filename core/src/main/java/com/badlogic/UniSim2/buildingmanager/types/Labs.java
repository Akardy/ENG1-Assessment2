package com.badlogic.UniSim2.buildingmanager.types;

import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.resources.*;
import com.badlogic.gdx.graphics.Texture;

/**
 * A building which represents a course specific building.
 *
 * @see Building
 */
public class Labs extends Building {

    private final int capacity;
    private final float satisfactionPerStudent;

    public Labs(Texture placedTexture, Texture collisionTexture, Texture draggingTexture,
                int width, int height, float cost, String name, BuildingTypes type, int capacity,
                float satisfactionPerStudent, int examSpace) {
        super(
            placedTexture,
            collisionTexture,
            draggingTexture,
            width,
            height,
            cost,
            name,
            type,
            examSpace
        );
        this.capacity = capacity;
        this.satisfactionPerStudent = satisfactionPerStudent;
    }
    public int getCapacity(){
        return capacity;
    }
    public float getSatisfaction(){
        return satisfactionPerStudent;
    }
    public String getStats(){
        return "Building: " + getType() + "\nSatisfaction earned: " + getSatisfactionGenerated() + "%" +
            "\nCapacity: " + capacity + "\nHow many students use: " + getHowFull() +
             "\nSatisfaction per student per 30s: " + satisfactionPerStudent +
            "\nExam Space: " + getExamSpace();
    }
}
