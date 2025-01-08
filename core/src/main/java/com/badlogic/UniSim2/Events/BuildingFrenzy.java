package com.badlogic.UniSim2.Events;

import com.badlogic.UniSim2.Main;
import com.badlogic.UniSim2.buildingmanager.BuildingManager;
import com.badlogic.UniSim2.buildingmanager.types.BuildingTypes;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.stats.BuildingCounts;
import com.badlogic.UniSim2.stats.Money;
import com.badlogic.UniSim2.stats.Satisfaction;
import com.badlogic.UniSim2.stats.Timer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class BuildingFrenzy {

    private Timer timer;
    private Satisfaction satisfaction;
    private BuildingManager buildingManager;
    private BuildingCounts buildingCounts;
    private Announcement announcement;
    private Skin skin;
    private Stage stage;

    private boolean eventTriggered;
    private float eventTimer;
    private int amountOfTypes;
    private boolean needUpdating;
    private Array<BuildingTypes> toBuild;

    private Label timerPrompt;
    private Array<Label> labels;
    private Array<Integer> originalAmounts;
    private Array<Integer> currentAmounts;
    private Array<Boolean> completedType;


    public BuildingFrenzy(Timer timer, Satisfaction satisfaction, BuildingManager buildingManager,
                          BuildingCounts buildingCounts, Announcement announcement, Main game) {
        this.timer = timer;
        this.satisfaction = satisfaction;
        this.buildingManager = buildingManager;
        this.buildingCounts = buildingCounts;
        this.announcement = announcement;
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.stage = new Stage(game.getViewport());
        this.eventTriggered = false;
        initialiseLabels();
    }

    private void initialiseLabels() {
        timerPrompt = new Label("", skin);
        timerPrompt.setFontScale(2);
        timerPrompt.setPosition(Consts.FRENZY_TIMER_X, Consts.FRENZY_TIMER_Y);
        timerPrompt.setVisible(false);



        Label topPrompt = new Label("", skin);
        topPrompt.setFontScale(2);
        topPrompt.setPosition(Consts.FRENZY_TOP_X,Consts.FRENZY_TOP_Y);
        topPrompt.setVisible(false);

        Label middlePrompt = new Label("", skin);
        middlePrompt.setFontScale(2);
        middlePrompt.setPosition(Consts.FRENZY_CENTER_X,Consts.FRENZY_CENTER_Y);
        middlePrompt.setVisible(false);

        Label bottomPrompt = new Label("", skin);
        bottomPrompt.setFontScale(2);
        bottomPrompt.setPosition(Consts.FRENZY_BOTTOM_X,Consts.FRENZY_BOTTOM_Y);
        bottomPrompt.setVisible(false);

        labels = new Array<>();
        labels.add(bottomPrompt);
        labels.add(middlePrompt);
        labels.add(topPrompt);

        stage.addActor(timerPrompt);
        stage.addActor(topPrompt);
        stage.addActor(middlePrompt);
        stage.addActor(bottomPrompt);
    }

    public void checkForBuildingFrenzy(float delta){
        //Check for exam time
        if ((int)timer.getElapsedTime() % 60 == 2 && !eventTriggered){
            startEvent();
        }

        if(eventTriggered){
            if(eventTimer > 0){
                eventTimer -= delta;
                updateTimer();
                if (needUpdating && !buildingManager.getCurrentlySelecting()){
                    needUpdating = false;
                    updateCountLabels();
                }else if(buildingManager.getCurrentlySelecting() && !needUpdating){
                    needUpdating = true;
                }

            }else {
                endEvent();
            }
        }
    }

    public void draw(){
        stage.act();
        stage.draw();
    }

    private void startEvent(){
        eventTriggered = true;
        eventTimer = 15f;
        announcement.showAnnouncement("Building Frenzy!");
        pickAmountOfTypes();
        toBuild = new Array<BuildingTypes>();
        originalAmounts = new Array<Integer>();
        completedType = new Array<>();

        for (int i = amountOfTypes; i > 0; i--){
            boolean allow;
            BuildingTypes possibleNewType = getRandomBuildingType();
            do{
                allow = true;
                for(BuildingTypes type : toBuild){
                    if(type.equals(possibleNewType)){
                        allow = false;
                        possibleNewType = getRandomBuildingType();
                        break;
                    }
                }
            }while (!allow);
            toBuild.add(possibleNewType);

            completedType.add(false);
        }

        if (amountOfTypes == 1){
            timerPrompt.setPosition(Consts.FRENZY_CENTER_X, Consts.FRENZY_CENTER_Y);
        }else if (amountOfTypes == 2){
            timerPrompt.setPosition(Consts.FRENZY_TOP_X, Consts.FRENZY_TOP_Y);
        }

        getOriginalAmounts();
        updateCountLabels();
    }


    private BuildingTypes getRandomBuildingType(){
        int numberOfOptions = BuildingTypes.values().length - 5;
        int random = new Random().nextInt(numberOfOptions);
        return BuildingTypes.values()[random];
    }

    private void getOriginalAmounts(){
        for(int i = 0; i < amountOfTypes; i++){
            originalAmounts.add(buildingCounts.getBuildingCounts(toBuild.get(i).ordinal()));
        }
    }



    private void getAmounts(){
        currentAmounts = new Array<>();
        for(int i = 0; i < amountOfTypes; i++){
            currentAmounts.add(buildingCounts.getBuildingCounts(toBuild.get(i).ordinal()));
        }
    }

    private void pickAmountOfTypes(){
        Random rand = new Random();
        amountOfTypes = rand.nextInt(2) + 1;
    }

    private void updateTimer(){
        timerPrompt.setText("Timer: " + String.format("%.1f", eventTimer));
        timerPrompt.setVisible(true);
    }

    private void updateCountLabels(){
        getAmounts();

        for (int i = 0; i < amountOfTypes; i++){
            int total = 4 - amountOfTypes;
            int count = Math.max(currentAmounts.get(i) - originalAmounts.get(i), 0);
            labels.get(i).setText(toBuild.get(i).name() + ": " + count + " / " + total);
            labels.get(i).setVisible(true);

            if(total <= count){
                completedType.set(i, true);
            }
        }

        Color colour = Color.RED;
        if(isAllComplete()){
           colour = Color.GREEN;
        }

        timerPrompt.setColor(colour);
        for(Label label : labels){
            label.setColor(colour);
        }
    }

    private boolean isAllComplete(){
        for(Boolean complete : completedType){
            if(!complete){
                return false;
            }
        }
        return true;
    }

    private void endEvent(){
        eventTriggered = false;

        if(isAllComplete()){
            announcement.showAnnouncement("Completed\nBuilding Frenzy!\n+15% Satisfaction");
            satisfaction.increaseSatis(15f);

        }else{
            announcement.showAnnouncement("Failed\nBuilding Frenzy!\n-15% Satisfaction");
            satisfaction.decreaseSatis(15f);
        }

        timerPrompt.setVisible(false);
        for(Label label : labels){
            label.setVisible(false);
        }
    }

}
