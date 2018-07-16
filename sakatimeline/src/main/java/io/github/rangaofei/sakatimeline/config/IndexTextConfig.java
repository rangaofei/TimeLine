package io.github.rangaofei.sakatimeline.config;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import io.github.rangaofei.sakatimeline.R;

/**
 * 用于配置序号的显示样式
 */
public class IndexTextConfig {
    private int textColor;
    private int textSize;

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public IndexTextConfig getIndexTextAttr(AttributeSet as, Context context) {
        final IndexTextConfig indexTextConfig = new IndexTextConfig();
        final TypedArray ta = context.obtainStyledAttributes(as, R.styleable.IndexText);
        if (ta == null) {
            return indexTextConfig;
        }
        final int textSize = ta.getDimensionPixelSize(R.styleable.IndexText_timeIndexSize,
                context.getResources().getDimensionPixelSize(R.dimen.default_text_size));
        final int textColor = ta.getColor(R.styleable.IndexText_timeIndexColor,
                context.getResources().getColor(R.color.pink));
        ta.recycle();
        indexTextConfig.setTextColor(textColor);
        indexTextConfig.setTextSize(textSize);
        return indexTextConfig;
    }
}
