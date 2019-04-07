package com.example.trainingapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.trainingapp.R;
import com.example.trainingapp.TrainingRoutine;
import com.example.trainingapp.adapters.ExerciseAdapter;

public class RoutineDetailsActivity extends AppCompatActivity implements ExerciseAdapter.OnNoteListener {

    private TrainingRoutine routine = null;
    private RecyclerView exerciseRV;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_view);

        setUp();
        ExerciseAdapter adapter = new ExerciseAdapter(routine.getExercises(), this);
        exerciseRV.setAdapter(adapter);
    }

    private void setUp() {
        exerciseRV = findViewById(R.id.routine_details_RV);
        exerciseRV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        exerciseRV.setLayoutManager(linearLayoutManager);
        extras = getIntent().getExtras();

        if (extras != null) {
            routine = (TrainingRoutine) extras.getSerializable(MainActivity.TAPPED_ROUTINE_KEY);
        }

        Toolbar toolbar = findViewById(R.id.routineDetailsToolbar);
        toolbar.setTitle(routine.getRoutineTitle());
    }

    @Override
    public void onNoteClick(int position) {
    }

    @Override
    public void onLongNoteClick(int position) {

    }
}
