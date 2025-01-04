package com.badlogic.UniSim2.Events;

import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.buildingmanager.BuildingManager;
import com.badlogic.UniSim2.buildingmanager.types.Accomodation;
import com.badlogic.UniSim2.stats.BuildingCounts;
import com.badlogic.UniSim2.stats.Money;
import com.badlogic.UniSim2.stats.Satisfaction;
import com.badlogic.UniSim2.stats.Timer;
import com.badlogic.gdx.utils.Array;

public class Achievements {

    private Announcement announcement;
    private BuildingManager buildingManager;
    private Satisfaction satisfaction;
    private BuildingCounts buildingCounts;
    private Timer timer;
    private Money money;

    boolean doneRichy;
    boolean doneSatisfactionAndNoLab;
    boolean doneBusyUni;
    boolean doneHungryHippo;

    Array<Integer> currentSates;
    boolean check;

    public Achievements(Announcement announcement, BuildingManager buildingManager, Satisfaction satisfaction,
                        BuildingCounts buildingCounts, Timer timer, Money money) {
        this.announcement = announcement;
        this.buildingManager = buildingManager;
        this.satisfaction = satisfaction;
        this.buildingCounts = buildingCounts;
        this.timer = timer;
        this.money = money;
        doneRichy = false;
        doneSatisfactionAndNoLab = false;
        doneBusyUni = false;
        doneHungryHippo = false;
        currentSates = new Array<>();
        updateStats();
    }

    public void updateAchievements() {
        if(buildingManager.getCurrentlySelecting()){
            check = true;
        } else if (check) {
            updateStats();
        }
        checkForAchievements();
    }

    private void checkForAchievements() {
        if (buildingCounts.getFoodZoneCount() == 10 && !doneHungryHippo ||
            (buildingCounts.getTotalCount() == 1 && buildingCounts.getFoodZoneCount() == 1)){
            announcement.showAnnouncement("Achievement:\nHungry Hippo");
            doneHungryHippo = true;
        }

        if (currentSates.get(0) > 10000 && !doneBusyUni){
            announcement.showAnnouncement("Achievement:\nBusy Uni");
            doneBusyUni = true;
        }

        if (buildingCounts.getLabsCount() == 0 && satisfaction.getSatis() > 30 && !doneSatisfactionAndNoLab){
            announcement.showAnnouncement("Achievement:\nSo Good No Lab");
            doneSatisfactionAndNoLab = true;
        }

        if (money.getMoney() > 100000 && !doneRichy){
            announcement.showAnnouncement("Achievement:\nRich McGee");
            doneRichy = true;
        }
    }

    private void updateStats() {
        int students = 0;
        for(Building building : buildingManager.getPlaced()){
            if(building instanceof Accomodation){
                students += ((Accomodation)building).getRooms();
            }
        }
        currentSates.clear();
        currentSates.add(students);
    }
}
