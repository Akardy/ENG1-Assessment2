package com.badlogic.UniSim2.Events;


import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.buildingmanager.BuildingManager;
import com.badlogic.UniSim2.buildingmanager.types.Accomodation;
import com.badlogic.UniSim2.stats.Satisfaction;
import com.badlogic.UniSim2.stats.Timer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Test class for verifying the behavior of the Exam event in various scenarios.
 * The tests check for correct announcement triggers under exam passing and failing conditions,
 * as well as behavior when the exam should not trigger.
 * This uses custom exception handling to bypass any graphical rendering errors occurring due to mocking
 */

class ExamEventTest {

    private Timer mockTimer;
    private BuildingManager mockBuildingManager;
    private Satisfaction mockSatisfaction;
    private Announcement mockAnnouncement;
    private Exam exam;
    private Stage dummyStage;
    private Skin dummySkin;
    /**
     * Sets up the mocked dependencies and initializes an Exam instance before each test.
     */
    @BeforeEach
    void setUp() {
        // Mock dependencies
        mockTimer = mock(Timer.class);
        mockBuildingManager = mock(BuildingManager.class);
        mockSatisfaction = mock(Satisfaction.class);
        mockAnnouncement = mock(Announcement.class);

        dummyStage = mock(Stage.class);
        dummySkin = mock(Skin.class);

        when(mockBuildingManager.getPlaced()).thenReturn(new com.badlogic.gdx.utils.Array<>());
        when(mockBuildingManager.getCurrentlySelecting()).thenReturn(false);

        exam = new Exam(mockTimer, mockBuildingManager, mockSatisfaction, mockAnnouncement, dummyStage, dummySkin);
    }
    /**
     * Tests that multiple buildings can lead to an "Exam Passed!" announcement.
     * Simulates conditions where combined exam space from multiple accommodations meets the requirement.
     * Verifies that the appropriate announcement is triggered by catching a custom RuntimeException.
     */
    @Test
    void testExamPassedMultipleBuildingsAnnouncement() {
        // Set time to exam time
        when(mockTimer.getElapsedTime()).thenReturn(50f);
        when(mockBuildingManager.getCurrentlySelecting()).thenReturn(false);


        // call check triggering event to trigger the exam start, runtime exception will occur due to skin but only after exam starts
        assertThrows(RuntimeException.class, () -> {
            exam.checkTriggeringEvent(0.1f);
        });

        // Stub the announcement method to throw an exception when exam passed and +10% satisfaction is announced
        RuntimeException passException = new RuntimeException("Exam passed triggered");
        doThrow(passException)
            .when(mockAnnouncement)
            .showAnnouncement("Exam Passed! \n+ 10% Satisfaction!");

        // Setup a mock Accomodations that ensures exam passing criteria
        Accomodation mockAccom = mock(Accomodation.class);
        when(mockAccom.getRooms()).thenReturn(100);
        when(mockAccom.getExamSpace()).thenReturn(150);
        when(mockAccom.isBroken()).thenReturn(false);


        // Set up a second mock accommodation (this one alone would fail but in combination should succeed)
        Accomodation mockAccom1 = mock(Accomodation.class);
        when(mockAccom1.getRooms()).thenReturn(100);
        when(mockAccom1.getExamSpace()).thenReturn(80);
        when(mockAccom1.isBroken()).thenReturn(false);

        Array<Building> placedBuildings = new Array<>();
        placedBuildings.add(mockAccom);
        placedBuildings.add(mockAccom1);
        when(mockBuildingManager.getPlaced()).thenReturn(placedBuildings);

        // Trigger the exam event and simulate passage of time
        RuntimeException caughtException = null;
        try {
            exam.checkTriggeringEvent(20f);
        } catch (RuntimeException e) {
            caughtException = e;  // Catch the exception from "Exam Passed" exam is passed
        }

        // Check if exception is the correct message
        assertEquals("Exam passed triggered", caughtException.getMessage());
    }
    /**
     * Tests that a single building triggers the "Exam Passed!" announcement correctly.
     * Sets up conditions for passing and verifies the announcement by catching a custom RuntimeException.
     */
    @Test
    void testExamPassedAnnouncement() {
        // Set time to exam time
        when(mockTimer.getElapsedTime()).thenReturn(50f);
        when(mockBuildingManager.getCurrentlySelecting()).thenReturn(false);


        // call check triggering event to trigger the exam start, runtime exception will occur due to skin but only after exam starts
        assertThrows(RuntimeException.class, () -> {
            exam.checkTriggeringEvent(0.1f);
        });

        // Stub the announcement method to throw an exception when exam passed and +10% satisfaction is announced
        RuntimeException passException = new RuntimeException("Exam passed triggered");
        doThrow(passException)
            .when(mockAnnouncement)
            .showAnnouncement("Exam Passed! \n+ 10% Satisfaction!");

        // Setup a mock Accomodation that ensures exam passing criteria
        Accomodation mockAccom = mock(Accomodation.class);
        when(mockAccom.getRooms()).thenReturn(100);
        when(mockAccom.getExamSpace()).thenReturn(150);
        when(mockAccom.isBroken()).thenReturn(false);

        Array<Building> placedBuildings = new Array<>();
        placedBuildings.add(mockAccom);
        when(mockBuildingManager.getPlaced()).thenReturn(placedBuildings);

        // Trigger the exam event and simulate passage of time
        RuntimeException caughtException = null;
        try {
                exam.checkTriggeringEvent(20f);
        } catch (RuntimeException e) {
            caughtException = e;  // Catch the exception from "Exam Passed" exam is passed
        }

        // Check if exception is the correct message
        assertEquals("Exam passed triggered", caughtException.getMessage());
    }
    /**
     * Tests that multiple buildings can lead to an "Exam Failed!" announcement.
     * Simulates conditions where combined exam space from multiple accommodations is insufficient.
     * Verifies that the appropriate announcement is triggered by catching a custom RuntimeException.
     */
    @Test
    void testExamFailedMultipleBuildingsAnnouncement() {
        // Set time to exam time
        when(mockTimer.getElapsedTime()).thenReturn(50f);
        when(mockBuildingManager.getCurrentlySelecting()).thenReturn(false);


        // call check triggering event to trigger the exam start, runtime exception will occur due to skin but only after exam starts
        assertThrows(RuntimeException.class, () -> {
            exam.checkTriggeringEvent(0.1f);
        });

        // Stub the announcement method to throw an exception when exam failed and -10% satisfaction is announced
        RuntimeException failException = new RuntimeException("Exam failed triggered");
        doThrow(failException)
            .when(mockAnnouncement)
            .showAnnouncement("Exam Failed! \n- 10% Satisfaction!");

        // Setup mock Accomodations that ensures exam failing criteria
        Accomodation mockAccom = mock(Accomodation.class);
        when(mockAccom.getRooms()).thenReturn(100);
        when(mockAccom.getExamSpace()).thenReturn(50);
        when(mockAccom.isBroken()).thenReturn(false);

        // Set up a second mock accommodation which by itself would pass, but in combination should fail
        Accomodation mockAccom1 = mock(Accomodation.class);
        when(mockAccom1.getRooms()).thenReturn(100);
        when(mockAccom1.getExamSpace()).thenReturn(110);
        when(mockAccom1.isBroken()).thenReturn(false);

        Array<Building> placedBuildings = new Array<>();
        placedBuildings.add(mockAccom);
        placedBuildings.add(mockAccom1);
        when(mockBuildingManager.getPlaced()).thenReturn(placedBuildings);


        // Trigger the exam event and simulate passage of time
        RuntimeException caughtException = null;
        try {
            exam.checkTriggeringEvent(20f);
        } catch (RuntimeException e) {
            caughtException = e;  // Catch the exception from "Exam Failed" exam is failed
        }

        // Check if exception is the correct message
        assertEquals("Exam failed triggered", caughtException.getMessage());
    }
    /**
     * Tests that a single building triggers the "Exam Failed!" announcement correctly.
     * Sets up conditions for failing and verifies the announcement by catching a custom RuntimeException.
     */
    @Test
    void testExamFailedAnnouncement() {
        // Set time to exam time
        when(mockTimer.getElapsedTime()).thenReturn(50f);
        when(mockBuildingManager.getCurrentlySelecting()).thenReturn(false);


        // call check triggering event to trigger the exam start, runtime exception will occur due to skin but only after exam starts
        assertThrows(RuntimeException.class, () -> {
            exam.checkTriggeringEvent(0.1f);
        });

        // Stub the announcement method to throw an exception when exam failed and -10% satisfaction is announced
        RuntimeException failException = new RuntimeException("Exam failed triggered");
        doThrow(failException)
            .when(mockAnnouncement)
            .showAnnouncement("Exam Failed! \n- 10% Satisfaction!");

        // Setup a mock Accomodation that ensures exam failing criteria
        Accomodation mockAccom = mock(Accomodation.class);
        when(mockAccom.getRooms()).thenReturn(100);
        when(mockAccom.getExamSpace()).thenReturn(50);
        when(mockAccom.isBroken()).thenReturn(false);

        Array<Building> placedBuildings = new Array<>();
        placedBuildings.add(mockAccom);
        when(mockBuildingManager.getPlaced()).thenReturn(placedBuildings);


        // Trigger the exam event and simulate passage of time
        RuntimeException caughtException = null;
        try {
            exam.checkTriggeringEvent(20f);
        } catch (RuntimeException e) {
            caughtException = e;  // Catch the exception from "Exam Failed" exam is failed
        }

        // Check if exception is the correct message
        assertEquals("Exam failed triggered", caughtException.getMessage());
    }


    /**
     * Tests that an exam announcement is triggered when the timer matches the exam condition.
     * It verifies that the showAnnouncement for "EXAM TIME!!!" is called.
     */
    @Test
    void testExamAnnouncementTrigger() {


        // set time to exam time
        when(mockTimer.getElapsedTime()).thenReturn(50f);


        // throws an exception when exam time is triggered - so graphic errors, occurring due to mocking, dont occur
        RuntimeException announcementException = new RuntimeException("Announcement triggered");
        doThrow(announcementException).when(mockAnnouncement).showAnnouncement("EXAM TIME!!!");

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            exam.checkTriggeringEvent(0.1f);
        });

        assertEquals("Announcement triggered", thrownException.getMessage());

        verify(mockAnnouncement).showAnnouncement("EXAM TIME!!!");
    }
    /**
     * Tests that no exam announcement is triggered when the timer does not match the exam condition.
     */
    @Test
    void testExamDoesNotTriggerWhenTimeNotMatching() {
        when(mockTimer.getElapsedTime()).thenReturn(30f);
        exam.checkTriggeringEvent(0.1f);
        verify(mockAnnouncement, never()).showAnnouncement(anyString());
    }


}
