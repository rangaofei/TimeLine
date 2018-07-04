package io.github.rangaofei.sakatimeline.proxy;

import android.widget.TextView;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ViewProxyHandler implements InvocationHandler {
    private ViewInterface viewInterface;

    public ViewProxyHandler(ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("setStyle")) {
            if (this.viewInterface.getView() instanceof TextView) {
                ((TextView) this.viewInterface.getView()).
                        setTextAppearance(((TextView) this.viewInterface.getView()).getContext(),
                                (int) args[0]);
            }
            return method.invoke(viewInterface, args);
        }
        return method.invoke(viewInterface, args);
    }
}
