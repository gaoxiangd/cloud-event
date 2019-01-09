package com.welian.service;

import com.welian.beans.account.User;
import com.welian.beans.cloudevent.CityReq;
import com.welian.beans.cloudevent.CustomModule;
import com.welian.beans.cloudevent.Ticket;
import com.welian.beans.cloudevent.TicketInfo;
import com.welian.beans.cloudevent.checkIn.CheckInReq;
import com.welian.beans.cloudevent.checkIn.CheckInResp;
import com.welian.beans.cloudevent.event.EventPara;
import com.welian.beans.cloudevent.user.UserResp;
import com.welian.config.CloudEventConfig;
import com.welian.enums.cloudevent.ParamEnum;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.mapper.EventRecordMapper;
import com.welian.mapper.EventSignAuthMapper;
import com.welian.mapper.EventStateCustomMapper;
import com.welian.mapper.EventTicketOrderMapper;
import com.welian.mapper.ExtensionLinkMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventRecord;
import com.welian.pojo.EventRecordExample;
import com.welian.pojo.EventSignAuth;
import com.welian.pojo.EventSignAuthExample;
import com.welian.pojo.EventStateCustom;
import com.welian.pojo.EventStateCustomExample;
import com.welian.pojo.EventTicketOrder;
import com.welian.pojo.EventTicketOrderExample;
import com.welian.pojo.ExtensionLink;
import com.welian.pojo.ExtensionLinkExample;
import com.welian.service.impl.EventServiceImpl;
import com.welian.service.impl.EventStatisticServiceImpl;
import org.sean.framework.util.Logger;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import com.welian.utils.DateUtil;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zhaopu on 2017/12/4.
 */
@Service
public class CheckInService {
    private static final Logger logger = Logger.newInstance(CheckInService.class);

    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private EventStateCustomMapper eventStateCustomMapper;
    @Autowired
    private EventSignAuthMapper eventSignAuthMapper;
    @Autowired
    private EventRecordMapper eventRecordMapper;
    @Autowired
    private EventTicketOrderMapper eventTicketOrderMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CommodityRemoteService commodityRemoteService;
    @Autowired
    private CloudEventConfig cloudEventConfig;
    @Autowired
    private ExtensionLinkMapper extensionLinkMapper;
    @Autowired
    private EventStatisticServiceImpl eventStatisticService;
    /**
     * 签到-主办方签到-员工提交签到权限
     */
    @Transactional
    public Object authorize(CheckInReq req) {
        CheckInResp resp = new CheckInResp();

        Event event = eventService.getEvent(req.eventId);
        if (event == null) {
            throw ExceptionUtil.createParamException("活动信息异常，请重试");
        }

        EventStateCustomExample eventStateCustomExample = new EventStateCustomExample();
        eventStateCustomExample.createCriteria().andEventIdEqualTo(req.eventId);
        List<EventStateCustom> eventStateCustoms = eventStateCustomMapper.selectByExample(eventStateCustomExample);
        if (StringUtil.isEmpty(eventStateCustoms)) {
            throw ExceptionUtil.createParamException("签到授权码错误");
        }

        if (!req.code.equals(eventStateCustoms.get(0).getAuthPassword())) {
            throw ExceptionUtil.createParamException("签到授权码错误");
        }

        EventSignAuthExample example = new EventSignAuthExample();
        example.createCriteria().andUidEqualTo(req.uid).andEventIdEqualTo(req.eventId);
        List<EventSignAuth> eventSignAuthList = eventSignAuthMapper.selectByExample(example);
        if (eventSignAuthList == null || eventSignAuthList.size() == 0) {
            EventSignAuth record = new EventSignAuth();
            record.setEventId(req.eventId);
            record.setUid(req.uid);
            record.setStatus(SqlEnum.EventSignAuthStatus.AUTH.getByteValue());
            record.setCreateTime(DateUtil.getNowDate());
            record.setModifyTime(DateUtil.getNowDate());
            Integer isSuccess = eventSignAuthMapper.insertSelective(record);
            if (isSuccess == 0) {
                throw ExceptionUtil.createParamException("授权失败");
            }
        }else {
            throw ExceptionUtil.createParamException("您已授权");
        }

        return resp;
    }


    /**
     * 签到-用户扫码
     */
    @Transactional
    public Object detectPermissionUser(CheckInReq req) {
        CheckInResp resp = new CheckInResp();

        Event event = eventService.getEvent(req.eventId);
        if (event == null) {
            throw ExceptionUtil.createParamException("活动信息异常，请重试");
        }

        //event
        resp.event = converseEvent(event);

        User user = userService.getUserInfoById(req.uid);
        if (user != null) {
            //user
            resp.user = converseUser(user);

            //根据uid和activeid取报名记录，如果未取到，则再根据微信号和活动id，去尝试获取记录
            EventRecordExample eventRecordExample = new EventRecordExample();
            eventRecordExample.createCriteria().andEventIdEqualTo(req.eventId).andUidEqualTo(req.uid).
                    andTicketLockEqualTo(SqlEnum.TicketLockType.TYPE_NORMAL.getValue());
            List<EventRecord> eventRecordList = eventRecordMapper.selectByExample(eventRecordExample);
            if (eventRecordList == null || eventRecordList.size() == 0) {
                //未报名活动
                resp.state = ParamEnum.EventSignInfoState.EVENT_SIGN_NO_ORDER.getValue();
                resp.msg = ParamEnum.EventSignInfoState.EVENT_SIGN_NO_ORDER.getDesc();
                return resp;
            }else {
                EventRecord eventRecord = eventRecordList.get(eventRecordList.size() - 1);
                //活动状态
                long time = System.currentTimeMillis() - event.getEndTime();
                if (time > 0) {
                    resp.state = ParamEnum.EventSignInfoState.EVENT_SIGN_ACTIVE_OVER.getValue();
                    resp.msg = ParamEnum.EventSignInfoState.EVENT_SIGN_ACTIVE_OVER.getDesc();
                    return resp;
                } else {
                    if (event.getState() == SqlEnum.EventStateType.TYPE_EVENT_DELETED.getByteValue()) {
                        resp.state = ParamEnum.EventSignInfoState.EVENT_SIGN_CANCEL_ORDER.getValue();
                        resp.msg = ParamEnum.EventSignInfoState.EVENT_SIGN_CANCEL_ORDER.getDesc();
                        return resp;
                    }else if (eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY.getByteValue()){
                        resp.state = ParamEnum.EventSignInfoState.EVENT_SIGN_WAIITING.getValue();
                        resp.msg = ParamEnum.EventSignInfoState.EVENT_SIGN_WAIITING.getDesc();
                        return resp;
                    }else if  (eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_VERIFY_FAILED.getByteValue()){
                        resp.state = ParamEnum.EventSignInfoState.EVENT_SIGN_REFUSE.getValue();
                        resp.msg = ParamEnum.EventSignInfoState.EVENT_SIGN_REFUSE.getDesc() + "\r\n理由:" + eventRecord.getReason();
                        return resp;
                    }else if  (eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_USER_DELETED.getByteValue() ||
                            eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_PLATFORM_DELETED.getByteValue()){
                        //未报名活动
                        resp.state = ParamEnum.EventSignInfoState.EVENT_SIGN_NO_ORDER.getValue();
                        resp.msg = ParamEnum.EventSignInfoState.EVENT_SIGN_NO_ORDER.getDesc();
                        return resp;
                    }
                }
                //ticket
                resp.ticketTypes = converseTicket(event,eventRecordList);
          }
        }

        return resp;
    }


    /**
     * 签到-活动方扫码
     */
    @Transactional
    public Object detectPermissionSponsor(CheckInReq req) {
        CheckInResp resp = new CheckInResp();

        EventRecordExample eventRecordExample = new EventRecordExample();
        eventRecordExample.createCriteria().andTradeNoEqualTo(req.tradeNo).
                andTicketLockEqualTo(SqlEnum.TicketLockType.TYPE_NORMAL.getValue());
        List<EventRecord> eventRecordList = eventRecordMapper.selectByExample(eventRecordExample);
        if (eventRecordList == null || eventRecordList.size() == 0) {
            resp.state = ParamEnum.EventSignInfoState.EVENT_SIGN_TICKET_ERROR.getValue();
            resp.msg = ParamEnum.EventSignInfoState.EVENT_SIGN_TICKET_ERROR.getDesc();
            return resp;
        }else {
            EventRecord eventRecord = eventRecordList.get(eventRecordList.size() - 1);
            Event event = eventService.getEvent(eventRecord.getEventId());
            if (event == null) {
                resp.state = ParamEnum.EventSignInfoState.EVENT_SIGN_ACTIVE_ERROR.getValue();
                resp.msg = ParamEnum.EventSignInfoState.EVENT_SIGN_ACTIVE_ERROR.getDesc();
                return resp;
            }else {
                //event
                resp.event = converseEvent(event);

                //活动状态
                long time = System.currentTimeMillis() - event.getEndTime();
                if (time > 0) {
                    resp.state = ParamEnum.EventSignInfoState.EVENT_SIGN_ACTIVE_OVER.getValue();
                    resp.msg = ParamEnum.EventSignInfoState.EVENT_SIGN_ACTIVE_OVER.getDesc();
                    return resp;
                } else {
                    if (event.getState() == SqlEnum.EventStateType.TYPE_EVENT_DELETED.getByteValue()) {
                        resp.state = ParamEnum.EventSignInfoState.EVENT_SIGN_CANCEL_ORDER.getValue();
                        resp.msg = ParamEnum.EventSignInfoState.EVENT_SIGN_CANCEL_ORDER.getDesc();
                        return resp;
                    } else {
                        if (event.getState() == SqlEnum.EventStateType.TYPE_EVENT_DELETED.getByteValue()) {
                            resp.state = ParamEnum.EventSignInfoState.EVENT_SIGN_CANCEL_ORDER.getValue();
                            resp.msg = ParamEnum.EventSignInfoState.EVENT_SIGN_CANCEL_ORDER.getDesc();
                            return resp;
                        }else if (eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY.getByteValue()){
                            resp.state = ParamEnum.EventSignInfoState.EVENT_SIGN_WAIITING.getValue();
                            resp.msg = ParamEnum.EventSignInfoState.EVENT_SIGN_WAIITING.getDesc();
                            return resp;
                        }else if  (eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_VERIFY_FAILED.getByteValue()){
                            resp.state = ParamEnum.EventSignInfoState.EVENT_SIGN_REFUSE.getValue();
                            resp.msg = ParamEnum.EventSignInfoState.EVENT_SIGN_REFUSE.getDesc() + "\r\n理由:" + eventRecord.getReason();
                            return resp;
                        }else if  (eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_USER_DELETED.getByteValue() ||
                                eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_PLATFORM_DELETED.getByteValue()){
                            //未报名活动
                            resp.state = ParamEnum.EventSignInfoState.EVENT_SIGN_NO_ORDER.getValue();
                            resp.msg = ParamEnum.EventSignInfoState.EVENT_SIGN_NO_ORDER.getDesc();
                            return resp;
                        }

                    }
                }

                //user
                if (req.uid != null && req.uid != 0) {
                    User user = userService.getUserInfoById(eventRecord.getUid());
                    if (user == null) {
                        throw ExceptionUtil.createParamException("用户不存在");
                    }
                    resp.user = converseUser(user);

                    EventSignAuthExample eventSignAuthExample = new EventSignAuthExample();
                    eventSignAuthExample.createCriteria().andEventIdEqualTo(event.getId()).andUidEqualTo(req.uid);
                    List<EventSignAuth> eventSignAuths = eventSignAuthMapper.selectByExample(eventSignAuthExample);
                    if (eventSignAuths == null || eventSignAuths.size() == 0) {
                        resp.workerStatus = ParamEnum.EventSignAuthState.WORKER_SIGN_AUTH_FALSAE.getValue();
                    }else {
                        EventSignAuth eventSignAuth = eventSignAuths.get(0);
                        if (eventSignAuth.getStatus() == SqlEnum.EventSignAuthStatus.AUTH.getByteValue()) {
                            resp.workerStatus = ParamEnum.EventSignAuthState.WORKER_SIGN_AUTH_TRUE.getValue();
                        }else {
                            resp.workerStatus = ParamEnum.EventSignAuthState.WORKER_SIGN_AUTH_FALSAE.getValue();
                        }
                    }
                }

                //ticket
                resp.ticketTypes = converseTicket(event,eventRecordList);
            }
        }

        return resp;
    }



    /**
     * 签到
     */
    @Transactional
    public Object checkin(CheckInReq req) {
        CheckInResp resp = new CheckInResp();

        EventRecordExample eventRecordExample = new EventRecordExample();
        eventRecordExample.createCriteria().andTradeNoEqualTo(req.tradeNo);
        List<EventRecord> eventRecordList = eventRecordMapper.selectByExample(eventRecordExample);
        if (eventRecordList == null || eventRecordList.size() == 0) {
            throw ExceptionUtil.createParamException("未报名该活动");
        }

        EventRecord eventRecord = eventRecordList.get(0);
        Event event = eventService.getEvent(eventRecord.getEventId());
        if (event == null) {
            throw ExceptionUtil.createParamException("活动信息不存在");
        }

        //活动状态
        long time = System.currentTimeMillis() - event.getEndTime();
        if (time > 0) {
            throw ExceptionUtil.createParamException("活动已结束，无法签到");
        } else {
            if (eventRecord.getState() != SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getByteValue()) {
                throw ExceptionUtil.createParamException("该用户等待审核或者审核拒绝无法签到");
            }
        }

        //是否已经签到
        EventTicketOrderExample example = new EventTicketOrderExample();
        if(!SqlEnum.IsImport.TYPE_IS_IMPORT.getValue().equals(eventRecord.getIsImport())) {
            if (!NumberUtil.isValidId(req.ticketId)) {
                throw ExceptionUtil.createParamException("票id错误");
            }
            example.createCriteria().andEventIdEqualTo(event.getId()).andEventRecordIdEqualTo(eventRecord.getId())
                    .andCommodityDetailIdEqualTo(req.ticketId);
        }else{
            if(StringUtil.isEmpty(req.ticketNo)){
                throw ExceptionUtil.createParamException("ticketNo异常");
            }
            example.createCriteria().andEventIdEqualTo(event.getId()).andEventRecordIdEqualTo(eventRecord.getId())
                    .andTicketNoEqualTo(req.ticketNo);
        }
            List<EventTicketOrder> eventTicketOrders = eventTicketOrderMapper.selectByExample(example);
            if (eventTicketOrders == null || eventTicketOrders.size() == 0) {
                throw ExceptionUtil.createParamException("未报名该活动");
            }

            EventTicketOrder eventTicketOrder = eventTicketOrders.get(0);
            if (eventTicketOrder.getSignState() == SqlEnum.EventSignStatus.EVENT_SIGN_TRUE.getValue()) {
                throw ExceptionUtil.createParamException("已签到该活动");
            }
            if (eventTicketOrder.getTicketState() == SqlEnum.EventTicketState.EVENT_TICKET_TRUE.getValue()) {
                throw ExceptionUtil.createParamException("活动票异常");
            }

        //签到
        EventTicketOrder record = new EventTicketOrder();
        record.setSignState(SqlEnum.EventSignStatus.EVENT_SIGN_TRUE.getValue());
        record.setSignTime(System.currentTimeMillis());
        record.setModifyTime(System.currentTimeMillis());
        if( eventTicketOrderMapper.updateByExampleSelective(record,example)==0){
            throw ExceptionUtil.createParamException("签到状态更新失败");
        }
        //3.7.8 之后由于导入数据 支持ticketId和ticketNo两种
            eventStatisticService.countChangeAfterSignIn(req.ticketNo,req.ticketId, 1);

        resp.couponStatus = event.getCouponStatus();

        return resp;
    }


    /**
     * 签到-签到管理
     *
     * @param req
     * @return
     */
    public Object manage(CheckInReq req) {
        CheckInResp resp = new CheckInResp();
        EventStateCustomExample example = new EventStateCustomExample();
        example.createCriteria().andEventIdEqualTo(req.eventId);
        List<EventStateCustom> eventStateCustomList = eventStateCustomMapper.selectByExample(example);
        if (eventStateCustomList == null || eventStateCustomList.size() == 0) {
            throw ExceptionUtil.createParamException("活动信息错误");
        }

        EventStateCustom eventStateCustom = eventStateCustomList.get(0);
        resp.code = eventStateCustom.getAuthPassword();
        resp.signinUrl = cloudEventConfig.getLink_signup_prefix() + "s?e=" + req.eventId;

        return resp;
    }


    private UserResp converseUser(User user) {
        UserResp userResp = new UserResp();
        userResp.uid = user.id;
        userResp.name = user.name;
        userResp.position = user.position;
        userResp.company = user.company;
        userResp.mobile = user.phone;
        userResp.company = user.company;
        userResp.avatar = user.avatar;

        return userResp;
    }

    private EventPara converseEvent(Event event) {
        EventPara eventPara = new EventPara();
        eventPara.id = event.getId();
        eventPara.title = event.getTitle();
        eventPara.startTime = event.getStartTime();
        if (event.getCityId() != 0) {
            eventPara.city = new CityReq();
            eventPara.city.id = event.getCityId();
            eventPara.city.name = event.getCityName();
        }

        EventStateCustomExample eventStateCustomExample = new EventStateCustomExample();
        eventStateCustomExample.createCriteria().andEventIdEqualTo(event.getId());
        List<EventStateCustom> eventStateCustoms = eventStateCustomMapper.selectByExample(eventStateCustomExample);
        if (eventStateCustoms != null && eventStateCustoms.size() != 0) {
            EventStateCustom eventStateCustom = eventStateCustoms.get(0);
            eventPara.customModule = new CustomModule();
            eventPara.customModule.isCharge = eventStateCustom.getCostType().intValue();
        }

        ExtensionLinkExample example = new ExtensionLinkExample();
        example.createCriteria().andEventIdEqualTo(event.getId());
        List<ExtensionLink> extensionLinkList = extensionLinkMapper.selectByExample(example);
        if (extensionLinkList != null && extensionLinkList.size() > 0) {
            eventPara.commonUrl = extensionLinkList.get(0).getLinkUrlCommon();//分享Url
        }

        return eventPara;
    }


    private List<Ticket> converseTicket(Event event , List<EventRecord> eventRecordList) {
        List<Ticket> ticketTypes = new ArrayList<>();

        List<Ticket> ticketModules = commodityRemoteService.converseToTicket(event.getCommodityId());
        if (ticketModules != null && ticketModules.size() > 0) {
            for (Ticket ticket : ticketModules) { //票种
                Ticket ticketR = new Ticket();
                ticketR.ticketList = new ArrayList<>();
                ticketR.title = ticket.title;
                ticketR.intro = ticket.intro;

                Integer size = 0;
                for (EventRecord eventRecord :eventRecordList) { //报名记录
                    if (eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getByteValue()) {
                        EventTicketOrderExample eventTicketOrderExample = new EventTicketOrderExample();
                        eventTicketOrderExample.createCriteria().andEventIdEqualTo(event.getId()).
                                andEventRecordIdEqualTo(eventRecord.getId()).andTicketIdEqualTo(ticket.id);
                        List<EventTicketOrder> eventTicketOrders = eventTicketOrderMapper.selectByExample(eventTicketOrderExample);

                        if (eventTicketOrders != null && eventTicketOrders.size() > 0) {
                            size = size + eventTicketOrders.size();
                            for (EventTicketOrder eventTicketOrder :eventTicketOrders) { //订单票
                                TicketInfo ticketInfo = new TicketInfo();
                                ticketInfo.id = eventTicketOrder.getCommodityDetailId();
                                ticketInfo.ticketNo = eventTicketOrder.getTicketNo();
                                ticketInfo.state = eventTicketOrder.getSignState();
                                ticketInfo.signTime = eventTicketOrder.getSignTime() == null ? 0 : eventTicketOrder.getSignTime();
                                ticketInfo.tradeNo = eventRecord.getTradeNo();
                                ticketR.ticketList.add(ticketInfo);
                            }
                        }
                    }
                }
                ticketR.totalAmount = ticket.price * size;
                ticketR.buyCount = size;

                if (ticketR.ticketList != null && ticketR.ticketList.size() > 0) {
                    ticketTypes.add(ticketR);
                }
            }
        }

        return ticketTypes;
    }

    public Object getvalidaChekIn(CheckInReq req) {

        CheckInResp resp = new CheckInResp();
        Integer eventTicketCount = eventTicketOrderMapper.selectValidTicketOrder(req.eventId,req.startTime,req.endTime,req.uids);
        if (eventTicketCount != null ) {
            resp.count = eventTicketCount;
        }else {
            resp.count = 0;
        }
        return resp;
    }


    /**
     * 签到-新的用户扫码
     */
    public Object newDetectPermissionUser(CheckInReq req) {
        CheckInResp resp = new CheckInResp();

        Event event = eventService.getEvent(req.eventId);
        if (event == null) {
            throw ExceptionUtil.createParamException("活动信息异常，请重试");
        }

        //event
        resp.event = converseEvent(event);

        if (req.uid == null) {
            //未报名活动
            resp.state = ParamEnum.EventSignInfoState.EVENT_SIGN_NO_ORDER.getValue();
            resp.msg = ParamEnum.EventSignInfoState.EVENT_SIGN_NO_ORDER.getDesc();
            return resp;
        }
        
        User user = userService.getUserInfoById(req.uid);
        if (user != null) {
            //user
            resp.user = converseUser(user);

            //根据uid和activeid取报名记录，如果未取到，则再根据微信号和活动id，去尝试获取记录
            EventRecordExample eventRecordExample = new EventRecordExample();
            eventRecordExample.createCriteria().andEventIdEqualTo(req.eventId).andUidEqualTo(req.uid).
                    andTicketLockEqualTo(SqlEnum.TicketLockType.TYPE_NORMAL.getValue());
            List<EventRecord> eventRecordList = eventRecordMapper.selectByExample(eventRecordExample);
            if (eventRecordList == null || eventRecordList.size() == 0) {
                //未报名活动
                resp.state = ParamEnum.EventSignInfoState.EVENT_SIGN_NO_ORDER.getValue();
                resp.msg = ParamEnum.EventSignInfoState.EVENT_SIGN_NO_ORDER.getDesc();
                return resp;
            } else {
                EventRecord eventRecord = eventRecordList.get(eventRecordList.size() - 1);
                //活动状态
                long time = System.currentTimeMillis() - event.getEndTime();
                long deadlineTime = System.currentTimeMillis() - event.getDeadlineTime();
                if (time > 0) {
                    resp.state = ParamEnum.EventSignInfoState.EVENT_SIGN_ACTIVE_OVER.getValue();
                    resp.msg = ParamEnum.EventSignInfoState.EVENT_SIGN_ACTIVE_OVER.getDesc();
                    return resp;
                } else {
                    if (event.getState() == SqlEnum.EventStateType.TYPE_EVENT_DELETED.getByteValue()) {
                        resp.state = ParamEnum.EventSignInfoState.EVENT_SIGN_CANCEL_ORDER.getValue();
                        resp.msg = ParamEnum.EventSignInfoState.EVENT_SIGN_CANCEL_ORDER.getDesc();
                        return resp;
                    } else if (eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY.getByteValue()) {

                        resp.state = ParamEnum.EventSignInfoState.EVENT_SIGN_WAIITING.getValue();
                        resp.msg = ParamEnum.EventSignInfoState.EVENT_SIGN_WAIITING.getDesc();
                        return resp;
                    } else if (eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_VERIFY_FAILED
                            .getByteValue()) {
                        resp.state = ParamEnum.EventSignInfoState.EVENT_SIGN_REFUSE.getValue();
                        resp.msg = ParamEnum.EventSignInfoState.EVENT_SIGN_REFUSE.getDesc() + "\r\n理由:" + eventRecord.getReason();
                        return resp;
                    } else if ((eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_USER_DELETED.getByteValue() ||
                            eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_PLATFORM_DELETED.getByteValue()) && deadlineTime > 0) {
                        //未报名活动且已经截止
                        resp.state = ParamEnum.EventSignInfoState.EVENT_SIGN_NO_ORDER1.getValue();
                        resp.msg = "手机号" + user.phone + ParamEnum.EventSignInfoState.EVENT_SIGN_NO_ORDER1.getDesc();
                        return resp;
                    }else if (eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_USER_DELETED.getByteValue() ||
                            eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_PLATFORM_DELETED
                                    .getByteValue()) {
                        //未报名活动
                        resp.state = ParamEnum.EventSignInfoState.EVENT_SIGN_NO_ORDER.getValue();
                        resp.msg = ParamEnum.EventSignInfoState.EVENT_SIGN_NO_ORDER.getDesc();
                        return resp;
                    }
                }
                //ticket
                resp.ticketTypes = newConverseTicket(event, eventRecordList);
            }
        }
        return resp;
    }

    private List<Ticket> newConverseTicket(Event event , List<EventRecord> eventRecordList) {
        List<Ticket> ticketTypes = new ArrayList<>();

        List<Ticket> ticketModules = commodityRemoteService.converseToTicket(event.getCommodityId());
        if (ticketModules != null && ticketModules.size() > 0) {
            for (Ticket ticket : ticketModules) { //票种
                for (EventRecord eventRecord :eventRecordList) { //报名记录
                    if (eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getByteValue()) {
                        EventTicketOrderExample eventTicketOrderExample = new EventTicketOrderExample();
                        eventTicketOrderExample.createCriteria().andEventIdEqualTo(event.getId()).
                                andEventRecordIdEqualTo(eventRecord.getId()).andTicketIdEqualTo(ticket.id);
                        List<EventTicketOrder> eventTicketOrders = eventTicketOrderMapper.selectByExample(eventTicketOrderExample);

                        if (eventTicketOrders != null && eventTicketOrders.size() > 0) {
                            for (EventTicketOrder eventTicketOrder :eventTicketOrders) { //订单票
                                Ticket ticketR = new Ticket();
                                ticketR.title = ticket.title;
                                ticketR.intro = ticket.intro;
                                ticketR.price = ticket.price;
                                TicketInfo ticketInfo = new TicketInfo();
                                ticketInfo.id = eventTicketOrder.getCommodityDetailId();
                                ticketInfo.ticketNo = eventTicketOrder.getTicketNo();
                                ticketInfo.state = eventTicketOrder.getSignState();
                                ticketInfo.signTime = eventTicketOrder.getSignTime() == null ? 0 : eventTicketOrder.getSignTime();
                                ticketInfo.tradeNo = eventRecord.getTradeNo();
                                ticketInfo.recordTime = eventRecord.getCreateTime();
                                ticketR.ticketInfo = ticketInfo;
                                ticketTypes.add(ticketR);
                            }
                        }
                    }
                }
            }
        }

        Collections.sort(ticketTypes ,  new Comparator<Ticket>() {

            public int compare(Ticket o1, Ticket o2) {
                // 返回值为int类型，大于0表示正序，小于0表示逆序
                return o1.ticketInfo.state - o2.ticketInfo.state;
            }
        });

        return ticketTypes;
    }

}
