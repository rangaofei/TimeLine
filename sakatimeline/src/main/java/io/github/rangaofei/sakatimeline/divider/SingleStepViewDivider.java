package io.github.rangaofei.sakatimeline.divider;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.LinearInterpolator;

import io.github.rangaofei.sakatimeline.config.TimeLineConfig;
import io.github.rangaofei.sakatimeline.customlayoutmanager.PerfectLinearLayoutManager;
import io.github.rangaofei.sakatimeline.exception.ExceptionMessage;

import static io.github.rangaofei.sakatimeline.divider.TimeLineType.StepViewType.BOTTOM_STEP_PROGRESS;
import static io.github.rangaofei.sakatimeline.divider.TimeLineType.StepViewType.LEFT_STEP_PROGRESS;
import static io.github.rangaofei.sakatimeline.divider.TimeLineType.StepViewType.RIGHT_STEP_PROGRESS;
import static io.github.rangaofei.sakatimeline.divider.TimeLineType.StepViewType.TOP_STEP_PROGRESS;

public class SingleStepViewDivider extends BaseDivider {
    private Paint textPaint;
    private Paint circlePaint;
    private Paint overlayPaint;
    private Paint.FontMetrics fm;
    private RecyclerView recyclerView;
    private float currentNum;
    private PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
    private PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(mode);

    private DividerLayoutAdapter dividerLayoutAdapter;

    private Rect drawRect = new Rect();

    public SingleStepViewDivider(Context context, TimeLineConfig timeLineConfig) {
        super(context, timeLineConfig);
        overlayPaint = new Paint();
        overlayPaint.setColor(timeLineConfig.getStepViewConfig().getPreColor());
        overlayPaint.setAntiAlias(true);
        overlayPaint.setXfermode(porterDuffXfermode);


        textPaint = new Paint();
        textPaint.setTextSize(timeLineConfig.getIndexTextConfig().getTextSize());
        textPaint.setColor(timeLineConfig.getIndexTextConfig().getTextColor());
        textPaint.setAntiAlias(true);
        fm = textPaint.getFontMetrics();

        circlePaint = new Paint();
        circlePaint.setStrokeWidth(timeLineConfig.getTimeStrokeWidth());
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(timeLineConfig.getStepViewConfig().getAfterColor());

        currentNum = timeLineConfig.getStepViewConfig().getDividerNum();
        dividerLayoutAdapter = timeLineConfig.getStepViewConfig().getDividerLayoutAdapter();

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
                    } else if (timeLineConfig.getType() == BOTTOM_STEP_PROGRESS) {
                        outRect.bottom = realHPadding;
                    }
                    break;
                case PerfectLinearLayoutManager.VERTICAL:
                    int realVPadding = (int) padding;
                    if (timeLineConfig.getType() == LEFT_STEP_PROGRESS) {
                        outRect.left = realVPadding;
                    } else if (timeLineConfig.getType() == RIGHT_STEP_PROGRESS) {
                        outRect.right = realVPadding;
                    }
                    break;
                default:
                    throwException(ExceptionMessage.UNKNOWN_ORIENTATION);
            }
        }
    }

    private void drawHorizontalStep(Canvas c, RecyclerView parent) {
        c.save();
        for (int i = 0; i < parent.getChildCount(); i++) {
            int layoutId = c.saveLayer(recyclerView.getPaddingLeft(),
                    recyclerView.getPaddingTop(),
                    recyclerView.getWidth() - recyclerView.getPaddingLeft() - recyclerView.getPaddingRight(),
                    recyclerView.getHeight() - recyclerView.getPaddingTop() - recyclerView.getPaddingBottom(),
                    null);

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
            switch (timeLineConfig.getStrokeType()) {
                case NORMAL:
                    c.drawLine(view.getLeft() - globalRect.left, circleY,
                            view.getRight() + globalRect.right, circleY, circlePaint);
                    break;
                case NO_ENDPOINT:
                    if (i == 0) {
                        c.drawLine(circleX, circleY,
                                view.getRight() + globalRect.right, circleY, circlePaint);
                    } else if (i == parent.getChildCount() - 1) {
                        c.drawLine(view.getLeft() - globalRect.left, circleY,
                                circleX, circleY, circlePaint);
                    } else {
                        c.drawLine(view.getLeft() - globalRect.left, circleY,
                                view.getRight() + globalRect.right, circleY, circlePaint);
                    }

                    break;
                default:
                    break;
            }

            drawDefaultCircle(c, circleX, circleY, i);
            switch (timeLineConfig.getStrokeType()) {
                case NORMAL:
                    if (i < currentNum && i > currentNum - 1) {
                        c.drawRect(view.getLeft() - globalRect.left, rectTop,
                                (view.getRight() + globalRect.right) * (currentNum - i),
                                rectBottom, overlayPaint);
                    } else if (i <= currentNum - 1) {
                        c.drawRect(view.getLeft() - globalRect.left, rectTop,
                                (view.getRight() + globalRect.right),
                                rectBottom, overlayPaint);
                    }
                    break;
                case NO_ENDPOINT:
                    if (i < currentNum - 1.5f) {//全画
                        c.drawRect(view.getLeft() - globalRect.left, rectTop,
                                (view.getRight() + globalRect.right),
                                rectBottom, overlayPaint);
                    } else if (i < currentNum - 1f && i >= currentNum - 1.5f) {//左全画，右部分画
                        c.drawRect(view.getLeft() - globalRect.left, rectTop,
                                view.getLeft() + view.getWidth() / 2 + view.getWidth() * (currentNum - i - 1f),
//                                (view.getLeft() + view.getWidth() / 2) * (1 + currentNum - i),
                                rectBottom, overlayPaint);
                    } else if (i >= currentNum - 1f && i <= currentNum - 0.5f) {//左部分画，右不画
                        c.drawRect(view.getLeft() - globalRect.left, rectTop,
                                view.getLeft() + view.getWidth() * (currentNum - i -0.5f),
                                rectBottom, overlayPaint);
                    }
                    break;
            }

            drawDrawable(c, circleX, circleY, i);
            drawText(c, circleX, circleY, i);
            c.restoreToCount(layoutId);
        }
        c.restore();
    }

    private void drawVerticalStep(Canvas c, RecyclerView parent) {
        c.save();
        for (int i = 0; i < parent.getChildCount(); i++) {
            int layoutId = c.saveLayer(recyclerView.getPaddingLeft(),
                    recyclerView.getPaddingTop(),
                    recyclerView.getWidth() - recyclerView.getPaddingLeft() - recyclerView.getPaddingRight(),
                    recyclerView.getHeight() - recyclerView.getPaddingTop() - recyclerView.getPaddingBottom(),
                    null);
            View view = parent.getChildAt(i);
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
            switch (timeLineConfig.getStrokeType()) {
                case NORMAL:
                    c.drawLine(circleX, view.getTop() - globalRect.top,
                            circleX, view.getBottom() + globalRect.bottom, circlePaint);
                    break;
                case NO_ENDPOINT:
                    if (i == 0) {
                        c.drawLine(circleX, circleY,
                                circleX, view.getBottom() + globalRect.bottom, circlePaint);
                    } else if (i == parent.getChildCount() - 1) {
                        c.drawLine(circleX, view.getTop() - globalRect.top,
                                circleX, circleY, circlePaint);
                    } else {
                        c.drawLine(circleX, view.getTop() - globalRect.top,
                                circleX, view.getBottom() + globalRect.bottom, circlePaint);
                    }
                    c.drawLine(circleX, view.getTop() - globalRect.top,
                            circleX, view.getBottom() + globalRect.bottom, circlePaint);
                    break;
                default:
                    break;
            }

            drawDefaultCircle(c, circleX, circleY, i);

            switch (timeLineConfig.getStrokeType()) {
                case NORMAL:
                    if (i < currentNum && i > currentNum - 1) {
                        c.drawRect(rectLeft, view.getTop() - globalRect.top,
                                rectRight,
                                (view.getBottom() + globalRect.bottom) * (currentNum - i), overlayPaint);
                    } else if (i <= currentNum - 1) {
                        c.drawRect(rectLeft, view.getTop() - globalRect.top,
                                rectRight,
                                view.getBottom() + globalRect.bottom, overlayPaint);
                    }
                    break;
                case NO_ENDPOINT:
                    if (i <= currentNum) {
                        c.drawRect(rectLeft, view.getTop() - globalRect.top,
                                rectRight,
                                view.getBottom() + globalRect.bottom, overlayPaint);
                    } else if (i == currentNum + 1 && i < parent.getChildCount() - 1) {
                        c.drawRect(rectLeft, view.getTop() - globalRect.top,
                                rectRight,
                                (view.getBottom() + globalRect.bottom) * (currentNum + 0.5f - i), overlayPaint);
                    }
                    break;
            }

            drawDrawable(c, circleX, circleY, i);
            drawText(c, circleX, circleY, i);
            c.restoreToCount(layoutId);
        }
        c.restore();
    }


    private void drawLine() {
        switch (timeLineConfig.getStrokeType()) {
            case NORMAL:
                break;
            case NO_ENDPOINT:
                break;
            default:
                throw new RuntimeException("unknown stroke_type");
        }
    }

    private void drawNormalLine() {

    }

    private void drawNoEndPointLine() {

    }

    /**
     * 绘制默认的圆
     *
     * @param c       画布
     * @param circleX 圆心x
     * @param circleY 圆心y
     */
    private void drawDefaultCircle(Canvas c, int circleX, int circleY, int index) {
        if (dividerLayoutAdapter == null || dividerLayoutAdapter.getDrawable(index) == null) {
            c.drawCircle(circleX, circleY, padding / 3, circlePaint);
        }
    }

    /**
     * 绘制用户设置的drawable
     */
    private void drawDrawable(Canvas c, int circleX, int circleY, int i) {
        if (dividerLayoutAdapter != null && dividerLayoutAdapter.getDrawable(i) != null) {
            Drawable drawable = dividerLayoutAdapter.getDrawable(i);
            drawRect.set(circleX - (int) padding / 3, circleY - (int) padding / 3,
                    circleX + (int) padding / 3, circleY + (int) padding / 3);
            drawable.setBounds(drawRect);
            drawable.draw(c);
        }
    }

    private void drawText(Canvas c, int circleX, int circleY, int i) {
        if (timeLineConfig.getStepViewConfig().isShowStepText()) {
            String text = String.valueOf(i + 1);
            float w = textPaint.measureText(text);
            c.drawText(text, circleX - w / 2, circleY + (fm.bottom - fm.top) / 2 - fm.bottom, textPaint);
        }
    }

    /**
     * 改变当前进度位置
     *
     * @param num      进度值
     * @param showAnim 是否显示动画
     */
    public void updateDividerNum(int num, boolean showAnim) {
        if (recyclerView == null) {
            return;
        }
        if (num > recyclerView.getChildCount() + 1) {
            num = recyclerView.getChildCount() + 1;
        }
        if (num < 0) {
            num = 0;
        }
        if (showAnim) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(currentNum, num);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.setDuration(1000);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (recyclerView != null) {
                        currentNum = (float) animation.getAnimatedValue();
                        timeLineConfig.getStepViewConfig().setDividerNum((int) currentNum);
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
