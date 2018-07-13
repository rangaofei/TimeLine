package io.github.rangaofei.sakatimeline.divider;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class SlideTouchHelperCallBack extends SakaItemTouchHelper.Callback {

    private RecyclerView.ViewHolder preViewHolder;
    private RecyclerView.ViewHolder curViewHolder;

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(0, swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.d("---", "onSwiped");
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        Log.d("---", "clearView");
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        Log.d("---", "dx=" + dX);
        if (actionState != ItemTouchHelper.ACTION_STATE_SWIPE) {
            return;
        }
        if (viewHolder != null) {
            curViewHolder = viewHolder;
        }
        View viewShow = ((ViewGroup) viewHolder.itemView).getChildAt(1);
        View viewHide = ((ViewGroup) viewHolder.itemView).getChildAt(0);
        int maxWidth = getMaxWidth(viewHide);
        if (!isCurrentlyActive) {
            if (viewShow.getTranslationX() >= maxWidth) {
                viewShow.setTranslationX(maxWidth);
            } else if (viewShow.getTranslationX() < maxWidth) {
                viewShow.setTranslationX(0);
            }
            return;
        }

        viewShow.setTranslationX(dX);
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        Log.d("---", "onSelectedChanged:" + actionState);
        if (actionState != SakaItemTouchHelper.ACTION_STATE_IDLE) {
            preViewHolder = curViewHolder;
            curViewHolder = viewHolder;
            if (preViewHolder != null)
                ((ViewGroup) preViewHolder.itemView).getChildAt(1).setTranslationX(0);
        }
    }

    public int getMaxWidth(View view) {
        return view.getWidth();
    }


}
