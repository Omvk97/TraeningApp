package com.example.trainingapp.adapters;

public interface MoveableItems {
    void onViewMove(int oldPosition, int newPosition);
    void collapseViews();
    void expandViews();
}
