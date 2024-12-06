package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.UniSim2.resources.*;

/**
 * A building which represents a place where lectures can take place.
 * 
 * @see Building
 */
public class Piazza extends Building {

    public Piazza() {
        super(
                Assets.piazzaPlacedTexture,
                Assets.piazzaCollisionTexture,
                Assets.piazzaDraggingTexture,
                Consts.LECTUREHALL_WIDTH,
                Consts.LECTUREHALL_HEIGHT,
                BuildingTypes.Piazza);
    }

}
