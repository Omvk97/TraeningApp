package com.example.trainingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.trainingapp.WorkoutExercise;
import com.example.trainingapp.ExerciseSet;
import com.example.trainingapp.R;

import java.util.List;

public class StartedWorkoutAdapter extends RecyclerView.Adapter<StartedWorkoutAdapter.StartedWorkoutViewHolder> {
    private List<WorkoutExercise> mWorkoutExercises;
    private OnNoteListener onNoteListener;

    public StartedWorkoutAdapter(List<WorkoutExercise> workoutExercises, OnNoteListener onNoteListener) {
        this.mWorkoutExercises = workoutExercises;
        this.onNoteListener = onNoteListener;
    }

    @Override
    public StartedWorkoutViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_started_workout_exercise, viewGroup, false);
        return new StartedWorkoutViewHolder(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(StartedWorkoutViewHolder workoutExerciseViewHolder, int i) {
        workoutExerciseViewHolder.exerciseName.setText(mWorkoutExercises.get(i).getExerciseName());
        TableLayout tableLayout = workoutExerciseViewHolder.exerciseSets;

        for (ExerciseSet set : mWorkoutExercises.get(i).getSets()) {
            View tester = LayoutInflater.from(tableLayout.getContext()).inflate(R.layout.exercise_set_view, null, false);
            TextView setNumber = tester.findViewById(R.id.setNumberTxt);
            TextView bestSetHistory = tester.findViewById(R.id.setBestHistoryTxt);
            TextView setWeightTxt = tester.findViewById(R.id.setWeightTxt);
            TextView setReps = tester.findViewById(R.id.setRepsTxt);
            int indexOfSet = mWorkoutExercises.get(i).getSets().indexOf(set);
            setNumber.setText(Integer.toString(indexOfSet + 1));
            bestSetHistory.setText(mWorkoutExercises.get(i).getBestHistory(indexOfSet));
            setWeightTxt.setText(mWorkoutExercises.get(i).getLastPerformedSetWeight());
            setReps.setText(mWorkoutExercises.get(i).getLastPerformedSetReps());
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

    protected class StartedWorkoutViewHolder extends ViewHolder implements OnClickListener, OnLongClickListener {
        private TextView exerciseName;
        private TableLayout exerciseSets;
        private Context context;
        private OnNoteListener onNoteListener;

        StartedWorkoutViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.startedWExerciseTxt);
            exerciseSets = itemView.findViewById(R.id.startedWorkSetTable);
            context = itemView.getContext();
            onNoteListener = onNoteListener;
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
