package com.badlogic.UniSim2.Events;

import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.buildingmanager.BuildingManager;
import com.badlogic.UniSim2.stats.BuildingCounts;
import com.badlogic.UniSim2.stats.Money;
import com.badlogic.UniSim2.stats.Satisfaction;
import com.badlogic.gdx.utils.Array;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.badlogic.UniSim2.buildingmanager.types.Accomodation;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AchievementsTest {

    private Announcement mockAnnouncement;
    private BuildingManager mockBuildingManager;
    private Satisfaction mockSatisfaction;
    private BuildingCounts mockBuildingCounts;
    private Money mockMoney;
    private Achievements achievements;

    @BeforeEach
    void setUp() {
        // Mock all dependencies
        mockAnnouncement = Mockito.mock(Announcement.class);
        mockBuildingManager = Mockito.mock(BuildingManager.class);
        mockSatisfaction = Mockito.mock(Satisfaction.class);
        mockBuildingCounts = Mockito.mock(BuildingCounts.class);
        mockMoney = Mockito.mock(Money.class);

        // By default, let's say no building is being selected
        when(mockBuildingManager.getCurrentlySelecting()).thenReturn(false);

        // Achievements constructor calls updateStats() which looks at Accomodation.
        // For safety, let's stub buildingManager.getPlaced() to return an empty Array.
        when(mockBuildingManager.getPlaced()).thenReturn(new Array<>());

        achievements = new Achievements(
                mockAnnouncement,
                mockBuildingManager,
                mockSatisfaction,
                mockBuildingCounts,
                mockMoney
        );
    }

    @Test
    void testRichMcGeeAchievement() {
        // If money > 50000, "Rich McGee" should trigger
        when(mockMoney.getMoney()).thenReturn(60000f);

        // Trigger an achievements check
        achievements.updateAchievements();

        // Verify "Rich McGee" was announced
        verify(mockAnnouncement).showAnnouncement("Achievement:\nRich McGee");
    }

    @Test
    void testHungryHippoAchievement() {
        // Two conditions in code:
        // 1) buildingCounts.getFoodZoneCount() == 10
        // OR
        // 2) (buildingCounts.getTotalCount() == 1 && buildingCounts.getFoodZoneCount() == 1)
        
        // Let's test the first condition:
        when(mockBuildingCounts.getFoodZoneCount()).thenReturn(10);
        when(mockBuildingCounts.getTotalCount()).thenReturn(10);

        achievements.updateAchievements();

        verify(mockAnnouncement).showAnnouncement("Achievement:\nHungry Hippo");
    }

    @Test
    void testBusyUniAchievement() {
        // 1) Mock an Accomodation with > 10000 rooms
        Accomodation mockAccom = Mockito.mock(Accomodation.class);
        when(mockAccom.getRooms()).thenReturn(20000);

        // 2) Have buildingManager.getPlaced() return that building
        Array<Building> placedArray = new Array<>();
        placedArray.add(mockAccom);
        when(mockBuildingManager.getPlaced()).thenReturn(placedArray);

        // 3) Simulate that we WERE selecting a building, so Achievements sets check = true
        when(mockBuildingManager.getCurrentlySelecting()).thenReturn(true);
        achievements.updateAchievements();

        // 4) Now simulate that we STOPPED selecting
        when(mockBuildingManager.getCurrentlySelecting()).thenReturn(false);
        achievements.updateAchievements();

        // 5) This second call triggers updateStats -> checkForAchievements -> "Busy Uni"
        verify(mockAnnouncement).showAnnouncement("Achievement:\nBusy Uni");
    }

    @Test
    void testSatisfactionAndNoLabAchievement() {
        // If labs == 0 and satisfaction > 79 -> "So Good No Lab"
        when(mockBuildingCounts.getLabsCount()).thenReturn(0);
        when(mockSatisfaction.getSatis()).thenReturn(80f);

        achievements.updateAchievements();

        verify(mockAnnouncement).showAnnouncement("Achievement:\nSo Good No Lab");
    }

    @Test
    void testNoAchievementsIfBelowThresholds() {
        // For example, money < 50000, labs != 0, etc.
        when(mockMoney.getMoney()).thenReturn(1000f);
        when(mockBuildingCounts.getFoodZoneCount()).thenReturn(0);
        when(mockBuildingCounts.getTotalCount()).thenReturn(5);
        when(mockBuildingCounts.getLabsCount()).thenReturn(1);
        when(mockSatisfaction.getSatis()).thenReturn(50f);

        achievements.updateAchievements();

        // None of the announcements should be called
        verify(mockAnnouncement, never()).showAnnouncement(anyString());
    }
}
