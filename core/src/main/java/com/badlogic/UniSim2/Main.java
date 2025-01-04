package com.badlogic.UniSim2;

import com.badlogic.UniSim2.GUImanager.*;
import com.badlogic.UniSim2.resources.*;
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
    private GameMenu gameMenu;

    @Override
    public void create() {
        Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 4,
                Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 4);
        Assets.loadTextures();
        startScreen = new StartScreen(this);
        setScreen(startScreen);
    }

    public StretchViewport getViewport() {
        return viewport;
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
    public void settingsFromGame(GameScreen screen, GameMenu gameMenu) {
        settingsScreen = new SettingsMenu(this, screen, gameMenu);
        setScreen(settingsScreen);
        gameScreen.dispose();
    }

    /**
     * Displays the settings screen by setting the screen to
     * {@link #settingsScreen}.
     */
    public void settingsFromStart(StartScreen screen) {
        settingsScreen = new SettingsMenu(this, screen);
        setScreen(settingsScreen);
        startScreen.dispose();
    }


    /**
     * Resumes the game by setting the screen to the {@link #gameScreen}. Should be
     * called by the {@link GameScreen} when the resume button is clicked.
     */
     public void returnToGame(GameScreen gameScreen, GameMenu menu) {
         setScreen(gameScreen);
         menu.pause();
         Gdx.input.setInputProcessor(menu.getStage());
         settingsScreen.dispose();
     }

    /**
     * Resumes the game by setting the screen to the {@link #gameScreen}. Should be
     * called by the {@link StartScreen} when the resume button is clicked.
     */
    public void returnToStart(StartScreen startScreen) {
        setScreen(startScreen);
        Gdx.input.setInputProcessor(startScreen.getStage());
        settingsScreen.dispose();
    }
}
