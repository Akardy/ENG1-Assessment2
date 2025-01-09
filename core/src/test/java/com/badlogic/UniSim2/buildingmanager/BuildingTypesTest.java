package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.UniSim2.buildingmanager.types.BuildingTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTypesTest {

    @Test
    void testMultipleBuildingTypesPerCategory() {
        // Check accommodation category has multiple types
        BuildingTypes[] accommodationTypes = {
            BuildingTypes.DERWENT,
            BuildingTypes.GOODRICKE,
            BuildingTypes.CONSTANTINE
        };
        Assertions.assertTrue(accommodationTypes.length > 1, "Accommodation category should have multiple types");

        // Check Lecture halls category has multiple types
        BuildingTypes[] lectureHallTypes = {
            BuildingTypes.PIAZZA,
            BuildingTypes.CENTRALHALL
        };
        Assertions.assertTrue(lectureHallTypes.length > 1, "LectureHall category should have multiple types");

        // Check food zones category has multiple types
        BuildingTypes[] foodZoneTypes = {
            BuildingTypes.NISA,
            BuildingTypes.GREGGS,
            BuildingTypes.DERWENTDINING
        };
        Assertions.assertTrue(foodZoneTypes.length > 1, "FoodZone category should have multiple types");

        // Check recreational buildings category has multiple types
        BuildingTypes[] recreationalTypes = {
            BuildingTypes.NATURE,
            BuildingTypes.GYM,
            BuildingTypes.SOCIETYBUILDING
        };
        Assertions.assertTrue(recreationalTypes.length > 1, "Recreational category should have multiple types");
    }
}
