package com.example.trainingapp;

import java.io.Serializable;

public class ExerciseSet implements Serializable {
    private static final long serialVersionUID = 2L;
    private double weight;
    private int reps;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
