package com.example.trainingapp.data_access_layer.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import com.example.trainingapp.WorkoutExercise;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ExerciseArrayListConverter {
    private static final String TAG = "ExerciseArrayListConver";
    private static Gson gson = new Gson();

    @TypeConverter
    public static ArrayList<WorkoutExercise> stringToArrayList(String dataJson) {
        if (dataJson == null) {
            return new ArrayList<>();
        } else {
            Type arrayListType = new TypeToken<ArrayList<WorkoutExercise>>() {}.getType();
            return gson.fromJson(dataJson, arrayListType);
        }
    }

    @TypeConverter
    public static String arrayListToString(ArrayList<WorkoutExercise> exercises) {
        return gson.toJson(exercises);
    }

}
