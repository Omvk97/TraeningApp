package com.example.trainingapp.activities;

/**
 * This class is to be used as a communication brdige between the activities, in order to avoid
 * using bundles as much, as it can get quite complex and confusing.
 * In order for this class to work properly it is of utmost importance that everytime a user interacts
 * with the attributes of this class, and the user doesn't use them anymore, that they are set to
 * null!!
 */
final class UserInteractions {
    private Long selectedWorkoutID = null;
    private Long selectedExerciseID = null;
    private static UserInteractions instance = new UserInteractions();

    public static UserInteractions getInstance() {
        return instance;
    }

    public Long getSelectedWorkoutID() {
        return selectedWorkoutID;
    }

    public void setSelectedWorkoutID(Long selectedWorkoutID) {
        this.selectedWorkoutID = selectedWorkoutID;
    }

    public Long getSelectedExerciseID() {
        return selectedExerciseID;
    }

    public void setSelectedExerciseID(Long selectedExerciseID) {
        this.selectedExerciseID = selectedExerciseID;
    }
}
