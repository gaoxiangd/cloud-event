package com.welian.controller;

import com.welian.ApplicationLauncher;
import com.welian.beans.cloudevent.org.OrgReq;
import com.welian.beans.cloudevent.org.OrgResp;
import com.welian.beans.cloudevent.sys.SysMessagesReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * created by GaoXiang on 2018/9/4
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {ApplicationLauncher.class})//classes 指定启动类,加载环境
@Transactional
public class TestSysMsgController extends BaseMockTest{
    /**
     * 获取系统消息列表
     * @throws Exception
     */
    @Test
    public void list()throws Exception{
        OrgReq req=new OrgReq();
        req.orgId=orgId;
        req.setSize(10);
        req.setPage(1);
        postSuccess("/sysmsg/getlist",req);
    }

    /**
     * 批量删除消息
     * @throws Exception
     */
    @Test
    public void delete()throws Exception{
        SysMessagesReq req=new SysMessagesReq();
        req.messageIds= Arrays.asList(1459,1458);
        postSuccess("/sysmsg/delete",req);
    }

    /**
     * 获得消息详情
     * @throws Exception
     */
    @Test
    public void getMsgDetail()throws Exception{
        SysMessagesReq req=new SysMessagesReq();
        req.messageId=1434;
        postSuccess("/sysmsg/getdetail",req);
    }

    /**
     * 计数未读消息
     * @throws Exception
     */
    @Test
    public void countUnReadMsg()throws Exception{
        OrgResp req=new OrgResp();
        req.orgId=orgId;
        postSuccess("/sysmsg/unreadmsg",req);
    }


}
