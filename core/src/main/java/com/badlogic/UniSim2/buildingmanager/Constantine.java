package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.UniSim2.resources.Consts;

public class Constantine extends Building {
    public Constantine() {
        super(
            Assets.constantinePlacedTexture,
            Assets.constantineCollisionTexture,
            Assets.constantineDraggingTexture,
            Consts.ACCOMODATION_WIDTH,
            Consts.ACCOMODATION_HEIGHT,
            BuildingTypes.Constantine
        );
    }
}
