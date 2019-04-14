package com.example.trainingapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class PreDefinedExercise { // TODO - This class is to be saved into a database with all the fields with its own table which is to be used when adding exercises, creating own exercises and so fourth
    private long id;
    private HashMap<Date, ArrayList<ExerciseSet>> sethistory;
    private MuscleCategory exerciseMuscleCategory;
    private Category category;
    private String pictureURL;
    private String exerciseName;

    public PreDefinedExercise(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastPerformedSetWeight() {
        /*return Double.toString(lastPerformedSet.getWeight());*/
        // TODO - return the latest weight based on date from setHIstory Map
        return "50";
    }

    public String getLastPerformedSetReps() {
        /*return Integer.toString(lastPerformedSet.getReps());*/
        // TODO - return the latest reps based on date from setHIstory Map
        return "8";
    }

    public String getBestSet(int index) {
        // TODO BASED ON INDEX OF ARRAYLIST (THE INDEX REPRESENTS THE ORDER OF THE SETS PERFORMED)
        return "50 X 22";
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void addNewExerciseSetHistory(Date date) {
        sethistory.put(date, new ArrayList<>(Arrays.asList(new ExerciseSet(), new ExerciseSet(), new ExerciseSet()))); // TODO - WHEN A WORKOUT IS STARTED A NEW ENTRY IS TO BE MADE AND RECORDED HERE SO FUCKING SMART DUDE
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
