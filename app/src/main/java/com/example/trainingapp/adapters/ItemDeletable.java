package com.example.trainingapp.adapters;

import android.content.Context;

public interface ItemDeletable {
    void deleteItem(int position);
    Context getContext();
}
