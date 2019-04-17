package com.example.trainingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.trainingapp.DataRepository;
import com.example.trainingapp.R;
import com.example.trainingapp.Workout;
import com.example.trainingapp.activities.MainActivity;

import java.util.List;

public class WorkoutOverviewAdapter extends RecyclerView.Adapter<WorkoutOverviewAdapter.ViewHolder> implements ItemDeletable {
    private List<Workout> mWorkouts;
    private OnNoteListener mOnNoteListener;
    private MainActivity mActivity;
    private Workout mDeletedWorkout;
    private int mDeletedWorkoutIndex;
    private DataRepository mDatabase;

    public WorkoutOverviewAdapter(List<Workout> workouts, MainActivity activity) {
        this.mWorkouts = workouts;
        this.mOnNoteListener = activity;
        this.mActivity = activity;
        mDatabase = DataRepository.getInstance(activity.getApplicationContext());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.workout_overview, viewGroup, false);
        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Workout workoutAtPosition = mWorkouts.get(i);
        viewHolder.lastTrainingDone.setText(workoutAtPosition.getLastTrainingString());
        viewHolder.routineTitle.setText(workoutAtPosition.getTitle());
        viewHolder.routineDays.setText(workoutAtPosition.getScheduledWeekDaysString());
        viewHolder.startWorkoutBtn.setTag(String.valueOf(workoutAtPosition.getId())); // SETTING THE ID OF THE WORKOUT SO IT CAN BE FOUND FROM THE DATABASE
    }

    @Override
    public int getItemCount() {
        return mWorkouts.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView lastTrainingDone;
        private TextView routineTitle;
        private TextView routineDays;
        private Button startWorkoutBtn;
        private OnNoteListener onNoteListener;

        ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
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

    @Override
    public Context getContext() {
        return mActivity.getApplicationContext();
    }

    @Override
    public void deleteItem(final int position) {
        mDeletedWorkout = mWorkouts.get(position);
        mDeletedWorkoutIndex = position;
        mWorkouts.remove(position);
        notifyDataSetChanged();
        mDatabase.deleteWorkouts(mDeletedWorkout);
        View contextView = mActivity.findViewById(R.id.workoutRV);
        String snackbarMessage = mActivity.getString(R.string.deletion, mDeletedWorkout.getTitle());
        Snackbar addedExerciseSnack = Snackbar.make(contextView, snackbarMessage, Snackbar.LENGTH_LONG);
        addedExerciseSnack.setAction(R.string.undo_string, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWorkouts.add(mDeletedWorkoutIndex, mDeletedWorkout);
                notifyDataSetChanged();
                mDatabase.insertWorkout(mDeletedWorkout);
            }
        });
        addedExerciseSnack.show();
    }
}
