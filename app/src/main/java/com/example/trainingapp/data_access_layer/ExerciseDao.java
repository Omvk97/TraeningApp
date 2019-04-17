package com.example.trainingapp.data_access_layer;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.trainingapp.PreDefinedExercise;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert
    Long[] insertAllPreDefinedExercises(PreDefinedExercise... preDefinedExercises);

    @Query("SELECT * FROM preDefined_exercises ORDER BY name")
    List<PreDefinedExercise> getAllPreDefinedExercises();

    @Query("SELECT * FROM preDefined_exercises WHERE id = :searchedID")
    PreDefinedExercise getPreDefinedExerciseByID(long searchedID);

    @Update
    void updateExercise(PreDefinedExercise... preDefinedExercises);

    @Delete
    void deleteExercise(PreDefinedExercise... preDefinedExercises);
}
