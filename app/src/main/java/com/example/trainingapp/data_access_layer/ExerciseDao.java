package com.example.trainingapp.data_access_layer;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.trainingapp.PreDefinedExercise;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert
    Long[] insertAllPreDefinedExercises(PreDefinedExercise... preDefinedExercises);

    @Query("SELECT * FROM preDefined_exercises ORDER BY name")
    List<PreDefinedExercise> getAllPreDefinedExercises();


}
