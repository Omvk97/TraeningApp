package com.example.trainingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;

    private static final String DATABASE_NAME = "workouts_db";

    private static DatabaseHelper instance = null;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.WorkoutTable.SQL_CREATE_WORKOUT_TABLE);
        db.execSQL(DatabaseContract.PreDefinedExerciseTable.SQL_CREATE_PREDEFINED_TABLE);
        db.execSQL(DatabaseContract.WorkoutExerciseTable.SQL_CREATE_WORKOUT_EXERCISE_TABLE);
        db.execSQL(DatabaseContract.ExerciseSetTable.SQL_CREATE_EXERCISE_SET_TABLE);
        db.execSQL(DatabaseContract.WorkoutExerciseLinkTable.SQL_CREATE_WORKOUT_EXERCISE_LINK_TABLE);
        /*db.execSQL(DatabasePreDefinedExercises.insertion);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.WorkoutTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.PreDefinedExerciseTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.WorkoutExerciseTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.ExerciseSetTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.WorkoutExerciseLinkTable.TABLE_NAME);
        onCreate(db);
    }

    public long insertWorkout(Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.WorkoutTable.COLUMN_WORKOUT_TITLE, workout.getTitle());
        values.put(DatabaseContract.WorkoutTable.COLUMN_DESCRIPTION, workout.getDescription());
        values.put(DatabaseContract.WorkoutTable.COLUMN_LAST_TRAINING, workout.getLastTraining().getTime());
        String scheduledWeekDayJson = new Gson().toJson(workout.getScheduledWeekDays());
        values.put(DatabaseContract.WorkoutTable.COLUMN_SCHEDULED_WEEK_DAYS, scheduledWeekDayJson);
        values.put(DatabaseContract.WorkoutTable.COLUMN_REST_TIMER, workout.getWorkoutRestTimer().toString());

        long idOfInsertedWorkout = db.insert(DatabaseContract.WorkoutTable.TABLE_NAME, null, values);

        db.close();
        return idOfInsertedWorkout;
    }

    public Workout getWorkout(long id) {
        Workout workout = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DatabaseContract.WorkoutTable.TABLE_NAME,
                new String[]{DatabaseContract.WorkoutTable.COLUMN_ID, DatabaseContract.WorkoutTable.COLUMN_WORKOUT_TITLE, DatabaseContract.WorkoutTable.COLUMN_DESCRIPTION, DatabaseContract.WorkoutTable.COLUMN_LAST_TRAINING},
                DatabaseContract.WorkoutTable.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            workout = new Workout(
                    cursor.getInt(cursor.getColumnIndex(DatabaseContract.WorkoutTable.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.WorkoutTable.COLUMN_WORKOUT_TITLE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.WorkoutTable.COLUMN_DESCRIPTION)),
                    cursor.getLong(cursor.getColumnIndex(DatabaseContract.WorkoutTable.COLUMN_LAST_TRAINING))
            );
        }
        db.close();
        return workout;
    }

    public List<Workout> getAllWorkouts() {
        List<Workout> workouts = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DatabaseContract.WorkoutTable.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Workout workout = new Workout(
                        cursor.getInt(cursor.getColumnIndex(DatabaseContract.WorkoutTable.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.WorkoutTable.COLUMN_WORKOUT_TITLE)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.WorkoutTable.COLUMN_DESCRIPTION)),
                        cursor.getLong(cursor.getColumnIndex(DatabaseContract.WorkoutTable.COLUMN_LAST_TRAINING))
                );
                workouts.add(workout);
            } while (cursor.moveToNext());
        }

        db.close();
        return workouts;
    }

    public int updateWorkout(Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.WorkoutTable.COLUMN_WORKOUT_TITLE, workout.getTitle());
        values.put(DatabaseContract.WorkoutTable.COLUMN_DESCRIPTION, workout.getDescription());
        values.put(DatabaseContract.WorkoutTable.COLUMN_LAST_TRAINING, workout.getLastTraining().getTime());
        return db.update(DatabaseContract.WorkoutTable.TABLE_NAME, values, DatabaseContract.WorkoutTable.COLUMN_ID +
                " = ?", new String[]{String.valueOf(workout.getId())});
    }

    public void deleteWorkout(Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DatabaseContract.WorkoutTable.TABLE_NAME, DatabaseContract.WorkoutTable.COLUMN_ID + " = ?",
                new String[]{String.valueOf(workout.getId())});
    }
}
