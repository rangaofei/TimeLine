package io.github.rangaofei.sakatimeline.divider;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.animation.LinearInterpolator;

import io.github.rangaofei.sakatimeline.config.StrokeType;
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
    private Paint iconOverlayPaint;
    private Paint itemOverlayPaint;
    private Paint.FontMetrics fm;
    private RecyclerView recyclerView;
    private float currentNum;
    private PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
    private PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(mode);
    private DividerLayoutAdapter dividerLayoutAdapter;

    private Rect drawRect = new Rect();
    private Rect layerRect = new Rect();

    public SingleStepViewDivider(Context context, TimeLineConfig timeLineConfig) {
        super(context, timeLineConfig);
        iconOverlayPaint = new Paint();
        iconOverlayPaint.setColor(timeLineConfig.getStepViewConfig().getPreColor());
        iconOverlayPaint.setAntiAlias(true);
        iconOverlayPaint.setXfermode(porterDuffXfermode);

        itemOverlayPaint = new Paint();
        itemOverlayPaint.setColor(timeLineConfig.getStepViewConfig().getAfterColor());
        itemOverlayPaint.setAntiAlias(true);
        itemOverlayPaint.setXfermode(porterDuffXfermode);


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
        if (parent.getClipToPadding()) {
            c.clipRect(parent.getPaddingLeft(), parent.getPaddingTop(),
                    parent.getWidth() - parent.getPaddingRight(),
                    parent.getHeight() - parent.getPaddingBottom());
        }

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof PerfectLinearLayoutManager) {

            switch (((PerfectLinearLayoutManager) layoutManager).getOrientation()) {
                case LinearLayoutManager.HORIZONTAL:
                    drawHorizontalStep(c, parent);
                    break;
                case LinearLayoutManager.VERTICAL:
                    Log.d("---", "onDraw");
                    drawVerticalStep(c, parent);
                    break;
                default:
                    throwException(ExceptionMessage.UNKNOWN_ORIENTATION);
            }
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof PerfectLinearLayoutManager) {
            switch (((PerfectLinearLayoutManager) layoutManager).getOrientation()) {
                case PerfectLinearLayoutManager.HORIZONTAL:
                    break;
                case PerfectLinearLayoutManager.VERTICAL:
                    break;
                default:
                    throwException(ExceptionMessage.UNKNOWN_ORIENTATION);
            }
        }
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

    private void drawHorizontalLine(Canvas c, RecyclerView parent, View view) {
        int i = parent.getChildAdapterPosition(view);
        int widthWithPadding = view.getWidth() + globalRect.right + globalRect.left;
        int lineStartX = 0, lineStopX = 0, lineStartY = 0, lineStopY = 0, rectStopX = 0;
        if (timeLineConfig.getStrokeType() == StrokeType.NORMAL) {
            lineStartX = view.getLeft() - globalRect.left;
            lineStopX = view.getRight() + globalRect.right;
            if (i <= currentNum - 1) {
                rectStopX = view.getRight() + globalRect.right;
            } else if (i > currentNum - 1 && i <= currentNum) {
                rectStopX = (int) (view.getLeft() - globalRect.left +
                        widthWithPadding * (currentNum - (int) currentNum));
            }
        } else if (timeLineConfig.getStrokeType() == StrokeType.NO_ENDPOINT) {
            if (i == 0) {
                lineStartX = view.getLeft() + view.getWidth() / 2;
                lineStopX = view.getRight() + globalRect.right;
            } else if (i == parent.getAdapter().getItemCount() - 1) {
                lineStartX = view.getLeft() - globalRect.left;
                lineStopX = view.getLeft() + view.getWidth() / 2;
            } else {
                lineStartX = view.getLeft() - globalRect.left;
                lineStopX = view.getRight() + globalRect.right;
            }

            if (i <= currentNum - 1.5f) {
                rectStopX = view.getRight() + globalRect.right;
            } else if (i > currentNum - 1.5f && i <= currentNum - 1f) {
                rectStopX = (int) (view.getLeft() + view.getWidth() / 2 +
                        (view.getWidth() / 2 + globalRect.right) * 2 * (currentNum - (int) currentNum));
            } else if (i > currentNum - 1f && i <= currentNum - 0.5) {
                rectStopX = (int) (view.getLeft() - globalRect.left +
                        (view.getWidth() / 2 + globalRect.left) * 2 * (currentNum - (int) currentNum));
            }
        }
        switch ((TimeLineType.StepViewType) timeLineConfig.getType()) {
            case TOP_STEP_PROGRESS:
                lineStartY = lineStopY = (int) (padding / 2);
                break;
            case BOTTOM_STEP_PROGRESS:
                lineStartY = lineStopY = view.getBottom() + (int) (padding / 2);
                break;
        }
        c.drawLine(lineStartX, lineStartY, lineStopX, lineStopY, circlePaint);

        c.drawRect(lineStartX, lineStartY - (int) (padding / 2), rectStopX,
                lineStopY + (int) (padding / 2), iconOverlayPaint);
    }

    //DividerItemDecoration
    private void drawHorizontalStep(Canvas c, RecyclerView parent) {
        c.save();
        for (int i = 0; i < parent.getChildCount(); i++) {
            layerRect.set(recyclerView.getPaddingLeft(),
                    recyclerView.getPaddingTop(),
                    recyclerView.getWidth() - recyclerView.getPaddingRight(),
                    recyclerView.getHeight() - recyclerView.getPaddingBottom());
            int layoutId = c.saveLayer(recyclerView.getPaddingLeft(),
                    recyclerView.getPaddingTop(),
                    recyclerView.getWidth() - recyclerView.getPaddingRight(),
                    recyclerView.getHeight() - recyclerView.getPaddingBottom(),
                    null, Canvas.ALL_SAVE_FLAG);

            View view = parent.getChildAt(i);
            drawHorizontalLine(c, parent, view);
            int circleX = view.getLeft() + view.getWidth() / 2;
            int circleY = 0;
            if (timeLineConfig.getType() == TOP_STEP_PROGRESS) {
                circleY = view.getTop() - (int) padding / 2;
            } else if (timeLineConfig.getType() == BOTTOM_STEP_PROGRESS) {
                circleY = view.getBottom() + (int) padding / 2;
            }
            drawDefaultCircle(c, circleX, circleY, i);
            drawDrawable(c, circleX, circleY, i);
            drawText(c, circleX, circleY, i);
            c.restoreToCount(layoutId);
        }
        c.restore();
    }


    private void drawVerticalLine(Canvas c, RecyclerView parent, View view) {
        int i = parent.getChildAdapterPosition(view);
        int heightWithPadding = view.getHeight() + globalRect.top + globalRect.bottom;
        int lineStartX = 0, lineStopX = 0, lineStartY = 0, lineStopY = 0, rectStopY = 0;
        if (timeLineConfig.getStrokeType() == StrokeType.NORMAL) {
            lineStartY = view.getTop() - globalRect.top;
            lineStopY = view.getBottom() + globalRect.bottom;
            if (i <= currentNum - 1) {
                rectStopY = view.getBottom() + globalRect.bottom;
            } else if (i > currentNum - 1 && i <= currentNum) {
                rectStopY = (int) (view.getTop() - globalRect.top +
                        heightWithPadding * (currentNum - (int) currentNum));
            }
        } else if (timeLineConfig.getStrokeType() == StrokeType.NO_ENDPOINT) {
            if (i == 0) {
                lineStartY = view.getTop() + view.getHeight() / 2;
                lineStopY = view.getBottom() + globalRect.bottom;
            } else if (i == parent.getAdapter().getItemCount() - 1) {
                lineStartY = view.getTop() - globalRect.top;
                lineStopY = view.getTop() + view.getHeight() / 2;
            } else {
                lineStartY = view.getTop() - globalRect.top;
                lineStopY = view.getBottom() + globalRect.bottom;
            }

            if (i <= currentNum - 1.5f) {
                rectStopY = view.getBottom() + globalRect.bottom;
            } else if (i > currentNum - 1.5f && i <= currentNum - 1f) {
                rectStopY = (int) (view.getTop() + view.getHeight() / 2 +
                        (view.getHeight() / 2 + globalRect.bottom) * 2 * (currentNum - (int) currentNum));
            } else if (i > currentNum - 1f && i <= currentNum - 0.5) {
                rectStopY = (int) (view.getTop() - globalRect.top +
                        (view.getHeight() / 2 + globalRect.top) * 2 * (currentNum - (int) currentNum));
            }
        }
        switch ((TimeLineType.StepViewType) timeLineConfig.getType()) {
            case LEFT_STEP_PROGRESS:
                lineStartX = lineStopX = (int) (padding / 2);
                break;
            case RIGHT_STEP_PROGRESS:
                lineStartX = lineStopX = view.getRight() + (int) (padding / 2);
                break;
        }
        c.drawLine(lineStartX, lineStartY, lineStopX, lineStopY, circlePaint);

        c.drawRect(lineStartX - padding / 2, lineStartY, lineStartX + padding / 2,
                rectStopY, iconOverlayPaint);
    }

    private void drawVerticalStep(Canvas c, RecyclerView parent) {
        c.save();
        for (int i = 0; i < parent.getChildCount(); i++) {
            int layoutId = c.saveLayer(recyclerView.getPaddingLeft(),
                    recyclerView.getPaddingTop(),
                    recyclerView.getWidth() - recyclerView.getPaddingRight(),
                    recyclerView.getHeight() - recyclerView.getPaddingBottom(),
                    null, Canvas.ALL_SAVE_FLAG);
            View view = parent.getChildAt(i);
            drawVerticalLine(c, parent, view);
            int circleX = 0;
            int circleY = view.getTop() + view.getHeight() / 2;
            if (timeLineConfig.getType() == LEFT_STEP_PROGRESS) {
                circleX = view.getLeft() - (int) padding / 2;
            } else if (timeLineConfig.getType() == RIGHT_STEP_PROGRESS) {
                circleX = view.getRight() + (int) padding / 2;
            }

            drawDefaultCircle(c, circleX, circleY, i);
            drawDrawable(c, circleX, circleY, i);
            drawText(c, circleX, circleY, i);
            c.restoreToCount(layoutId);
        }
        c.restore();
    }

//DividerItemDecoration

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
                        recyclerView.postInvalidate();
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
