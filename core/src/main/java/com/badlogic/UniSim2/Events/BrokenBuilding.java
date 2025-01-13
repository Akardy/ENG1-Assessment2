package com.badlogic.UniSim2.Events;

import com.badlogic.UniSim2.GUImanager.BuildingMenu;
import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.buildingmanager.BuildingManager;
import com.badlogic.UniSim2.buildingmanager.types.BuildingTypes;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.stats.BuildingCounts;
import com.badlogic.UniSim2.stats.Money;
import com.badlogic.UniSim2.stats.Satisfaction;
import com.badlogic.UniSim2.stats.Timer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

import java.util.Random;

public class BrokenBuilding {

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
    private Announcement announcement;
    private Building disabledBuilding;
    private Money money;

    private Label prompt;
    private Label satTickPrompt;
    private Skin skin;

    private float satTickPromptTimer;
    private float brokenBuildingTickTimer;
    private final float TICK_TIME = 5;

    private Satisfaction satisfaction;

    /**
     * Constructs a BrokenBuilding event.
     *
     * @param timer           the Timer tracking game time.
     * @param buildingManager the manager for handling buildings.
     * @param buildingCounts  the counts of various buildings.
     * @param announcement    the Announcement system for displaying messages.
     * @param money           the Money object for managing currency.
     * @param satisfaction    the Satisfaction object to adjust player satisfaction.
     */
    public BrokenBuilding(Timer timer, BuildingManager buildingManager, BuildingCounts buildingCounts,
                          Announcement announcement, Money money, Satisfaction satisfaction, ShapeRenderer shapeRenderer,
                          Skin skin) {
        this.shapeRenderer = shapeRenderer;
        selectTimeGoOf();
        hasGoneOf = false;
        renderLine = false;
        this.timer = timer;
        this.buildingManager = buildingManager;
        this.skin = skin;

        scalex = (float) Gdx.graphics.getWidth() / Consts.WORLD_WIDTH;
        scaley = (float) Gdx.graphics.getHeight() / Consts.WORLD_HEIGHT;

        this.buildingCounts = buildingCounts;
        this.announcement = announcement;
        this.money = money;
        brokenBuildingTickTimer = TICK_TIME;
        satTickPromptTimer = TICK_TIME / 2;
        this.satisfaction = satisfaction;

        prompt = new Label("", skin);
        prompt.setFontScale(2);
        prompt.setPosition((x * scalex) + 10, (y * scaley) + 65);
        prompt.setVisible(true);
        announcement.addNewLabel(prompt);
    }
    /**
     * Selects a random time 20 seconds onwards, in seconds, when the building will break
     */
    private void selectTimeGoOf() {
        Random random = new Random();
        goOfSeconds = random.nextInt(280) + 20;
    }
    /**
     * Checks if it's time to trigger the broken building event, if so initiates
     * the event.
     */
    public void checkTriggeringEvent(){
        if ((int) timer.getElapsedTime() == goOfSeconds && !hasGoneOf) {
            hasGoneOf = true;
            if (!buildingManager.getPlaced().isEmpty()) {
                startEvent();
            }
        }
    }
    /**
     * Renders "cross" for the broken building, handles user input
     * to fix the building, and applies satisfaction decay over time.
     *
     * @param delta the time elapsed since the last frame.
     */

    public void renderCross(float delta){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);

        float thickness = 10;

        shapeRenderer.rectLine(x * scalex, (y + height) * scaley, (x + width) * scalex, y * scaley, thickness);

        shapeRenderer.rectLine(x * scalex, y * scaley, (x + width) * scalex, (y + height) * scaley, thickness);

        shapeRenderer.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.F)){
            endEvent();
        }

        if(brokenBuildingTickTimer > 0){
            brokenBuildingTickTimer -= delta;
        }else {
            satDecay();
            brokenBuildingTickTimer = TICK_TIME;
        }

        if(satTickPrompt.isVisible() && satTickPromptTimer > 0){
            satTickPromptTimer -= delta;
        }else if(satTickPrompt.isVisible()){
            satTickPrompt.setVisible(false);
            satTickPromptTimer = TICK_TIME /2;
        }

    }
    /**
     * Indicates whether the broken building event's "cross" should be rendered.
     *
     * @return true if the broken building "cross" should be rendered, false otherwise.
     */
    public boolean doRender(){
        return renderLine;
    }
    /**
     * Initiates the broken building event, selecting a building to break, disabling
     * it, and setting up UI prompts.
     */
    private void startEvent() {
        pickBuilding();
        renderLine = true;
        disableBuilding();
        announcement.showAnnouncement("Building Broke!!");

        prompt = new Label("Press F\nto fix\nBuilding", skin);
        prompt.setFontScale(2);
        prompt.setPosition((x * scalex) + 10, (y * scaley) + 65);
        prompt.setVisible(true);
        announcement.addNewLabel(prompt);

        satTickPrompt = new Label("-2% Satisfaction" , skin);
        satTickPrompt.setFontScale(2);
        satTickPrompt.setAlignment(Align.bottomLeft);
        satTickPrompt.setPosition(x * scalex, (y * scaley) + height + 50);
        satTickPrompt.setColor(Color.RED);
        satTickPrompt.setVisible(false);
        announcement.addNewLabel(satTickPrompt);
    }
    /**
     * Randomly selects a building from the placed buildings to be broken.
     */
    private void pickBuilding(){
        Random random = new Random();
        indexOfBuilding = random.nextInt(buildingManager.getPlaced().size);

        x = buildingManager.getPlaced().get(indexOfBuilding).getX();
        y = buildingManager.getPlaced().get(indexOfBuilding).getY();
        width = buildingManager.getPlaced().get(indexOfBuilding).getWidth();
        height = buildingManager.getPlaced().get(indexOfBuilding).getHeight();
    }
    /**
     * Disables the selected building, updates building counts, and marks it as broken.
     */
    private void disableBuilding(){
        disabledBuilding = buildingManager.getPlaced().get(indexOfBuilding);
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
    /**
     * Applies satisfaction decay over time if the building does not get fixed.
     */
    private void satDecay(){
        satisfaction.decreaseSatis(Consts.SAT_DECAY_FROM_BROKEN_BUILDING);
        satTickPrompt.setVisible(true);
    }
    /**
     * Ends the broken building event by attempting to fix the building if
     * the player can afford the repair cost, or displaying an error otherwise.
     */
    private void endEvent(){
        if(money.reduceMoney(Consts.FIX_BUILDING_COST)){
            announcement.showAnnouncement("Fixed Building!\n-£" + Consts.FIX_BUILDING_COST);
            enableBuilding();
            renderLine = false;
            prompt.remove();
            satTickPrompt.remove();
        }else{
            buildingManager.showError("Can't afford to fix the Building!");
        }
    }
    /**
     * Re-enables the previously broken building, updates building counts, and
     * marks it as no longer broken.
     */
    private void enableBuilding(){
        BuildingTypes type = disabledBuilding.getType();
        switch (type){
            case CONSTANTINE:
            case DERWENT:
            case GOODRICKE:
                buildingCounts.incrementAccomadation(type.ordinal());
                break;
            case GREGGS:
            case DERWENTDINING:
            case NISA:
                buildingCounts.incrementFoodZones(type.ordinal());
                break;
            case GYM:
            case SOCIETYBUILDING:
            case NATURE:
                buildingCounts.incrementRecreational(type.ordinal());
                break;
            case PIAZZA:
            case CENTRALHALL:
                buildingCounts.incrementLectureHall(type.ordinal());
                break;
            case SOFTWARELABS:
            case HARDWARELABS:
                buildingCounts.incrementLabs(type.ordinal());
                break;
            case LIBRARY:
                buildingCounts.incrementLibary(type.ordinal());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        BuildingMenu.updateCountLabel(disabledBuilding);
        disabledBuilding.setBroken(false);
    }

    public float getGoOfSeconds(){
        return goOfSeconds;
    }

    public boolean getHasGoneOff(){
        return hasGoneOf;
    }
}
