package com.example.trainingapp.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toolbar;

import com.example.trainingapp.Exercise;
import com.example.trainingapp.R;

public class EditExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        setupToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setToolbarTitle();
        }
    }

    private void setupToolbar() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.editExerciseToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setToolbarTitle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Toolbar toolbar = findViewById(R.id.editExerciseToolbar);
            Exercise tappedExercise = (Exercise) bundle.get(AddExerciseActivity.EXERCISE_TO_BE_EDITED);
            if (tappedExercise != null) {
                toolbar.setTitle(tappedExercise.getExerciseName());
            }
        }
    }
}
