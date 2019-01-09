package com.welian.controller;

import com.welian.ApplicationLauncher;
import com.welian.beans.cloudevent.CityReq;
import com.welian.beans.cloudevent.CustomModule;
import com.welian.beans.cloudevent.LocReq;
import com.welian.beans.cloudevent.Sponsor;
import com.welian.beans.cloudevent.event.EventPara;
import com.welian.beans.cloudevent.event.SaveEventReq;
import com.welian.beans.cloudevent.manage.EventManageReq;
import com.welian.beans.cloudevent.manage.RmdOrHotReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Author jeizas
 * @Date 2018 5/31/18 4:46 PM
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {ApplicationLauncher.class})//classes 指定启动类,加载环境
@Transactional
public class TestServerManageController extends BaseMockTest{

    @Test
    public void recommendOrHot() throws Exception {
        RmdOrHotReq req = new RmdOrHotReq();
        req.eventId=eventId;
        req.type = 1;
        req.adminUid = uid;
        req.cityList = Arrays.asList(cityId, 131);

        postSuccess("/manage/recommendorhot",req);
    }
    @Test
    public void list() throws Exception {
        EventManageReq req = new EventManageReq();
        req.eventName = "";
        req.cityId = 0;
        req.recommend = 1;
        req.hot=-2;
        req.recommendHomeStatus=-2;
        req.timeType=1;
        req.startTime="";
        req.endTime="";
        req.orderType=1;
        req.lineType=-2;
        req.state=-2;
        req.eventType=-2;
        req.page=1;
        req.size=20;
        postSuccess("/manage/list",req);
    }
    @Test
    public void detail() throws Exception {
        EventPara req = new EventPara();
        req.eventId=eventId;
        postSuccess("/manage/detail",req);
    }
    @Test
    public void delete() throws Exception {
        EventManageReq req = new EventManageReq();
        req.eventId=eventId;
        req.adminUid=uid;
        postSuccess("/manage/delete",req);
    }
    @Test
    public void update() throws Exception {
        SaveEventReq req = new SaveEventReq();
        req.orgId=orgId;
        EventPara event=new EventPara();
        event.eventId=eventId;
        event.eventType=1;
        event.title="9.4创业活动";
        event.lineType=0;
        event.address="address";
        event.city=new CityReq();
        event.city.id=179;
        event.city.name="杭州";
        event.loc=new LocReq();
        event.loc.lng=120.219375;
        event.loc.lat=30.259244;
        event.startTime=System.currentTimeMillis();
        event.endTime=System.currentTimeMillis()+24*3600*1000;
        event.deadlineTime=1536548863000l;
        event.logo="nvfa1442457837648_750_558_383.jpg";
        event.state=2;
        event.detail="<p>迭代迭代</p>";
        Sponsor sponsor=new Sponsor();
        sponsor.name="aa";
        sponsor.id=1160;
        event.sponsors=new ArrayList<>();
        event.sponsors.add(sponsor);
        event.isPromotion=0;
        event.customModule=new CustomModule();
        event.customModule.isCustomVerify=0;
        event.customModule.isGroupChat=0;
        event.customModule.numberStatus=0;
        event.customModule.isPublicity=0;
        req.event=event;
        req.requestSource=1;
        req.adminUid=uid;
        postSuccess("/manage/save",req);
    }

    @Test
    public void updateCoupon() throws Exception {
        EventManageReq req = new EventManageReq();
        req.eventId=eventId;
        req.operate=1;
        postSuccess("/manage/updatecoupon",req);
    }
    @Test
    public void more() throws Exception {
        EventManageReq req = new EventManageReq();
        req.eventId=eventId;
        req.cityId=0;
        req.state=2;
        req.beforeDeadLineTime=true;
        req.orderType=2;
        req.page=1;
        req.size=3;
        req.hot=-2;
        req.recommend=1;
        postSuccess("/manage/more",req);
    }
}