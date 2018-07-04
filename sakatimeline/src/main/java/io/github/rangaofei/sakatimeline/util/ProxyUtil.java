package io.github.rangaofei.sakatimeline.util;

import android.support.annotation.IdRes;
import android.view.View;

import java.lang.reflect.Proxy;

import io.github.rangaofei.sakatimeline.adapter.BaseViewHolder;
import io.github.rangaofei.sakatimeline.proxy.ViewInterface;
import io.github.rangaofei.sakatimeline.proxy.ViewProxy;
import io.github.rangaofei.sakatimeline.proxy.ViewProxyHandler;

public class ProxyUtil {

    @SuppressWarnings("unchecked")
    public static <T extends View> ViewInterface<T> createView(final BaseViewHolder viewHolder,
                                                               @IdRes final int id) {
        T result = viewHolder.itemView.findViewById(id);

        return (ViewInterface<T>) Proxy.newProxyInstance(
                ViewInterface.class.getClassLoader(),
                new Class[]{ViewInterface.class},
                new ViewProxyHandler(new ViewProxy(result)));
    }
}
