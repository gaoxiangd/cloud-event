package com.welian.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.IOException;

/**
 * Created by liangzi on 15/11/27.
 *
 * 找个时间统一替换这个方法
 * sun.misc.BASE64Decoder;
 * sun.misc.BASE64Encoder
 */
public class Base64Helper {

    public static String encode(byte[] bt) {
        return Base64.encodeBase64String(bt);
//        return new BASE64Encoder().encode(bt);
    }

    public static byte[] decodeBuffer(String data) throws IOException {
        return Base64.decodeBase64(data);
//        BASE64Decoder decoder = new BASE64Decoder();
//        return decoder.decodeBuffer(data);
    }
}
