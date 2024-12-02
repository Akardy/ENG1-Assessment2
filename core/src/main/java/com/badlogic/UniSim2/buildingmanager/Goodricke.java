package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.UniSim2.resources.Consts;

public class Goodricke extends Building {
    public Goodricke() {
        super(
            Assets.goodrickePlacedTexture,
            Assets.goodrickeCollisionTexture,
            Assets.goodrickeDraggingTexture,
            Consts.ACCOMODATION_WIDTH,
            Consts.ACCOMODATION_HEIGHT,
            BuildingTypes.Goodricke
        );
    }
}
