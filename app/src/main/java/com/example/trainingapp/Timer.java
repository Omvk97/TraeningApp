package com.example.trainingapp;

import java.io.Serializable;

public class Timer implements Serializable {
    private static final long serialVersionUID = 4L;
    private int miliSeconds;

    public Timer(int miliSeconds) {
        this.miliSeconds = miliSeconds;
    }

    public int getMiliSeconds() {
        return miliSeconds;
    }

    public void setMiliSeconds(int miliSeconds) {
        this.miliSeconds = miliSeconds;
    }
}
