package io.github.rangaofei.sakatimeline;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.List;

import io.github.rangaofei.sakatimeline.adapter.AbstractTimeLineAdapter;
import io.github.rangaofei.sakatimeline.config.IndexTextConfig;
import io.github.rangaofei.sakatimeline.config.StepViewConfig;
import io.github.rangaofei.sakatimeline.config.StrokeType;
import io.github.rangaofei.sakatimeline.config.TimeLineConfig;
import io.github.rangaofei.sakatimeline.customlayoutmanager.PerfectLinearLayoutManager;
import io.github.rangaofei.sakatimeline.divider.BaseDivider;
import io.github.rangaofei.sakatimeline.divider.SingleStepViewDivider;
import io.github.rangaofei.sakatimeline.divider.TimeLineType;
import io.github.rangaofei.sakatimeline.util.ExceptionUtil;

import static io.github.rangaofei.sakatimeline.divider.TimeLineType.StepViewType.*;

public class TimeLineView extends RecyclerView {
    private LayoutManager layoutManager;
    private BaseDivider divider;
    private TimeLineConfig timeLineConfig;

    public TimeLineView(Context context) {
        this(context, null);
    }

    public TimeLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getCustomAttr(attrs);
    }


    private void initData() {
        if (timeLineConfig.getType() instanceof StepViewType) {
            switch ((StepViewType) timeLineConfig.getType()) {
                case TOP_STEP_PROGRESS:
                    layoutManager = new PerfectLinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    ((PerfectLinearLayoutManager) layoutManager).setLayoutTTB(true);
                    break;
                case BOTTOM_STEP_PROGRESS:
                    layoutManager = new PerfectLinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    ((PerfectLinearLayoutManager) layoutManager).setLayoutTTB(false);
                    break;
                case LEFT_STEP_PROGRESS:
                    layoutManager = new PerfectLinearLayoutManager(getContext());
                    ((PerfectLinearLayoutManager) layoutManager).setRTL(false);
                    break;
                case RIGHT_STEP_PROGRESS:
                    layoutManager = new PerfectLinearLayoutManager(getContext());
                    ((PerfectLinearLayoutManager) layoutManager).setRTL(true);
                    break;
            }
            divider = new SingleStepViewDivider(getContext(), timeLineConfig);

        }

        this.setLayoutManager(layoutManager);
        this.addItemDecoration(divider);
        timeLineConfig.getAdapter().setTimeLineType(timeLineConfig.getType());
        this.setAdapter(timeLineConfig.getAdapter());
    }

    private void getTimeLineViewAttr(AttributeSet as) {
        final TypedArray ta = getContext().obtainStyledAttributes(as, R.styleable.TimeLineView);

        ta.recycle();
    }

    private IndexTextConfig getIndexTextAttr(AttributeSet as) {
        final IndexTextConfig indexTextConfig = new IndexTextConfig();
        final TypedArray ta = getContext().obtainStyledAttributes(as, R.styleable.IndexText);
        if (ta == null) {
            return indexTextConfig;
        }
        final int textSize = ta.getDimensionPixelSize(R.styleable.IndexText_timeIndexSize,
                getResources().getDimensionPixelSize(R.dimen.default_text_size));
        final int textColor = ta.getColor(R.styleable.IndexText_timeIndexColor, getResources().getColor(R.color.pink));
        ta.recycle();
        indexTextConfig.setTextColor(textColor);
        indexTextConfig.setTextSize(textSize);
        return indexTextConfig;
    }

    private StepViewConfig getStepViewAttr(AttributeSet as) {
        StepViewConfig stepViewConfig = new StepViewConfig();
        final TypedArray ta = getContext().obtainStyledAttributes(as, R.styleable.StepViewDivider);
        if (ta == null) {
            return stepViewConfig;
        }
        final boolean showIndex = ta.getBoolean(R.styleable.StepViewDivider_stepShowOrder, false);
        final int preColor = ta.getColor(R.styleable.StepViewDivider_stepPreColor, getResources().getColor(R.color.teal));
        final int afterColor = ta.getColor(R.styleable.StepViewDivider_stepAfterColor, getResources().getColor(R.color.grey));
        ta.recycle();
        stepViewConfig.setShowStepText(showIndex);
        stepViewConfig.setPreColor(preColor);
        stepViewConfig.setAfterColor(afterColor);
        return stepViewConfig;
    }

    private void getCustomAttr(AttributeSet attributeSet) {
        this.timeLineConfig = new TimeLineConfig();
        this.timeLineConfig.setStepViewConfig(getStepViewAttr(attributeSet));
        this.timeLineConfig.setIndexTextConfig(getIndexTextAttr(attributeSet));


        final TypedArray ta =
                getContext().obtainStyledAttributes(attributeSet, R.styleable.TimeLineView);
        final int padding =
                ta.getDimensionPixelSize(R.styleable.TimeLineView_timePadding,
                        getResources().getDimensionPixelOffset(R.dimen.default_padding));
        final Drawable drawable = ta.getDrawable(R.styleable.TimeLineView_timeDrawable);
        final int strokeColor =
                ta.getColor(R.styleable.TimeLineView_timeStrokeColor, getResources().getColor(R.color.grey));
        final int strokeWidth =
                ta.getDimensionPixelSize(R.styleable.TimeLineView_timeStrokeWidth,
                        getResources().getDimensionPixelOffset(R.dimen.default_stroke_width));
        final int strokeType = ta.getInt(R.styleable.TimeLineView_strokeType, 0);
        switch (strokeType) {
            case 0:
                timeLineConfig.setStrokeType(StrokeType.NORMAL);
                break;
            case 1:
                timeLineConfig.setStrokeType(StrokeType.NO_ENDPOINT);
                break;
            case 2:
                break;
        }
        final int timeLineType = ta.getInt(R.styleable.TimeLineView_timeLineType, 0);
        switch (timeLineType) {
            case 0:
                timeLineConfig.setType(LEFT_STEP_PROGRESS);
                break;
            case 1:
                timeLineConfig.setType(RIGHT_STEP_PROGRESS);
                break;
            case 2:
                timeLineConfig.setType(TOP_STEP_PROGRESS);
                break;
            case 3:
                timeLineConfig.setType(BOTTOM_STEP_PROGRESS);
                break;

        }
        timeLineConfig.setPadding(padding);
        timeLineConfig.setTimeDrawable(drawable);
        timeLineConfig.setTimeColor(strokeColor);
        timeLineConfig.setTimeStrokeWidth(strokeWidth);

        ta.recycle();
        this.setOverScrollMode(OVER_SCROLL_NEVER);
    }


    public void setTimeLineConfig(AbstractTimeLineAdapter adapter, TimeLineType type) {
        this.setTimeLineConfig(adapter, type, 0);
    }

    public void setTimeLineConfig(AbstractTimeLineAdapter adapter, TimeLineType type, int dividerNum) {
        this.setTimeLineConfig(adapter, type, dividerNum, null);
    }

    public void setTimeLineConfig(AbstractTimeLineAdapter adapter,
                                  TimeLineType type,
                                  int dividerNum,
                                  List<Drawable> list) {
        ExceptionUtil.checkIfNull(this.timeLineConfig, "TimeLineConfig");
        ExceptionUtil.checkIfNull(adapter, "AbstractTimeLineAdapter");
        this.timeLineConfig.setAdapter(adapter);
        this.timeLineConfig.setType(type);
        this.timeLineConfig.getStepViewConfig().setDividerNum(dividerNum);
        this.timeLineConfig.getStepViewConfig().setDividerLayoutAdapter(list);
        initData();
    }

    public void updateDividerNum(int dividerNum, boolean showAnim) {
        ExceptionUtil.checkIfNull(this.timeLineConfig, "TimeLineConfig");
        ExceptionUtil.checkIfNull(this.timeLineConfig.getStepViewConfig(), "StepViewConfig");

        if (this.timeLineConfig.getStepViewConfig().getDividerNum() == dividerNum) {
            return;
        }
        if (this.divider instanceof SingleStepViewDivider) {
            ((SingleStepViewDivider) divider).updateDividerNum(dividerNum, showAnim);
        }

    }

    public void updateDividerNum(int dividerNum) {
        updateDividerNum(dividerNum, true);
    }
}
