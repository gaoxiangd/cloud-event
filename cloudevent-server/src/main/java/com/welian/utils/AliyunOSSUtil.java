package com.welian.utils;


import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.welian.config.Constant;
import org.apache.http.util.TextUtils;

import java.io.File;

public class AliyunOSSUtil {

    /**
     * 阿里云文件上传
     *
     * @param aliyunOssBucket
     * @param fileDir
     * @param fileName
     */
    public static String uploadFile(String endPoint, String aliyunOssBucket, String fileDir, String fileName) throws Exception {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endPoint, Constant.ALIOSS_ACCESS_KEY_ID, Constant.ALIOSS_ACCESS_KEY_SECRET);
        // 上传文件
        File file = new File(fileDir, fileName);
        String newfileName = fileName.replace("WL", String.valueOf(file.length() / 1024));
        ossClient.putObject(aliyunOssBucket, newfileName, file);
        // 关闭client
        ossClient.shutdown();
        return newfileName;
    }



    /**
     * 下载到指定的本地文件中，如果指定的本地文件不存在则会新建。
     *
     * @param aliyunOssBucket
     * @param saveDir
     * @param fileName
     */
    public static String downloadFile(String endpoint, String aliyunOssBucket, String saveDir, String fileName) throws OSSException {
        File dir = new File(saveDir);
        if (!dir.exists() || dir.isFile()) {
            dir.delete();
            dir.mkdirs();
        }
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, Constant.ALIOSS_ACCESS_KEY_ID, Constant.ALIOSS_ACCESS_KEY_SECRET);
        // 下载object到文件
        ossClient.getObject(new GetObjectRequest(aliyunOssBucket, fileName), new File(saveDir + "/" + fileName));
        // 关闭client
        ossClient.shutdown();
        return fileName;
    }

    public static String createPDFName() {
        return AliyunOSSUtil.get4Letter() + System.currentTimeMillis() + ".pdf";
    }

    public static String get4Letter() {
        return getRandomLetterDownCase(4);
    }

    public static String getRandomLetterDownCase(int len) {
        String str = "";
        for (int i = 0; i < len; i++) {// 你想生成几个字符的，就把len改成几．
            str = str + (char) (Math.random() * 26 + 'a');
        }
        return str;
    }

    public static String getNameFromUrl(String url) {
        if (TextUtils.isEmpty(url))
            return url;
        if (url.contains("/")) {
            return url.substring(url.lastIndexOf("/") + 1);
        }
        return url;
    }

    public static String createXLSName() {
        return AliyunOSSUtil.get4Letter() + System.currentTimeMillis() + ".xls";
    }
}
