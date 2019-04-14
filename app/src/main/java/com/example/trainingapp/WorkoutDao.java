package com.example.trainingapp;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface WorkoutDao {
    @Query("SELECT * FROM workout WHERE id = :idOfWorkout")
    Workout getWorkoutByID(long idOfWorkout);

    @Query("SELECT * FROM workout")
    List<Workout> getAllWorkouts();

    @Query("SELECT COUNT(*) FROM workout")
    int countNumOfWorkouts();

    @Insert
    long insertWorkout(Workout workout);

    @Update
    void updateWorkout(Workout workout);

    @Delete
    void deleteWorkout(Workout workout);
}
