package io.github.rangaofei.sakatimeline.proxy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
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
            int color = ta.getColor(0, 0x00000000);
            ta.recycle();
            if (color != 0x00000000)
                textView.setBackgroundColor(color);
            textView.setTextAppearance(context, id);
        }


    }
}
