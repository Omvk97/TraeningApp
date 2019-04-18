package com.example.trainingapp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.trainingapp.adapters.DeletableItems;
import com.example.trainingapp.adapters.MoveableItems;

public class ItemAnimations extends ItemTouchHelper.SimpleCallback {
    private static final String TAG = "ItemAnimations";
    private Drawable icon;
    private final ColorDrawable swipeBackground;
    private DeletableItems mDeletableItems;
    private MoveableItems mMoveableItems;

    public ItemAnimations(int movementFlagDragAndDrop, DeletableItems deletableItems) {
        super(movementFlagDragAndDrop, ItemTouchHelper.LEFT);
        mDeletableItems = deletableItems;
        if (deletableItems instanceof MoveableItems) {
            mMoveableItems = (MoveableItems) deletableItems;
        }
        icon = ContextCompat.getDrawable(deletableItems.getContext(), R.drawable.delete);
        swipeBackground = new ColorDrawable(Color.RED);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        if (mMoveableItems != null) {
            mMoveableItems.onViewMove(viewHolder.getAdapterPosition(), viewHolder1.getAdapterPosition());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int position = viewHolder.getAdapterPosition();
        mDeletableItems.deleteItem(position);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 6;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if (dX < 0) { // Swiping to the left
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            swipeBackground.setBounds(itemView.getRight() + ((int) dX) - 100,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else { // view is unSwiped
            swipeBackground.setBounds(0, 0, 0, 0);
            icon.setBounds(0, 0, 0, 0);
        }

        swipeBackground.draw(c);
        icon.draw(c);
    }


}
