package com.badlogic.UniSim2.buildingmanager.types;

import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.resources.*;
import com.badlogic.gdx.graphics.Texture;

/**
 * A building which represents a library.
 * @see Building
 */
public class Library extends Building{
    // a multiplier effect which effects how much stats are given from lecture halls
    private final float multiplierEffect;
    /**
     * Constructs a new Library building.
     *
     * @param placedTexture    the texture when the library is placed on the map
     * @param collisionTexture the texture used when the library collides with another object
     * @param draggingTexture  the texture used when dragging the library for placement
     * @param width            the width of the library building
     * @param height           the height of the library building
     * @param cost             the cost to place the library
     * @param name             the name of the library
     * @param type             the building type enum for libraries
     * @param examSpace        the exam space provided by the library
     */
    public Library(Texture placedTexture, Texture collisionTexture, Texture draggingTexture,
                   int width, int height, float cost, String name, BuildingTypes type, int examSpace) {
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
        this.multiplierEffect = 1.2f;
    }
    /**
     * Returns a string with detailed statistics about the library
     *
     * @return a formatted string containing the library's statistics.
     */
    public String getStats(){
        return "Building: " + getType() + "\nSatisfaction earned: " + getSatisfactionGenerated() + "%" +
            "\nMultiplier effect: " + multiplierEffect +
            "\nExam Space: " + getExamSpace();
    }
}
