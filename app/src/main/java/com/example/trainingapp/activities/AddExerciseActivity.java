package com.example.trainingapp.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.trainingapp.DatabaseHelper;
import com.example.trainingapp.PreDefinedExercise;
import com.example.trainingapp.R;
import com.example.trainingapp.TrainingAppDatabase;
import com.example.trainingapp.Workout;
import com.example.trainingapp.WorkoutExercise;
import com.example.trainingapp.adapters.AddExerciseAdapter;
import com.example.trainingapp.adapters.OnNoteListener;

import java.util.ArrayList;
import java.util.List;

public class AddExerciseActivity extends AppCompatActivity implements OnNoteListener {
    private static final String TAG = "AddExerciseActivity";
    public static final String EXERCISE_TO_PASSALONG = "exercise to be edited";
    private List<PreDefinedExercise> mWorkoutExercises = new ArrayList<>();
    private RecyclerView exerciseRV;
    private AddExerciseAdapter adapter;
    private Workout selectedWorkout;
    private long selectedWorkoutID;
    private DatabaseHelper db;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_exercise_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO - Search after specific exercise
        //TODO - Filter popup with exercise muscle category + exercise Equipment
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        db = DatabaseHelper.getInstance(this);
        selectedWorkoutID = getIntent().getLongExtra(WorkoutActivity.SELECTED_WORKOUT_ID_KEY, -1);
        if (selectedWorkoutID != -1) {
            selectedWorkout = db.getWorkout(selectedWorkoutID);
        }

        // Change toolbar title to the selected exercise
        setupToolbar();
        // set up test mWorkoutExercises, shall be replaced with setting up default mWorkoutExercises
        setUpTestExercises();

        exerciseRV = findViewById(R.id.allExercisesRV);
        exerciseRV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        exerciseRV.setLayoutManager(linearLayoutManager);

        adapter = new AddExerciseAdapter(mWorkoutExercises, this);
        exerciseRV.setAdapter(adapter);

    }

    public void setUpTestExercises() {
        TrainingAppDatabase db = TrainingAppDatabase.getInstance(this);
        mWorkoutExercises.addAll(db.exerciseDao().getAllPreDefinedExercises());


/*        WorkoutExercise editedWorkoutExercise = ((WorkoutExercise) selectedWorkoutID.getSerializable(EXERCISE_TO_PASSALONG));
        if (editedWorkoutExercise != null) {
            mWorkoutExercises.add(editedWorkoutExercise);
        }*/
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.addExerciseToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitle(getString(R.string.toolbar_add_exercise));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void createCustomExercise(View v) {
/*        Intent editExercise = new Intent(this, EditExerciseActivity.class);
        editExercise.putExtra(WorkoutActivity.SELECTED_WORKOUT_ID_KEY, selectedWorkout);
        startActivity(editExercise);*/
    }

    public void editExercise(View v) {
        // TODO WorkoutExercise edit AND VISUAL REPRESENTATION THAT YOU HAVE CLICKED
/*        Intent editExercise = new Intent(this, EditExerciseActivity.class);
        editExercise.putExtra(WorkoutActivity.SELECTED_WORKOUT_ID_KEY, selectedWorkout);
        editExercise.putExtra(EXERCISE_TO_PASSALONG, mWorkoutExercises.get((Integer) v.getTag()));
        startActivity(editExercise);*/
    }


    @Override
    public void onNoteClick(final int position) {
        View contextView = findViewById(R.id.allExercisesRV);
        final WorkoutExercise exerciseToBeAdded = (WorkoutExercise) mWorkoutExercises.get(position);
        exerciseToBeAdded.setGeneralRestTimer(selectedWorkout.getWorkoutRestTimer());
        selectedWorkout.addExercise(exerciseToBeAdded);
        String snackbarMessage = getString(R.string.exercise_added, mWorkoutExercises.get(position).getExerciseName());
        Snackbar addedExerciseSnack = Snackbar.make(contextView, snackbarMessage, Snackbar.LENGTH_LONG);
        addedExerciseSnack.setAction(R.string.undo_string, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedWorkout.getExercises().contains(mWorkoutExercises.get(position))) {
                    selectedWorkout.removeExercise(exerciseToBeAdded);
                }
            }
        });
        addedExerciseSnack.show();
    }

    @Override
    public void onLongNoteClick(final int position) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.deletion, mWorkoutExercises.get(position).getExerciseName()))
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mWorkoutExercises.remove(mWorkoutExercises.get(position));
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
/*        Intent goBackToWorkout = new Intent(this, WorkoutActivity.class);
        goBackToWorkout.putExtra(WorkoutActivity.SELECTED_WORKOUT_ID_KEY, selectedWorkout);
        startActivity(goBackToWorkout);*/
    }
}
