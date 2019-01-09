package com.welian.utils;

/**
 * Created by zhaopu on 2018/6/28.
 */
public class AssertUtils {
    public static void requireTrue(boolean value, String msg) {
        if (!value) {
            throw new RuntimeException(msg);
        }
    }
}
