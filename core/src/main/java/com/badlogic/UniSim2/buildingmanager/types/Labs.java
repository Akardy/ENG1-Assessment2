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

    /**
     * Constructs a new Labs building.
     *
     * @param placedTexture           texture when the lab is placed on the map
     * @param collisionTexture        texture used when the lab collides with another object
     * @param draggingTexture         texture used when dragging the lab for placement
     * @param width                   the width of the lab building
     * @param height                  the height of the lab building
     * @param cost                    the cost to place the lab
     * @param name                    the name of the lab
     * @param type                    the building type enum for labs
     * @param capacity                the capacity of the lab (number of students it can accommodate)
     * @param satisfactionPerStudent  the satisfaction earned per student attending the lab per 30 seconds
     * @param examSpace               the exam space provided by the lab
     */

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
    /**
     * Returns a string containing detailed statistics about the lab building
     * @return a formatted string with the lab's statistics.
     */
    public String getStats(){
        return "Building: " + getType() + "\nSatisfaction earned: " + getSatisfactionGenerated() + "%" +
            "\nCapacity: " + capacity + "\nHow many students use: " + getHowFull() +
             "\nSatisfaction per student per 30s: " + satisfactionPerStudent +
            "\nExam Space: " + getExamSpace();
    }
}
