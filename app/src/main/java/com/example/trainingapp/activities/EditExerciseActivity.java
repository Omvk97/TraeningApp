package com.example.trainingapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.trainingapp.R;
import com.example.trainingapp.Workout;
import com.example.trainingapp.WorkoutExercise;

public class EditExerciseActivity extends AppCompatActivity {
    // TODO - MAYBE MAKE THIS INTO A FRAGMENT AND ALSO EXERCISEHISTORY ACTIVITY AND THEN THE USER SHOULD BE ABLE TO NAVIGATE USING A BOTTOM BAR UNDER AN "EXERCISES" TAB BETWEEN THE INFORMATION AND HISTORY
    private static final String TAG = "EditExerciseActivity";
    private Bundle bundle;
    private WorkoutExercise mSelectedWorkoutExercise;
    private Workout selectedWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercise);
        setUp();
    }

    private void setUp() {
        setupExercise();
        setupToolbar();
        EditText exerciseName = findViewById(R.id.exerciseNameEditTxt);
        exerciseName.setText(mSelectedWorkoutExercise.getExerciseName());
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.editExerciseToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(mSelectedWorkoutExercise.getExerciseName());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setupExercise() {
        bundle = getIntent().getExtras();
        if (bundle != null) {
            mSelectedWorkoutExercise = (WorkoutExercise) bundle.get(AddExerciseActivity.EXERCISE_TO_PASSALONG);
            if (mSelectedWorkoutExercise == null) {
                mSelectedWorkoutExercise = new WorkoutExercise(""); // if nothing has been passed to bundle a new exercise is to be created
            }
            selectedWorkout = (Workout) bundle.get(WorkoutActivity.SELECTED_WORKOUT_ID_KEY);
        }
    }

    public void chooseMusclesWorked(View view) {
        final WorkoutExercise.MuscleCategory[] muscleGroups = WorkoutExercise.MuscleCategory.values();
        final CharSequence[] muscleOptions = new CharSequence[muscleGroups.length];
        int exercisePreSelectedPosition = -1;
        for (int i = 0; i < muscleGroups.length; i++) {
            muscleOptions[i] = muscleGroups[i].getStringRepresentation();
            if (mSelectedWorkoutExercise.getExerciseMuscleCategory() != null) {
                if (mSelectedWorkoutExercise.getExerciseMuscleCategory().equals(muscleGroups[i])) {
                    exercisePreSelectedPosition = i;
                }
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Muscle Group");
        builder.setSingleChoiceItems(muscleOptions, exercisePreSelectedPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSelectedWorkoutExercise.setExerciseMuscleCategory(muscleGroups[which]);
            }
        })
                .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    public void chooseExerciseCategory(View viev) {
        final WorkoutExercise.Category[] categories = WorkoutExercise.Category.values();
        final CharSequence[] categoryOptions = new CharSequence[categories.length];
        int exercisePreSelectedPosition = -1;
        for (int i = 0; i < categories.length; i++) {
            categoryOptions[i] = categories[i].getStringRepresentation();
            if (mSelectedWorkoutExercise.getCategory() != null) {
                if (mSelectedWorkoutExercise.getCategory().equals(categories[i])) {
                    exercisePreSelectedPosition = i;
                }
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Category");
        builder.setSingleChoiceItems(categoryOptions, exercisePreSelectedPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSelectedWorkoutExercise.setCategory(categories[which]);
            }
        })
                .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    public void uploadOwnPicture(View view) {
        // TODO - DIALOG BOX WITH SOME SORT OF CHOOSING OF PICTURE OR MAYBE URL UPLOADING
        // TODO selectedExericse.setImage(view.getdrawable)
    }

    private void saveExerciseName() {
        EditText exerciseNameEditTxt = findViewById(R.id.exerciseNameEditTxt);
        mSelectedWorkoutExercise.setExerciseName(exerciseNameEditTxt.getText().toString());

    }

    @Override
    public void onBackPressed() {
/*        // TODO - ADD DATABASE SUPPORT WHERE IT ADDS IT TO THE DATABSE SO THIS IS NOT AFHÆNGIG AF SELECEDWORKOUT AS THAT MAKES NO SENSE
        Intent addExerciseIntent = new Intent(this, AddExerciseActivity.class);
        saveExerciseName();
        addExerciseIntent.putExtra(AddExerciseActivity.EXERCISE_TO_PASSALONG, mSelectedWorkoutExercise);
        addExerciseIntent.putExtra(WorkoutActivity.SELECTED_WORKOUT_ID_KEY, selectedWorkout);
        startActivity(addExerciseIntent);*/
    }
}
