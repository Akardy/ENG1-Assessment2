package com.badlogic.UniSim2.buildingmanager;

import NPC.NPCManager;
import com.badlogic.UniSim2.GUImanager.BuildingMenu;
import com.badlogic.UniSim2.stats.Money;
import com.badlogic.UniSim2.buildingmanager.types.*;
import com.badlogic.UniSim2.mapmanager.Map;
import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.stats.BuildingCounts;
import com.badlogic.UniSim2.stats.Satisfaction;
import com.badlogic.UniSim2.stats.Timer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.*;
import java.util.List;

/**
 * This class is used to manage all of the placed {@link Building buildings}
 * on the map as well as a single selectedBuilding.
 */
public class BuildingManager {

    private Array<Building> placed; // Array of all the buildings on the map in order of when placed

    private Building currentBuilding; // References the building currently selected

    private boolean currentlySelecting; // True when a building is selected and being being dragged

    private BuildingCounts buildingCounts;

    private NPCManager npcManager;

    private float libraryMultiplier;

    private Money money;
    private Satisfaction satisfaction;
    private Timer timer;

    private Label statsLabel;


    private Stage stage;

    private float scaleX;
    private float scaleY;

    private Skin skin;

    private Label errorLabel;
    private float errorTimer = 0; // Timer for hiding the label



    public BuildingManager(BuildingCounts buildingCounts, NPCManager npcManager, Money money, Satisfaction satisfaction, Timer timer, float scaleX, float scaleY) {
        placed = new Array<>();
        currentBuilding = null;
        currentlySelecting = false;
        this.buildingCounts = buildingCounts;
        this.npcManager = npcManager;
        this.money = money;
        this.satisfaction = satisfaction;
        this.timer = timer;
        this.stage = new Stage();
        libraryMultiplier = 1.2f;
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        initialiseUI();

    }

    public void initialiseUI(){
        initialiseStatsLabel();
        initialiseErrorLabel();
    }
    public void initialiseErrorLabel() {
        // Create the error label
        errorLabel = new Label("", skin); // Initially empty
        errorLabel.setColor(Color.RED); // Red text for error
        errorLabel.setFontScale(4); // Increase size
        errorLabel.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - 50); // Top center
        errorLabel.setVisible(false); // Hidden by default
        stage.addActor(errorLabel); // Add to the stage
    }
    public void showError(String message) {
        errorLabel.setText(message); // Set the message text
        errorLabel.setVisible(true); // Show the label
        errorTimer = 2.0f; // Display for 2 seconds
    }

    /**
     * Adds currentBuilding to the buildings array and to collidableSprites array.
     *
     * @param building The building to add.
     */
    private void addBuilding(Building building) {
        placed.add(building);
        Map.collidableSprites.add(building);
    }

    /**
     * Used to determine what to do when the mouse moves or clicks.
     *
     * @param mousePos The position of the mouse in world coordinates.
     * @param clicked  true if a click has happened and false if not.
     */
    public void input(Vector2 mousePos, boolean clicked, boolean backspacePressed) {
        // If we're currently selecting a building
        if (currentlySelecting) {
            if (clicked) {
                handlePlacing(); // Place the building in the location of the click
            } else if (backspacePressed) {
                removeBuilding();
            }
            else{
                handleDragging(mousePos); // Otherwise continue dragging the building
            }
        }

    }

    private void removeBuilding() {
        placed.removeValue(currentBuilding, true);
        Map.collidableSprites.removeValue(currentBuilding, true);
        currentBuilding = null;
        currentlySelecting = false;
    }

    /**
     * Used to place a building in a location. The {@link #currentBuilding} holds
     * the location where it should be placed.
     */

    private boolean placingInProgress = false; // Guard to prevent duplicate calls

    private void handlePlacing() {
        if (currentlySelecting && currentBuilding != null && !placingInProgress) {
            placingInProgress = true; // Prevent duplicate execution

            if (!isColliding(currentBuilding) && money.getMoney() > currentBuilding.getCost()) {

                money.reduceMoney(currentBuilding.getCost());


                currentBuilding.placeBuilding(); // Place building

                // Update counts for the building type

                // Update aggregate count for Accomodation
                if (currentBuilding instanceof Accomodation) {
                    buildingCounts.incrementAccomadation(currentBuilding.getType().ordinal());
                    // add NPCs (one NPC per 50 students)
                    int npcCount = ((Accomodation) currentBuilding).getRooms() / 50;
                    NPCManager.addNPC(npcCount);
                    // checks for new discount Rates on recreational buildings
                    for (Building building: placed){
                        if (building.getType() == BuildingTypes.NATURE || building.getType() == BuildingTypes.GYM || building.getType() == BuildingTypes.SOCIETYBUILDING){ // TODO: Change to accomodation
                            building.calculateDiscountRate(currentBuilding.getX(), currentBuilding.getY());
                        }
                    }
                }

                if (currentBuilding instanceof FoodZone) {
                    buildingCounts.incrementFoodZones(currentBuilding.getType().ordinal());
                }

                if (currentBuilding instanceof Recreational) {
                    buildingCounts.incrementRecreational(currentBuilding.getType().ordinal());
                    // calculate discount rate
                    for (Building building: placed){
                        if (building.getType() == BuildingTypes.DERWENT || building.getType() == BuildingTypes.GOODRICKE || building.getType() == BuildingTypes.CONSTANTINE){ // TODO: Change to accomodation
                            ((Recreational) currentBuilding).calculateDiscountRate(building.getX(), building.getY());
                        }
                    }

                }

                if (currentBuilding instanceof LectureHall) {
                    buildingCounts.incrementLectureHall(currentBuilding.getType().ordinal());
                }

                if (currentBuilding instanceof Labs) {
                    buildingCounts.incrementLabs(currentBuilding.getType().ordinal());
                }

                if (currentBuilding instanceof Library){
                    buildingCounts.incrementLibary(currentBuilding.getType().ordinal());
                }

                BuildingMenu.updateCountLabel(currentBuilding);

                // Reset current building and selection state
                addBuilding(currentBuilding); // Adds currentBuilding to the buildings array and to collidableSprites array
                currentBuilding = null;
                currentlySelecting = false;
            }
            else if (!isColliding(currentBuilding) && money.getMoney() < currentBuilding.getCost()) {
                showError("Cannot afford building");
                currentBuilding = null;
                currentlySelecting = false;
            }


            placingInProgress = false; // Reset the guard
        }

    }
    public void updateErrorLabel(float delta) {
        // Hide error label after timer expires
        if (errorTimer > 0) {
            errorTimer -= delta;
            if (errorTimer <= 0) {
                errorLabel.setVisible(false);
            }
        }
    }


    /**
     * Called when a building button has been pressed. Deals with placing a new
     * building
     * corresponding to the button pressed determined with type
     *
     * @param type The type of the building which the building button relates to.
     */
    public void handleSelection(BuildingTypes type) {

        handleType(type); // Sets current building to the type of building selected
        currentlySelecting = true; // Sets currently selecting to true as we have selected a building to drag and
                                   // place
        currentBuilding.selectBuilding(); // Selects building
    }

    /**
     * Creates a new building based on the type.
     *
     * @param type The type of the building to create.
     */
    private void handleType(BuildingTypes type) {
        switch (type) {
            case DERWENT:
                currentBuilding = new Accomodation(Assets.derwentPlacedTexture, Assets.derwentCollisionTexture, Assets.derwentDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 2500F, "Derwent", 250, BuildingTypes.DERWENT, 1f, 5, 250);
                break;
            case CONSTANTINE:
                currentBuilding = new Accomodation(Assets.constantinePlacedTexture, Assets.constantineCollisionTexture, Assets.constantineDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 6000, "Constantine", 600, BuildingTypes.CONSTANTINE, 1.2f, 3, 250);
                break;
            case GOODRICKE:
                currentBuilding = new Accomodation(Assets.goodrickePlacedTexture, Assets.goodrickeCollisionTexture, Assets.goodrickeDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 4000, "Goodricke", 400, BuildingTypes.GOODRICKE, 1.5f, 4, 250);
                break;
            case PIAZZA:
                currentBuilding = new LectureHall(Assets.piazzaPlacedTexture, Assets.piazzaCollisionTexture, Assets.piazzaDraggingTexture,
                    Consts.LECTUREHALL_WIDTH, Consts.LECTUREHALL_HEIGHT, 2500, "Piazza", BuildingTypes.PIAZZA, 200, 0.001f, 100);
                break;
            case CENTRALHALL:
                currentBuilding = new LectureHall(Assets.centralHallPlacedTexture, Assets.centralHallCollisionTexture, Assets.centralHallDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 4000, "Central Hall", BuildingTypes.CENTRALHALL, 400, 0.0012f, 100);
                break;
            case SOFTWARELABS:
                currentBuilding = new Labs(Assets.softwareLabsPlacedTexture, Assets.softwareLabsCollisionTexture, Assets.softwareLabsDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 2000, "Software labs", BuildingTypes.SOFTWARELABS, 100, 0.005f, 100);
                break;
            case HARDWARELABS:
                currentBuilding = new Labs(Assets.hardwareLabsPlacedTexture, Assets.hardwareLabsCollisionTexture, Assets.hardwareLabsDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 3500, "Hardware Labs", BuildingTypes.HARDWARELABS, 150, 0.006f, 100);
                break;
            case NISA:
                currentBuilding = new FoodZone(Assets.nisaPlacedTexture, Assets.nisaCollisionTexture, Assets.nisaDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 1000, "Nisa", BuildingTypes.NISA, 200, 2, 0.0005f);
                break;
            case GREGGS:
                currentBuilding = new FoodZone(Assets.greggsPlacedTexture, Assets.greggsCollisionTexture, Assets.greggsDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 2000, "Greggs", BuildingTypes.GREGGS, 300, 2.5f, 0.0008f);
                break;
            case DERWENTDINING:
                currentBuilding = new FoodZone(Assets.derwentDiningPlacedTexture, Assets.derwentDiningCollisionTexture, Assets.derwentDiningDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 3000, "Derwent Dining", BuildingTypes.DERWENTDINING, 500, 3.2f, 0.001f);
                break;
            case NATURE:
                currentBuilding = new Recreational(Assets.naturePlacedTexture, Assets.natureCollisionTexture, Assets.natureDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 1000, "Nature", BuildingTypes.NATURE, 100, 0.05f);
                break;
            case GYM:
                currentBuilding = new Recreational(Assets.gymPlacedTexture, Assets.gymCollisionTexture, Assets.gymDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 2000, "Gym", BuildingTypes.GYM, 150, 0.07f);
                break;
            case SOCIETYBUILDING:
                currentBuilding = new Recreational(Assets.societyBuildingPlacedTexture, Assets.societyBuildingCollisionTexture, Assets.societyBuildingDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 3000, "Society Building", BuildingTypes.SOCIETYBUILDING, 250, 0.01f);
                break;
            case LIBRARY:
                currentBuilding = new Library(Assets.libraryPlacedTexture, Assets.libraryCollisionTexture, Assets.libraryDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 3500, "Library", BuildingTypes.LIBRARY, 100);
                break;
            default:
                throw new IllegalArgumentException("Unhandled BuildingType: " + type);
        }
    }

    /**
     * Updates the position of the currently selected building to the mouse pos and
     * changes
     * the texture of the building depending on whether it is colliding with
     * another building.
     *
     * @param mousPos The position of the mouse if world coords.
     */
    private void handleDragging(Vector2 mousPos) {
        boolean colliding = isColliding(currentBuilding);
        currentBuilding.handleDragging(mousPos, colliding);
    }

    /**
     * Checks whether a building is colliding with anything in
     * {@link Map#collidableSprites}.
     *
     * @param building The building to check.
     * @return true if the building is colliding with something and false otherwise.
     */
    private boolean isColliding(Building building) {

        // For all sprites that are collidable
        for (Sprite collidableSprite : Map.collidableSprites) {
            // Check if the building is overlapping with any
            boolean overlaps = building.getBoundingRectangle().overlaps(collidableSprite.getBoundingRectangle());
            // If so then return true
            if (!building.equals(collidableSprite) && overlaps) {
                return true;
            }
        }
        // If not colliding return false
        return false;
    }

    /**
     * Draws all the buildings and clamps them to ensure they cannot go outside
     * of the map boundaries. Will also draw the {@link #currentBuilding}
     * on top of any placed buildings.
     *
     * @param spriteBatch
     */


    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        for (Building building : placed) {
            building.clampPosition(); // Ensures the buildings cannot be outside the map boundaries
            if (!building.equals(currentBuilding)) {
                building.draw(spriteBatch);
            }
        }
        // Draws currentBuilding on top of every other building
        if (currentBuilding != null) {
            currentBuilding.draw(spriteBatch);
        }
        spriteBatch.end();

        stage.act();
        stage.draw();
    }



    private void initialiseStatsLabel() {
        statsLabel = new Label("", skin);
        statsLabel.setVisible(false);
        statsLabel.getStyle().fontColor = Color.BLACK;
        statsLabel.setSize(400 * scaleX, 70 * scaleY);
        statsLabel.setFontScale(1.5f);
        statsLabel.setAlignment(Align.center);
        statsLabel.setWrap(true);
        statsLabel.getStyle().background = createBackgroundColor(Color.LIGHT_GRAY);

        stage.addActor(statsLabel);
    }
    private TextureRegionDrawable createBackgroundColor(Color color) {
        // Create a Pixmap for the background
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();

        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        return new TextureRegionDrawable(texture);
    }


    public Building getHoveredBuilding(Vector2 mousePos) { // see if a building is being hovered over
        for (Building building : placed) {
            if (building.getBoundingRectangle().contains(mousePos)) {
                return building;
            }
        }
        return null;
    }


    public void displayBuildingStats(Building building) { // TODO: THIS NEEDS TO BE DISPLAYED AFTER NPCs - they go over
        String stats = building.getStats();
        statsLabel.setText(stats); // Update label text
        float padding = 1.1f; // Padding around text
        GlyphLayout layout = new GlyphLayout(statsLabel.getStyle().font, stats); // Used to calculate text dimensions


        // Set the label size based on the longest line width and height with padding
        statsLabel.setSize((layout.width) * padding, statsLabel.getPrefHeight() * padding);

        Vector2 screenPos = new Vector2(building.getX(), building.getY());
        float labelX = (float) ((screenPos.x * scaleX)+ (building.getWidth() / 2 * scaleX));
        float labelY = (float) ((screenPos.y * scaleY) + building.getHeight() * scaleY);
        labelX = Math.min(labelX, Gdx.graphics.getWidth() - (layout.width * scaleX));
        labelY = Math.min(labelY, Gdx.graphics.getHeight() - (statsLabel.getHeight() * scaleY));

        statsLabel.setPosition(labelX, labelY);
        statsLabel.setVisible(true);
    }

    public void hideBuildingStats(){
        statsLabel.setVisible(false);
    }

    public void gainSatisfactionAndCurrency(boolean isThirtySeconds){ //
        float satisfactionGain = 0f;
        float currencyGain = 0f;
        int totalCapacity = 0;
        int totalRooms = 0;
        int libraryCount = 0;
        // count Buildings
        for (Building building : placed){
            if(building.getType() != BuildingTypes.LIBRARY ){
                totalCapacity += building.getCapacity();
                totalRooms += building.getRooms();
            } else {
                libraryCount += 1;}

        }
        // Update currency/stats from buildings
        float libraryGain = 0;
        float studentFillBuildingPercent = Math.min(1, (float) totalRooms / totalCapacity);
        for (Building building : placed){
            if (!building.isBroken()) {
                int studentsInBuilding = (int) (building.getCapacity() * studentFillBuildingPercent);
                building.setHowFull(studentsInBuilding);
                if (building instanceof Accomodation) {
                    currencyGain = building.getRooms() * building.getIncome();
                    building.updateMoneyGenerated(currencyGain);
                } else if (building instanceof LectureHall) {
                    satisfactionGain = studentsInBuilding * building.getSatisfaction() * (libraryCount * libraryMultiplier);
                    libraryGain += studentsInBuilding * building.getSatisfaction() * (libraryCount * (libraryMultiplier - 1));
                    building.updateSatisfactionGenerated(satisfactionGain);
                } else if (building instanceof Labs && isThirtySeconds) {
                    satisfactionGain = studentsInBuilding * building.getSatisfaction();
                    building.updateSatisfactionGenerated(satisfactionGain);
                } else if (building.getType() != BuildingTypes.LIBRARY) {
                    currencyGain = studentsInBuilding * building.getIncome();
                    satisfactionGain = studentsInBuilding * building.getSatisfaction();
                    building.updateMoneyGenerated(currencyGain);
                    building.updateSatisfactionGenerated(satisfactionGain);
                }
                satisfaction.increaseSatis(satisfactionGain);
                money.increaseMoney(currencyGain);
            }
        }
        // calculate how much satisfaction gained through libraries
        float libraryGainPer = libraryGain / libraryCount;
        for (Building building: placed){
            if (building.getType() == BuildingTypes.LIBRARY){
                building.updateSatisfactionGenerated(libraryGainPer);
            }
        }


    }


    /**
     * @return true if a building is currently being selected and false otherwise.
     */
    public boolean getCurrentlySelecting() {
        return currentlySelecting;
    }

    /**
     * Calls {@link Building#dispose()} on each building this stores.
     */
    public void dispose() {
        for (Building building : placed) {
            building.dispose();
        }
    }

    public Array<Building> getPlaced() {
        return placed;
    }
}
