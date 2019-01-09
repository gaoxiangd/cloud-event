package com.welian.controller;

import com.welian.ApplicationLauncher;
import com.welian.beans.cloudevent.usermange.UserMangeReq;
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
public class TestUserManageController extends BaseMockTest {

    /**
     * 群发历史
     *
     * @throws Exception
     */
    @Test
    public void list() throws Exception {
        UserMangeReq req = new UserMangeReq();
        req.orgId = orgId;
        postSuccess("/user/manage/sms/history", req);
    }

    /**
     * 推送
     * @see TestEventRecordController customSignUp()
     */
    @Test
    public void push() throws Exception {


    }

    /**
     * 获取机构下报名过的活动列表
     */
    @Test
    public void signupList() throws Exception {
        getSuccess("/user/manage/signup/event/2133");
    }

    /**
     * 推送条数
     *
     * @return
     * @see TestEventRecordController customSignUp()
     */
    @Test
    public void pushCount() throws Exception {


    }


    /**
     * 短信剩余条数
     */
    @Test
    public void sms() throws Exception {
        getSuccess("/user/manage/sms/"+orgId);
    }


    /**
     * 获取用户管理列表
     *
     * @return
     */
    @Test
    public void userManage() throws Exception {
        UserMangeReq req = new UserMangeReq();
        req.orgId = orgId;
        postSuccess("/user/manage", req);

    }

    /**
     * 查看报名记录-用户
     *
     * @return
     */
    @Test
    public void record() throws Exception {
        UserMangeReq req = new UserMangeReq();
        req.orgId = orgId;
        req.uid = uid;
        postSuccess("/user/manage/record", req);

    }

    /**
     * 短信充值
     *
     * @return
     */
    @Test
    public void smsPay() throws Exception {
        //配置项无法加载
        UserMangeReq req = new UserMangeReq();
        req.orgId = orgId;
        req.uid = uid;
        req.smsCount = 10;
        req.payType = 2;
        req.returnUrl="ceshi";
        postSuccess("/user/manage/smspay", req);

    }

}
