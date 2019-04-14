package com.example.trainingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trainingapp.WorkoutExercise;
import com.example.trainingapp.R;

import java.util.List;

public class AddExerciseAdapter extends RecyclerView.Adapter<AddExerciseAdapter.ExerciseViewHolder> {
    private List<WorkoutExercise> mWorkoutExercises;
    private OnNoteListener onNoteListener;

    public AddExerciseAdapter(List<WorkoutExercise> workoutExercises, OnNoteListener onNoteListener) {
        this.mWorkoutExercises = workoutExercises;
        this.onNoteListener = onNoteListener;
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_add_exercise, viewGroup, false);
        return new ExerciseViewHolder(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder exerciseViewHolder, int i) {
        exerciseViewHolder.exerciseName.setText(mWorkoutExercises.get(i).getExerciseName());
        exerciseViewHolder.exerciseImage.setBackgroundResource(R.drawable.image_timer);
        exerciseViewHolder.editImageButton.setTag(i); // Tag to get position in array
    }

    @Override
    public int getItemCount() {
        return mWorkoutExercises.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    protected class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView exerciseName;
        private ImageView exerciseImage;
        private ImageButton editImageButton;
        private OnNoteListener onNoteListener;

        ExerciseViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            this.exerciseName = itemView.findViewById(R.id.exerciseName);
            this.exerciseImage = itemView.findViewById(R.id.exerciseImage);
            this.editImageButton = itemView.findViewById(R.id.editImageButton);
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
