package com.example.trainingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;

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
        db.execSQL(WorkoutContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WorkoutContract.WorkoutEntry.TABLE_NAME);
        onCreate(db);
    }

    public long insertWorkout(Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_TITLE, workout.getTitle());
        values.put(WorkoutContract.WorkoutEntry.COLUMN_DESCRIPTION, workout.getDescription());
        values.put(WorkoutContract.WorkoutEntry.COLUMN_LAST_TRAINING, workout.getLastTraining().getTime());

        long idOfInsertedWorkout = db.insert(WorkoutContract.WorkoutEntry.TABLE_NAME, null, values);

        db.close();
        return idOfInsertedWorkout;
    }

    public Workout getWorkout(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(WorkoutContract.WorkoutEntry.TABLE_NAME,
                new String[]{WorkoutContract.WorkoutEntry.COLUMN_ID, WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_TITLE, WorkoutContract.WorkoutEntry.COLUMN_DESCRIPTION, WorkoutContract.WorkoutEntry.COLUMN_LAST_TRAINING},
                WorkoutContract.WorkoutEntry.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Workout workout = new Workout(
                cursor.getInt(cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_TITLE)),
                cursor.getString(cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_DESCRIPTION)),
                cursor.getLong(cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_LAST_TRAINING))
        );
        db.close();
        return workout;
    }

    public List<Workout> getAllWorkouts() {
        List<Workout> workouts = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + WorkoutContract.WorkoutEntry.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Workout workout = new Workout(
                        cursor.getInt(cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_TITLE)),
                        cursor.getString(cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_DESCRIPTION)),
                        cursor.getLong(cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_LAST_TRAINING))
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
        values.put(WorkoutContract.WorkoutEntry.COLUMN_WORKOUT_TITLE, workout.getTitle());
        values.put(WorkoutContract.WorkoutEntry.COLUMN_DESCRIPTION, workout.getDescription());
        values.put(WorkoutContract.WorkoutEntry.COLUMN_LAST_TRAINING, workout.getLastTraining().getTime());
        return db.update(WorkoutContract.WorkoutEntry.TABLE_NAME, values, WorkoutContract.WorkoutEntry.COLUMN_ID +
                " = ?", new String[]{String.valueOf(workout.getId())});
    }

    public void deleteWorkout(Workout workout) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(WorkoutContract.WorkoutEntry.TABLE_NAME, WorkoutContract.WorkoutEntry.COLUMN_ID + " = ?",
                new String[]{String.valueOf(workout.getId())});
    }
}
