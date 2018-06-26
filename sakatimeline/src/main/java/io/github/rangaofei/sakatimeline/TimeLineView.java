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

import io.github.rangaofei.sakatimeline.adapter.AbstractTimeLineAdapter;
import io.github.rangaofei.sakatimeline.customlayoutmanager.RightLinearLayoutManager;
import io.github.rangaofei.sakatimeline.customlayoutmanager.TimeLineGridLayoutManager;
import io.github.rangaofei.sakatimeline.customlayoutmanager.TimeLineLayoutManager;
import io.github.rangaofei.sakatimeline.divider.BaseDivider;
import io.github.rangaofei.sakatimeline.divider.LeftOnlyDivider;
import io.github.rangaofei.sakatimeline.divider.LeftRightDivider;
import io.github.rangaofei.sakatimeline.divider.RightOnlyDivider;
import io.github.rangaofei.sakatimeline.divider.SingleStepProgressDivider;

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
        switch (timeLineConfig.getTimeLineType()) {
            case ONLY_LEFT:
                layoutManager = new LinearLayoutManager(getContext());
                divider = new LeftOnlyDivider(getContext(), timeLineConfig);
                break;
            case ONLY_RIGHT:
                layoutManager = new RightLinearLayoutManager(getContext());
                divider = new RightOnlyDivider(getContext(), timeLineConfig);
                break;
            case LEFT_TO_RIGHT:
                break;
            case RIGHT_TO_LEFT:
                break;
            case LEFT_KEY:
                layoutManager = new TimeLineGridLayoutManager(getContext(), 2);
                divider = new LeftRightDivider(getContext(), timeLineConfig);
                break;
            case LEFT_VALUE:
                break;
            case TOP_STEP_PROGRESS:
                layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                divider = new SingleStepProgressDivider(getContext(), timeLineConfig);
                break;
            case LEFT_STEP_PROGRESS:
                layoutManager = new LinearLayoutManager(getContext());
                divider = new SingleStepProgressDivider(getContext(), timeLineConfig);
                break;
            default:
                break;
        }
        this.setLayoutManager(layoutManager);
        this.addItemDecoration(divider);
        timeLineConfig.getAdapter().setTimeLineType(timeLineConfig.getTimeLineType());
        this.setAdapter(timeLineConfig.getAdapter());
    }

    private void getCustomAttr(AttributeSet attributeSet) {
        this.timeLineConfig = new TimeLineConfig();
        final TypedArray ta = getContext().obtainStyledAttributes(attributeSet, R.styleable.TimeLineView);
        float padding = ta.getDimension(R.styleable.TimeLineView_timePadding, 30);
        Drawable drawable = ta.getDrawable(R.styleable.TimeLineView_timeDrawable);
        int color = ta.getColor(R.styleable.TimeLineView_timeStrokeColor, Color.parseColor("#9e9e9e"));
        float strokeWidth = ta.getDimension(R.styleable.TimeLineView_timeStrokeWidth, 10);
        timeLineConfig.setPadding(padding);
        timeLineConfig.setTimeDrawable(drawable);
        timeLineConfig.setTimeColor(color);
        timeLineConfig.setTimeStrokeWidth((int) strokeWidth);
        ta.recycle();
    }


    public void setTimeLineConfig(AbstractTimeLineAdapter adapter, TimeLineType type) {
        if (this.timeLineConfig == null) {
            throw new RuntimeException("nu");
        }
        this.timeLineConfig.setAdapter(adapter);
        this.timeLineConfig.setTimeLineType(type);
        initData();
    }
}
