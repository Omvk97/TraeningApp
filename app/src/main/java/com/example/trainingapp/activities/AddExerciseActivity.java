package com.example.trainingapp.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.trainingapp.DataRepository;
import com.example.trainingapp.PreDefinedExercise;
import com.example.trainingapp.R;
import com.example.trainingapp.Workout;
import com.example.trainingapp.WorkoutExercise;
import com.example.trainingapp.adapters.AddExerciseAdapter;
import com.example.trainingapp.adapters.NoteListener;

import java.util.ArrayList;
import java.util.List;

public class AddExerciseActivity extends AppCompatActivity implements NoteListener {
    private static final String TAG = "AddExerciseActivity";
    private List<PreDefinedExercise> preDefinedExercises = new ArrayList<>();
    private AddExerciseAdapter mAdapter;
    private Workout selectedWorkout;
    private DataRepository data;
    private SearchView mSearchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_exercise_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.exerciseSearch)
                .getActionView();
        mSearchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        mSearchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO - Search after specific exercise
        //TODO - Filter popup with exercise muscle category + exercise Equipment
        if (item.getItemId() == R.id.exerciseSearch) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        data = DataRepository.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectedWorkout = data.getWorkoutByID(UserInteractions.getInstance().getSelectedWorkoutID());
        preDefinedExercises.addAll(data.getAllPreDefinedExercises());

        setupToolbar();

        RecyclerView exerciseRV = findViewById(R.id.allExercisesRV);
        exerciseRV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        exerciseRV.setLayoutManager(linearLayoutManager);

        mAdapter = new AddExerciseAdapter(preDefinedExercises, this);
        exerciseRV.setAdapter(mAdapter);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.addExerciseToolbar);
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

    public void createCustomExercise(View v) {
        Intent editExercise = new Intent(this, EditExerciseActivity.class);
        startActivity(editExercise);
    }

    public void editExercise(View v) {
        Intent editExercise = new Intent(this, EditExerciseActivity.class);
        UserInteractions.getInstance().setSelectedExerciseID(preDefinedExercises.get(Integer.valueOf(v.getTag().toString())).getId());
        startActivity(editExercise);
    }


    @Override
    public void onNoteClick(final int position) {
        View contextView = findViewById(R.id.allExercisesRV);
        final WorkoutExercise exerciseToBeAdded = new WorkoutExercise(preDefinedExercises.get(position));
        selectedWorkout.addExercise(exerciseToBeAdded);
        String snackbarMessage = getString(R.string.exercise_added, preDefinedExercises.get(position).getExerciseName());
        Snackbar addedExerciseSnack = Snackbar.make(contextView, snackbarMessage, Snackbar.LENGTH_LONG);
        addedExerciseSnack.setAction(R.string.undo_string, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedWorkout.removeExercise(exerciseToBeAdded);
            }
        });
        addedExerciseSnack.show();
    }

    @Override
    public void onLongNoteClick(final int position) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.deletion_confirmation, preDefinedExercises.get(position).getExerciseName()))
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        data.deleteExercise(preDefinedExercises.get(position));
                        preDefinedExercises.remove(preDefinedExercises.get(position));
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
        Intent goBackToWorkout = new Intent(this, WorkoutActivity.class);
        startActivity(goBackToWorkout);
    }

    @Override
    protected void onPause() {
        super.onPause();
        preDefinedExercises = new ArrayList<>();
        data.updateWorkout(selectedWorkout);
    }
}
