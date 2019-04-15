package com.example.trainingapp;

public class RestTimer {
    private int totalMiliseconds;
    private int minutes;
    private int seconds;

    public RestTimer(int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public void setTotalMiliseconds(int totalMiliseconds) {
        this.totalMiliseconds = totalMiliseconds;
    }

    public int getTotalMiliseconds() {
        return totalMiliseconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
        calculateNewMiliSeconds();
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
        calculateNewMiliSeconds();
    }

    private void calculateNewMiliSeconds() {
        int miliSecInAMinute = 60000;
        int miliSecInASecond = 1000;
        totalMiliseconds = (minutes * miliSecInAMinute) + (seconds * miliSecInASecond);
    }

    @Override
    public String toString() {
        return "RestTimer{" +
                "minutes=" + minutes +
                ", seconds=" + seconds +
                '}';
    }
}
