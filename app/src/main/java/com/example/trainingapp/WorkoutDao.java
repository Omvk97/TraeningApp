package com.example.trainingapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface WorkoutDao {

    @Insert
    long insertWorkout(Workout workout);

    @Query("SELECT * FROM workouts ORDER BY id")
    List<Workout> getAllWorkouts();

    @Query("SELECT * FROM workouts WHERE id = :searchedID")
    Workout getWorkoutById(long searchedID);

    @Update
    void updateWorkout(Workout... workouts);

    @Delete
    void deleteWorkouts(Workout... workouts);

}
