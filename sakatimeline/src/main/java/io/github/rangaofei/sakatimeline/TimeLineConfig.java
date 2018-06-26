package io.github.rangaofei.sakatimeline;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import io.github.rangaofei.sakatimeline.adapter.AbstractTimeLineAdapter;
import io.github.rangaofei.sakatimeline.exception.BaseException;
import io.github.rangaofei.sakatimeline.exception.ExceptionMessage;

public class TimeLineConfig {
    private float padding;

    private Drawable timeDrawable;

    private float timeStrokeWidth;
    private int timeColor;

    private TimeLineType timeLineType = TimeLineType.ONLY_LEFT;
    private AbstractTimeLineAdapter adapter;

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

    public TimeLineType getTimeLineType() {
        return timeLineType;
    }

    public void setTimeLineType(TimeLineType timeLineType) {
        this.timeLineType = timeLineType;
    }


    public AbstractTimeLineAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(AbstractTimeLineAdapter adapter) {
        this.adapter = adapter;
    }

    public Bitmap getBitmap() {
        BitmapDrawable bd = (BitmapDrawable) timeDrawable;
        if (bd == null) {
            throw new BaseException(ExceptionMessage.DRAWABLE_NULL);
        }
        return bd.getBitmap();
    }
}
