package com.badlogic.UniSim2.resources;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaskGenerator {
    private List<String> tasksList; // List of all possible tasks
    private Random random;

    public TaskGenerator(List<String> tasksList) {
        this.tasksList = new ArrayList<>(tasksList);
        this.random = new Random();
    }

     /**
     * Generates a random task from the tasks list.
     * 
     * @return A randomly selected task.
     */
    private String randomTask() {
        int randomIndex = random.nextInt(tasksList.size());
        return tasksList.get(randomIndex);
    }


    public List<TaskGroup> generateTasks(int gameDurationMinutes) {
        List<TaskGroup> taskSchedule = new ArrayList<>();
        int totalGameTime = gameDurationMinutes * 60; // Convert minutes to seconds
        int lastStartTime = 0;

        while (lastStartTime < totalGameTime) {
            String task = randomTask();

            // Generate random start time >= lastStartTime
            int startTime = lastStartTime + random.nextInt(10) + 1; 

            if (startTime + 15 > totalGameTime) {
                break; // Prevent tasks from exceeding total game time
            }

            // Add the task group to the schedule
            taskSchedule.add(new TaskGroup(task, startTime, 15));

            // Update the last start time
            lastStartTime = startTime + 15; // Fixed duration of 15 seconds
        }

        return taskSchedule;
    }

   /**
     * A class representing a single task with start time and duration.
     */
    public static class TaskGroup {
        private String task;
        private int startTime;
        private int duration;

        public TaskGroup(String task, int startTime, int duration) {
            this.task = task;
            this.startTime = startTime;
            this.duration = duration;
        }

        public String getTask() {
            return task;
        }

        public int getStartTime() {
            return startTime;
        }

        public int getDuration() {
            return duration;
        }

        @Override
        public String toString() {
            return "TaskGroup{" +
                    "task='" + task + '\'' +
                    ", startTime=" + startTime +
                    ", duration=" + duration +
                    '}';
        }
    }
}

