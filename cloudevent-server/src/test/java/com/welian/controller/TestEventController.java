package com.welian.controller;

import com.welian.ApplicationLauncher;
import com.welian.beans.cloudevent.CityReq;
import com.welian.beans.cloudevent.CustomModule;
import com.welian.beans.cloudevent.EvenBaseReq;
import com.welian.beans.cloudevent.LocReq;
import com.welian.beans.cloudevent.Sponsor;
import com.welian.beans.cloudevent.Ticket;
import com.welian.beans.cloudevent.app.AppReq;
import com.welian.beans.cloudevent.event.EventPara;
import com.welian.beans.cloudevent.event.EventSearchPara;
import com.welian.beans.cloudevent.event.ParameterReq;
import com.welian.beans.cloudevent.event.SaveEventReq;
import com.welian.beans.cloudevent.event.SaveEventResp;
import com.welian.beans.cloudevent.query.EventQuery;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.sean.framework.util.JSONUtil;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dayangshu on 17/7/11.
 */

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {ApplicationLauncher.class})//classes 指定启动类,加载环境
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestEventController extends BaseMockTest{

    private static Integer newEventId;
    @Test
    public void testApi() throws Exception {
    }

    /**
     * 新建活动
     * @throws Exception
     */
    @Test
    public void test01() throws Exception {
        SaveEventReq req = new SaveEventReq();
        req.orgId=orgId;
        EventPara event=new EventPara();
        event.eventId=0;
        event.eventType=1;
        event.title="9.11创业活动";
        event.lineType=0;
        event.address="address";
        event.city=new CityReq();
        event.city.id=179;
        event.city.name="杭州";
        event.loc=new LocReq();
        event.loc.lng=120.219375;
        event.loc.lat=30.259244;
        event.startTime=System.currentTimeMillis();
        event.endTime=System.currentTimeMillis()+1000;
        event.deadlineTime=System.currentTimeMillis()+999;
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
        event.customModule.isCharge=1;
        List<Ticket> tickets=new ArrayList<>();
        Ticket ticket1=new Ticket();
        ticket1.title="1";
        ticket1.intro="1";
        ticket1.price=10D;
        ticket1.count=11;
        tickets.add(ticket1);
        Ticket ticket2=new Ticket();
        ticket2.title="2";
        ticket2.intro="2";
        ticket2.price=20D;
        ticket2.count=22;
        tickets.add(ticket2);
        event.customModule.ticketList=tickets;
        req.event=event;
        req.requestSource=2;
        newEventId=resultData(post("/event/save",req).andReturn(), SaveEventResp.class).eventReq.eventId;
        Thread.sleep(1100);
        EvenBaseReq evenBaseReq = new EvenBaseReq();
        evenBaseReq.eventId=newEventId;
        evenBaseReq.uid = uid;
        evenBaseReq.intro = "aaa";
        postFail("/org/settlementapply",evenBaseReq,"结算金额必须大于￥1");
    }

    /**
     * 获取活动详细信息
     */
    @Test
    public void test02() throws Exception {
        EventPara req=new EventPara();
        req.eventId=eventId;
        req.level=0;
        postSuccess("/event/getdetail",req);
    }
    /**
     * 云活动获取活动列表
     */
    @Test
    public void test03() throws Exception {
        EventSearchPara eventSearchPara = new EventSearchPara();
        eventSearchPara.orgId = orgId;
        eventSearchPara.page = 1;
        eventSearchPara.size = 20;
        eventSearchPara.level=0;
        ParameterReq parameterReq = new ParameterReq();
        eventSearchPara.parameter = parameterReq;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/event/list")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(eventSearchPara))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();
    }

    /**
     * 获取活动下所有通过审核的报名记录的用户信息
     */
    @Test
    public void test04() throws Exception {
        EventPara eventSearchPara = new EventPara();
        eventSearchPara.eventId = eventId;
        eventSearchPara.setPage(1);
        eventSearchPara.setSize(20);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/event/recordsuser")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(eventSearchPara))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();
    }

    /**
     * 获取全部活动列表信息
     */
    @Test
    public void test05() throws Exception {
        EventSearchPara req = new EventSearchPara();
        req.page = 1;
        req.size = 30;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/event/simplelist")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();
    }

    /**
     * 修改活动状态信息
     */
    @Test
    public void test06() throws Exception {
        EventPara req = new EventPara();
        req.orgId = orgId;
        req.eventId=eventId;
        req.type = 0;
        postFail("/event/updatestatus",req,"活动已结束，无法取消发布");
    }

    /**
     * 获取活动配置信息
     */
    @Test
    public void test07() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/event/options")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();
    }

    /**
     * 搜索主办方
     */
    @Test
    public void test08() throws Exception {
        EventSearchPara req = new EventSearchPara();
        req.keyword = "烦";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/event/searchsponsor")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();
    }

    /**
     * 根据推广链接的id获取事件活动详情
     */
    @Test
    public void test09() throws Exception {
        EventPara req = new EventPara();
        req.eventId=eventId;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/event/tickets")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();
    }

    /**
     * 排序规则的名词和ID列表给客户端.
     * 推荐排序、最新活动、最热活动.
     */
    @Test
    public void test10() throws Exception {
        EventPara req = new EventPara();
        req.eventId=eventId;
        mockMvc.perform(MockMvcRequestBuilders.post("/event/orderrules")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();
    }

    /**
     * 收藏或者取消收藏活动
     */
    @Test
    public void test11() throws Exception {
        AppReq req = new AppReq();
        req.eventId=eventId;
        req.uid = uid;
        req.type = 1;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/event/favorite")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();
    }

    /**
     * 活动城市列表
     */
    @Test
    public void test12() throws Exception {
        AppReq req = new AppReq();
        req.eventId=eventId;
        req.uid = uid;
        req.type = 1;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/event/citylist")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();
    }

    /**
     * 查看用户报名详细记录
     */
    @Test
    public void test13() throws Exception {
        AppReq req = new AppReq();
        req.eventId=eventId;
        req.uid = uid;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/event/checkorderstatus")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();
    }

    /**
     * 取用户创建 收藏 报名 活动列表
     */
    @Test
    public void test14() throws Exception {
        AppReq req = new AppReq();
        req.type = 2;
        req.eventType = 1;
        req.uid = uid;
        req.page = 1;
        req.size = 30;
        req.uid = uid;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/event/usereventlist")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();
    }

    /**
     * app活动排序列表
     */
    @Test
    public void test15() throws Exception {
        AppReq req = new AppReq();
        req.page = 1;
        req.size = 30;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/event/orderedlist")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();
    }

    /**
     * 获取活动详情content
     */
    @Test
    public void test16() throws Exception {
        AppReq req = new AppReq();
        req.eventId=eventId;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/event/getcontent")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();
    }


    /**
     * 根据报名id获取活动报名信息（有问题）
     */
    @Test
    public void test17() throws Exception {
        AppReq req = new AppReq();
        req.recordId = 1592029;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/event/getEventRecord")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();
    }

    /**
     * 用户主动取消报名（需验证）
     */
    @Test
    public void test18() throws Exception {
        AppReq req = new AppReq();
        req.recordId = 1590416;
        req.from = 1;
        postFail("/event/cancel",req,"活动已经开始不能取消");
    }
    /**
     * 科委项目
     * 活动list
     */
    @Test
    public void test19() throws Exception {
        EventSearchPara req = new EventSearchPara();
        req.page = 1;
        req.size = 30;
        req.eventIds= Arrays.asList(eventId);
        postFail("/event/keweieventlist",req,"活动已经开始不能取消");

    }

    /**
     * 科委项目
     * 给定活动，对其活动Logo打水印操作
     */
    @Test
    public void test20() throws Exception {
        EventSearchPara req = new EventSearchPara();
        req.logo = "http://fed.welian.com/4_event/welianLogo.png";
        req.detail = "11111";
        req.eventId=eventId;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/event/watermark")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();
    }

    /**
     * 获取创业活动列表
     *
     * @return
     */
    @Test
    public void test21() throws Exception {
        AppReq req = new AppReq();
        req.page = 1;
        req.size = 30;
        req.date=-1;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/event/eventList")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();
    }

    /**
     * 获取当前活动的票的信息（需验证）
     */
    @Test
    public void test22() throws Exception {
        AppReq req = new AppReq();
        req.eventId=eventId;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/event/getTicket")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();
    }

    /**
     * 获取活动下所有报名记录信息
     */
    @Test
    public void test23() throws Exception {
        AppReq req = new AppReq();
        req.eventId=eventId;
        req.page = 1;
        req.size = 30;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/event/getEventRecords")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();
    }

    /**
     * 获取某用户某活动下的票券信息
     */
    @Test
    public void test24() throws Exception {
        AppReq req = new AppReq();
        req.eventId=eventId;
        req.uid = uid;
        postSuccess("/event/buyerticket",req);
    }

    /**
     * 获取某用户某活动下的票券信息
     */
    @Test
    public void test25() throws Exception {
        AppReq req = new AppReq();
        req.eventId=eventId;
        req.uid = uid;
        postSuccess("/event/ticketOrder",req);
    }

    /**
     * 根据ticket_no(recordId) 找到票券信息
     */
    @Test
    public void test26() throws Exception {
        AppReq req = new AppReq();
        req.ticketNo = "1534173412880FCFH";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/event/recordInfo")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();
    }

    /**
     *  取活动详情h5
     */
    @Test
    public void test27() throws Exception {
        AppReq req = new AppReq();
        req.eventId=eventId;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/event/geteventh5")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();
    }


    /**
     *  推荐活动
     */
    @Test
    public void test28() throws Exception {
        AppReq req = new AppReq();
        req.eventId=eventId;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/event/recommend")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(req))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();
    }

    /**
     *  用户在X活动下的的报名记录
     */
    @Test
    public void test29() throws Exception {
        AppReq req = new AppReq();
        req.eventId=eventId;
        req.uid=uid;
        postSuccess("/event/ticket/info",req);
    }

    /**
     * 推荐首页的活动
     */
    @Test
    public void test30() throws Exception {
        EventQuery query = new EventQuery();
        query.cityId = null;
        query.setSize(10);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/event/recomment/home")
                .contentType(MediaType.APPLICATION_JSON).content(JSONUtil.obj2Json(query))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();
    }

    /**
     * 首页数据
     */
    @Test
    public void test31() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/event/portal/statistics")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"state\":1000}"))
                .andReturn();

    }




}
