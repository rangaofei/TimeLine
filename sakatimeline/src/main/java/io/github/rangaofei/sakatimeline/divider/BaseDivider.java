package io.github.rangaofei.sakatimeline.divider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.github.rangaofei.sakatimeline.TimeLineConfig;
import io.github.rangaofei.sakatimeline.exception.BaseException;
import io.github.rangaofei.sakatimeline.exception.ExceptionMessage;

import static io.github.rangaofei.sakatimeline.exception.ExceptionMessage.*;

public abstract class BaseDivider extends RecyclerView.ItemDecoration {
    protected Context context;
    protected float padding;
    protected TimeLineConfig timeLineConfig;
    protected static final int DEFAULT_DIVIDER_GAP = 0;


    public BaseDivider(Context context, TimeLineConfig timeLineConfig) {
        this.context = context;
        this.timeLineConfig = timeLineConfig;
        this.padding = timeLineConfig.getPadding();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        onChildDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        onChildDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager == null) {
            throwException(LAYOUT_MANAGER_NULL);
        }
        if (layoutManager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            switch (orientation) {
                case LinearLayoutManager.VERTICAL:
                    getVerticalItemOffsets(outRect, view, parent, state);
                    break;
                case LinearLayoutManager.HORIZONTAL:
                    getHorizontalItemOffsets(outRect, view, parent, state);
                    break;
                default:
                    throwException(UNKNOWN_ORIENTATION);
                    break;
            }
        }

    }

    public void getHorizontalItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

    }

    public void getVerticalItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

    }

    protected void throwException(ExceptionMessage message) {
        throw new BaseException(message);

    }

    public abstract void onChildDraw(Canvas c, RecyclerView parent, RecyclerView.State state);

    public abstract void onChildDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state);
}
