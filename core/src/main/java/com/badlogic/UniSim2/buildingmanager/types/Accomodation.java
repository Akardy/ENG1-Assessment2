package com.badlogic.UniSim2.buildingmanager.types;

import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.resources.*;
import com.badlogic.gdx.graphics.Texture;

/**
 * A building which represents student accommodation.
 * @see Building
 */
public class Accomodation extends Building{

    private final int rooms;
    private int studentsCont;
    private final int breakDownChance;
    private final float incomePerStudent;
    private final int costToFix;

    /**
     * Constructs a new Accomodation building.
     *
     * @param placedTexture      texture when the accommodation is placed on the map
     * @param collisionTexture   texture used when the accommodation collides with another object
     * @param draggingTexture    texture used when dragging the accommodation for placement
     * @param width              the width of the accommodation building
     * @param height             the height of the accommodation building
     * @param cost               the cost to place the accommodation
     * @param name               the name of the accommodation building
     * @param rooms              the number of rooms in the accommodation
     * @param type               the specific building type enum for accommodations
     * @param incomePerStudent   the income generated per student per 10 seconds
     * @param breakDownChance    the chance that the building may break down
     * @param costToFix          the cost to fix the building when broken
     */

    public Accomodation(Texture placedTexture, Texture collisionTexture, Texture draggingTexture,
                        int width, int height, float cost, String name, int rooms, BuildingTypes type,
                        float incomePerStudent, int breakDownChance, int costToFix) {
        super(
            placedTexture,
            collisionTexture,
            draggingTexture,
            width,
            height,
            cost,
            name,
            type,
            0
        );
        this.rooms = rooms;
        this.incomePerStudent = incomePerStudent;
        this.breakDownChance = breakDownChance;
        this.costToFix = costToFix;

    }


    public int getRooms(){
        return rooms;
    }
    public float getIncome(){
        return incomePerStudent;
    }
    /**
     * Returns a string containing detailed statistics about the accommodation building
     *
     * @return a formatted string with the accommodation's statistics.
     */
    public String getStats(){
        return "Building: " + getType() + "\nMoney earned: " + getMoneyGenerated() + "\nStudents: "
            + rooms + "\nIncome per student per 10s: " + incomePerStudent;
    }
}
