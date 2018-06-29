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


    public static void note(String msg, Object... args) {
        logMessage(Diagnostic.Kind.NOTE, msg, args);
    }

    public static void error(String msg, Object... args) {
        logMessage(Diagnostic.Kind.ERROR, msg, args);
    }

    public static void warning(String msg, Object... args) {
        logMessage(Diagnostic.Kind.WARNING, msg, args);
    }

    public static void other(String msg, Object... args) {
        logMessage(Diagnostic.Kind.OTHER, msg, args);
    }

    public static void logMessage(Diagnostic.Kind kind, String msg, Object... args) {
        if (DEBUG) {
            if (messager == null) {
                throw new RuntimeException("Messager is NULL");
            }
            messager.printMessage(kind, String.format(msg, args));
        }
    }

}
