package com.example.trainingapp;

public final class DatabaseContract {
    private DatabaseContract() {}

    public static class WorkoutTable {
        public static final String TABLE_NAME = "workouts";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_WORKOUT_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_LAST_TRAINING = "last_training_date";
        public static final String COLUMN_REST_TIMER = "rest_timer";
        public static final String COLUMN_SCHEDULED_WEEK_DAYS = "scheduled_week_days";

        public static final String SQL_CREATE_WORKOUT_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_WORKOUT_TITLE + " TINYTEXT NOT NULL, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_SCHEDULED_WEEK_DAYS + " TEXT, " // JSON
                + COLUMN_REST_TIMER + " TEXT, "
                + COLUMN_LAST_TRAINING + " BIGINTEGER UNSIGNED" // STORE DATE AS A LONG
                + ")";
    }

    public static class PreDefinedExerciseTable {
        public static final String TABLE_NAME = "pre_defined_exercises";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_MUSCLE_CATEGORY = "muscle_category";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_PICTURE_URL = "picture_url";

        public static final String SQL_CREATE_PREDEFINED_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_MUSCLE_CATEGORY + " TEXT, " // JSON
                + COLUMN_CATEGORY + " TEXT, " // JSON
                + COLUMN_PICTURE_URL + " TEXT"
                + ")";
    }

    public static class WorkoutExerciseTable {
        public static final String TABLE_NAME = "workout_exercises";
        public static final String COLUMN_PRE_DEFINED_EXERCISE_ID = "pre_defined_exercise_id"; // FOREIGN KEY TO COLUMN ID IN PREDEFINEDEXERCISERTABLE
        public static final String COLUMN_REST_TIMER = "rest_timer";

        public static final String SQL_CREATE_WORKOUT_EXERCISE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_PRE_DEFINED_EXERCISE_ID + " INTEGER UNSIGNED NOT NULL, "
                + COLUMN_REST_TIMER + " BIGINTEGER UNSIGNED, "
                + "FOREIGN KEY(" + COLUMN_PRE_DEFINED_EXERCISE_ID + ") REFERENCES " +
                PreDefinedExerciseTable.TABLE_NAME + "(" + PreDefinedExerciseTable.COLUMN_ID + ")"
                + ")";
    }

    public static class WorkoutExerciseLinkTable {
        public static final String TABLE_NAME = "workout_exercise_link";
        public static final String COLUMN_WORKOUT_ID = "workout_id";
        public static final String COLUMN_EXERCISE_ID = "exercise_id";

        public static final String SQL_CREATE_WORKOUT_EXERCISE_LINK_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_WORKOUT_ID + " INTEGER UNSIGNED NOT NULL, "
                + COLUMN_EXERCISE_ID + " INTEGER UNSIGNED NOT NULL, "
                + "FOREIGN KEY(" + COLUMN_WORKOUT_ID + ") REFERENCES "
                + WorkoutTable.TABLE_NAME + "(" + WorkoutTable.COLUMN_ID + "), "
                + "FOREIGN KEY(" + COLUMN_EXERCISE_ID + ") REFERENCES "
                + WorkoutExerciseTable.TABLE_NAME + "(" + WorkoutExerciseTable.COLUMN_PRE_DEFINED_EXERCISE_ID + ")"
                + ")";
    }

    public static class ExerciseSetTable {
        public static final String TABLE_NAME = "exercise_sets";
        public static final String COLUMN_EXERCISE_ID = "associated_exercise_id";
        public static final String COLUMN_REPS = "repetitions";
        public static final String COLUMN_WEIGHT = "weight";

        public static final String SQL_CREATE_EXERCISE_SET_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_EXERCISE_ID + " INTEGER UNSIGNED NOT NULL, "
                + COLUMN_WEIGHT + " FLOAT(20,2) NOT NULL, "
                + COLUMN_REPS + " SMALLINT UNSIGNED NOT NULL, "
                + "FOREIGN KEY(" + COLUMN_EXERCISE_ID + ") REFERENCES "
                + PreDefinedExerciseTable.TABLE_NAME + "(" + PreDefinedExerciseTable.COLUMN_ID + ")"
                + ")";

    }



}
