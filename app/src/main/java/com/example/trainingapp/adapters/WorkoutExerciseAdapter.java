package com.example.trainingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.trainingapp.WorkoutExercise;
import com.example.trainingapp.ExerciseSet;
import com.example.trainingapp.R;

import java.util.ArrayList;
import java.util.List;

public class WorkoutExerciseAdapter extends RecyclerView.Adapter<WorkoutExerciseAdapter.WorkoutExerciseViewHolder> {
    private List<WorkoutExercise> mWorkoutExercises;
    private OnNoteListener onNoteListener;

    public WorkoutExerciseAdapter(ArrayList<WorkoutExercise> workoutExercises, OnNoteListener onNoteListener) {
        this.mWorkoutExercises = workoutExercises;
        this.onNoteListener = onNoteListener;
    }


    @Override
    public WorkoutExerciseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_workout_exercise, viewGroup, false);
        return new WorkoutExerciseViewHolder(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(WorkoutExerciseViewHolder workoutExerciseViewHolder, int i) {
        workoutExerciseViewHolder.exerciseTitle.setText(mWorkoutExercises.get(i).getExerciseName());
        workoutExerciseViewHolder.restBetweenSets.setText(mWorkoutExercises.get(i).getRestTimerString());

        TableLayout tableLayout = workoutExerciseViewHolder.exerciseSets;
        for (ExerciseSet set : mWorkoutExercises.get(i).getSets()) {
            View tester = LayoutInflater.from(tableLayout.getContext()).inflate(R.layout.exercise_set_view, null, false);
            TextView setNumber = tester.findViewById(R.id.setNumberTxt);
            TextView bestSetHistory = tester.findViewById(R.id.setBestHistoryTxt);
            TextView setWeightTxt = tester.findViewById(R.id.setWeightTxt);
            TextView setReps = tester.findViewById(R.id.setRepsTxt);
            setNumber.setText(Integer.toString(mWorkoutExercises.get(i).getSets().indexOf(set) + 1));
            bestSetHistory.setText(mWorkoutExercises.get(i).getBestHistory(i));
            setWeightTxt.setText("50");
            setReps.setText("8");
            tableLayout.addView(tester);
        }
    }

    @Override
    public int getItemCount() {
        return mWorkoutExercises.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    protected class WorkoutExerciseViewHolder extends ViewHolder implements OnClickListener, OnLongClickListener {
        private TextView exerciseTitle;
        private TextView restBetweenSets;
        private ImageView exerciseImage;
        private TableLayout exerciseSets;
        private OnNoteListener onNoteListener;

        WorkoutExerciseViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            exerciseTitle = itemView.findViewById(R.id.workoutExerciseTitleTxt);
            restBetweenSets = itemView.findViewById(R.id.restBetweenSetsTxt);
            exerciseImage = itemView.findViewById(R.id.workoutExerciseImage);
            exerciseSets = itemView.findViewById(R.id.exerciseSetsRV);

            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onNoteListener.onLongNoteClick(getAdapterPosition());
            return true;
        }
    }
}
