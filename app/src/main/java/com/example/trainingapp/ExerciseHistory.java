package com.example.trainingapp;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExerciseHistory implements Serializable {
    private static final long serialVersionUID = 3L;
    private Date dateOfExercise;
    private ExerciseSet lastPerformedSet = new ExerciseSet();
    private HashMap<Integer, ExerciseSet> historyOfExerciseSet = new HashMap<>();

    public void saveExerciseHistory() {
    }

    public void setLastPerformedSetKg(double weight) {
        lastPerformedSet.setWeight(weight);
    }

    public void setLastPerformedSetReps(int reps) {
        lastPerformedSet.setWeight(reps);
    }

    public ExerciseSet getLastPerformedSet() {
        return lastPerformedSet;
    }

    private int calculateOneRepMax() {
        return 0;
    }

    public String getBestSet(int setNumber) {
        for (Map.Entry<Integer, ExerciseSet> set : historyOfExerciseSet.entrySet()) {
            if (set.getKey() == setNumber) {
                return set.getValue().getWeight() + " X " + set.getValue().getReps();
            }
        }
        return "";
    }


}
