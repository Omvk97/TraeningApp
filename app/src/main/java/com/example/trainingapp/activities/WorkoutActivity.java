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
import android.widget.EditText;
import android.widget.Toast;

import com.example.trainingapp.AppDatabase;
import com.example.trainingapp.R;
import com.example.trainingapp.Workout;
import com.example.trainingapp.adapters.OnNoteListener;
import com.example.trainingapp.adapters.WorkoutExerciseAdapter;

public class WorkoutActivity extends AppCompatActivity implements OnNoteListener {
    private static final String TAG = "WorkoutActivity";
    public static final String SELECTED_WORKOUT_KEY = "selected selectedWorkout";
    private Workout selectedWorkout = null;
    private RecyclerView exerciseRV;
    private WorkoutExerciseAdapter adapter;
    private Bundle extras;
    private EditText restTimerminText;
    private EditText restTimersecText;
    private AlertDialog alertDialog;
    private AppDatabase db;

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
                editWorkoutIntent.putExtra(SELECTED_WORKOUT_KEY, selectedWorkout);
                startActivity(editWorkoutIntent);
                break;
            case R.id.menuItemAdjustTimer:
                createTimerDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_view);

        db = AppDatabase.getAppDatabase(this);

        setUp();
        adapter = new WorkoutExerciseAdapter(selectedWorkout.getWorkoutExercises(), this);
        exerciseRV.setAdapter(adapter);
    }

    private void createTimerDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_rest_timer, null);
        restTimerminText = dialogView.findViewById(R.id.restTimerMinTxt);
        restTimersecText = dialogView.findViewById(R.id.restTimerSecTxt);
        updateMinTimerText(selectedWorkout.getRestTimerMinutes());
        updateSecTimerText(selectedWorkout.getRestTimerSeconds());
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    public void restTimerPlusClick(View v) {
        final int MINUTES_TO_BE_ADDED_INTERVAL = 1;
        final int SECONDS_TO_BE_ADDED_INTERVAL = 15;
        int initialMinuteSet = Integer.parseInt(restTimerminText.getText().toString());
        int initialSecondsSet = Integer.parseInt(restTimersecText.getText().toString());
        switch (v.getTag().toString()) {
            case "min":
                updateMinTimerText(initialMinuteSet + MINUTES_TO_BE_ADDED_INTERVAL);
                break;
            case "sec":
                if (initialSecondsSet + SECONDS_TO_BE_ADDED_INTERVAL >= 60) {
                    updateMinTimerText(initialMinuteSet + 1);
                    restTimersecText.setText("0"); // After a minute has been added seconds is set to 0
                } else {
                    int numOfSecondsUpToInterval = SECONDS_TO_BE_ADDED_INTERVAL - (initialSecondsSet % SECONDS_TO_BE_ADDED_INTERVAL);
                    if (numOfSecondsUpToInterval == 0) {
                        updateSecTimerText(initialSecondsSet + SECONDS_TO_BE_ADDED_INTERVAL);
                    } else {
                        updateSecTimerText(initialSecondsSet + numOfSecondsUpToInterval);
                    }
                }
                break;
        }
    }

    public void restTimerMinusClick(View v) {
        final int MINUTES_TO_BE_REMOVED_INTERVAL = -1;
        final int SECONDS_TO_BE_REMOVED_INTERVAL = -15;
        int initialMinuteSet = Integer.parseInt(restTimerminText.getText().toString());
        int initialSecondsSet = Integer.parseInt(restTimersecText.getText().toString());
        switch (v.getTag().toString()) {
            case "min":
                if (initialMinuteSet + MINUTES_TO_BE_REMOVED_INTERVAL <= 0) {
                    updateMinTimerText(0); // can't go under zero minutes
                } else {
                    updateMinTimerText(initialMinuteSet + MINUTES_TO_BE_REMOVED_INTERVAL);
                }
                break;
            case "sec":
                if (initialSecondsSet + SECONDS_TO_BE_REMOVED_INTERVAL <= 0) {
                    updateSecTimerText(0);
                } else {
                    int numOfSecondsDownToInterval = SECONDS_TO_BE_REMOVED_INTERVAL - (initialSecondsSet % SECONDS_TO_BE_REMOVED_INTERVAL);
                    if (numOfSecondsDownToInterval == 0) {
                        updateSecTimerText(initialSecondsSet + SECONDS_TO_BE_REMOVED_INTERVAL);
                    } else {
                        updateSecTimerText(initialSecondsSet + numOfSecondsDownToInterval);
                    }
                }
                break;
        }
    }

    public void restTimerOkBtn(View v) {
        int minutesInput = Integer.parseInt(restTimerminText.getText().toString());
        int secondsInput = Integer.parseInt(restTimersecText.getText().toString());
        if (secondsInput >= 60) {
            Toast.makeText(this, "CONVERTED SECONDS INTO MINUTES", Toast.LENGTH_LONG).show();
            minutesInput += secondsInput / 60;
            updateMinTimerText(minutesInput);
            int leftOverSeconds = secondsInput % 60;
            updateSecTimerText(leftOverSeconds);
        } else {
            selectedWorkout.setGeneralRestTimer(minutesInput, secondsInput);
            adapter.notifyDataSetChanged();
            alertDialog.dismiss();
        }

    }

    public void restTimerCancelBtn(View v) {
        alertDialog.dismiss();
    }

    private void updateMinTimerText(int minute) {
        restTimerminText.setText(Integer.toString(minute));
    }

    private void updateSecTimerText(int seconds) {
        restTimersecText.setText(Integer.toString(seconds));
    }

    public void addExercise(View v) {
        Intent addExerciseIntent = new Intent(this, AddExerciseActivity.class);
        if (selectedWorkout != null) {
            addExerciseIntent.putExtra(SELECTED_WORKOUT_KEY, selectedWorkout);
        }
        startActivity(addExerciseIntent);
    }

    @Override
    public void onNoteClick(int position) {
        Intent editExerciseIntent = new Intent(this, EditExerciseActivity.class);
        editExerciseIntent.putExtra(AddExerciseActivity.EXERCISE_TO_PASSALONG, selectedWorkout.getWorkoutExercises().get(position));
        editExerciseIntent.putExtra(WorkoutActivity.SELECTED_WORKOUT_KEY, selectedWorkout);
        startActivity(editExerciseIntent);
    }

    @Override
    public void onLongNoteClick(final int position) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.deletion, selectedWorkout.getWorkoutExercises().get(position).getExerciseName()))
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        selectedWorkout.getWorkoutExercises().remove(selectedWorkout.getWorkoutExercises().get(position));
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
            selectedWorkout = (Workout) extras.getSerializable(SELECTED_WORKOUT_KEY);
        }

        Toolbar toolbar = findViewById(R.id.routineDetailsToolbar);
        toolbar.setTitle(selectedWorkout.getTitle());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

/*        Gson gson = new Gson();
        String json = gson.toJson(selectedWorkout.getWorkoutExercises());
        Log.d(TAG, "setUp: " + json);*/
    }

    @Override
    public void onBackPressed() {
        db.workoutDao().updateWorkout(selectedWorkout);
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }
}
