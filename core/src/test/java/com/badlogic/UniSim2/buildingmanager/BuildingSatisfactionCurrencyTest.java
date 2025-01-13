package com.badlogic.UniSim2.buildingmanager;

import NPC.NPCManager;
import com.badlogic.UniSim2.buildingmanager.types.*;
import com.badlogic.UniSim2.stats.BuildingCounts;
import com.badlogic.UniSim2.stats.Money;
import com.badlogic.UniSim2.stats.Satisfaction;
import com.badlogic.UniSim2.stats.Timer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;


class BuildingSatisfactionCurrencyTest {

    private BuildingManager manager;
    private BuildingCounts mockBuildingCounts;
    private Money mockMoney;
    private Satisfaction mockSatisfaction;
    private Timer mockTimer;
    private Stage dummyStage;
    private Skin dummySkin;

    @BeforeEach
    void setUp() {
        mockBuildingCounts = mock(BuildingCounts.class);
        NPCManager mockNPCManager = mock(NPCManager.class);
        mockMoney = mock(Money.class);
        mockSatisfaction = mock(Satisfaction.class);
        mockTimer = mock(Timer.class);

        dummyStage = mock(Stage.class);
        dummySkin = mock(Skin.class);

        manager = new BuildingManager(mockBuildingCounts, mockNPCManager, mockMoney, mockSatisfaction,
            mockTimer, 1f, 1f, dummyStage, dummySkin);
        manager.getPlaced().clear();
    }

    @Test
    void testAccomodationFullyFilledStudents() {
        // Test scenario where one accommodation building is fully filled with students.
        Accomodation accom = mock(Accomodation.class);
        when(accom.isBroken()).thenReturn(false);
        when(accom.getType()).thenReturn(BuildingTypes.DERWENT);
        when(accom.getRooms()).thenReturn(250);
        when(accom.getIncome()).thenReturn(1f);

        Array<Building> placed = manager.getPlaced();
        placed.add(accom);

        manager.gainSatisfactionAndCurrency(false);

        verify(mockMoney).increaseMoney(250f);
        verify(mockSatisfaction).increaseSatis(0f);
    }

    @Test
    void testNoStudentsScenario() {
        manager.gainSatisfactionAndCurrency(false);

        FoodZone foodZone = mock(FoodZone.class);
        when(foodZone.isBroken()).thenReturn(false);
        when(foodZone.getType()).thenReturn(BuildingTypes.NISA);
        when(foodZone.getCapacity()).thenReturn(200);
        when(foodZone.getIncome()).thenReturn(2f);
        when(foodZone.getSatisfaction()).thenReturn(0.0005f);

        verify(mockMoney, never()).increaseMoney(anyFloat());
        verify(mockSatisfaction, never()).increaseSatis(anyFloat());
    }

    @Test
    void testMultipleBuildingsVaryingOccupancy() {
        // Test scenario with multiple buildings where totalRooms < totalCapacity.
        Accomodation accom1 = mock(Accomodation.class);
        when(accom1.isBroken()).thenReturn(false);
        when(accom1.getType()).thenReturn(BuildingTypes.DERWENT);
        when(accom1.getRooms()).thenReturn(250);
        when(accom1.getIncome()).thenReturn(1f);

        FoodZone foodZone = mock(FoodZone.class);
        when(foodZone.isBroken()).thenReturn(false);
        when(foodZone.getType()).thenReturn(BuildingTypes.NISA);
        when(foodZone.getCapacity()).thenReturn(200);
        when(foodZone.getIncome()).thenReturn(2f);
        when(foodZone.getSatisfaction()).thenReturn(0.0005f);

        Array<Building> placed = manager.getPlaced();
        placed.add(accom1);
        placed.add(foodZone);


        manager.gainSatisfactionAndCurrency(false);

        verify(mockMoney).increaseMoney(250f);
        verify(mockMoney).increaseMoney(400f);


        verify(mockSatisfaction).increaseSatis(0.1f);
    }
    @Test
    void testLectureHallWithLibrary() {
        Accomodation accom1 = mock(Accomodation.class);
        when(accom1.isBroken()).thenReturn(false);
        when(accom1.getType()).thenReturn(BuildingTypes.DERWENT);
        when(accom1.getRooms()).thenReturn(250);
        when(accom1.getIncome()).thenReturn(1f);

        LectureHall lectureHall = mock(LectureHall.class);
        when(lectureHall.isBroken()).thenReturn(false);
        when(lectureHall.getType()).thenReturn(BuildingTypes.PIAZZA);
        when(lectureHall.getCapacity()).thenReturn(200);
        when(lectureHall.getSatisfaction()).thenReturn(0.001f);

        Library library = mock(Library.class);
        when(library.isBroken()).thenReturn(false);
        when(library.getType()).thenReturn(BuildingTypes.LIBRARY);
        when(library.getCapacity()).thenReturn(100);
        when(library.getRooms()).thenReturn(100);

        Array<Building> placed = manager.getPlaced();
        placed.add(accom1);
        placed.add(lectureHall);
        placed.add(library);

        manager.gainSatisfactionAndCurrency(false);
        verify(mockSatisfaction).increaseSatis(0.24000001f);

    }
    @Test
    void testLabsDuringThirtySeconds() {
        Accomodation accom1 = mock(Accomodation.class);
        when(accom1.isBroken()).thenReturn(false);
        when(accom1.getType()).thenReturn(BuildingTypes.DERWENT);
        when(accom1.getRooms()).thenReturn(250);
        when(accom1.getIncome()).thenReturn(1f);

        Labs labs = mock(Labs.class);
        when(labs.isBroken()).thenReturn(false);
        when(labs.getType()).thenReturn(BuildingTypes.SOFTWARELABS);
        when(labs.getCapacity()).thenReturn(100);
        when(labs.getSatisfaction()).thenReturn(0.005f);

        Array<Building> placed = manager.getPlaced();
        placed.add(labs);
        placed.add(accom1);

        manager.gainSatisfactionAndCurrency(true);

        verify(mockSatisfaction).increaseSatis(0.5f);
    }
    @Test
    void testLabsNotDuringThirtySeconds() {
        Accomodation accom1 = mock(Accomodation.class);
        when(accom1.isBroken()).thenReturn(false);
        when(accom1.getType()).thenReturn(BuildingTypes.DERWENT);
        when(accom1.getRooms()).thenReturn(250);
        when(accom1.getIncome()).thenReturn(1f);

        Labs labs = mock(Labs.class);
        when(labs.isBroken()).thenReturn(false);
        when(labs.getType()).thenReturn(BuildingTypes.SOFTWARELABS);
        when(labs.getCapacity()).thenReturn(100);
        when(labs.getSatisfaction()).thenReturn(0.005f);

        Array<Building> placed = manager.getPlaced();
        placed.add(labs);
        placed.add(accom1);

        manager.gainSatisfactionAndCurrency(false);
        verify(mockSatisfaction, times(2)).increaseSatis(0f);
    }
}
