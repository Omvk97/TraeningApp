package com.example.trainingapp;

public final class WorkoutContract {
    private WorkoutContract() {}

    public static class WorkoutEntry {
        public static final String TABLE_NAME = "workouts";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_WORKOUT_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_LAST_TRAINING = "last_training_date";
        public static final String COLUMN_EXERCISES = "exercises";
    }

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + WorkoutEntry.TABLE_NAME + "("
            + WorkoutEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + WorkoutEntry.COLUMN_WORKOUT_TITLE + " TINYTEXT NOT NULL, "
            + WorkoutEntry.COLUMN_DESCRIPTION + " TEXT, "
            + WorkoutEntry.COLUMN_EXERCISES + " TEXT, "
            + WorkoutEntry.COLUMN_LAST_TRAINING + " BIGINTEGER UNSIGNED" // STORE DATE AS A LONG
            + ")";

}
