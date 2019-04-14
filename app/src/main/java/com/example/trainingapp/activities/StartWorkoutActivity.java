package com.example.trainingapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.trainingapp.R;
import com.example.trainingapp.Workout;
import com.example.trainingapp.adapters.OnNoteListener;
import com.example.trainingapp.adapters.StartedWorkoutAdapter;

public class StartWorkoutActivity extends AppCompatActivity implements OnNoteListener {

    private Workout selectedWorkout;
    private RecyclerView exercisesRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedWorkout = (Workout) extras.getSerializable(MainActivity.STARTED_WORKOUT_KEY);
        }

        exercisesRV = findViewById(R.id.startedWorkExercisesRV);
        exercisesRV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        exercisesRV.setLayoutManager(linearLayoutManager);
        StartedWorkoutAdapter adapter = new StartedWorkoutAdapter(selectedWorkout.getExercises(), this);
        exercisesRV.setAdapter(adapter);
    }

    @Override
    public void onNoteClick(int position) {

    }

    @Override
    public void onLongNoteClick(int position) {

    }
}
