package com.example.trainingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.trainingapp.R;
import com.example.trainingapp.Workout;
import com.example.trainingapp.adapters.ExerciseAdapter;

public class WorkoutActivity extends AppCompatActivity implements ExerciseAdapter.OnNoteListener {
    private static final String TAG = "WorkoutActivity";
    private Workout workout = null;
    private RecyclerView exerciseRV;
    private ExerciseAdapter adapter;
    private Bundle extras;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.workout_overview_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: What");
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_view);

        //TODO MenuInflater with "Change workout name, set general timer etc..

        setUp();
        adapter = new ExerciseAdapter(workout.getExercises(), this);
        exerciseRV.setAdapter(adapter);
    }

    public void addExercise(View v) {
        Intent addExerciseIntent = new Intent(this, AddExerciseActivity.class);
        startActivity(addExerciseIntent);


        /*workout.addExercise(new Exercise("Deadlift", Exercise.Category.DUMBBELL));
        adapter.notifyDataSetChanged();*/
    }

    @Override
    public void onNoteClick(int position) {
        //TODO Exercise History activity
    }

    @Override
    public void onLongNoteClick(int position) {
        //TODO Delete exercise dialog box
    }

    private void setUp() {
        exerciseRV = findViewById(R.id.routine_details_RV);
        exerciseRV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        exerciseRV.setLayoutManager(linearLayoutManager);
        extras = getIntent().getExtras();

        if (extras != null) {
            workout = (com.example.trainingapp.Workout) extras.getSerializable(MainActivity.TAPPED_ROUTINE_KEY);
        }

        Toolbar toolbar = findViewById(R.id.routineDetailsToolbar);
        toolbar.setTitle(workout.getRoutineTitle());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
