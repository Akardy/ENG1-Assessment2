package com.badlogic.UniSim2.GUImanager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.buildingmanager.BuildingManager;
import com.badlogic.UniSim2.resources.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

public class BuildingMenu {

    private Stage stage;
    private BuildingManager buildings;
    private Image menuBar;
    private final Skin skin;

    public static int[] buildingCounts;
    private static Array<Label> countLabels;

    // The main building types to show initially
    private final Building.BuildingTypes[] mainClasses = {
            Building.BuildingTypes.Accomodation,
            Building.BuildingTypes.LectureHall,
            Building.BuildingTypes.Library,
            Building.BuildingTypes.Course,
            Building.BuildingTypes.FoodZone,
            Building.BuildingTypes.Recreational
    };

    private Table menuTable;

    public BuildingMenu(Stage stage, BuildingManager buildings) {
        this.stage = stage;
        Gdx.input.setInputProcessor(stage);
        this.buildings = buildings;

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        // Initialize buildingCounts (adjust the size if there are more types)
        buildingCounts = new int[20];
        countLabels = new Array<>();

        // Create a table to hold the menu items
        menuTable = new Table();
        // menuTable.setFillParent(true);
        menuTable.setDebug(false); // Set to true to see layout boundariess

        menuTable.setPosition(Consts.MENU_BAR_X, Consts.MENU_BAR_Y);
        menuTable.setSize(Consts.MENU_BAR_WIDTH, Consts.MENU_BAR_HEIGHT);

        stage.addActor(menuTable);
    }

    public void createBuildingMenu() {
        createMenuBar();
        showMainOptions();
        menuTable.toFront();
    }

    private void showMainOptions() {
        menuTable.clear();
        countLabels.clear(); // Reset count labels array

        // Add a title row for clarity (optional)
        // Label titleLabel = new Label("Main Building Types", skin);
        // menuTable.add(titleLabel).colspan(2).center().pad(10);
        // menuTable.row();

        // Add main type buttons and a placeholder for count labels in the same row
        for (Building.BuildingTypes type : mainClasses) {
            final ImageButton button = createImageButton(type);
            final Label countLabel = createCountLabelForType(type);
            // Add button and label to the table in the same row
            // menuTable.add(button).pad(20).left();
            // menuTable.add(countLabel).pad(0).left();
            // menuTable.row();
            Stack stack = new Stack();
            stack.add(button); // Add the button first

            Container<Label> labelContainer = new Container<>(countLabel);
            labelContainer.align(Align.topRight);

            // Use negative padding to move the label outside the button boundary
            labelContainer.padTop(-10).padRight(-10);

            stack.add(labelContainer);

            // Add the stack to the table as before
            menuTable.add(stack).pad(20);
            menuTable.row();

            // Add listener to show sub-options (or handle directly)
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!buildings.getCurrentlySelecting()) {
                        SoundManager.playClick();
                        showSubOptions(type); // Show sub-options in the same menu
                    }
                }
            });
        }
    }

    private void showSubOptions(Building.BuildingTypes mainType) {
        menuTable.clear();
        countLabels.clear();

        // // Add a title for the sub-menu
        // Label titleLabel = new Label(mainType.name() + " Options", skin);
        // menuTable.add(titleLabel).colspan(2).center().pad(10);
        // titleLabel.setColor(Color.BLACK); // Set the title color to red
        // titleLabel.setFontScale(1f); // Increase the title font size
        // menuTable.row();

        String[] labels;
        Building.BuildingTypes[] subTypes;

        switch (mainType) {
            case Accomodation:
                labels = new String[] { "Derwent", "Goodricke", "Constantine" };
                subTypes = new Building.BuildingTypes[] {
                        Building.BuildingTypes.Derwent,
                        Building.BuildingTypes.Goodricke,
                        Building.BuildingTypes.Constantine
                };
                break;
            case FoodZone:
                labels = new String[] { "Nisa", "Greggs", "Derwent Dining" };
                subTypes = new Building.BuildingTypes[] {
                        Building.BuildingTypes.Nisa,
                        Building.BuildingTypes.Greggs,
                        Building.BuildingTypes.DerwentDining
                };
                break;
            case Recreational:
                labels = new String[] { "Nature", "Gym", "Society Building" };
                subTypes = new Building.BuildingTypes[] {
                        Building.BuildingTypes.Nature,
                        Building.BuildingTypes.Gym,
                        Building.BuildingTypes.SocietyBuilding
                };
                break;
            case LectureHall:
                labels = new String[] { "Piazza", "CentralHall" };
                subTypes = new Building.BuildingTypes[] {
                        Building.BuildingTypes.Piazza,
                        Building.BuildingTypes.CentralHall
                };
                break;
            case Course:
                labels = new String[] { "Software Labs", "Hardware Labs" };
                subTypes = new Building.BuildingTypes[] {
                        Building.BuildingTypes.SoftwareLabs,
                        Building.BuildingTypes.HardwareLabs
                };
                break;
            case Library:
                labels = new String[] { "Library" };
                subTypes = new Building.BuildingTypes[] { Building.BuildingTypes.Library };
                break;
            default:
                // If no sub-options, directly handle selection and go back to main menu
                buildings.handleSelection(mainType);
                showMainOptions();
                return;
        }

        for (int i = 0; i < subTypes.length; i++) {
            final Building.BuildingTypes subtype = subTypes[i];
            int index = subtype.ordinal();
            int displayCount = buildingCounts[index] / 2;

            // Just show the building name on the button, not the count
            String buildingName = labels[i];
            TextButton button = new TextButton(buildingName, skin);
            button.getLabel().setFontScale(1.5f);

            // Create a stack to overlay the count label on the button
            Stack stack = new Stack();
            stack.add(button);

            // Create a label for the count
            Label countLabel = new Label(String.valueOf(displayCount), skin);
            countLabel.setColor(Color.BLACK); // Set text color
            countLabel.setFontScale(1.4f); // Increase font size

            // Create a container to align the count label at the top-right corner
            Container<Label> countContainer = new Container<>(countLabel);
            countContainer.align(Align.topRight);

            // Adjust padding if you want the label slightly outside the button boundary
            countContainer.padTop(-10).padRight(-10);

            // Add the container with the count label on top of the button
            stack.add(countContainer);

            // Add the stack (with button + count label) to the table
            menuTable.add(stack).colspan(2).pad(20).width(145).height(50).fillX();
            menuTable.row();

            // Add listener to handle clicks
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    buildings.handleSelection(subtype);
                    showMainOptions();
                }
            });
        }

        // Add a back button
        TextButton backButton = new TextButton("Back", skin);
        backButton.getLabel().setFontScale(1.3f);
        menuTable.add(backButton).colspan(2).pad(10).width(70).height(30).fillX();
        menuTable.row();
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showMainOptions();
            }
        });
    }

    private ImageButton createImageButton(Building.BuildingTypes type) {
        int index = type.ordinal();
        Texture buttonUpTexture = Assets.buttonUpTextures[index];
        Texture buttonDownTexture = Assets.buttonDownTextures[index];
        Drawable buttonUpDrawable = new TextureRegionDrawable(buttonUpTexture);
        Drawable buttonDownDrawable = new TextureRegionDrawable(buttonDownTexture);

        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = buttonUpDrawable;
        buttonStyle.down = buttonDownDrawable;
        buttonStyle.over = buttonDownDrawable;

        ImageButton button = new ImageButton(buttonStyle);
        return button;
    }

    private Label createCountLabelForType(Building.BuildingTypes type) {
        int index = type.ordinal();
        int displayCount = buildingCounts[index] / 2;
        Label label = new Label(String.valueOf(displayCount), skin);
        label.setColor(Consts.COUNT_COLOR);
        label.setFontScale(Consts.COUNT_SIZE);
        countLabels.add(label);
        return label;
    }

    public static void updateCountLabel(Building.BuildingTypes type) {
        int index = type.ordinal();

        if (type == Building.BuildingTypes.Accomodation) {
            int total = buildingCounts[Building.BuildingTypes.Derwent.ordinal()]
                    + buildingCounts[Building.BuildingTypes.Goodricke.ordinal()]
                    + buildingCounts[Building.BuildingTypes.Constantine.ordinal()];
            buildingCounts[index] = total;
        }

        if (type == Building.BuildingTypes.FoodZone) {
            int total = buildingCounts[Building.BuildingTypes.Nisa.ordinal()]
                    + buildingCounts[Building.BuildingTypes.Greggs.ordinal()]
                    + buildingCounts[Building.BuildingTypes.DerwentDining.ordinal()];
            buildingCounts[index] = total;
        }

        if (type == Building.BuildingTypes.Recreational) {
            int total = buildingCounts[Building.BuildingTypes.Nature.ordinal()]
                    + buildingCounts[Building.BuildingTypes.Gym.ordinal()]
                    + buildingCounts[Building.BuildingTypes.SocietyBuilding.ordinal()];
            buildingCounts[index] = total;
        }

        if (type == Building.BuildingTypes.LectureHall) {
            int total = buildingCounts[Building.BuildingTypes.Piazza.ordinal()]
                    + buildingCounts[Building.BuildingTypes.CentralHall.ordinal()];
            buildingCounts[index] = total;
        }

        if (type == Building.BuildingTypes.Course) {
            int total = buildingCounts[Building.BuildingTypes.SoftwareLabs.ordinal()]
                    + buildingCounts[Building.BuildingTypes.HardwareLabs.ordinal()];
            buildingCounts[index] = total;
        }

        // Update the display count
        int displayCount = buildingCounts[index] / 2;
        if (index < countLabels.size) {
            countLabels.get(index).setText(String.valueOf(displayCount));
        }
    }

    private void createMenuBar() {
        menuBar = new Image(Assets.menuBarTexture);
        menuBar.setSize(Consts.MENU_BAR_WIDTH, Consts.MENU_BAR_HEIGHT);
        menuBar.setPosition(Consts.MENU_BAR_X, Consts.MENU_BAR_Y);
        stage.addActor(menuBar);
        // menuBar.toFront();
    }

    public void draw() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void dispose() {
    }
}
