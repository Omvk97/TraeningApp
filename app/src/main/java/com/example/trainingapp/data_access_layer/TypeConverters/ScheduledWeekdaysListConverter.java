package com.example.trainingapp.data_access_layer.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import com.example.trainingapp.Workout;
import com.google.gson.Gson;

import java.util.ArrayList;


public class ScheduledWeekdaysListConverter {
    private static final String TAG = "ScheduledWeekdaysListCo";
    private static Gson gson = new Gson();

    @TypeConverter
    public static ArrayList<Workout.WeekDay> stringToArrayList(String weekDays) {
        if (weekDays == null) {
            return new ArrayList<>();
        } else {
            String[] weekDaysSplitted = weekDays.split(",");
            ArrayList<Workout.WeekDay> weekdaysList = new ArrayList<>();
            for (String weekdayString : weekDaysSplitted) {
                try {
                    weekdaysList.add(Workout.WeekDay.valueOf(weekdayString.trim()));
                } catch (IllegalArgumentException ignored) {

                }
            }
            return weekdaysList;
        }
    }

    @TypeConverter
    public static String arrayListToString(ArrayList<Workout.WeekDay> weekDays) {
        String weekdayString = "";
        for (Workout.WeekDay weekDay : weekDays) {
            weekdayString += weekDay.toString().trim();
            weekdayString += ",";
        }
        return weekdayString;
    }

}
