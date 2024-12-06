package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.UniSim2.resources.*;

public class Gym extends Building {
    public Gym() {
        super(
            Assets.gymPlacedTexture,
            Assets.gymCollisionTexture,
            Assets.gymDraggingTexture,
            Consts.RECREATIONAL_WIDTH,
            Consts.RECREATIONAL_HEIGHT,
            BuildingTypes.Gym
        );
    }
}
