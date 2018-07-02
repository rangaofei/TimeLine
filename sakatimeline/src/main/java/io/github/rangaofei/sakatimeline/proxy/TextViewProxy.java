package io.github.rangaofei.sakatimeline.proxy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

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

        TypedArray t = context.obtainStyledAttributes(id, R.styleable.TimeLineViewProxy);
        if (ta != null) {
            int v = ta.getInt(R.styleable.TimeLineViewProxy_visibleProxy, 0);
            switch (v) {
                case 0:
                    textView.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    textView.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    textView.setVisibility(View.GONE);
                    break;
            }
        }
        t.recycle();
    }
}
