package io.github.rangaofei.sakatimeline.divider;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.github.rangaofei.sakatimeline.TimeLineConfig;

public class TimeLineDivider extends RecyclerView.ItemDecoration {
    private Rect itemRect = new Rect();
    private Paint paint;
    private Paint textPaint;
    private TimeLineConfig timeLineConfig;

    public TimeLineDivider() {
        paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        paint.setAntiAlias(true);
        textPaint = new Paint(paint);
        textPaint.setColor(Color.BLACK);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(50);
        textPaint.setStrokeWidth(20);
    }

    public void setTimeLineConfig(TimeLineConfig timeLineConfig) {
        this.timeLineConfig = timeLineConfig;
        textPaint.setStrokeWidth(timeLineConfig.getTimeStrokeWidth());
        textPaint.setColor(timeLineConfig.getTimeColor());
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {


    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        for (int i = 0; i < parent.getLayoutManager().getChildCount(); i++) {
            final View view = parent.getChildAt(i);
            if (i % 2 == 1) {

            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (!(parent.getLayoutManager() instanceof LinearLayoutManager)) {
            throw new RuntimeException("do not support");

        }

    }


}
