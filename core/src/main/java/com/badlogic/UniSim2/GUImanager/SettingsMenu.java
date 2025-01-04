package com.badlogic.UniSim2.GUImanager;

import com.badlogic.UniSim2.Main;
import com.badlogic.UniSim2.resources.Assets;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.resources.SoundManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;




/**
 * This screen should be shown when the user presses a button on the pause menu.
 */
public class SettingsMenu implements Screen {
    private Main game;
    private StretchViewport viewport;
    private Stage stage;
    private SoundManager music;
    private final Skin skin;
    private ImageButton resumeButton;
    private GameScreen gameScreen;
    private GameMenu gameMenu;
    private StartScreen startScreen;
    private ImageButton res720Button;
    private ImageButton res1080Button;
    private ImageButton res1440Button;

    SpriteBatch spriteBatch = new SpriteBatch();

    public SettingsMenu(Main game, GameScreen gameScreen, GameMenu gameMenu) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.gameMenu = gameMenu;
        this.viewport = game.getViewport();
        this.stage = new Stage(this.viewport);
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        addResumeButton();
        musicSlider();
        screenSize();

    }

    public SettingsMenu(Main game, StartScreen startScreen) {
        this.game = game;
        this.startScreen = startScreen;
        this.viewport = game.getViewport();
        this.stage = new Stage(this.viewport);
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        addResumeButton();
        musicSlider();
        screenSize();
    }

    /**
     * Adds a menu button to the screen.
     */
    private void addResumeButton() {
        setupResumeButton(); // Initializes resumeMenuButton with the correct textures, size, and position
        addResumeClick(); // Adds a click listener to resume button
    }

    /**
     * Ensures that when the resume button is pressed, the game is restarts.
     */
    private void addResumeClick() {
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameScreen != null) {
                    game.returnToGameSettings(gameScreen, gameMenu);
                } else if (startScreen != null) {
                    game.returnToStartSettings(startScreen);
                }
            }
        });
        stage.addActor(resumeButton);
    }

    // Initializes resumeButton
    private void setupResumeButton() {

        // Setting up the textures
        Drawable resumeButtonDrawable = new TextureRegionDrawable(Assets.resumeButtonTexture);
        ImageButton.ImageButtonStyle resumeButtonStyle = new ImageButton.ImageButtonStyle();
        resumeButtonStyle.up = resumeButtonDrawable;
        resumeButtonStyle.down = resumeButtonDrawable;
        resumeButtonStyle.over = resumeButtonDrawable;

        // Initializing resumeButton and setting its size and position
        resumeButton = new ImageButton(resumeButtonStyle);
        resumeButton.setSize(Consts.TOP_LEFT_BUTTON_WIDTH, Consts.TOP_LEFT_BUTTON_HEIGHT);
        resumeButton.setPosition(Consts.TOP_LEFT_BUTTON_X, Consts.TOP_LEFT_BUTTON_Y);
    }

    public void musicSlider() {
        // Music volume slider
        Label musicLabel = new Label("Music Volume", skin);
        musicLabel.setFontScale(3);
        musicLabel.setAlignment(Align.center);
        musicLabel.setColor(Consts.TIMER_COLOR);

        // Position the label at the top center of the screen
        musicLabel.setPosition(Consts.MUSIC_SLIDER_LABEL_X, Consts.MUSIC_SLIDER_LABEL_Y, Align.center);

        // Add the label to the stage
        stage.addActor(musicLabel);

        Slider musicSlider = new Slider(0, 1, 0.01f, false, skin);
        musicSlider.setValue(music.getVolume()); // Initialize slider with current volume
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                music.setVolume(musicSlider.getValue()); // Adjust volume
            }
        });

        // Position the slider below the label
        musicSlider.setPosition(Consts.MUSIC_SLIDER_X, Consts.MUSIC_SLIDER_Y, Align.center);

        // Add the slider to the stage
        stage.addActor(musicSlider);
    }

    /**
     * Buttons for changing the screen size
     */
    private void screenSize() {

        // Resolution buttons
        Drawable res720ButtonDrawable = new TextureRegionDrawable(Assets.res720ButtonTexture);
        ImageButton.ImageButtonStyle res720ButtonStyle = new ImageButton.ImageButtonStyle();
        res720ButtonStyle.up = res720ButtonDrawable;
        res720ButtonStyle.down = res720ButtonDrawable;
        res720ButtonStyle.over = res720ButtonDrawable;
        res720Button = new ImageButton(res720ButtonStyle);
        res720Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.graphics.setWindowedMode(1280, 720);  // Set resolution
            }
        });

        Drawable res1080ButtonDrawable = new TextureRegionDrawable(Assets.res1080ButtonTexture);
        ImageButton.ImageButtonStyle res1080ButtonStyle = new ImageButton.ImageButtonStyle();
        res1080ButtonStyle.up = res1080ButtonDrawable;
        res1080ButtonStyle.down = res1080ButtonDrawable;
        res1080ButtonStyle.over = res1080ButtonDrawable;
        res1080Button = new ImageButton(res1080ButtonStyle);
        res1080Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.graphics.setWindowedMode(1920, 1080);
            }
        });

        Drawable res1440ButtonDrawable = new TextureRegionDrawable(Assets.res1440ButtonTexture);
        ImageButton.ImageButtonStyle res1440ButtonStyle = new ImageButton.ImageButtonStyle();
        res1440ButtonStyle.up = res1440ButtonDrawable;
        res1440ButtonStyle.down = res1440ButtonDrawable;
        res1440ButtonStyle.over = res1440ButtonDrawable;
        res1440Button = new ImageButton(res1440ButtonStyle);
        res1440Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.graphics.setWindowedMode(2560, 1440);
            }
        });


        Label resolutionLabel = new Label("Resolution", skin);
        resolutionLabel.setFontScale(3);
        resolutionLabel.setAlignment(Align.center);
        resolutionLabel.setColor(Consts.TIMER_COLOR);

        // Positioning and adding buttons to the stage
        res720Button.setSize(Consts.RES720_WIDTH, Consts.RES720_HEIGHT);
        res720Button.setPosition(Consts.RES720_X, Consts.RES720_Y);
        res1080Button.setSize(Consts.RES1080_WIDTH, Consts.RES1080_HEIGHT);
        res1080Button.setPosition(Consts.RES1080_X, Consts.RES1080_Y);
        res1440Button.setSize(Consts.RES1440_WIDTH, Consts.RES1440_HEIGHT);
        res1440Button.setPosition(Consts.RES1440_X, Consts.RES1440_Y);
        resolutionLabel.setPosition(Consts.RES_LABEL_X, Consts.RES_LABEL_Y);

        stage.addActor(res720Button);
        stage.addActor(res1080Button);
        stage.addActor(res1440Button);
        stage.addActor(resolutionLabel);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    // Draws the background of the settings menu
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
