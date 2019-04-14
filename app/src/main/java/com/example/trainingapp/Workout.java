package com.example.trainingapp;

import android.content.Context;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workout")
public class Workout implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "title")
    private String title = "New Workout";
    @ColumnInfo(name = "description")
    private String description = "";
    private ArrayList<WorkoutExercise> mWorkoutExercises = new ArrayList<>();
    @ColumnInfo(name = "last_training_date")
    private Date lastTraining;
    private ArrayList<WeekDay> scheduledWeekDays = new ArrayList<>();
    private RestTimer generalRestTimer;

    public Workout() {
        lastTraining = new Date(); // TODO - Fix so when a new workout is created the last training is not today
        generalRestTimer = new RestTimer(1, 30);
    }

    public Workout(int id, String title, String description, long dateMiliSeconds) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.lastTraining = new Date(dateMiliSeconds);
        generalRestTimer = new RestTimer(1, 30);

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getLastTraining() {
        return lastTraining;
    }

    public void setLastTraining(Date lastTraining) {
        this.lastTraining = lastTraining;
    }

    public void setScheduledWeekDays(ArrayList<WeekDay> scheduledWeekDays) {
        this.scheduledWeekDays = scheduledWeekDays;
    }

    public void setGeneralRestTimer(RestTimer generalRestTimer) {
        this.generalRestTimer = generalRestTimer;
    }

    public ArrayList<WorkoutExercise> getWorkoutExercises() {
        return mWorkoutExercises;
    }

    public void setWorkoutExercises(ArrayList<WorkoutExercise> workoutExercises) {
        this.mWorkoutExercises = workoutExercises;
    }

    public void addExercise(WorkoutExercise workoutExercise) {
        mWorkoutExercises.add(workoutExercise);
    }

    public void removeExercise(WorkoutExercise workoutExercise) {
        mWorkoutExercises.remove(workoutExercise);
    }

    public String getLastTraining(Context applicationContext) {
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(applicationContext);
        return dateFormat.format(lastTraining);
    }

    public void setLastTraining() {
        // TODO - GET THE CURRENT TIME WHEN THIS IS CALLED AND SET IT TO LAST TRAINING
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<WeekDay> getScheduledWeekDays() {
        return scheduledWeekDays;
    }

    public String getScheduledWeekDaysString() {
        String weekdays = "";
        for (WeekDay day : scheduledWeekDays) {
            if (scheduledWeekDays.indexOf(day) == scheduledWeekDays.size() - 1) {
                weekdays += day;
            } else {
                weekdays += day + ", ";
            }
        }
        return weekdays;
    }

    public void addWeekDay(Workout.WeekDay day) {
        scheduledWeekDays.add(day);
    }

    public void removeWeekDay(WeekDay day) {
        scheduledWeekDays.remove(day);
    }

    public int getRestTimerMinutes() {
        return generalRestTimer.getMinutes();
    }

    public int getRestTimerSeconds() {
        return generalRestTimer.getSeconds();
    }

    public RestTimer getGeneralRestTimer() {
        return generalRestTimer;
    }

    public void setGeneralRestTimer(int minutes, int seconds) {
        generalRestTimer.setMinutes(minutes);
        generalRestTimer.setSeconds(seconds);
    }

    public enum WeekDay {
        SUNDAY(1), MONDAY(2), TUESDAY(3), WEDNESDAY(4), THURSDAY(5), FRIDAY(6), SATURDAY(7); // Corresponds to Java.util.Calendars weekdays

        private int weekdayValue;

        WeekDay(int i) {
            this.weekdayValue = i;
        }

        public int getWeekdayValue() {
            return weekdayValue;
        }
    }

    @Override
    public String toString() {
        return "Workout{" +
                ", mWorkoutExercises=" + mWorkoutExercises +
                ", lastTraining=" + lastTraining +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", scheduledWeekDays=" + scheduledWeekDays +
                '}';
    }
}
