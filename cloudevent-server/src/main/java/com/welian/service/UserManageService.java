package com.welian.service;

import org.sean.framework.bean.BaseResult;
import com.welian.beans.account.User;
import com.welian.beans.cloudevent.event.UserEventSearchPara;
import com.welian.beans.cloudevent.org.OrderChannelReq;
import com.welian.beans.cloudevent.org.OrgInfoResp;
import com.welian.beans.cloudevent.record.RecordsUserResp;
import com.welian.beans.cloudevent.user.UserResp;
import com.welian.beans.cloudevent.usermange.UserMangeReq;
import com.welian.beans.cloudevent.usermange.UserMangeResp;
import com.welian.client.wallet.PayClient;
import com.welian.client.wallet.WalletClient;
import org.sean.framework.code.ResultCodes;
import com.welian.config.CloudEventConfig;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.enums.wallet.SystemDictionary;
import com.welian.enums.wallet.beans.AlipayResp;
import com.welian.enums.wallet.beans.TradeReq;
import com.welian.enums.wallet.beans.TradeResp;
import com.welian.enums.wallet.beans.WalletReq;
import org.sean.framework.exception.CodeException;
import com.welian.mapper.EventMapper;
import com.welian.mapper.EventRecordMapper;
import com.welian.mapper.EventRecordUserMapper;
import com.welian.mapper.SmsHistoryMapper;
import com.welian.mapper.SmsManageMapper;
import com.welian.mapper.SmsManageOrderMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventExample;
import com.welian.pojo.EventRecord;
import com.welian.pojo.EventRecordUser;
import com.welian.pojo.EventRecordUserExample;
import com.welian.pojo.SmsHistory;
import com.welian.pojo.SmsHistoryExample;
import com.welian.pojo.SmsManage;
import com.welian.pojo.SmsManageExample;
import com.welian.pojo.SmsManageOrder;
import com.welian.service.impl.EventServiceImpl;
import com.welian.service.impl.EventSmsServiceImpl;
import com.welian.service.impl.OrgServiceImpl;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import com.welian.utils.ExceptionUtil;
import com.welian.utils.OrderNoGenerator;
import com.welian.utils.PagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhaopu on 2018/6/22.
 */
@Service("userManageService")
public class UserManageService {

    @Autowired
    private SmsHistoryMapper smsHistoryMapper;
    @Autowired
    private SmsManageMapper smsManageMapper;
    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private CloudEventConfig cloudEventConfig;
    @Autowired
    private EventSmsServiceImpl eventSmsService;
    @Autowired
    private EventRecordMapper eventRecordMapper;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private PayClient payClient;
    @Autowired
    private WalletClient walletClient;
    @Autowired
    private SmsManageOrderMapper smsManageOrderMapper;
    @Autowired
    private EventRecordUserMapper eventRecordUserMapper;
    @Autowired
    private OrgServiceImpl orgService;


    /**
     * 群发历史
     */
    public Object smsHistory(UserMangeReq req) {
        UserMangeResp respR = new UserMangeResp();
        respR.list = new ArrayList<>();
        SmsHistoryExample example = new SmsHistoryExample();
        example.setLimit(req.size);
        example.setOffset(PagingUtil.getStart(req.page, req.size));
        example.createCriteria().andOrgIdEqualTo(req.orgId);
        example.setOrderByClause("create_time desc");
        List<SmsHistory> smsHistorys = smsHistoryMapper.selectByExample(example);

        if (smsHistorys != null && smsHistorys.size() > 0) {
            for (SmsHistory smsHistory : smsHistorys) {
                UserMangeResp resp = new UserMangeResp();
                resp.time = smsHistory.getCreateTime();

                String[] array = smsHistory.getEventTitles().split("&&&&&&&&");
                for (String str : array) {
                    if (resp.object == null || resp.object.length() == 0) {
                        resp.object = "[" + str + "]";
                    }else {
                        resp.object = resp.object + "[" + str + "]";
                    }
                }
                resp.content = smsHistory.getContent();
                respR.list.add(resp);
            }
        }

        SmsHistoryExample exampleCount = new SmsHistoryExample();
        exampleCount.createCriteria().andOrgIdEqualTo(req.orgId);
        Long smsHistoryCount = smsHistoryMapper.countByExample(exampleCount);
        respR.count = smsHistoryCount;
        return respR;
    }

    /**
     * 获取机构下报名过的活动列表
     */
    public Object signupEvent(Integer orgId) {
        UserMangeResp respR = new UserMangeResp();
        respR.list = new ArrayList<>();
        List<UserMangeResp> respList = eventService.getSignupEvent(orgId);
        if (respList != null && respList.size() > 0) {
            for (UserMangeResp resp : respList) {
                respR.list.add(resp);
            }
        }
        return respR;
    }

    /**
     * 短信剩余条数
     *
     * @param orgId
     * @return
     */
    public Object sms(Integer orgId) {
        UserMangeResp respR = new UserMangeResp();
        SmsManageExample example = new SmsManageExample();
        example.createCriteria().andOrgIdEqualTo(orgId);
        List<SmsManage> smsManage =  smsManageMapper.selectByExample(example);
        if (smsManage != null && smsManage.size() > 0) {
            respR.smsCount = smsManage.get(0).getSmsCount();
            respR.price = cloudEventConfig.getSms_price();
        }else {
            respR.smsCount = 0;
            respR.price = cloudEventConfig.getSms_price();
        }

        return respR;
    }


    /**
     * 推送
     *
     * @param req
     * @return
     */
    @Transactional
    public Object push(UserMangeReq req) {

        UserMangeResp resp = new UserMangeResp();

        int contentR1 = req.content.indexOf("【");
        int contentR2 = req.content.indexOf("】");
        if(contentR1 != -1 || contentR2 != -1){
            throw ExceptionUtil.createParamException("短信中不得包含特殊字符【】");
        }

        //判断机构是否认证
        this.checkOrg(req.orgId);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 21);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date m21 = c.getTime();

        if (System.currentTimeMillis() > m21.getTime() && System.currentTimeMillis() < m21.getTime() + 39600000) {
            throw ExceptionUtil.createParamException("当前时间段[晚上21：00至早上8：00]无法推送");
        }

        Map<String,List<String>> listMap = this.getPhoneList(req);
        List<String> uidList = listMap.get("uids");
        List<String> phoneList  = listMap.get("phones");

        List<Integer> uids = new ArrayList<>();
        for (String uidStr: uidList) {
            uids.add(Integer.valueOf(uidStr));
        }

        //记录并 扣除短信条数
        SmsManageExample example = new SmsManageExample();
        example.createCriteria().andOrgIdEqualTo(req.orgId);
        List<SmsManage> smsManageList = smsManageMapper.selectByExample(example);

        if (smsManageList != null && smsManageList.size() > 0) {
            if (smsManageList.get(0).getSmsCount() < phoneList.size()) {
                throw ExceptionUtil.createParamException("短信余额不足,请先充值短信");
            }
            SmsManage smsManage = new SmsManage();
            smsManage.setId(smsManageList.get(0).getId());
            smsManage.setSmsCount(smsManageList.get(0).getSmsCount() - phoneList.size());
            smsManage.setModifyTime(System.currentTimeMillis());
            int result1 = smsManageMapper.updateByPrimaryKeySelective(smsManage);
            if (result1 == 0) {
                throw ExceptionUtil.createParamException("短信剩余条数更新失败");
            }
        }else {
            throw ExceptionUtil.createParamException("请先充值短信");
        }


        EventExample eventExample = new EventExample();
        eventExample.createCriteria().andIdIn(req.eventIds);
        List<Event> events = eventMapper.selectByExample(eventExample);
        String event_ids = "";
        String event_titles = "";
        if (events != null && events.size() > 0) {
            for (Event event : events) {
                if (event_ids.length() == 0) {
                    event_ids = event.getId().toString();
                }else {
                    event_ids = event_ids + "," + event.getId();
                }
                if (event_titles.length() == 0) {
                    event_titles = event.getTitle();
                }else {
                    event_titles = event_titles + "&&&&&&&&" + event.getTitle();
                }
            }
        }

        SmsHistory record = new SmsHistory();
        record.setOrgId(req.orgId);
        record.setContent(req.content);
        record.setEventIds(event_ids);
        record.setEventTitles(event_titles);
        record.setPushSmsCount(phoneList.size());
        record.setCreateTime(System.currentTimeMillis());
        record.setModifyTime(System.currentTimeMillis());
        int result = smsHistoryMapper.insertSelective(record);
        if (result == 0) {
            throw ExceptionUtil.createParamException("短信发送历史插入失败");
        }

        //发送短信 并返回发送数量
        eventSmsService.sendSms(req.content,phoneList,uids,req.eventIds);

        resp.count = Long.valueOf(phoneList.size());

        return resp;
    }

    /**
     * 推送条数
     *
     * @param req
     * @return
     */
    public Object pushCount(UserMangeReq req) {
        UserMangeResp resp = new UserMangeResp();

        //判断机构是否认证
        this.checkOrg(req.orgId);

        Map<String,List<String>> listMap = this.getPhoneList(req);
        List<String> phoneList  = listMap.get("phones");
        resp.count = Long.valueOf(phoneList.size());

        return resp;
    }



    public Map<String,List<String>> getPhoneList(UserMangeReq req) {
        //只取审核通过的的报名列表 并且关注过机构的的用户
        UserEventSearchPara eventSearchReq = new UserEventSearchPara();
        eventSearchReq.orgId = req.orgId;
        eventSearchReq.eventIds = req.eventIds;
        eventSearchReq.nowTime = System.currentTimeMillis();
        List<EventRecord> eventRecordList = eventRecordMapper.selectUserSignUpEventRecordAndOrgList(eventSearchReq);
        if (eventRecordList == null || eventRecordList.size() == 0) {
            throw ExceptionUtil.createParamException("请选择有报名记录的活动");
        }

        //获取需要发送的电话号码
        Set<String> phones = new HashSet<>();
        //同一个用户用多个项目报名同一活动只发一条短信
        Set<String> uids=new HashSet<>();
        List<Integer> recordIds=new ArrayList<>();
        for(EventRecord eventRecord:eventRecordList){
            if(NumberUtil.isInValidId(eventRecord.getUid())){
                continue;
            }
            recordIds.add(eventRecord.getId());
            uids.add(eventRecord.getUid().toString());
        }
        EventRecordUserExample recordUserExample=new EventRecordUserExample();
        recordUserExample.createCriteria().andEventRecordIdIn(recordIds);
        List<EventRecordUser> recordUsers=eventRecordUserMapper.selectByExample(recordUserExample);

        for(EventRecordUser recordUser:recordUsers){
            phones.add(recordUser.getPhone()) ;
        }

        List<String> logUidList = new ArrayList<>(uids);
        List<String>  phoneList   = new ArrayList<>(phones);

        Map<String,List<String>> listMap = new HashMap<>();
        listMap.put("uids" , logUidList);
        listMap.put("phones", phoneList);

        return listMap;
    }


    /**
     * 获取用户管理列表
     *
     * @param req
     * @return
     */
    public Object userManage(UserMangeReq req) {
        RecordsUserResp response = new RecordsUserResp();
        response.list = new ArrayList<>();
        req.phone = StringUtil.isEmpty(req.phone)? null : req.phone;
      
        //判断机构是否认证
        this.checkOrg(req.orgId);

        //只取审核通过的的报名列表 的用户
        UserEventSearchPara eventSearchReq = new UserEventSearchPara();
        eventSearchReq.page = PagingUtil.getStart(req.page, req.size);
        eventSearchReq.orgId = req.orgId;
        eventSearchReq.size = req.size;
        eventSearchReq.phone = req.phone;
        if (req.name != null) {
            eventSearchReq.name = "%" + req.name + "%";
        }
        List<Integer> uidList = eventRecordMapper.selectUserRecordAndOrgList(eventSearchReq);

        UserEventSearchPara eventSearchReq1 = new UserEventSearchPara();
        eventSearchReq1.orgId = req.orgId;
        eventSearchReq1.phone = req.phone;
        if (req.name != null) {
            eventSearchReq1.name = "%" + req.name + "%";
        }
        List<Integer> count = eventRecordMapper.selectUserRecordAndOrgCount(eventSearchReq1);

        if (uidList.size() != 0) {
            Map<Integer, User> userMap = userService.getUserInfoListByIds(uidList);
            for (Map.Entry<Integer, User> entry : userMap.entrySet()) {
                User user = entry.getValue();
                UserResp userResp = new UserResp();
                userResp.uid = user.id;
                userResp.name = user.name;
                userResp.mobile = user.phone;
                userResp.company = user.company;
                userResp.position = user.position;

                response.list.add(userResp);
            }
        }

        response.count = count.size();
        return response;
    }


    /**
     * 查看报名记录-用户
     *
     * @param req
     * @return
     */
    public Object record(UserMangeReq req) {
        UserMangeResp respR = new UserMangeResp();
        respR.list = new ArrayList<>();
        UserEventSearchPara eventSearchReq = new UserEventSearchPara();
        eventSearchReq.page = PagingUtil.getStart(req.page, req.size);
        eventSearchReq.orgId = req.orgId;
        eventSearchReq.uid = req.uid;
        eventSearchReq.size = req.size;
        List<UserMangeResp> userMangeResps = eventRecordMapper.selectUserRecord(eventSearchReq);

        respR.list = userMangeResps;

        return respR;
    }


    /**
     * 短信充值
     *
     * @param req
     * @return
     */
    public Object smspay(UserMangeReq req) {
        UserMangeResp respR = new UserMangeResp();

        //判断机构是否认证
        this.checkOrg(req.orgId);

        if (req.payType != SystemDictionary.PayType.ALIPAY.getCode()) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_BIZ, "暂不支持其他类型支付");
        }

        OrderNoGenerator generator = new OrderNoGenerator(1, 18);
        String orderNo =  generator.generatorOrderNo();

        //创建订单
        TradeReq tradeReq = new TradeReq();
        tradeReq.businessTradeNo = orderNo; //业务系统订单号
        tradeReq.detail = "机构短信充值";//交易详情
        tradeReq.sourceUid = req.uid;//付款用户
        tradeReq.module = SystemDictionary.TradeModel.ORGSMS.getCode();//交易模块
        tradeReq.totalAmount = String.valueOf(req.smsCount * cloudEventConfig.getSms_price());//交易金额
        tradeReq.channel = SystemDictionary.TradeChannel.WEB_ADMIN.getCode();//交易渠道
        tradeReq.triggerUid = req.uid;//发起交易uid
        tradeReq.intro = "机构短信充值";//交易描述
        BaseResult<TradeResp> tradeRespBaseResult = payClient.createOrder(tradeReq);
        if (!tradeRespBaseResult.isSuccess()) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_BIZ, tradeRespBaseResult.getErrormsg());
        }
        TradeResp tradeResp = tradeRespBaseResult.getData();


        WalletReq walletReq = new WalletReq();
        walletReq.tradeNo = orderNo;
        walletReq.returnUrl = req.returnUrl;
        BaseResult<AlipayResp> alipayRespBaseResult = walletClient.pcpay(walletReq);
        if (!alipayRespBaseResult.isSuccess()) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_BIZ, alipayRespBaseResult.getErrormsg());
        }
        AlipayResp alipayResp = alipayRespBaseResult.getData();
        respR.tradeNo = tradeResp.tradeNo;
        respR.form = alipayResp.form;


        SmsManageOrder record = new SmsManageOrder();
        record.setOrgId(req.orgId);
        record.setTradeNo(tradeReq.businessTradeNo);
        record.setWalletTradeNo(tradeResp.tradeNo);
        record.setUid(req.uid);
        record.setSmsCount(req.smsCount);
        record.setPrice(req.smsCount * cloudEventConfig.getSms_price());
        record.setState(SqlEnum.SmsManageState.WAIT.getValue());
        record.setCreateTime(System.currentTimeMillis());
        record.setModifyTime(System.currentTimeMillis());
        int result1 = smsManageOrderMapper.insertSelective(record);
        if (result1 == 0) {
            throw ExceptionUtil.createParamException("短信充值记录插入失败");
        }

        return respR;
    }


    public void checkOrg(Integer orgId) {
        OrderChannelReq req = new OrderChannelReq();
        req.orgId = orgId;
        OrgInfoResp resp = orgService.getInfoById(req);
        if (!resp.auth.verifyStatus.equals(SqlEnum.OrgVerifyStatus.CHECK_PASS.getValue())) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_NOT_SUPPORTED, "机构暂未认证");
        }

    }

}
