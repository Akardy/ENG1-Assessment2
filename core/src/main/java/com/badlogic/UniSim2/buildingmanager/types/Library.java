package com.badlogic.UniSim2.buildingmanager.types;

import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.resources.*;
import com.badlogic.gdx.graphics.Texture;

/**
 * A building which represents a library.
 * @see Building
 */
public class Library extends Building{

    private final float multiplierEffect;

    public Library(Texture placedTexture, Texture collisionTexture, Texture draggingTexture,
                   int width, int height, float cost, String name, BuildingTypes type) {
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
        this.multiplierEffect = 1.2f;
    }
    public float getMultiplierEffect(){return multiplierEffect;}
}
