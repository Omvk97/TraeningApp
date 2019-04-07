package com.example.trainingapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.trainingapp.Exercise;
import com.example.trainingapp.R;
import com.example.trainingapp.adapters.ExerciseAdapter;

import java.util.ArrayList;

import static com.example.trainingapp.activities.MainActivity.TAPPED_ROUTINE_EXERCISE_KEY;
import static com.example.trainingapp.activities.MainActivity.TAPPED_ROUTINE_NAME_KEY;

public class RoutineDetailsActivity extends AppCompatActivity implements ExerciseAdapter.OnNoteListener {

    private ArrayList<Exercise> routineExercise = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_details);

        RecyclerView exerciseRV = findViewById(R.id.routine_details_RV);
        exerciseRV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        exerciseRV.setLayoutManager(linearLayoutManager);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            routineExercise = (ArrayList<Exercise>) extras.getSerializable(TAPPED_ROUTINE_EXERCISE_KEY);
            Toolbar toolbar = findViewById(R.id.routineDetailsToolbar);
            toolbar.setTitle(extras.getString(TAPPED_ROUTINE_NAME_KEY));
        }
        if (routineExercise != null) {
            ExerciseAdapter adapter = new ExerciseAdapter(routineExercise, this);
            exerciseRV.setAdapter(adapter);

        } else {
            // TODO Write that a mistake happend
        }
    }

    @Override
    public void onNoteClick(int position) {

    }

    @Override
    public void onLongNoteClick(int position) {

    }
}
