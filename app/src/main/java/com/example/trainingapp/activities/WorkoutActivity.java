package com.example.trainingapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.trainingapp.R;
import com.example.trainingapp.Workout;
import com.example.trainingapp.adapters.OnNoteListener;
import com.example.trainingapp.adapters.WorkoutExerciseAdapter;

public class WorkoutActivity extends AppCompatActivity implements OnNoteListener {
    private static final String TAG = "WorkoutActivity";
    public static final String SELECTED_WORKOUT_KEY = "selected workout";
    private Workout workout = null;
    private RecyclerView exerciseRV;
    private WorkoutExerciseAdapter adapter;
    private Bundle extras;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.workout_overview_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemEditWorkoutProp:
                Intent editWorkoutIntent = new Intent(this, EditWorkoutActivity.class);
                editWorkoutIntent.putExtra(SELECTED_WORKOUT_KEY, workout);
                startActivity(editWorkoutIntent);
                break;
            case R.id.menuItemAdjustTimer:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.rest_timer_dialog_view, null);
                dialogBuilder.setView(dialogView);
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void restTimerPlusClick(View v) {
        // TODO - Add 15seconds or 1 minute to the textview of the rest timer layout
    }

    public void restTimerMinusClick(View v) {
        // TODO - Minus 15seconds or 1 minute to the textview of the rest timer layout
    }

    public void restTimerOkBtn(View v) {
        // TODO - CHANGE THE TIMER IN WORKOUT TO MILISECONDS PERHAPS`?
    }

    public void restTimerCancelBtn(View v) {
        // TODO - LET THE TIMER NOT BE CHANGED
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_view);


        setUp();
        adapter = new WorkoutExerciseAdapter(workout.getExercises(), this);
        exerciseRV.setAdapter(adapter);
    }

    public void addExercise(View v) {
        Intent addExerciseIntent = new Intent(this, AddExerciseActivity.class);
        if (workout != null) {
            addExerciseIntent.putExtra(SELECTED_WORKOUT_KEY, workout);
        }
        startActivity(addExerciseIntent);
    }

    @Override
    public void onNoteClick(int position) {
        //TODO Exercise History activity
    }

    @Override
    public void onLongNoteClick(final int position) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.deletion, workout.getExercises().get(position).getExerciseName()))
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        workout.getExercises().remove(workout.getExercises().get(position));
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                }).create().show();
    }

    private void setUp() {
        exerciseRV = findViewById(R.id.routine_details_RV);
        exerciseRV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        exerciseRV.setLayoutManager(linearLayoutManager);
        extras = getIntent().getExtras();

        if (extras != null) {
            workout = (Workout) extras.getSerializable(SELECTED_WORKOUT_KEY);
        }

        Toolbar toolbar = findViewById(R.id.routineDetailsToolbar);
        toolbar.setTitle(workout.getWorkoutName());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Intent backToMainActivity = new Intent(this, MainActivity.class);
        backToMainActivity.putExtra(SELECTED_WORKOUT_KEY, workout);
        startActivity(backToMainActivity);
    }
}
