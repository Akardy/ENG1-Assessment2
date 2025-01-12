package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.UniSim2.buildingmanager.types.Accomodation;
import com.badlogic.UniSim2.buildingmanager.types.Recreational;
import com.badlogic.UniSim2.buildingmanager.types.BuildingTypes;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

import static org.junit.jupiter.api.Assertions.*;


public class BuildingBonusTest {

    @Test
    public void testCalculateDiscountRateRandomPlace() {
        // Mock textures
        Texture mockPlacedTexture = mock(Texture.class);
        Texture mockCollisionTexture = mock(Texture.class);
        Texture mockDraggingTexture = mock(Texture.class);


        Recreational recreational = new Recreational(
            mockPlacedTexture,
            mockCollisionTexture,
            mockDraggingTexture,
            Consts.RECREATIONAL_WIDTH,
            Consts.RECREATIONAL_HEIGHT,
            1000f,
            "Gym",
            BuildingTypes.GYM,
            150,
            0.07f
        );

        recreational.setPosition(200, 200);

        // Create an accommodation building
        Accomodation accomodation = new Accomodation(
            mockPlacedTexture,
            mockCollisionTexture,
            mockDraggingTexture,
            Consts.ACCOMODATION_WIDTH,
            Consts.ACCOMODATION_HEIGHT,
            2500f,
            "Derwent",
            250,
            BuildingTypes.DERWENT,
            1f,
            5,
            250
        );

        accomodation.setPosition(500, 400);

        // Calculate discount rate (to calculate new best discount rate)
        recreational.calculateDiscountRate(accomodation.getX(), accomodation.getY());
        // calculated value through our formula:
        float calculatedBestDiscountRate = 0.042555783f;
        assertEquals(calculatedBestDiscountRate, recreational.getBestDiscountRate());
    }
    @Test
    public void testCalculateDiscountRateMultiple() {
        // Mock textures
        Texture mockPlacedTexture = mock(Texture.class);
        Texture mockCollisionTexture = mock(Texture.class);
        Texture mockDraggingTexture = mock(Texture.class);


        Recreational recreational = new Recreational(
            mockPlacedTexture,
            mockCollisionTexture,
            mockDraggingTexture,
            Consts.RECREATIONAL_WIDTH,
            Consts.RECREATIONAL_HEIGHT,
            1000f,
            "Gym",
            BuildingTypes.GYM,
            150,
            0.07f
        );

        recreational.setPosition(200, 200);

        // Create an accommodation building
        Accomodation accomodation = new Accomodation(
            mockPlacedTexture,
            mockCollisionTexture,
            mockDraggingTexture,
            Consts.ACCOMODATION_WIDTH,
            Consts.ACCOMODATION_HEIGHT,
            2500f,
            "Derwent",
            250,
            BuildingTypes.DERWENT,
            1f,
            5,
            250
        );

        accomodation.setPosition(300, 400);
        // Calculate discount rate (to calculate best discount rate)
        recreational.calculateDiscountRate(accomodation.getX(), accomodation.getY());



        // Create second accommodation building
        Accomodation accomodation2 = new Accomodation(
            mockPlacedTexture,
            mockCollisionTexture,
            mockDraggingTexture,
            Consts.ACCOMODATION_WIDTH,
            Consts.ACCOMODATION_HEIGHT,
            2500f,
            "Derwent",
            250,
            BuildingTypes.DERWENT,
            1f,
            5,
            250
        );

        accomodation2.setPosition(50, 400);
        // Calculate discount rate (to calculate best discount rate)
        recreational.calculateDiscountRate(accomodation2.getX(), accomodation2.getY());

        // Create third accommodation building
        Accomodation accomodation3 = new Accomodation(
            mockPlacedTexture,
            mockCollisionTexture,
            mockDraggingTexture,
            Consts.ACCOMODATION_WIDTH,
            Consts.ACCOMODATION_HEIGHT,
            2500f,
            "Derwent",
            250,
            BuildingTypes.DERWENT,
            1f,
            5,
            250
        );

        accomodation3.setPosition(700, 700);
        // Calculate discount rate (to calculate best discount rate)
        recreational.calculateDiscountRate(accomodation.getX(), accomodation.getY());


        // calculated value through our formula:
        float calculatedBestDiscountRate2 = 0.011149931f;
        assertEquals(calculatedBestDiscountRate2, recreational.getBestDiscountRate());
    }
    @Test
    public void testCalculateDiscountRateNoAccomodation() {
        // Mock textures
        Texture mockPlacedTexture = mock(Texture.class);
        Texture mockCollisionTexture = mock(Texture.class);
        Texture mockDraggingTexture = mock(Texture.class);


        Recreational recreational = new Recreational(
            mockPlacedTexture,
            mockCollisionTexture,
            mockDraggingTexture,
            Consts.RECREATIONAL_WIDTH,
            Consts.RECREATIONAL_HEIGHT,
            1000f,
            "Gym",
            BuildingTypes.GYM,
            150,
            0.07f
        );

        recreational.setPosition(200, 200);

        // calculated value through our formula:
        float calculatedBestDiscountRate = 1;
        assertEquals(calculatedBestDiscountRate, recreational.getBestDiscountRate());
    }
}
