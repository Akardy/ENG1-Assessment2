package com.badlogic.UniSim2.GUImanager;

import NPC.NPCManager;
import com.badlogic.UniSim2.Main;
import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.mapmanager.Map;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.resources.SoundManager;
import com.badlogic.UniSim2.stats.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * This screen is used when the game is being played.
 */
public class GameScreen implements Screen {
    private Main game;
    private StretchViewport viewport;

    private Timer timer;
    private Money money;
    private Satisfaction satisfaction;
    private NPCCount num;

    private NPCManager NPCManager;
    private BuildingCounts counts;

    private GameMenu menu; // Used to make and display the game menu

    boolean isPaused = false;

    // This variable is needed to stop a crash from occuring when the game ends.
    boolean hasEnded = false;

    private int lastProcessedSecond;
    private int lastProcessedMinute;

    private Map map;
    public GameScreen(Main game){
        this.game = game;
        viewport = game.getViewport();
        this.counts = new BuildingCounts();
        timer = new Timer();
        money = new Money();
        satisfaction = new Satisfaction();
        num = new NPCCount();
        NPCManager = new NPCManager();
        map = new Map(game, counts, NPCManager, money, satisfaction, timer);
        menu = new GameMenu(game, timer, money, satisfaction, num, map.getBuildingManager(), counts);
        SoundManager.playMusic();

    }

    @Override
    public void show() {
        menu.activate();
    }

    @Override
    public void render(float delta) {
        input();
        update();
        if(((int)timer.getElapsedTime()) % 10 == 0 && ((int)timer.getElapsedTime()) != (lastProcessedSecond)){
            lastProcessedSecond = (int)timer.getElapsedTime();
            map.getBuildingManager().gainSatisfactionAndCurrency(((int)timer.getElapsedTime()) % 30 == 0);
            satisfaction.decay();

        }
        if((int)timer.getElapsedTime() % 60 == 0 && (int)timer.getElapsedTime() != lastProcessedMinute){
            lastProcessedMinute = (int)timer.getElapsedTime();
            satisfaction.incrementDecay();

        }

        if (hasEnded) return;
        draw();
        NPCManager.update(delta);

    }

    /**
     * Processes input. Will pause/resume the game if the space is pressed.
     */
    private void input() {
        menu.input();
        map.input();
        Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(mousePos); // Convert screen coordinates to world coordinates

        Building hoveredBuilding = map.getBuildingManager().getHoveredBuilding(mousePos);

        if (hoveredBuilding != null) {
            map.getBuildingManager().displayBuildingStats(hoveredBuilding);
        } else {
            map.getBuildingManager().hideBuildingStats();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            if (isPaused) {
                isPaused = false;
                menu.resume();
            }
            else {
                isPaused = true;
                menu.pause();
            }
        }

    }

    /**
     * Will update the timer or not (depending on whether the game is paused)
     * and will end the game if the timer has reached its max time.
     */
    private void update() {
        if (isPaused == false) {
            timer.update();
            if (timer.hasReachedMaxTime()) {
                game.endGame();
                hasEnded = true;
            }
        }
    }

    /**
     * Draws the game. This means drawing the game menu, building menu and game
     * map.
     */
    private void draw() {
        viewport.apply();
        ScreenUtils.clear(Consts.BACKGROUND_COLOR);
        map.draw();
        menu.draw();
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
        menu.dispose();
    }
}
