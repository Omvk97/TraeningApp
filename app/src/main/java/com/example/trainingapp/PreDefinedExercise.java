package com.example.trainingapp;

import android.widget.ImageView;

public class PreDefinedExercise { // TODO - This class is to be saved into a database with all the fields with its own table which is to be used when adding exercises, creating own exercises and so fourth
    private MuscleCategory exerciseMuscleCategory;
    private Category category;
    private ImageView picture;
    private String exerciseName = "";
    private ExerciseHistory exerciseHistory;

    public PreDefinedExercise() {
        exerciseHistory = new ExerciseHistory();
    }

    public ImageView getPicture() {
        return picture;
    }

    public void setPicture(ImageView picture) {
        this.picture = picture;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setExerciseHistory(ExerciseHistory exerciseHistory) {
        this.exerciseHistory = exerciseHistory;
    }

    public String getBestHistory(int setNumber) {
        return exerciseHistory.getBestSet(setNumber);
    }

    public String getLastPerformedSetWeight() {
        return Double.toString(exerciseHistory.getLastPerformedSet().getWeight());
    }

    public String getLastPerformedSetReps() {
        return Integer.toString(exerciseHistory.getLastPerformedSet().getReps());
    }

    public MuscleCategory getExerciseMuscleCategory() {
        return exerciseMuscleCategory;
    }

    public void setExerciseMuscleCategory(MuscleCategory exerciseMuscleCategory) {
        this.exerciseMuscleCategory = exerciseMuscleCategory;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public enum MuscleCategory {
        CORE("Core"), BICEPS("Biceps"), TRICEPS("Triceps"), LOWERBACK("Lower-Back"),
        UPPERBACK("Upper-Back"), CHEST("Chest"), LEGS("Legs"), SHOULDERS("Shoulders"), OTHER("Other");

        private final String stringRepresentation;

        MuscleCategory(String stringRepresentation) {
            this.stringRepresentation = stringRepresentation;
        }

        public String getStringRepresentation() {
            return stringRepresentation;
        }
    }

    public enum Category {
        BARBELL("Barbell"), DUMBBELL("Dumbbell"), SMITHMACHINE("Smith-Machine"), MACHINE("Machine"),
        OTHER("Other"), BODYWEIGHT("Bodyweight"), ASSISTEDBODY("Assisted-Body"), TIME("Time"), EZBAR("Ez-Bar");

        private final String stringRepresentation;

        Category(String stringRepresentation) {
            this.stringRepresentation = stringRepresentation;
        }

        public String getStringRepresentation() {
            return stringRepresentation;
        }
    }
}
