package io.github.rangaofei.sakatimeline.proxy;

import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TextViewProxyHandler implements InvocationHandler {
    private TextViewInterface anInterface;

    public TextViewProxyHandler(TextViewInterface anInterface) {
        this.anInterface = anInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d("+++", method.getName());
        return method.invoke(anInterface, args);
    }
}
