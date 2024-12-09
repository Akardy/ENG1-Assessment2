package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.UniSim2.resources.*;
import com.badlogic.gdx.graphics.Texture;

import static com.badlogic.UniSim2.resources.Consts.scaleToCellSize;

/**
 * A building which represents a recreational building where students can have
 * fun.
 * @see Building
 */
public class Recreational extends Building{

    private final float upKeep;

    public Recreational(Texture placedTexture, Texture collisionTexture, Texture draggingTexture,
                        int width, int height, float cost, String name, float upKeep) {
        super(
            placedTexture,
            collisionTexture,
            draggingTexture,
            width * Consts.CELL_SIZE,
            height * Consts.CELL_SIZE,
            BuildingTypes.Recreational,
            cost,
            name
        );
        this.upKeep = upKeep;
    }
}
