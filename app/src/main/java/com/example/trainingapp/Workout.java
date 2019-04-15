package com.example.trainingapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.trainingapp.data_access_layer.TypeConverters.DateConverter;
import com.example.trainingapp.data_access_layer.TypeConverters.ExerciseArrayListConverter;
import com.example.trainingapp.data_access_layer.TypeConverters.ScheduledWeekdaysListConverter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
@Entity(tableName = "workouts")
public class Workout {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "title")
    private String title = "New Workout";
    @ColumnInfo(name = "description")
    private String description = "";
    @ColumnInfo(name = "exercises")
    @TypeConverters(ExerciseArrayListConverter.class)
    private ArrayList<WorkoutExercise> exercises = new ArrayList<>();
    @ColumnInfo(name = "last_training_date")
    @TypeConverters(DateConverter.class)
    private Date lastTraining;
    @ColumnInfo(name = "scheduled_weekDays")
    @TypeConverters(ScheduledWeekdaysListConverter.class)
    private ArrayList<WeekDay> scheduledWeekDays = new ArrayList<>();
    @Embedded(prefix = "rest_timer_")
    private RestTimer workoutRestTimer;

    public Workout() {
        lastTraining = new Date(); // TODO - Fix so when a new workout is created the last training is not today
        workoutRestTimer = new RestTimer(1, 30);
    }

    public Workout(int id, String title, String description, long dateMiliSeconds) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.lastTraining = new Date(dateMiliSeconds);
        workoutRestTimer = new RestTimer(1, 30);

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

    public void setWorkoutRestTimer(RestTimer workoutRestTimer) {
        this.workoutRestTimer = workoutRestTimer;
    }

    public ArrayList<WorkoutExercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<WorkoutExercise> exercises) {
        this.exercises = exercises;
    }

    public void addExercise(WorkoutExercise workoutExercise) {
        workoutExercise.setGeneralRestTimer(workoutRestTimer);
        exercises.add(workoutExercise);
    }

    public void removeExercise(WorkoutExercise workoutExercise) {
        exercises.remove(workoutExercise);
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
        return workoutRestTimer.getMinutes();
    }

    public int getRestTimerSeconds() {
        return workoutRestTimer.getSeconds();
    }

    public RestTimer getWorkoutRestTimer() {
        return workoutRestTimer;
    }

    public void setGeneralRestTimer(int minutes, int seconds) {
        workoutRestTimer.setMinutes(minutes);
        workoutRestTimer.setSeconds(seconds);
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
                ", exercises=" + exercises +
                ", lastTraining=" + lastTraining +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", scheduledWeekDays=" + scheduledWeekDays +
                '}';
    }
}
