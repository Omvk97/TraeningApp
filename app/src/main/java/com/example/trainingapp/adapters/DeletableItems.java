package com.example.trainingapp.adapters;

import android.content.Context;

public interface ItemMotionContract {
    void deleteItem(int position);
    void onViewMoved(int oldPosition, int newPosition);
    Context getContext();
}
