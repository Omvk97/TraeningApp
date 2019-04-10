package com.example.trainingapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.example.trainingapp.R;
import com.example.trainingapp.Workout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String ADDED_WORKOUT = "Added workout";
    private List<Workout> workouts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            workouts.add((Workout) extras.getSerializable(WorkoutActivity.SELECTED_WORKOUT_KEY));
        }
        setAllRoutinesInLinearLayout();
        setScheduledWorkoutCard();
    }

    public void addTrainingRoutine(View v) {
        Workout newWorkout = new Workout();
        Intent intent = new Intent(getApplicationContext(), WorkoutActivity.class);
        intent.putExtra(WorkoutActivity.SELECTED_WORKOUT_KEY, newWorkout);
        startActivity(intent);
    }

    public void startWorkoutHandler(View startButton) {
        Log.d(TAG, "startWorkoutHandler: clicked");
    }

    private void setScheduledWorkoutCard() {
        //TODO - Get the current time of the android device and check all the dates from workouts arraylist and if one of the workouts scheduled workouts matches todays date set it here
        CardView scheduledRoutineCard = findViewById(R.id.scheduled_routine);
        if (workouts.size() != 0) {
            Log.d(TAG, "setScheduledWorkoutCard: Insertion");
            Workout scheduledWorkout = workouts.get(workouts.size() - 1);
            TextView lastTrainingDoneTest = scheduledRoutineCard.findViewById(R.id.lastTrainingDoneDateTxt);
            TextView titleTest = scheduledRoutineCard.findViewById(R.id.routineTitleTxt);
            TextView routineWeekDays = scheduledRoutineCard.findViewById(R.id.routineDaysTxt);
            lastTrainingDoneTest.setText(getString(R.string.lastTrainingDone, scheduledWorkout.getLastTraining(this)));
            titleTest.setText(scheduledWorkout.getWorkoutName());
            routineWeekDays.setText(scheduledWorkout.getScheduledWeekDaysString());
        } else {
            Log.d(TAG, "setScheduledWorkoutCard: DELETION");
            ((ViewGroup)scheduledRoutineCard.getParent()).removeView(scheduledRoutineCard);
            TextView yourScheduledWorkout = findViewById(R.id.scheduledWorkoutTxt);
            ((ViewGroup)yourScheduledWorkout.getParent()).removeView(yourScheduledWorkout);
        }
    }

    private void setAllRoutinesInLinearLayout() {
        for (Workout workout : workouts) { // TODO - MAKE THIS WITH A VIEWHOLDER PATTERN TO INCREASE PERFORMANCE MAYBE YOUR OWN "ADAPTER" ATLEAST MOVE IT TO ANOTHER CLASS
            LinearLayout linearLayout = findViewById(R.id.allWorkoutsLinLay);
            View tester = getLayoutInflater().inflate(R.layout.workout_overview, null, false);
            TextView lastTrainingDone = tester.findViewById(R.id.lastTrainingDoneDateTxt);
            TextView routineTitle = tester.findViewById(R.id.routineTitleTxt);
            TextView routineDays = tester.findViewById(R.id.routineDaysTxt);
            lastTrainingDone.setText(getString(R.string.lastTrainingDone, workout.getLastTraining(this)));
            routineTitle.setText(workout.getWorkoutName());
            routineDays.setText(workout.getScheduledWeekDaysString());
            tester.setTag(workouts.indexOf(workout)); // Setting the tag to the positon in the arraylist for later reference

            tester.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), WorkoutActivity.class);
                    intent.putExtra(WorkoutActivity.SELECTED_WORKOUT_KEY, workouts.get((Integer) v.getTag()));
                    startActivity(intent);
                }
            });
            tester.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d(TAG, "onLongNoteClick: called");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setMessage(getString(R.string.deletion, workouts.get((Integer) v.getTag()).getWorkoutName()))
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Log.d(TAG, "onClick: User pressed yes");
                                    // TODO - Delete the workout from the arraylist and the view
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
            linearLayout.addView(tester);
            Space space = new Space(this);
            space.setMinimumHeight(16);
            linearLayout.addView(space);
        }
    }
}
