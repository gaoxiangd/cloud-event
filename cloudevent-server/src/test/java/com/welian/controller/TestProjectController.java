package com.welian.controller;

import com.welian.beans.cloudevent.org.OrderChannelReq;
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
public class TestProjectController extends BaseMockTest{
   Integer orgId=2140;
   Integer signUpId=1592035;


    /**
     * 根据机构id获取项目列表
     */
    @Test
    public void list()throws Exception{
        OrderChannelReq req=new OrderChannelReq();
        req.orgId=orgId;
        req.page=1;
        req.size=10;
        req.type=1;
        postSuccess("/project/list",req);
    }

    /**
     * 对报名项目进行评星和备注
     */
    @Test
    public void operate()throws Exception{
        OrderChannelReq req=new OrderChannelReq();
        req.signUpId=signUpId;
        req.orgId=orgId;
        req.remark="不错";
        postSuccess("/project/operatetoproject",req);
    }

    /**
     * 获取对项目的反馈列表
     */
    @Test
    public void getFeedbackList()throws Exception{
        OrderChannelReq req=new OrderChannelReq();
        req.signUpId=signUpId;
        req.orgId=orgId;
        req.page=1;
        req.size=10;
        postSuccess("/project/getfeedbacklist",req);
    }

    /**
     *
     */
    @Test
    public void getThreeProject()throws Exception{
        OrderChannelReq req=new OrderChannelReq();
        req.signUpId=signUpId;
        req.orgId=orgId;
        postSuccess("/project/getthreeproject",req);
    }

    /**
     *获取项目筛选的参数
     */
    @Test
    public void getProjectParams()throws Exception{
        OrderChannelReq req=new OrderChannelReq();
        postSuccess("/project/getparams",req);
    }



}
