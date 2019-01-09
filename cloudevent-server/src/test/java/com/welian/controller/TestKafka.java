package com.welian.controller;

import com.welian.ApplicationLauncher;
import com.welian.service.EventKafkaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * created by GaoXiang on 2018/7/23
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {ApplicationLauncher.class})//classes 指定启动类,加载环境
public class TestKafka {
    @Autowired EventKafkaService eventKafkaService;

    /**
     * 测试kafka回调
     * @see TestEventRecordController customSignUp()
     */
    @Test
    public void insertRecord(){

    }
}
