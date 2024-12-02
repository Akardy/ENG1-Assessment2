package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.UniSim2.resources.*;

/**
 * A building which represents a course specific building.
 * 
 * @see Building
 */
public class HardwareLabs extends Building {

    public HardwareLabs() {
        super(
                Assets.hardwareLabsPlacedTexture,
                Assets.hardwareLabsCollisionTexture,
                Assets.hardwareLabsDraggingTexture,
                Consts.COURSE_WIDTH,
                Consts.COURSE_HEIGHT,
                BuildingTypes.HardwareLabs);
    }
}
