package com.example.trainingapp;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Exercise implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int INITIAL_NUM_OF_SETS = 3;
    private MuscleCategory exerciseMuscleCategory;
    private Category category;
    private ImageView picture;
    private String exerciseName;
    private List<String> notes;
    private List<ExerciseSet> sets;
    private ExerciseHistory exerciseHistory;
    private Timer restTimer;
    private List<String> exerciseNotes = new ArrayList<>(); // TODO - Gør så man kan skrive noter til ens workout

    public Exercise(String exerciseName, Category category) {
        this.picture = picture;
        this.exerciseName = exerciseName;
        this.category = category;
        this.notes = new ArrayList<>();
        this.sets = new ArrayList<>();
        for (int i = 0; i < INITIAL_NUM_OF_SETS; i++) {
            sets.add(new ExerciseSet());
        }
        restTimer = new Timer(90000);
    }

    public ImageView getPicture() {
        return picture;
    }

    public void setPicture(ImageView picture) {
        this.picture = picture;
    }

    public String getExerciseName() {
        if (!category.toString().equalsIgnoreCase("other")) {
            return category.toString().substring(0, 1).toUpperCase() + category.toString().substring(1).toLowerCase() + " " + exerciseName;
        } else {
            return exerciseName;
        }

    }

    public List<ExerciseSet> getSets() {
        return sets;
    }

    public void removeSet() {

    }

    public void addSet(ExerciseSet set) {
        sets.add(set);
    }

    public String getNumSets() {
        return Integer.toString(sets.size());
    }

    public String getBestHistory() {
        return "120 X 6";
    }

    public String getRestTimer() {
        final int miliSecInAMinute = 60000;
        final int miliSecInASecond = 1000;
        int minutes = restTimer.getMiliSeconds() / miliSecInAMinute;
        int seconds = (restTimer.getMiliSeconds() % miliSecInAMinute) / miliSecInASecond;
        return minutes + " : " + seconds;
    }

    public enum MuscleCategory {
        CORE, BICEPS, TRICEPS, BACK, CHEST, LEGS, SHOULDERS, OTHER
    }

    public enum Category {
        BARBELL, DUMBBELL, SMITHMACHINE, MACHINE, OTHER, BODYWEIGHT, ASSISTEDBODY, TIME, EZBAR;

    }
}
