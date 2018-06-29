package io.github.rangaofei.javatimeline.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil {

    private static String layoutIdRegex = "^R\\.layout\\.[a-z_]+$";
    private static String resIdRegex = "^R\\.id\\.[A-Za-z1-9_-]+$";

    public static boolean isLayoutId(String src) {
        return isMatch(layoutIdRegex, src);
    }

    public static boolean isResId(String src) {
        return isMatch(resIdRegex, src);
    }

    private static boolean isMatch(String regex, String src) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(src);
        return matcher.matches();
    }


}
