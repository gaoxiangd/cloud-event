package com.welian.utils;

/**
 * created by GaoXiang on 2018/8/22
 */

/**
 * 分数转换
 */
public class FractionUtil {


    public static String getFraction(int fenzi,int fenmu){
        int n;
        if(fenzi > fenmu)
            n = fenzi;
        else
            n = fenmu;
        int maxn = 0;
        for(int i = 1; i <= n; ++i){
            if(fenzi % i == 0 && fenmu % i == 0)
                maxn = i;
        }
        int a = fenzi / maxn;
        int b = fenmu / maxn;
        if(a == b)
            return 1+"";
        else
            return(a + "/" + b);
    }

}
