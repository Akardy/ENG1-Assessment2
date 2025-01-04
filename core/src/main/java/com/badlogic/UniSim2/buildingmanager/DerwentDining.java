package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.UniSim2.buildingmanager.types.BuildingTypes;
import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.UniSim2.resources.Consts;

public class DerwentDining extends Building {
    public DerwentDining() {
        super(
                Assets.derwentDiningPlacedTexture,
                Assets.derwentDiningCollisionTexture,
                Assets.derwentDiningDraggingTexture,
                Consts.FOODZONE_WIDTH,
                Consts.FOODZONE_HEIGHT,
                0,
                "name",
                BuildingTypes.DERWENTDINING);
    }
}
