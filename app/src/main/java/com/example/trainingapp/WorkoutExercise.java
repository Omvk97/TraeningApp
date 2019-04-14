package com.example.trainingapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WorkoutExercise extends PreDefinedExercise implements Serializable {
    private static final long serialVersionUID = 2L;
    private List<ExerciseSet> sets;
    private RestTimer restTimer = null;

    public WorkoutExercise(String exerciseName) {
        this.sets = new ArrayList<>();
        int INITIAL_NUM_OF_SETS = 3;
        for (int i = 0; i < INITIAL_NUM_OF_SETS; i++) {
            sets.add(new ExerciseSet());
        }
    }

    public void setGeneralRestTimer(RestTimer workoutRestTimer) {
        this.restTimer = workoutRestTimer; // References the same timer in memory as the one in workout
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

    public List<ExerciseSet> getSets() {
        return sets;
    }

    public void setSets(List<ExerciseSet> sets) {
        this.sets = sets;
    }

    public void removeSet() {

    }

    public void addSet(ExerciseSet set) {
        sets.add(set);
    }

    public String getNumSets() {
        return Integer.toString(sets.size());
    }

    public void startTimer() {
        // TODO - Countdown timer maybe?
    }
}
