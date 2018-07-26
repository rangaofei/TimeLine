package io.github.rangaofei.sakatimeline.config;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import java.util.List;

import io.github.rangaofei.sakatimeline.R;
import io.github.rangaofei.sakatimeline.divider.DividerLayoutAdapter;

/**
 * 配置StepViewDivider
 */
public class StepViewConfig {
    //是否显示index，序号，从1开始
    private boolean showStepText;
    //当前分割点
    private float dividerNum;
    //分割点前边线的颜色
    private int preColor;
    //分割点后边线的颜色
    private int afterColor;
    //默认的index图标是一个圆圈，圆圈的半径
    private int circleRadius;
    //用于覆盖默认的index点
    private DividerLayoutAdapter dividerLayoutAdapter;

    public boolean isShowStepText() {
        return showStepText;
    }

    public void setShowStepText(boolean showStepText) {
        this.showStepText = showStepText;
    }


    public float getDividerNum() {
        return dividerNum;
    }

    public void setDividerNum(float dividerNum) {
        this.dividerNum = dividerNum;
    }


    public int getPreColor() {
        return preColor;
    }

    public void setPreColor(int preColor) {
        this.preColor = preColor;
    }

    public int getAfterColor() {
        return afterColor;
    }

    public void setAfterColor(int afterColor) {
        this.afterColor = afterColor;
    }

    public int getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
    }

    public DividerLayoutAdapter getDividerLayoutAdapter() {
        return dividerLayoutAdapter;
    }

    public void setDividerLayoutAdapter(DividerLayoutAdapter dividerLayoutAdapter) {
        this.dividerLayoutAdapter = dividerLayoutAdapter;
    }

    public void setDividerLayoutAdapter(List<Drawable> drawableList) {
        if (drawableList == null || drawableList.size() < 1) {
            return;
        }

        this.dividerLayoutAdapter = new DividerLayoutAdapter(drawableList) {
        };
    }


    public StepViewConfig getStepViewAttr(AttributeSet as, Context context) {
        StepViewConfig stepViewConfig = new StepViewConfig();
        final TypedArray ta = context.obtainStyledAttributes(as, R.styleable.StepViewDivider);
        if (ta == null) {
            return stepViewConfig;
        }
        final boolean showIndex = ta.getBoolean(R.styleable.StepViewDivider_stepShowOrder, false);
        final int preColor = ta.getColor(R.styleable.StepViewDivider_stepPreColor, context.getResources().getColor(R.color.teal));
        final int afterColor = ta.getColor(R.styleable.StepViewDivider_stepAfterColor, context.getResources().getColor(R.color.grey));
        ta.recycle();
        stepViewConfig.setShowStepText(showIndex);
        stepViewConfig.setPreColor(preColor);
        stepViewConfig.setAfterColor(afterColor);
        return stepViewConfig;
    }
}
