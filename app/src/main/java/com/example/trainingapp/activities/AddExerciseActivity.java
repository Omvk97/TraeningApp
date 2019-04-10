package com.example.trainingapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.trainingapp.Exercise;
import com.example.trainingapp.R;
import com.example.trainingapp.Workout;
import com.example.trainingapp.adapters.AddExerciseAdapter;
import com.example.trainingapp.adapters.OnNoteListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddExerciseActivity extends AppCompatActivity implements OnNoteListener {
    private static final String TAG = "AddExerciseActivity";
    public static final String EXERCISE_TO_BE_EDITED = "exercise to be eidted";
    private List<Exercise> exercises = new ArrayList<>();
    private RecyclerView exerciseRV;
    private AddExerciseAdapter adapter;
    private Workout selectedWorkout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_exercise_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO - Search after specific exercise
        //TODO - Filter popup with exercise muscle category + exercise Equipment
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d(TAG, "onOptionsItemSelected: Home pressed");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedWorkout = (Workout) extras.getSerializable(WorkoutActivity.SELECTED_WORKOUT_KEY);
        }

        // Change toolbar title to the selected exercise
        setupToolbar();
        setUpTestExercises();

        // Get selected workout that the user wants to add exercises to

        exerciseRV = findViewById(R.id.allExercisesRV);
        exerciseRV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        exerciseRV.setLayoutManager(linearLayoutManager);

        adapter = new AddExerciseAdapter(exercises, this);
        exerciseRV.setAdapter(adapter);

    }

    public void createCustomExercise(View v) {
        // TODO Exercise edit

        /*
        Just testing
         */
        Random random = new Random();
        int randomNumber = random.nextInt(3) + 1;
        switch (randomNumber) {
            case 1:
                exercises.add(new Exercise("Pull Ups", Exercise.Category.BODYWEIGHT));
                break;
            case 2:
                exercises.add(new Exercise("Bent Over Rows", Exercise.Category.DUMBBELL));
                break;
            case 3:
                exercises.add(new Exercise("Push Ups", Exercise.Category.BODYWEIGHT));
                break;
        }
        adapter.notifyDataSetChanged();
    }

    public void setUpTestExercises() {
        exercises.add(new Exercise("Back Extenseion", Exercise.Category.OTHER));
        exercises.add(new Exercise("Seated Shoulder Press", Exercise.Category.DUMBBELL));
        exercises.add(new Exercise("Deadlift", Exercise.Category.BARBELL));
        exercises.add(new Exercise("Curl", Exercise.Category.EZBAR));
        exercises.add(new Exercise("Bench Press", Exercise.Category.DUMBBELL));
        exercises.add(new Exercise("Lunge", Exercise.Category.DUMBBELL));
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.addExerciseToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.toolbar_add_exercise));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void editExercise(View v) {
        // TODO Exercise edit and fix null point exception
        Intent editExercise = new Intent(this, EditExerciseActivity.class);
        editExercise.putExtra(EXERCISE_TO_BE_EDITED, exercises.get((Integer) v.getTag()));
        startActivity(editExercise);
    }

    @Override
    public void onNoteClick(final int position) {
        View contextView = findViewById(R.id.allExercisesRV);
        selectedWorkout.addExercise(exercises.get(position));
        String snackbarMessage = getString(R.string.exercise_added, exercises.get(position).getExerciseName());
        Snackbar addedExerciseSnack = Snackbar.make(contextView, snackbarMessage, Snackbar.LENGTH_LONG);
        addedExerciseSnack.setAction(R.string.undo_string, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedWorkout.getExercises().contains(exercises.get(position))) {
                    selectedWorkout.removeExercise(exercises.get(position));
                }
            }
        });
        addedExerciseSnack.show();
    }

    @Override
    public void onLongNoteClick(final int position) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.deletion, exercises.get(position).getExerciseName()))
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        exercises.remove(exercises.get(position));
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                }).create().show();
    }

    @Override
    public void onBackPressed() {
        Intent goBackToWorkout = new Intent(this, WorkoutActivity.class);
        goBackToWorkout.putExtra(WorkoutActivity.SELECTED_WORKOUT_KEY, selectedWorkout);
        startActivity(goBackToWorkout);
    }
}
