package com.badlogic.UniSim2.buildingmanager.types;

import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.resources.*;
import com.badlogic.gdx.graphics.Texture;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A building which represents a recreational building where students can have
 * fun.
 * @see Building
 */
public class Recreational extends Building{



    private final int capacity;
    private float satisfactionPerStudent;
    private final float originSatisfactionPerStudent;
    private float bestDiscountRate;
    private final int maxCellDistance;



    public Recreational(Texture placedTexture, Texture collisionTexture, Texture draggingTexture,
                        int width, int height, float cost, String name, BuildingTypes type, int capacity,
                        float satisfactionPerStudent) {
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
        this.capacity = capacity;
        this.originSatisfactionPerStudent = satisfactionPerStudent;
        this.bestDiscountRate = 1f;
        maxCellDistance = 68;
        this.satisfactionPerStudent = satisfactionPerStudent;


    }
    public void calculateDiscountRate(float buildingX, float buildingY){
        float distX = (float) StrictMath.pow((buildingX - this.getX()), 2);
        float distY = (float) StrictMath.pow((buildingY - this.getY()), 2);
        float realCellDist = ((float) StrictMath.pow(distX + distY, 0.5) / Consts.CELL_SIZE) - 4; // the four offsets the actual building itself
        float discountRate = (float) StrictMath.pow(realCellDist / maxCellDistance, 2); // square to add quadratic delay - i.e. so its not linear
        if(discountRate < bestDiscountRate){
            this.bestDiscountRate = discountRate;
            this.satisfactionPerStudent = (1 - discountRate) * originSatisfactionPerStudent;
        }


    }
    public int getCapacity(){
        return capacity;
    }
    public float getSatisfaction(){
        return satisfactionPerStudent;
    }

    public float getBestDiscountRate(){ return bestDiscountRate;}


    public String getStats(){
        return "Building: " + getType() + "\nAccomodation Proximity Discount: -" + roundToSignificantFigures(bestDiscountRate, 3) + "%" +
            "\nSatisfaction earned: " + getSatisfactionGenerated() + "%" +
            "\nCapacity: " + capacity + "\nHow many students use: " + getHowFull() +
            "\nOriginal satisfaction per student per 10s: " + originSatisfactionPerStudent +
            "\nSatisfaction per student per 10s: " + roundToSignificantFigures(satisfactionPerStudent, 3);
    }
}
