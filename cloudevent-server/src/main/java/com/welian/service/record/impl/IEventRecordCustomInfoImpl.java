package com.welian.service.record.impl;

import com.welian.beans.account.UserReq;
import com.welian.beans.cloudevent.Ticket;
import com.welian.beans.cloudevent.record.CustomSignUpReq;
import com.welian.beans.cloudevent.record.CustomSignUpResp;
import com.welian.enums.account.EnumReqType;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.mapper.EventRecordMapper;
import com.welian.mapper.ExtensionLinkMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventRecord;
import com.welian.pojo.EventRecordExample;
import com.welian.pojo.EventStateCustom;
import com.welian.pojo.ExtensionLink;
import com.welian.service.CommodityRemoteService;
import com.welian.service.UserService;
import com.welian.service.impl.EventServiceImpl;
import com.welian.service.impl.StateCustomModuleService;
import com.welian.service.record.IEventRecordCustomInfo;
import com.welian.utils.Base64Utils;
import com.welian.utils.ExceptionUtil;
import org.sean.framework.annotation.Synchronized;
import org.sean.framework.service.RedisService;
import org.sean.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * created by GaoXiang on 2017/12/5
 */
@Component("iEventRecordCustomInfo")
public class IEventRecordCustomInfoImpl extends IEventRecordInfoImpl implements IEventRecordCustomInfo {

    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private ExtensionLinkMapper extensionLinkMapper;
    @Autowired
    private StateCustomModuleService stateCustomModuleService;
    @Autowired
    private CommodityRemoteService commodityRemoteService;
    @Autowired
    private EventRecordMapper eventRecordMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;



    @Synchronized(argType = Synchronized.ArgType.ARG_BEAN, keyName = "eventId")//锁定活动
    public CustomSignUpResp signUp(CustomSignUpReq req) {

        CustomSignUpResp response = new CustomSignUpResp();
        ExtensionLink extensionLink = extensionLinkMapper.selectByPrimaryKey(req.extensionLinkId);
        Event event = eventService.getEvent(extensionLink.getEventId());
        if(event==null){
            throw ExceptionUtil.createParamException("活动不存在");
        }
        EventStateCustom eventStateCustom = stateCustomModuleService.get(event.getId());
        //3.7.2加逻辑，uid由服务端获取
        UserReq user=new UserReq();
        user.type= EnumReqType.PRE;
        user.position=req.user.position;
        user.mobile=req.user.mobile;
        user.company=req.user.company;
        user.name=req.user.name;
        req.user.uid=userService.getUidByUser(user);
        //如果是免费活动，不能重复报名
        if (eventStateCustom.getCostType().equals(SqlEnum.CostType.TYPE_CHARGE_NOT.getValue().byteValue())) {
            checkUserFirstSignup(req.user.uid, event);
        }
        //判断活动时间状态
        if(!SqlEnum.IsImport.TYPE_IS_IMPORT.equals(req.isImportRecord)) {
            judegeEventState(event);
        }
        //判断是否还有票,票券是否改变
        judegeTicketRemaind(event, req.ticketList);
        //获取订单号
        String tradeNo = commodityRemoteService.getOrderNo();
        //默认订单过期时间为十分钟
        Long expireTime = redisService.getCurrentTime() + 10 * 60 * 1000;
        response.tradeNo = tradeNo;
        //保存活动报名,先把票锁定，支付成功以后解锁
        EventRecord eventRecord = saveCustomRecord(req, event, tradeNo, expireTime);
        //保存活动报名时填写的自定义字段
        saveRecordCustomField(req.additionalForm, eventRecord.getId(),event.getId());

        //保存报名者的信息
        saveRecordUser(req.user, eventRecord.getId());
        //下单
        createOrder(event, req, expireTime, tradeNo,req.channel);
        response.recordId = eventRecord.getId();
        response.recordIdStr = Base64Utils.encode(eventRecord.getId().toString().getBytes());
        response.startTime=event.getStartTime();
        response.endTime=event.getEndTime();
        response.recordStatus=eventRecord.getState();
        response.uid=eventRecord.getUid();
        return response;
    }

    private void unLockRecord(Integer id) {
        EventRecord record = new EventRecord();
        record.setId(id);
        record.setTicketLock(SqlEnum.TicketLockType.TYPE_NORMAL.getValue());
        eventRecordMapper.updateByPrimaryKeySelective(record);
    }


    @Transactional
    public EventRecord saveCustomRecord(CustomSignUpReq req, Event event, String tradeNo, Long expireTime) {
        EventRecord eventRecord = new EventRecord();
        eventRecord.setEventId(event.getId());
        eventRecord.setOrgId(event.getOrgId());
        eventRecord.setCreateTime(System.currentTimeMillis());
        eventRecord.setExtensionLinkId(req.extensionLinkId);
        eventRecord.setSignUpType(SqlEnum.SignUpType.TYPE_EVENT_CUSTOM.getByteValue());//观众报名
        eventRecord.setModifyTime(System.currentTimeMillis());
        eventRecord.setSource(req.type.byteValue());
        eventRecord.setUid(req.user.uid);
        eventRecord.setTradeEndTime(expireTime);
        eventRecord.setTradeNo(tradeNo);
        eventRecord.setIsImport(req.isImportRecord);
        EventStateCustom eventStateCustom = stateCustomModuleService.get(eventRecord.getEventId());
        if (eventStateCustom.getVerifyType() != null &&
                eventStateCustom.getVerifyType() == SqlEnum.RecordVerifyType.TYPE_VERIFY_YES.getByteValue()
                        .byteValue()) {
            eventRecord.setState(SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY.getByteValue());
        } else {
            eventRecord.setState(SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getByteValue());
        }
        //锁票
        eventRecord.setTicketLock(SqlEnum.TicketLockType.TYPE_LOCK.getValue());
        int result = eventRecordMapper.insertSelective(eventRecord);
        if (result == 0) {
            throw ExceptionUtil.createParamException("报名失败");
        }
        saveTicketRecordUrl(eventRecord.getId());

        return eventRecord;

    }


    private String createOrder(Event event, CustomSignUpReq req, Long expireTime, String tradeNo , Integer channel) {

        return commodityRemoteService.createOrder(event, req, expireTime, tradeNo ,channel);

    }

    private void judegeTicketRemaind(Event event, List<Ticket> ticketList) {
        if (StringUtil.isEmpty(ticketList)) {
            throw ExceptionUtil.createParamException("ticketList参数异常");
        }
        Map<Integer, Ticket> ticketMap = commodityRemoteService.converseToTicketMap(event.getCommodityId());
        for (Ticket ticket : ticketList) {
            //票券发生变化
            if(!ticketMap.containsKey(ticket.id)){
                throw ExceptionUtil.createParamException("活动表单已更新，请刷新页面重新报名");
            }
            if (ticket.buyCount > ticketMap.get(ticket.id).remaindCount) {
                throw ExceptionUtil.createParamException(ticketMap.get(ticket.id).title + "的数量不足");
            }
        }

    }

    private void judegeEventState(Event event) {
        //判断活动时间是否有效
        if (event.getDeadlineTime() != null) {
            if (event.getDeadlineTime() < redisService.getCurrentTime()) {
                throw ExceptionUtil.createParamException("活动报名已截止，无法报名");
            } else {

            }
        }
    }

    /**
     * 如果是免费活动，不能重复报名
     */
    public void checkUserFirstSignup(Integer uid, Event event) {
        EventRecordExample example = new EventRecordExample();
        List<Byte> validStates = new ArrayList<>();
        validStates.add(SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getByteValue());
        validStates.add(SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY.getByteValue());
        example.createCriteria().andUidEqualTo(uid).andEventIdEqualTo(event.getId()).andTicketLockEqualTo(
                SqlEnum.TicketLockType.TYPE_NORMAL.getValue()).andStateIn(validStates);
        Integer userSignupTimes = eventRecordMapper.selectByExample(example).size();
        if (userSignupTimes >= 1) {
            throw ExceptionUtil.createParamException("你已经报名该活动");
        }

    }
}
