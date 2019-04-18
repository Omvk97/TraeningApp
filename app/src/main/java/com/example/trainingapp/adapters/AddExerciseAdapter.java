package com.example.trainingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trainingapp.PreDefinedExercise;
import com.example.trainingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AddExerciseAdapter extends RecyclerView.Adapter<AddExerciseAdapter.ExerciseViewHolder>  implements Filterable {
    private static final String TAG = "AddExerciseAdapter";
    private List<PreDefinedExercise> mPreDefinedExercises;
    private List<PreDefinedExercise> mPreDefinedExercisesFiltered;
    private NoteListener mNoteListener;

    public AddExerciseAdapter(List<PreDefinedExercise> preDefinedExercises, NoteListener noteListener) {
        mPreDefinedExercises = preDefinedExercises;
        mPreDefinedExercisesFiltered = preDefinedExercises;
        mNoteListener = noteListener;

    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_add_exercise, viewGroup, false);
        return new ExerciseViewHolder(view, mNoteListener);
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder exerciseViewHolder, int i) {
        final PreDefinedExercise preDefinedExercise = mPreDefinedExercisesFiltered.get(i);
        exerciseViewHolder.exerciseName.setText(preDefinedExercise.getExerciseName());
        exerciseViewHolder.editImageButton.setTag(i); // Tag to get position in array

        Picasso.get().load(preDefinedExercise.getPictureUriString())
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.error)
                .into(exerciseViewHolder.exerciseImage);
    }

    @Override
    public int getItemCount() {
        return mPreDefinedExercisesFiltered.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toLowerCase();
                if (charString.isEmpty()) {
                    mPreDefinedExercisesFiltered = mPreDefinedExercises;
                } else {
                    List<PreDefinedExercise> filteredList = new ArrayList<>();
                    for (PreDefinedExercise exercise : mPreDefinedExercises) {
                        String exerciseName = exercise.getExerciseName().toLowerCase();
                        String exerciseMuscleGroup = exercise.getExerciseMuscleCategory().getStringRepresentation()
                                .toLowerCase();
                        String exerciseCategory = exercise.getCategory().getStringRepresentation().toLowerCase();
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (exerciseName.contains(charString) || exerciseMuscleGroup.contains(charString) // TODO - Remove this as soon as filtering is done
                            || exerciseCategory.contains(charString)) {
                            filteredList.add(exercise);
                        }
                    }
                    mPreDefinedExercisesFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mPreDefinedExercisesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mPreDefinedExercisesFiltered = (ArrayList<PreDefinedExercise>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    protected class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView exerciseName;
        private ImageView exerciseImage;
        private ImageButton editImageButton;
        private NoteListener mNoteListener;

        ExerciseViewHolder(@NonNull View itemView, NoteListener noteListener) {
            super(itemView);
            this.exerciseName = itemView.findViewById(R.id.exerciseName);
            this.exerciseImage = itemView.findViewById(R.id.exerciseImage);
            this.editImageButton = itemView.findViewById(R.id.editImageButton);
            this.mNoteListener = noteListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mNoteListener.onNoteClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            mNoteListener.onLongNoteClick(getAdapterPosition());
            return true;
        }
    }


}
