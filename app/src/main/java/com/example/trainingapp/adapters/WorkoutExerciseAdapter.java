package com.example.trainingapp.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.trainingapp.DataRepository;
import com.example.trainingapp.R;
import com.example.trainingapp.WorkoutExercise;
import com.example.trainingapp.activities.WorkoutActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WorkoutExerciseAdapter extends RecyclerView.Adapter<WorkoutExerciseAdapter.WorkoutExerciseViewHolder> implements ItemDeletable {
    private List<WorkoutExercise> mWorkoutExercises;
    private OnNoteListener mOnNoteListener;
    private WorkoutActivity mActivity;
    private WorkoutExercise mDeletedExercise;
    private int mDeletedExerciseIndex;
    private DataRepository mDatabase;

    public WorkoutExerciseAdapter(ArrayList<WorkoutExercise> workoutExercises, WorkoutActivity workoutActivity) {
        mActivity = workoutActivity;
        mWorkoutExercises = workoutExercises;
        mOnNoteListener = workoutActivity;
    }

    @Override
    public WorkoutExerciseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_workout_exercise, viewGroup, false);
        return new WorkoutExerciseViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(WorkoutExerciseViewHolder workoutExerciseViewHolder, int i) {
        workoutExerciseViewHolder.exerciseTitle.setText(mWorkoutExercises.get(i).getExerciseName());
        workoutExerciseViewHolder.restBetweenSets.setText(mWorkoutExercises.get(i).getRestTimerString());
        workoutExerciseViewHolder.addSetBtn.setTag(String.valueOf(i)); // Position in exercises arraylist for later reference of which exercise it belongs to
        workoutExerciseViewHolder.adjustRestTimerBtn.setTag(String.valueOf(i)); // Position in exercises arraylist for later reference of which exercise it belongs to
        ListView setsListView = workoutExerciseViewHolder.setsList;
        SetAdapter customAdapter = new SetAdapter(mActivity.getApplicationContext(), R.layout.exercise_set_test_view, mWorkoutExercises.get(i));
        setsListView.setAdapter(customAdapter);
        if (setsListView.getHeaderViewsCount() == 0) {
            setListViewHeader(setsListView);
        }

        Picasso.get().load(mWorkoutExercises.get(i).getPictureUriString())
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.error)
                .into(workoutExerciseViewHolder.exerciseImage);
    }

    @Override
    public int getItemCount() {
        return mWorkoutExercises.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public Context getContext() {
        return mActivity.getApplicationContext();
    }

    @Override
    public void deleteItem(final int position) {
        mDeletedExercise = mWorkoutExercises.get(position);
        mDeletedExerciseIndex = position;
        mWorkoutExercises.remove(position);
        notifyDataSetChanged();
        View contextView = mActivity.findViewById(R.id.routine_details_RV);
        String snackbarMessage = mActivity.getString(R.string.deletion, mDeletedExercise.getExerciseName());
        Snackbar addedExerciseSnack = Snackbar.make(contextView, snackbarMessage, Snackbar.LENGTH_LONG);
        addedExerciseSnack.setAction(R.string.undo_string, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWorkoutExercises.add(mDeletedExerciseIndex, mDeletedExercise);
                notifyDataSetChanged();
            }
        });
        addedExerciseSnack.show();
    }

    protected class WorkoutExerciseViewHolder extends ViewHolder implements OnClickListener, OnLongClickListener {
        private TextView exerciseTitle;
        private TextView restBetweenSets;
        private ListView setsList;
        private Button addSetBtn;
        private Button adjustRestTimerBtn;
        private ImageView exerciseImage;
        private OnNoteListener onNoteListener;

        WorkoutExerciseViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            exerciseTitle = itemView.findViewById(R.id.workoutExerciseTitleTxt);
            restBetweenSets = itemView.findViewById(R.id.restBetweenSetsTxt);
            setsList = itemView.findViewById(R.id.setsListView);
            addSetBtn = itemView.findViewById(R.id.addSetBtn);
            adjustRestTimerBtn = itemView.findViewById(R.id.adjustRestTimerBtn);
            exerciseImage = itemView.findViewById(R.id.workoutExerciseImage);


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

    private void setListViewHeader(ListView setsListView) {
        View headerView = LayoutInflater.from(mActivity.getApplicationContext()).inflate(R.layout.exercise_set_test_view, null);
        TextView headerSetNumber = headerView.findViewById(R.id.setNumberTestTxt);
        headerSetNumber.setText("SET");
        headerSetNumber.setTypeface(null, Typeface.BOLD);
        TextView headerBest = headerView.findViewById(R.id.setBestTestTxt);
        headerBest.setText("BEST");
        headerBest.setTypeface(null, Typeface.BOLD);
        TextView headerWeight = headerView.findViewById(R.id.setWeightTestTxt);
        headerWeight.setText("KG");
        headerWeight.setTypeface(null, Typeface.BOLD);
        TextView headerReps = headerView.findViewById(R.id.setRepsTestTxt);
        headerReps.setText("REPS");
        headerReps.setTypeface(null, Typeface.BOLD);
        setsListView.addHeaderView(headerView);
    }
}
