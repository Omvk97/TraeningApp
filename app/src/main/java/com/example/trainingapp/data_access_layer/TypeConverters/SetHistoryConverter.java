package com.example.trainingapp.data_access_layer.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import com.example.trainingapp.ExerciseSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SetHistoryConverter {

    private static Gson gson = new Gson();

    @TypeConverter
    public static HashMap<Date, ArrayList<ExerciseSet>> stringToMap(String dataJson) {
        if (dataJson == null) {
            return new HashMap<>();
        } else {
            Type hashmapType = new TypeToken<HashMap<Date, ArrayList<ExerciseSet>>>() {}.getType();
            return gson.fromJson(dataJson, hashmapType);
        }
    }

    @TypeConverter
    public static String hashMapToString(HashMap<Date, ArrayList<ExerciseSet>> setHistory) {
        return gson.toJson(setHistory);
    }
}
