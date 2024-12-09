package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.UniSim2.resources.*;
import com.badlogic.gdx.graphics.Texture;

/**
 * A building which represents a place where students can eat food.
 * @see Building
 */
public class FoodZone extends Building{

    private final float moneyGenerated;
    private final int capacity;

    public FoodZone(Texture placedTexture, Texture collisionTexture, Texture draggingTexture,
                    int width, int height, float cost, String name, float moneyGenerated, int capacity) {
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
        this.moneyGenerated = moneyGenerated;
        this.capacity = capacity;
    }
}
