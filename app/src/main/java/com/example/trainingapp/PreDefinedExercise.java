package com.example.trainingapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.trainingapp.data_access_layer.TypeConverters.EnumConverter;
import com.example.trainingapp.data_access_layer.TypeConverters.SetHistoryConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

@Entity(tableName = "preDefined_exercises")
public class PreDefinedExercise {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "setHistory")
    @TypeConverters(SetHistoryConverter.class)
    private HashMap<Date, ArrayList<ExerciseSet>> sethistory = new HashMap<>();
    @ColumnInfo(name = "muscleCategory")
    @TypeConverters(EnumConverter.class)
    private MuscleCategory exerciseMuscleCategory;
    @ColumnInfo(name = "category")
    @TypeConverters(EnumConverter.class)
    private Category category;
    @ColumnInfo(name = "picture_url")
    private String pictureUriString;
    @ColumnInfo(name = "name")
    private String exerciseName;
    @ColumnInfo(name = "customExercise")
    private boolean customExercise = false;

    public PreDefinedExercise(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public PreDefinedExercise(String exerciseName, MuscleCategory muscleCategory, Category category, String pictureUriString) {
        this(exerciseName);
        this.exerciseMuscleCategory = muscleCategory;
        this.category = category;
        this.pictureUriString = pictureUriString;
    }

    public boolean isCustomExercise() {
        return customExercise;
    }

    public void setCustomExercise(boolean customExercise) {
        this.customExercise = customExercise;
    }

    public HashMap<Date, ArrayList<ExerciseSet>> getSethistory() {
        return sethistory;
    }

    public void setSethistory(HashMap<Date, ArrayList<ExerciseSet>> sethistory) {
        this.sethistory = sethistory;
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

    public String getPictureUriString() {
        return pictureUriString;
    }

    public void setPictureUriString(String pictureUriString) {
        this.pictureUriString = pictureUriString;
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
        CORE("Core"), BICEPS("Biceps"), TRICEPS("Triceps"), LOWERBACK("Lower Back"),
        UPPERBACK("Upper Back"), CHEST("Chest"), LEGS("Legs"), SHOULDERS("Shoulders"), OTHER("Other");

        private final String stringRepresentation;

        MuscleCategory(String stringRepresentation) {
            this.stringRepresentation = stringRepresentation;
        }

        public static MuscleCategory getMuscleCategory(String stringRepresentation) {
            for (MuscleCategory muscleCategory : values()) {
                if (stringRepresentation.equalsIgnoreCase(muscleCategory.getStringRepresentation())) {
                    return muscleCategory;
                }
            }
            return null;
        }

        public String getStringRepresentation() {
            return stringRepresentation;
        }
    }

    public enum Category {
        BARBELL("Barbell"), DUMBBELL("Dumbbell"), SMITHMACHINE("Smith-Machine"), MACHINE("Machine"),
        OTHER("Other"), BODYWEIGHT("Bodyweight"), ASSISTEDBODY("Assisted-Body"), TIME("Time"), EZBAR("Ez-Bar"),
        RACK("Rack");

        private final String stringRepresentation;

        Category(String stringRepresentation) {
            this.stringRepresentation = stringRepresentation;
        }

        public static Category getCategory(String stringRepresentation) {
            for (Category category : values()) {
                if (category.getStringRepresentation().equalsIgnoreCase(stringRepresentation)) {
                    return category;
                }
            }
            return null;
        }

        public String getStringRepresentation() {
            return stringRepresentation;
        }
    }
}
