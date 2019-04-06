package com.example.trainingapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class TrainingRoutine {

    public enum DayOfWeek {Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday}

    private HashMap<Exercise, Exercise> supersets = new HashMap<>();
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private Date startDate;
    private Date schedueledEndDate;
    private Date lastTraining;
    private String routineTitle;
    private String description;
    private ArrayList<String> routinesNotes = new ArrayList<>();
    private ArrayList<DayOfWeek> scheduledWeekDays = new ArrayList<>();

    public TrainingRoutine(String routineTitle) {
        this.routineTitle = routineTitle;
    }

    public int calculateTotalSets() {
        //TODO for every exercise get total sets and add it to a totalsets counter and return it
        return 0;
    }

    public HashMap<Exercise, Exercise> getSupersets() {
        return supersets;
    }

    public void setSupersets(HashMap<Exercise, Exercise> supersets) {
        this.supersets = supersets;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getSchedueledEndDate() {
        return schedueledEndDate;
    }

    public void setSchedueledEndDate(Date schedueledEndDate) {
        this.schedueledEndDate = schedueledEndDate;
    }

    public Date getLastTraining() {
        return lastTraining;
    }

    public void setLastTraining(Date lastTraining) {
        this.lastTraining = lastTraining;
    }

    public String getRoutineTitle() {
        return routineTitle;
    }

    public void setRoutineTitle(String routineTitle) {
        this.routineTitle = routineTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getRoutinesNotes() {
        return routinesNotes;
    }

    public void setRoutinesNotes(ArrayList<String> routinesNotes) {
        this.routinesNotes = routinesNotes;
    }

    public String getScheduledWeekDays() {
        String weekdaysString = "";
        for (DayOfWeek trainingDay : scheduledWeekDays) {
            weekdaysString += trainingDay + ", ";
        }
        return weekdaysString;
    }

    public void setScheduledWeekDays(ArrayList<DayOfWeek> scheduledWeekDays) {
        this.scheduledWeekDays = scheduledWeekDays;
    }
}
