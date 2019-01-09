package com.welian.controller;

import com.welian.beans.cloudevent.org.OrgPackagePageReq;
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
public class TestOrgPackageController extends BaseMockTest{
    /**
     * 套餐管理列表
     */
    @Test
    public void list() throws Exception{
        OrgPackagePageReq req=new OrgPackagePageReq();
        req.type=1;
        req.keyword="";
        req.page=1;
        req.size=10;
        postSuccess("/org/package/list",req);
    }

    /**
     * 修改套餐类型
     * @throws Exception
     */
    @Test
    public void update() throws Exception{
        OrgPackagePageReq req=new OrgPackagePageReq();
        req.orgId=orgId;
        req.packageId=2;
        postSuccess("/org/package/save",req);
    }

    /**
     * 获得所有套餐信息
     */
    @Test
    public void getPackageInfo()throws Exception{
        OrgPackagePageReq req=new OrgPackagePageReq();
        postSuccess("/org/package/packageinfo",req);
    }

}
