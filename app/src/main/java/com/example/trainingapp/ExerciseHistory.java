package com.example.trainingapp;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ExerciseHistory implements Serializable {
    private static final long serialVersionUID = 3L;
    private Date dateOfExercise;
    private HashMap<Integer, ExerciseSet> historyOfExerciseSet;

    public void saveExerciseHistory(Exercise exerciseToBeSaved) {
        List<ExerciseSet> allSetsFromExercise = exerciseToBeSaved.getSets();
        for (ExerciseSet set : allSetsFromExercise) {
            historyOfExerciseSet.put(allSetsFromExercise.indexOf(set), set);
        }
    }

    private int calculateOneRepMax() {
        return 0;
    }


}
