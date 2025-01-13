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

    /**
     * Constructs a new LectureHall building
     *
     * @param placedTexture          texture when the lecture hall is placed on the map
     * @param collisionTexture       texture used when the lecture hall collides with another object
     * @param draggingTexture        texture used when dragging the lecture hall for placement
     * @param width                  the width of the lecture hall
     * @param height                 the height of the lecture hall
     * @param cost                   the cost to place the lecture hall
     * @param name                   the name of the lecture hall
     * @param type                   the building type enum for lecture halls
     * @param capacity               the seating capacity of the lecture hall
     * @param satisfactionPerStudent satisfaction earned per student attending lectures
     * @param examSpace              the exam space provided by the lecture hall
     */
    public LectureHall(Texture placedTexture, Texture collisionTexture, Texture draggingTexture,
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

    /**
     * Returns a string containing detailed statistics about the lecture hall
     *
     * @return a formatted string with the lecture hall's statistics.
     */
    public String getStats(){
        return "Building: " + getType() + "\nSatisfaction earned: " + getSatisfactionGenerated() + "%" +
            "\nCapacity: " + capacity + "\nHow many students use: " + getHowFull() +
            "\nSatisfaction per student per 10s: " + satisfactionPerStudent +
            "\nExam Space: " + getExamSpace();
    }

}
