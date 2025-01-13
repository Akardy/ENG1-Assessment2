package com.badlogic.UniSim2.Events;

import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.buildingmanager.BuildingManager;
import com.badlogic.UniSim2.buildingmanager.types.Accomodation;
import com.badlogic.UniSim2.buildingmanager.types.BuildingTypes;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.stats.BuildingCounts;
import com.badlogic.UniSim2.stats.Money;
import com.badlogic.UniSim2.stats.Satisfaction;
import com.badlogic.UniSim2.stats.Timer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BrokenBuildingTest {

    Timer timer;
    BuildingManager mockBuildingManager;
    BuildingCounts buildingCounts;
    Announcement announcement;
    Money money;
    Satisfaction satisfaction;
    BrokenBuilding event;
    ShapeRenderer mockShapeRenderer;
    Skin mockSkin;

    @BeforeEach
    void setUp() {
        timer = new Timer();
        mockBuildingManager = mock(BuildingManager.class);
        buildingCounts = new BuildingCounts();
        announcement = mock(Announcement.class);
        money = new Money();
        satisfaction = new Satisfaction();
        mockShapeRenderer = mock(ShapeRenderer.class);
        mockSkin = Mockito.mock(Skin.class);

        Gdx.graphics = mock(Graphics.class);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Mockito.mock(BitmapFont.class);

        when(mockSkin.get(Label.LabelStyle.class)).thenReturn(labelStyle);;


        when(Gdx.graphics.getWidth()).thenReturn(1920);
        when(Gdx.graphics.getHeight()).thenReturn(1080);

        event = new BrokenBuilding(timer, mockBuildingManager, buildingCounts, announcement, money, satisfaction, mockShapeRenderer,
            mockSkin);
    }

    @Test
    void eventTriggersTest(){
        when(Gdx.graphics.getDeltaTime()).thenReturn(event.getGoOfSeconds());

        Array<Building> toReturnArray = new Array<>();
        toReturnArray.add(mock(Accomodation.class));

        when(toReturnArray.get(0).getType()).thenReturn(BuildingTypes.DERWENT);
        when(mockBuildingManager.getPlaced()).thenReturn(toReturnArray);

        timer.update();
        event.checkTriggeringEvent();
        assertTrue(event.getHasGoneOff());


    }

}
