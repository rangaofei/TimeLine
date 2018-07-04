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
        textView.setTextAppearance(textView.getContext(), id);
        ViewProxy<TextView> viewProxy = new ViewProxy<>(textView);
        viewProxy.setStyle(id);

    }

}
