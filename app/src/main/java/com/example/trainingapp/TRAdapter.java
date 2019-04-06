package com.example.trainingapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TRAdapter extends RecyclerView.Adapter<TRAdapter.RoutineViewHolder> {

    private List<TrainingRoutine> routines;
    private OnNoteListener onNoteListener;

    public TRAdapter(List<TrainingRoutine> routines, OnNoteListener onNoteListener) {
        this.routines = routines;
        this.onNoteListener = onNoteListener;
    }

    @Override
    public TRAdapter.RoutineViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.workout_routine_view, viewGroup, false);
        RoutineViewHolder routineViewHolder = new RoutineViewHolder(view, onNoteListener);
        return routineViewHolder;
    }

    @Override
    public void onBindViewHolder(TRAdapter.RoutineViewHolder routineHolder, int i) {
        routineHolder.lastTraining.setText(String.format("Last training: %s", routines.get(i).getLastTraining().toString()));
        routineHolder.routineTitle.setText(routines.get(i).getRoutineTitle());
        routineHolder.routineWeekDays.setText(routines.get(i).getScheduledWeekDays());

    }

    @Override
    public int getItemCount() {
        return routines.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class RoutineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView lastTraining;
        private TextView routineTitle;
        private TextView routineWeekDays;
        private OnNoteListener onNoteListener;

        public RoutineViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            lastTraining = itemView.findViewById(R.id.lastTrainingDoneDate);
            routineTitle = itemView.findViewById(R.id.trainingRoutineTitleTxtV);
            routineWeekDays = itemView.findViewById(R.id.routineDaysTxt);
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

    public interface OnNoteListener {
        void onNoteClick(int position);

        void onLongNoteClick(int position);
    }
}
