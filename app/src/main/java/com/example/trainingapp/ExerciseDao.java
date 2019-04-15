package com.example.trainingapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert
    long insertPreDefinedExercise(PreDefinedExercise preDefinedExercise);

    @Query("SELECT * FROM preDefined_exercises ORDER BY name")
    List<PreDefinedExercise> getAllPreDefinedExercises();


}
