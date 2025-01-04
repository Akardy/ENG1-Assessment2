package com.badlogic.UniSim2;

import java.util.Arrays;
import java.util.List;

import com.badlogic.UniSim2.GUImanager.CreditsScreen;
import com.badlogic.UniSim2.GUImanager.EndScreen;
import com.badlogic.UniSim2.GUImanager.GameScreen;
import com.badlogic.UniSim2.GUImanager.SettingsMenu;
import com.badlogic.UniSim2.GUImanager.StartScreen;
import com.badlogic.UniSim2.resources.*;
import com.badlogic.UniSim2.resources.TaskGenerator.TaskGroup;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends Game {

    private StretchViewport viewport = new StretchViewport(Consts.WORLD_WIDTH, Consts.WORLD_HEIGHT);

    private StartScreen startScreen;
    private GameScreen gameScreen;
    private EndScreen endScreen;
    private CreditsScreen creditsScreen;
    private SettingsMenu settingsScreen;
    List<TaskGroup> gameTasks;

    @Override
    public void create() {
        Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 4,
                Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 4);
        Assets.loadTextures();
        List<String> tasksList = Arrays.asList(
            "Task 1",
            "Task 2",
            "Task 3",
            "Task 4",
            "Task 5",
            "Task 6"
        );
        
        TaskGenerator generator = new TaskGenerator(tasksList);
        gameTasks = generator.generateTasks(5); 
        startScreen = new StartScreen(this);
        setScreen(startScreen);
        
    }

    public StretchViewport getViewport() {
        return viewport;
    }

    public List<TaskGroup> getGameTasks() {
        return gameTasks;  
    }

    @Override
    public void render() {
        super.render();
    }

    /**
     * Starts the game by setting the screen to the {@link #gameScreen}. Should be
     * called by the {@link StartScreen} when the start button is clicked.
     */
    public void startGame() {
        gameScreen = new GameScreen(this);
        setScreen(gameScreen);
        startScreen.dispose();
       
    }

    /**
     * Ends the game by settings the screen to {@link #endScreen}. Should be called
     * by
     * the {@link GameScreen} when the timer ends.
     */
    public void endGame() {
        endScreen = new EndScreen(this, 0);
        setScreen(endScreen);
        gameScreen.dispose();
    }

    /**
     * Displays the credits by setting the screen to {@link #creditsScreen}.
     */
    public void viewCredits() {
        creditsScreen = new CreditsScreen(this);
        setScreen(creditsScreen);
        startScreen.dispose();
    }

    /**
     * Displays the settings screen by setting the screen to
     * {@link #settingsScreen}.
     */
    public void settings(String screen) {
        settingsScreen = new SettingsMenu(this, screen);
        setScreen(settingsScreen);
        if (screen == "start") {
            startScreen.dispose();
        } else {
            gameScreen.dispose();
        }
    }

    /**
     * Resumes the game by setting the screen to the {@link #gameScreen}. Should be
     * called by the {@link StartScreen} when the resume button is clicked.
     */
    public void returnToGame() {
        // gameScreen = new GameScreen(this);
        setScreen(gameScreen);
        settingsScreen.dispose();
    }
}
