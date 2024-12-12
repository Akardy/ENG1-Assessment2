package com.badlogic.UniSim2.buildingmanager.types;

import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.resources.*;
import com.badlogic.gdx.graphics.Texture;

/**
 * A building which represents student accommodation.
 * @see Building
 */
public class Accomodation extends Building{

    private final int rooms;
    private int studentsCont;

    public Accomodation(Texture placedTexture, Texture collisionTexture, Texture draggingTexture,
                        int width, int height, float cost, String name, int rooms, BuildingTypes type) {
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
        this.rooms = rooms;
        this.studentsCont = rooms;
    }
}
