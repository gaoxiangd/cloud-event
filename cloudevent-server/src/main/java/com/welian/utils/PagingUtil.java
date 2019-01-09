package com.welian.utils;

/**
 * Created by memorytale on 2017/4/24.
 */
public class PagingUtil {

    public static Integer getStart(int page, int size) {
        if (page <= 0) {
            return 0;
        }
        return (page - 1) * size;
    }
}
