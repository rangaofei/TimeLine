package io.github.rangaofei.sakatimeline.util;

import io.github.rangaofei.sakatimeline.exception.BaseException;
import io.github.rangaofei.sakatimeline.exception.TimeLineException;

public class ExceptionUtil {

    public static void checkIfNull(Object o, String name) {
        if (o == null) {
            thrException(new NullPointerException(name));
        }
    }

    public static void thrException(TimeLineException exception) {
        throw new BaseException(exception);

    }

    public static void thrException(RuntimeException e) {
        throw e;
    }
}
