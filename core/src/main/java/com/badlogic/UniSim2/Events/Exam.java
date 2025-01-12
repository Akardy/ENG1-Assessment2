package com.badlogic.UniSim2.Events;

import com.badlogic.UniSim2.buildingmanager.Building;
import com.badlogic.UniSim2.buildingmanager.BuildingManager;
import com.badlogic.UniSim2.buildingmanager.types.Accomodation;
import com.badlogic.UniSim2.stats.Satisfaction;
import com.badlogic.UniSim2.stats.Timer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

public class Exam {

    private Stage stage;
    private Skin skin;
    private Timer timer;
    private BuildingManager buildingManager;

    private float timerTime;

    private boolean examHasTriggered;

    private Label examTimer;
    private Label examSpaces;
    private Label examStudents;

    private boolean checkChange;

    private Satisfaction satisfaction;

    private Announcement announcement;
    /**
     * Constructs an Exam event.
     *
     * @param timer            the Timer object tracking game time.
     * @param buildingManager  the BuildingManager handling building logic.
     * @param satisfaction     the Satisfaction object for adjusting satisfaction levels.
     * @param announcement     the Announcement system for displaying messages.
     */
    public Exam(Timer timer, BuildingManager buildingManager, Satisfaction satisfaction, Announcement announcement) {
        this.stage = new Stage();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.timer = timer;
        examHasTriggered = false;
        this.buildingManager = buildingManager;
        this.timerTime = 0;
        this.satisfaction = satisfaction;
        this.announcement = announcement;
    }
    /**
     * Draws the exam-related UI elements.
     */
    public void draw(){
        stage.act();
        stage.draw();
    }
    /**
     * Checks if the exam event should be triggered based on the elapsed time.
     * Updates the exam timer and building statistics during the event.
     *
     * @param delta the time elapsed since the last check.
     */
    public void checkTriggeringEvent(float delta){
        if (timerTime > 0){
            timerTime -= delta;
            updateExamTimer();
        }else if(examHasTriggered){
            endExamEvent();
        }
        if(examHasTriggered){
            if(buildingManager.getCurrentlySelecting()) {
                checkChange = true;
            }else if(checkChange){
                checkChange = false;
                updateExamAmounts();
            }
        }
        //Check for exam time
        if ((int)timer.getElapsedTime() % 60 == 50 && !examHasTriggered){
            examHasTriggered = true;
            announcement.showAnnouncement("EXAM TIME!!!");
            examEvent();
        }
    }
    /**
     * Initiates the exam event,
     * setting the exam timer, and initializing the UI display.
     */
    private void examEvent(){
        Array<Integer> stats= getExamStats();
        timerTime = 15f;
        initialiseShowExam(stats.get(0), stats.get(1));
    }
    /**
     * Initializes and displays exam-related UI labels with given stats.
     *
     * @param examSpace       the total available exam space.
     * @param amountOfStudents the total number of students required for the exam.
     */

    private void initialiseShowExam(int examSpace, int amountOfStudents){
        examTimer = new Label("Timer: " + String.format("%.1f", timerTime), skin);
        examTimer.setFontScale(3);
        examTimer.setPosition(Gdx.graphics.getWidth() / 6.4f, 130f);
        examTimer.setVisible(true);

        examSpaces = new Label("Amount of Exam Space: " + examSpace, skin);
        examSpaces.setFontScale(3);
        examSpaces.setPosition(Gdx.graphics.getWidth() / 6.4f, 80f);
        examSpaces.setVisible(true);

        examStudents = new Label("Amount of Students: " + amountOfStudents, skin);
        examStudents.setFontScale(3);
        examStudents.setPosition(Gdx.graphics.getWidth() /6.4f, 30f);
        examStudents.setVisible(true);

        if (examSpace < amountOfStudents){
            examTimer.setColor(Color.RED);
            examSpaces.setColor(Color.RED);
            examStudents.setColor(Color.RED);
        }else{
            examTimer.setColor(Color.GREEN);
            examSpaces.setColor(Color.GREEN);
            examStudents.setColor(Color.GREEN);
        }

        stage.addActor(examTimer);
        stage.addActor(examSpaces);
        stage.addActor(examStudents);
    }

    private void updateExamTimer(){
        examTimer.setText("Timer: " + String.format("%.1f", timerTime));
    }
    /**
     * Updates the labels for exam space and student count based on current stats.
     * Change label colours depending on whether exam space meets student requirements.
     * Green if met, red if not
     */
    private void updateExamAmounts(){
        Array<Integer> stats = getExamStats();
        examSpaces.setText("Amount of Exam Space: " + stats.get(0));
        examStudents.setText("Amount of Students: " + stats.get(1));

        if (stats.get(0) < stats.get(1)){
            examTimer.setColor(Color.RED);
            examSpaces.setColor(Color.RED);
            examStudents.setColor(Color.RED);
        }else {
            examTimer.setColor(Color.GREEN);
            examSpaces.setColor(Color.GREEN);
            examStudents.setColor(Color.GREEN);
        }
    }

    /**
     * Calculates and retrieves current exam stats - total exam space
     * and number of students.
     *
     * @return an Array of two integers: [examSpace, amountOfStudents].
     */
    private Array<Integer> getExamStats() {
        Array<Building> placed = buildingManager.getPlaced();
        int examSpace = 0;
        int amountOfStudents = 0;

        for (Building building : placed) {
            if (!building.isBroken()) {
                examSpace += building.getExamSpace();

                if (building instanceof Accomodation) {
                    amountOfStudents += ((Accomodation) building).getRooms();
                }
            }
        }

        Array<Integer> stats = new Array<>();
        stats.add(examSpace);
        stats.add(amountOfStudents);
        return stats;
    }
    /**
     * Ends the exam event, hides exam related UI elements, and adjusts satisfaction
     * based on exam results, pass or fail.
     */

    private void endExamEvent(){
        examHasTriggered = false;
        examTimer.setVisible(false);
        examSpaces.setVisible(false);
        examStudents.setVisible(false);

        Array<Integer> stats = getExamStats();

        if (stats.get(0) >= stats.get(1)){
            announcement.showAnnouncement("Exam Passed! \n+ 10% Satisfaction!");
            satisfaction.increaseSatis(10);
        }else {
            announcement.showAnnouncement("Exam Failed! \n- 10% Satisfaction!");
            satisfaction.decreaseSatis(10);
        }
    }
}
