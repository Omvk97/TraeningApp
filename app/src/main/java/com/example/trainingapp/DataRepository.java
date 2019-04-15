package com.example.trainingapp;

import android.content.Context;
import android.os.AsyncTask;

import com.example.trainingapp.data_access_layer.TrainingAppDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DataRepository {

    private static TrainingAppDatabase db;

    private static DataRepository INSTANCE = null;

    private DataRepository(Context context) {
        db = TrainingAppDatabase.getInstance(context);
    }

    public static DataRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DataRepository(context);
        }
        return INSTANCE;
    }

    public Long insertWorkout(final Workout workout) {
        try {
            return (new InsertWorkoutAsynctask().execute(workout).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class InsertWorkoutAsynctask extends AsyncTask<Workout, Void, Long> {
        @Override
        protected Long doInBackground(Workout... workouts) {
            return db.workoutDao().insertWorkout(workouts[0]);
        }
    }

    public List<Workout> getAllWorkouts() {
        try {
            return (new getWorkoutsAsyncTask().execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class getWorkoutsAsyncTask extends AsyncTask<Void, Void, List<Workout>> {
        @Override
        protected List<Workout> doInBackground(Void... voids) {
            return db.workoutDao().getAllWorkouts();
        }
    }

    public Workout getWorkoutByID(long workoutID) {
        try {
            return (new WorkoutByIDAsyncTask().execute(workoutID).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class WorkoutByIDAsyncTask extends AsyncTask<Long, Void, Workout> {
        @Override
        protected Workout doInBackground(Long... longs) {
            return db.workoutDao().getWorkoutById(longs[0]);
        }
    }

    public void updateWorkout(Workout... workouts) {
        new UpdateWorkoutsAsyncTask().execute(workouts);
    }

    private class UpdateWorkoutsAsyncTask extends AsyncTask<Workout, Void, Void> {
        @Override
        protected Void doInBackground(Workout... workouts) {
            db.workoutDao().updateWorkout(workouts);
            return null;
        }
    }

    public void deleteWorkouts(Workout... workouts) {
        new DeleteWorkoutsAsyncTask().execute(workouts);
    }

    private class DeleteWorkoutsAsyncTask extends AsyncTask<Workout, Void, Void> {
        @Override
        protected Void doInBackground(Workout... workouts) {
            db.workoutDao().deleteWorkouts(workouts);
            return null;
        }
    }

    public Long[] insertAllPreDefinedExercises(PreDefinedExercise... preDefinedExercises) {
        try {
            return (new InsertPreDefinedExercisesAsyncTask().execute(preDefinedExercises).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class InsertPreDefinedExercisesAsyncTask extends AsyncTask<PreDefinedExercise, Void, Long[]> {
        @Override
        protected Long[] doInBackground(PreDefinedExercise... preDefinedExercises) {
            return db.exerciseDao().insertAllPreDefinedExercises(preDefinedExercises);
        }
    }

    public List<PreDefinedExercise> getAllPreDefinedExercises() {
        try {
            return (new AllPreDefinedExercisesAsyncTask().execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class AllPreDefinedExercisesAsyncTask extends AsyncTask<Void, Void, List<PreDefinedExercise>> {

        @Override
        protected List<PreDefinedExercise> doInBackground(Void... voids) {
            return db.exerciseDao().getAllPreDefinedExercises();
        }
    }


}

































