package com.welian.controller;

import com.welian.ApplicationLauncher;
import com.welian.beans.cloudevent.app.AppReq;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by GaoXiang on 2018/9/4
 */

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {ApplicationLauncher.class})//classes 指定启动类,加载环境
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
public class TestV1EventController extends BaseMockTest{

    /**
     * 获取小程序活动列表
     */
    @Test
    public void test1() throws Exception {
        AppReq req=new AppReq();
        postSuccess("/v1/event/events",req);
    }
    /**
     * 获取小程序活动列表
     */
    @Test
    public void test2() throws Exception {
        AppReq req=new AppReq();
        req.content="路演";
        postSuccess("/v1/event/search",req);
    }

    /**
     * 我的活动列表
     */
    @Test
    public void test3() throws Exception {
        AppReq req=new AppReq();
        postSuccess("/v1/event/records",req);
    }

    /**
     * 我的票券列表
     */
    @Test
    public void test4() throws Exception {
        AppReq req=new AppReq();
        req.eventId=eventId;
        postSuccess("/v1/event/records",req);
    }



    /**
     * 活动自动结算(提供测试手动调用(当天的),本来是定时任务)
     */
    @Test
    public void test5() throws Exception {
        AppReq req=new AppReq();
        postSuccess("/v1/event/auth/settlement",req);
    }


}
