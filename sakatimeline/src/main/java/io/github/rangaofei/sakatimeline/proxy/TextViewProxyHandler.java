package io.github.rangaofei.sakatimeline.proxy;

import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TextViewProxyHandler implements InvocationHandler {
    private TextView textView;

    public TextViewProxyHandler(TextView textView) {
        this.textView = textView;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d("+++", method.getName());
        if (method.getName().equals("setTextAppearance")) {
            Log.d("+++", proxy.getClass().getName());
            return method.invoke(new TextViewProxy(textView), args);
        }
        return method.invoke(this, args);
    }
}
