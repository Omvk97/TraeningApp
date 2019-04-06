package com.example.trainingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TRAdapter.OnNoteListener {
    private static final String TAG = "MainActivity";
    private List<TrainingRoutine> routines = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDummyTrainingRoutines();
        setScheduledWorkoutCard();
        setAllWorkoutsRecyclerView();
    }

    private void setScheduledWorkoutCard() {
        CardView tester = findViewById(R.id.scheduledWorkoutCard);
        TextView lastTrainingDoneTest = tester.findViewById(R.id.lastTrainingDoneDate);
        TextView titleTest = tester.findViewById(R.id.trainingRoutineTitleTxtV);
        TextView routineWeekDays = tester.findViewById(R.id.routineDaysTxt);
        TrainingRoutine oneOfTheRoutines = routines.get(routines.size() - 1);
        lastTrainingDoneTest.setText(getString(R.string.lastTrainingDone, oneOfTheRoutines.getLastTraining().toString()));
        titleTest.setText(oneOfTheRoutines.getRoutineTitle());
        routineWeekDays.setText(oneOfTheRoutines.getScheduledWeekDays());
    }

    private void setAllWorkoutsRecyclerView() {
        RecyclerView allWorkoutsRV = findViewById(R.id.allWorkoutsRecyclerView);
        allWorkoutsRV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        allWorkoutsRV.setLayoutManager(linearLayoutManager);

        TRAdapter adapter = new TRAdapter(routines, this);
        allWorkoutsRV.setAdapter(adapter);
    }

    private void createDummyTrainingRoutines() {
        TrainingRoutine test1 = new TrainingRoutine("FULL ON NECK BUILDING BOI");
        test1.setLastTraining(new Date(100000000));
        test1.setScheduledWeekDays(new ArrayList<>(Arrays.asList(TrainingRoutine.DayOfWeek.Monday, TrainingRoutine.DayOfWeek.Sunday)));
        routines.add(test1);
        TrainingRoutine test2 = new TrainingRoutine("FULL ON LEFT PINKY BOI");
        test2.setLastTraining(new Date(10000000000L));
        test2.setScheduledWeekDays(new ArrayList<>(Arrays.asList(TrainingRoutine.DayOfWeek.Tuesday, TrainingRoutine.DayOfWeek.Thursday)));
        routines.add(test2);
        TrainingRoutine test3 = new TrainingRoutine("UPPER BODY");
        test3.setLastTraining(new Date(100000000000L));
        test3.setScheduledWeekDays(new ArrayList<>(Arrays.asList(TrainingRoutine.DayOfWeek.Wednesday, TrainingRoutine.DayOfWeek.Saturday)));
        routines.add(test3);
        TrainingRoutine test4 = new TrainingRoutine("Lower body EXTREME 8MIN");
        test4.setLastTraining(new Date(100000000000000L));
        test4.setScheduledWeekDays(new ArrayList<>(Arrays.asList(TrainingRoutine.DayOfWeek.Monday, TrainingRoutine.DayOfWeek.Friday)));
        routines.add(test4);
        routines.add(test4);
        routines.add(test4);
        routines.add(test4);
        routines.add(test4);
        routines.add(test4);
        routines.add(test4);
        routines.add(test4);
        routines.add(test4);
        routines.add(test4);
        routines.add(test4);
        routines.add(test4);
        routines.add(test4);
        routines.add(test4);
        routines.add(test4);
    }

    public void startWorkoutHandler(View startButton) {
        Log.d(TAG, "startWorkoutHandler: clicked");
    }

    public void editWorkoutHandler(View editButton) {
        Log.d(TAG, "editWorkoutHandler: clicked");
    }

    @Override
    public void onNoteClick(int position) {
        Log.d(TAG, "onNoteClick: called");
        Intent intent = new Intent(this, RoutineDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLongNoteClick(int position) {
        Log.d(TAG, "onLongNoteClick: called");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_workout)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d(TAG, "onClick: User pressed yes");
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d(TAG, "onClick: USer perssed cancel");
                    }
                }).create().show();
    }
}
