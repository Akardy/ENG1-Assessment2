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

    public Labs(Texture placedTexture, Texture collisionTexture, Texture draggingTexture,
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
    }
}
