package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.UniSim2.buildingmanager.types.BuildingTypes;
import com.badlogic.UniSim2.GUImanager.BuildingMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTypesTest {

    private BuildingMenu buildingMenu;

    @BeforeEach
    void setup() {
        Skin mockSkin = Mockito.mock(Skin.class);
        buildingMenu = new BuildingMenu(mockSkin);
    }

    @Test
    void testAccommodationHasMultipleTypes() {
        BuildingTypes[] subOptions = buildingMenu.getSubOptionList(BuildingTypes.ACCOMODATION);

        assertNotNull(subOptions, "Accommodation category should not be null");
        assertTrue(subOptions.length > 1, "Accommodation category should have multiple building types");
    }

    @Test
    void testLectureHallHasMultipleTypes() {
        BuildingTypes[] subOptions = buildingMenu.getSubOptionList(BuildingTypes.LECTUREHALL);

        assertNotNull(subOptions, "LectureHall category should not be null");
        assertTrue(subOptions.length > 1, "LectureHall category should have multiple building types");
    }

    @Test
    void testLibraryHasSingleType() {
        BuildingTypes[] subOptions = buildingMenu.getSubOptionList(BuildingTypes.LIBRARY);

        assertNotNull(subOptions, "Library category should not be null");
        assertEquals(1, subOptions.length, "Library category should have exactly one building type");
    }

    @Test
    void testLabsHaveMultipleTypes() {
        BuildingTypes[] subOptions = buildingMenu.getSubOptionList(BuildingTypes.LABS);

        assertNotNull(subOptions, "Labs category should not be null");
        assertTrue(subOptions.length > 1, "Labs category should have multiple building types");
    }

    @Test
    void testFoodZoneHasMultipleTypes() {
        BuildingTypes[] subOptions = buildingMenu.getSubOptionList(BuildingTypes.FOODZONE);

        assertNotNull(subOptions, "FoodZone category should not be null");
        assertTrue(subOptions.length > 1, "FoodZone category should have multiple building types");
    }

    @Test
    void testRecreationalHasMultipleTypes() {
        BuildingTypes[] subOptions = buildingMenu.getSubOptionList(BuildingTypes.RECREATIONAL);

        assertNotNull(subOptions, "Recreational category should not be null");
        assertTrue(subOptions.length > 1, "Recreational category should have multiple building types");
    }
}
