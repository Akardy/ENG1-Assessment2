package com.badlogic.UniSim2.buildingmanager.types;

public enum BuildingTypes {
    DERWENT("Accommodation"),
    GOODRICKE("Accommodation"),
    CONSTANTINE("Accommodation"),
    NISA("FoodZone"),
    GREGGS("FoodZone"),
    DERWENTDINING("FoodZone"),
    GYM("Recreational"),
    SOCIETYBUILDING("Recreational"),
    PIAZZA("LectureHall"),
    CENTRALHALL("LectureHall"),
    SOFTWARELABS("Labs"),
    HARDWARELABS("Labs"),
    NATURE("Recreational"),
    LIBRARY("Library"),
    ACCOMODATION("N/A"),
    LECTUREHALL("N/A"),
    LABS("N/A"),
    FOODZONE("N/A"),
    RECREATIONAL("N/A");

    private String type;

    BuildingTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
