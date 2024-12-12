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

    private final float upKeep;

    public Recreational(Texture placedTexture, Texture collisionTexture, Texture draggingTexture,
                        int width, int height, float cost, String name, float upKeep, BuildingTypes type) {
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
        this.upKeep = upKeep;
    }
}
