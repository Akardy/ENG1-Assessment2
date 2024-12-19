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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.*;
import java.util.List;

/**
 * This class is used to manage all of the placed {@link Building buildings}
 * on the map as well as a single selectedBuilding.
 */
public class BuildingManager {

    private List<Building> buildings;

    private Array<Building> placed; // Array of all the buildings on the map in order of when placed

    private Building currentBuilding; // References the building currently selected

    private boolean currentlySelecting; // True when a building is selected and being being dragged

    private BuildingCounts buildingCounts;

    private NPCManager npcManager;

    private Money money;
    private Satisfaction satisfaction;
    private Timer timer;

    private Label statsLabel;

    private Stage stage;

    public BuildingManager(BuildingCounts buildingCounts, NPCManager npcManager, Money money, Satisfaction satisfaction, Timer timer) {
        placed = new Array<>();
        currentBuilding = null;
        currentlySelecting = false;
        this.buildingCounts = buildingCounts;
        this.npcManager = npcManager;
        this.money = money;
        this.satisfaction = satisfaction;
        this.timer = timer;
        this.stage = new Stage();
        initialiseStatsLabel();
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
        } else {
            if(clicked) {
                for (Building building : placed) {
                    if (building.getBoundingRectangle().contains(mousePos)) {
                        currentBuilding = building;  // Select the building under the mouse pos
                        currentBuilding.selectBuilding();
                        currentlySelecting = true;
                        break; // Stop once a building is selected
                    }
                }
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
            // System.out.println("handlePlacing called for: " + currentBuilding.getType());

            if (!isColliding(currentBuilding) && money.getMoney() > currentBuilding.getCost()) {

                money.reduceMoney(currentBuilding.getCost());


                currentBuilding.placeBuilding(); // Place building

                // Update counts for the building type

                // Update aggregate count for Accomodation
                if (currentBuilding instanceof Accomodation) {
                    buildingCounts.incrementAccomadation(currentBuilding.getType().ordinal());
                    int npcCount = ((Accomodation) currentBuilding).getRooms() / 50;
                    NPCManager.addNPC(npcCount);
                }

                if (currentBuilding instanceof FoodZone) {
                    buildingCounts.incrementFoodZones(currentBuilding.getType().ordinal());
                }

                if (currentBuilding instanceof Recreational) {
                    buildingCounts.incrementRecreational(currentBuilding.getType().ordinal());
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
                currentBuilding = null;
                currentlySelecting = false;
            }

            placingInProgress = false; // Reset the guard
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
        addBuilding(currentBuilding); // Adds currentBuilding to the buildings array and to collidableSprites array
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
                    Consts.LECTUREHALL_WIDTH, Consts.LECTUREHALL_HEIGHT, 2500, "Piazza", BuildingTypes.PIAZZA, 200, 0.001f);
                break;
            case CENTRALHALL:
                currentBuilding = new LectureHall(Assets.goodrickePlacedTexture, Assets.goodrickeCollisionTexture, Assets.goodrickeDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 4000, "Central Hall", BuildingTypes.CENTRALHALL, 400, 0.0012f);
                break;
            case SOFTWARELABS:
                currentBuilding = new Labs(Assets.goodrickePlacedTexture, Assets.goodrickeCollisionTexture, Assets.goodrickeDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 2000, "Software labs", BuildingTypes.SOFTWARELABS, 100, 0.005f);
                break;
            case HARDWARELABS:
                currentBuilding = new Labs(Assets.goodrickePlacedTexture, Assets.goodrickeCollisionTexture, Assets.goodrickeDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 3500, "Hardware Labs", BuildingTypes.HARDWARELABS, 150, 0.006f);
                break;
            case NISA:
                currentBuilding = new FoodZone(Assets.goodrickePlacedTexture, Assets.goodrickeCollisionTexture, Assets.goodrickeDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 1000, "Nisa", BuildingTypes.NISA, 200, 2, 0.0005f);
                break;
            case GREGGS:
                currentBuilding = new FoodZone(Assets.goodrickePlacedTexture, Assets.goodrickeCollisionTexture, Assets.goodrickeDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 2000, "Greggs", BuildingTypes.GREGGS, 300, 2.5f, 0.0008f);
                break;
            case DERWENTDINING:
                currentBuilding = new FoodZone(Assets.goodrickePlacedTexture, Assets.goodrickeCollisionTexture, Assets.goodrickeDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 3000, "Derwent Dining", BuildingTypes.DERWENTDINING, 500, 3.2f, 0.001f);
                break;
            case NATURE:
                currentBuilding = new Recreational(Assets.goodrickePlacedTexture, Assets.goodrickeCollisionTexture, Assets.goodrickeDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 1000, "Nature", BuildingTypes.NATURE, 100, 0.05f);
                break;
            case GYM:
                currentBuilding = new Recreational(Assets.goodrickePlacedTexture, Assets.goodrickeCollisionTexture, Assets.goodrickeDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 2000, "Gym", BuildingTypes.GYM, 150, 0.07f);
                break;
            case SOCIETYBUILDING:
                currentBuilding = new Recreational(Assets.goodrickePlacedTexture, Assets.goodrickeCollisionTexture, Assets.goodrickeDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 3000, "Society Building", BuildingTypes.SOCIETYBUILDING, 250, 0.01f);
                break;
            case LIBRARY:
                currentBuilding = new Library(Assets.goodrickePlacedTexture, Assets.goodrickeCollisionTexture, Assets.goodrickeDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 3500, "Library", BuildingTypes.LIBRARY);
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
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        statsLabel = new Label("", skin);
        statsLabel.setVisible(false);
        //statsLabel.setPosition(10, Gdx.graphics.getHeight() - 50);
        statsLabel.setSize(300, 100);
        statsLabel.setFontScale(1.2f);
        statsLabel.setWrap(true);
        stage.addActor(statsLabel);
    }


    public Building getHoveredBuilding(Vector2 mousePos) { // see if a building is being hovered over
        for (Building building : placed) {
            if (building.getBoundingRectangle().contains(mousePos)) {
                return building;
            }
        }
        return null;
    }
    public void displayBuildingStats(Building building, Viewport viewport) {
        String stats = building.getStats();
        statsLabel.setText(stats);
        statsLabel.setVisible(true);

        // Position the label just above the building
        //Vector2 buildingWorldPos = new Vector2(building.getX(), building.getY());
        Vector3 screenPos = viewport.project(new Vector3(building.getX(), building.getY(), 0));
        float labelX = screenPos.x + (building.getWidth() / 2) - (statsLabel.getPrefWidth() / 2);
        float labelY = screenPos.y + building.getHeight() + 10; // Slight offset above the building
        statsLabel.setPosition(labelX, labelY);
    }
    public void hideBuildingStats(){
        statsLabel.setVisible(false);
    }

    public void gainSatisfactionAndCurrency(boolean isThirtySeconds){ // TODO: recreational do not have their distance boost yet
        float satisfactionGain = 0f;
        float currencyGain = 0f;
        int totalCapacity = 0;
        int totalRooms = 0;
        int libraryCount = 0;
        for (Building building : placed){
            if(building.getType() != BuildingTypes.LIBRARY ){
                totalCapacity += building.getCapacity();
                totalRooms += building.getRooms();
            } else {
                libraryCount += 1;}

        }
        float studentFillBuildingPercent = Math.min(1, (float) totalRooms / totalCapacity);
        for (Building building : placed){
            int studentsInBuilding = (int) (building.getCapacity() * studentFillBuildingPercent);
            building.setHowFull(studentsInBuilding);
            if(building.getType() == BuildingTypes.DERWENT || building.getType() == BuildingTypes.GOODRICKE || building.getType() == BuildingTypes.CONSTANTINE) { // TODO: change to accom
                currencyGain = building.getRooms() * building.getIncome();
                building.updateMoneyGenerated(currencyGain);
            }
            if(building.getType() == BuildingTypes.PIAZZA || building.getType() == BuildingTypes.CENTRALHALL) { // TODO: change to lecture
                satisfactionGain = studentsInBuilding * building.getSatisfaction() * (1 + (libraryCount * building.getMultiplierEffect()));
                building.updateSatisfactionGenerated(satisfactionGain);
            }
            if ((building.getType() == BuildingTypes.SOFTWARELABS || building.getType() == BuildingTypes.HARDWARELABS) && isThirtySeconds){ // TODO: Change to labs
                satisfactionGain = studentsInBuilding * building.getSatisfaction();
                building.updateSatisfactionGenerated(satisfactionGain);
            }
            if (building.getType() != BuildingTypes.LIBRARY){
                currencyGain = studentsInBuilding * building.getIncome();
                satisfactionGain = studentsInBuilding * building.getSatisfaction();
                building.updateMoneyGenerated(currencyGain);
                building.updateSatisfactionGenerated(satisfactionGain);
            }
            satisfaction.increaseSatis(satisfactionGain);
            money.increaseMoney(currencyGain);
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
}
