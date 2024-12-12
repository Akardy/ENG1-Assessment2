package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.UniSim2.GUImanager.BuildingMenu;
import com.badlogic.UniSim2.buildingmanager.types.*;
import com.badlogic.UniSim2.mapmanager.Map;
import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.stats.BuildingCounts;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

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

    public BuildingManager(BuildingCounts buildingCounts) {
        placed = new Array<>();
        currentBuilding = null;
        currentlySelecting = false;
        this.buildingCounts = buildingCounts;
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

            if (!isColliding(currentBuilding)) {
                currentBuilding.placeBuilding(); // Place building

                // Update counts for the building type

                // Update aggregate count for Accomodation
                if (currentBuilding instanceof Accomodation) {
                    buildingCounts.incrementAccomadation(currentBuilding.getType().ordinal());
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
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 100000, "Derwent", 100, BuildingTypes.DERWENT);
                break;
            case CONSTANTINE:
                currentBuilding = new Accomodation(Assets.constantinePlacedTexture, Assets.constantineCollisionTexture, Assets.constantineDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 100000, "Constantine", 100, BuildingTypes.CONSTANTINE);
                break;
            case GOODRICKE:
                currentBuilding = new Accomodation(Assets.goodrickePlacedTexture, Assets.goodrickeCollisionTexture, Assets.goodrickeDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 100000, "Goodricke", 100, BuildingTypes.GOODRICKE);
                break;
            case PIAZZA:
                currentBuilding = new LectureHall(Assets.piazzaPlacedTexture, Assets.piazzaCollisionTexture, Assets.piazzaDraggingTexture,
                    Consts.LECTUREHALL_WIDTH, Consts.LECTUREHALL_HEIGHT, 10000000, "Piazza", BuildingTypes.PIAZZA);
                break;
            case CENTRALHALL:
                currentBuilding = new LectureHall(Assets.goodrickePlacedTexture, Assets.goodrickeCollisionTexture, Assets.goodrickeDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 100000, "Goodricke", BuildingTypes.CENTRALHALL);
                break;
            case SOFTWARELABS:
                currentBuilding = new Labs(Assets.goodrickePlacedTexture, Assets.goodrickeCollisionTexture, Assets.goodrickeDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 100000, "Goodricke", BuildingTypes.SOFTWARELABS);
                break;
            case HARDWARELABS:
                currentBuilding = new Labs(Assets.goodrickePlacedTexture, Assets.goodrickeCollisionTexture, Assets.goodrickeDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 100000, "Goodricke", BuildingTypes.HARDWARELABS);
                break;
            case NISA:
                currentBuilding = new FoodZone(Assets.goodrickePlacedTexture, Assets.goodrickeCollisionTexture, Assets.goodrickeDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 100000, "Goodricke", 100, 100,  BuildingTypes.NISA);
                break;
            case GREGGS:
                currentBuilding = new FoodZone(Assets.goodrickePlacedTexture, Assets.goodrickeCollisionTexture, Assets.goodrickeDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 100000, "Goodricke", 100, 100,  BuildingTypes.GREGGS);
                break;
            case DERWENTDINING:
                currentBuilding = new FoodZone(Assets.goodrickePlacedTexture, Assets.goodrickeCollisionTexture, Assets.goodrickeDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 100000, "Goodricke", 100, 100,  BuildingTypes.DERWENTDINING);
                break;
            case NATURE:
                currentBuilding = new Recreational(Assets.goodrickePlacedTexture, Assets.goodrickeCollisionTexture, Assets.goodrickeDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 100000, "Goodricke", 100, BuildingTypes.NATURE);
                break;
            case GYM:
                currentBuilding = new Recreational(Assets.goodrickePlacedTexture, Assets.goodrickeCollisionTexture, Assets.goodrickeDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 100000, "Goodricke", 100, BuildingTypes.GYM);
                break;
            case SOCIETYBUILDING:
                currentBuilding = new Recreational(Assets.goodrickePlacedTexture, Assets.goodrickeCollisionTexture, Assets.goodrickeDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 100000, "Goodricke", 100, BuildingTypes.SOCIETYBUILDING);
                break;
            case LIBRARY:
                currentBuilding = new Library(Assets.goodrickePlacedTexture, Assets.goodrickeCollisionTexture, Assets.goodrickeDraggingTexture,
                    Consts.ACCOMODATION_WIDTH, Consts.ACCOMODATION_HEIGHT, 100000, "Goodricke", BuildingTypes.LIBRARY);
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
