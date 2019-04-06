package com.example.trainingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView allWorkouts = findViewById(R.id.allWorkoutsRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        allWorkouts.setLayoutManager(linearLayoutManager);

        CardView tester = findViewById(R.id.scheduledWorkoutCard);
        TextView lastTrainingDoneTest = tester.findViewById(R.id.lastTrainingDoneDate);
        lastTrainingDoneTest.setText(getString(R.string.lastTrainingDone, "04/04/2019"));
    }

    public void startWorkoutHandler(View startButton) {
        Log.d(TAG, "startWorkoutHandler: clicked");
    }

    public void editWorkoutHandler(View editButton) {
        Log.d(TAG, "editWorkoutHandler: clicked");
    }
}
