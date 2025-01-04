package com.badlogic.UniSim2.resources;

import com.badlogic.UniSim2.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * This class is used to store all textures that will be used in the game.
 * All textures are made public static variable and should not be changed
 * by another other class.
 */
public class Assets {

    public static Music music;
    public static Sound click;

    public static Texture startBackgroundTexture;
    public static Texture startButtonUpTexture;
    public static Texture startButtonDownTexture;

    public static Texture creditsButtonTexture;

    public static Texture startMenuButtonTexture;

    public static Texture resumeButtonTexture;

    public static Texture settingsButtonTexture;

    public static Texture res720ButtonTexture;

    public static Texture res1080ButtonTexture;

    public static Texture res1440ButtonTexture;

    public static Texture backgroundTexture;
    public static Texture pathTexture;

    public static Texture menuBarTexture;

    ///////////// button up //////////////

    public static Texture accomodationButtonUpTexture;
    public static Texture derwentButtonUpTexture;
    public static Texture goodrickeButtonUpTexture;
    public static Texture constantineButtonUpTexture;

    public static Texture lectureHallButtonUpTexture;
    public static Texture piazzaButtonUpTexture;
    public static Texture centralHallButtonUpTexture;

    public static Texture libraryButtonUpTexture;

    public static Texture courseButtonUpTexture;
    public static Texture softwareLabsButtonUpTexture;
    public static Texture hardwareLabsButtonUpTexture;

    public static Texture foodZoneButtonUpTexture;
    public static Texture nisaButtonUpTexture;
    public static Texture greggsButtonUpTexture;
    public static Texture derwentDiningButtonUpTexture;

    public static Texture recreationalButtonUpTexture;
    public static Texture natureButtonUpTexture;
    public static Texture societyBuildingButtonUpTexture;
    public static Texture gymButtonUpTexture;

    public static Texture[] buttonUpTextures;

    ////////////////// button down /////////////
    public static Texture accomodationButtonDownTexture;
    public static Texture derwentButtonDownTexture;
    public static Texture goodrickeButtonDownTexture;
    public static Texture constantineButtonDownTexture;

    public static Texture lectureHallButtonDownTexture;
    public static Texture piazzaButtonDownTexture;
    public static Texture centralHallButtonDownTexture;

    public static Texture libraryButtonDownTexture;

    public static Texture courseButtonDownTexture;
    public static Texture softwareLabsButtonDownTexture;
    public static Texture hardwareLabsButtonDownTexture;

    public static Texture foodZoneButtonDownTexture;
    public static Texture nisaButtonDownTexture;
    public static Texture greggsButtonDownTexture;
    public static Texture derwentDiningButtonDownTexture;

    public static Texture recreationalButtonDownTexture;
    public static Texture natureButtonDownTexture;
    public static Texture societyBuildingButtonDownTexture;
    public static Texture gymButtonDownTexture;

    public static Texture[] buttonDownTextures;

    ///////////// texture/////////////
    public static Texture accomodationPlacedTexture;
    public static Texture accomodationCollisionTexture;
    public static Texture accomodationDraggingTexture;

    public static Texture derwentPlacedTexture;
    public static Texture derwentCollisionTexture;
    public static Texture derwentDraggingTexture;

    public static Texture goodrickePlacedTexture;
    public static Texture goodrickeCollisionTexture;
    public static Texture goodrickeDraggingTexture;

    public static Texture constantinePlacedTexture;
    public static Texture constantineCollisionTexture;
    public static Texture constantineDraggingTexture;

    public static Texture lectureHallPlacedTexture;
    public static Texture lectureHallCollisionTexture;
    public static Texture lectureHallDraggingTexture;

    public static Texture piazzaPlacedTexture;
    public static Texture piazzaCollisionTexture;
    public static Texture piazzaDraggingTexture;

    public static Texture centralHallPlacedTexture;
    public static Texture centralHallCollisionTexture;
    public static Texture centralHallDraggingTexture;

    public static Texture libraryPlacedTexture;
    public static Texture libraryCollisionTexture;
    public static Texture libraryDraggingTexture;

    public static Texture coursePlacedTexture;
    public static Texture courseCollisionTexture;
    public static Texture courseDraggingTexture;

    public static Texture softwareLabsPlacedTexture;
    public static Texture softwareLabsCollisionTexture;
    public static Texture softwareLabsDraggingTexture;

    public static Texture hardwareLabsPlacedTexture;
    public static Texture hardwareLabsCollisionTexture;
    public static Texture hardwareLabsDraggingTexture;

    public static Texture foodZonePlacedTexture;
    public static Texture foodZoneCollisionTexture;
    public static Texture foodZoneDraggingTexture;

    public static Texture nisaPlacedTexture;
    public static Texture nisaCollisionTexture;
    public static Texture nisaDraggingTexture;

    public static Texture greggsPlacedTexture;
    public static Texture greggsCollisionTexture;
    public static Texture greggsDraggingTexture;

    public static Texture derwentDiningPlacedTexture;
    public static Texture derwentDiningCollisionTexture;
    public static Texture derwentDiningDraggingTexture;

    public static Texture recreationalPlacedTexture;
    public static Texture recreationalCollisionTexture;
    public static Texture recreationalDraggingTexture;

    public static Texture naturePlacedTexture;
    public static Texture natureCollisionTexture;
    public static Texture natureDraggingTexture;

    public static Texture gymPlacedTexture;
    public static Texture gymCollisionTexture;
    public static Texture gymDraggingTexture;

    public static Texture societyBuildingPlacedTexture;
    public static Texture societyBuildingCollisionTexture;
    public static Texture societyBuildingDraggingTexture;

    public static TextureRegion NPCTexture;

    private Assets() {
    };

    /**
     * This method loads all the textures that might be used.
     * Note that this method should not be called before libgdx has called the
     * {@link Main#create()} method.
     */
    public static void loadTextures() {

        // =======================================
        // SOUND EFFECTS AND MUSIC.
        // =======================================
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        click = Gdx.audio.newSound(Gdx.files.internal("sounds/click.mp3"));

        // Start menu textures
        startBackgroundTexture = new Texture("startBackground.png");
        startButtonUpTexture = new Texture("startButtonUp.png");
        startButtonDownTexture = new Texture("startButtonDown.png");

        creditsButtonTexture = new Texture("creditsButtonUp.png");

        startMenuButtonTexture = new Texture("textures/buttons/startMenuButton.png");

        resumeButtonTexture = new Texture("textures/buttons/resumeButton.png");

        settingsButtonTexture = new Texture("textures/buttons/settingsButton.png");

        res720ButtonTexture = new Texture("textures/buttons/res720Button.png");

        res1080ButtonTexture = new Texture("textures/buttons/res1080Button.png");

        res1440ButtonTexture = new Texture("textures/buttons/res1440Button.png");

        // Background texture
        backgroundTexture = new Texture("background.png");
        pathTexture = new Texture("path.png");

        menuBarTexture = new Texture("menuBar.png");

        // =======================================
        // BUTTON TEXTURES
        // =======================================
        // Building button textures when not hovering over
        accomodationButtonUpTexture = new Texture("textures/buttons/accomodationButtonUp.png");
        derwentButtonUpTexture = new Texture("textures/buttons/accomodationButtonUp.png");
        goodrickeButtonUpTexture = new Texture("textures/buttons/accomodationButtonUp.png");
        constantineButtonUpTexture = new Texture("textures/buttons/accomodationButtonUp.png");

        lectureHallButtonUpTexture = new Texture("textures/buttons/lectureHallButtonUp.png");
        centralHallButtonUpTexture = new Texture("textures/buttons/lectureHallButtonUp.png");
        piazzaButtonUpTexture = new Texture("textures/buttons/lectureHallButtonUp.png");

        libraryButtonUpTexture = new Texture("textures/buttons/libraryButtonUp.png");
        courseButtonUpTexture = new Texture("textures/buttons/courseButtonUp.png");
        softwareLabsButtonUpTexture = new Texture("textures/buttons/courseButtonUp.png");
        hardwareLabsButtonUpTexture = new Texture("textures/buttons/courseButtonUp.png");

        foodZoneButtonUpTexture = new Texture("textures/buttons/foodZoneButtonUp.png");
        nisaButtonUpTexture = new Texture("textures/buttons/foodZoneButtonUp.png");
        greggsButtonUpTexture = new Texture("textures/buttons/foodZoneButtonUp.png");
        derwentDiningButtonUpTexture = new Texture("textures/buttons/foodZoneButtonUp.png");

        recreationalButtonUpTexture = new Texture("textures/buttons/recreationalButtonUp.png");
        natureButtonUpTexture = new Texture("textures/buttons/natureButtonUp.png");
        gymButtonUpTexture = new Texture("textures/buttons/natureButtonUp.png");
        societyBuildingButtonUpTexture = new Texture("textures/buttons/natureButtonUp.png");

        buttonUpTextures = new Texture[] {
                accomodationButtonUpTexture,
                lectureHallButtonUpTexture,
                libraryButtonUpTexture,
                courseButtonUpTexture,
                foodZoneButtonUpTexture,
                recreationalButtonUpTexture
        };

        // Building button textures when hovering over
        accomodationButtonDownTexture = new Texture("textures/buttons/accomodationButtonDown.png");
        derwentButtonDownTexture = new Texture("textures/buttons/accomodationButtonDown.png");
        constantineButtonDownTexture = new Texture("textures/buttons/accomodationButtonDown.png");
        goodrickeButtonDownTexture = new Texture("textures/buttons/accomodationButtonDown.png");

        lectureHallButtonDownTexture = new Texture("textures/buttons/lectureHallButtonDown.png");
        piazzaButtonDownTexture = new Texture("textures/buttons/lectureHallButtonDown.png");
        centralHallButtonDownTexture = new Texture("textures/buttons/lectureHallButtonDown.png");

        libraryButtonDownTexture = new Texture("textures/buttons/libraryButtonDown.png");
        courseButtonDownTexture = new Texture("textures/buttons/courseButtonDown.png");
        softwareLabsButtonDownTexture = new Texture("textures/buttons/courseButtonDown.png");
        hardwareLabsButtonDownTexture = new Texture("textures/buttons/courseButtonDown.png");

        foodZoneButtonDownTexture = new Texture("textures/buttons/foodZoneButtonDown.png");
        nisaButtonDownTexture = new Texture("textures/buttons/foodZoneButtonDown.png");
        greggsButtonDownTexture = new Texture("textures/buttons/foodZoneButtonDown.png");
        derwentDiningButtonDownTexture = new Texture("textures/buttons/foodZoneButtonDown.png");

        recreationalButtonDownTexture = new Texture("textures/buttons/recreationalButtonDown.png");
        natureButtonDownTexture = new Texture("textures/buttons/natureButtonDown.png");
        gymButtonDownTexture = new Texture("textures/buttons/natureButtonDown.png");
        societyBuildingButtonDownTexture = new Texture("textures/buttons/natureButtonDown.png");

        buttonDownTextures = new Texture[] {
                accomodationButtonDownTexture,
                lectureHallButtonDownTexture,
                libraryButtonDownTexture,
                courseButtonDownTexture,
                foodZoneButtonDownTexture,
                recreationalButtonDownTexture
        };

        // =======================================
        // BUILDING SPRITE TEXTURES
        // =======================================
        accomodationPlacedTexture = new Texture("textures/buildings/accomodationPlaced.png");
        accomodationCollisionTexture = new Texture("textures/buildings/accomodationCollision.png");
        accomodationDraggingTexture = new Texture("textures/buildings/accomodationDragging.png");

        derwentPlacedTexture = new Texture("textures/buildings/derwentPlaced.png");
        derwentCollisionTexture = new Texture("textures/buildings/derwentCollision.png");
        derwentDraggingTexture = new Texture("textures/buildings/derwentDragging.png");

        goodrickePlacedTexture = new Texture("textures/buildings/derwentPlaced.png");
        goodrickeCollisionTexture = new Texture("textures/buildings/derwentCollision.png");
        goodrickeDraggingTexture = new Texture("textures/buildings/derwentDragging.png");

        constantinePlacedTexture = new Texture("textures/buildings/derwentPlaced.png");
        constantineCollisionTexture = new Texture("textures/buildings/derwentCollision.png");
        constantineDraggingTexture = new Texture("textures/buildings/derwentDragging.png");

        lectureHallPlacedTexture = new Texture("textures/buildings/lectureHallPlaced.png");
        lectureHallCollisionTexture = new Texture("textures/buildings/lectureHallCollision.png");
        lectureHallDraggingTexture = new Texture("textures/buildings/lectureHallDragging.png");

        piazzaPlacedTexture = new Texture("textures/buildings/lectureHallPlaced.png");
        piazzaCollisionTexture = new Texture("textures/buildings/lectureHallCollision.png");
        piazzaDraggingTexture = new Texture("textures/buildings/lectureHallDragging.png");

        centralHallPlacedTexture = new Texture("textures/buildings/lectureHallPlaced.png");
        centralHallCollisionTexture = new Texture("textures/buildings/lectureHallCollision.png");
        centralHallDraggingTexture = new Texture("textures/buildings/lectureHallDragging.png");

        libraryPlacedTexture = new Texture("textures/buildings/libraryPlaced.png");
        libraryCollisionTexture = new Texture("textures/buildings/libraryCollision.png");
        libraryDraggingTexture = new Texture("textures/buildings/libraryDragging.png");

        coursePlacedTexture = new Texture("textures/buildings/coursePlaced.png");
        courseCollisionTexture = new Texture("textures/buildings/courseCollision.png");
        courseDraggingTexture = new Texture("textures/buildings/courseDragging.png");

        softwareLabsPlacedTexture = new Texture("textures/buildings/coursePlaced.png");
        softwareLabsCollisionTexture = new Texture("textures/buildings/courseCollision.png");
        softwareLabsDraggingTexture = new Texture("textures/buildings/courseDragging.png");

        hardwareLabsPlacedTexture = new Texture("textures/buildings/coursePlaced.png");
        hardwareLabsCollisionTexture = new Texture("textures/buildings/courseCollision.png");
        hardwareLabsDraggingTexture = new Texture("textures/buildings/courseDragging.png");

        foodZonePlacedTexture = new Texture("textures/buildings/foodZonePlaced.png");
        foodZoneCollisionTexture = new Texture("textures/buildings/foodZoneCollision.png");
        foodZoneDraggingTexture = new Texture("textures/buildings/foodZoneDragging.png");

        nisaPlacedTexture = new Texture("textures/buildings/foodZonePlaced.png");
        nisaCollisionTexture = new Texture("textures/buildings/foodZoneCollision.png");
        nisaDraggingTexture = new Texture("textures/buildings/foodZoneDragging.png");

        greggsPlacedTexture = new Texture("textures/buildings/foodZonePlaced.png");
        greggsCollisionTexture = new Texture("textures/buildings/foodZoneCollision.png");
        greggsDraggingTexture = new Texture("textures/buildings/foodZoneDragging.png");

        derwentDiningPlacedTexture = new Texture("textures/buildings/foodZonePlaced.png");
        derwentDiningCollisionTexture = new Texture("textures/buildings/foodZoneCollision.png");
        derwentDiningDraggingTexture = new Texture("textures/buildings/foodZoneDragging.png");

        recreationalPlacedTexture = new Texture("textures/buildings/recreationalPlaced.png");
        recreationalCollisionTexture = new Texture("textures/buildings/recreationalCollision.png");
        recreationalDraggingTexture = new Texture("textures/buildings/recreationalDragging.png");

        naturePlacedTexture = new Texture("textures/buildings/naturePlaced.png");
        natureCollisionTexture = new Texture("textures/buildings/natureCollision.png");
        natureDraggingTexture = new Texture("textures/buildings/natureDragging.png");

        gymPlacedTexture = new Texture("textures/buildings/recreationalPlaced.png");
        gymCollisionTexture = new Texture("textures/buildings/recreationalCollision.png");
        gymDraggingTexture = new Texture("textures/buildings/recreationalDragging.png");

        societyBuildingPlacedTexture = new Texture("textures/buildings/recreationalPlaced.png");
        societyBuildingCollisionTexture = new Texture("textures/buildings/recreationalCollision.png");
        societyBuildingDraggingTexture = new Texture("textures/buildings/recreationalDragging.png");

        NPCTexture = new TextureRegion(new Texture("textures/NPC.png"));

    }
}
