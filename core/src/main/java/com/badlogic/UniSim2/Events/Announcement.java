package com.badlogic.UniSim2.Events;

import com.badlogic.UniSim2.buildingmanager.types.Labs;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

public class Announcement {

    private Stage stage;
    private Skin skin;
    private Label eventAnnouncement;
    private float eventAnnouncementTime;
    /**
     * Constructs an Announcement instance
     */
    public Announcement(){
        this.stage = new Stage();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        eventAnnouncementTime = 0;
        initialiseAnnouncementLabel();
    }
    /**
     * Initializes the announcement label with default styling and position,
     * adds it to the stage, and sets it to be initially invisible.
     */
    private void initialiseAnnouncementLabel() {
        // Create the error label
        eventAnnouncement = new Label("", skin); // Initially empty
        eventAnnouncement.setColor(Color.BLUE); // Red text for error
        eventAnnouncement.setFontScale(10); // Increase size
        eventAnnouncement.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f); // Top center
        eventAnnouncement.setVisible(false); // Hidden by default
        eventAnnouncement.setAlignment(Align.center);
        stage.addActor(eventAnnouncement); // Add to the stage
    }
    /**
     * Displays an announcement message on the screen for a set duration.
     *
     * @param message the message to be displayed.
     */
    public void showAnnouncement(String message) {
        eventAnnouncement.setText(message); // Set the message text
        eventAnnouncement.setVisible(true); // Show the label
        eventAnnouncementTime = 5f; // Display for 2 seconds
    }
    /**
     * Hides the label when the display duration has expired.
     *
     * @param delta the time elapsed since the last frame.
     */
    private void updateAnnouncementLabel(float delta) {
        // Hide announcement label after timer expires
        if (eventAnnouncementTime > 0) {
            eventAnnouncementTime -= delta;
            if (eventAnnouncementTime <= 0) {
                eventAnnouncement.setVisible(false);
            }
        }
    }
    /**
     * Updates the announcement state, decrementing the display timer.
     *
     * @param delta the time elapsed since the last update.
     */
    public void update(float delta) {
        updateAnnouncementLabel(delta);
    }
    /**
     * renders the announcement.
     */
    public void draw(){
        stage.act();
        stage.draw();
    }
    /**
     * Adds a new label to the stage while preserving the current announcement label.
     * This method removes the existing announcement label temporarily, adds the new label,
     * and then re-adds the announcement label.
     *
     * @param newLabel the new Label to add to the stage.
     */
    public void addNewLabel (Label newLabel){
        eventAnnouncement.remove();
        stage.addActor(newLabel);
        stage.addActor(eventAnnouncement);
    }
}
