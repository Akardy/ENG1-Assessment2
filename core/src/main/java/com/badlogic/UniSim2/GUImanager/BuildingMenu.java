package com.badlogic.UniSim2.GUImanager;

import com.badlogic.UniSim2.buildingmanager.types.*;
import com.badlogic.UniSim2.stats.BuildingCounts;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.buildingmanager.BuildingManager;
import com.badlogic.UniSim2.resources.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

/**
 * A menu which can be used to place new buildings onto the map. This menu is
 * a part of the {@link GameMenu game menu}. It shows all the types of buildings
 * that can be placed and how many of them are already placed.
 */
public class BuildingMenu {

    private Stage stage;
    private BuildingManager buildings;
    private Image menuBar;

    private static BuildingCounts buildingCounts;

    private final Skin skin;

    // Holds the count of each type of building

    // Holds the labels that display the count of each building
    private static Array<Label> countLabels;

    public BuildingMenu(Stage stage, BuildingManager buildings, BuildingCounts buildingCounts) {
        this.stage = stage;
        Gdx.input.setInputProcessor(stage);
        this.buildings = buildings;
        this.buildingCounts = buildingCounts;

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));


        countLabels = new Array<>();
    }

    /**
     * Creates a bar to hold all the building buttons, creates the building
     * buttons and creates the labels which hold the amount of times each
     * building has been placed.
     */
    public void createBuildingMenu() {
        createMenuBar();
        createImageButtons();
        createCountLabels();
    }

    /**
     * Creates a button for each building types with a gap between each button and
     * a count label for each button.
     */
    // private void createImageButtons() {
    // int buttonGap = Consts.BUILDING_BUTTON_GAP;

    // // Iterating through each type of building
    // for (Building.BuildingTypes type : Building.BuildingTypes.values()) {

    // createImageButton(type, buttonGap);
    // buttonGap += Consts.BUILDING_BUTTON_GAP;
    // }
    // }

    private void createImageButtons() {
        int buttonGap = Consts.BUILDING_BUTTON_GAP;

        for (int i = 0; i < 6; i++) {
            createImageButton(i, buttonGap);
            buttonGap += Consts.BUILDING_BUTTON_GAP;
        }
    }

    /**
     * Creates a single image button and adds it to the {@link #stage}.
     *
     * @param type      The building type to create a button for. Defines the
     *                  texture of
     *                  the button.
     * @param buttonGap The gap from the max y coord a button can be placed defined
     *                  by
     *                  {@link Consts#BUILDING_BUTTON_Y_BOUNDARY} to where the
     *                  button should be placed.
     *                  Will place newly created button at
     *                  ({@link Consts#BUILDING_BUTTON_Y_BOUNDARY} - buttonGap).
     */
    private void createImageButton(int index, int buttonGap) {
        ImageButton button = setupImageButton(index, buttonGap); // Creates a button of the building type
        addImageButtonClick(button, index); // Adds a click listener to the button so we can do something when
                                                  // clicked
        stage.addActor(button);
    }

    /**
     * Sets the texture, size nad position of the button.
     *
     * @param index     The index of the button textures in
     *                  {@link Assets#buttonUpTextures}
     *                  and {@link Assets#buttonDownTextures}.
     * @param buttonGap The gap from the max y coord a button can be placed defined
     *                  by
     *                  {@link Consts#BUILDING_BUTTON_Y_BOUNDARY} to where the
     *                  button should be placed.
     *                  Will place newly created button at
     *                  ({@link Consts#BUILDING_BUTTON_Y_BOUNDARY} - buttonGap).
     * @return The button.
     */
    private ImageButton setupImageButton(int index, int buttonGap) {
        Texture buttonUpTexture = Assets.buttonUpTextures[index]; // Texture when not hovering or clicking
        Texture buttonDownTexture = Assets.buttonDownTextures[index]; // Texture when hovering or clicking
        Drawable buttonUpDrawable = new TextureRegionDrawable(buttonUpTexture);
        Drawable buttonDownDrawable = new TextureRegionDrawable(buttonDownTexture);
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();

        buttonStyle.up = buttonUpDrawable;
        buttonStyle.down = buttonDownDrawable;
        buttonStyle.over = buttonDownDrawable;

        ImageButton button = new ImageButton(buttonStyle); // Creates a new button with the correct building button
                                                           // textures

        button.setSize(Consts.BUILDING_BUTTON_WIDTH, Consts.BUILDING_BUTTON_HEIGHT);
        button.setPosition(Consts.BUILDING_BUTTON_X_BOUNDARY, Consts.BUILDING_BUTTON_Y_BOUNDARY - buttonGap); // Places
                                                                                                              // the
                                                                                                              // button
                                                                                                              // with a
                                                                                                              // gap

        setUpCountLabel(index, button); // Sets up a count label for the button to count how many times the buildint
                                        // type is placed

        return button;
    }

    /**
     * Adds a click listener to the button so that it knows what to do when
     * clicked.
     *
     * @param button The button to add the listener to.
     * @param type   The type of building that button represents. This building type
     *               will be created when the button is pressed.
     * @param index  The index of the building in the enum
     *               {@link Building#BuildingTypes}
     */
    private void addImageButtonClick(ImageButton button, int index) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!buildings.getCurrentlySelecting()) {
                    SoundManager.playClick();

                    if (index == 0) {
                        showAccommodationPopup(); // Popup for Accommodation options
                    } else if (index == 1) {
                        showLectureHallPopup(); // Popup for FoodZone options
                    } else if (index == 2) {
                        showLibraryPopup();
                    } else if (index == 3) {
                        showCoursePopup();
                    } else if (index == 4) {
                        showFoodZonePopup(); // Popup for Labs options
                    } else if (index == 5) {
                        showRecreationalPopup();
                    }
                    //else {
//                        buildings.handleSelection(type); // Handle other building types
//                    }
                }
            }
        });
    }

    //////// library///
    private void showLibraryPopup() {
        Window popupWindow = new Window("Library Options", skin);

        popupWindow.setSize(400, 200);
        popupWindow.setPosition(200, 300);
        popupWindow.setMovable(true);

        popupWindow.getTitleTable().padTop(20).center();

        // Add button for Library
        String label = "Library";
        BuildingTypes type = BuildingTypes.LIBRARY;
        int count = buildingCounts.getBuildingCounts(type.ordinal()); // Adjust for double increment issue

        TextButton button = new TextButton(label + " (" + count + ")", skin);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buildings.handleSelection(type); // Select and place the Library
                popupWindow.remove(); // Close the popup after selection
            }
        });

        popupWindow.row();
        popupWindow.add(button).pad(10).fillX();

        // Add a close button
        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                popupWindow.remove(); // Close the popup
            }
        });

        popupWindow.row();
        popupWindow.add(closeButton).pad(10).fillX();

        // Add the popup to the stage
        stage.addActor(popupWindow);
    }

    // //////// Accomodation ////////////

    private void showAccommodationPopup() {
        Window popupWindow = new Window("Accommodation Options", skin);

        popupWindow.setSize(400, 300);
        popupWindow.setPosition(200, 300);
        popupWindow.setMovable(true);

        popupWindow.getTitleTable().padTop(20).center();

        // Add buttons and counts for accommodation options
        String[] labels = { "DERWENT", "GOODRICKE", "CONSTANTINE" };
        BuildingTypes[] subTypes = {
                BuildingTypes.DERWENT,
                BuildingTypes.GOODRICKE,
                BuildingTypes.CONSTANTINE
        };

        for (int i = 0; i < labels.length; i++) {
            BuildingTypes subType = subTypes[i];
            int count = buildingCounts.getBuildingCounts(subType.ordinal());

            TextButton button = new TextButton(labels[i] + " (" + count + ")", skin);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    buildings.handleSelection(subType); // Select and place the building
                    popupWindow.remove(); // Close the popup after selection
                }
            });

            popupWindow.row();
            popupWindow.add(button).pad(10).fillX();
        }

        // Add a close button
        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                popupWindow.remove(); // Close the popup
            }

        });

        popupWindow.row();
        popupWindow.add(closeButton).pad(10).fillX();

        stage.addActor(popupWindow);
    }

    ///////////// food zone/////
    private void showFoodZonePopup() {
        Window popupWindow = new Window("Food Zone Options", skin);

        popupWindow.setSize(400, 300);
        popupWindow.setPosition(200, 300);
        popupWindow.setMovable(true);

        popupWindow.getTitleTable().padTop(20).center();

        // Add buttons and counts for FoodZone options
        String[] labels = { "NISA", "GREGGS", "DERWENT Dining" };
        BuildingTypes[] subTypes = {
                BuildingTypes.NISA,
                BuildingTypes.GREGGS,
                BuildingTypes.DERWENTDINING
        };

        for (int i = 0; i < labels.length; i++) {
            BuildingTypes subType = subTypes[i];
            int count = buildingCounts.getBuildingCounts(subType.ordinal());

            TextButton button = new TextButton(labels[i] + " (" + count + ")", skin);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    buildings.handleSelection(subType); // Select and place the building
                    popupWindow.remove(); // Close the popup after selection
                }
            });

            popupWindow.row();
            popupWindow.add(button).pad(10).fillX();
        }

        // Add a close button
        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                popupWindow.remove(); // Close the popup
            }

        });

        popupWindow.row();
        popupWindow.add(closeButton).pad(10).fillX();

        stage.addActor(popupWindow);
    }

    /////////// recreational////////
    private void showRecreationalPopup() {
        Window popupWindow = new Window("Recreational Options", skin);

        popupWindow.setSize(400, 300);
        popupWindow.setPosition(200, 300);
        popupWindow.setMovable(true);

        popupWindow.getTitleTable().padTop(20).center();

        // Define Recreational subtypes and labels
        String[] labels = { "NATURE", "GYM", "Society Building" };
        BuildingTypes[] subTypes = {
                BuildingTypes.NATURE,
                BuildingTypes.GYM,
                BuildingTypes.SOCIETYBUILDING
        };

        // Ensure subTypes align with buildingCounts array size
        for (int i = 0; i < subTypes.length; i++) {
            BuildingTypes subType = subTypes[i];

            // Safeguard: Check bounds of buildingCounts before accessing
//            if (subType.ordinal() >= buildingCounts.length) {
//                System.err.println("Invalid index for buildingCounts: " + subType.ordinal());
//                continue;
//            }

            int count = buildingCounts.getBuildingCounts(subType.ordinal()); // Adjust count display logic

            TextButton button = new TextButton(labels[i] + " (" + count + ")", skin);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    buildings.handleSelection(subType); // Select and place the building
                    popupWindow.remove(); // Close the popup after selection
                }
            });

            popupWindow.row();
            popupWindow.add(button).pad(10).fillX();
        }

        // Add a close button
        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                popupWindow.remove(); // Close the popup
            }

        });

        popupWindow.row();
        popupWindow.add(closeButton).pad(10).fillX();

        stage.addActor(popupWindow);
    }

    ////////// course///
    private void showCoursePopup() {
        Window popupWindow = new Window("Courses Options", skin);

        popupWindow.setSize(400, 300);
        popupWindow.setPosition(200, 300);
        popupWindow.setMovable(true);

        popupWindow.getTitleTable().padTop(20).center();

        // Add buttons and counts for Labs options
        String[] labels = { "Software Labs", "Hardware Labs" };
        BuildingTypes[] subTypes = {
                BuildingTypes.SOFTWARELABS,
                BuildingTypes.HARDWARELABS
        };

        for (int i = 0; i < labels.length; i++) {
            BuildingTypes subType = subTypes[i];
            int count = buildingCounts.getBuildingCounts(subType.ordinal());

            TextButton button = new TextButton(labels[i] + " (" + count + ")", skin);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    buildings.handleSelection(subType); // Select and place the building
                    popupWindow.remove(); // Close the popup after selection
                }
            });

            popupWindow.row();
            popupWindow.add(button).pad(10).fillX();
        }

        // Add a close button
        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                popupWindow.remove(); // Close the popup
            }

        });

        popupWindow.row();
        popupWindow.add(closeButton).pad(10).fillX();

        stage.addActor(popupWindow);
    }

    /////////// lecture hall/////
    private void showLectureHallPopup() {
        Window popupWindow = new Window("Lecture Hall Options", skin);

        popupWindow.setSize(400, 300);
        popupWindow.setPosition(200, 300);
        popupWindow.setMovable(true);

        popupWindow.getTitleTable().padTop(20).center();

        // Add buttons and counts for Lecture Hall options
        String[] labels = { "Piazza", "CENTRALHALL" };
        BuildingTypes[] subTypes = {
                BuildingTypes.PIAZZA,
                BuildingTypes.CENTRALHALL
        };

        for (int i = 0; i < labels.length; i++) {
            BuildingTypes subType = subTypes[i];
            int count = buildingCounts.getBuildingCounts(subType.ordinal());

            TextButton button = new TextButton(labels[i] + " (" + count + ")", skin);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    buildings.handleSelection(subType); // Select and place the building
                    popupWindow.remove(); // Close the popup after selection
                }
            });

            popupWindow.row();
            popupWindow.add(button).pad(10).fillX();
        }

        // Add a close button
        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                popupWindow.remove(); // Close the popup
            }

        });

        popupWindow.row();
        popupWindow.add(closeButton).pad(10).fillX();

        stage.addActor(popupWindow);
    }

    /**
     * A count label is created for each building button to show how many building
     * of that type have been placed on the map.
     *
     * @param index  The index of the button textures in
     *               {@link Assets#buttonUpTextures}
     *               and {@link Assets#buttonDownTextures}.
     * @param button The button the count label is for.
     */
    private void setUpCountLabel(int index, ImageButton button) {

        int count = buildingCounts.getAccommodationCount(); // Gets the count for the type of building using the type index in
                                           // BuildingTypes
        Label countLabel = new Label(String.valueOf(count), skin);

        // Sets position of label to the top right of the button
        float x = button.getX() + button.getWidth();
        float y = button.getY() + button.getHeight();
        countLabel.setPosition(x, y);

        countLabel.setColor(Consts.COUNT_COLOR);
        countLabel.setFontScale(Consts.COUNT_SIZE);
        countLabels.add(countLabel);
    }

    /**
     * Increments the count label for a specified building button label.
     *
     * @param index The index of the building in the enum
     *              {@link Building#BuildingTypes}
     */
    /**
     * Updates the count label for a specified building type.
     * If the type is Accomodation, it sums up the counts for DERWENT, GOODRICKE,
     * and CONSTANTINE.
     *
     * @param type The type of building to update the count for.
     */
    public static void updateCountLabel(Building currentBuilding) {

        // Update the count for the specific type
        if (currentBuilding instanceof Accomodation) {
            // Aggregate count for Accomodation
            countLabels.get(0).setText(buildingCounts.getAccommodationCount());
        }

        if (currentBuilding instanceof LectureHall) {
            countLabels.get(1).setText(buildingCounts.getLectureHallCount());
        }

        if (currentBuilding instanceof Library) {
            countLabels.get(2).setText(buildingCounts.getLibaryCount());
        }

        if (currentBuilding instanceof Labs) {
            countLabels.get(3).setText(buildingCounts.getLabsCount());
        }

        if (currentBuilding instanceof  FoodZone) {
            countLabels.get(4).setText(buildingCounts.getFoodZoneCount());
        }

        if (currentBuilding instanceof Recreational) {
            countLabels.get(5).setText(buildingCounts.getRecreationalCount());
        }
    }

    /**
     * Adds the count labels to the stage.
     */
    private void createCountLabels() {
        for (Label countLabel : countLabels) {
            stage.addActor(countLabel);
        }
    }

    private void createMenuBar() {
        menuBar = new Image(Assets.menuBarTexture);
        menuBar.setSize(Consts.MENU_BAR_WIDTH, Consts.MENU_BAR_HEIGHT);
        menuBar.setPosition(Consts.MENU_BAR_X, Consts.MENU_BAR_Y);
        stage.addActor(menuBar);
    }

    /**
     * Draws the building menu.
     */
    public void draw() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void dispose() {
    }
}
