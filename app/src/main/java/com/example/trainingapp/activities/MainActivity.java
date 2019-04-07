package com.example.trainingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.trainingapp.R;
import com.example.trainingapp.Workout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private List<WorkoutActivity> routines = new ArrayList<>();
    static final String TAPPED_ROUTINE_KEY = "tapped routine exercise";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addTrainingRoutine(View v) {
        Workout newWorkout = new Workout();
        Intent intent = new Intent(getApplicationContext(), WorkoutActivity.class);
        intent.putExtra(TAPPED_ROUTINE_KEY, newWorkout);
        startActivity(intent);


    }

    public void startWorkoutHandler(View startButton) {
        Log.d(TAG, "startWorkoutHandler: clicked");
    }



















    private void setScheduledWorkoutCard() {
/*        CardView tester = findViewById(R.id.scheduled_routine);
        TextView lastTrainingDoneTest = tester.findViewById(R.id.lastTrainingDoneDateTxt);
        TextView titleTest = tester.findViewById(R.id.routineTitleTxt);
        TextView routineWeekDays = tester.findViewById(R.id.routineDaysTxt);
        Workout oneOfTheRoutines = routines.get(routines.size());
        lastTrainingDoneTest.setText(getString(R.string.lastTrainingDone, oneOfTheRoutines.getLastTraining().toString()));
        titleTest.setText(oneOfTheRoutines.getRoutineTitle());
        routineWeekDays.setText(oneOfTheRoutines.getScheduledWeekDays());*/
    }

    private void setAllRoutinesInLinearLayout() {
        /*for (WorkoutActivity routine : routines) {
            LinearLayout linearLayout = findViewById(R.id.allWorkoutsLinLay);
            View tester = getLayoutInflater().inflate(R.layout.workout_overview, null, false);
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
                    Intent intent = new Intent(getApplicationContext(), WorkoutActivity.class);
                    intent.putExtra(TAPPED_ROUTINE_KEY, routines.get((Integer) v.getTag()));
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
        }*/
    }

    private void createDummyTrainingRoutines() {
        /*Exercise exercise1 = new Exercise("Deadlift", Exercise.Category.BARBELL);
        Exercise exercise2 = new Exercise("Curl", Exercise.Category.EZBAR);
        Exercise exercise3 = new Exercise("Bent over rows", Exercise.Category.BARBELL);
        Exercise exercise4 = new Exercise("Lunges", Exercise.Category.DUMBBELL);

        WorkoutActivity test1 = new WorkoutActivity("FULL ON NECK BUILDING BOI");
        test1.setLastTraining(new Date(100000000));
        test1.setScheduledWeekDays(new ArrayList<>(Arrays.asList(com.example.trainingapp.Workout.DayOfWeek.Monday, com.example.trainingapp.Workout.DayOfWeek.Sunday)));
        test1.setExercises(new ArrayList<Exercise>(Arrays.asList(exercise1, exercise2, exercise3, exercise4)));
        routines.add(test1);

        com.example.trainingapp.Workout test2 = new com.example.trainingapp.Workout("FULL ON LEFT PINKY BOI");
        test2.setLastTraining(new Date(10000000000L));
        test2.setScheduledWeekDays(new ArrayList<>(Arrays.asList(com.example.trainingapp.Workout.DayOfWeek.Tuesday, com.example.trainingapp.Workout.DayOfWeek.Thursday)));
        test2.setExercises(new ArrayList<Exercise>(Arrays.asList(exercise1, exercise2, exercise3, exercise4)));
        routines.add(test2);

        com.example.trainingapp.Workout test3 = new com.example.trainingapp.Workout("UPPER BODY");
        test3.setLastTraining(new Date(100000000000L));
        test3.setScheduledWeekDays(new ArrayList<>(Arrays.asList(com.example.trainingapp.Workout.DayOfWeek.Wednesday, com.example.trainingapp.Workout.DayOfWeek.Saturday)));
        routines.add(test3);

        test3.setExercises(new ArrayList<Exercise>(Arrays.asList(exercise1, exercise2, exercise3, exercise4)));
        com.example.trainingapp.Workout test4 = new com.example.trainingapp.Workout("Lower body EXTREME 8MIN");
        test4.setLastTraining(new Date(100000000000000L));
        test4.setScheduledWeekDays(new ArrayList<>(Arrays.asList(com.example.trainingapp.Workout.DayOfWeek.Monday, com.example.trainingapp.Workout.DayOfWeek.Friday)));
        test4.setExercises(new ArrayList<Exercise>(Arrays.asList(exercise1, exercise2, exercise3, exercise4)));
        routines.add(test4);

        routines.add(test4);
        routines.add(test4);
        routines.add(test4);*/
    }
}
