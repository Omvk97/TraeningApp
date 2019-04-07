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

import com.example.trainingapp.Exercise;
import com.example.trainingapp.ExerciseSet;
import com.example.trainingapp.R;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private List<Exercise> exercises;
    private OnNoteListener onNoteListener;

    public ExerciseAdapter(List<Exercise> exercises, OnNoteListener onNoteListener) {
        this.exercises = exercises;
        this.onNoteListener = onNoteListener;
    }

    @Override
    public ExerciseAdapter.ExerciseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_workout_details, viewGroup, false);
        return new ExerciseAdapter.ExerciseViewHolder(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(ExerciseAdapter.ExerciseViewHolder exerciseViewHolder, int i) {
        exerciseViewHolder.exerciseTitle.setText(exercises.get(i).getExerciseName());
        exerciseViewHolder.restBetweenSets.setText(exercises.get(i).getRestTimer());

        TableLayout tableLayout = exerciseViewHolder.exerciseSets;

        for (ExerciseSet set : exercises.get(i).getSets()) {
            View tester = LayoutInflater.from(tableLayout.getContext()).inflate(R.layout.exercise_set_view, null, false);
            TextView setNumber = tester.findViewById(R.id.setNumberTxt);
            TextView bestSetHistory = tester.findViewById(R.id.setBestHistoryTxt);
            TextView setWeightTxt = tester.findViewById(R.id.setWeightTxt);
            TextView setReps = tester.findViewById(R.id.setRepsTxt);
            setNumber.setText(Integer.toString(exercises.get(i).getSets().indexOf(set) + 1));
            bestSetHistory.setText(exercises.get(i).getBestHistory());
            setWeightTxt.setText("50");
            setReps.setText("8");
            tableLayout.addView(tester);
        }
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface OnNoteListener {
        void onNoteClick(int position);

        void onLongNoteClick(int position);
    }

    public class ExerciseViewHolder extends ViewHolder implements OnClickListener, OnLongClickListener {
        private TextView exerciseTitle;
        private TextView restBetweenSets;
        private ImageView exerciseImage;
        private TableLayout exerciseSets;
        private OnNoteListener onNoteListener;

        ExerciseViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            exerciseTitle = itemView.findViewById(R.id.exerciseTitleTxt);
            restBetweenSets = itemView.findViewById(R.id.restBetweenSetsTxt);
            exerciseImage = itemView.findViewById(R.id.exerciseImage);
            exerciseSets = itemView.findViewById(R.id.exerciseSets);

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
