package com.badlogic.UniSim2.Events;

import com.badlogic.UniSim2.GUImanager.BuildingMenu;
import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.buildingmanager.BuildingManager;
import com.badlogic.UniSim2.buildingmanager.types.BuildingTypes;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.stats.BuildingCounts;
import com.badlogic.UniSim2.stats.Timer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class BrokenBuilding {

    private Random random;
    private ShapeRenderer shapeRenderer;

    private float goOfSeconds;
    private boolean hasGoneOf;
    private Timer timer;
    private boolean renderLine;

    private BuildingManager buildingManager;
    private float x, y, width, height;
    private int indexOfBuilding;

    private float scalex;
    private float scaley;

    private BuildingCounts buildingCounts;


    public BrokenBuilding(Timer timer, BuildingManager buildingManager, BuildingCounts buildingCounts) {
        shapeRenderer = new ShapeRenderer();
        selectTimeGoOf();
        hasGoneOf = false;
        renderLine = false;
        this.timer = timer;
        this.buildingManager = buildingManager;
        random = new Random();

        scalex = (float) Gdx.graphics.getWidth() / Consts.WORLD_WIDTH;
        scaley = (float) Gdx.graphics.getHeight() / Consts.WORLD_HEIGHT;

        this.buildingCounts = buildingCounts;
    }

    private void selectTimeGoOf() {
        //assert random != null;
        //goOfSeconds = random.nextInt((int)Consts.STARTING_SECONDS - 20 + 1) + 20;
        goOfSeconds = 5;
    }

    public void checkTriggeringEvent(){
        if ((int) timer.getElapsedTime() == goOfSeconds && !hasGoneOf) {
            hasGoneOf = true;
            startEvent();
        }
    }

    public void renderCross(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);

        float thickness = 10;

        shapeRenderer.rectLine(x * scalex, (y + height) * scaley, (x + width) * scalex, y * scaley, thickness);

        shapeRenderer.rectLine(x * scalex, y * scaley, (x + width) * scalex, (y + height) * scaley, thickness);

        shapeRenderer.end();
    }

    public boolean doRender(){
        return renderLine;
    }

    private void startEvent() {
        pickBuilding();
        renderLine = true;
        disableBuilding();
        addDecayAndMoney();
    }

    private void pickBuilding(){
        indexOfBuilding = random.nextInt(buildingManager.getPlaced().size);

        x = buildingManager.getPlaced().get(indexOfBuilding).getX();
        y = buildingManager.getPlaced().get(indexOfBuilding).getY();
        width = buildingManager.getPlaced().get(indexOfBuilding).getWidth();
        height = buildingManager.getPlaced().get(indexOfBuilding).getHeight();
    }

    private void disableBuilding(){
        Building disabledBuilding = buildingManager.getPlaced().get(indexOfBuilding);
        BuildingTypes type = disabledBuilding.getType();
        switch (type){
            case CONSTANTINE:
            case DERWENT:
            case GOODRICKE:
                buildingCounts.decrementAccomadation(type.ordinal());
                break;
            case GREGGS:
            case DERWENTDINING:
            case NISA:
                buildingCounts.decrementFoodZone(type.ordinal());
                break;
            case GYM:
            case SOCIETYBUILDING:
            case NATURE:
                buildingCounts.decrementRecreational(type.ordinal());
                break;
            case PIAZZA:
            case CENTRALHALL:
                buildingCounts.decrementLectureHall(type.ordinal());
                break;
            case SOFTWARELABS:
            case HARDWARELABS:
                buildingCounts.decrementLabs(type.ordinal());
                break;
            case LIBRARY:
                buildingCounts.decrementLibary(type.ordinal());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        BuildingMenu.updateCountLabel(disabledBuilding);
        disabledBuilding.setBroken(true);
    }

    private void addDecayAndMoney(){

    }
}
