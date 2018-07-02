package io.github.rangaofei.sakatimeline;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.github.rangaofei.sakatimeline.adapter.AbstractTimeLineAdapter;
import io.github.rangaofei.sakatimeline.customlayoutmanager.PerfectLinearLayoutManager;
import io.github.rangaofei.sakatimeline.customlayoutmanager.TimeLineGridLayoutManager;
import io.github.rangaofei.sakatimeline.divider.BaseDivider;
import io.github.rangaofei.sakatimeline.divider.LeftOnlyDivider;
import io.github.rangaofei.sakatimeline.divider.LeftRightDivider;
import io.github.rangaofei.sakatimeline.divider.RightOnlyDivider;
import io.github.rangaofei.sakatimeline.divider.SingleStepViewDivider;
import io.github.rangaofei.sakatimeline.divider.TimeLineType;

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

    private void getCustomAttr(AttributeSet attributeSet) {
        this.timeLineConfig = new TimeLineConfig();
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
        final boolean showStepOrder =
                ta.getBoolean(R.styleable.TimeLineView_stepShowOrder, false);
        final int stepPreColor =
                ta.getColor(R.styleable.TimeLineView_stepPreColor, getResources().getColor(R.color.teal));
        final int stepAfterColor =
                ta.getColor(R.styleable.TimeLineView_stepAfterColor, getResources().getColor(R.color.grey));
        final int stepIndexColor =
                ta.getColor(R.styleable.TimeLineView_stepIndexColor, getResources().getColor(R.color.white));
        timeLineConfig.setPadding(padding);
        timeLineConfig.setTimeDrawable(drawable);
        timeLineConfig.setTimeColor(strokeColor);
        timeLineConfig.setTimeStrokeWidth(strokeWidth);

        TimeLineConfig.StepViewConfig stepViewConfig = new TimeLineConfig.StepViewConfig();
        stepViewConfig.setShowStepText(showStepOrder);
        stepViewConfig.setIndexColor(stepIndexColor);
        stepViewConfig.setPreColor(stepPreColor);
        stepViewConfig.setAfterColor(stepAfterColor);
        timeLineConfig.setStepViewConfig(stepViewConfig);

        ta.recycle();
    }


    public void setTimeLineConfig(AbstractTimeLineAdapter adapter, TimeLineType type) {
        if (this.timeLineConfig == null) {
            throw new RuntimeException("nu");
        }
        this.timeLineConfig.setAdapter(adapter);
        this.timeLineConfig.setType(type);
        initData();
    }

    public void setTimeLineConfig(AbstractTimeLineAdapter adapter, TimeLineType type, int dividerNum) {
        if (this.timeLineConfig == null) {
            throw new RuntimeException("nu");
        }
        this.timeLineConfig.setAdapter(adapter);
        this.timeLineConfig.setType(type);
        this.timeLineConfig.getStepViewConfig().setDividerNum(dividerNum);
        initData();
    }

    public void setTimeLineConfig(AbstractTimeLineAdapter adapter, TimeLineType type, int dividerNum, List<Drawable> list) {
        if (this.timeLineConfig == null) {
            throw new RuntimeException("nu");
        }
        this.timeLineConfig.setAdapter(adapter);
        this.timeLineConfig.setType(type);
        this.timeLineConfig.getStepViewConfig().setDividerNum(dividerNum);
        this.timeLineConfig.getStepViewConfig().setDividerLayoutAdapter(list);
        initData();
    }

    public void updateDividerNum(int dividerNum, boolean showAnim) {
        if (this.timeLineConfig == null || this.timeLineConfig.getStepViewConfig() == null) {
            throw new RuntimeException("nu");
        }

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
