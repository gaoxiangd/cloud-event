package com.welian.controller;

import com.welian.beans.cloudevent.ExtensionLinkReq;
import com.welian.beans.cloudevent.ExtensionLinkResp;
import com.welian.ApplicationLauncher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by GaoXiang on 2018/8/29
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {ApplicationLauncher.class})//classes 指定启动类,加载环境
@Transactional
public class TestExtensionLinkController extends BaseMockTest{

    /**
     * 推广链接相关接口测试用例
     */
    @Test
    public void testApi() throws Exception {
        //新增链接
        ExtensionLinkReq req = new ExtensionLinkReq();
        req.extensionLinkName = "测试链接名字";
        req.eventId=eventId;
        ExtensionLinkResp resp=resultData(postSuccess("/event/addlink",req).andReturn(), ExtensionLinkResp.class);

        //修改链接
        req.extensionLinkName="测试链接名字2";
        req.extensionLinkId=resp.extensionLinkId;
        postSuccess("/event/reviselink",req);
        //获取活动通用表单链接
        req.linkListType=1;
        req.page=1;
        req.size=10;
        postSuccess("/event/getlinklist",req);
        //获取默认连接
        postSuccess("/event/getdefaultlink",req);
        //根据uniqueKey和tradeNo，获得跳转url地址
        postSuccess("/event/getdefaultlink",req);
        req.tradeNo="1535358129769XKJQ";
        req.uniqueKey="ODI1NDA===";
        postSuccess("/event/resultUrl",req);

    }


}
