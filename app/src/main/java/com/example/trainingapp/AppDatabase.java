package com.example.trainingapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Workout.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract WorkoutDao workoutDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "workout-database")
            .allowMainThreadQueries() // TODO - CHANGE THIS, THIS IS JUST FOR TESTING
            .build();
        }
        return INSTANCE;
    }
}
