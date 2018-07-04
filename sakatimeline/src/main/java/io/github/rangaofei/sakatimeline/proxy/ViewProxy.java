package io.github.rangaofei.sakatimeline.proxy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import io.github.rangaofei.sakatimeline.R;

/**
 * 一个基本的view
 */
public class ViewProxy<T extends View> implements ViewInterface<T> {
    private T t;

    public ViewProxy(T t) {
        this.t = t;
    }

    @Override
    public T setStyle(int id) {
        TypedArray ta = t.getContext().obtainStyledAttributes(id, R.styleable.TimeLineViewProxy);
        Drawable drawable = ta.getDrawable(R.styleable.TimeLineViewProxy_backgroundProxy);
        int v = ta.getInt(R.styleable.TimeLineViewProxy_visibleProxy, -1);
        boolean clickable = ta.getBoolean(R.styleable.TimeLineViewProxy_clickProxy, true);
        setBackground(drawable);
        setClickAble(clickable);
        setVisibility(v);
        ta.recycle();
        return this.t;
    }

    @Override
    public T getView() {
        return this.t;
    }

    /**
     * 设置view的可见性
     */
    private void setVisibility(int v) {
        if (v == -1) {
            return;
        }
        switch (v) {
            case 0:
                t.setVisibility(View.VISIBLE);
                break;
            case 1:
                t.setVisibility(View.INVISIBLE);
                break;
            case 2:
                t.setVisibility(View.GONE);
                break;
        }
    }

    private void setBackground(Drawable drawable) {
        Log.d("+++", "presetBackground");
        if (drawable == null) {
            Log.d("+++", "setBackground");
            return;
        }

        t.setBackground(drawable);
    }

    private void setClickAble(boolean clickAble) {
        t.setClickable(clickAble);
    }


    private void setSpecialView(int id) {
        if (this.t instanceof TextView) {
            ((TextView) this.t).setTextAppearance(this.t.getContext(), id);
        }
    }
}

