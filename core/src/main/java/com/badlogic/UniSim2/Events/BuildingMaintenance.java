package com.badlogic.UniSim2.Events;

import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.buildingmanager.BuildingManager;
import com.badlogic.UniSim2.buildingmanager.types.BuildingTypes;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.stats.Money;
import com.badlogic.UniSim2.stats.Satisfaction;
import com.badlogic.UniSim2.stats.Timer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BuildingMaintenance {

    private ShapeRenderer shapeRenderer;
    private Timer timer;
    private BuildingManager buildingManager;
    private Money money;
    private Satisfaction satisfaction;

    private float maintainSeconds;
    private boolean hasStarted;
    private boolean renderIndicator;

    private Building targetBuilding;
    private Announcement announcement;
    private Label prompt;
    private Label satTickPrompt;
    private Skin skin;

    private float satTickPromptTimer;
    private float maintenanceTickTimer;
    private final float TICK_TIME = 5;

    private float x, y, width, height;
    private float scalex;
    private float scaley;

    private static final Map<BuildingTypes, MaintenanceInfo> MAINTENANCE_INFO = new HashMap<>();

    static {
        MAINTENANCE_INFO.put(
            BuildingTypes.LIBRARY,
            new MaintenanceInfo("Library needs maintenance!", 200, "-2%")
        );
        MAINTENANCE_INFO.put(
            BuildingTypes.NISA,
            new MaintenanceInfo("Nisa needs restocking!", 150, "-2%")
        );
        MAINTENANCE_INFO.put(
            BuildingTypes.GREGGS,
            new MaintenanceInfo("Greggs needs supplies!", 150, "-2%")
        );
        MAINTENANCE_INFO.put(
            BuildingTypes.GYM,
            new MaintenanceInfo("Gym needs maintenance!", 100, "-1%")
        );
    }

    private static class MaintenanceInfo {

        String message;
        int cost;
        String decayAmount;

        MaintenanceInfo(String message, int cost, String decayAmount) {
            this.message = message;
            this.cost = cost;
            this.decayAmount = decayAmount;
        }
    }

    public BuildingMaintenance(
        Timer timer,
        BuildingManager buildingManager,
        Money money,
        Satisfaction satisfaction,
        Announcement announcement
    ) {
        this.shapeRenderer = new ShapeRenderer();
        this.timer = timer;
        this.buildingManager = buildingManager;
        this.money = money;
        this.satisfaction = satisfaction;
        this.announcement = announcement;

        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.scalex = (float) Gdx.graphics.getWidth() / Consts.WORLD_WIDTH;
        this.scaley = (float) Gdx.graphics.getHeight() / Consts.WORLD_HEIGHT;

        this.maintenanceTickTimer = TICK_TIME;
        this.satTickPromptTimer = TICK_TIME / 2;
        this.hasStarted = false;
        this.renderIndicator = false;

        selectMaintenanceTime();
    }

    private void selectMaintenanceTime() {
        Random random = new Random();
        maintainSeconds = random.nextInt(280) + 20;
    }

    public void checkMaintenance() {
        if ((int) timer.getElapsedTime() == maintainSeconds && !hasStarted) {
            hasStarted = true;
            if (!buildingManager.getPlaced().isEmpty()) {
                startMaintenance();
            }
        }
    }

    public void render(float delta) {
        if (!renderIndicator) return;

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            endMaintenance();
        }

        if (maintenanceTickTimer > 0) {
            maintenanceTickTimer -= delta;
        } else {
            maintainanceDecay();
            maintenanceTickTimer = TICK_TIME;
        }

        if (satTickPrompt.isVisible() && satTickPromptTimer > 0) {
            satTickPromptTimer -= delta;
        } else if (satTickPrompt.isVisible()) {
            satTickPrompt.setVisible(false);
            satTickPromptTimer = TICK_TIME / 2;
        }
    }

    private void startMaintenance() {
        pickBuilding();
        if (targetBuilding == null) return;

        renderIndicator = true;
        MaintenanceInfo info = MAINTENANCE_INFO.get(targetBuilding.getType());
        announcement.showAnnouncement(info.message);

        prompt = new Label("Press M\nto maintain\nCost: £" + info.cost, skin);
        prompt.setFontScale(2);
        prompt.setPosition((x * scalex) + 10, (y * scaley) + 65);
        prompt.setVisible(true);
        announcement.addNewLabel(prompt);

        satTickPrompt = new Label(info.decayAmount + " Satisfaction", skin);
        satTickPrompt.setFontScale(2);
        satTickPrompt.setAlignment(Align.bottomLeft);
        satTickPrompt.setPosition(x * scalex, (y * scaley) + height + 50);
        satTickPrompt.setColor(Color.RED);
        satTickPrompt.setVisible(false);
        announcement.addNewLabel(satTickPrompt);
    }

    private void pickBuilding() {
        Random random = new Random();
        if (buildingManager.getPlaced().size == 0) return;

        int index = random.nextInt(buildingManager.getPlaced().size);
        targetBuilding = buildingManager.getPlaced().get(index);

        x = targetBuilding.getX();
        y = targetBuilding.getY();
        width = targetBuilding.getWidth();
        height = targetBuilding.getHeight();
    }

    private void maintainanceDecay() {
        satisfaction.decreaseSatis(2f);
        satTickPrompt.setVisible(true);
    }

    private void endMaintenance() {
        MaintenanceInfo info = MAINTENANCE_INFO.get(targetBuilding.getType());
        if (money.reduceMoney(info.cost)) {
            announcement.showAnnouncement(
                "Maintenance complete!\n-£" + info.cost
            );
            satisfaction.increaseSatis(5f);
            renderIndicator = false;
            prompt.remove();
            satTickPrompt.remove();
            selectMaintenanceTime();
            hasStarted = false;
        } else {
            buildingManager.showError("Can't afford maintenance!");
        }
    }

    public boolean doRender() {
        return renderIndicator;
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
