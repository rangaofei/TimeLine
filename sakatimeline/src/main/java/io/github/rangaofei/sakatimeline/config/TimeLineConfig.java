package io.github.rangaofei.sakatimeline.config;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.List;

import io.github.rangaofei.sakatimeline.adapter.AbstractTimeLineAdapter;
import io.github.rangaofei.sakatimeline.divider.DividerLayoutAdapter;
import io.github.rangaofei.sakatimeline.divider.TimeLineType;
import io.github.rangaofei.sakatimeline.exception.BaseException;
import io.github.rangaofei.sakatimeline.exception.ExceptionMessage;

public class TimeLineConfig {
    private float padding;
    private Drawable timeDrawable;

    private float timeStrokeWidth;
    private int timeColor;


    private TimeLineType type;
    private AbstractTimeLineAdapter adapter;

    private StepViewConfig stepViewConfig;


    private IndexTextConfig indexTextConfig;

    private StrokeType strokeType;

    public IndexTextConfig getIndexTextConfig() {
        return indexTextConfig;
    }

    public void setIndexTextConfig(IndexTextConfig indexTextConfig) {
        this.indexTextConfig = indexTextConfig;
    }

    public TimeLineConfig() {
    }

    public TimeLineConfig(AbstractTimeLineAdapter adapter) {
        this.adapter = adapter;
    }

    public Drawable getTimeDrawable() {
        return timeDrawable;
    }

    public void setTimeDrawable(Drawable timeDrawable) {
        this.timeDrawable = timeDrawable;
    }

    public float getPadding() {
        return padding;
    }

    public void setPadding(float padding) {
        this.padding = padding;
    }

    public float getTimeStrokeWidth() {
        return timeStrokeWidth;
    }

    public void setTimeStrokeWidth(float timeStrokeWidth) {
        this.timeStrokeWidth = timeStrokeWidth;
    }

    public int getTimeColor() {
        return timeColor;
    }

    public void setTimeColor(int timeColor) {
        this.timeColor = timeColor;
    }


    public AbstractTimeLineAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(AbstractTimeLineAdapter adapter) {
        this.adapter = adapter;
    }

    public TimeLineType getType() {
        return type;
    }

    public void setType(TimeLineType type) {
        this.type = type;
    }

    public Bitmap getBitmap() {
        BitmapDrawable bd = (BitmapDrawable) timeDrawable;
        if (bd == null) {
            throw new BaseException(ExceptionMessage.DRAWABLE_NULL);
        }
        return bd.getBitmap();
    }


    public StepViewConfig getStepViewConfig() {
        return stepViewConfig;
    }

    public void setStepViewConfig(StepViewConfig stepViewConfig) {
        this.stepViewConfig = stepViewConfig;
    }

    public StrokeType getStrokeType() {
        return strokeType;
    }

    public void setStrokeType(StrokeType strokeType) {
        this.strokeType = strokeType;
    }
}
