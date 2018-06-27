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
    protected Rect globalRect = new Rect();
    protected static final int DEFAULT_LEFT_DIVIDER_GAP = 10;
    protected static final int DEFAULT_RIGHT_DIVIDER_GAP = 10;
    protected static final int DEFAULT_TOP_DIVIDER_GAP = 10;
    protected static final int DEFAULT_BOTTOM_DIVIDER_GAP = 10;


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
        outRect.set(DEFAULT_LEFT_DIVIDER_GAP, DEFAULT_TOP_DIVIDER_GAP,
                DEFAULT_RIGHT_DIVIDER_GAP, DEFAULT_BOTTOM_DIVIDER_GAP);
        globalRect.set(outRect);

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
