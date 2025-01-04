package com.badlogic.UniSim2.stats;

public class BuildingCounts {

    private int[] buildingCounts;
    private int accommodation;
    private int foodZone;
    private int labs;
    private int lectureHall;
    private int libary;
    private int recreational;
    private int total;

    public BuildingCounts() {
        buildingCounts = new int[14];
        for (int i = 0; i < 14; i++) {
            buildingCounts[i] = 0;
        }
        accommodation = 0;
        foodZone = 0;
        labs = 0;
        lectureHall = 0;
        libary = 0;
        recreational = 0;
    }


    public int getBuildingCounts(int index) {
        return buildingCounts[index];
    }

    public int getAccommodationCount() {
        return accommodation;
    }

    public int getFoodZoneCount() {
        return foodZone;
    }

    public int getLabsCount() {
        return labs;
    }

    public int getLectureHallCount() {
        return lectureHall;
    }

    public int getLibaryCount() {
        return libary;
    }

    public int getRecreationalCount() {
        return recreational;
    }

    public int getTotalCount() {return total;}

    public void incrementAccomadation(int index) {
        accommodation++;
        total++;
        buildingCounts[index]++;
    }

    public void incrementFoodZones(int index) {
        foodZone++;
        total++;
        buildingCounts[index]++;
    }

    public void incrementLabs(int index) {
        labs++;
        total++;
        buildingCounts[index]++;
    }

    public void incrementLectureHall(int index) {
        lectureHall++;
        total++;
        buildingCounts[index]++;
    }

    public void incrementLibary(int index) {
        libary++;
        total++;
        buildingCounts[index]++;
    }

    public void incrementRecreational(int index) {
        recreational++;
        total++;
        buildingCounts[index]++;
    }

    public void decrementAccomadation(int index) {
        accommodation--;
        total--;
        buildingCounts[index]--;
    }

    public void decrementFoodZone(int index) {
        foodZone--;
        total--;
        buildingCounts[index]--;
    }

    public void decrementLabs(int index) {
        labs--;
        total--;
        buildingCounts[index]--;
    }

    public void decrementLectureHall(int index) {
        lectureHall--;
        total--;
        buildingCounts[index]--;
    }

    public void decrementLibary(int index) {
        libary--;
        total--;
        buildingCounts[index]--;
    }

    public void decrementRecreational(int index) {
        recreational--;
        total--;
        buildingCounts[index]--;
    }
}
