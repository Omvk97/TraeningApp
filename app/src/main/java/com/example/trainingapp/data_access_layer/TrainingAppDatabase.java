package com.example.trainingapp.data_access_layer;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.trainingapp.PreDefinedExercise;
import com.example.trainingapp.Workout;

@Database(entities = {PreDefinedExercise.class, Workout.class}, version = 2)
public abstract class TrainingAppDatabase extends RoomDatabase {

    private static TrainingAppDatabase INSTANCE = null;

    public abstract ExerciseDao exerciseDao();

    public abstract WorkoutDao workoutDao();

    public static TrainingAppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    TrainingAppDatabase.class, "training_app.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
