package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.UniSim2.resources.Consts;

public class Greggs extends Building {
    public Greggs() {
        super(
                Assets.greggsPlacedTexture,
                Assets.greggsCollisionTexture,
                Assets.greggsDraggingTexture,
                Consts.FOODZONE_WIDTH,
                Consts.FOODZONE_HEIGHT,
                BuildingTypes.Greggs);
    }
}
