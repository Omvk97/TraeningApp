package com.example.trainingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.trainingapp.ExerciseSet;
import com.example.trainingapp.R;
import com.example.trainingapp.WorkoutExercise;

public class SetAdapter extends ArrayAdapter<ExerciseSet> {
    private int ressourceLayout;
    private Context context;
    private WorkoutExercise workoutExercise;

       public SetAdapter(Context context, int ressourceLayout, WorkoutExercise exercise) {
        super(context, ressourceLayout, exercise.getSets());
        this.ressourceLayout = ressourceLayout;
        this.context = context;
        this.workoutExercise = exercise;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.exercise_set_test_view, null);
        }

        ExerciseSet set = getItem(position);

        if (set != null) {
            TextView setNumber = view.findViewById(R.id.setNumberTestTxt);
            TextView setBest = view.findViewById(R.id.setBestTestTxt);
            TextView setWeight = view.findViewById(R.id.setWeightTestTxt);
            TextView setReps = view.findViewById(R.id.setRepsTestTxt);

            int indexOfSet = workoutExercise.getSets().indexOf(set);
            setNumber.setText(String.valueOf(indexOfSet + 1));
            setBest.setText(String.valueOf(workoutExercise.getBestSet(indexOfSet)));
            setWeight.setText(workoutExercise.getLastPerformedSetWeight());
            setReps.setText(workoutExercise.getLastPerformedSetReps());
        }

        return view;
    }
}
