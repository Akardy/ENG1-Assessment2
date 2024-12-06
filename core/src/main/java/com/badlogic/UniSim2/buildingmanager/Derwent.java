package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.UniSim2.resources.*;

public class Derwent extends Building {
    public Derwent() {
        super(
            Assets.derwentPlacedTexture,
            Assets.derwentCollisionTexture,
            Assets.derwentDraggingTexture,
            Consts.ACCOMODATION_WIDTH,
            Consts.ACCOMODATION_HEIGHT,
            BuildingTypes.Derwent
        );
    }
}
