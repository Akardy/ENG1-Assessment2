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

    public String getStats(){
        return "Building: " + getType() + "\nMoney earned: " + getMoneyGenerated() + "\nStudents: "
            + rooms + "\nIncome per student per 10s: " + incomePerStudent;
    }
}
