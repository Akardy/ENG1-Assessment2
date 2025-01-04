package com.badlogic.UniSim2.Events;

import com.badlogic.UniSim2.buildingmanager.types.Labs;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Announcement {

    Stage stage;
    Skin skin;
    Label eventAnnouncement;
    float eventAnnouncementTime;

    public Announcement(){
        this.stage = new Stage();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        eventAnnouncementTime = 0;
        initialiseAnnouncementLabel();
    }

    private void initialiseAnnouncementLabel() {
        // Create the error label
        eventAnnouncement = new Label("", skin); // Initially empty
        eventAnnouncement.setColor(Color.BLUE); // Red text for error
        eventAnnouncement.setFontScale(10); // Increase size
        eventAnnouncement.setPosition(Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight() / 2f); // Top center
        eventAnnouncement.setVisible(false); // Hidden by default
        stage.addActor(eventAnnouncement); // Add to the stage
    }

    public void showAnnouncement(String message) {
        eventAnnouncement.setText(message); // Set the message text
        eventAnnouncement.setVisible(true); // Show the label
        eventAnnouncementTime = 5f; // Display for 2 seconds
    }

    private void updateAnnouncementLabel(float delta) {
        // Hide announcement label after timer expires
        if (eventAnnouncementTime > 0) {
            eventAnnouncementTime -= delta;
            if (eventAnnouncementTime <= 0) {
                eventAnnouncement.setVisible(false);
            }
        }
    }

    public void update(float delta) {
        updateAnnouncementLabel(delta);
    }

    public void draw(){
        stage.act();
        stage.draw();
    }
}
