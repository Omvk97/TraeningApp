package com.example.trainingapp.activities;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.example.trainingapp.DataRepository;
import com.example.trainingapp.R;
import com.example.trainingapp.Workout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String STARTED_WORKOUT_KEY = "started workout";
    private List<Workout> workouts = new ArrayList<>();
    private Context context;
    private Calendar currentDate = Calendar.getInstance();
    private LinearLayout linearLayout;
    private DataRepository data;
    private boolean noScheduledWorkoutTextAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = findViewById(R.id.allWorkoutsLinLay);
        currentDate.setTimeZone(TimeZone.getDefault());
        context = this;
        data = DataRepository.getInstance(context);
        workouts.addAll(data.getAllWorkouts());
        setAllRoutinesInLinearLayout();
        setScheduledWorkoutCard();

    }

    public void startWorkout(View view) {
/*        Intent startWorkoutIntent = new Intent(this, StartWorkoutActivity.class);
        startWorkoutIntent.putExtra(STARTED_WORKOUT_KEY, workouts.get((Integer) view.getTag()));
        startActivity(startWorkoutIntent);*/
    }

    public void addTrainingRoutine(View v) {
        Workout newWorkout = new Workout();
        Long idOfInsertedWorkout = data.insertWorkout(newWorkout);
        if (idOfInsertedWorkout != null) {
            newWorkout.setId(idOfInsertedWorkout);
            Intent intent = new Intent(this, WorkoutActivity.class);
            intent.putExtra(WorkoutActivity.SELECTED_WORKOUT_ID_KEY, idOfInsertedWorkout);
            startActivity(intent);
        }
    }

    public void startWorkoutHandler(View startButton) {
        Log.d(TAG, "startWorkoutHandler: clicked");
    }

    private void setScheduledWorkoutCard() {
        boolean scheduledWorkoutToday = false;
        // The scheduled workout should be added at the index where allWorkouts textview is and thereby moving allworkouts textview one down
        int allWorkoutsTextIndex = linearLayout.indexOfChild(findViewById(R.id.allWorkoutsTxt));
        for (Workout workout : workouts) {
            for (Workout.WeekDay scheduledDay : workout.getScheduledWeekDays()) {
                if (currentDate.get(Calendar.DAY_OF_WEEK) == scheduledDay.getWeekdayValue()) {
                    View scheduledWorkout = inflateWorkoutView(workout);
                    linearLayout.addView(scheduledWorkout, allWorkoutsTextIndex++);
                    addSpaceInLinearLayout(allWorkoutsTextIndex++);
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

    private void setAllRoutinesInLinearLayout() {
        for (Workout workout : workouts) {
            View workoutView = inflateWorkoutView(workout);
            linearLayout.addView(workoutView);
            addSpaceInLinearLayout(linearLayout.getChildCount());
        }
    }

    private View inflateWorkoutView(Workout workout) {
        // TODO - MAKE THIS WITH A VIEWHOLDER PATTERN TO INCREASE PERFORMANCE MAYBE YOUR OWN "ADAPTER" ATLEAST MOVE IT TO ANOTHER CLASS
        View workoutView = getLayoutInflater().inflate(R.layout.workout_overview, null, false);
        TextView lastTrainingDone = workoutView.findViewById(R.id.lastTrainingDoneDateTxt);
        TextView routineTitle = workoutView.findViewById(R.id.routineTitleTxt);
        TextView routineDays = workoutView.findViewById(R.id.routineDaysTxt);
        Button startWorkoutBtn = workoutView.findViewById(R.id.startWorkoutBtn);
        lastTrainingDone.setText(getString(R.string.lastTrainingDone, workout.getLastTraining(context)));
        routineTitle.setText(workout.getTitle());
        routineDays.setText(workout.getScheduledWeekDaysString());
        workoutView.setTag(String.valueOf(workout.getId())); // SETTING THE ID OF THE WORKOUT SO IT CAN BE GET FROM THE DATABASE
        startWorkoutBtn.setTag(String.valueOf(workout.getId())); // SETTING THE ID OF THE WORKOUT SO IT CAN BE GET FROM THE DATABASE
        workoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View selectedView) {
                Intent intent = new Intent(getApplicationContext(), WorkoutActivity.class);
                intent.putExtra(WorkoutActivity.SELECTED_WORKOUT_ID_KEY, Long.valueOf(selectedView.getTag().toString()));
                startActivity(intent);
            }
        });
        workoutView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View selectedView) {
                final Workout selectedWorkout = data.getWorkoutByID((Long) selectedView.getTag());
                new AlertDialog.Builder(context)
                        .setMessage(getString(R.string.deletion, selectedWorkout.getTitle()))
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                data.deleteWorkouts(selectedWorkout);
                                linearLayout.removeView(selectedView);
                                workouts.remove(selectedWorkout);
                                setScheduledWorkoutCard();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Do nothing
                            }
                        }).create().show();
                return true;
            }
        });

        return workoutView;
    }

    private void addSpaceInLinearLayout(int index) {
        Space space = new Space(context);
        int spaceBetweenCardsDP = (int) (getResources().getDimension(R.dimen.space_between_workout_card) / getResources().getDisplayMetrics().density);
        space.setMinimumHeight(spaceBetweenCardsDP);
        linearLayout.addView(space, index);
    }
}
