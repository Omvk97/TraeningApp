package com.example.trainingapp;

import android.content.Context;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Workout implements Serializable {
    private static final long serialVersionUID = 5L;

    public enum WeekDay {Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday}

    private HashMap<Exercise, Exercise> supersets = new HashMap<>(); //TODO - Implementere at man kan sætte supersæt
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private Date lastTraining;
    private String workoutName = "New Workout";
    private String description = "";
    private ArrayList<WeekDay> scheduledWeekDays = new ArrayList<>();

    public Workout() {
        lastTraining = new Date();
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

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    public void removeExercise(Exercise exercise) {
        exercises.remove(exercise);
    }

    public String getLastTraining(Context applicationContext) {
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(applicationContext);
        return dateFormat.format(lastTraining);
    }

    public void setLastTraining() {
        // TODO - GET THE CURRENT TIME WHEN THIS IS CALLED AND SET IT TO LAST TRAINING
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<WeekDay> getScheduledWeekDays() {
        return scheduledWeekDays;
    }

    public String getScheduledWeekDaysString() {
        String weekdays = "";
        for (WeekDay day : scheduledWeekDays) {
            if (scheduledWeekDays.indexOf(day) == scheduledWeekDays.size() - 1) {
                weekdays += day;
            } else {
                weekdays += day + ", ";
            }
        }
        return weekdays;
    }

    public void addWeekDay(Workout.WeekDay day) {
        scheduledWeekDays.add(day);
    }

    public void removeWeekDay(WeekDay day) {
        scheduledWeekDays.remove(day);
    }

    @Override
    public String toString() {
        return "Workout{" +
                "supersets=" + supersets +
                ", exercises=" + exercises +
                ", lastTraining=" + lastTraining +
                ", workoutName='" + workoutName + '\'' +
                ", description='" + description + '\'' +
                ", scheduledWeekDays=" + scheduledWeekDays +
                '}';
    }
}
