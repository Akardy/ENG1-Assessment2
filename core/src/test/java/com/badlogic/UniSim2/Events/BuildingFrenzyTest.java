package com.badlogic.UniSim2.Events;

import com.badlogic.UniSim2.Main;
import com.badlogic.UniSim2.buildingmanager.BuildingManager;
import com.badlogic.UniSim2.stats.BuildingCounts;
import com.badlogic.UniSim2.stats.Satisfaction;
import com.badlogic.UniSim2.stats.Timer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BuildingFrenzyTest {

    private Timer mockTimer;
    private Satisfaction mockSatisfaction;
    private Announcement mockAnnouncement;
    private BuildingManager mockBuildingManager;
    private BuildingCounts mockBuildingCounts;
    private Main mockGame;
    private Stage mockStage;
    private Skin mockSkin;

    private BuildingFrenzy buildingFrenzy;

    @BeforeEach
    void setUp() {
        mockTimer = Mockito.mock(Timer.class);
        mockAnnouncement = Mockito.mock(Announcement.class);
        mockBuildingManager = Mockito.mock(BuildingManager.class);
        mockSatisfaction = Mockito.mock(Satisfaction.class);
        mockBuildingCounts = Mockito.mock(BuildingCounts.class);
        mockGame = Mockito.mock(Main.class);

        // By default, let's say no building is being selected
        when(mockBuildingManager.getCurrentlySelecting()).thenReturn(false);

        // Achievements constructor calls updateStats() which looks at Accomodation.
        // For safety, let's stub buildingManager.getPlaced() to return an empty Array.
        when(mockBuildingManager.getPlaced()).thenReturn(new Array<>());


        mockSkin = Mockito.mock(Skin.class);
        mockStage = Mockito.mock(Stage.class);

        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = Mockito.mock(BitmapFont.class);

        when(mockSkin.get(LabelStyle.class)).thenReturn(labelStyle);

        buildingFrenzy = new BuildingFrenzy(
            mockTimer,
            mockSatisfaction,
            mockBuildingManager,
            mockBuildingCounts,
            mockAnnouncement,
            mockGame,
            mockStage,
            mockSkin
        );
    }

    @Test
    void testCheckForBuildingFrenzy() {
        // Should ends with success when no building shoule be built
        when(this.mockTimer.getElapsedTime()).thenReturn(-1f);
        buildingFrenzy.setEventTriggered(true);

        buildingFrenzy.checkForBuildingFrenzy(100);
        verify(mockAnnouncement).showAnnouncement("Completed\nBuilding Frenzy!\n+15% Satisfaction");

        // Nothing happens when the timer doesn't satisfy the check
        when(this.mockTimer.getElapsedTime()).thenReturn(60f);

        buildingFrenzy.checkForBuildingFrenzy(100);

        // Should start frenzy announcement when timer satisfies the check
        when(mockTimer.getElapsedTime()).thenReturn(25f);
        buildingFrenzy.checkForBuildingFrenzy(5);
        verify(mockAnnouncement).showAnnouncement("Building Frenzy!");
    }

    @Test
    void testFailedToBuildDuringFrenzy() {
        Array<Boolean> completedType = new Array<>();
        completedType.add(false);
        when(this.mockTimer.getElapsedTime()).thenReturn(-1f);
        buildingFrenzy.setEventTriggered(true);
        buildingFrenzy.updateCompletedType(completedType);

        buildingFrenzy.checkForBuildingFrenzy(100);
        verify(mockAnnouncement).showAnnouncement("Failed\nBuilding Frenzy!\n-15% Satisfaction");
    }

}
