package com.example.trainingapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.trainingapp.data_access_layer.TrainingAppDatabase;

import java.io.File;

public class PreDefinedExerciseInsertionAsyncTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "PreDefinedExerciseInser";
    private TrainingAppDatabase db;
    private Context mContext;

    public PreDefinedExerciseInsertionAsyncTask(Context context) {
        db = TrainingAppDatabase.getInstance(context);
        this.mContext = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        // TODO - Give all predefined exercises their own picture in assets folder
        String resUri = Uri.fromFile(new File("//android_asset/exercise_images/test1.png")).toString();
        PreDefinedExercise deadliftBarbell = new PreDefinedExercise("Deadlift (Barbell)", PreDefinedExercise.MuscleCategory.LOWERBACK, PreDefinedExercise.Category.BARBELL, resUri);
        PreDefinedExercise ezBarCurl = new PreDefinedExercise("Ez-Bar Curl", PreDefinedExercise.MuscleCategory.BICEPS, PreDefinedExercise.Category.EZBAR, resUri);
        PreDefinedExercise seatedOverHeadPress = new PreDefinedExercise("Seated Overhead Press (Dumbbell)", PreDefinedExercise.MuscleCategory.SHOULDERS, PreDefinedExercise.Category.DUMBBELL, resUri);
        PreDefinedExercise backExtension = new PreDefinedExercise("Back Extension", PreDefinedExercise.MuscleCategory.LOWERBACK, PreDefinedExercise.Category.RACK, resUri);
        PreDefinedExercise benchPressBarbell = new PreDefinedExercise("Bench Press (Barbell)", PreDefinedExercise.MuscleCategory.CHEST, PreDefinedExercise.Category.BARBELL, resUri);
        PreDefinedExercise inclineBenchPressDumbbell = new PreDefinedExercise("Incline Bench Press (Dumbbell)", PreDefinedExercise.MuscleCategory.CHEST, PreDefinedExercise.Category.DUMBBELL, resUri);
        PreDefinedExercise inclineBenchPressBarbell = new PreDefinedExercise("Incline Bench Press (Barbell)", PreDefinedExercise.MuscleCategory.CHEST, PreDefinedExercise.Category.BARBELL, resUri);
        PreDefinedExercise lungeDumbbell = new PreDefinedExercise("Lunge (Dumbbell)", PreDefinedExercise.MuscleCategory.LEGS, PreDefinedExercise.Category.DUMBBELL, resUri);
        PreDefinedExercise squatBarbell = new PreDefinedExercise("Squat (Barbell)", PreDefinedExercise.MuscleCategory.LEGS, PreDefinedExercise.Category.BARBELL, resUri);
        db.exerciseDao().insertAllPreDefinedExercises(deadliftBarbell, ezBarCurl, seatedOverHeadPress, backExtension, benchPressBarbell, inclineBenchPressBarbell, inclineBenchPressDumbbell, lungeDumbbell, squatBarbell);
        return null;
    }
}
