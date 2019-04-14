package com.example.trainingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.trainingapp.R;
import com.example.trainingapp.Workout;

public class EditWorkoutActivity extends AppCompatActivity {
    private static final String TAG = "EditWorkoutActivity";
    private Workout selectedWorkout;
    private TextView workoutName;
    private ToggleButton monday;
    private ToggleButton tuesday;
    private ToggleButton wednesday;
    private ToggleButton thursday;
    private ToggleButton friday;
    private ToggleButton saturday;
    private ToggleButton sunday;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedWorkout = (Workout) extras.getSerializable(WorkoutActivity.SELECTED_WORKOUT_KEY);
        }
        initialiseFields();
        setUpToolbar();
    }

    private void initialiseFields() {
        workoutName = findViewById(R.id.workoutNameTxt);
        monday = findViewById(R.id.toggleMonday);
        tuesday = findViewById(R.id.toggleTuesday);
        wednesday = findViewById(R.id.toggleWednesday);
        thursday = findViewById(R.id.toggleThursday);
        friday = findViewById(R.id.toggleFriday);
        saturday = findViewById(R.id.toggleSaturday);
        sunday = findViewById(R.id.toggleSunday);
        description = findViewById(R.id.workoutDescriptionTxt);

        workoutName.setText(selectedWorkout.getTitle());
        if (selectedWorkout.getScheduledWeekDays().size() != 0) {
            for (Workout.WeekDay weekday : selectedWorkout.getScheduledWeekDays()) {
                switch (weekday.toString().toLowerCase().trim()) {
                    case "monday":
                        monday.toggle();
                        break;
                    case "tuesday":
                        tuesday.toggle();
                        break;
                    case "wednesday":
                        wednesday.toggle();
                        break;
                    case "thursday":
                        thursday.toggle();
                        break;
                    case "friday":
                        friday.toggle();
                        break;
                    case "saturday":
                        saturday.toggle();
                        break;
                    case "sunday":
                        sunday.toggle();
                        break;
                }
            }
        }
        description.setText(selectedWorkout.getDescription());
    }

    public void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.editExerciseToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void exerciseWorkoutDayHandler(View clickedView) {
        ToggleButton selectedToggle = (ToggleButton) clickedView;
        for (Workout.WeekDay day : Workout.WeekDay.values()) {
            if (selectedToggle.getTag().toString().equalsIgnoreCase(day.toString())) {
                if (!selectedWorkout.getScheduledWeekDays().contains(day)) {
                    selectedWorkout.addWeekDay(day);
                } else {
                    selectedWorkout.removeWeekDay(day);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        // TODO - ADD THIS TO HOME BUTTON IN TOOLBAR
        selectedWorkout.setTitle(workoutName.getText().toString());
        selectedWorkout.setDescription(description.getText().toString());
        Intent goBackToWorkoutView = new Intent(this, WorkoutActivity.class);
        goBackToWorkoutView.putExtra(WorkoutActivity.SELECTED_WORKOUT_KEY, selectedWorkout);
        startActivity(goBackToWorkoutView);
    }
}
