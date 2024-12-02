package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.UniSim2.resources.*;

public class SocietyBuilding extends Building {
    public SocietyBuilding() {
        super(
            Assets.societyBuildingPlacedTexture,
            Assets.societyBuildingCollisionTexture,
            Assets.societyBuildingDraggingTexture,
            Consts.RECREATIONAL_WIDTH,
            Consts.RECREATIONAL_HEIGHT,
            BuildingTypes.SocietyBuilding
        );
    }
}
