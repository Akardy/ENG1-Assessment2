package com.badlogic.UniSim2.GUImanager;

import NPC.NPCManager;
import com.badlogic.UniSim2.Events.Achievements;
import com.badlogic.UniSim2.Events.Announcement;
import com.badlogic.UniSim2.Events.BrokenBuilding;
import com.badlogic.UniSim2.Events.BuildingMaintenance;
import com.badlogic.UniSim2.Events.Exam;
import com.badlogic.UniSim2.Main;
import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.mapmanager.Map;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.resources.SoundManager;
import com.badlogic.UniSim2.stats.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * This screen is used when the game is being played.
 */
public class GameScreen implements Screen {
    private Main game;
    private StretchViewport viewport;
    private Stage stage;

    private Timer timer;
    private Money money;
    private Satisfaction satisfaction;
    private NPCCount num;

    private NPCManager NPCManager;
    private BuildingCounts counts;

    private Exam examEvent;

    private Hud hud; // Used to make and display the game hud

    boolean isPaused = false;

    // This variable is needed to stop a crash from occuring when the game ends.
    boolean hasEnded = false;

    private int lastProcessedSecond;
    private int lastProcessedMinute;

    private Map map;

    private float scaleX;
    private float scaleY;

    private Announcement announcement;
    private Achievements achievements;
    private BrokenBuilding brokenBuildingEvent;
    private BuildingMaintenance buildingMaintenance;

    public GameScreen(Main game) {
        this.game = game;
        viewport = game.getViewport();
        this.counts = new BuildingCounts();
        timer = new Timer();
        money = new Money();
        satisfaction = new Satisfaction();
        num = new NPCCount();
        scaleX = viewport.getScreenWidth() / viewport.getWorldWidth();
        scaleY = viewport.getScreenHeight() / viewport.getWorldHeight();
        NPCManager = new NPCManager(scaleX, scaleY);
        map = new Map(game, counts, NPCManager, money, satisfaction, timer, scaleX, scaleY);
        hud = new Hud(game, timer, money, satisfaction, num, map.getBuildingManager(), counts, this);
        SoundManager.playMusic();
        announcement = new Announcement();
        examEvent = new Exam(timer, map.getBuildingManager(), satisfaction, announcement);
        achievements = new Achievements(announcement, map.getBuildingManager(), satisfaction, counts, money);
        brokenBuildingEvent = new BrokenBuilding(timer, map.getBuildingManager(), counts, announcement, money, satisfaction);
        buildingMaintenance = new BuildingMaintenance(timer, map.getBuildingManager(), money, satisfaction, announcement);
    }

    @Override
    public void show() {
        hud.activate();
    }

    @Override
    public void render(float delta) {
        input();
        update(delta);
        // update satisfaction decay
        if ((int) timer.getTimeLeft() % 60 == 0 && (int) timer.getTimeLeft() != lastProcessedMinute) {
            lastProcessedMinute = (int) timer.getTimeLeft();
            satisfaction.incrementDecay();

        }
        // update currency/satisfaction every ten seconds
        if (((int) timer.getTimeLeft()) % 10 == 0 && ((int) timer.getTimeLeft()) != (lastProcessedSecond)) {
            lastProcessedSecond = (int) timer.getTimeLeft();
            map.getBuildingManager().gainSatisfactionAndCurrency(((int) timer.getTimeLeft()) % 30 == 0);
            satisfaction.decay();
        }
        // show if there is an error message
        map.getBuildingManager().updateErrorLabel(delta);

        buildingMaintenance.render(delta);

        if (hasEnded)
            return;
        draw(delta);
        NPCManager.update(delta);
        Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(mousePos); // Convert screen coordinates to world coordinates

        Building hoveredBuilding = map.getBuildingManager().getHoveredBuilding(mousePos);



        if (hoveredBuilding != null) {
            map.getBuildingManager().displayBuildingStats(hoveredBuilding);
        } else {
            map.getBuildingManager().hideBuildingStats();
        }
    }

    /**
     * Processes input. Will pause/resume the game if the space is pressed.
     */
    private void input() {
        hud.input();
        map.input();


        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (isPaused) {
                isPaused = false;
                hud.resume();
            } else {
                isPaused = true;
                hud.pause();
            }
        }

    }

    /**
     * Will update the timer or not (depending on whether the game is paused)
     * and will end the game if the timer has reached its max time.
     */
    private void update(float delta) {
        if (!isPaused) {
            timer.update();
            examEvent.checkTriggeringEvent(delta);
            achievements.updateAchievements();
            announcement.update(delta);
            brokenBuildingEvent.checkTriggeringEvent();
            buildingMaintenance.checkMaintenance();
            if (timer.hasReachedMaxTime()) {
                game.endGame(satisfaction);
                hasEnded = true;
            }
        }
    }

    /**
     * Draws the game. This means drawing the game hud, building hud and game
     * map.
     */
    private void draw(float delta) {
        viewport.apply();
        ScreenUtils.clear(Consts.BACKGROUND_COLOR);
        map.draw();
        hud.draw();
        if(brokenBuildingEvent.doRender()) {
            brokenBuildingEvent.renderCross(delta);
        }
        examEvent.draw();
        announcement.draw();
    }

    @Override
    public void resize(int width, int height) {
        map.resize(width, height);
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        map.dispose();
        hud.dispose();
        buildingMaintenance.dispose();
    }

}
