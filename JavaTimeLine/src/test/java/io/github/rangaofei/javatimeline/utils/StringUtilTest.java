package io.github.rangaofei.javatimeline.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {
    private String test1 = "R.layout.txt";
    private String test2 = "R.ll.123";

    private String test3 = "R.id.ttt";
    private String test4 = "R.ii.123";

    @Test
    public void isLayoutId() {
        boolean b = StringUtil.isLayoutId(test1);
        assertTrue(b);

        boolean b1 = StringUtil.isLayoutId(test2);
        assertFalse(b1);
    }

    @Test
    public void isResId() {
        boolean b = StringUtil.isResId(test3);
        assertTrue(b);

        boolean b1 = StringUtil.isResId(test4);
        assertFalse(b1);
    }
}