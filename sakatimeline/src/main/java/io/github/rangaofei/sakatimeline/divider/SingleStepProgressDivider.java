package io.github.rangaofei.sakatimeline.divider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import io.github.rangaofei.sakatimeline.TimeLineConfig;
import io.github.rangaofei.sakatimeline.exception.ExceptionMessage;

public class SingleStepProgressDivider extends BaseDivider {
    private Paint textPaint;
    private Paint circlePaint;
    private Paint.FontMetrics fm;

    public SingleStepProgressDivider(Context context, TimeLineConfig timeLineConfig) {
        super(context, timeLineConfig);
        textPaint = new Paint();
        textPaint.setTextSize(30);
        textPaint.setColor(Color.WHITE);
        circlePaint = new Paint();
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStrokeWidth(5);
        fm = textPaint.getFontMetrics();
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            switch (((LinearLayoutManager) layoutManager).getOrientation()) {
                case LinearLayoutManager.HORIZONTAL:
                    drawHorizontalStep(c, parent);
                    break;
                case LinearLayoutManager.VERTICAL:
                    drawVerticalStep(c, parent);
                    break;
                default:
                    throwException(ExceptionMessage.UNKNOWN_ORIENTATION);
            }
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            switch (((LinearLayoutManager) layoutManager).getOrientation()) {
                case LinearLayoutManager.HORIZONTAL:
                    int realHPadding = (int) padding;
                    outRect.set(DEFAULT_DIVIDER_GAP, realHPadding, 0, DEFAULT_DIVIDER_GAP);
                    break;
                case LinearLayoutManager.VERTICAL:
                    int realVPadding = (int) padding;
                    outRect.set(realVPadding, DEFAULT_DIVIDER_GAP, 0, DEFAULT_DIVIDER_GAP);
                    break;
                default:
                    throwException(ExceptionMessage.UNKNOWN_ORIENTATION);
            }
        }
    }

    private void drawHorizontalStep(Canvas c, RecyclerView parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            int circleX = view.getLeft() + view.getWidth() / 2;
            int circleY = view.getTop() - (int) padding / 2;
            Log.d("---", "padding="+padding+",x=" + circleX + ",y=" + circleY);
            c.drawLine(view.getLeft(), circleY, view.getRight(), circleY, circlePaint);
            c.drawCircle(circleX, circleY, padding / 3, circlePaint);
            String text = String.valueOf(i + 1);
            float w = textPaint.measureText(text);
            c.drawText(text, circleX - w / 2, circleY + (fm.bottom - fm.top) / 2 - fm.bottom, textPaint);
        }
    }

    private void drawVerticalStep(Canvas c, RecyclerView parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            int circleX = view.getLeft() - (int) padding / 2;
            int circleY = view.getTop() + view.getHeight() / 2;
            Log.d("---", "x=" + circleX + ",y=" + circleY);
            c.drawLine(circleX, view.getTop(), circleX, view.getBottom(), circlePaint);
            c.drawCircle(circleX, circleY, padding / 3, circlePaint);
            String text = String.valueOf(i + 1);
            float w = textPaint.measureText(text);
            c.drawText(text, circleX - w / 2, circleY + (fm.bottom - fm.top) / 2 - fm.bottom, textPaint);
        }
    }
}
