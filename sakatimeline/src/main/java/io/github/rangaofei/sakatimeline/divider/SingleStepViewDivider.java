package io.github.rangaofei.sakatimeline.divider;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import io.github.rangaofei.sakatimeline.TimeLineConfig;
import io.github.rangaofei.sakatimeline.customlayoutmanager.PerfectLinearLayoutManager;
import io.github.rangaofei.sakatimeline.exception.ExceptionMessage;

import static io.github.rangaofei.sakatimeline.divider.TimeLineType.StepViewType.BOTTOM_STEP_PROGRESS;
import static io.github.rangaofei.sakatimeline.divider.TimeLineType.StepViewType.LEFT_STEP_PROGRESS;
import static io.github.rangaofei.sakatimeline.divider.TimeLineType.StepViewType.RIGHT_STEP_PROGRESS;
import static io.github.rangaofei.sakatimeline.divider.TimeLineType.StepViewType.TOP_STEP_PROGRESS;

public class SingleStepViewDivider extends BaseDivider {
    private Paint textPaint;
    private Paint circlePaint;
    private Paint overColor;
    private Paint.FontMetrics fm;
    private RecyclerView recyclerView;
    private float currentNum;
    private PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
    private PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(mode);

    public SingleStepViewDivider(Context context, TimeLineConfig timeLineConfig) {
        super(context, timeLineConfig);
        overColor = new Paint();
        overColor.setColor(timeLineConfig.getStepViewConfig().getPreColor());
        overColor.setAntiAlias(true);

        overColor.setXfermode(porterDuffXfermode);
        textPaint = new Paint();
        textPaint.setTextSize(30);
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        circlePaint = new Paint();
        circlePaint.setStrokeWidth(5);
        circlePaint.setAntiAlias(true);
//        circlePaint.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.CLEAR));
        fm = textPaint.getFontMetrics();

        currentNum = timeLineConfig.getStepViewConfig().getDividerNum();
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof PerfectLinearLayoutManager) {
            switch (((PerfectLinearLayoutManager) layoutManager).getOrientation()) {
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
        super.getItemOffsets(outRect, view, parent, state);
        this.recyclerView = parent;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof PerfectLinearLayoutManager) {
            switch (((PerfectLinearLayoutManager) layoutManager).getOrientation()) {
                case PerfectLinearLayoutManager.HORIZONTAL:
                    int realHPadding = (int) padding;
                    if (timeLineConfig.getType() == TOP_STEP_PROGRESS) {
                        outRect.top = realHPadding;
//                        outRect.set(DEFAULT_DIVIDER_GAP, realHPadding, 0, DEFAULT_DIVIDER_GAP);
                    } else if (timeLineConfig.getType() == BOTTOM_STEP_PROGRESS) {
//                        outRect.set(DEFAULT_DIVIDER_GAP, 0, DEFAULT_DIVIDER_GAP, realHPadding);
                        outRect.bottom = realHPadding;
                    }
                    break;
                case PerfectLinearLayoutManager.VERTICAL:
                    int realVPadding = (int) padding;
                    if (timeLineConfig.getType() == LEFT_STEP_PROGRESS) {
                        outRect.left = realVPadding;
//                        outRect.set(realVPadding, DEFAULT_DIVIDER_GAP, 0, DEFAULT_DIVIDER_GAP);
                    } else if (timeLineConfig.getType() == RIGHT_STEP_PROGRESS) {
//                        outRect.set(0, DEFAULT_DIVIDER_GAP, realVPadding, DEFAULT_DIVIDER_GAP);
                        outRect.right = realVPadding;
                    }
                    break;
                default:
                    throwException(ExceptionMessage.UNKNOWN_ORIENTATION);
            }
        }
    }

    private void drawHorizontalStep(Canvas c, RecyclerView parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            int layoutId = c.saveLayer(0, 0, recyclerView.getWidth(), recyclerView.getHeight(), null);
            circlePaint.setColor(timeLineConfig.getStepViewConfig().getAfterColor());
            View view = parent.getChildAt(i);
            int circleX = view.getLeft() + view.getWidth() / 2;
            int circleY = 0, rectTop = 0, rectBottom = 0;
            if (timeLineConfig.getType() == TOP_STEP_PROGRESS) {
                circleY = view.getTop() - (int) padding / 2;
                rectTop = view.getTop() - (int) padding;
                rectBottom = view.getTop();
            } else if (timeLineConfig.getType() == BOTTOM_STEP_PROGRESS) {
                circleY = view.getBottom() + (int) padding / 2;
                rectTop = view.getBottom();
                rectBottom = view.getBottom() + (int) padding;
            }
            c.drawLine(view.getLeft() - globalRect.left, circleY,
                    view.getRight() + globalRect.right, circleY, circlePaint);
            c.drawCircle(circleX, circleY, padding / 3, circlePaint);

            if (i < currentNum && i > currentNum - 1) {
                c.drawRect(view.getLeft() - globalRect.left, rectTop,
                        (view.getRight() + globalRect.right) * (currentNum - i),
                        rectBottom, overColor);
            } else if (i <= currentNum - 1) {
                c.drawRect(view.getLeft() - globalRect.left, rectTop,
                        (view.getRight() + globalRect.right),
                        rectBottom, overColor);
            }
            if (timeLineConfig.getStepViewConfig().isShowStepText()) {
                String text = String.valueOf(i + 1);
                float w = textPaint.measureText(text);
                c.drawText(text, circleX - w / 2, circleY + (fm.bottom - fm.top) / 2 - fm.bottom, textPaint);
            }


        }
    }

    private void drawVerticalStep(Canvas c, RecyclerView parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            int layoutId = c.saveLayer(0, 0, recyclerView.getWidth(), recyclerView.getHeight(), null);
            View view = parent.getChildAt(i);
            circlePaint.setColor(timeLineConfig.getStepViewConfig().getAfterColor());
            int circleX = 0, rectLeft = 0, rectRight = 0;
            int circleY = view.getTop() + view.getHeight() / 2;
            if (timeLineConfig.getType() == LEFT_STEP_PROGRESS) {
                circleX = view.getLeft() - (int) padding / 2;
                rectLeft = view.getLeft() - (int) padding;
                rectRight = view.getLeft();
            } else if (timeLineConfig.getType() == RIGHT_STEP_PROGRESS) {
                circleX = view.getRight() + (int) padding / 2;
                rectLeft = view.getRight();
                rectRight = view.getRight() + (int) padding;
            }
            c.drawLine(circleX, view.getTop() - globalRect.top,
                    circleX, view.getBottom() + globalRect.bottom, circlePaint);
            c.drawCircle(circleX, circleY, padding / 3, circlePaint);

            if (i < currentNum && i > currentNum - 1) {
                c.drawRect(rectLeft, view.getTop() - globalRect.top,
                        rectRight,
                        (view.getBottom() + globalRect.bottom) * (currentNum - i), overColor);
            } else if (i <= currentNum - 1) {
                c.drawRect(rectLeft, view.getTop() - globalRect.top,
                        rectRight,
                        view.getBottom() + globalRect.bottom, overColor);
            }


            if (timeLineConfig.getStepViewConfig().isShowStepText()) {
                String text = String.valueOf(i + 1);
                float w = textPaint.measureText(text);
                c.drawText(text, circleX - w / 2, circleY + (fm.bottom - fm.top) / 2 - fm.bottom, textPaint);
            }
            c.restoreToCount(layoutId);
        }
    }

    public void updateDividerNum(int num, boolean showAnim) {
        if (num > recyclerView.getChildCount()) {
            num = recyclerView.getChildCount();
        }
        if (num < 1) {
            num = 1;
        }
        if (showAnim) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(currentNum, num);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.setDuration(2500);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (recyclerView != null) {
                        currentNum = (float) animation.getAnimatedValue();
                        timeLineConfig.getStepViewConfig().setDividerNum((int) currentNum);
                        Log.d("---", "vaule=" + currentNum);
                        recyclerView.invalidateItemDecorations();
                    }
                }
            });
            valueAnimator.start();

        } else {
            if (recyclerView != null) {
                currentNum = num;
                timeLineConfig.getStepViewConfig().setDividerNum((int) currentNum);
                recyclerView.invalidateItemDecorations();
            }
        }
    }

}
