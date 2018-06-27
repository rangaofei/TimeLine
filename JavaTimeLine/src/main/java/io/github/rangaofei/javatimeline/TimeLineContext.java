package io.github.rangaofei.javatimeline;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

public class TimeLineContext {

    public static final String TAG = "TimeLine";
    private static final boolean DEBUG = true;
    public static Filer filter;

    public static Messager messager;

    public static Elements elementUtil;


    public static Filer getFilter() {
        if (filter == null) {
            throw new RuntimeException("filter is null");
        }
        return filter;
    }


    public static Messager getMessager() {
        if (messager == null) {
            throw new RuntimeException("messager is null");
        }
        return messager;
    }


    public static Elements getElementUtil() {
        if (elementUtil == null) {
            throw new RuntimeException("elementutil is null");
        }
        return elementUtil;
    }

    public static void note(String msg) {
        if (DEBUG) {
            if (messager == null) {
                throw new RuntimeException("messager is null");
            }
            messager.printMessage(Diagnostic.Kind.NOTE, TAG + ":" + msg);
        }
    }

    public static void note(String msg, Object... args) {
        if (DEBUG) {
            if (messager == null) {
                throw new RuntimeException("messager is null");
            }
            messager.printMessage(Diagnostic.Kind.NOTE, TAG + ":" + String.format(msg, args));
        }
    }

}
