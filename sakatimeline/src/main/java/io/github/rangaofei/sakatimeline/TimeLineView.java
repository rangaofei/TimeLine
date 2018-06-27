package io.github.rangaofei.sakatimeline;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

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
        if (TimeLineViewType.ONLY_LEFT.equals(timeLineConfig.getType())) {
            layoutManager = new LinearLayoutManager(getContext());
            divider = new LeftOnlyDivider(getContext(), timeLineConfig);

        } else if (TimeLineViewType.ONLY_RIGHT.equals(timeLineConfig.getType())) {
            layoutManager = new PerfectLinearLayoutManager(getContext());
            divider = new RightOnlyDivider(getContext(), timeLineConfig);

        } else if (TimeLineViewType.LEFT_TO_RIGHT.equals(timeLineConfig.getType())) {
        } else if (TimeLineViewType.RIGHT_TO_LEFT.equals(timeLineConfig.getType())) {
        } else if (TimeLineViewType.LEFT_KEY.equals(timeLineConfig.getType())) {
            layoutManager = new TimeLineGridLayoutManager(getContext(), 2);
            divider = new LeftRightDivider(getContext(), timeLineConfig);

        } else if (TimeLineViewType.LEFT_VALUE.equals(timeLineConfig.getType())) {
        } else {
        }
        this.setLayoutManager(layoutManager);
        this.addItemDecoration(divider);
        timeLineConfig.getAdapter().setTimeLineType(timeLineConfig.getType());
        this.setAdapter(timeLineConfig.getAdapter());
    }

    private void getCustomAttr(AttributeSet attributeSet) {
        this.timeLineConfig = new TimeLineConfig();
        final TypedArray ta = getContext().obtainStyledAttributes(attributeSet, R.styleable.TimeLineView);
        float padding = ta.getDimension(R.styleable.TimeLineView_timePadding, 30);
        Drawable drawable = ta.getDrawable(R.styleable.TimeLineView_timeDrawable);
        int color = ta.getColor(R.styleable.TimeLineView_timeStrokeColor, Color.parseColor("#9e9e9e"));
        float strokeWidth = ta.getDimension(R.styleable.TimeLineView_timeStrokeWidth, 10);
        boolean showStepOrder = ta.getBoolean(R.styleable.TimeLineView_stepShowOrder, false);
        int stepPreColor = ta.getColor(R.styleable.TimeLineView_stepPreColor, Color.parseColor("#259b24"));
        int stepAfterColor = ta.getColor(R.styleable.TimeLineView_stepAfterColor, Color.parseColor("#9e9e9e"));
        timeLineConfig.setPadding(padding);
        timeLineConfig.setTimeDrawable(drawable);
        timeLineConfig.setTimeColor(color);
        timeLineConfig.setTimeStrokeWidth((int) strokeWidth);
        TimeLineConfig.StepViewConfig stepViewConfig = new TimeLineConfig.StepViewConfig();
        stepViewConfig.setShowStepText(showStepOrder);
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
