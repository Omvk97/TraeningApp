package com.example.trainingapp.data_access_layer.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import com.example.trainingapp.PreDefinedExercise;

public class EnumConverter {
    @TypeConverter
    public static PreDefinedExercise.MuscleCategory getMuscleCategory(String stringrepresentation) {
        return PreDefinedExercise.MuscleCategory.getMuscleCategory(stringrepresentation);
    }

    @TypeConverter
    public static String getStringRepresentation(PreDefinedExercise.MuscleCategory muscleCategory) {
        return muscleCategory.getStringRepresentation();
    }

    @TypeConverter
    public static PreDefinedExercise.Category getCategory(String stringrepresentation) {
        return PreDefinedExercise.Category.getCategory(stringrepresentation);
    }

    @TypeConverter
    public static String getStringRepresentation(PreDefinedExercise.Category category) {
        return category.getStringRepresentation();
    }
}
