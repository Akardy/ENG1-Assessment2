package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.UniSim2.resources.*;

/**
 * A building which represents a course specific building.
 * 
 * @see Building
 */
public class SoftwareLabs extends Building {

    public SoftwareLabs() {
        super(
                Assets.softwareLabsPlacedTexture,
                Assets.softwareLabsCollisionTexture,
                Assets.softwareLabsDraggingTexture,
                Consts.COURSE_WIDTH,
                Consts.COURSE_HEIGHT,
                BuildingTypes.SoftwareLabs);
    }
}
