package com.welian.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;

public class Base64Utils {
    private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
            .toCharArray();

    public static String encode(byte[] data) {
        int start = 0;
        int len = data.length;
        StringBuffer buf = new StringBuffer(data.length * 3 / 2);

        int end = len - 3;
        int i = start;
        int n = 0;

        while (i <= end) {
            int d = ((((int) data[i]) & 0x0ff) << 16)
                    | ((((int) data[i + 1]) & 0x0ff) << 8)
                    | (((int) data[i + 2]) & 0x0ff);

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append(legalChars[(d >> 6) & 63]);
            buf.append(legalChars[d & 63]);

            i += 3;

            if (n++ >= 14) {
                n = 0;
                buf.append(" ");
            }
        }

        if (i == start + len - 2) {
            int d = ((((int) data[i]) & 0x0ff) << 16)
                    | ((((int) data[i + 1]) & 255) << 8);

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append(legalChars[(d >> 6) & 63]);
            buf.append("=");
        } else if (i == start + len - 1) {
            int d = (((int) data[i]) & 0x0ff) << 16;

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append("==");
        }
        String result = buf.toString().replaceAll("/", "-");
        return result;
    }

    public static byte[] decode(String s) {
        s = s.replaceAll("%3D", "=");
        s = s.replaceAll("-", "/");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            decode(s, bos);
        } catch (IOException e) {
            return null;
        }

        byte[] decodedBytes = bos.toByteArray();

        try {
            bos.close();
            bos = null;
        } catch (IOException ex) {
            System.err.println("Error while decoding BASE64: " + ex.toString());
        }

        return decodedBytes;
    }

    public static String decodeToString(String str) {
        str = URLDecoder.decode(str);
        String d = decodeBufferString(str);
        if (d != null) {
            return d;
        }

        str = str.replaceAll("%3D", "=");
        str = str.replaceAll("-", "/");
        byte[] bytes = decode(str);
        if (bytes != null) {
            return new String(bytes);
        }
        return null;
    }

    private static void decode(String s, OutputStream os) throws IOException {
        int i = 0;
        int len = s.length();

        while (true) {
            while (i < len && s.charAt(i) <= ' ')
                i++;

            if (i == len)
                break;

            int tri = (decode(s.charAt(i)) << 18)
                    + (decode(s.charAt(i + 1)) << 12)
                    + (decode(s.charAt(i + 2)) << 6)
                    + (decode(s.charAt(i + 3)));

            os.write((tri >> 16) & 255);
            if (s.charAt(i + 2) == '=')
                break;
            os.write((tri >> 8) & 255);
            if (s.charAt(i + 3) == '=')
                break;
            os.write(tri & 255);

            i += 4;
        }
    }

    private static int decode(char c) {
        if (c >= 'A' && c <= 'Z')
            return ((int) c) - 65;
        else if (c >= 'a' && c <= 'z')
            return ((int) c) - 97 + 26;
        else if (c >= '0' && c <= '9')
            return ((int) c) - 48 + 26 + 26;
        else
            switch (c) {
                case '+':
                    return 62;
                case '/':
                    return 63;
                case '=':
                    return 0;
                default:
                    throw new RuntimeException("unexpected code: " + c);
            }
    }

    private static String decodeBufferString(String encodeStr) {
        if (encodeStr == null) {
            return null;
        }
        try {
            String pid1 = new String(Base64Helper.decodeBuffer(encodeStr));
            return pid1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
