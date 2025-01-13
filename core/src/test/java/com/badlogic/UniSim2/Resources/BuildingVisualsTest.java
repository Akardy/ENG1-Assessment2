package com.badlogic.UniSim2.Resources;

import com.badlogic.gdx.Gdx;
import org.junit.jupiter.api.Test;
import com.badlogic.UniSim2.resources.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BuildingVisualsTest extends AbstractHeadlessGdxTest{
    @Test
    public void testCentralHallPlacedAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.centralHallPlacedTexture)).exists(),
            "The asset for central hall placed should be available");
    }

    @Test
    public void testCentralHallDraggingAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.centralHallDraggingTexture)).exists(),
            "The asset for central hall dragging should be available");
    }

    @Test
    public void testCentralHallCollisionAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.centralHallCollisionTexture)).exists(),
            "The asset for central hall collision should be available");
    }

    @Test
    public void testConstantinePlacedAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.constantinePlacedTexture)).exists(),
            "The asset for constantine placed should be available");
    }

    @Test
    public void testConstantineDraggingAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.constantineDraggingTexture)).exists(),
            "The asset for constantine dragging should be available");
    }

    @Test
    public void testConstantineCollisionAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.constantineCollisionTexture)).exists(),
            "The asset for constantine collision should be available");
    }

    @Test
    public void testDerwentPlacedAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.derwentPlacedTexture)).exists(),
            "The asset for derwent placed should be available");
    }

    @Test
    public void testDerwentDraggingAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.derwentDraggingTexture)).exists(),
            "The asset for derwent dragging should be available");
    }

    @Test
    public void testDerwentCollisionAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.derwentCollisionTexture)).exists(),
            "The asset for derwent collision should be available");
    }

    @Test
    public void testDerwentDiningPlacedAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.derwentDiningPlacedTexture)).exists(),
            "The asset for derwent dining placed should be available");
    }

    @Test
    public void testDerwentDiningDraggingAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.derwentDiningDraggingTexture)).exists(),
            "The asset for derwent dining dragging should be available");
    }

    @Test
    public void testDerwentDiningCollisionAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.derwentDiningCollisionTexture)).exists(),
            "The asset for derwent dining collision should be available");
    }

    @Test
    public void testGoodrickePlacedAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.goodrickePlacedTexture)).exists(),
            "The asset for goodricke placed should be available");
    }

    @Test
    public void testGoodrickeDraggingAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.goodrickeDraggingTexture)).exists(),
            "The asset for goodricke dragging should be available");
    }

    @Test
    public void testGoodrickeCollisionAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.goodrickeCollisionTexture)).exists(),
            "The asset for goodricke collision should be available");
    }

    @Test
    public void testGreggsPlacedAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.greggsPlacedTexture)).exists(),
            "The asset for greggs placed should be available");
    }

    @Test
    public void testGreggsDraggingAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.greggsDraggingTexture)).exists(),
            "The asset for greggs dragging should be available");
    }

    @Test
    public void testGreggsCollisionAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.greggsCollisionTexture)).exists(),
            "The asset for greggs collision should be available");
    }

    @Test
    public void testGymPlacedAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.gymPlacedTexture)).exists(),
            "The asset for the gym placed should be available");
    }

    @Test
    public void testGymDraggingAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.gymDraggingTexture)).exists(),
            "The asset for the gym dragging should be available");
    }

    @Test
    public void testGymCollisionAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.gymCollisionTexture)).exists(),
            "The asset for the gym collision should be available");
    }

    @Test
    public void testHardwareLabsPlacedAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.hardwareLabsPlacedTexture)).exists(),
            "The asset for the hardware labs placed should be available");
    }

    @Test
    public void testHardwareLabsDraggingAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.hardwareLabsDraggingTexture)).exists(),
            "The asset for the hardware labs dragging should be available");
    }

    @Test
    public void testHardwareLabsCollisionAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.hardwareLabsCollisionTexture)).exists(),
            "The asset for the hardware labs collision should be available");
    }

    @Test
    public void testLibraryPlacedAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.libraryPlacedTexture)).exists(),
            "The asset for the library placed should be available");
    }

    @Test
    public void testLibraryDraggingAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.libraryDraggingTexture)).exists(),
            "The asset for the library dragging should be available");
    }

    @Test
    public void testLibraryCollisionAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.libraryCollisionTexture)).exists(),
            "The asset for the library collision should be available");
    }

    @Test
    public void testNaturePlacedAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.naturePlacedTexture)).exists(),
            "The asset for nature placed should be available");
    }

    @Test
    public void testNatureDraggingAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.natureDraggingTexture)).exists(),
            "The asset for nature dragging should be available");
    }

    @Test
    public void testNatureCollisionAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.natureCollisionTexture)).exists(),
            "The asset for nature collision should be available");
    }

    @Test
    public void testNisaPlacedAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.nisaPlacedTexture)).exists(),
            "The asset for nisa placed should be available");
    }

    @Test
    public void testNisaDraggingAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.nisaDraggingTexture)).exists(),
            "The asset for nisa dragging should be available");
    }

    @Test
    public void testNisaCollisionAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.nisaCollisionTexture)).exists(),
            "The asset for nisa collision should be available");
    }

    @Test
    public void testPiazzaPlacedAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.piazzaPlacedTexture)).exists(),
            "The asset for the piazza placed should be available");
    }

    @Test
    public void testPiazzaDraggingAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.piazzaDraggingTexture)).exists(),
            "The asset for the piazza dragging should be available");
    }

    @Test
    public void testPiazzaCollisionAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.piazzaCollisionTexture)).exists(),
            "The asset for the piazza collision should be available");
    }

    @Test
    public void testSocietyBuildingPlacedAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.societyBuildingPlacedTexture)).exists(),
            "The asset for the society building placed should be available");
    }

    @Test
    public void testSocietyBuildingDraggingAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.societyBuildingDraggingTexture)).exists(),
            "The asset for the society building dragging should be available");
    }

    @Test
    public void testSocietyBuildingCollisionAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.societyBuildingCollisionTexture)).exists(),
            "The asset for the society building collision should be available");
    }

    @Test
    public void testSoftwareLabsPlacedAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.softwareLabsPlacedTexture)).exists(),
            "The asset for the software labs placed should be available");
    }

    @Test
    public void testSoftwareLabsDraggingAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.softwareLabsDraggingTexture)).exists(),
            "The asset for the software labs dragging should be available");
    }

    @Test
    public void testSoftwareLabsCollisionAssetExists() {
        assertTrue(Gdx.files.internal(String.valueOf(Assets.softwareLabsCollisionTexture)).exists(),
            "The asset for the software labs collision should be available");
    }
}
