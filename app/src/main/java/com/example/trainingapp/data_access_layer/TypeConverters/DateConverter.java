package com.example.trainingapp.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static Date getDate(long miliseconds) {
        return new Date(miliseconds);
    }

    @TypeConverter
    public static long getMiliSeconds(Date date) {
        return date.getTime();
    }

}
