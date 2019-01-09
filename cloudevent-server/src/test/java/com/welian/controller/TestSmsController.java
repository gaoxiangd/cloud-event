package com.welian.controller;

import com.welian.beans.cloudevent.sms.SendSmsReq;
import com.welian.ApplicationLauncher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * created by GaoXiang on 2018/9/4
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {ApplicationLauncher.class})//classes 指定启动类,加载环境
public class TestSmsController extends BaseMockTest {

    /**
     * 给活动下所有用户发送短信
     * @throws Exception
     */
    @Test
    public void send() throws Exception {
        SendSmsReq req = new SendSmsReq();
        req.eventId=eventId;
        req.content = "测试用例的内容";
        postFail("/sms/sendsms", req,"活动已结束，短信功能无法使用");
    }

    /**
     * 获取短信列表
     * @throws Exception
     */
    @Test
    public void list() throws Exception {
        SendSmsReq req = new SendSmsReq();
        req.eventId=eventId;
        postSuccess("/sms/list",req);
    }


}
