package com.badlogic.UniSim2.buildingmanager.types;

import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.resources.*;
import com.badlogic.gdx.graphics.Texture;

/**
 * A building which represents a place where lectures can take place.
 * @see Building
 */
public class LectureHall extends Building{

    public LectureHall(Texture placedTexture, Texture collisionTexture, Texture draggingTexture,
                       int width, int height, float cost, String name, BuildingTypes type) {
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
    }

}
