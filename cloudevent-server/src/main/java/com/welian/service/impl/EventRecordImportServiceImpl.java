package com.welian.service.impl;

import com.welian.beans.cloudevent.Ticket;
import com.welian.beans.cloudevent.record.CustomImportListReq;
import com.welian.beans.cloudevent.record.CustomImportReq;
import com.welian.beans.cloudevent.record.CustomSignUpResp;
import com.welian.beans.cloudevent.record.ProjectImportReq;
import com.welian.beans.cloudevent.record.ProjectSignUpResp;
import com.welian.beans.cloudevent.user.UserReq;
import com.welian.beans.urls.Url;
import com.welian.beans.urls.UrlReq;
import com.welian.beans.urls.UrlType;
import com.welian.client.commodity.CommodityOrderClient;
import com.welian.client.commodity.CustomServiceClient;
import com.welian.client.urls.UrlClient;
import com.welian.commodity.beans.Buyer;
import com.welian.commodity.beans.CustomServiceDetailReq;
import com.welian.commodity.beans.CustomServiceReq;
import com.welian.commodity.beans.Order;
import com.welian.commodity.beans.OrderDetail;
import com.welian.config.CloudEventConfig;
import com.welian.config.Constant;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.mapper.EventCustomFieldMapper;
import com.welian.mapper.EventMapper;
import com.welian.mapper.EventRecordCollectionMapper;
import com.welian.mapper.EventRecordMapper;
import com.welian.mapper.EventRecordProjectMapper;
import com.welian.mapper.EventRecordUserMapper;
import com.welian.mapper.EventSignAuthMapper;
import com.welian.mapper.EventStateCustomMapper;
import com.welian.mapper.EventTicketOrderMapper;
import com.welian.mapper.ExtensionLinkMapper;
import com.welian.mapper.ProjectBackupInfoMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventCustomField;
import com.welian.pojo.EventCustomFieldExample;
import com.welian.pojo.EventExample;
import com.welian.pojo.EventRecord;
import com.welian.pojo.EventRecordCollection;
import com.welian.pojo.EventRecordExample;
import com.welian.pojo.EventRecordProject;
import com.welian.pojo.EventRecordUser;
import com.welian.pojo.EventStateCustom;
import com.welian.pojo.EventStateCustomExample;
import com.welian.pojo.EventStateProject;
import com.welian.pojo.EventTicketOrder;
import com.welian.pojo.EventTicketOrderExample;
import com.welian.pojo.ExtensionLink;
import com.welian.pojo.ProjectBackupInfo;
import com.welian.pojo.ProjectBackupInfoExample;
import com.welian.service.CommodityRemoteService;
import com.welian.service.UserService;
import com.welian.utils.Base64Utils;
import com.welian.utils.DateUtil;
import com.welian.utils.ExceptionUtil;
import org.sean.framework.annotation.Synchronized;
import org.sean.framework.bean.BaseResult;
import org.sean.framework.util.JSONUtil;
import org.sean.framework.util.Logger;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("eventRecordImportService")
public class EventRecordImportServiceImpl {
    private static final Logger logger = Logger.newInstance(EventRecordImportServiceImpl.class);
    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private EventRecordMapper eventRecordMapper;
    @Autowired
    private EventRecordCollectionMapper eventRecordCollectionMapper;
    @Autowired
    private EventCustomFieldMapper eventCustomFieldMapper;
    @Autowired
    private EventRecordUserMapper eventRecordUserMapper;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private ExtensionLinkServiceImpl extensionLinkService;
    @Autowired
    private CloudEventConfig cloudEventConfig;
    @Autowired
    private UrlClient urlClient;
    @Autowired
    private StateProjectModuleServiceImpl stateProjectModuleService;
    @Autowired
    private EventRecordProjectMapper eventRecordProjectMapper;
    @Autowired
    private ProjectBackupInfoMapper projectBackupInfoMapper;
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private CommodityRemoteService commodityRemoteService;
    @Autowired
    private EventStateCustomMapper eventStateCustomMapper;
    @Autowired
    private StateCustomModuleService stateCustomModuleService;
    @Autowired
    private CommodityOrderClient commodityOrderClient;
    @Autowired
    private EventSignAuthMapper eventSignAuthMapper;
    @Autowired
    private EventTicketOrderMapper eventTicketOrderMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CommodityRemoteService CommodityRemoteService;
    @Autowired
    private ExtensionLinkMapper extensionLinkMapper;
    @Autowired
    private EventStatisticServiceImpl eventStatisticService;
    @Autowired
    private CustomServiceClient customServiceClient;


    @Transactional
    public Object projectSignUp(ProjectImportReq req) {
        ExtensionLink extensionLink = extensionLinkService.getDefaultLinkByEventId(req.eventId);
        if (extensionLink == null || extensionLink.getState() != 0) {
            throw ExceptionUtil.createParamException("找不到推广链接");
        }
        Event event = eventService.getEvent(extensionLink.getEventId());
        if (event == null || !NumberUtil.isValidId(event.getTemplateId())) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        EventRecordExample eventRecordExample = new EventRecordExample();
        eventRecordExample.createCriteria().andOldRecordIdEqualTo(req.recordId).andSignUpTypeEqualTo((byte) 1);
        long count = eventRecordMapper.countByExample(eventRecordExample);
        if (count > 0) {
            throw ExceptionUtil.createParamException("活动报名数据已导入");
        }

        EventStateProject eventStateProject = stateProjectModuleService.get(event.getId());
        if (event.getTemplateId() == SqlEnum.EventType.TYPE_EVENT_ROADSHOW.getValue().intValue()) {
            //如果当前活动是详细版的
            if (SqlEnum.ProjectModuleType.TYPE_DETAIL.getByteValue().byteValue()
                    == eventStateProject.getProjectInputType()) {
                throw ExceptionUtil.createParamException("活动表单已更新，请刷新页面重新报名");
            } else if (SqlEnum.ProjectModuleType.TYPE_SIMPLE.getByteValue().byteValue()
                    == eventStateProject.getProjectInputType()) {
                return signUp(req, event, eventStateProject.getProjectInputType()
                        .intValue());
            } else {
                throw ExceptionUtil.createParamException("项目模板异常");
            }
        } else {
            throw ExceptionUtil.createParamException("对不起，此活动不能报名项目，请联系客服");
        }
    }


    @Transactional
    public Object customSignUp(CustomImportListReq req) {
        if (StringUtil.isEmpty(req.list)) {
            throw ExceptionUtil.createParamException("列表为空");
        }
        for (CustomImportReq customImportReq : req.list) {
            if (!NumberUtil.isValidId(customImportReq.recordId)) {
                throw ExceptionUtil.createParamException("recordId参数异常");
            }
            if (!NumberUtil.isValidId(customImportReq.eventId)) {
                throw ExceptionUtil.createParamException("eventId参数异常");
            }
            if (!NumberUtil.isValidId(customImportReq.source)) {
                throw ExceptionUtil.createParamException("source参数异常");
            }
            if (customImportReq.recordType == null) {
                throw ExceptionUtil.createParamException("recordType参数异常");
            }
            if (!NumberUtil.isValidId(customImportReq.createTime)) {
                throw ExceptionUtil.createParamException("createTime参数异常");
            }
            if (!NumberUtil.isValidId(customImportReq.modifyTime)) {
                throw ExceptionUtil.createParamException("modifyTime参数异常");
            }
            if (customImportReq.user == null ) {
                throw ExceptionUtil.createParamException("参数异常");
            }
            EventExample eventExample = new EventExample();
            eventExample.createCriteria().andOldEventIdEqualTo(customImportReq.eventId).andTemplateIdEqualTo(1);
            List<Event> events = eventMapper.selectByExample(eventExample);
            if (StringUtil.isEmpty(events)) {
                throw ExceptionUtil.createParamException(customImportReq.eventId + "的活动不存在");
            }
            Event event = events.get(0);

            saveCustomRecord(customImportReq, event);
        }
        return null;
    }

    @Synchronized(argType = Synchronized.ArgType.ARG_BEAN, argIndex = 1, keyName = "id")//锁定活动
    private void saveCustomRecord(CustomImportReq customImportReq, Event event) {
        EventRecordExample eventRecordExample = new EventRecordExample();
        eventRecordExample.createCriteria().andOldRecordIdEqualTo(customImportReq.recordId)
                .andSignUpTypeEqualTo((byte) 0);
        long count = eventRecordMapper.countByExample(eventRecordExample);
        if (count > 0) {
            throw ExceptionUtil.createParamException(customImportReq.recordId + "的报名记录已存在");
        }

        ExtensionLink extensionLink = extensionLinkService.getDefaultLinkByEventId(event.getId());
        if (extensionLink == null || extensionLink.getState() != 0) {
            throw ExceptionUtil.createParamException(event.getId() + "找不到推广链接");
        }
        if (!NumberUtil.isValidId(event.getTemplateId())) {
            throw ExceptionUtil.createParamException(event.getId() + "活动不存在");
        }
        CustomSignUpResp response = new CustomSignUpResp();
        EventStateCustom eventStateCustom = stateCustomModuleService.get(event.getId());
        //如果是免费活动，不能重复报名
        if (customImportReq.user == null) {
            throw ExceptionUtil.createParamException(customImportReq.user.uid + "user参数异常");
        }
        if (checkUserFirstSignup(customImportReq.user.uid, event)) {
            throw ExceptionUtil.createParamException(customImportReq.user.uid + "报名已存在");
        }

        //判断活动时间状态
        //判断是否还有票
        judegeTicketRemaind(event, customImportReq.ticketList);
        //获取订单号
        String tradeNo = commodityRemoteService.getOrderNo();
        //默认订单过期时间为十分钟
        Long expireTime = customImportReq.createTime + 24 * 60 * 60 * 1000l * 14;
        response.tradeNo = tradeNo;
        //保存活动报名,先把票锁定，支付成功以后解锁
        EventRecord eventRecord = saveCustomRecord(customImportReq, event, tradeNo, expireTime, extensionLink
                .getId());
        //保存活动报名时填写的自定义字段
        saveRecordCustomField(event.getId(), customImportReq.additionalForms, eventRecord.getId());
        //保存报名者的信息
        saveRecordUser(customImportReq.user, eventRecord.getId());
        //下单
        boolean b = createOrder(event, customImportReq, expireTime, tradeNo);
        if (b) {
            EventRecord record = new EventRecord();
            record.setId(eventRecord.getId());
            record.setTicketLock(SqlEnum.TicketLockType.TYPE_NORMAL.getValue());
            eventRecordMapper.updateByPrimaryKeySelective(record);
            insertByRecord(eventRecord);
            //
            EventTicketOrderExample example = new EventTicketOrderExample();
            example.createCriteria().andEventIdEqualTo(event.getId()).andEventRecordIdEqualTo(eventRecord.getId());
            List<EventTicketOrder> eventTicketOrders = eventTicketOrderMapper.selectByExample(example);
            if (eventTicketOrders == null || eventTicketOrders.size() == 0) {
                throw ExceptionUtil.createParamException("未报名该活动");
            }
            //签到
            if (customImportReq.signin == 1) {
                for (EventTicketOrder ticketOrder : eventTicketOrders) {
                    if (ticketOrder.getSignState() == SqlEnum.EventSignStatus.EVENT_SIGN_TRUE.getValue()) {
                        throw ExceptionUtil.createParamException("已签到该活动");
                    }
                    if (ticketOrder.getTicketState() == SqlEnum.EventTicketState.EVENT_TICKET_TRUE.getValue()) {
                        throw ExceptionUtil.createParamException("活动票异常");
                    }
                    EventTicketOrder record1 = new EventTicketOrder();
                    record1.setSignState(SqlEnum.EventSignStatus.EVENT_SIGN_TRUE.getValue());
                    record1.setSignTime(customImportReq.signTime);
                    record1.setModifyTime(System.currentTimeMillis());
                    record1.setId(ticketOrder.getId());
                    eventTicketOrderMapper.updateByPrimaryKeySelective(record1);
                }
            }
            if (eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_USER_DELETED.getByteValue() ||
                    eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_PLATFORM_DELETED.getByteValue() ||
                    eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_VERIFY_FAILED.getByteValue()) {
                if (eventStateCustom.getCostType() == 0) {
                    //调用商品接口把库存释放
                    CustomServiceReq req = new CustomServiceReq();
                    req.commodityOrderNo = eventRecord.getTradeNo();
                    req.customServiceDetailReqs = new ArrayList<>();
                    req.price = Constant.FREE_PRICE_LONG;
                    req.uid = eventRecord.getUid();
                    CustomServiceDetailReq customServiceDetailReq;
                    for (EventTicketOrder eventTicketOrder : eventTicketOrders) {
                        customServiceDetailReq = new CustomServiceDetailReq();
                        customServiceDetailReq.commodityOrderDetailId = eventTicketOrder.getCommodityDetailId();
                        //免费票传0
                        customServiceDetailReq.price = Constant.FREE_PRICE_LONG;
                        req.customServiceDetailReqs.add(customServiceDetailReq);
                    }
                    BaseResult<String> baseResult = customServiceClient.save(req);
                    if (!baseResult.isSuccess()) {
                        throw ExceptionUtil.createParamException("取消时失败" + baseResult.getErrormsg());
                    }
                } else {
                    throw ExceptionUtil.createParamException("收费活动不能取消");
                }
            }
        }
    }

    @Transactional
    public EventRecord saveCustomRecord(CustomImportReq req, Event event, String tradeNo, Long expireTime, Integer
            extensionLinkId) {
        EventRecord eventRecord = new EventRecord();
        eventRecord.setEventId(event.getId());
        eventRecord.setOldRecordId(req.recordId);
        eventRecord.setOrgId(event.getOrgId());
        eventRecord.setCreateTime(req.createTime);
        eventRecord.setExtensionLinkId(extensionLinkId);
        eventRecord.setSignUpType(SqlEnum.SignUpType.TYPE_EVENT_CUSTOM.getValue().byteValue());//观众报名
        eventRecord.setModifyTime(req.modifyTime);
        eventRecord.setSource(req.source.byteValue());
        eventRecord.setUid(req.user.uid);
        eventRecord.setState((byte) (int) req.recordType);
        eventRecord.setTradeEndTime(expireTime);
        eventRecord.setTradeNo(tradeNo);
        EventStateCustom eventStateCustom = stateCustomModuleService.get(eventRecord.getEventId());
        //锁票
        eventRecord.setTicketLock(SqlEnum.TicketLockType.TYPE_LOCK.getValue());
        int result = eventRecordMapper.insertSelective(eventRecord);
        if (result == 0) {
            throw ExceptionUtil.createParamException("报名失败");
        }
        saveTicketRecordUrl(eventRecord.getId());

        return eventRecord;

    }

    public void saveRecordCustomField(Integer eventId, List<String> additionalForms, Integer eventRecordId) {
        if (StringUtil.isEmpty(additionalForms)) {
            return;
        }
        EventCustomFieldExample eventCustomFieldExample = new EventCustomFieldExample();
        eventCustomFieldExample.createCriteria().andEventIdEqualTo(eventId).andStateEqualTo((byte) 0);
        List<EventCustomField> eventCustomFields = eventCustomFieldMapper.selectByExample(eventCustomFieldExample);
        if (StringUtil.isEmpty(eventCustomFields)) {
            return;
        }
        if (additionalForms.size() != eventCustomFields.size()) {
            logger.info(eventId + "活动的自定义字段不一致");

        }
        EventRecordCollection eventRecordCollection;
        String addtionalForm;
        for (int i = 0, count = additionalForms.size(); i < count; i++) {
            addtionalForm = additionalForms.get(i);

            eventRecordCollection = new EventRecordCollection();
            eventRecordCollection.setCreateTime(System.currentTimeMillis());
            eventRecordCollection.setModifyTime(System.currentTimeMillis());
            eventRecordCollection.setEventRecordId(eventRecordId);
            if (eventCustomFields.size() <= i) {
                return;
            }
            eventRecordCollection.setCollectionId(eventCustomFields.get(i).getId());
            eventRecordCollection.setContent(addtionalForm);
            eventRecordCollectionMapper.insertSelective(eventRecordCollection);
        }
    }

    private boolean judegeTicketRemaind(Event event, List<Ticket> ticketList) {
        Map<Integer, Ticket> ticketMap = commodityRemoteService.converseToTicketMap(event.getCommodityId());
        if (StringUtil.isEmpty(ticketMap)) {
            throw ExceptionUtil.createParamException("ticketMap参数异常");
        }
        EventStateCustomExample example = new EventStateCustomExample();
        example.createCriteria().andEventIdEqualTo(event.getId());
        List<EventStateCustom> eventStateCustomList = eventStateCustomMapper.selectByExample(example);
        if (eventStateCustomList == null || eventStateCustomList.size() == 0) {
            throw ExceptionUtil.createParamException("活动信息错误2");
        }
        EventStateCustom eventStateCustom = eventStateCustomList.get(0);
        if (eventStateCustom.getCostType() == 0) {//免费
            for (Map.Entry<Integer, Ticket> entry : ticketMap.entrySet()) {
                Ticket ticket = entry.getValue();
                if (ticket.remaindCount == 0) {
                    return true;
                }
            }
        } else {
            if (StringUtil.isEmpty(ticketList)) {
                throw ExceptionUtil.createParamException("ticketList参数异常");
            }
            for (Map.Entry<Integer, Ticket> entry : ticketMap.entrySet()) {
                Ticket ticket = entry.getValue();
                for (Ticket ticketOld : ticketList) {
                    if (!StringUtil.isEmpty(ticket.title) && ticket.title.equals(ticketOld.title) &&
                            ticket.price != null && ticket.price.equals(ticketOld.price)) {
                        if (StringUtil.isEmpty(ticket.intro) && !StringUtil.isEmpty(ticketOld.intro)) {
                            throw ExceptionUtil.createParamException("介绍有问题1");
                        } else if (!StringUtil.isEmpty(ticket.intro) && StringUtil.isEmpty(ticketOld.intro)) {
                            throw ExceptionUtil.createParamException("介绍有问题2");
                        } else if (!StringUtil.isEmpty(ticket.title) && !StringUtil.isEmpty(ticketOld.intro) && !ticket
                                .intro.equals(ticketOld.intro)) {
                            throw ExceptionUtil.createParamException("介绍有问题3");
                        }
                        if (ticketOld.buyCount > ticket.remaindCount) {
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    /**
     * 如果是免费活动，不能重复报名
     */
    public boolean checkUserFirstSignup(Integer uid, Event event) {
        EventRecordExample example = new EventRecordExample();
        List<Byte> validStates = new ArrayList<>();
        validStates.add(SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getByteValue());
        validStates.add(SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY.getByteValue());
        example.createCriteria().andUidEqualTo(uid).andEventIdEqualTo(event.getId()).andTicketLockEqualTo(
                SqlEnum.TicketLockType.TYPE_NORMAL.getValue()).andStateIn(validStates);
        Integer userSignupTimes = eventRecordMapper.selectByExample(example).size();
        if (userSignupTimes >= 1) {
            return true;
        }
        return false;
    }


    @Synchronized(argType = Synchronized.ArgType.ARG_BEAN, argIndex = 1, keyName = "id")//锁定活动
    @Transactional
    public ProjectSignUpResp signUp(ProjectImportReq req, Event event, Integer moduleType) {
        Long expireTime = DateUtil.curTimeMillis() + 10 * 60 * 1000;
        //判断是否已经报过名了
        judgeHasSignUpActivity(event.getId(), req.project.pid);
        //保存活动报名，获取订单号
        EventRecord eventRecord = saveProjectRecord(req, event, expireTime);
        //保存项目备份
        saveProjectBackUp(req, eventRecord.getId(), moduleType);
        //保存报名者的信息
        saveRecordUser(req.user, eventRecord.getId());
        //下单
        commodityRemoteService.createOrderForProject(event, req.user, expireTime, eventRecord
                .getTradeNo(), false);

        //支付回调成功，解锁
        EventRecord record = new EventRecord();
        record.setId(eventRecord.getId());
        record.setTicketLock(SqlEnum.TicketLockType.TYPE_NORMAL.getValue());
        eventRecordMapper.updateByPrimaryKeySelective(record);
        insertByRecord(eventRecord);
        //

        EventTicketOrderExample example = new EventTicketOrderExample();
        example.createCriteria().andEventIdEqualTo(event.getId()).andEventRecordIdEqualTo(eventRecord.getId());
        List<EventTicketOrder> eventTicketOrders = eventTicketOrderMapper.selectByExample(example);
        if (eventTicketOrders == null || eventTicketOrders.size() == 0) {
            throw ExceptionUtil.createParamException("未报名该活动111");
        }
        if (eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_USER_DELETED.getByteValue() ||
                eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_PLATFORM_DELETED.getByteValue() ||
                eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_VERIFY_FAILED.getByteValue()) {
            //调用商品接口把库存释放
            CustomServiceReq req1 = new CustomServiceReq();
            req1.commodityOrderNo = eventRecord.getTradeNo();
            req1.customServiceDetailReqs = new ArrayList<>();
            req1.price = Constant.FREE_PRICE_LONG;
            req1.uid = eventRecord.getUid();
            CustomServiceDetailReq customServiceDetailReq;
            for (EventTicketOrder eventTicketOrder : eventTicketOrders) {
                customServiceDetailReq = new CustomServiceDetailReq();
                customServiceDetailReq.commodityOrderDetailId = eventTicketOrder.getCommodityDetailId();
                //免费票传0
                customServiceDetailReq.price = Constant.FREE_PRICE_LONG;
                req1.customServiceDetailReqs.add(customServiceDetailReq);
            }
            customServiceClient.save(req1);
        }

        //将信息返回客户端
        ProjectSignUpResp projectSignUpResp = new ProjectSignUpResp();
        projectSignUpResp.pid = req.project.pid;
        projectSignUpResp.signUpId = eventRecord.getId();
        projectSignUpResp.recordStatus = eventRecord.getState();
        projectSignUpResp.startTime = event.getStartTime();
        projectSignUpResp.endTime = event.getEndTime();
        return projectSignUpResp;
    }

    /**
     * 判断是否报名了活动
     *
     * @param eventId
     * @param pid
     */
    private void judgeHasSignUpActivity(Integer eventId, Integer pid) {
        int count = eventRecordMapper.hasSignUpCount(eventId, pid);
        if (count != 0) {
            throw ExceptionUtil.createParamException("该项目已经报名此活动");
        }
    }

    @Transactional
    public EventRecord saveProjectRecord(ProjectImportReq req, Event event, Long expireTime) {
        ExtensionLink extensionLink = extensionLinkService.getDefaultLinkByEventId(req.eventId);
        //生成订单(3.4.0版本后该字段已废弃）
        String orderNumber = orderService.createOrderNumber();
        //生成票单(3.4.0版本后该字段已废弃）
        String ticketNumber = orderService.createTicketNumber();
        EventRecord eventRecord = new EventRecord();
        eventRecord.setId(req.recordId);
        eventRecord.setOldRecordId(req.recordId);
        eventRecord.setEventId(event.getId());
        eventRecord.setOrgId(event.getOrgId());
        eventRecord.setCreateTime(req.createTime);
        eventRecord.setExtensionLinkId(extensionLink.getId());
        eventRecord.setSignUpType(SqlEnum.SignUpType.TYPE_EVENT_PROJECT.getByteValue());//项目报名
        eventRecord.setModifyTime(req.modifyTime);
        eventRecord.setSource((byte) (int) req.source);
        eventRecord.setUid(req.user.uid);
        eventRecord.setOrderNumber(orderNumber);
        eventRecord.setTicketNumber(ticketNumber);
        //获得订单号
        String tradeNo = commodityRemoteService.getOrderNo();
        eventRecord.setTradeNo(tradeNo);
        eventRecord.setTradeEndTime(expireTime);
        eventRecord.setTicketLock(SqlEnum.TicketLockType.TYPE_LOCK.getValue());
        //都为审核通过
        eventRecord.setState((byte) (int) req.recordType);

        int result = eventRecordMapper.insertSelective2(eventRecord);
        if (result == 0) {
            throw ExceptionUtil.createParamException("报名失败");
        }

        EventRecordProject eventRecordProject = new EventRecordProject();
        eventRecordProject.setCreateTime(req.createTime);
        eventRecordProject.setEventRecordId(eventRecord.getId());
        eventRecordProject.setPid(req.project.pid);
        eventRecordProject.setSignUpTime(req.createTime);
        eventRecordProject.setStarLevel((byte) 0);
        int result1 = eventRecordProjectMapper.insertSelective(eventRecordProject);
        if (result1 == 0) {
            throw ExceptionUtil.createParamException("报名失败");
        }
        saveTicketRecordUrl(eventRecord.getId());
        return eventRecord;

    }

    /**
     * 拿到recordId之后加密生成短连接
     */
    public void saveTicketRecordUrl(Integer recordId) {
        UrlReq urlReq = new UrlReq();
        urlReq.type = UrlType.NORMAL;
        urlReq.source = cloudEventConfig.getUrl_req_source();
        urlReq.url = cloudEventConfig.getLink_ticket_prefix() +
                Base64Utils.encode(JSONUtil.obj2Json(recordId).getBytes()).toString();
        BaseResult<Url> baseResult = urlClient.get(urlReq);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException("创建短链失败");
        }
        EventRecord eventRecordReq = new EventRecord();
        eventRecordReq.setId(recordId);
        eventRecordReq.setTicketUrl(baseResult.getData().shortUrl);
        eventRecordMapper.updateByPrimaryKeySelective(eventRecordReq);
    }

    /**
     * 更新项目备份表
     *
     * @param moduleType 活动中项目的模板类型,如果是简单版的，需要对传入的数据部分存入
     */
    public void saveProjectBackUp(ProjectImportReq req, Integer eventRecordId, Integer moduleType) {
        if (req.project == null || req.user == null) {
            throw ExceptionUtil.createParamException("备份参数错误");
        }
        ProjectBackupInfoExample example = new ProjectBackupInfoExample();
        example.createCriteria().andPidEqualTo(req.project.pid).andEventRecordIdEqualTo(eventRecordId);
        List<ProjectBackupInfo> projectBackupInfos = projectBackupInfoMapper.selectByExample(example);
        ProjectBackupInfo projectBackupInfo = new ProjectBackupInfo();
        projectBackupInfo.setName(req.project.name);
        projectBackupInfo.setIntro(req.project.intro);
        projectBackupInfo.setLogo(req.project.logo);
        projectBackupInfo.setCityId(req.project.cityId);
        projectBackupInfo.setCityName(req.project.cityName);
        projectBackupInfo.setIndustryId(req.project.industryFirstId);
        projectBackupInfo.setIndustryName(req.project.industryFirstName);
        projectBackupInfo.setProjectCreateUid(req.user.uid);
        projectBackupInfo.setModifyTime(req.modifyTime);
        projectBackupInfo.setEventRecordId(eventRecordId);
        if (req.bp != null) {
            projectBackupInfo.setProjectSignBpId(req.bp.bpId);
            projectBackupInfo.setProjectSignBpName(req.bp.bpName);
            projectBackupInfo.setProjectSignBpUrl(req.bp.bpUrl);
        }
        if (projectBackupInfos == null || projectBackupInfos.isEmpty()) {
            projectBackupInfo.setPid(req.project.pid);
            projectBackupInfo.setCreateTime(req.createTime);
            int result = projectBackupInfoMapper.insertSelective(projectBackupInfo);
            if (result == 0) {
                throw ExceptionUtil.createParamException("保存项目备份失败");
            }
        } else {
            ProjectBackupInfoExample backupInfoExample = new ProjectBackupInfoExample();
            backupInfoExample.createCriteria().andPidEqualTo(req.project.pid).andEventRecordIdEqualTo(eventRecordId);
            int result = projectBackupInfoMapper.updateByExampleSelective(projectBackupInfo, backupInfoExample);
            if (result == 0) {
                throw ExceptionUtil.createParamException("修改项目备份失败");
            }
        }
    }

    public void saveRecordUser(UserReq userReq, Integer eventRecordId) {
        if (userReq == null) {
            return;
        }
        EventRecordUser eventRecordUser = new EventRecordUser();
        eventRecordUser.setCompany(userReq.company);
        eventRecordUser.setPosition(userReq.position);
        eventRecordUser.setName(userReq.name);
        eventRecordUser.setPhone(userReq.mobile);
        eventRecordUser.setUid(userReq.uid);
        eventRecordUser.setEventRecordId(eventRecordId);
        eventRecordUser.setCreateTime(System.currentTimeMillis());
        eventRecordUser.setModifyTime(System.currentTimeMillis());
        eventRecordUserMapper.insertSelective(eventRecordUser);
    }


    public boolean createOrder(Event event, CustomImportReq req, Long expireTime, String tradeNo) {
        if (req.user == null) {
            throw ExceptionUtil.createParamException("user参数异常");
        }
        Order order = new Order();
        order.orderNo = tradeNo;
        order.orderType = SqlEnum.OrderType.TYPE_EVENT.getValue();
        //优惠价格设置为0
        order.discountAmount = 0l;
        order.isonline = SqlEnum.EventLineType.TYPE_OFFLINE.getValue();
        order.platformId = cloudEventConfig.getCommodity_platform_id();
        order.uid = req.user.uid;
        order.failureTime = expireTime;
        order.operEndTime = new Date(event.getEndTime());
        Buyer user = new Buyer();
        user.uid = req.user.uid;
        user.company = req.user.company;
        user.name = req.user.name;
        user.phone = req.user.mobile;
        user.position = req.user.position;
        order.buyer = user;
        List<OrderDetail> orderDetails = new ArrayList<>();
        Map<Integer, Ticket> ticketsMap = commodityRemoteService.converseToTicketMap(event.getCommodityId());
        EventStateCustomExample example = new EventStateCustomExample();
        example.createCriteria().andEventIdEqualTo(event.getId());
        List<EventStateCustom> eventStateCustoms = eventStateCustomMapper.selectByExample(example);
        if (StringUtil.isEmpty(eventStateCustoms)) {
            throw ExceptionUtil.createParamException("EventStateCustom参数异常");
        }
        //免费活动
        if (eventStateCustoms.get(0).getCostType().equals(SqlEnum.CostType.TYPE_CHARGE_NOT.getValue().byteValue())) {
            List<Ticket> tickets = commodityRemoteService.converseToTicket(event.getCommodityId());
            if (tickets.size() != 1) {
                throw ExceptionUtil.createParamException("商品传来的规格参数异常");
            }
            Ticket ticket = tickets.get(0);
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.commodityId = event.getCommodityId();
            orderDetail.standardId = ticket.id;
            orderDetail.price = ticket.price.longValue();
            orderDetail.payPrice = 0l;
            orderDetail.discountAmount = 0l;
            orderDetail.count = 1;
            orderDetails.add(orderDetail);
        }
        //收费
        else {
            OrderDetail orderDetail;
            for (Map.Entry<Integer, Ticket> entry : ticketsMap.entrySet()) {
                Ticket ticket = entry.getValue();
                for (Ticket ticketOld : req.ticketList) {
                    if (!StringUtil.isEmpty(ticket.title) && ticket.title.equals(ticketOld.title) &&
                            ticket.price != null && ticket.price.equals(ticketOld.price)) {
                        if (StringUtil.isEmpty(ticket.intro) && !StringUtil.isEmpty(ticketOld.intro)) {
                            throw ExceptionUtil.createParamException("介绍有问题4");
                        } else if (!StringUtil.isEmpty(ticket.intro) && StringUtil.isEmpty(ticketOld.intro)) {
                            throw ExceptionUtil.createParamException("介绍有问题5");
                        } else if (!StringUtil.isEmpty(ticket.title) && !StringUtil.isEmpty(ticketOld.intro) && !ticket
                                .intro.equals(ticketOld.intro)) {
                            throw ExceptionUtil.createParamException("介绍有问题6");
                        }
                        orderDetail = new OrderDetail();
                        orderDetail.commodityId = event.getCommodityId();
                        orderDetail.standardId = ticket.id;
                        orderDetail.price = ticketsMap.get(ticket.id).price.longValue();
                        orderDetail.payPrice = ticketsMap.get(ticket.id).price.longValue();
                        orderDetail.discountAmount = 0l;
                        orderDetail.count = ticketOld.buyCount;
                        orderDetails.add(orderDetail);
                    }
                }
            }

        }
        order.orderDetails = orderDetails;
        BaseResult<String> baseResult = commodityOrderClient.order(order);
        if (!baseResult.isSuccess()) {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
        return true;
    }


    public void insertByRecord(EventRecord eventRecord) {

        Order order = commodityRemoteService.getOrder(eventRecord.getTradeNo());
        if (eventRecord != null) {
            List<EventTicketOrder> list = new ArrayList<>();
            EventTicketOrder eventTicketOrder;
            for (OrderDetail orderDetail : order.orderDetails) {
                eventTicketOrder = new EventTicketOrder();
                eventTicketOrder.setEventRecordId(eventRecord.getId());
                eventTicketOrder.setEventId(eventRecord.getEventId());
                eventTicketOrder.setSignState(SqlEnum.EventSignStatus.EVENT_SIGN_FALSE.getValue());
                eventTicketOrder.setTicketState(SqlEnum.EventTicketState.EVENT_TICKET_FALSE.getValue());
                eventTicketOrder.setCreateTime(eventRecord.getCreateTime());
                eventTicketOrder.setModifyTime(eventRecord.getModifyTime());
                //票号为商品id+规格id+明细id
                eventTicketOrder.setTicketNo(orderDetail.commodityId.toString() + orderDetail.standardId.toString() +
                        orderDetail.id.toString());
                eventTicketOrder.setTicketId(orderDetail.standardId);
                eventTicketOrder.setCommodityDetailId(orderDetail.id);
                list.add(eventTicketOrder);
            }
            eventTicketOrderMapper.batchInsert(list);

        }
    }


}
