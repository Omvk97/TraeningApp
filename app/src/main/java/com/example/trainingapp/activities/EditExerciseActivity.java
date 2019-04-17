package com.example.trainingapp.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainingapp.DataRepository;
import com.example.trainingapp.PreDefinedExercise;
import com.example.trainingapp.R;
import com.example.trainingapp.Workout;
import com.example.trainingapp.WorkoutExercise;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

public class EditExerciseActivity extends AppCompatActivity {
    private static final String TAG = "EditExerciseActivity";
    private PreDefinedExercise selectedExercise = null;
    private Workout selectedWorkout = null;
    private DataRepository database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercise);
        database = DataRepository.getInstance(this);
        setUp();
    }

    private void setUp() {
        setupExercise();
        setupToolbar();
        loadExerciseImage();
        checkCustomExercise();
        EditText exerciseName = findViewById(R.id.exerciseNameEditTxt);
        exerciseName.setText(selectedExercise.getExerciseName());
        TextView selectedMuscleGroup = findViewById(R.id.selectedMuscleGroup);
        selectedMuscleGroup.setText(selectedExercise.getExerciseMuscleCategory().getStringRepresentation());
        TextView selectedCategory = findViewById(R.id.selectedCategory);
        selectedCategory.setText(selectedExercise.getCategory().getStringRepresentation());
    }

    private void setupExercise() {
        if (UserInteractions.getInstance().getSelectedExerciseID() != null) {
            selectedExercise = database.getPreDefinedExerciseByID(UserInteractions.getInstance().getSelectedExerciseID());
        } else {
            selectedExercise = new PreDefinedExercise("", null, null, "");
            selectedExercise.setCustomExercise(true);
            database.insertAllPreDefinedExercises(selectedExercise);
        }

        selectedWorkout = database.getWorkoutByID(UserInteractions.getInstance().getSelectedWorkoutID());
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.editExerciseToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(selectedExercise.getExerciseName());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void checkCustomExercise() {
        // TODO - Its not working
        if (selectedExercise.isCustomExercise()) {
            EditText exerciseNameText = findViewById(R.id.exerciseNameEditTxt);
            ImageView exerciseNameBlock = findViewById(R.id.exerciseNameBlock);
            Button uploadImageButton = findViewById(R.id.uploadImageBtn);
            ImageView uploadImageBlock = findViewById(R.id.uploadImageBlock);
            Button categoryBtn = findViewById(R.id.categoryBtn);
            ImageView setCategoryBlock = findViewById(R.id.setCategoryBlock);
            Button muscleGroupBtn = findViewById(R.id.muscleGroupBtn);
            ImageView setMuscleGroupBlock = findViewById(R.id.setMuscleGroupBlock);
            exerciseNameText.setFocusable(true);
            exerciseNameText.setFocusableInTouchMode(true);
            exerciseNameText.setClickable(true);
            uploadImageButton.setFocusable(true);
            uploadImageButton.setFocusableInTouchMode(true);
            uploadImageButton.setClickable(true);
            uploadImageBlock.setVisibility(View.INVISIBLE);
            categoryBtn.setClickable(true);
            setCategoryBlock.setVisibility(View.INVISIBLE);
            muscleGroupBtn.setClickable(true);
            setMuscleGroupBlock.setVisibility(View.INVISIBLE);
        }
    }

    public void chooseMusclesWorked(View view) {
        final WorkoutExercise.MuscleCategory[] muscleGroups = WorkoutExercise.MuscleCategory.values();
        final CharSequence[] muscleOptions = new CharSequence[muscleGroups.length];
        int exercisePreSelectedPosition = -1;
        for (int i = 0; i < muscleGroups.length; i++) {
            muscleOptions[i] = muscleGroups[i].getStringRepresentation();
            if (selectedExercise.getExerciseMuscleCategory() != null) {
                if (selectedExercise.getExerciseMuscleCategory().equals(muscleGroups[i])) {
                    exercisePreSelectedPosition = i;
                }
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Muscle Group");
        builder.setSingleChoiceItems(muscleOptions, exercisePreSelectedPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedExercise.setExerciseMuscleCategory(muscleGroups[which]);
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
            if (selectedExercise.getCategory() != null) {
                if (selectedExercise.getCategory().equals(categories[i])) {
                    exercisePreSelectedPosition = i;
                }
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Category");
        builder.setSingleChoiceItems(categoryOptions, exercisePreSelectedPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedExercise.setCategory(categories[which]);
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

    public static final int GALLERY_REQUEST_CODE = 1;
    public static final int MY_PERMISSIONS_REQUEST_GALLERY = 1; // Gets the result of the callback method

    public void uploadOwnPicture(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) { // Permission not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                // TODO - explain it or something
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_GALLERY);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_GALLERY);
            }
        } else { // Permission granted
            launchGalleryChooser();
        }
    }

    private void launchGalleryChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_GALLERY: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    launchGalleryChooser();
                } else {
                    Toast.makeText(this, "Can't Launch Gallery Chooser", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    //database.getData return the content URI for the selected Image
                    Uri selectedImageUri = null;
                    if (data != null) {
                        selectedImageUri = data.getData();
                    }
                    InputStream input;
                    Bitmap bitmapImage;
                    try {
                        input = this.getContentResolver().openInputStream(selectedImageUri);
                        bitmapImage = BitmapFactory.decodeStream(input);
                        Random randomInt = new Random();
                        File f = new File(this.getFilesDir(), "file" + randomInt.nextLong() + "name");
                        if (f.createNewFile()) {
                            // TODO - Do this in a background thread
                            //Convert bitmap to byte array
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmapImage.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, baos);
                            byte[] bitmapData = baos.toByteArray();
                            baos.close();
                            //write the bytes in file
                            FileOutputStream fos = new FileOutputStream(f);
                            fos.write(bitmapData);
                            fos.flush();
                            fos.close();
                        }
                        selectedExercise.setPictureUriString(f.toURI().toString());
                        loadExerciseImage();
                    } catch (FileNotFoundException e) {
                        Toast.makeText(this, "Could not handle image", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

            }
    }

    private void loadExerciseImage() {
        ImageView exerciseImage = findViewById(R.id.editExerciseImage);
        Picasso.get().load(selectedExercise.getPictureUriString())
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.error)
                .into(exerciseImage);
    }

    @Override
    public void onBackPressed() {
        UserInteractions.getInstance().setSelectedExerciseID(null); // No selected exercise anymore
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EditText exerciseNameEditTxt = findViewById(R.id.exerciseNameEditTxt);
        selectedExercise.setExerciseName(exerciseNameEditTxt.getText().toString());
        database.updateExercise(selectedExercise);
        updateExercisesFromWorkouts();
    }

    private void updateExercisesFromWorkouts() {
        List<Workout> allUserWorkout = database.getAllWorkouts();
        for (Workout workout : allUserWorkout) {
            for (WorkoutExercise exercise : workout.getExercises()) {
                if (exercise.getId() == selectedExercise.getId()) {
                    exercise.updateWorkoutExercise(selectedExercise);
                    database.updateWorkout(workout);
                }
            }
        }
    }
}
