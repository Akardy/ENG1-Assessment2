package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.UniSim2.buildingmanager.types.Accomodation;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.UniSim2.buildingmanager.types.BuildingTypes;
import com.badlogic.UniSim2.GUImanager.BuildingMenu;
import com.badlogic.gdx.utils.Array;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTypesTest {


    @Test
    void testAccommodationHasMultipleTypes() {
        int count = 0;
        for(BuildingTypes buildingType : BuildingTypes.values()) {
            if (Objects.equals(buildingType.getType(), "Accommodation")) {
                count++;
            }
        }
        assertTrue(count > 1, "Should be multiple types of Accommodation");
    }

    @Test
    void testLectureHallHasMultipleTypes() {
        int count = 0;
        for(BuildingTypes buildingType : BuildingTypes.values()) {
            if (Objects.equals(buildingType.getType(), "LectureHall")) {
                count++;
            }
        }
        assertTrue(count > 1, "Should be multiple types of LectureHall");
    }

    @Test
    void testLibraryHasSingleType() {
        int count = 0;
        for(BuildingTypes buildingType : BuildingTypes.values()) {
            if (Objects.equals(buildingType.getType(), "Library")) {
                count++;
            }
        }
        assertEquals(1, count, "Should have one type of Library");
    }

    @Test
    void testLabsHaveMultipleTypes() {
        int count = 0;
        for(BuildingTypes buildingType : BuildingTypes.values()) {
            if (Objects.equals(buildingType.getType(), "Labs")) {
                count++;
            }
        }
        assertTrue(count > 1, "Should be multiple types of Labs");
    }

    @Test
    void testFoodZoneHasMultipleTypes() {
        int count = 0;
        for(BuildingTypes buildingType : BuildingTypes.values()) {
            if (Objects.equals(buildingType.getType(), "FoodZone")) {
                count++;
            }
        }
        assertTrue(count > 1, "Should be multiple types of FoodZone");
    }

    @Test
    void testRecreationalHasMultipleTypes() {
        int count = 0;
        for(BuildingTypes buildingType : BuildingTypes.values()) {
            if (Objects.equals(buildingType.getType(), "Recreational")) {
                count++;
            }
        }
        assertTrue(count > 1, "Should be multiple types of Recreational");
    }
}
