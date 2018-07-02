package io.github.rangaofei.sakatimeline;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

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

    public static class StepViewConfig {
        private boolean showStepText;
        private int dividerNum;
        private int indexColor;
        private int preColor;
        private int afterColor;
        private DividerLayoutAdapter dividerLayoutAdapter;

        public boolean isShowStepText() {
            return showStepText;
        }

        public void setShowStepText(boolean showStepText) {
            this.showStepText = showStepText;
        }


        public int getDividerNum() {
            return dividerNum;
        }

        public void setDividerNum(int dividerNum) {
            this.dividerNum = dividerNum;
        }

        public int getIndexColor() {
            return indexColor;
        }

        public void setIndexColor(int indexColor) {
            this.indexColor = indexColor;
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

            DividerLayoutAdapter d = new DividerLayoutAdapter(drawableList) {
            };
            this.dividerLayoutAdapter = d;
//            this.dividerLayoutAdapter = dividerLayoutAdapter;
        }
    }
}
