package com.welian.controller;

import com.welian.ApplicationLauncher;
import com.welian.beans.cloudevent.checkIn.CheckInReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by GaoXiang on 2018/9/5
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {ApplicationLauncher.class})//classes 指定启动类,加载环境
@Transactional
public class TestCheckInController extends BaseMockTest{


    /**
     * 签到-主办方签到-员工提交签到权限
     * @return
     * @see TestEventRecordController  customSignUp()
     */
    @Test
    public void authorize()throws Exception{

    }

    /**
     * 签到
     * @return
     * @see TestEventRecordController  customSignUp()
     */
    @Test
    public void checkIn()throws Exception{

    }

    /**
     * 签到-用户扫码
     * @return
     */
    @Test
    public void detectPermissionUser()throws Exception{
        CheckInReq req=new CheckInReq();
        req.eventId=eventId;
        req.uid=uid;
        postSuccess("/checkin/detectpermissionuser",req);
    }


    /**
     * 签到-活动方扫码
     * @return
     */
    @Test
    public void detectPermissionSponsor()throws Exception{
        CheckInReq req=new CheckInReq();
        req.tradeNo="1535014474666ZAXF";
        postSuccess("/checkin/detectpermissionsponsor",req);
    }

    /**
     * 签到-签到管理
     * @return
     */
    @Test
    public void manage()throws Exception{
        CheckInReq req=new CheckInReq();
        req.eventId=eventId;
        postSuccess("/checkin/manage",req);
    }

    /**
     * 根据时间获取有效的签到数据 (活动券那边需要)
     * @return
     */
    @Test
    public void getvalidactiverecord()throws Exception{
        CheckInReq req=new CheckInReq();
        req.eventId=eventId;
        req.startTime=1535014474836l;
        req.endTime=1536620400000l;
        postSuccess("/checkin/getvalidachekin",req);
    }

    /**
     * 签到-用户扫码
     * @return
     */
    @Test
    public void newDetectPermissionUser()throws Exception{
        CheckInReq req=new CheckInReq();
        req.eventId=eventId;
        req.uid=uid;
        postSuccess("/checkin/detectpermissionuser",req);
    }





}
