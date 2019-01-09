package com.welian.utils;

import com.alibaba.fastjson.JSONObject;
import org.sean.framework.code.ResultCodes;
import org.sean.framework.exception.CodeException;
import org.sean.framework.util.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Author jeizas
 * @Date 2018 5/14/18 11:47 PM
 */
public class HttpTool {

    private static Logger logger = Logger.newInstance(HttpTool.class);

    /**
     * 发送http post请求
     *
     * @param url    请求url
     * @param parmas post参数
     * @return
     */
    public static String doPost(String url, JSONObject parmas) {

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Basic YWRtaW46");
        String result = "";

        try {

            StringEntity s = new StringEntity(parmas.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(s);

            // 发送请求
            HttpResponse httpResponse = client.execute(post);

            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                strber.append(line + "\n");
            }

            inStream.close();
            result = strber.toString();

            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                logger.error("接口请求异常，http statusCode={}，url={}, param={}",
                        httpResponse.getStatusLine().getStatusCode(), url, parmas.toString());
                throw new CodeException(ResultCodes.Code.COMMON_ERROR_BIZ, "server:"
                        + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (CodeException e) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_BIZ, e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("接口请求异常，url={}, param={}", url, parmas.toString());
        }
        return result;
    }
}
