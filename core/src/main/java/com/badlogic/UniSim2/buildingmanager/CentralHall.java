package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.UniSim2.resources.*;

/**
 * A building which represents a place where lectures can take place.
 * 
 * @see Building
 */
public class CentralHall extends Building {

    public CentralHall() {
        super(
                Assets.centralHallPlacedTexture,
                Assets.centralHallCollisionTexture,
                Assets.centralHallDraggingTexture,
                Consts.LECTUREHALL_WIDTH,
                Consts.LECTUREHALL_HEIGHT,
                BuildingTypes.CentralHall);
    }

}
