package com.example.trainingapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.trainingapp.R;
import com.example.trainingapp.Workout;
import com.example.trainingapp.adapters.OnNoteListener;

public class StartWorkoutActivity extends AppCompatActivity implements OnNoteListener {

    private Workout selectedWorkout;
    private RecyclerView exercisesRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);

    }

    @Override
    public void onNoteClick(int position) {

    }

    @Override
    public void onLongNoteClick(int position) {

    }
}
