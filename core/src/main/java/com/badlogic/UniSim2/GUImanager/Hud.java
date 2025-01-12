package com.badlogic.UniSim2.GUImanager;

import com.badlogic.UniSim2.Main;
import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.buildingmanager.BuildingManager;
import com.badlogic.UniSim2.buildingmanager.types.Accomodation;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.resources.SoundManager;
import com.badlogic.UniSim2.stats.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

/**
 * This is the game menu that is shown by the {@link GameScreen}. It contains
 * the {@link Timer timer} for the game and {@link BuildingMenu the building
 * menu}
 * which can be used to place new buildings.
 */
public class Hud {
    private Stage stage;
    private Main game;
    private final Skin skin;
    private BuildingMenu buildingMenu;
    private Timer timer;
    private Money money;
    private Satisfaction satisfaction;
    private Label timerLabel;
    private Label moneyLabel;
    private Label satisLabel;
    private Label studentLabel;
    private Label pauseLabel;
    private boolean isPaused;
    private Window popupWindow;
    private GameScreen gameScreen;
    private boolean hasStudentChanged;
    private BuildingManager buildingManager;

    public Hud(Main game, Timer timer, Money money, Satisfaction satisfaction,
               BuildingManager buildings, BuildingCounts counts, GameScreen gameScreen) {
        stage = new Stage(game.getViewport());
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        buildingMenu = new BuildingMenu(stage, buildings, counts);
        this.game = game;
        this.timer = timer;
        this.money = money;
        this.satisfaction = satisfaction;
        isPaused = false;
        this.gameScreen = gameScreen;
        hasStudentChanged = false;
        buildingManager = buildings;
        createMenu();
    }

    /**
     * Activates the input processor needed for the menu to use inputs.
     */
    public void activate() {
        Gdx.input.setInputProcessor(stage);
    }

    private void createMenu() {
        buildingMenu.createBuildingMenu();
        createTimerLabel();
        createMoneyLabel();
        createSatisLabel();
        createPauseLabel();
        createStudentLabel();
    }

    // Adds a label at the top of the screen displaying the time
    private void createTimerLabel() {

        // Initialize timerLabel
        timerLabel = new Label("00:00", skin);
        timerLabel.setFontScale(3);
        timerLabel.setAlignment(Align.center);
        timerLabel.setColor(Consts.TIMER_COLOR);

        // Position the label at the top center of the screen
        timerLabel.setPosition(Consts.TIMER_X, Consts.TIMER_Y, Align.center);

        // Add the label to the stage
        stage.addActor(timerLabel);
    }

    /**
     * Updates the time shown on the label to the elapsed time got from
     * the timer.
     */
    private void updateTimerLabel() {
        float elapsedTime = timer.getTimeLeft();
        int minutes = (int) (elapsedTime / 60);
        int seconds = (int) (elapsedTime % 60);
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    /**
     * Should be called when the game is paused.
     */
    public void pause() {
        timerLabel.setText("PAUSED");
        isPaused = true;
        pausePopup();
    }

    /**
     * Should be called when the game is resumed.
     */
    public void resume() {
        updateTimerLabel();
        isPaused = false;
        popupWindow.remove();
    }

    /**
     * A popup pause menu
     */
    private void pausePopup() {
        popupWindow = new Window("Pause", skin);

        popupWindow.setSize(400, 300);
        popupWindow.setPosition(600, 250);
        popupWindow.setMovable(true);

        popupWindow.getTitleTable().padTop(20).center();

        TextButton menuButton = new TextButton("Main Menu", skin);

        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundManager.stopMusic();
                game.create();
                popupWindow.remove();
                dispose();
            }
        });

        popupWindow.row();
        popupWindow.add(menuButton).pad(10).fillX();

        TextButton settingsButton = new TextButton("Settings Menu", skin);

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.settingsFromGame(gameScreen, Hud.this);
                popupWindow.remove();
            }
        });

        popupWindow.row();
        popupWindow.add(settingsButton).pad(10).fillX();

        stage.addActor(popupWindow);
    }

    // Adds a label at the top right of the screen displaying the money stat
    private void createMoneyLabel() {

        // Initialize timerLabel
        moneyLabel = new Label("Money: £0.00", skin);
        moneyLabel.setFontScale(2);
        moneyLabel.setAlignment(Align.right);
        moneyLabel.setColor(Consts.MONEY_COLOR);

        // Position the label at the top center of the screen
        moneyLabel.setPosition(Consts.MONEY_X + 10, Consts.MONEY_Y, Align.center);

        // Add the label to the stage
        stage.addActor(moneyLabel);
    }

    /**
     * Updates the money shown on the label to the amount got from
     * the money variable class.
     */
    private void updateMoneyLabel() {
        float updatedMoney = money.getMoney();
        int pounds = (int) (updatedMoney);
        int pence = (int) ((updatedMoney - ((int) updatedMoney)) * 100);
        moneyLabel.setText(String.format("Money: £" + "%02d.%02d", pounds, pence));
    }

    // Adds a label at the top right of the screen displaying the satisfaction stat
    private void createSatisLabel() {

        // Initialize satisLabel
        satisLabel = new Label("Satisfaction: 0%", skin);
        satisLabel.setFontScale(2);
        satisLabel.setAlignment(Align.right);
        satisLabel.setColor(Consts.SATIS_COLOR);

        // Position the label at the top center of the screen
        satisLabel.setPosition(Consts.SATIS_X - 10, Consts.SATIS_Y, Align.center);

        // Add the label to the stage
        stage.addActor(satisLabel);
    }

    /**
     * Updates the satisfaction level shown on the label to the value got
     * from the satisfaction variable class.
     */
    private void updateSatisLabel() {
        float updatedSatis = satisfaction.getSatis();
        satisLabel.setText(String.format("Satisfaction: %s", updatedSatis + "%"));
    }

    // Adds a label at the bottom right of the screen displaying the number of
    // students
    private void createStudentLabel() {

        // Initialize studentLabel
        studentLabel = new Label("Students: 0", skin);
        studentLabel.setFontScale(2);
        studentLabel.setAlignment(Align.right);
        studentLabel.setColor(Consts.NUM_COLOR);

        // Position the label at the top center of the screen
        studentLabel.setPosition(Consts.NUM_X, Consts.NUM_Y, Align.center);

        // Add the label to the stage
        stage.addActor(studentLabel);
    }

    /**
     * Updates the number of stuents shown on the label to the value got
     * from the NPCCount variable class.
     */
    private void updateStudentLabel() {
        if (hasStudentChanged && !buildingManager.getCurrentlySelecting()){
            int updatedStudent = calculateStudents();
            studentLabel.setText("Students: " + updatedStudent);
        }
    }

    private int calculateStudents() {
        int amount = 0;
        for(Building building : buildingManager.getPlaced()){
            if(building instanceof Accomodation){
                amount += ((Accomodation)building).getRooms();
            }
        }
        return amount;
    }

    // Adds a label at the bottom right of the screen displaying the pause
    // instructions
    private void createPauseLabel() {

        // Initialize satisLabel
        pauseLabel = new Label("press esc to pause", skin);
        pauseLabel.setFontScale(2);
        pauseLabel.setAlignment(Align.center);
        pauseLabel.setColor(Consts.PAUSE_COLOR);

        // Position the label at the top center of the screen
        pauseLabel.setPosition(Consts.PAUSE_X, Consts.PAUSE_Y, Align.center);

        // Add the label to the stage
        stage.addActor(pauseLabel);
    }

    /**
     * Processes any input.
     */
    public void input() {
        stage.act(Gdx.graphics.getDeltaTime());
    }

    /**
     * Updates and draws the menu.
     */
    public void draw() {
        if (!isPaused) {
            if(buildingManager.getCurrentlySelecting() && !hasStudentChanged){
                hasStudentChanged = true;
            }
            updateTimerLabel();
            updateMoneyLabel();
            updateSatisLabel();
            updateStudentLabel();
        }
        buildingMenu.draw();
        stage.draw();
    }

    /**
     * @return true if the menu is paused and false if not.
     */
    public boolean getPaused() {
        return isPaused;
    }

    // Getter for stage
    public Stage getStage() {
        return stage;
    }

    /**
     * Gets rid the all textures. This method should be called when the menu is
     * not going to be used anymore.
     */
    public void dispose() {
        buildingMenu.dispose();
        stage.dispose();
        skin.dispose();
    }
}
