package com.badlogic.UniSim2.buildingmanager.types;

import com.badlogic.UniSim2.buildingmanager.Building;
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
                    int width, int height, float cost, String name, float moneyGenerated, int capacity, BuildingTypes type) {
        super(
            placedTexture,
            collisionTexture,
            draggingTexture,
            width * Consts.CELL_SIZE,
            height * Consts.CELL_SIZE,
            cost,
            name,
            type
        );
        this.moneyGenerated = moneyGenerated;
        this.capacity = capacity;
    }
}
