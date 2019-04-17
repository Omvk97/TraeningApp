package com.example.trainingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.trainingapp.DataRepository;
import com.example.trainingapp.R;
import com.example.trainingapp.Workout;

public class EditWorkoutPropertiesActivity extends AppCompatActivity {
    private static final String TAG = "EditWorkoutProperties";
    private Workout selectedWorkout;
    private TextView workoutName;
    private TextView description;
    private DataRepository data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO - Save what user has written on pause and set it back on resume
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);
        data = DataRepository.getInstance(this);
        selectedWorkout = data.getWorkoutByID(UserInteractions.getInstance().getSelectedWorkoutID());
        initialiseFields();
        setUpToolbar();
    }

    private void initialiseFields() {
        workoutName = findViewById(R.id.workoutNameTxt);
        LinearLayout toggleButtons = findViewById(R.id.toggleButtonsLayout);
        description = findViewById(R.id.workoutDescriptionTxt);

        workoutName.setText(selectedWorkout.getTitle());
        if (selectedWorkout.getScheduledWeekDays().size() != 0) {
            for (Workout.WeekDay weekday : selectedWorkout.getScheduledWeekDays()) {
                switch (weekday.toString().toLowerCase().trim()) {
                    case "monday":
                        ((ToggleButton)toggleButtons.getChildAt(0)).toggle();
                        break;
                    case "tuesday":
                        ((ToggleButton)toggleButtons.getChildAt(1)).toggle();
                        break;
                    case "wednesday":
                        ((ToggleButton)toggleButtons.getChildAt(2)).toggle();
                        break;
                    case "thursday":
                        ((ToggleButton)toggleButtons.getChildAt(3)).toggle();
                        break;
                    case "friday":
                        ((ToggleButton)toggleButtons.getChildAt(4)).toggle();
                        break;
                    case "saturday":
                        ((ToggleButton)toggleButtons.getChildAt(5)).toggle();
                        break;
                    case "sunday":
                        ((ToggleButton)toggleButtons.getChildAt(6)).toggle();
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
        // TODO - Add "error" fields if something wrong has been inserted. Textfields has an error attribute
        selectedWorkout.setTitle(workoutName.getText().toString());
        selectedWorkout.setDescription(description.getText().toString());
        data.updateWorkout(selectedWorkout);
        Intent goBackToWorkoutView = new Intent(this, WorkoutActivity.class);
        startActivity(goBackToWorkoutView);
    }
}
