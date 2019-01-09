package com.welian.controller;

import com.welian.ApplicationLauncher;
import com.welian.beans.cloudevent.AddtionalForm;
import com.welian.beans.cloudevent.Base64KeyReq;
import com.welian.beans.cloudevent.CityReq;
import com.welian.beans.cloudevent.CustomModule;
import com.welian.beans.cloudevent.ExtensionLinkReq;
import com.welian.beans.cloudevent.LocReq;
import com.welian.beans.cloudevent.ProjectModule;
import com.welian.beans.cloudevent.Sponsor;
import com.welian.beans.cloudevent.Ticket;
import com.welian.beans.cloudevent.TicketResp;
import com.welian.beans.cloudevent.app.AppReq;
import com.welian.beans.cloudevent.checkIn.CheckInReq;
import com.welian.beans.cloudevent.checkIn.CheckInResp;
import com.welian.beans.cloudevent.coupon.CouponReq;
import com.welian.beans.cloudevent.event.EventPara;
import com.welian.beans.cloudevent.event.SaveEventReq;
import com.welian.beans.cloudevent.event.SaveEventResp;
import com.welian.beans.cloudevent.project.BPResp;
import com.welian.beans.cloudevent.project.ProjectBaseReq;
import com.welian.beans.cloudevent.project.ProjectFinancingInfoReq;
import com.welian.beans.cloudevent.record.CustomSignUpReq;
import com.welian.beans.cloudevent.record.CustomSignUpResp;
import com.welian.beans.cloudevent.record.ProjectSignUpReq;
import com.welian.beans.cloudevent.signup.SearchSignupReq;
import com.welian.beans.cloudevent.signup.UpdateSignupReq;
import com.welian.beans.cloudevent.user.EventServiceReq;
import com.welian.beans.cloudevent.user.UserReq;
import com.welian.beans.cloudevent.usermange.UserMangeReq;
import com.welian.enums.wallet.message.PaySuccessMessage;
import com.welian.service.EventKafkaService;
import com.welian.service.impl.EventServiceImpl;
import com.welian.service.impl.ExtensionLinkServiceImpl;
import com.welian.service.impl.StateCustomModuleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created by GaoXiang on 2018/7/26
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {ApplicationLauncher.class})//classes 指定启动类,加载环境
@Transactional
public class TestEventRecordController extends BaseMockTest {

    @Autowired
    EventServiceImpl eventService;
    @Autowired
    ExtensionLinkServiceImpl extensionLinkService;
    @Autowired
    StateCustomModuleService stateCustomModuleService;
    @Autowired
    EventKafkaService eventKafkaService;

    /**
     * 报名根据uniqueKey获取活动详情
     */
    @Test
    public void getDetailByKey() throws Exception {
        Base64KeyReq req = new Base64KeyReq();
        req.uniqueKey = "NDIxNDU1NjI1MiY4MjUzOA==";
        postSuccess("/signup/getdetailbykey", req);
    }

    /**
     * 小程序获取活动详情
     */
    @Test
    public void getMiniEvent() throws Exception {
        Base64KeyReq req = new Base64KeyReq();
        req.eventId = eventId;
        postSuccess("/signup/miniprogram/event", req);
    }

    /**
     * 报名根据uniqueKey获取活动详情
     */
    @Test
    public void getFormByKey() throws Exception {
        Base64KeyReq req = new Base64KeyReq();
        req.uniqueKey = "NDIxNDU1NjI1MiY4MjUzOA==";
        postSuccess("/signup/getformbykey", req);
    }

    /**
     * 获取报名的项目列表
     */
    @Test
    public void getProjectList() throws Exception {
        EventServiceReq req = new EventServiceReq();
        req.eventId = eventId;
        postSuccess("/signup/projectlist", req);
    }

    /**
     * 项目报名
     */
    @Test
    public void projectSignUp() throws Exception {

        //先创建一个完整版的路演大赛
        SaveEventReq eventReq = new SaveEventReq();
        eventReq.orgId = orgId;
        EventPara event = new EventPara();
        event.eventId = 0;
        event.eventType = 2;
        event.title = "9.1444路演大赛测试用例编写";
        event.lineType = 1;
        event.address = "";
        event.city = new CityReq();
        event.city.id = 179;
        event.city.name = "杭州";
        event.loc = new LocReq();
        event.loc.lng = 120.219375;
        event.loc.lat = 30.259244;
        event.startTime = System.currentTimeMillis();
        event.endTime = System.currentTimeMillis() + 1000 * 24 * 3600;
        event.deadlineTime = System.currentTimeMillis() + 1000 * 23 * 3600;
        event.logo = "nvfa1442457837648_750_558_383.jpg";
        event.state = 2;
        event.detail = "<p>迭代迭代</p>";
        Sponsor sponsor = new Sponsor();
        sponsor.name = "aa";
        sponsor.id = 1160;
        event.sponsors = new ArrayList<>();
        event.sponsors.add(sponsor);
        event.isPromotion = 0;
        event.projectModule = new ProjectModule();
        event.projectModule.isFree = 0;
        event.projectModule.projectFreeCount = 0;
        event.projectModule.projectType = 1;
        event.projectModule.isPublicity = 0;
        event.projectModule.isProjectVerify = 0;
        event.projectModule.isGroupChat = 0;
        event.projectModule.numberStatus=0;
        event.financingService=new EventServiceReq();
        event.financingService.isOpenFinancingService=0;
        event.financingService.groupSetting=0;
        event.financingService.investorMatchCount=20;
        event.originalState=-1;
        eventReq.event = event;
        eventReq.requestSource = 2;
        Integer newEventId = resultData(post("/event/save", eventReq).andReturn(), SaveEventResp.class).eventReq.eventId;

        //报名
        ProjectSignUpReq req = new ProjectSignUpReq();
        req.projectType = 1;
        ProjectBaseReq project = new ProjectBaseReq();
        project.pid = 69545;
        project.name = "6.8项目";
        project.intro = "111";
        project.logo = "uyuv1528254398907_434_1283_87.png";
        project.cityId = 69545;
        project.cityName = "成都";
        project.industryFirstId = 69545;
        project.industryFirstName = "工具";
        project.industrySecondId = 69545;
        project.industrySecondName = "其他";
        project.projectStage = 69545;
        project.remark = "";
        project.website = "";
        req.project = project;
        ProjectFinancingInfoReq financingInfo = new ProjectFinancingInfoReq();
        financingInfo.stage = 2;
        financingInfo.amount = 2222;
        financingInfo.amountUnit = 4;
        financingInfo.share = 22.00;
        req.financingInfo = financingInfo;
        BPResp bp = new BPResp();
        bp.bpId = 27405;
        bp.bpUrl = "";
        bp.bpName = "例会分享（自我管理这四个维度）.pdf";
        bp.bpSize = 0l;
        req.bp = bp;
        List<AddtionalForm> addtionalForms = new ArrayList<>();
        AddtionalForm addtionalForm = new AddtionalForm();
        addtionalForm.required = 1;
        addtionalForm.label = "毕业院校";
        addtionalForm.value = "gg";
        addtionalForm.id = 1159;
        addtionalForm.limit = 100;
        addtionalForm.type = 0;
        addtionalForms.add(addtionalForm);
        AddtionalForm addtionalForm1 = new AddtionalForm();
        addtionalForm1.required = 1;
        addtionalForm1.label = "出生年月日";
        addtionalForm1.value = "gg";
        addtionalForm1.id = 1160;
        addtionalForm1.limit = 100;
        addtionalForm1.type = 0;
        addtionalForms.add(addtionalForm1);
        req.additionalForm = addtionalForms;
        req.type = 1;
        req.extensionLinkId = extensionLinkService.getDefaultLinkByEventId(newEventId).getId();
        UserReq user = new UserReq();
        user.uid = uid;
        user.name = "ggg";
        user.mobile = "15757871092";
        user.position = "ad";
        user.company = "fda";
        req.user = user;
        postSuccess("/signup/projectsign",req);
    }

    /**
     * 创业活动报名
     */
    @Test
    public void customSignUp() throws Exception {
        //先创建一个免费的创业活动
        SaveEventReq req = new SaveEventReq();
        req.orgId = orgId;
        EventPara event = new EventPara();
        event.eventId = 0;
        event.eventType = 1;
        event.title = "9.13333创业活动";
        event.lineType = 0;
        event.address = "address";
        event.city = new CityReq();
        event.city.id = 179;
        event.city.name = "杭州";
        event.loc = new LocReq();
        event.loc.lng = 120.219375;
        event.loc.lat = 30.259244;
        event.startTime = System.currentTimeMillis();
        event.endTime = System.currentTimeMillis() + 1000 * 24 * 3600;
        event.deadlineTime = System.currentTimeMillis() + 1000 * 23 * 3600;
        event.logo = "nvfa1442457837648_750_558_383.jpg";
        event.state = 2;
        event.detail = "<p>迭代迭代</p>";
        Sponsor sponsor = new Sponsor();
        sponsor.name = "aa";
        sponsor.id = 1160;
        event.sponsors = new ArrayList<>();
        event.sponsors.add(sponsor);
        event.isPromotion = 0;
        event.customModule = new CustomModule();
        event.customModule.isCustomVerify = 0;
        event.customModule.isGroupChat = 0;
        event.customModule.numberStatus = 0;
        event.customModule.isPublicity = 0;
        event.customModule.isCharge = 0;
        event.customModule.freeCount = 0;
        req.event = event;
        req.requestSource = 2;
        Integer newEventId = resultData(post("/event/save", req).andReturn(), SaveEventResp.class).eventReq.eventId;

        //报名
        CustomSignUpReq signUpReq = new CustomSignUpReq();
        UserReq user = new UserReq();
        user.uid = uid;
        user.name = "ggg";
        user.mobile = "15757871092";
        user.position = "ad";
        user.company = "fda";
        signUpReq.user = user;
        signUpReq.extensionLinkId = extensionLinkService.getDefaultLinkByEventId(newEventId).getId();
        signUpReq.type = 1;
        List<Ticket> ticketList = new ArrayList<>();
        Ticket ticket = new Ticket();
        AppReq appReq = new AppReq();
        appReq.eventId = newEventId;
        ticket.id = ((TicketResp) eventService.getEventTicket(appReq)).list.get(0).id;
        ticket.buyCount = 1;
        ticketList.add(ticket);
        signUpReq.ticketList = ticketList;
        CustomSignUpResp record = resultData(post("/signup/customsign", signUpReq).andReturn(), CustomSignUpResp.class);
        //签到权限验证
        CheckInReq checkInReq = new CheckInReq();
        checkInReq.eventId = newEventId;
        checkInReq.code = stateCustomModuleService.get(newEventId).getAuthPassword();
        checkInReq.uid = uid;
        postFail("/checkin/authorize", checkInReq, "您已授权");
        //模拟kafka流程
        PaySuccessMessage paySuccessMessage = new PaySuccessMessage();
        paySuccessMessage.setBusinessTradeNo(record.tradeNo);
        eventKafkaService.insertEventTicketOrder(paySuccessMessage);
        //签到
        //这里的ticketId需要调用签到接口返回，不是规格id
        CheckInReq inReq = new CheckInReq();
        inReq.eventId = newEventId;
        inReq.uid = uid;
        CheckInReq checkInReq1 = new CheckInReq();
        checkInReq1.tradeNo = record.tradeNo;
        checkInReq1.ticketId = resultData(post("/checkin/detectpermissionuser", inReq).andReturn(), CheckInResp
                .class).ticketTypes.get(0).ticketList.get(0).id;
        postSuccess("/checkin", checkInReq1);
        //推送条数(只要结束的活动可以，下同)
        UserMangeReq req1 = new UserMangeReq();
        req1.orgId = orgId;
        req1.eventIds = Arrays.asList(newEventId);
        postFail("/user/manage/push/count", req1,"请选择有报名记录的活动");
        //推送
        UserMangeReq req2 = new UserMangeReq();
        req2.orgId = orgId;
        req2.eventIds = Arrays.asList(newEventId);
        req2.content = "测试下咯";
        postFail("/user/manage/push", req2,"请选择有报名记录的活动");
    }

    /**
     * 通过extensionLinkId补上bp
     */
    @Test
    public void updateSignUpBP() throws Exception {
        ExtensionLinkReq req = new ExtensionLinkReq();
        req.extensionLinkId = 2374;
        req.pid = 69545;
        req.bpId = 27405;
        req.bpName = "测试.pdf";
        req.bpUrl = "znkf1513763574165.pdf";
        postSuccess("/signup/updatesignupbp", req);
    }

    /**
     * 通过recordId补上bp
     */
    @Test
    public void updateSignUpBPByRecordId() throws Exception {
        ExtensionLinkReq req = new ExtensionLinkReq();
        req.recordId = 1590911;
        req.pid = 69545;
        req.bpId = 27405;
        req.bpName = "测试.pdf";
        req.bpUrl = "znkf1513763574165.pdf";
        postSuccess("/signup/updateSignUpBPByRecordId", req);
    }

    /**
     * 批量修改活动报名记录状态
     */
    @Test
    public void updateRecordStatus() throws Exception {
        UpdateSignupReq req = new UpdateSignupReq();
        req.state = 0;
        req.signUpList = Arrays.asList(1590912);
        req.eventId = eventId;
        postSuccess("/signup/updatestatus", req);
    }

    /**
     * 云活动平台-搜索报名记录列表
     */
    @Test
    public void searchRecord() throws Exception {
        SearchSignupReq req = new SearchSignupReq();
        req.keyword = "";
        req.page = 1;
        req.size = 10;
        req.type = 0;
        req.eventId = eventId;
        postSuccess("/signup/search", req);
    }


    /**
     * 云活动平台-报名表单-票
     */
    @Test
    public void tickets() throws Exception {
        SearchSignupReq req = new SearchSignupReq();
        req.eventId = eventId;
        postSuccess("/signup/tickets", req);
    }

    /**
     * 是否报过名
     */
    @Test
    public void existRecord() throws Exception {
        AppReq req = new AppReq();
        req.eventId = eventId;
        req.uid = uid;
        postSuccess("/signup/existrecord", req);
    }

    /**
     * 微信根据unionId获取用户信息
     */
    @Test
    public void getUserByUnionId() throws Exception {
        UserReq req = new UserReq();
        req.unionId = "oUMiajmvMIu72GEY_prf8ANhxGR0";
        postSuccess("/signup/unionuser", req);
    }

    /**
     * 获取优惠券信息
     */
    @Test
    public void coupons() throws Exception {
        CouponReq req = new CouponReq();
        req.tradeNo = "1534747053020UWEC";
        req.eventId = eventId;
        req.type = 1;
        postSuccess("/signup/coupons", req);
    }

}
