package com.badlogic.UniSim2.GUImanager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.UniSim2.Main;
import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.Align;

/**
 * This screen should be shown when the game ends.
 */
public class EndScreen implements Screen {
    private Main game;
    private StretchViewport viewport;
    private Stage stage;
    private Label scoreLabel;
    private final Skin skin;
    private int score;
    private ImageButton menuButton;

    SpriteBatch spriteBatch = new SpriteBatch();

    public EndScreen(Main game, int score) {
        this.game = game;
        this.viewport = game.getViewport();
        this.stage = new Stage(this.viewport);
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.score = score;
        createScoreLabel();
        addMenuButton();
    }

    // Adds a label to the middle of the screen displaying the score that the player
    // managed to get throughout the game.
    private void createScoreLabel() {
        // Initialize scoreLabel
        scoreLabel = new Label("Score : " + score, skin);
        scoreLabel.setFontScale(3);
        scoreLabel.setAlignment(Align.center);
        scoreLabel.setColor(Consts.TIMER_COLOR);

        // Position the label at the top center of the screen
        scoreLabel.setPosition(Consts.SCORE_LABEL_X, Consts.SCORE_LABEL_Y, Align.center);

        // Add the label to the stage
        stage.addActor(scoreLabel);
    }

    // Initializes menuButton
    private void setupStartMenuButton() {

        // Setting up the textures
        Drawable menuButtonDrawable = new TextureRegionDrawable(Assets.startMenuButtonTexture);
        ImageButton.ImageButtonStyle startButtonStyle = new ImageButton.ImageButtonStyle();
        startButtonStyle.up = menuButtonDrawable;
        startButtonStyle.down = menuButtonDrawable;
        startButtonStyle.over = menuButtonDrawable;

        // Initializing menuButton and setting its size and position
        menuButton = new ImageButton(startButtonStyle);
        menuButton.setSize(Consts.TOP_LEFT_BUTTON_WIDTH, Consts.TOP_LEFT_BUTTON_HEIGHT);
        menuButton.setPosition(Consts.TOP_LEFT_BUTTON_X, Consts.TOP_LEFT_BUTTON_Y);
    }

    /**
     * Adds a menu button to the screen.
     */
    private void addMenuButton() {
        setupStartMenuButton(); // Initializes startMenuButton with the correct textures, size, and position
        addStartMenuButtonClick(); // Adds a click listener to menu button
    }

    /**
     * Ensures that when the menu button is pressed, returns to the main menu.
     */
    private void addStartMenuButtonClick() {
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.create();
                dispose();
            }
        });
        stage.addActor(menuButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    // Draws the background of the start menu
    private void drawBackground() {
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(Assets.startBackgroundTexture, 0, 0, Consts.WORLD_WIDTH, Consts.WORLD_HEIGHT);
        spriteBatch.end();

    }

    @Override
    public void render(float delta) {
        viewport.apply();
        ScreenUtils.clear(Consts.BACKGROUND_COLOR);
        drawBackground();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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
        stage.dispose();
        skin.dispose();
    }
}
