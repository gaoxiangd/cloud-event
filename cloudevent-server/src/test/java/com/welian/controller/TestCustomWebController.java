package com.welian.controller;

import com.welian.beans.cloudevent.customweb.CustomWebReq;
import com.welian.ApplicationLauncher;
import org.junit.Test;
import org.junit.runner.RunWith;
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
@Transactional
public class TestCustomWebController extends BaseMockTest{

    /**
     * 保存专题官网
     *
     * @param
     * @return
     */
    @Test
    public void save() throws Exception{
        CustomWebReq req=new CustomWebReq();
        req.eventId=eventId;
        req.content="哈哈哈";
        postSuccess("/event/official/save",req);
    }

    /**
     * 获取专题官网详情接口eventId和uniqueKey必须传一个，
     * 如果平台方没有设置专题官网，会传给前端活动的详情信息，用户渲染默认的专题官网
     * 用途：
     * 1：当传入eventId时，作用在平台方单个活动-分享-专题官网时用到，用于活动方编辑专题官网时使用
     * 2：当传入uniqueKey时，作用在专题官网展示给报名用户时使用，用于渲染页面
     *
     * @param
     * @return
     */
    @Test
    public void get()throws Exception{
        CustomWebReq req=new CustomWebReq();
        req.eventId=eventId;
        postSuccess("/event/official/get",req);
    }
}
