package com.welian.utils;


import org.sean.framework.util.I18nUtil;

import java.util.Locale;

/**
 * Description: 国际化工具类
 * Date: 2016/10/19 17:41
 *
 * @author Sean.xie
 */
public class I18N {

    /**
     * 业务文案, 放在values/biz_string.properties
     *
     * @param key
     * @param args
     * @return
     */
    public static String getString(String key, Object... args) {
        return I18nUtil.getLocalResource(Locale.getDefault(), "values/biz_strings", key, args);
    }

    /**
     * 错误文案, 放在values/error_strings.properties
     *
     * @param key
     * @param args
     * @return
     */
    public static String getErrorMsg(String key, Object... args) {
        return I18nUtil.getLocalResource(Locale.getDefault(), "values/error_strings", key, args);
    }
}
