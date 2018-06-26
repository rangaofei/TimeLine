package io.github.rangaofei.sakatimeline.customlayoutmanager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class TimeLineLayoutManager extends GridLayoutManager {

    private int gap = -1;

    public TimeLineLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TimeLineLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        int maxRight = 0;
        if (gap == -1) {
            for (int i = 0; i < getChildCount(); i = i + 2) {
                View view = getChildAt(i);
                if (maxRight < view.getRight()) {
                    maxRight = view.getRight();
                }
//            view.setLeft(200);
                Log.d("---", "view.left=" + view.getLeft() + "view.right=" + view.getRight());
                Log.d("---", "view.w=" + view.getWidth() + "view.h=" + view.getHeight());
            }
        } else {
            int l = getChildAt(1).getLeft();
            maxRight = l - gap;
        }

        for (int i = 0; i < getChildCount(); i = i + 2) {
            View view = getChildAt(i);
            int w = view.getWidth();
            view.setRight(maxRight);
            view.setLeft(view.getRight() - w);
            view.getHeight();
        }
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }
}
