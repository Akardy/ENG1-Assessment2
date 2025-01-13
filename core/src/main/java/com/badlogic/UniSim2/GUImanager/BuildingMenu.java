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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Align;

/**
 * A menu which can be used to place new buildings onto the map. This menu is
 * a part of the {@link Hud} game menu. It shows all the types of buildings
 * that can be placed and how many of them are already placed.
 *
 * In this version, we display sub-options for each main building type
 * within the same menu window (using Tables), rather than opening popups.
 */
public class BuildingMenu {

    private Stage stage;
    private BuildingManager buildings;
    private Image menuBar;

    /**
     * All building counts are in this BuildingCounts object, with methods like
     * getAccommodationCount(), getLectureHallCount(), getFoodZoneCount(), etc.
     */
    private static BuildingCounts buildingCounts;

    private final Skin skin;

    // Holds the labels that display the count of each building
    private static Array<Label> countLabels;

    // A Table that will hold our main or sub-menu buttons
    private Table menuTable;

    /**
     * Define the main building classes that we want to show in the first level
     * of our menu (like "Accommodation", "LectureHall", "Library", etc.).
     */
    private final BuildingTypes[] mainClasses = {
            BuildingTypes.ACCOMODATION,
            BuildingTypes.LECTUREHALL,
            BuildingTypes.LIBRARY,
            BuildingTypes.LABS,
            BuildingTypes.FOODZONE,
            BuildingTypes.RECREATIONAL
    };
    /**
     * Retrieves the cost of a given building type.
     *
     * @param type the BuildingTypes enum value.
     * @return the cost of the building type.
     */
    private float getCostOfType(BuildingTypes type) {
        switch (type) {
            case DERWENT:         return 2500f;
            case CONSTANTINE:     return 6000f;
            case GOODRICKE:       return 4000f;
            case PIAZZA:          return 2500f;
            case CENTRALHALL:     return 4000f;
            case SOFTWARELABS:    return 2000f;
            case HARDWARELABS:    return 3500f;
            case NISA:            return 1000f;
            case GREGGS:          return 2000f;
            case DERWENTDINING:   return 3000f;
            case NATURE:          return 1000f;
            case GYM:             return 2000f;
            case SOCIETYBUILDING: return 3000f;
            case LIBRARY:         return 3500f;
            default:
                // If you add new BuildingTypes in the future, handle them here.
                return 0;
        }
    }


    /**
     * Constructs a BuildingMenu so buildings can be purchased.
     *
     * @param stage          the stage for UI actors.
     * @param buildings      the building manager handling building logic.
     * @param buildingCounts the counts of various building types.
     */
    public BuildingMenu(Stage stage, BuildingManager buildings, BuildingCounts buildingCounts) {
        this.stage = stage;
        Gdx.input.setInputProcessor(stage);
        this.buildings = buildings;
        BuildingMenu.buildingCounts = buildingCounts;

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        countLabels = new Array<>();

        // Create a Table to hold the menu items.
        menuTable = new Table();
        menuTable.setDebug(false);
        // Set position and size so it overlays your bar or sits on top of it.
        menuTable.setPosition(Consts.MENU_BAR_X, Consts.MENU_BAR_Y);
        menuTable.setSize(Consts.MENU_BAR_WIDTH, Consts.MENU_BAR_HEIGHT);

        stage.addActor(menuTable);
    }




    /**
     * Creates the building menu: the menu bar plus the main options.
     */
    public void createBuildingMenu() {
        createMenuBar();
        showMainOptions();
        menuTable.toFront();
    }

    /**
     * Creates the menu bar graphic.
     */
    private void createMenuBar() {
        menuBar = new Image(Assets.menuBarTexture);
        menuBar.setSize(Consts.MENU_BAR_WIDTH, Consts.MENU_BAR_HEIGHT);
        menuBar.setPosition(Consts.MENU_BAR_X, Consts.MENU_BAR_Y);
        stage.addActor(menuBar);
    }

    /**
     * Clears the menuTable and shows the main building categories (Accom, LectureHall, etc.).
     */
    private void showMainOptions() {
        menuTable.clear();
        countLabels.clear(); // Clear out old references before adding new ones

        // For each main building type, we create an ImageButton and a label on top (count).
        for (BuildingTypes type : mainClasses) {
            // Create an ImageButton for the main type
            final ImageButton button = createImageButton(type);
            // Create a Label that shows the count for that main type
            final Label countLabel = createMainTypeCountLabel(type);

            Stack stack = new Stack();
            stack.add(button);

            // Container to position the label at the top-right of the button
            Container<Label> labelContainer = new Container<>(countLabel);
            labelContainer.align(Align.topRight);
            // Slight negative padding if you want it near the corner
            labelContainer.padTop(-10).padRight(-10);

            stack.add(labelContainer);

            // Add the stacked item to the table
            menuTable.add(stack).pad(20);
            menuTable.row();

            // When clicked, show sub-options in this same menu table
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!buildings.getCurrentlySelecting()) {
                        SoundManager.playClick();
                        showSubOptions(type);
                    }
                }
            });
        }
    }

    /**
     * Shows sub-options for the selected main building type, in the same Table.
     *
     */
    private void showSubOptions(BuildingTypes mainType) {
        // Clear out previous items and labels
        menuTable.clear();
        countLabels.clear();

        // Determine the sub-types and the display labels we want
        String[] labels;
        BuildingTypes[] subTypes;

        switch (mainType) {
            case ACCOMODATION:
                labels = new String[] { "DERWENT", "GOODRICKE", "CONSTANTINE" };
                subTypes = new BuildingTypes[] {
                        BuildingTypes.DERWENT,
                        BuildingTypes.GOODRICKE,
                        BuildingTypes.CONSTANTINE
                };
                break;

            case LECTUREHALL:
                labels = new String[] { "PIAZZA", "CENTRALHALL" };
                subTypes = new BuildingTypes[] {
                        BuildingTypes.PIAZZA,
                        BuildingTypes.CENTRALHALL
                };
                break;

            case LIBRARY:
                labels = new String[] { "LIBRARY" };
                subTypes = new BuildingTypes[] {
                        BuildingTypes.LIBRARY
                };
                break;

            case LABS:
                labels = new String[] { "Software Labs", "Hardware Labs" };
                subTypes = new BuildingTypes[] {
                        BuildingTypes.SOFTWARELABS,
                        BuildingTypes.HARDWARELABS
                };
                break;

            case FOODZONE:
                labels = new String[] { "NISA", "GREGGS", "DERWENT Dining" };
                subTypes = new BuildingTypes[] {
                        BuildingTypes.NISA,
                        BuildingTypes.GREGGS,
                        BuildingTypes.DERWENTDINING
                };
                break;

            case RECREATIONAL:
                labels = new String[] { "NATURE", "GYM", "Society Building" };
                subTypes = new BuildingTypes[] {
                        BuildingTypes.NATURE,
                        BuildingTypes.GYM,
                        BuildingTypes.SOCIETYBUILDING
                };
                break;

            default:
                // If none match, just go back to main or do nothing
                showMainOptions();
                return;
        }

        // populate the sub-menu
        for (int i = 0; i < subTypes.length; i++) {
            final BuildingTypes subType = subTypes[i];
            // get cost
            float cost = getCostOfType(subType);

            // build multiline text
            String line1 = labels[i];     // e.g. "Greggs"
            String line2 = "£" + cost;    // e.g. "£2000"
            String buttonText = line1 + "\n" + line2;

            // create the button
            TextButton button = new TextButton(buttonText, skin);

            // Let the label wrap and center
            button.getLabel().setWrap(true);
            button.getLabel().setAlignment(Align.center);
            button.getLabel().setFontScale(1.2f);

            // create a stack for the count label
            Stack stack = new Stack();
            stack.add(button);

            // set up subCount in top‑right corner
            int subCount = buildingCounts.getBuildingCounts(subType.ordinal());
            Label countLabel = new Label(String.valueOf(subCount), skin);
            countLabel.setColor(Consts.COUNT_COLOR);
            countLabel.setFontScale(1.4f);

            Container<Label> countContainer = new Container<>(countLabel);
            countContainer.align(Align.topRight);
            countContainer.padTop(-10).padRight(-10);

            stack.add(countContainer);

            menuTable.add(stack).colspan(2).pad(20).width(150).height(80).fillX();
            menuTable.row();

            // add click listener to place the building
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    buildings.handleSelection(subType);
                    showMainOptions(); // or remain in sub-menu
                }
            });
        }


        // Add a "Back" button to go back to main options
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

    /**
     * Creates an ImageButton for given main building type.
     *
     * @param type the main building type.
     * @return an ImageButton for the given type.
     */
    private ImageButton createImageButton(BuildingTypes type) {
        Texture buttonUpTexture;
        Texture buttonDownTexture;

        switch (type) {
            case ACCOMODATION:
                buttonUpTexture = Assets.accomodationButtonUpTexture;
                buttonDownTexture = Assets.accomodationButtonDownTexture;
                break;

            case LECTUREHALL:
                buttonUpTexture = Assets.lectureHallButtonUpTexture;
                buttonDownTexture = Assets.lectureHallButtonDownTexture;
                break;

            case LIBRARY:
                buttonUpTexture = Assets.libraryButtonUpTexture;
                buttonDownTexture = Assets.libraryButtonDownTexture;
                break;

            case LABS:
                buttonUpTexture = Assets.courseButtonUpTexture;
                buttonDownTexture = Assets.courseButtonDownTexture;
                break;

            case FOODZONE:
                buttonUpTexture = Assets.foodZoneButtonUpTexture;
                buttonDownTexture = Assets.foodZoneButtonDownTexture;
                break;

            case RECREATIONAL:
                buttonUpTexture = Assets.recreationalButtonUpTexture;
                buttonDownTexture = Assets.recreationalButtonDownTexture;
                break;

            default:
                buttonUpTexture = Assets.accomodationButtonUpTexture;
                buttonDownTexture = Assets.accomodationButtonDownTexture;
                break;
        }

        // Build ImageButton style
        Drawable buttonUpDrawable = new TextureRegionDrawable(buttonUpTexture);
        Drawable buttonDownDrawable = new TextureRegionDrawable(buttonDownTexture);

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = buttonUpDrawable;
        style.down = buttonDownDrawable;

        // Create and return the ImageButton
        ImageButton button = new ImageButton(style);
        button.setSize(Consts.BUILDING_BUTTON_WIDTH, Consts.BUILDING_BUTTON_HEIGHT);
        return button;
    }


    /**
     * Creates a label showing how many of the given main type exist.
     * For main types that are "composites" (e.g., Accommodation is made of DERWENT,
     * GOODRICKE, CONSTANTINE), we call the appropriate method from BuildingCounts.
     */
    private Label createMainTypeCountLabel(BuildingTypes type) {
        int count;
        if (type == BuildingTypes.ACCOMODATION) {
            count = buildingCounts.getAccommodationCount();
        } else if (type == BuildingTypes.LECTUREHALL) {
            count = buildingCounts.getLectureHallCount();
        } else if (type == BuildingTypes.LIBRARY) {
            count = buildingCounts.getLibaryCount();
        } else if (type == BuildingTypes.LABS) {
            count = buildingCounts.getLabsCount();
        } else if (type == BuildingTypes.FOODZONE) {
            count = buildingCounts.getFoodZoneCount();
        } else if (type == BuildingTypes.RECREATIONAL) {
            count = buildingCounts.getRecreationalCount();
        } else {
            count = 0; // default
        }

        Label label = new Label(String.valueOf(count), skin);
        label.setColor(Consts.COUNT_COLOR);
        label.setFontScale(Consts.COUNT_SIZE);
        countLabels.add(label); // keep track of it in the array
        return label;
    }

    /**
     * Updates the label for building counts whenever a building is placed.
     * Call this from wherever you handle building-placed events.
     */
    public static void updateCountLabel(Building currentBuilding) {
        // If the building is, for example, an Accomodation, update label 0
        if (countLabels == null || countLabels.size < 6) {
            // If in building sub menu then ignore
            return;
        }
        if (currentBuilding instanceof Accomodation) {
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

        if (currentBuilding instanceof FoodZone) {
            countLabels.get(4).setText(buildingCounts.getFoodZoneCount());
        }

        if (currentBuilding instanceof Recreational) {
            countLabels.get(5).setText(buildingCounts.getRecreationalCount());
        }
    }

    /**
     * Draw the stage.
     */
    public void draw() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    /**
     * Dispose resources if needed.
     */
    public void dispose() {
        // If you need to dispose anything, do it here.
    }


}
