package com.welian.controller;

import com.welian.ApplicationLauncher;
import com.welian.beans.cloudevent.statistical.StatisticReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * Created by zhaopu on 2017/7/4.
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {ApplicationLauncher.class})//classes 指定启动类,加载环境
@Transactional
public class TestEventStatisticController extends BaseMockTest{

    Integer orgId=71;
    /**
     * 后台首页-顶部数量统计
     */
    @Test
    public void testtotal() throws Exception {
        StatisticReq req = new StatisticReq();
        req.orgId = orgId;
        postSuccess("/statistic/total",req);
    }

    /**
     * 报名图表统计
     */
    @Test
    public void testsignupstatistic() throws Exception {
        StatisticReq req = new StatisticReq();
        req.orgId = orgId;
        req.days = 7;
        req.eventId=eventId;
        postSuccess("/statistic/signupstatistic",req);
    }

    /**
     * 报名人员分布情况统计
     */
    @Test
    public void testareadistribution() throws Exception {
        StatisticReq req = new StatisticReq();
        req.orgId = orgId;
        req.eventId=eventId;
        postSuccess("/statistic/areadistribution",req);
    }


    /**
     * 单个活动的概况信息
     */
    @Test
    public void testsingleevent() throws Exception {
        StatisticReq req = new StatisticReq();
        req.eventId=eventId;
        postSuccess("/statistic/singleevent",req);
    }


    /**
     * 统计定时任务
     */
//    @Test
//    public void testRun() throws Exception {
//        StatisticReq req = new StatisticReq();
//        postSuccess("/statistic/task",req);
//
//    }

    /**
     * 获取渠道统计接口
     */
    @Test
    public void getOrgStatistics() throws Exception {
        StatisticReq req = new StatisticReq();
        req.orgId=orgId;
        req.days=7;
        postSuccess("/statistic/getorgstatistics",req);

    }

    /**
     *科委项目
     *  查询近30天的每天报名数和签到数
     */
    @Test
    public void getKeweiEvnet() throws Exception {
        StatisticReq req = new StatisticReq();
        req.eventIds= Arrays.asList(82550, 82549);
        req.days=7;
        postSuccess("/statistic/keweievent",req);

    }

    /**
     * 创业活动表单浏览数加一
     */
    @Test
    public void formAdd() throws Exception {
        StatisticReq req = new StatisticReq();
        req.eventId=eventId;
        postSuccess("/statistic/formcont",req);

    }



}
