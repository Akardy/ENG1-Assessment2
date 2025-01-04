package com.badlogic.UniSim2.GUImanager;

import com.badlogic.UniSim2.Main;
import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * This screen should be shown when the user presses a button on the start menu.
 */
public class CreditsScreen implements Screen {
    private Main game;
    private StretchViewport viewport;
    private Stage stage;
    private Label credits;
    private final Skin skin;
    private ImageButton menuButton;

    SpriteBatch spriteBatch = new SpriteBatch();

    public CreditsScreen(Main game) {
        this.game = game;
        this.viewport = game.getViewport();
        this.stage = new Stage(this.viewport);
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        CreditsLabel();
        addMenuButton();
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

    // Adds the list of credits to the screen
    private void CreditsLabel() {
        // Initialize scoreLabel
        credits = new Label(
                "We used several 3rd-party libraries and assets to enhance functionality in the UniSim game system. Below is a list of these resources\n"
                        +
                        "LibGDX\n" +
                        "Gradle" +
                        "Assets: \n" +
                        "Pix2d - https://pix2d.com/\n" +
                        "Music - https://uppbeat.io/t/vocalista/quest-of-legends\n" +
                        "Click - https://uppbeat.io/sfx/click-soft-ui/7130/23080\n" +
                        "Lucidchart - https://www.lucidchart.com\n" +
                        "\nGame Developed by: Juliet Urquhart, Katie Schilling, Matias Duplock, Mohammed Elijack, Nora Wu and Theo Coleman",
                skin);
        credits.setFontScale(1);
        credits.setAlignment(Align.center);
        credits.setColor(Consts.TIMER_COLOR);

        // Position the label at the top center of the screen
        credits.setPosition(Consts.SCORE_LABEL_X, Consts.SCORE_LABEL_Y, Align.center);

        // Add the label to the stage
        stage.addActor(credits);
    }

    // Initializes menuButton
    private void setupStartMenuButton() {

        // Setting up the textures
        Drawable menuButtonDrawable = new TextureRegionDrawable(Assets.startMenuButtonTexture);
        ImageButton.ImageButtonStyle startButtonStyle = new ImageButton.ImageButtonStyle();
        startButtonStyle.up = menuButtonDrawable;
        startButtonStyle.down = menuButtonDrawable;
        startButtonStyle.over = menuButtonDrawable;

        // Initializing startButton and setting its size and position
        menuButton = new ImageButton(startButtonStyle);
        menuButton.setSize(Consts.TOP_LEFT_BUTTON_WIDTH, Consts.TOP_LEFT_BUTTON_HEIGHT);
        menuButton.setPosition(Consts.TOP_LEFT_BUTTON_X, Consts.TOP_LEFT_BUTTON_Y);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    // Draws the background of the credits menu
    private void drawBackground() {
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        viewport.apply();
        spriteBatch.begin();
        spriteBatch.draw(Assets.backgroundTexture, 0, 0, Consts.WORLD_WIDTH, Consts.WORLD_HEIGHT);
        spriteBatch.end();

    }

    @Override
    public void render(float delta) {
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
