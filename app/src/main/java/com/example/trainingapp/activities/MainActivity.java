package com.example.trainingapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.example.trainingapp.Exercise;
import com.example.trainingapp.R;
import com.example.trainingapp.TrainingRoutine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private List<TrainingRoutine> routines = new ArrayList<>();
    static final String TAPPED_ROUTINE_EXERCISE_KEY = "tapped routine exercise";
    static final String TAPPED_ROUTINE_NAME_KEY = "tapped routine name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDummyTrainingRoutines();
        setScheduledWorkoutCard();
        setAllRoutinesInLinearLayout();
    }

    private void setScheduledWorkoutCard() {
        CardView tester = findViewById(R.id.scheduled_routine);
        TextView lastTrainingDoneTest = tester.findViewById(R.id.lastTrainingDoneDateTxt);
        TextView titleTest = tester.findViewById(R.id.routineTitleTxt);
        TextView routineWeekDays = tester.findViewById(R.id.routineDaysTxt);
        TrainingRoutine oneOfTheRoutines = routines.get(routines.size() - 1);
        lastTrainingDoneTest.setText(getString(R.string.lastTrainingDone, oneOfTheRoutines.getLastTraining().toString()));
        titleTest.setText(oneOfTheRoutines.getRoutineTitle());
        routineWeekDays.setText(oneOfTheRoutines.getScheduledWeekDays());
    }

    private void setAllRoutinesInLinearLayout() {
        for (TrainingRoutine routine : routines) {
            LinearLayout linearLayout = findViewById(R.id.allWorkoutsLinLay);
            View tester = getLayoutInflater().inflate(R.layout.recyclerview_workout_routine, null, false);
            TextView lastTrainingDone = tester.findViewById(R.id.lastTrainingDoneDateTxt);
            TextView routineTitle = tester.findViewById(R.id.routineTitleTxt);
            TextView routineDays = tester.findViewById(R.id.routineDaysTxt);
            lastTrainingDone.setText(routine.getLastTraining().toString());
            routineTitle.setText(routine.getRoutineTitle());
            routineDays.setText(routine.getScheduledWeekDays());
            tester.setTag(routines.indexOf(routine));
            tester.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), RoutineDetailsActivity.class);
                    intent.putExtra(TAPPED_ROUTINE_EXERCISE_KEY, routines.get((Integer) v.getTag()).getExercises());
                    intent.putExtra(TAPPED_ROUTINE_NAME_KEY, routines.get((Integer) v.getTag()).getRoutineTitle());
                    startActivity(intent);
                }
            });
            tester.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d(TAG, "onLongNoteClick: called");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
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
                    return true;
                }
            });
            linearLayout.addView(tester);
            Space space = new Space(this);
            space.setMinimumHeight(16);
            linearLayout.addView(space);
        }
    }

    private void createDummyTrainingRoutines() {
        Exercise exercise1 = new Exercise("Deadlift", Exercise.Category.BARBELL);
        Exercise exercise2 = new Exercise("Curl", Exercise.Category.EZBAR);
        Exercise exercise3 = new Exercise("Bent over rows", Exercise.Category.BARBELL);
        Exercise exercise4 = new Exercise("Lunges", Exercise.Category.DUMBBELL);

        TrainingRoutine test1 = new TrainingRoutine("FULL ON NECK BUILDING BOI");
        test1.setLastTraining(new Date(100000000));
        test1.setScheduledWeekDays(new ArrayList<>(Arrays.asList(TrainingRoutine.DayOfWeek.Monday, TrainingRoutine.DayOfWeek.Sunday)));
        test1.setExercises(new ArrayList<Exercise>(Arrays.asList(exercise1, exercise2, exercise3, exercise4)));
        routines.add(test1);

        TrainingRoutine test2 = new TrainingRoutine("FULL ON LEFT PINKY BOI");
        test2.setLastTraining(new Date(10000000000L));
        test2.setScheduledWeekDays(new ArrayList<>(Arrays.asList(TrainingRoutine.DayOfWeek.Tuesday, TrainingRoutine.DayOfWeek.Thursday)));
        test2.setExercises(new ArrayList<Exercise>(Arrays.asList(exercise1, exercise2, exercise3, exercise4)));
        routines.add(test2);

        TrainingRoutine test3 = new TrainingRoutine("UPPER BODY");
        test3.setLastTraining(new Date(100000000000L));
        test3.setScheduledWeekDays(new ArrayList<>(Arrays.asList(TrainingRoutine.DayOfWeek.Wednesday, TrainingRoutine.DayOfWeek.Saturday)));
        routines.add(test3);

        test3.setExercises(new ArrayList<Exercise>(Arrays.asList(exercise1, exercise2, exercise3, exercise4)));
        TrainingRoutine test4 = new TrainingRoutine("Lower body EXTREME 8MIN");
        test4.setLastTraining(new Date(100000000000000L));
        test4.setScheduledWeekDays(new ArrayList<>(Arrays.asList(TrainingRoutine.DayOfWeek.Monday, TrainingRoutine.DayOfWeek.Friday)));
        test4.setExercises(new ArrayList<Exercise>(Arrays.asList(exercise1, exercise2, exercise3, exercise4)));
        routines.add(test4);

        routines.add(test4);
        routines.add(test4);
        routines.add(test4);
    }

    public void startWorkoutHandler(View startButton) {
        Log.d(TAG, "startWorkoutHandler: clicked");
    }
}
