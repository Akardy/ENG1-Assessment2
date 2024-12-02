package com.badlogic.UniSim2.GUImanager;

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

    private final Skin skin;

    // Holds the count of each type of building
    private int accomodationCount, lectureHallCount, libraryCount, courseCount, foodZoneCount, recreationalCount,
            natureCount;
    public static int[] buildingCounts;

    // Holds the labels that display the count of each building
    private static Array<Label> countLabels;

    public BuildingMenu(Stage stage, BuildingManager buildings) {
        this.stage = stage;
        Gdx.input.setInputProcessor(stage);
        this.buildings = buildings;

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        // Initializes buildingCounts with each building type
        buildingCounts = new int[] {
                accomodationCount,
                lectureHallCount,
                libraryCount,
                courseCount,
                foodZoneCount,
                recreationalCount,
                natureCount,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,

        };

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

        // Add buttons only for the main classes
        Building.BuildingTypes[] mainClasses = {
                Building.BuildingTypes.Accomodation,
                Building.BuildingTypes.LectureHall,
                Building.BuildingTypes.Library,
                Building.BuildingTypes.Course,
                Building.BuildingTypes.FoodZone,
                Building.BuildingTypes.Recreational
        };

        for (Building.BuildingTypes type : mainClasses) {
            createImageButton(type, buttonGap);
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
    private void createImageButton(Building.BuildingTypes type, int buttonGap) {
        int index = type.ordinal(); // Gets the index of type within BuildingTypes
        ImageButton button = setupImageButton(index, buttonGap); // Creates a button of the building type
        addImageButtonClick(button, type, index); // Adds a click listener to the button so we can do something when
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
    private void addImageButtonClick(ImageButton button, Building.BuildingTypes type, int index) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!buildings.getCurrentlySelecting()) {
                    SoundManager.playClick();

                    if (type == Building.BuildingTypes.Accomodation) {
                        showAccommodationPopup(); // Popup for Accommodation options
                    } else if (type == Building.BuildingTypes.FoodZone) {
                        showFoodZonePopup(); // Popup for FoodZone options
                    } else if (type == Building.BuildingTypes.Recreational) {
                        showRecreationalPopup();
                    } else if (type == Building.BuildingTypes.LectureHall) {
                        showLectureHallPopup();
                    } else if (type == Building.BuildingTypes.Course) {
                        showCoursePopup(); // Popup for Labs options
                    } else if (type == Building.BuildingTypes.Library) {
                        showLibraryPopup();
                    } else {
                        buildings.handleSelection(type); // Handle other building types
                    }
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
        Building.BuildingTypes type = Building.BuildingTypes.Library;
        int count = buildingCounts[type.ordinal()] / 2; // Adjust for double increment issue

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
        String[] labels = { "Derwent", "Goodricke", "Constantine" };
        Building.BuildingTypes[] subTypes = {
                Building.BuildingTypes.Derwent,
                Building.BuildingTypes.Goodricke,
                Building.BuildingTypes.Constantine
        };

        for (int i = 0; i < labels.length; i++) {
            Building.BuildingTypes subtype = subTypes[i];
            int count = buildingCounts[subtype.ordinal()] / 2;

            TextButton button = new TextButton(labels[i] + " (" + count + ")", skin);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    buildings.handleSelection(subtype); // Select and place the building
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
        String[] labels = { "Nisa", "Greggs", "Derwent Dining" };
        Building.BuildingTypes[] subTypes = {
                Building.BuildingTypes.Nisa,
                Building.BuildingTypes.Greggs,
                Building.BuildingTypes.DerwentDining
        };

        for (int i = 0; i < labels.length; i++) {
            Building.BuildingTypes subtype = subTypes[i];
            int count = BuildingMenu.buildingCounts[subtype.ordinal()] / 2;

            TextButton button = new TextButton(labels[i] + " (" + count + ")", skin);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    buildings.handleSelection(subtype); // Select and place the building
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
        String[] labels = { "Nature", "Gym", "Society Building" };
        Building.BuildingTypes[] subTypes = {
                Building.BuildingTypes.Nature,
                Building.BuildingTypes.Gym,
                Building.BuildingTypes.SocietyBuilding
        };

        // Ensure subTypes align with buildingCounts array size
        for (int i = 0; i < subTypes.length; i++) {
            Building.BuildingTypes subtype = subTypes[i];

            // Safeguard: Check bounds of buildingCounts before accessing
            if (subtype.ordinal() >= buildingCounts.length) {
                System.err.println("Invalid index for buildingCounts: " + subtype.ordinal());
                continue;
            }

            int count = buildingCounts[subtype.ordinal()] / 2; // Adjust count display logic

            TextButton button = new TextButton(labels[i] + " (" + count + ")", skin);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    buildings.handleSelection(subtype); // Select and place the building
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
        Building.BuildingTypes[] subTypes = {
                Building.BuildingTypes.SoftwareLabs,
                Building.BuildingTypes.HardwareLabs
        };

        for (int i = 0; i < labels.length; i++) {
            Building.BuildingTypes subtype = subTypes[i];
            int count = buildingCounts[subtype.ordinal()] / 2;

            TextButton button = new TextButton(labels[i] + " (" + count + ")", skin);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    buildings.handleSelection(subtype); // Select and place the building
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
        String[] labels = { "Piazza", "CentralHall" };
        Building.BuildingTypes[] subTypes = {
                Building.BuildingTypes.Piazza,
                Building.BuildingTypes.CentralHall
        };

        for (int i = 0; i < labels.length; i++) {
            Building.BuildingTypes subtype = subTypes[i];
            int count = BuildingMenu.buildingCounts[subtype.ordinal()] / 2;

            TextButton button = new TextButton(labels[i] + " (" + count + ")", skin);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    buildings.handleSelection(subtype); // Select and place the building
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

        int count = buildingCounts[index]; // Gets the count for the type of building using the type index in
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
     * If the type is Accomodation, it sums up the counts for Derwent, Goodricke,
     * and Constantine.
     * 
     * @param type The type of building to update the count for.
     */
    public static void updateCountLabel(Building.BuildingTypes type) {
        int index = type.ordinal();

        // Update the count for the specific type
        if (type == Building.BuildingTypes.Accomodation) {
            // Aggregate count for Accomodation
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

        if (type == Building.BuildingTypes.Library) {
            int displayCount = buildingCounts[index] / 2; // Adjust for double increment issue
            if (index < countLabels.size) {
                countLabels.get(index).setText(String.valueOf(displayCount));
            }
        }

        // Display half the count as a workaround
        int displayCount = buildingCounts[index] / 2;

        // Update the label
        if (index < countLabels.size) {
            countLabels.get(index).setText(String.valueOf(displayCount));
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
