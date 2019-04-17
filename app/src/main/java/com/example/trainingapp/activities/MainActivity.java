package com.example.trainingapp.activities;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trainingapp.DataRepository;
import com.example.trainingapp.PreDefinedExerciseInsertionAsyncTask;
import com.example.trainingapp.R;
import com.example.trainingapp.Workout;
import com.example.trainingapp.adapters.OnNoteListener;
import com.example.trainingapp.adapters.WorkoutRoutinesOverviewAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements OnNoteListener {
    private static final String FIRST_TIME_USE = "first time";
    private List<Workout> workouts = new ArrayList<>();
    private Context context;
    private DataRepository data;
    private RecyclerView workoutRV;
    private WorkoutRoutinesOverviewAdapter adapter;
    private boolean noScheduledWorkoutTextAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean(FIRST_TIME_USE, true)) {
            (new PreDefinedExerciseInsertionAsyncTask(this)).execute();
            sharedPreferences.edit().putBoolean(FIRST_TIME_USE, false).apply();
        }

        context = this;
        data = DataRepository.getInstance(context);
        workouts.addAll(data.getAllWorkouts());
        setScheduledWorkoutCard();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        workoutRV = findViewById(R.id.workoutRV);
        workoutRV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        workoutRV.setLayoutManager(linearLayoutManager);
        adapter = new WorkoutRoutinesOverviewAdapter(workouts, this);
        workoutRV.setAdapter(adapter);
    }

    private void setScheduledWorkoutCard() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTimeZone(TimeZone.getDefault());
        LinearLayout linearLayout = findViewById(R.id.allWorkoutsLinLay);
        boolean scheduledWorkoutToday = false;
        // The scheduled workout should be added at the index where allWorkouts textview is and thereby moving allworkouts textview one down
        int allWorkoutsTextIndex = linearLayout.indexOfChild(findViewById(R.id.allWorkoutsTxt));
        for (Workout workout : workouts) {
            for (Workout.WeekDay scheduledDay : workout.getScheduledWeekDays()) {
                if (currentDate.get(Calendar.DAY_OF_WEEK) == scheduledDay.getWeekdayValue()) {
                    View scheduledWorkout = inflateWorkoutView(workout);
                    linearLayout.addView(scheduledWorkout, allWorkoutsTextIndex++);
                    scheduledWorkoutToday = true;
                }
            }
        }
        if (!scheduledWorkoutToday && !noScheduledWorkoutTextAdded) {
            TextView noScheduledWorkout = new TextView(context);
            noScheduledWorkout.setText(R.string.no_scheduled_workout);
            linearLayout.addView(noScheduledWorkout, allWorkoutsTextIndex++);
            noScheduledWorkoutTextAdded = true;
        }
    }

    // TODO - Try to use third party library with Recyclerview sections instead at some point in future
    private View inflateWorkoutView(final Workout workout) {
        View workoutView = getLayoutInflater().inflate(R.layout.workout_overview, null, false);
        TextView lastTrainingDone = workoutView.findViewById(R.id.lastTrainingDoneDateTxt);
        TextView routineTitle = workoutView.findViewById(R.id.routineTitleTxt);
        TextView routineDays = workoutView.findViewById(R.id.routineDaysTxt);
        Button startWorkoutBtn = workoutView.findViewById(R.id.startWorkoutBtn);
        lastTrainingDone.setText(getString(R.string.lastTrainingDone, workout.getLastTrainingString()));
        routineTitle.setText(workout.getTitle());
        routineDays.setText(workout.getScheduledWeekDaysString());
        startWorkoutBtn.setTag(String.valueOf(workout.getId())); // SETTING THE ID OF THE WORKOUT SO IT CAN BE GET FROM THE DATABASE
        workoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNoteClick(workouts.indexOf(workout));
            }
        });
        workoutView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongNoteClick(workouts.indexOf(workout));
                return true;
            }
        });
        return workoutView;
    }

    public void addTrainingRoutine(View v) {
        Workout newWorkout = new Workout();
        Long idOfInsertedWorkout = data.insertWorkout(newWorkout);
        if (idOfInsertedWorkout != null) {
            newWorkout.setId(idOfInsertedWorkout);
            Intent intent = new Intent(this, WorkoutActivity.class);
            UserInteractions.getInstance().setSelectedWorkoutID(idOfInsertedWorkout);
            startActivity(intent);
        }
    }

    public void startWorkout(View view) {
/*        Intent startWorkoutIntent = new Intent(this, StartWorkoutActivity.class);
        startWorkoutIntent.putExtra(STARTED_WORKOUT_KEY, workouts.get((Integer) view.getTag()));
        startActivity(startWorkoutIntent);*/
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(getApplicationContext(), WorkoutActivity.class);
        UserInteractions.getInstance().setSelectedWorkoutID(workouts.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onLongNoteClick(int position) {
        final Workout selectedWorkout = workouts.get(position);
        new AlertDialog.Builder(context)
                .setMessage(getString(R.string.deletion, selectedWorkout.getTitle()))
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        data.deleteWorkouts(selectedWorkout);
                        workouts.remove(selectedWorkout);
                        adapter.notifyDataSetChanged();
                        setScheduledWorkoutCard();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Do nothing
                    }
                }).create().show();
    }
}


























