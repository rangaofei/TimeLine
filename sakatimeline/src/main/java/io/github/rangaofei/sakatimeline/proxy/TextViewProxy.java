package io.github.rangaofei.sakatimeline.proxy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import io.github.rangaofei.sakatimeline.R;


public class TextViewProxy implements TextViewInterface {
    private TextView textView;

    public TextViewProxy(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void setTextAppearance(Context context, int id) {

        TypedArray ta = context.obtainStyledAttributes(id, R.styleable.ViewBackgroundHelper);
        if (ta != null) {
            Drawable drawable = ta.getDrawable(0);
            ta.recycle();
            if (drawable != null) {
                textView.setBackground(drawable);
            }
            textView.setTextAppearance(context, id);
        }


    }
}
