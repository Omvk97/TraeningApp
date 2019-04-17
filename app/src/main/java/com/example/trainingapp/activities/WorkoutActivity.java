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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.trainingapp.DataRepository;
import com.example.trainingapp.R;
import com.example.trainingapp.SwipeToDeleteCallBack;
import com.example.trainingapp.Workout;
import com.example.trainingapp.WorkoutExercise;
import com.example.trainingapp.adapters.OnNoteListener;
import com.example.trainingapp.adapters.WorkoutExerciseAdapter;

public class WorkoutActivity extends AppCompatActivity implements OnNoteListener {
    private static final String TAG = "WorkoutActivity";
    private Workout mSelectedWorkout = null;
    private WorkoutExerciseAdapter mAdapter;
    private EditText mRestTimerMinText;
    private EditText mRestTimerSectext;
    private DataRepository mDatabase;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.workout_overview_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemEditWorkoutProp:
                Intent editWorkoutIntent = new Intent(this, EditWorkoutPropertiesActivity.class);
                startActivity(editWorkoutIntent);
                break;
            case R.id.menuItemAdjustTimer:
                createWorkoutTimerDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_view);
        mDatabase = DataRepository.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRecyclerView();

        Toolbar toolbar = findViewById(R.id.routineDetailsToolbar);
        toolbar.setTitle(mSelectedWorkout.getTitle());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setupRecyclerView() {
        RecyclerView exerciseRV = findViewById(R.id.routine_details_RV);
        mSelectedWorkout = mDatabase.getWorkoutByID(UserInteractions.getInstance().getSelectedWorkoutID());
        exerciseRV.setHasFixedSize(true);
        exerciseRV.setNestedScrollingEnabled(false);
        exerciseRV.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WorkoutExerciseAdapter(mSelectedWorkout.getExercises(), this);
        exerciseRV.setAdapter(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallBack(mAdapter));
        itemTouchHelper.attachToRecyclerView(exerciseRV);
    }

    private void createWorkoutTimerDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_rest_timer, null);
        mRestTimerMinText = dialogView.findViewById(R.id.restTimerMinTxt);
        mRestTimerSectext = dialogView.findViewById(R.id.restTimerSecTxt);
        updateMinTimerText(mSelectedWorkout.getWorkoutRestTimer().getMinutes());
        updateSecTimerText(mSelectedWorkout.getWorkoutRestTimer().getSeconds());
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int minValue = Integer.valueOf(mRestTimerMinText.getText().toString());
                int secValue = Integer.valueOf(mRestTimerSectext.getText().toString());
                mSelectedWorkout.setGeneralRestTimer(minValue, secValue);
                for (WorkoutExercise exercise : mSelectedWorkout.getExercises()) {
                    exercise.setGeneralRestTimer(mSelectedWorkout);
                }
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        }).create().show();
    }

    public void restTimerPlusClick(View v) {
        final int MINUTES_TO_BE_ADDED_INTERVAL = 1;
        final int SECONDS_TO_BE_ADDED_INTERVAL = 15;
        int initialMinuteSet = Integer.parseInt(mRestTimerMinText.getText().toString());
        int initialSecondsSet = Integer.parseInt(mRestTimerSectext.getText().toString());
        switch (v.getTag().toString()) {
            case "min":
                updateMinTimerText(initialMinuteSet + MINUTES_TO_BE_ADDED_INTERVAL);
                break;
            case "sec":
                if (initialSecondsSet + SECONDS_TO_BE_ADDED_INTERVAL >= 60) {
                    updateMinTimerText(initialMinuteSet + 1);
                    mRestTimerSectext.setText("0"); // After a minute has been added seconds is set to 0
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
        int initialMinuteSet = Integer.parseInt(mRestTimerMinText.getText().toString());
        int initialSecondsSet = Integer.parseInt(mRestTimerSectext.getText().toString());
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

    private void updateMinTimerText(int minute) {
        mRestTimerMinText.setText(Integer.toString(minute));
    }

    private void updateSecTimerText(int seconds) {
        mRestTimerSectext.setText(Integer.toString(seconds));
    }

    public void addExercise(View v) {
        Intent addExerciseIntent = new Intent(this, AddExerciseActivity.class);
        startActivity(addExerciseIntent);
    }

    public void addSetHandler(View view) {
        WorkoutExercise workoutExercise = mSelectedWorkout.getExercises().get(Integer.valueOf(view.getTag().toString()));
        workoutExercise.addSet();
        mAdapter.notifyDataSetChanged();
    }

    public void adjustExerciseRestTimer(View view) {
        final WorkoutExercise workoutExercise = mSelectedWorkout.getExercises().get(Integer.valueOf(view.getTag().toString()));
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_rest_timer, null);
        mRestTimerMinText = dialogView.findViewById(R.id.restTimerMinTxt);
        mRestTimerSectext = dialogView.findViewById(R.id.restTimerSecTxt);
        updateMinTimerText(workoutExercise.getRestTimer().getMinutes());
        updateSecTimerText(workoutExercise.getRestTimer().getSeconds());
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int minValue = Integer.valueOf(mRestTimerMinText.getText().toString());
                int secValue = Integer.valueOf(mRestTimerSectext.getText().toString());
                workoutExercise.setSpecificRestTimer(minValue, secValue);
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        }).create().show();
    }

    @Override
    public void onNoteClick(int position) {
        Intent editExerciseIntent = new Intent(this, EditExerciseActivity.class);
        long idOfSelectedExercise = mSelectedWorkout.getExercises().get(position).getId();
        UserInteractions.getInstance().setSelectedExerciseID(idOfSelectedExercise);
        startActivity(editExerciseIntent);
    }

    @Override
    public void onLongNoteClick(final int position) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.deletion_confirmation, mSelectedWorkout.getExercises().get(position).getExerciseName()))
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mSelectedWorkout.getExercises().remove(mSelectedWorkout.getExercises().get(position));
                        mAdapter.notifyDataSetChanged();
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
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        UserInteractions.getInstance().setSelectedWorkoutID(null);
        startActivity(mainActivityIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDatabase.updateWorkout(mSelectedWorkout);
    }



}
