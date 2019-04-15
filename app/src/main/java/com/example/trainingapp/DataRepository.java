package com.example.trainingapp;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DatabaseManager {

    private static TrainingAppDatabase db;

    private static DatabaseManager INSTANCE = null;

    private DatabaseManager(Context context) {
        db = TrainingAppDatabase.getInstance(context);
    }

    public static DatabaseManager getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseManager(context);
        }
        return INSTANCE;
    }

    public static void insertWorkout(final Workout workout) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.workoutDao().insertWorkout(workout);
                return null;
            }
        }.execute();
    }

    public List<Workout> getAllWorkouts() {
        try {
            new getWorkoutsAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class getWorkoutsAsyncTask extends AsyncTask<Void, Void, List<Workout>> {

        @Override
        protected List<Workout> doInBackground(Void... voids) {
            return db.workoutDao().getAllWorkouts();
        }
    }
}
