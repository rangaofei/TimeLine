package io.github.rangaofei.sakatimeline.divider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.github.rangaofei.sakatimeline.TimeLineConfig;
import io.github.rangaofei.sakatimeline.adapter.AbstractTimeLineAdapter;

public class RightOnlyDivider extends BaseDivider {


    private Paint drawPaint;

    private Paint linePaint;

    public RightOnlyDivider(Context context, TimeLineConfig timeLineConfig) {
        super(context, timeLineConfig);
        drawPaint = new Paint();
        drawPaint.setColor(Color.parseColor("#259b24"));
        drawPaint.setStrokeWidth(timeLineConfig.getTimeStrokeWidth());
        linePaint = new Paint();
        linePaint.setColor(Color.parseColor("#259b24"));
        linePaint.setStrokeWidth(timeLineConfig.getTimeStrokeWidth());
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        float x = 0;
        for (int i = 0; i < parent.getChildCount(); i++) {
            final View view = parent.getChildAt(i);
            if (parent.getChildViewHolder(view) instanceof AbstractTimeLineAdapter.KeyViewHolder) {
                drawPaint.setColor(Color.RED);
                x = view.getRight() + padding / 2;
            } else if (parent.getChildViewHolder(view) instanceof AbstractTimeLineAdapter.ValueViewHolder) {
                drawPaint.setColor(Color.GREEN);
                x = view.getRight() + padding + padding / 2;
            }
            //画垂直的线
            linePaint.setStrokeWidth(timeLineConfig.getTimeStrokeWidth());
            if (i != 0 && i != parent.getChildCount() - 1) {
                c.drawLine(x, view.getTop() - 10,
                        x, view.getBottom() + 10, linePaint);
            } else if (i == 0) {
                c.drawLine(x, view.getTop() + view.getHeight() / 2 + padding / 5 + 5,
                        x, view.getBottom() + 10, linePaint);
            } else if (i == parent.getChildCount() - 1) {
                c.drawLine(x, view.getTop() - 10,
                        x, view.getBottom() - view.getHeight() / 2 - padding / 5 - 5, linePaint);
            }


            //画横线
            c.drawLine(x - padding / 4 - 10, view.getTop() + view.getHeight() / 2,
                    view.getRight() - 20, view.getTop() + view.getHeight() / 2, linePaint);

            //画圆圈
            drawPaint.setStyle(Paint.Style.FILL);
            c.drawCircle(x, view.getTop() + view.getHeight() / 2, padding / 5, drawPaint);
            drawPaint.setStyle(Paint.Style.STROKE);
            drawPaint.setStrokeWidth(5);
            c.drawCircle(x, view.getTop() + view.getHeight() / 2, padding / 5 + 5, drawPaint);

        }
        drawPaint.setColor(Color.parseColor("#259b24"));

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int realPadding = (int) padding;
        if (parent.getChildViewHolder(view) instanceof AbstractTimeLineAdapter.KeyViewHolder) {

        } else if (parent.getChildViewHolder(view) instanceof AbstractTimeLineAdapter.ValueViewHolder) {
            realPadding = 2 * realPadding;
        }
        outRect.set(0, 10, realPadding, 10);
    }
}
