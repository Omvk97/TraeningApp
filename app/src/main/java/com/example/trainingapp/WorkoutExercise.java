package com.example.trainingapp;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class WorkoutExercise extends PreDefinedExercise {
    private RestTimer restTimer = null;
    private Pair<Date, ArrayList<ExerciseSet>> workoutToBePerformedScabelon;

    public WorkoutExercise(PreDefinedExercise preDefinedExercise) {
        super(preDefinedExercise.getExerciseName(), preDefinedExercise.getExerciseMuscleCategory(),
                preDefinedExercise.getCategory(), preDefinedExercise.getPictureUriString());
        setId(preDefinedExercise.getId());
        workoutToBePerformedScabelon = new Pair<>(new Date(), new ArrayList<>(Arrays.asList(new ExerciseSet(), new ExerciseSet(), new ExerciseSet())));
    }


    public void setGeneralRestTimer(Workout workout) {
        this.restTimer = workout.getWorkoutRestTimer(); // References the same timer in memory as the one in workout
    }

    public void setSpecificRestTimer(int minutes, int seconds) {
        this.restTimer = new RestTimer(minutes, seconds);
    }

    public RestTimer getRestTimer() {
        return restTimer;
    }

    public String getRestTimerString() {
        return restTimer.getMinutes() + " : " + restTimer.getSeconds();
    }

    public ArrayList<ExerciseSet> getSets() {
        return workoutToBePerformedScabelon.second;
    }

    public void setSets(List<ExerciseSet> sets, Date date) {
        this.workoutToBePerformedScabelon = new Pair<>(date, (ArrayList<ExerciseSet>) sets);
    }

    public void removeSet(ExerciseSet set) {
        workoutToBePerformedScabelon.second.remove(set);
    }

    public void addSet() {
        workoutToBePerformedScabelon.second.add(new ExerciseSet());
    }

    public void startTimer() {
        // TODO - Countdown timer maybe?
    }

    public void updateWorkoutExercise(PreDefinedExercise preDefinedExercise) {
        setId(preDefinedExercise.getId());
        setExerciseName(preDefinedExercise.getExerciseName());
        setExerciseMuscleCategory(preDefinedExercise.getExerciseMuscleCategory());
        setCategory(preDefinedExercise.getCategory());
        setPictureUriString(preDefinedExercise.getPictureUriString());
    }
}
