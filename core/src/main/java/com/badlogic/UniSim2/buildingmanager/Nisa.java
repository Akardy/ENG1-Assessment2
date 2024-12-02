package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.UniSim2.resources.Consts;

public class Nisa extends Building {
    public Nisa() {
        super(
                Assets.nisaPlacedTexture,
                Assets.nisaCollisionTexture,
                Assets.nisaDraggingTexture,
                Consts.FOODZONE_WIDTH,
                Consts.FOODZONE_HEIGHT,
                BuildingTypes.Nisa);
    }
}
