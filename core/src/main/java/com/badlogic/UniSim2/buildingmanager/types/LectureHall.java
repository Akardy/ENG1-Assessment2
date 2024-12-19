package com.badlogic.UniSim2.buildingmanager.types;

import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.resources.*;
import com.badlogic.gdx.graphics.Texture;

/**
 * A building which represents a place where lectures can take place.
 * @see Building
 */
public class LectureHall extends Building{

    private final int capacity;
    private final float satisfactionPerStudent;


    public LectureHall(Texture placedTexture, Texture collisionTexture, Texture draggingTexture,
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
        this.satisfactionPerStudent = satisfactionPerStudent;
    }
    public int getCapacity(){
        return capacity;
    }
    public float getSatisfaction(){
        return satisfactionPerStudent;
    }

}
