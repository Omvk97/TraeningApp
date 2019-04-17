package com.example.trainingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.trainingapp.R;
import com.example.trainingapp.Workout;

import java.util.List;

public class WorkoutRoutinesOverviewAdapter extends RecyclerView.Adapter<WorkoutRoutinesOverviewAdapter.WorkoutOverviewViewHolder> {
    private List<Workout> workouts;
    private OnNoteListener onNoteListener;

    public WorkoutRoutinesOverviewAdapter(List<Workout> workouts, OnNoteListener onNoteListener) {
        this.workouts = workouts;
        this.onNoteListener = onNoteListener;
    }

    @Override
    public WorkoutOverviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.workout_overview, viewGroup, false);
        return new WorkoutOverviewViewHolder(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(WorkoutOverviewViewHolder workoutViewHolder, int i) {
        Workout workoutAtPosition = workouts.get(i);
        workoutViewHolder.lastTrainingDone.setText(workoutAtPosition.getLastTrainingString());
        workoutViewHolder.routineTitle.setText(workoutAtPosition.getTitle());
        workoutViewHolder.routineDays.setText(workoutAtPosition.getScheduledWeekDaysString());
        workoutViewHolder.startWorkoutBtn.setTag(String.valueOf(workoutAtPosition.getId())); // SETTING THE ID OF THE WORKOUT SO IT CAN BE FOUND FROM THE DATABASE
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    protected class WorkoutOverviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView lastTrainingDone;
        private TextView routineTitle;
        private TextView routineDays;
        private Button startWorkoutBtn;
        private OnNoteListener onNoteListener;

        WorkoutOverviewViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            this.lastTrainingDone = itemView.findViewById(R.id.lastTrainingDoneDateTxt);
            this.routineTitle = itemView.findViewById(R.id.routineTitleTxt);
            this.routineDays = itemView.findViewById(R.id.routineDaysTxt);
            this.startWorkoutBtn = itemView.findViewById(R.id.startWorkoutBtn);
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
