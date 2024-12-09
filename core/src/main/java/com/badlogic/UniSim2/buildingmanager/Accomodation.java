package com.badlogic.UniSim2.buildingmanager;

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
                        int width, int height, float cost, String name, int rooms, int studentsCont) {
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
        this.rooms = rooms;
        this.studentsCont = studentsCont;
    }
}
