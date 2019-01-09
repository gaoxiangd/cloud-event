package com.welian.service.impl;

import com.welian.beans.account.Organization;
import com.welian.beans.account.User;
import com.welian.beans.chat.GroupChatReq;
import com.welian.beans.cloudevent.AddtionalForm;
import com.welian.beans.cloudevent.Base64KeyReq;
import com.welian.beans.cloudevent.CityReq;
import com.welian.beans.cloudevent.CreateOrUpdateEventConstant;
import com.welian.beans.cloudevent.CustomModule;
import com.welian.beans.cloudevent.EventResp;
import com.welian.beans.cloudevent.EventServiceResp;
import com.welian.beans.cloudevent.ExtensionLinkReq;
import com.welian.beans.cloudevent.ExtensionLinkResp;
import com.welian.beans.cloudevent.Guest;
import com.welian.beans.cloudevent.LocReq;
import com.welian.beans.cloudevent.OldEventReq;
import com.welian.beans.cloudevent.OrgAndEventListResp;
import com.welian.beans.cloudevent.ProjectModule;
import com.welian.beans.cloudevent.PublisherResp;
import com.welian.beans.cloudevent.Sponsor;
import com.welian.beans.cloudevent.Ticket;
import com.welian.beans.cloudevent.TicketInfo;
import com.welian.beans.cloudevent.TicketInfoResp;
import com.welian.beans.cloudevent.TicketResp;
import com.welian.beans.cloudevent.app.AppReq;
import com.welian.beans.cloudevent.app.AppResp;
import com.welian.beans.cloudevent.app.RecordInfoResp;
import com.welian.beans.cloudevent.event.EventInfo;
import com.welian.beans.cloudevent.event.EventList;
import com.welian.beans.cloudevent.event.EventListResp;
import com.welian.beans.cloudevent.event.EventPara;
import com.welian.beans.cloudevent.event.EventSearchPara;
import com.welian.beans.cloudevent.event.PortalStatistics;
import com.welian.beans.cloudevent.event.SaveEventReq;
import com.welian.beans.cloudevent.event.SaveEventResp;
import com.welian.beans.cloudevent.event.UserEventSearchPara;
import com.welian.beans.cloudevent.investor.InvestorGroupReq;
import com.welian.beans.cloudevent.org.OrderChannelReq;
import com.welian.beans.cloudevent.org.OrgAuthResp;
import com.welian.beans.cloudevent.org.OrgInfoResp;
import com.welian.beans.cloudevent.org.OrgReq;
import com.welian.beans.cloudevent.org.OrgResp;
import com.welian.beans.cloudevent.project.BPResp;
import com.welian.beans.cloudevent.project.ProjectResp;
import com.welian.beans.cloudevent.query.EventQuery;
import com.welian.beans.cloudevent.record.EventRecordListResp;
import com.welian.beans.cloudevent.record.EventRecordResp;
import com.welian.beans.cloudevent.record.EventRecordUserResp;
import com.welian.beans.cloudevent.record.RecordsUserResp;
import com.welian.beans.cloudevent.trade.TradeResp;
import com.welian.beans.cloudevent.user.EventServiceReq;
import com.welian.beans.cloudevent.user.EventUserSignUpResp;
import com.welian.beans.cloudevent.user.UserResp;
import com.welian.beans.cloudevent.usermange.UserMangeResp;
import com.welian.beans.tag.TagResp;
import com.welian.client.account.RongChatClient;
import com.welian.client.bpmanage.BpClient;
import com.welian.client.commodity.CommodityOrderClient;
import com.welian.commodity.beans.Order;
import com.welian.commodity.beans.OrderDetail;
import com.welian.config.CloudEventConfig;
import com.welian.config.Constant;
import com.welian.enums.chat.GroupchatType;
import com.welian.enums.cloudevent.EnumRecommendStatus;
import com.welian.enums.cloudevent.EventDataLevel;
import com.welian.enums.cloudevent.ParamEnum;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.mapper.EventAdRelationMapper;
import com.welian.mapper.EventCityRelationMapper;
import com.welian.mapper.EventCustomFieldMapper;
import com.welian.mapper.EventInvestorGroupRelationMapper;
import com.welian.mapper.EventMapper;
import com.welian.mapper.EventRecordCollectionMapper;
import com.welian.mapper.EventRecordMapper;
import com.welian.mapper.EventRecordUserMapper;
import com.welian.mapper.EventSignAuthMapper;
import com.welian.mapper.EventSponsorMapper;
import com.welian.mapper.EventStateCustomMapper;
import com.welian.mapper.EventStateProjectMapper;
import com.welian.mapper.EventTicketOrderMapper;
import com.welian.mapper.EventUidFavoriteRelationMapper;
import com.welian.mapper.ExtensionLinkMapper;
import com.welian.mapper.InvestorGroupMapper;
import com.welian.mapper.OrgInfoMapper;
import com.welian.mapper.ProjectBackupInfoMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventCityRelation;
import com.welian.pojo.EventCityRelationExample;
import com.welian.pojo.EventCustomField;
import com.welian.pojo.EventCustomFieldExample;
import com.welian.pojo.EventExample;
import com.welian.pojo.EventGuest;
import com.welian.pojo.EventInvestorGroupRelation;
import com.welian.pojo.EventInvestorGroupRelationExample;
import com.welian.pojo.EventRecord;
import com.welian.pojo.EventRecordCollection;
import com.welian.pojo.EventRecordCollectionExample;
import com.welian.pojo.EventRecordExample;
import com.welian.pojo.EventRecordUser;
import com.welian.pojo.EventRecordUserExample;
import com.welian.pojo.EventSignAuth;
import com.welian.pojo.EventSponsor;
import com.welian.pojo.EventSponsorManage;
import com.welian.pojo.EventStateCustom;
import com.welian.pojo.EventStateCustomExample;
import com.welian.pojo.EventStateProject;
import com.welian.pojo.EventStateProjectExample;
import com.welian.pojo.EventTicketOrder;
import com.welian.pojo.EventTicketOrderExample;
import com.welian.pojo.EventUidFavoriteRelation;
import com.welian.pojo.EventUidFavoriteRelationExample;
import com.welian.pojo.ExtensionLink;
import com.welian.pojo.ExtensionLinkExample;
import com.welian.pojo.InvestorGroup;
import com.welian.pojo.InvestorGroupExample;
import com.welian.pojo.OrgInfo;
import com.welian.pojo.OrgInfoExample;
import com.welian.pojo.ProjectBackupInfo;
import com.welian.pojo.ProjectBackupInfoExample;
import com.welian.service.CommodityRemoteService;
import com.welian.service.CustomFormModuleService;
import com.welian.service.EventCityRelationModule;
import com.welian.service.GuestModuleService;
import com.welian.service.ProjectService;
import com.welian.service.SolrService;
import com.welian.service.SponsorsModuleService;
import com.welian.service.TagModuleService;
import com.welian.service.TicketOrderService;
import com.welian.service.UserService;
import com.welian.service.record.impl.IEventRecordProjectInfoImpl;
import com.welian.service.task.ScheduledTask;
import com.welian.utils.Base64Utils;
import com.welian.utils.DateUtil;
import com.welian.utils.DateUtils;
import com.welian.utils.ExceptionUtil;
import com.welian.utils.PagingUtil;
import org.sean.framework.annotation.Synchronized;
import org.sean.framework.bean.BaseResult;
import org.sean.framework.bean.PageData;
import org.sean.framework.service.RedisService;
import org.sean.framework.util.JSONUtil;
import org.sean.framework.util.Logger;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

/**
 * Created by dayangshu on 17/7/4.
 */
@Service("eventService")
public class EventServiceImpl  {
    private static final Logger logger = Logger.newInstance(EventServiceImpl.class);
    @Autowired
    private CloudEventConfig cloudEventConfig;

    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private EventStateProjectMapper eventStateProjectMapper;
    @Autowired
    private InvestorGroupMapper investorGroupMapper;
    @Autowired
    private EventInvestorGroupRelationMapper eventInvestorGroupRelationMapper;
    @Autowired
    private OrgInfoMapper orgInfoMapper;
    @Autowired
    private EventRecordMapper eventRecordMapper;
    @Autowired
    private EventUidFavoriteRelationMapper favoriteRelationMapper;
    @Autowired
    private ExtensionLinkMapper extensionLinkMapper;
    @Autowired
    private EventRecordUserMapper eventRecordUserMapper;
    @Autowired
    private EventCustomFieldMapper eventCustomFieldMapper;
    @Autowired
    private EventRecordCollectionMapper eventRecordCollectionMapper;
    @Autowired
    private ProjectBackupInfoMapper projectBackupInfoMapper;
    @Autowired
    private SponsorsModuleService sponsorsModuleService;
    @Autowired
    private GuestModuleService guestModuleService;
    @Autowired
    private StateProjectModuleServiceImpl stateProjectModuleService;
    @Autowired
    private StateCustomModuleService stateCustomModuleService;
    @Autowired
    private CustomFormModuleService customFormModuleService;
    @Autowired
    private TagModuleService tagModuleService;
    @Autowired
    private ExtensionLinkServiceImpl extensionLinkService;
    @Autowired
    private RongChatClient rongChatClient;
    @Autowired
    private EventRecordServiceImpl eventRecordService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrgPrivilegeRemoteServiceImpl OrgRemoteService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ManageServiceImpl manageService;
    @Autowired
    private IEventRecordProjectInfoImpl iEventRecordProjectInfo;
    @Autowired
    private SolrService solrService;
    @Autowired
    private ScheduledTask scheduledTask;
    @Autowired
    private EventCityRelationModule eventCityRelationModule;
    @Autowired
    private CityRemoteServiceImpl cityRemoteService;
    @Autowired
    private CommodityRemoteService commodityRemoteService;
    @Autowired
    private EventTicketOrderMapper eventTicketOrderMapper;

    @Autowired
    private CommodityOrderClient commodityOrderClient;
    @Autowired
    private EventStateCustomMapper eventStateCustomMapper;
    @Autowired
    private OrgServiceImpl orgService;
    @Autowired
    private EventSignAuthMapper eventSignAuthMapper;

    @Autowired
    private TicketOrderService ticketOrderService;
    @Autowired
    private EventCityRelationMapper eventCityRelationMapper;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private EventSponsorMapper eventSponsorMapper;

    @Transactional
    public SaveEventResp saveEvent(SaveEventReq saveEventReq, Integer uid, Integer sourceFrom) {
        checkEventParamWithException(saveEventReq,uid);
        EventPara eventReq = saveEventReq.event;
        Integer count = orgInfoMapper.selectByNewId(saveEventReq.orgId);
        if (count == 0) {
            throw ExceptionUtil.createParamException("机构不存在");
        }
        Event event = new Event();
        switch (eventReq.eventType) {
            case 1:
                event.setTemplateId(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue());
                break;
            case 2:
                event.setTemplateId(SqlEnum.EventType.TYPE_EVENT_ROADSHOW.getValue());//路演大赛
                break;
            case 3:
                event.setTemplateId(SqlEnum.EventType.TYPE_EVENT_FINANCE_DELIVERY.getValue());//融资投递活动
                break;
            default:
                throw ExceptionUtil.createParamException("参数错误");
        }
        event.setTitle(eventReq.title);
        event.setLineType(eventReq.lineType);
        event.setAddress(eventReq.address);
        event.setDeadlineTime(eventReq.deadlineTime);
        event.setStartTime(eventReq.startTime);
        event.setEndTime(eventReq.endTime);
        event.setLogo(eventReq.logo);
        event.setDetail(eventReq.detail);
        event.setOpenExtension(eventReq.isPromotion);
        event.setOrgId(saveEventReq.orgId);
        if (eventReq.state != null && eventReq.state != SqlEnum.EventStateType.TYPE_EVENT_PUBLISH_NOT.getByteValue() &&
                eventReq.state != SqlEnum.EventStateType.TYPE_EVENT_PUBLISHED.getByteValue()) {
            throw ExceptionUtil.createParamException("state参数错误");
        }
        event.setState(eventReq.state);
        event.setModifyTime(System.currentTimeMillis());
        if (eventReq.city != null) {
            event.setCityId(eventReq.city.id);
            event.setCityName(eventReq.city.name);
        }
        if (SqlEnum.EventLineType.TYPE_ONLINE.getByteValue().equals(eventReq.lineType)) {
            event.setCityId(Constant.DEFAULT_NOT_NULL_NUMBER);
            event.setCityName(Constant.DEFAULT_NOT_NULL_STRING);
        }
        //地址位置
        if (eventReq.loc != null) {
            event.setLongitude(eventReq.loc.lng);
            event.setLatitude(eventReq.loc.lat);
        }
        SaveEventResp resp = new SaveEventResp();
        //未认证机构不能创建收费活动,运营后台没穿isCharge字段，兼容
        if (sourceFrom.equals(ParamEnum.EventRequestSource.SOURCE_FROM_CLOUD.getValue()) && event.getTemplateId()
                .equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue()) && saveEventReq.event.customModule.isCharge
                .equals
                        (SqlEnum.CostType.TYPE_CHARGE.getValue())) {
            checkOrg(saveEventReq.orgId);
        }
        boolean hasRecord = eventRecordService.checkHasRecord(eventReq.eventId);
        boolean hasToPayOrder = eventRecordService.checkHasRecord(eventReq.eventId);

        Integer origEventType = 0;
        if (NumberUtil.isValidId(eventReq.eventId)) {
            //修改活动
            Event origEvent = getEvent(eventReq.eventId);
            origEventType = origEvent.getTemplateId();
            modifyEvent(eventReq,sourceFrom,hasRecord,hasToPayOrder,origEvent,resp,event);
        } else {
            //新增活动
            addEvent(eventReq,event,uid,resp);
        }
        //保存活动的主办方
        sponsorsModuleService.save(eventReq.eventId, eventReq.sponsors);
        //保存活动的嘉宾
        guestModuleService.save(eventReq.eventId, eventReq.guests);
        if (!hasRecord && !hasToPayOrder) {
            //保存活动的自定义表单
            customFormModuleService.save(eventReq.eventId, eventReq.additionalForm);
        }
        //保存活动的标签
        tagModuleService.save(eventReq.eventId, eventReq.tags);
        resp.eventId = eventReq.eventId;
        //如果是一键融资活动，保存一键融资服务
        switch (eventReq.eventType) {
            case 3:
                if (saveEventReq.requestSource == ParamEnum.RequestSource.TYPE_FROM_MANAGE.getValue()) {
                    break;
                }
                if (saveEventReq.event.financingService == null) {
                    throw ExceptionUtil.createParamException("请填写一键融资服务信息");
                }
                saveEventReq.event.financingService.eventId = eventReq.eventId;
                reviseService(saveEventReq.event.financingService);
                break;
        }
        //如果关闭融资服务，删除投资人分组关系
        if (origEventType.equals(SqlEnum.EventType.TYPE_EVENT_FINANCE_DELIVERY.getValue()) && !eventReq.eventType
                .equals(SqlEnum.EventType.TYPE_EVENT_FINANCE_DELIVERY.getValue())) {
            deleteGroupSetting(eventReq.eventId);
        }
        return resp;
    }

    /**
     * 新增活动
     * @param eventReq 活动入参
     * @param event 封装修改的活动属性
     * @param uid 用户id
     * @param resp 返回参数
     */
    private void addEvent(EventPara eventReq, Event event, Integer uid, SaveEventResp resp) {
        event.setPublishUid(uid);
        event.setCreateTime(System.currentTimeMillis());
        int num = eventMapper.insertSelective(event);
        if (!NumberUtil.isValidId(num)) {
            throw ExceptionUtil.createParamException("保存活动失败");
        }
        eventReq.eventId = event.getId();
        //给发布者加一个签到权限
        EventSignAuth eventSignAuth = new EventSignAuth();
        eventSignAuth.setEventId(event.getId());
        eventSignAuth.setUid(uid);
        eventSignAuth.setStatus(SqlEnum.EventSignAuthStatus.AUTH.getByteValue());
        eventSignAuth.setCreateTime(DateUtil.getNowDate());
        eventSignAuth.setModifyTime(DateUtil.getNowDate());
        eventSignAuthMapper.insertSelective(eventSignAuth);
        event.setCreateTime(System.currentTimeMillis());
        //创建活动的url
        ExtensionLink extensionLink = extensionLinkService.createDefaultExtensionLinkChannelEventId(event.getId());
        if (extensionLink != null) {
            ExtensionLinkResp extensionLinkResp = new ExtensionLinkResp();
            extensionLinkResp.uniqueKey = extensionLink.getUniqueKey();

            resp.linkUrlCommon = getLinkUrlCommon(eventReq.eventType.intValue(), extensionLinkResp);

            resp.commonUrl = extensionLink.getLinkUrlCommon();
        }
        Long startTimes = System.currentTimeMillis();
        //只有开启群聊状态打开时创建活动群聊
        if (eventReq.customModule != null && SqlEnum.GroupChatOpenType.TYPE_OPEN.getValue().equals(eventReq
                .customModule.isGroupChat) || (eventReq.projectModule != null &&
                SqlEnum.GroupChatOpenType.TYPE_OPEN.getByteValue().equals(eventReq.projectModule.isGroupChat))) {
            createGroupChat(eventReq, event);
        }
        logger.info("活动群聊创建时间：" + (System.currentTimeMillis() - startTimes) + "毫秒");
        //创业活动 保存活动的票券设置
        if (eventReq.eventType.equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getByteValue())) {
            //生成活动观众签到授权码
            eventReq.customModule.code = getRandomDigital();
            stateCustomModuleService.save(eventReq.eventId, eventReq.customModule);
        } else {
            //融资活动或者路演大赛 保存活动的项目报名的设置
            stateProjectModuleService.save(eventReq.eventId, eventReq.projectModule);
        }
        //在推荐热门表生成一条数据，方便线上活动的update操作
        manageService.insertDefaultHotAndRcm(eventReq.eventId);
        //调商品服务生成商品号
        Integer commodityId = commodityRemoteService.CreateCommodity(eventReq);
        if (NumberUtil.isInValidId(commodityId)) {
            throw ExceptionUtil.createParamException("commodityId参数异常");
        }
        event.setCommodityId(commodityId);
        eventMapper.updateByPrimaryKeySelective(event);
        resp.eventReq = eventReq;
    }

    /**
     * 修改活动
     * @param eventReq 活动入参
     * @param sourceFrom 来源
     * @param hasRecord 是否有报名记录
     * @param hasToPayOrder 是否有未完成订单
     * @param origEvent 数据库找到的原来活动信息
     * @param resp 返回参数
     * @param event 封装修改的活动属性
     */
    private void modifyEvent(EventPara eventReq, Integer sourceFrom, boolean hasRecord, boolean hasToPayOrder,
                             Event origEvent, SaveEventResp resp, Event event) {
        if (origEvent == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        //活动已经结束，不允许修改
        if (event.getEndTime() < DateUtil.getNowDate().getTime()) {
            throw ExceptionUtil.createWarnException("该活动已结束,不允许修改");
        }
        //修改活动状态要判断是否有报名记录
        if (eventReq.state != null && eventReq.state.equals(SqlEnum.EventStateType.TYPE_EVENT_PUBLISH_NOT
                .getByteValue())) {
            //如果有报名记录，需要把所有的报名记录全部删除，才能取消发布
            if (hasRecord || hasToPayOrder) {
                throw ExceptionUtil.createWarnException("存在报名或者未付款记录，不能取消发布！");
            }
        }
        event.setId(eventReq.eventId);
        event.setModifyTime(System.currentTimeMillis());
        Long origEndTime = origEvent.getEndTime();
        eventMapper.updateByPrimaryKeySelective(event);
        Event event1 = getEvent(event.getId());
        //创业活动
        if (eventReq.eventType.equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getByteValue())) {
            //创建活动群聊
            EventStateCustom eventStateCustom = stateCustomModuleService.get(eventReq.eventId);
            if (eventReq.customModule != null &&
                    eventReq.customModule.isGroupChat.byteValue() == Constant.GROUP_CHART_OPEN &&
                    eventStateCustom != null && StringUtil.isEmpty(eventStateCustom.getGroupChat())) {
                createGroupChat(eventReq, event1);
            }
            //调用商品接口验证票券,更新票券信息,运营后台不支持修改票券信息
            if (sourceFrom.equals(ParamEnum.EventRequestSource.SOURCE_FROM_CLOUD.getValue())) {
                checkAndUpdateTicket(eventReq, event.getId());
            }
            //更新设置
            stateCustomModuleService.updateStateCustom(eventReq.customModule, eventReq.eventId);
        }
        //路演大赛
        else {
            //创建活动群聊
            EventStateProject eventStateProject = stateProjectModuleService.get(eventReq
                    .eventId);
            if (eventReq.projectModule != null &&
                    eventReq.projectModule.isGroupChat.byteValue() == Constant.GROUP_CHART_OPEN &&
                    eventStateProject != null && StringUtil.isEmpty(eventStateProject.getGroupChat())) {
                createGroupChat(eventReq, event1);
            }
            //更新设置
            stateProjectModuleService.updateStateProject(eventReq.projectModule, eventReq.eventId);
            checkRecordCount(eventReq);
            commodityRemoteService.updateCommodityForFree(eventReq, event1.getCommodityId());
        }
        ExtensionLinkExample example = new ExtensionLinkExample();
        example.createCriteria().andEventIdEqualTo(event.getId());
        example.setOrderByClause("id asc");
        List<ExtensionLink> extensionLinkList = extensionLinkMapper.selectByExample(example);
        if (extensionLinkList != null && extensionLinkList.size() > 0) {
            ExtensionLinkResp extensionLinkResp = new ExtensionLinkResp();
            extensionLinkResp.uniqueKey = extensionLinkList.get(0).getUniqueKey();
            resp.linkUrlCommon = getLinkUrlCommon(eventReq.eventType.intValue(), extensionLinkResp);
            resp.commonUrl = extensionLinkList.get(0).getLinkUrlCommon();
        }
        sponsorsModuleService.delete(eventReq.eventId);
        guestModuleService.delete(eventReq.eventId);
        tagModuleService.delete(eventReq.eventId);
        boolean hasRecordFlag = eventRecordService.checkHasRecord(eventReq.eventId);
        if (hasRecordFlag) {
            if (checkAdditionFormUpdate(eventReq.additionalForm, eventReq.eventId)) {
                throw ExceptionUtil.createWarnException("此活动已有报名记录，活动表单不能再更改");
            }
        } else {
            customFormModuleService.delete(eventReq.eventId);
        }
        //不管是哪种活动，修改活动的结束时间需要去商品更新商品下架时间
        changeCommodityEndTime(origEvent.getCommodityId(), event.getEndTime(), origEndTime);
        // TODO: 2018/1/16 更新solr
        resp.eventReq = eventReq;
    }

    /**
     * 保存活动时校验活动参数
     * @param saveEventReq 活动入参
     * @param uid 用户id
     */
    private void checkEventParamWithException(SaveEventReq saveEventReq,Integer uid) {
        if (!NumberUtil.isValidId(saveEventReq.requestSource)) {
            throw ExceptionUtil.createParamException("requestSource参数错误");
        }
        if (!NumberUtil.isValidId(uid) && saveEventReq.requestSource == ParamEnum.RequestSource.TYPE_FROM_CLOUD
                .getValue()) {
            throw ExceptionUtil.createParamException("请登录");
        }
        if (!NumberUtil.isValidId(saveEventReq.orgId)) {
            throw ExceptionUtil.createParamException("机构参数错误");
        }
        if (saveEventReq.event == null) {
            throw ExceptionUtil.createParamException("请输入活动内容");
        }
        Byte eventType = saveEventReq.event.eventType;
        if (eventType == null) {
            throw ExceptionUtil.createParamException("eventType参数异常");
        }
        if (!eventType.equals(SqlEnum.EventType.TYPE_EVENT_ROADSHOW.getByteValue()) &&
                !eventType.equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getByteValue()) &&
                !eventType.equals(SqlEnum.EventType.TYPE_EVENT_FINANCE_DELIVERY.getByteValue())) {
            throw ExceptionUtil.createParamException("eventType参数异常");
        }
        if (eventType.equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getByteValue())) {
            //创业活动，置空projectModule字段
            saveEventReq.event.projectModule = null;
        } else {
            //路演或者融资活动置空customModule字段
            saveEventReq.event.customModule = null;
        }
        if (StringUtil.isEmpty(saveEventReq.event.title)) {
            throw ExceptionUtil.createParamException("请填写活动标题");
        }

        if (saveEventReq.event.title.length() > 50 || saveEventReq.event.title.length() < 5) {
            throw ExceptionUtil.createParamException("活动标题字数小于5字或者大于50字");
        }
        if (saveEventReq.event.lineType == null) {
            throw ExceptionUtil.createParamException("请选择活动line类型");
        }
        //如果是线下活动，必须要填写举办城市和举办地以及百度经纬度坐标
        if (saveEventReq.event.lineType == SqlEnum.EventLineType.TYPE_OFFLINE.getByteValue().byteValue()) {
            if (saveEventReq.event.city == null || saveEventReq.event.city.id == null) {
                throw ExceptionUtil.createParamException("请选择活动举办城市");
            }
            if (StringUtil.isEmpty(saveEventReq.event.address)) {
                throw ExceptionUtil.createParamException("请填写活动举办地址");
            }
            if (saveEventReq.event.loc == null || saveEventReq.event.loc.lat == null || saveEventReq.event.loc.lng ==
                    null) {
                throw ExceptionUtil.createParamException("请定位活动地图坐标");
            }
        }
        if (!NumberUtil.isValidId(saveEventReq.event.startTime)) {
            throw ExceptionUtil.createParamException("请选择活动开始时间");
        }
        if (!NumberUtil.isValidId(saveEventReq.event.endTime)) {
            throw ExceptionUtil.createParamException("请选择活动结束时间");
        }
        if (!NumberUtil.isValidId(saveEventReq.event.deadlineTime)) {
            // throw ExceptionUtil.createParamException("请选择活动报名截止时间");
            saveEventReq.event.deadlineTime = saveEventReq.event.startTime;
        }
        if (saveEventReq.event.startTime >= saveEventReq.event.endTime) {
            throw ExceptionUtil.createParamException("活动开始时间必须小于活动结束时间");
        }
        if (saveEventReq.event.deadlineTime >= saveEventReq.event.endTime) {
            throw ExceptionUtil.createParamException("活动报名截止时间必须小于活动结束时间");
        }
        if (saveEventReq.event.endTime <= System.currentTimeMillis()) {
            throw ExceptionUtil.createParamException("活动结束时间要大于当前时间");
        }
        if (StringUtil.isEmpty(saveEventReq.event.logo)) {
            throw ExceptionUtil.createParamException("请上传活动海报");
        }
        if (StringUtil.isEmpty(saveEventReq.event.detail)) {
            throw ExceptionUtil.createParamException("请填写活动详情");
        }
        if (saveEventReq.event.sponsors == null || saveEventReq.event.sponsors.size() == 0) {
            throw ExceptionUtil.createParamException("主办方参数错误");
        }
        if (saveEventReq.event.city == null) {
            throw ExceptionUtil.createParamException("城市参数错误");
        }
        if (saveEventReq.event.projectModule == null &&
                (saveEventReq.event.eventType.equals(SqlEnum.EventType.TYPE_EVENT_ROADSHOW.getByteValue()) ||
                        saveEventReq.event.eventType.equals(SqlEnum.EventType.TYPE_EVENT_FINANCE_DELIVERY
                                .getByteValue()))) {
            throw ExceptionUtil.createParamException("项目报名参数错误");
        }
        if (saveEventReq.event.customModule == null &&
                (saveEventReq.event.eventType.equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getByteValue()))) {
            throw ExceptionUtil.createParamException("报名票券参数错误");
        }
    }

    /**
     * 修改活动的结束时间需要去商品更新商品下架时间
     *
     * @param commodityId
     * @param endTime
     * @param origEndTime
     */
    private void changeCommodityEndTime(Integer commodityId, Long endTime, Long origEndTime) {
        if (endTime == null) {
            throw ExceptionUtil.createParamException("endTime参数异常");
        }
        //只有结束时间改变了才更新
        if (!origEndTime.equals(endTime)) {
            commodityRemoteService.changeCommodityEndTime(commodityId, endTime);
        }
    }

    private void checkOrg(Integer orgId) {

        OrderChannelReq req = new OrderChannelReq();
        req.orgId = orgId;
        OrgInfoResp resp = orgService.getInfoById(req);
        if (!resp.auth.verifyStatus.equals(SqlEnum.OrgVerifyStatus.CHECK_PASS.getValue())) {
            throw ExceptionUtil.createWarnException("只有通过认证的机构可以发布收费活动");
        }

    }

    private String getLinkUrlCommon(Integer eventType, ExtensionLinkResp extensionLinkResp) {
        //方便前端处理，返回长链,加入pre代表预览，前端用来隐藏掉报名按钮

        String linkUrlCommon = cloudEventConfig.getLink_common_prefix() +
                Base64Utils.encode(extensionLinkResp.uniqueKey.getBytes()).toString();

        return linkUrlCommon;
    }

    private void checkAndUpdateTicket(EventPara eventReq, Integer eventId) {
        Event event = getEvent(eventId);
        List<Ticket> ticketList = eventReq.customModule.ticketList;
        if (eventReq.customModule.isCharge.equals(SqlEnum.CostType.TYPE_CHARGE.getValue())) {
            if (StringUtil.isEmpty(ticketList)) {
                throw ExceptionUtil.createParamException("请设置你的票种信息");
            }
            commodityRemoteService.updateCommodity(eventReq, event.getCommodityId());
        }
        //免费活动也要update
        else {
            //如果之前是免费的改为收费的，要做校验
            if (stateCustomModuleService.get(eventId).getCostType().equals(
                    SqlEnum.CostType.TYPE_CHARGE.getByteValue())) {
                if (eventRecordService.checkHasRecord(eventId) || eventRecordService.checkHasToPayOrder(eventId)) {
                    throw ExceptionUtil.createParamException("存在报名记录或未付款订单");
                }
            }
            commodityRemoteService.updateCommodityForFree(eventReq, event.getCommodityId());
        }
    }


    private void createGroupChat(EventPara eventReq, Event event) {
        if (!StringUtil.isEmpty(event.getTitle()) && NumberUtil.isValidId(event.getId()) && NumberUtil.isValidId
                (event.getPublishUid())) {
            GroupChatReq groupChatReq = new GroupChatReq();
            groupChatReq.name = event.getTitle();
            groupChatReq.relationId = event.getId();
            groupChatReq.uid = event.getPublishUid();
            groupChatReq.type = GroupchatType.ACTIVE.getValue();
            groupChatReq.logo = event.getLogo();
            BaseResult<String> baseResult = rongChatClient.create(groupChatReq);
            if (baseResult.getData() != null) {
                if (eventReq.eventType.equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getByteValue())) {
                    eventReq.customModule.groupChatNumber = baseResult.getData();
                } else {
                    eventReq.projectModule.groupChatNumber = baseResult.getData();
                }

            }
        }
    }


    public Event getEvent(int eventId) {
        return eventMapper.selectByPrimaryKey(eventId);
    }

    public Integer getEventJoinedCount(Integer eventId) {
        Event event = getEvent(eventId);
        if (event == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())) {
            EventStateCustom eventStateCustom = stateCustomModuleService.get(eventId);
            if (eventStateCustom == null) {
                throw ExceptionUtil.createParamException("找不到该EventStateCustom");
            }
            return eventStateCustom.getJoinedCount();
        } else {
            EventStateProject eventStateProject = stateProjectModuleService.get(eventId);
            if (eventStateProject == null) {
                throw ExceptionUtil.createParamException("找不到该ventStateProject");
            }
            return eventStateProject.getJoinedCount();
        }
    }


    @Transactional
    public void updateEvent(Event event) {
        if (event != null) {
            eventMapper.updateByPrimaryKeySelective(event);
            //更新solr
            solrService.updateSolrInfoById(event.getId().toString(), "state", event.getState());
        }
    }


    public EventPara conversePara(Event event, Integer level) {
        if (event != null) {
            EventPara eventPara = new EventPara();

            eventPara.title = event.getTitle();
            eventPara.city = new CityReq();
            eventPara.city.id = event.getCityId();
            eventPara.city.name = event.getCityName();
            eventPara.eventType = event.getTemplateId().byteValue();
            eventPara.lineType = event.getLineType();
            eventPara.startTime = event.getStartTime();
            eventPara.endTime = event.getEndTime();
            eventPara.address = event.getAddress();
            if (event.getEndTime() != null && event.getEndTime() < System.currentTimeMillis()) {
                eventPara.state = SqlEnum.EventStateType.TYPE_EVENT_FINISHED.getByteValue();
            } else {
                eventPara.state = event.getState();
            }
            eventPara.originalState = event.getState() == null ? null : event.getState().intValue();
            User profile = userService.getUserInfoById(event.getPublishUid());
            if (profile != null) {
                PublisherResp publisher = new PublisherResp();
                publisher.id = event.getPublishUid();
                publisher.name = profile.name;
                eventPara.publisher = publisher;
            }

            if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())) {
                eventPara.customModule = stateCustomModuleService.conversePara(stateCustomModuleService.get(event
                        .getId()));
            } else {
                eventPara.projectModule = stateProjectModuleService.conversePara(stateProjectModuleService.get(event
                        .getId()));
            }

            eventPara.deadlineTime = event.getDeadlineTime();
            eventPara.logo = cloudEventConfig.getImage_prefix() + event.getLogo();
            eventPara.isPromotion = event.getOpenExtension();
            eventPara.eventId = event.getId();
            eventPara.id = event.getId();
            LocReq loc = new LocReq();
            loc.lat = event.getLatitude();
            loc.lng = event.getLongitude();
            eventPara.loc = loc;

            switch (level) {
                case 1:
                    return eventPara;
            }

            eventPara.detail = event.getDetail();
            return eventPara;
        }
        return null;
    }



    public EventPara converseParaSimple(Event event) {
        if (event != null) {
            EventPara eventPara = new EventPara();
            eventPara.title = event.getTitle();
            eventPara.city = new CityReq();
            eventPara.city.id = event.getCityId();
            eventPara.city.name = event.getCityName();
            eventPara.eventType = event.getTemplateId().byteValue();
            eventPara.lineType = event.getLineType();
            eventPara.startTime = event.getStartTime();
            eventPara.endTime = event.getEndTime();
            eventPara.address = event.getAddress();
            eventPara.commodityId = event.getCommodityId();
            if (event.getEndTime() != null && event.getEndTime() < System.currentTimeMillis()) {
                eventPara.state = SqlEnum.EventStateType.TYPE_EVENT_FINISHED.getByteValue();
            } else {
                eventPara.state = event.getState();
            }
            eventPara.originalState = event.getState() == null ? null : event.getState().intValue();
            eventPara.deadlineTime = event.getDeadlineTime();
            eventPara.logo = cloudEventConfig.getImage_prefix() + event.getLogo();
            eventPara.isPromotion = event.getOpenExtension();
            eventPara.eventId = event.getId();
            eventPara.id = event.getId();
            LocReq loc = new LocReq();
            loc.lat = event.getLatitude();
            loc.lng = event.getLongitude();
            eventPara.loc = loc;

            PublisherResp publisher = new PublisherResp();
            publisher.id = event.getPublishUid();
            eventPara.publisher = publisher;

            return eventPara;
        }
        return null;
    }

    public EventServiceResp getService(Integer eventId) {
        if (!NumberUtil.isValidId(eventId)) {
            throw ExceptionUtil.createParamException("活动id参数错误");
        }
        EventServiceResp eventActivityResp = new EventServiceResp();
        //取关联的广告列表
        eventActivityResp.eventId = eventId;
        return eventActivityResp;
    }


    public EventServiceReq getFinancingService(Integer eventId) {
        if (!NumberUtil.isValidId(eventId)) {
            throw ExceptionUtil.createParamException("活动id参数错误");
        }
        EventServiceReq eventActivityResp = new EventServiceReq();
        EventStateProjectExample eventStateProjectExample = new EventStateProjectExample();
        eventStateProjectExample.createCriteria().andEventIdEqualTo(eventId);
        List<EventStateProject> eventStateProjects = eventStateProjectMapper.selectByExample(eventStateProjectExample);
        if (eventStateProjects == null || eventStateProjects.isEmpty()) {
            eventActivityResp.isOpenFinancingService = CreateOrUpdateEventConstant.OpenFinancingServiceType.NOT_OPEN;
            eventActivityResp.investorMatchCount = 0;
        } else {
            EventStateProject eventActivity = eventStateProjects.get(0);
            eventActivityResp.isOpenFinancingService = (int) eventActivity.getIsOpenFinancing();
            //一键融资服务
            if ((int) eventActivity.getIsOpenFinancing() == CreateOrUpdateEventConstant.OpenFinancingServiceType.OPEN) {
                eventActivityResp.investorMatchCount = eventActivity.getInvestorMatchCount();
                eventActivityResp.groupSetting = (int) eventActivity.getGroupSetting();
                if ((int) eventActivity.getGroupSetting() == CreateOrUpdateEventConstant.GroupSettingType.USE_CUSTOM) {
                    EventInvestorGroupRelationExample eventInvestorGroupRelationExample = new
                            EventInvestorGroupRelationExample();
                    eventInvestorGroupRelationExample.createCriteria().andEventIdEqualTo(eventId);
                    List<EventInvestorGroupRelation> eventInvestorGroupRelations = eventInvestorGroupRelationMapper.
                            selectByExample(eventInvestorGroupRelationExample);
                    if (eventInvestorGroupRelations == null || eventInvestorGroupRelations.isEmpty()) {
                        eventActivityResp.investorGroups = new ArrayList<>();
                    } else {
                        List<Integer> integers = new ArrayList<>();
                        for (EventInvestorGroupRelation relation : eventInvestorGroupRelations) {
                            integers.add(relation.getInvestorGroupId());
                        }
                        InvestorGroupExample investorGroupExample = new InvestorGroupExample();
                        investorGroupExample.createCriteria().andStateEqualTo((byte) 0).andIdIn(integers);
                        List<InvestorGroup> investorGroups = investorGroupMapper.selectByExample(investorGroupExample);
                        if (investorGroups != null && !investorGroups.isEmpty()) {
                            List<InvestorGroupReq> investorGroupResps = new ArrayList<>();
                            InvestorGroupReq investorGroupResp;
                            for (InvestorGroup investorGroup : investorGroups) {
                                investorGroupResp = new InvestorGroupReq();
                                investorGroupResp.investorGroupId = investorGroup.getId();
                                investorGroupResp.investorGroupName = investorGroup.getGroupName();
                                investorGroupResp.investorCount = investorGroup.getCount();
                                investorGroupResps.add(investorGroupResp);
                            }
                            eventActivityResp.investorGroups = investorGroupResps;
                        }
                    }

                }
            }
        }
        return eventActivityResp;
    }


    @Transactional
    public Object reviseService(EventServiceReq req) {
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("活动id参数错误");
        }
        if (!NumberUtil.isValidId(req.investorMatchCount)) {
            throw ExceptionUtil.createParamException("匹配投资人的个数需为1-20之间");
        }
        if (req.investorMatchCount > 20 || req.investorMatchCount < 1) {
            throw ExceptionUtil.createParamException("匹配投资人的个数需为1-20之间");
        }
        if (req.isOpenFinancingService == null) {
            throw ExceptionUtil.createParamException("isOpenFinancingService参数错误");
        }
        //当开启一键融资服务时，必须设置分组设置
        if (req.isOpenFinancingService == CreateOrUpdateEventConstant.OpenFinancingServiceType.OPEN) {
            if (req.groupSetting == null) {
                throw ExceptionUtil.createParamException("groupSetting参数错误");
            }
            if (req.groupSetting == 1) {
                if (req.investorGroups == null || req.investorGroups.isEmpty()) {
                    throw ExceptionUtil.createParamException("请设置自定义投资人分组");
                }
            }
        }
        //如果类型不是融资投递类型的活动，不能进行更改
        Event event = eventMapper.selectByPrimaryKey(req.eventId);
        if (event.getState() != null &&
                event.getState().intValue() == SqlEnum.EventStateType.TYPE_EVENT_DELETED.getValue()) {
            throw ExceptionUtil.createParamException("活动已删除");
        }
        if (event.getTemplateId() != null &&
                event.getTemplateId().intValue() != SqlEnum.EventType.TYPE_EVENT_FINANCE_DELIVERY.getValue()) {
            throw ExceptionUtil.createParamException("此活动不是融资投递活动，不能修改");
        }
        EventStateProject eventStateProject = new EventStateProject();
        //保存匹配投资人的个数
        eventStateProject.setInvestorMatchCount(req.investorMatchCount);
        //一键融资活动全部都开启
        eventStateProject.setIsOpenFinancing((byte) CreateOrUpdateEventConstant.OpenFinancingServiceType.OPEN);
        //重新保存之前，需要先清空之前存在的活动与分组之间的关联关系
        deleteGroupSetting(req.eventId);
        //如果是开启一键融资服务，要进行设置投资人匹配数量，分组信息
        if (req.isOpenFinancingService == CreateOrUpdateEventConstant.OpenFinancingServiceType.OPEN) {
            //保存分组设置
            eventStateProject.setGroupSetting(req.groupSetting.byteValue());
            //如果是使用自定义的分组，保存分组配置
            if (req.groupSetting == CreateOrUpdateEventConstant.GroupSettingType.USE_CUSTOM) {
                //将分组id和事件活动id进行关联
                reviseGroupSetting(req.investorGroups, req.eventId);
            }
        } else {
            eventStateProject.setGroupSetting((byte) CreateOrUpdateEventConstant.GroupSettingType.USE_WELIAN_DEFAULT);
        }
        //更新此活动的数据
        EventStateProjectExample example = new EventStateProjectExample();
        example.createCriteria().andEventIdEqualTo(req.eventId);
        int result = eventStateProjectMapper.updateByExampleSelective(eventStateProject, example);
        if (result == 0) {
            throw ExceptionUtil.createParamException("更新失败");
        }
        return null;
    }


    public EventListResp searchEventList(EventSearchPara eventSearchPara) {
        eventSearchPara.page = PagingUtil.getStart(eventSearchPara.page, eventSearchPara.size);
        List<Event> eventList = eventMapper.selectEventByPara(eventSearchPara);

        EventListResp eventListResp = new EventListResp();

        EventSearchPara eventSearchParaTmp = new EventSearchPara();
        eventSearchParaTmp.orgId = eventSearchPara.orgId;
        eventListResp.allTotal = getEventTotalByOrgId(eventSearchParaTmp);

        if (eventList == null || eventList.isEmpty()) {
            eventListResp.list = new ArrayList<>();
            eventListResp.total = 0;
            return eventListResp;
        }
        eventListResp.list = getEventListResp(eventList, eventSearchPara.level);
        eventListResp.total = getEventTotalByOrgId(eventSearchPara);
        return eventListResp;
    }

    /**
     * 获取全部活动列表信息
     *
     * @param eventSearchPara
     * @return
     */

    public Object getAllSimpleEventList(EventSearchPara eventSearchPara) {
        EventExample example = new EventExample();
        example.setOrderByClause("modify_time asc");
        example.setLimit(eventSearchPara.size);
        example.setOffset(PagingUtil.getStart(eventSearchPara.page, eventSearchPara.size));
        EventExample.Criteria criteria = example.createCriteria();
        if (eventSearchPara.modifyTime != null && eventSearchPara.modifyTime != 0) {
            criteria.andModifyTimeGreaterThan(eventSearchPara.modifyTime);
        }
        List<Event> events = eventMapper.selectByExample(example);
        List<EventPara> eventParaList = new ArrayList<>();
        events.forEach(event -> {
            EventPara para = new EventPara();
            para.id = event.getId();
            para.eventId = event.getId();
            para.startTime = event.getStartTime();
            para.endTime = event.getEndTime();
            para.deadlineTime = event.getDeadlineTime();
            para.title = event.getTitle();
            CityReq city = new CityReq();
            city.id = event.getCityId();
            city.name = event.getCityName();
            para.city = city;
            para.eventType = event.getTemplateId().byteValue();
            List<EventSponsor> sponsorList = (List) sponsorsModuleService.get(event.getId());
            List<Sponsor> sponsorReqList = sponsorsModuleService.converseParaList(sponsorList);
            para.sponsors = sponsorReqList;
            eventParaList.add(para);
        });
        return eventParaList;
    }

    /**
     * 获取某用户某活动下的票券信息
     */

    public Object getBuyerTicket(AppReq req) {
        if (NumberUtil.isInValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("eventId参数异常");
        }
        if (NumberUtil.isInValidId(req.uid)) {
            throw ExceptionUtil.createParamException("uid参数异常");
        }
        EventRecordExample recordExample = new EventRecordExample();
        recordExample.createCriteria().andEventIdEqualTo(req.eventId).andUidEqualTo(req.uid).andTicketLockEqualTo
                (SqlEnum.TicketLockType.TYPE_NORMAL.getValue());
        List<EventRecord> eventRecords = eventRecordMapper.selectByExample(recordExample);
        List<String> tradeNoLists = new ArrayList<>();
        if (!StringUtil.isEmpty(eventRecords)) {
            eventRecords.forEach(eventRecord -> {
                tradeNoLists.add(eventRecord.getTradeNo());
            });
        }
        Integer commodityId = getEvent(req.eventId).getCommodityId();
        TicketResp resp = new TicketResp();
        resp.list = commodityRemoteService.getTicketsForApp(tradeNoLists, commodityId);
        ;
        return resp;
    }


    public EventRecordListResp getTicketOrder(AppReq req) {
        if (NumberUtil.isInValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("eventId参数异常");
        }
        if (NumberUtil.isInValidId(req.uid)) {
            throw ExceptionUtil.createParamException("uid参数异常");
        }

        // TODO: 2018/2/12 会存在老活动数据 (2845 - 60000) 这个区间老创业活动 会转换为新活动id
        if (req.eventId >= 2845 && req.eventId < 60000) {
            EventExample example = new EventExample();
            example.createCriteria().andTemplateIdEqualTo(1).andOldEventIdEqualTo(req.eventId);
            List<Event> events = eventMapper.selectByExample(example);

            if (events != null && events.size() != 0) {
                Event event1 = events.get(0);
                req.eventId = event1.getId();
            }
        }


        List<EventRecordResp> eventRecordResps = new ArrayList<>();
        EventQuery query = new EventQuery();
        query.uid = req.uid;
        query.eventId = req.eventId;
        List<EventRecord> records = eventRecordService.getValidRecords(query);
        Event event = getEvent(req.eventId);
        Integer costType;
        if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())) {
            costType = stateCustomModuleService.get(event.getId()).getCostType().intValue();
        } else {
            costType = stateProjectModuleService.get(event.getId()).getCostType().intValue();
        }
        EventRecordResp resp;
        for (EventRecord eventRecord : records) {
            resp = new EventRecordResp();
            resp.createTime = eventRecord.getCreateTime();
            resp.id = eventRecord.getId();
            //原字段orderNumber废弃，用商品订单号tradeNo
            resp.orderNumber = eventRecord.getTradeNo();
            resp.costType = costType;
            eventRecordResps.add(resp);
        }
        EventRecordListResp response = new EventRecordListResp();
        response.list = eventRecordResps;
        return response;
    }


    public Object getEventH5(AppReq req) {
        EventPara eventPara = new EventPara();
        Event event = getEvent(req.eventId);
        eventPara = conversePara(event, EventDataLevel.LEVEL1.getLevel());
        String uniqueKey = extensionLinkService.getDefaultLinkByEventId(req.eventId).getUniqueKey();
        eventPara.jumpUrl = cloudEventConfig.getLink_common_prefix() + Base64Utils.encode
                (JSONUtil.obj2Json((uniqueKey + "&" + req.eventId)).getBytes());
        return eventPara;
    }


    public List<Event> getAllEventListByOrgId(Integer orgId) {
        EventExample eventExample = getEventExample(orgId);
        return eventMapper.selectByExample(eventExample);
    }


    public List<Event> getAllEventPublishedListByOrgId(Integer orgId) {
        EventExample eventExample = new EventExample();
        List<Byte> bytes = new ArrayList<>();
        bytes.add(SqlEnum.EventStateType.TYPE_EVENT_PUBLISHED.getByteValue());
        eventExample.createCriteria().andOrgIdEqualTo(orgId).andStateIn(bytes);
        return eventMapper.selectByExample(eventExample);
    }

    private EventExample getEventExample(Integer orgId) {
        EventExample eventExample = new EventExample();
        List<Byte> bytes = new ArrayList<>();
        bytes.add(SqlEnum.EventStateType.TYPE_EVENT_PUBLISH_NOT.getByteValue());
        bytes.add(SqlEnum.EventStateType.TYPE_EVENT_PUBLISHED.getByteValue());
        eventExample.createCriteria().andOrgIdEqualTo(orgId).andStateIn(bytes);
        return eventExample;
    }


    public long getAllEventCountByOrgId(Integer orgId) {
        EventExample eventExample = getEventExample(orgId);
        return eventMapper.countByExample(eventExample);
    }


    public List<EventList> getEventListResp(List<Event> list, Byte level) {
        List<EventList> eventLists = new ArrayList<>();
        if (list != null && list.size() > 0) {
            List<Integer> uids = new ArrayList<>();
            Map<Integer, Integer> mapIntger = new HashMap<>();
            for (Event event : list) {
                mapIntger.put(event.getPublishUid(), event.getPublishUid());
            }
            for (Map.Entry<Integer, Integer> entry : mapIntger.entrySet()) {
                uids.add(entry.getValue());
            }
            Map<Integer, User> map = userService.getUserInfoListByIds(uids);
            Map<Integer, EventCityRelation> allRecommendMap = eventCityRelationModule.getAllRecommendMap();
            for (Event event : list) {
                EventList eventList = getEventList(event, level, allRecommendMap);
                if (eventList != null && map.containsKey(event.getPublishUid())) {
                    eventList.publishUser = new UserResp();
                    eventList.publishUser.uid = event.getPublishUid();
                    eventList.publishUser.name = map.get(event.getPublishUid()).name;
//                    //置顶推荐
//                    if (eventList.recommendState != null && eventList.recommendState == SqlEnum.RecommendType
//                            .TYPE_RECOMMEND.getValue().byteValue()) {
//                        eventLists.add(0, eventList);
//                    } else {
                    //去掉删除的
                    if (eventList.state != SqlEnum.EventStateType.TYPE_EVENT_DELETED.getByteValue()) {
                        eventLists.add(eventList);
                    }
//                    }
                }
            }
        }
        return eventLists;
    }


    public Integer getEventTotalByOrgId(EventSearchPara eventSearchPara) {
        return eventMapper.countEventList(eventSearchPara);
    }


    public EventPara getEventDetailByLevel(Event event, Integer level, Integer uid) {
        EventStateProject eventStateProject = null;
        EventStateCustom eventStateCustom = null;

        if ((event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_ROADSHOW.getValue()) ||
                event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_FINANCE_DELIVERY.getValue()))) {
            eventStateProject = stateProjectModuleService.get(event
                    .getId());
        }
        if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())) {
            eventStateCustom = stateCustomModuleService.get(event.getId());
        }

        Set<Integer> levelSet = EventDataLevel.getParentLevels(level);
        EventPara eventPara = new EventPara();
        if (levelSet.contains(EventDataLevel.LEVEL1.getLevel())) {
            eventPara = conversePara(event, EventDataLevel.LEVEL1.getLevel());
        } else if (levelSet.contains(EventDataLevel.LEVEL2.getLevel())) {
            eventPara = conversePara(event, EventDataLevel.LEVEL2.getLevel());
        }

        for (Integer levelTmp : levelSet) {
            switch (levelTmp) {
                case 3:
                    List<EventSponsor> sponsorList = (List) sponsorsModuleService.get(event.getId());
                    List<Sponsor> sponsorReqList = sponsorsModuleService.converseParaList(sponsorList);
                    eventPara.sponsors = sponsorReqList;
                    break;
                case 4:
                    List<EventGuest> guestList = (List) guestModuleService.get(event.getId());
                    List<Guest> guestReqList = guestModuleService.converseParaList(guestList);
                    eventPara.guests = guestReqList;
                    break;
                case 5:
                    List<EventCustomField> eventCustomFieldList = (List) customFormModuleService.get(event.getId());
                    List<AddtionalForm> addtionalFormList = customFormModuleService.converseParaList
                            (eventCustomFieldList);
                    eventPara.additionalForm = addtionalFormList;
                    break;
                case 6:
                    TagResp tagResp = (TagResp) tagModuleService.get(event.getId());
                    eventPara.tags = tagModuleService.converseParaList(tagResp.list);
                    break;
                case 7:
                    if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_ROADSHOW.getValue()) ||
                            event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_FINANCE_DELIVERY.getValue())) {
                        eventPara.projectModule = stateProjectModuleService.conversePara(eventStateProject);
                        //在event_state_project加人数限制字段，这里放stateProjectModuleService.conversePara方法处理
                        Integer ticketCount = commodityRemoteService.getFreeTicketCount(event.getCommodityId());
                        eventPara.projectModule.projectFreeCount = ticketCount.equals(Constant.NO_LIMIT_NUMBER) ?
                                0 : ticketCount;
                    }
                    if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())) {
                        eventPara.customModule = stateCustomModuleService.conversePara(eventStateCustom);
                        //如果是免费票，置空ticketList，收费票才返回ticketList
                        if (eventStateCustom.getCostType().equals(SqlEnum.CostType.TYPE_CHARGE.getValue().byteValue()
                        )) {
                            eventPara.customModule.ticketList = commodityRemoteService.converseToTicket(event
                                    .getCommodityId());
                            //收费活动前端需要freecount参数，且值为0
                            eventPara.customModule.freeCount = 0;
                        } else {
                            //从商品服务拿票数
                            eventPara.customModule.freeCount = commodityRemoteService.converseToTicket(event
                                    .getCommodityId()).get(0).count;
                        }
                        eventPara.priceMin = getMinPrice(eventPara.customModule);
                    }
                    break;
                case 8:
                    ExtensionLinkExample example = new ExtensionLinkExample();
                    example.createCriteria().andEventIdEqualTo(event.getId());
                    example.setOrderByClause("id asc");
                    List<ExtensionLink> extensionLinkList = extensionLinkMapper.selectByExample(example);
                    if (extensionLinkList != null && extensionLinkList.size() > 0) {
                        eventPara.commonUrl = extensionLinkList.get(0).getLinkUrlCommon();//分享Url
                    }
                    break;
                case 10:
                    //报名成功人数
                    if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_ROADSHOW.getValue()) ||
                            event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_FINANCE_DELIVERY.getValue())) {
                        eventPara.recordNum = eventStateProject.getJoinedCount();
                    }
                    if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())) {
                        eventPara.recordNum = eventStateCustom.getJoinedCount();
                    }
                    break;
                case 11: //3.3.0.1版本 App接口兼容 提供兼容字段已经新字段
                    if (uid == null) {
                        throw ExceptionUtil.createParamException("用户id不能为空");
                    }

                    List<EventCityRelation> hotEvents = eventCityRelationModule.getHotOrRecommendEvents(ParamEnum
                            .HotOrRcmType
                            .TYPE_HOT, getEventIdsByOrgId(event.getOrgId()));
                    List<EventCityRelation> recommendEvents = eventCityRelationModule.getHotOrRecommendEvents(ParamEnum
                            .HotOrRcmType.TYPE_RECOMMENT, getEventIdsByOrgId(event.getOrgId()));

                    eventPara = getAppEventPara(eventPara, event, uid, event.getCityId(), hotEvents, recommendEvents);

                    eventPara.publishOrg = getEventOrgPara(event);

                    break;
                case 14: //打印相关信息

                    eventPara.title = event.getTitle();
                    eventPara.timeRange = DateUtil.converseTime(event.getStartTime(), event.getEndTime());
                    eventPara.address = event.getAddress();
                    eventPara.signinUrl = cloudEventConfig.getLink_signup_prefix() + "s?e=" + event.getId();

                    break;
            }
        }
        return eventPara;
    }

    public String getMinPrice(CustomModule customModule) {
        String priceMin = "免费";
        if (customModule.isCharge.equals(SqlEnum.CostType.TYPE_CHARGE_NOT.getValue())) {
            return priceMin;
        } else {
            Double amount = new Double(0);
            if (customModule.ticketList != null && customModule.ticketList.size() > 0) {
                for (int i = 0; i < customModule.ticketList.size(); i++) {
                    Ticket ticket = customModule.ticketList.get(i);
                    if (i == 0) {
                        amount = ticket.price;
                    } else {
                        int retval = amount.compareTo(ticket.price);
                        if (retval > 0) {
                            amount = ticket.price;
                        }
                    }
                }
            }

            priceMin = "￥" + (amount / 100) + "起";
            return priceMin;
        }
    }


    /**
     * app需要的参数
     *
     * @param eventPara eventPara
     * @param event     event
     * @param uid       uid
     * @return app需要的参数
     */
    private EventPara getAppEventPara(EventPara eventPara, Event event, Integer uid, Integer cityId,
                                      List<EventCityRelation> hotEvents, List<EventCityRelation> recommendEvents) {
        checkValidEventType(event.getTemplateId());
        Integer recordNum;
        Integer numberState;
        if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())) {
            EventStateCustom eventStateCustom = stateCustomModuleService.get(event.getId());
            recordNum = eventStateCustom.getJoinedCount() == null ? 0 : eventStateCustom.getJoinedCount();
            numberState = eventStateCustom.getCustomNumberState().intValue();
        } else {
            EventStateProject eventStateProject = stateProjectModuleService.get(event.getId());
            recordNum = eventStateProject.getJoinedCount() == null ? 0 : eventStateProject.getJoinedCount();
            numberState = eventStateProject.getProjectNumberState().intValue();
        }

        //活动报名数量
        eventPara.recordNum = recordNum;

        //如果报名项目数量显示状态设置为开启
        if (!numberState.equals(0)) {
            //活动票
            eventPara.tickets = commodityRemoteService.converseToTicket(event.getCommodityId());
            //获取5条报名的项目信息,倒序取最新5条
            List<EventRecord> eventRecordList = iEventRecordProjectInfo.
                    getRecordListByEventIdAndLevel(event.getId(),
                            1, 5, "id desc",
                            ParamEnum.ProjectRecordListGetType.TYPE_AUDITED_AND_WAIT_VERIFY_FAILED);
            if (!StringUtil.isEmpty(eventRecordList)) {
                List<Integer> records = new ArrayList<>();
                for (EventRecord eventRecord : eventRecordList) {
                    records.add(eventRecord.getId());
                }
                ProjectBackupInfoExample projectBackupInfoExample = new ProjectBackupInfoExample();
                projectBackupInfoExample.createCriteria().andEventRecordIdIn(records);
                List<ProjectBackupInfo> projectBackupInfos = projectBackupInfoMapper.
                        selectByExample(projectBackupInfoExample);
                if (!StringUtil.isEmpty(projectBackupInfos)) {
                    eventPara.signUpList = new ArrayList<>();
                    ProjectResp projectResp;
                    for (ProjectBackupInfo projectInfo : projectBackupInfos) {
                        projectResp = new ProjectResp();
                        projectResp.name = projectInfo.getName();
                        projectResp.id = projectInfo.getId();
                        projectResp.logo = cloudEventConfig.getImage_prefix() + projectInfo.getLogo();
                        eventPara.signUpList.add(projectResp);
                    }
                }
            }
        }
        EventQuery query = new EventQuery();
        query.uid = uid;
        query.eventId = event.getId();
        List<EventRecord> eventRecords = eventRecordService.getValidRecords(query);
        eventPara.isSignUp = 0;
        if (eventRecords != null && eventRecords.size() > 0) {
            eventPara.isSignUp = 1;//报名
            eventPara.recordStatus = eventRecords.get(0).getState();
        }

        //是否收藏
        EventUidFavoriteRelationExample favoriteRelationExample = new EventUidFavoriteRelationExample();
        favoriteRelationExample.createCriteria().andEventIdEqualTo(event.getId()).andUidEqualTo(uid);
        Long favoriteCount = favoriteRelationMapper.countByExample(favoriteRelationExample);
        eventPara.isFavorite = 0;
        if (favoriteCount > 0) {
            eventPara.isFavorite = 1;
        }
        //url
        eventPara.url = cloudEventConfig.getEvent_content_url() + event.getId();
        eventPara.sortType = getSortType(event, hotEvents, recommendEvents, cityId);
        String uniqueKey = extensionLinkService.getDefaultLinkByEventId(event.getId()).getUniqueKey();
        Base64KeyReq base = new Base64KeyReq();
        base.uniqueKey = uniqueKey;
        base.aid = event.getId();
        eventPara.jumpUrl = cloudEventConfig.getLink_common_prefix() + Base64Utils.encode
                ((uniqueKey + "&" + event.getId()).getBytes());

        if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())) {
            eventPara.customModule.ticketList = commodityRemoteService.converseToTicket(event
                    .getCommodityId());
            eventPara.priceMin = getMinPrice(eventPara.customModule);
        }

        return eventPara;

    }

    /**
     * app需要的参数
     *
     * @param eventPara eventPara
     * @param event     event
     * @return app需要的参数
     */
    public EventPara getAppBasicEventPara(EventPara eventPara,
                                          Map<Integer, EventRecord> recordsMap,
                                          Event event,
                                          Map<Integer, Boolean> eventFavoriteMap,
                                          Map<Integer, EventStateProject> eventStateProjectMap,
                                          Map<Integer, EventStateCustom> eventStateCustomMap,
                                          Map<Integer, ExtensionLink> extensionLinkMap,
                                          Map<Integer, List<Sponsor>> sponsorMap,
                                          List<EventCityRelation> hotEvents,
                                          List<EventCityRelation> recommendEvents,
                                          Map<Integer, List<Ticket>> ticketsMap,
                                          Integer cityId,
                                          Integer level) {
        checkValidEventType(event.getTemplateId());
        Integer recordNum;
        if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())) {
            EventStateCustom eventStateCustom = eventStateCustomMap.get(event.getId());
            eventPara.customModule = stateCustomModuleService.conversePara(eventStateCustom);
            recordNum = eventStateCustom.getJoinedCount() == null ? 0 : eventStateCustom.getJoinedCount();
        } else {
            EventStateProject eventStateProject = eventStateProjectMap.get(event.getId());
            eventPara.projectModule = stateProjectModuleService.conversePara(eventStateProject);
            recordNum = eventStateProject.getJoinedCount() == null ? 0 : eventStateProject.getJoinedCount();
        }

        //活动报名数量
        eventPara.recordNum = recordNum;


        if (hotEvents != null && recommendEvents != null) {
            eventPara.sortType = getSortType(event, hotEvents, recommendEvents, cityId);
        }


        //是否收藏
        if (eventFavoriteMap.containsKey(event.getId())) {
            eventPara.isFavorite = 1;
        } else {
            eventPara.isFavorite = 0;
        }
        eventPara.isSignUp = 0;
        if (recordsMap.containsKey(eventPara.eventId)) {
            eventPara.isSignUp = 1;//报名
            if (level == 1) {
                eventPara.recordStatus = recordsMap.get(eventPara.eventId).getState();
            }
        }
        List<Sponsor> sponsorReqList = sponsorMap.get(eventPara.eventId);
        eventPara.sponsors = sponsorReqList;

        if (level == 1) {
            if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())) {
                eventPara.customModule.ticketList = ticketsMap.get(event.getCommodityId());
                eventPara.priceMin = getMinPrice(eventPara.customModule);
            }
            if (extensionLinkMap != null) {
                ExtensionLink extensionLink = extensionLinkMap.get(eventPara.eventId);
                String uniqueKey = extensionLink.getUniqueKey();
                eventPara.jumpUrl = cloudEventConfig.getLink_common_prefix() + Base64Utils.encode
                        ((uniqueKey + "&" + event.getId()).getBytes());
            }
        }

        return eventPara;
    }


    /**
     * 是否有效的活动类型
     *
     * @param templateId
     */
    private void checkValidEventType(Integer templateId) {
        if (!templateId.equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue()) && !templateId.equals(SqlEnum
                .EventType.TYPE_EVENT_ROADSHOW.getValue()) && !templateId.equals(SqlEnum.EventType
                .TYPE_EVENT_FINANCE_DELIVERY.getValue())) {
            throw ExceptionUtil.createParamException("eventType参数异常");
        }
    }


    /**
     * 机构信息
     *
     * @param event
     * @return 返回机构信息
     */
    private OrgResp getEventOrgPara(Event event) {
        //机构
        OrgInfoExample orgInfoExample = new OrgInfoExample();
        orgInfoExample.createCriteria().andIdEqualTo(event.getOrgId());
        List<OrgInfo> orgInfos = orgInfoMapper.selectByExample(orgInfoExample);
        if (orgInfos != null && orgInfos.size() > 0) {
            OrgResp orgResp = new OrgResp();
            orgResp.id = orgInfos.get(0).getId();//报名
            orgResp.name = orgInfos.get(0).getName();
            orgResp.intro = orgInfos.get(0).getIntro();//报名
            orgResp.logo = orgInfos.get(0).getLogo();//报名
            orgResp.eventCount = orgInfos.get(0).getEventCount();//报名
//                        orgResp.state = 1;
            return orgResp;
        }
        return null;
    }


    public com.welian.beans.cloudevent.event.EventResp getEventDetail(EventPara eventPara) {
        if (!NumberUtil.isValidId(eventPara.eventId)) {
            throw ExceptionUtil.createParamException("活动参数错误");
        } else if (eventPara.level == null) {
            throw ExceptionUtil.createParamException("活动参数错误");
        }


        // TODO: 2018/2/12 会存在老活动数据 (2845 - 60000) 这个区间老创业活动 会转换为新活动id
        if (eventPara.eventId >= 2845 && eventPara.eventId < 60000) {
            EventExample example = new EventExample();
            example.createCriteria().andTemplateIdEqualTo(1).andOldEventIdEqualTo(eventPara.eventId);
            List<Event> events = eventMapper.selectByExample(example);

            if (events != null && events.size() != 0) {
                Event event1 = events.get(0);
                eventPara.eventId = event1.getId();
            }
        }


        Event event = getEvent(eventPara.eventId);
        if (event == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        EventPara eventParaTmp = getEventDetailByLevel(event, eventPara.level, eventPara.uid);

        com.welian.beans.cloudevent.event.EventResp eventResp = new com.welian.beans.cloudevent.event.EventResp();
        eventResp.eventPara = eventParaTmp;
        //这里的recordNum+1并不是活动报名数，用于前端控制是否可以修改部分的信息
        if (eventPara.level.equals(10)) {
            if (eventRecordService.checkHasRecord(event.getId()) || eventRecordService.checkHasToPayOrder
                    (event.getId())) {
                eventResp.eventPara.recordNum = eventParaTmp.recordNum == null ? 1 : eventParaTmp.recordNum;
            }
        }
        if (event.getTemplateId() == SqlEnum.EventType.TYPE_EVENT_FINANCE_DELIVERY.getValue().intValue()) {
            eventResp.eventPara.financingService = getFinancingService(event.getId());
        }
        eventResp.orgId = event.getOrgId();
        eventResp.userId = event.getPublishUid();
        String uniqueKey = extensionLinkService.getDefaultLinkByEventId(eventPara.eventId).getUniqueKey();
        Base64KeyReq base = new Base64KeyReq();
        base.uniqueKey = uniqueKey;
        base.aid = eventPara.eventId;
        eventResp.eventPara.jumpUrl = cloudEventConfig.getLink_common_prefix() + Base64Utils.encode
                ((uniqueKey + "&" + eventPara.eventId).getBytes());
        return eventResp;
    }


    public EventInfo getEventByEventId(Integer eventId) {
        Event eventResp = eventMapper.selectByPrimaryKey(eventId);
        if (eventResp == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        ExtensionLinkExample example = new ExtensionLinkExample();
        String linkUrlCommon = null;
        example.createCriteria().andEventIdEqualTo(eventId).andLinkTypeEqualTo(
                SqlEnum.LINKTYPE.TYPE_DEFAULT.getValue().byteValue());
        List<ExtensionLink> extensionLinks = extensionLinkMapper.selectByExample(example);
        if (!StringUtil.isEmpty(extensionLinks)) {
            linkUrlCommon = extensionLinks.get(0).getLinkUrlCommon();
        }
        EventInfo event = converseEvent(eventResp, linkUrlCommon);
        return event;
    }


    public TicketResp getTickets(EventPara req) {
        if (NumberUtil.isInValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("eventId参数错误");
        }
        Event event = eventMapper.selectByPrimaryKey(req.eventId);
        TicketResp response = new TicketResp();
        List<Ticket> ticketList = new ArrayList<>();
        for (Ticket ticket : commodityRemoteService.converseToTicket(event.getCommodityId())) {
            if (ticket.remaindCount.intValue() != 0) {
                ticketList.add(0, ticket);
            } else {
                ticketList.add(ticket);
            }
        }
        response.list = ticketList;
        EventPara eventResp = new EventPara();
        eventResp.id = event.getId();
        eventResp.defaultExtensionLinkId = extensionLinkService.getDefaultLinkByEventId(req.eventId).getId();
        eventResp.title = event.getTitle();
        eventResp.city = new CityReq();
        eventResp.city.id = event.getCityId();
        eventResp.city.name = event.getCityName();
        eventResp.startTimeStr = DateUtil.getWeekAndDate(event.getStartTime());
        eventResp.lineType = event.getLineType();
        eventResp.costType = getCostType(event);
        eventResp.eventType = event.getTemplateId().byteValue();
        //自定义字段
        List<EventCustomField> eventCustomFieldList = (List) customFormModuleService.get(eventResp.id);
        List<AddtionalForm> addtionalFormList = customFormModuleService.converseParaList(eventCustomFieldList);
        eventResp.additionalForm = addtionalFormList;
        if (!SqlEnum.EventType.TYPE_EVENT_COMMON.getValue().equals(event.getTemplateId())) {
            eventResp.projectModule = stateProjectModuleService.conversePara(stateProjectModuleService.get(event
                    .getId()));
        }
        response.event = eventResp;
        return response;
    }

    /**
     * 获取活动的收费信息
     *
     * @param event 活动实体类
     * @return
     */
    public Integer getCostType(Event event) {
        if (event == null) {
            return null;
        }
        if (SqlEnum.EventType.TYPE_EVENT_COMMON.getValue().equals(event.getTemplateId())) {
            return stateCustomModuleService.get(event.getId()).getCostType().intValue();
        } else if (SqlEnum.EventType.TYPE_EVENT_ROADSHOW.getValue().equals(event.getTemplateId()) ||
                SqlEnum.EventType.TYPE_EVENT_FINANCE_DELIVERY.getValue().equals(event.getTemplateId())) {
            return stateProjectModuleService.get(event.getId()).getCostType().intValue();
        } else {
            //异常
            return null;
        }
    }

    private EventInfo converseEvent(Event eventResp, String commonUrl) {
        EventInfo event = new EventInfo();
        event.adCount = eventResp.getAdCount();
        event.address = eventResp.getAddress();
        event.cityId = eventResp.getCityId();
        event.cityName = eventResp.getCityName();
        event.costType = eventResp.getCostType();
        event.createTime = eventResp.getCreateTime();
        event.deadlineTime = eventResp.getDeadlineTime();
        event.detail = eventResp.getDetail();
        event.detailBrowseCount = eventResp.getDetailBrowseCount();
        event.endTime = eventResp.getEndTime();
        event.favoriteCount = eventResp.getFavoriteCount();
        event.formBrowseCount = eventResp.getFormBrowseCount();
        event.id = eventResp.getId();
        event.joinedCount = eventResp.getJoinedCount();
        event.latitude = eventResp.getLatitude();
        event.lineType = eventResp.getLineType();
        event.commonUrl = commonUrl;
        event.linkUrlCount = eventResp.getLinkUrlCount();
        event.logo = eventResp.getLogo();
        event.longitude = eventResp.getLongitude();
        event.modifyTime = eventResp.getModifyTime();
        event.openExtension = eventResp.getOpenExtension();
        event.orgId = eventResp.getOrgId();
        event.publishUid = eventResp.getPublishUid();
        event.recommendStatus = eventResp.getRecommendStatus();
        event.startTime = eventResp.getStartTime();
        event.state = eventResp.getState();
        event.templateId = eventResp.getTemplateId();
        event.title = eventResp.getTitle();
        return event;

    }


    private EventList getEventList(Event event, Byte level, Map<Integer, EventCityRelation> allRecommendMap) {
        if (event != null) {
            EventList eventList = new EventList();
            CityReq cityReq = new CityReq();
            cityReq.id = event.getCityId();
            cityReq.name = event.getCityName();
            eventList.title = event.getTitle();
            eventList.eventId = event.getId();
            if (level == 0) {
                eventList.city = cityReq;
                eventList.address = event.getAddress();
                eventList.signUpTotal = event.getJoinedCount();
                eventList.collectionTotal = event.getFavoriteCount();
                eventList.isFree = event.getCostType();
                eventList.logo = cloudEventConfig.getImage_prefix() + event.getLogo();
                eventList.state = event.getState();
                if (allRecommendMap != null && allRecommendMap.containsKey(event.getId())) {
                    eventList.recommendState = allRecommendMap.get(event.getId()).getRecommendState().byteValue();
                }
                eventList.type = event.getTemplateId().byteValue();
                eventList.timeStr = DateUtil.converseTime(event.getStartTime(), event.getEndTime());
                eventList.checkInTotal = Constant.CHECK_IN_TOTAL;
                eventList.lineType = event.getLineType();
            }

            return eventList;
        }
        return null;
    }



    public Object getEventInfoByLinkIdJson(ExtensionLinkReq req) {

        return null;
    }

    /**
     * 删除某一个事件活动下的所有关联的投资人分组
     *
     * @param eventId
     */
    private void deleteGroupSetting(Integer eventId) {
        EventInvestorGroupRelationExample example = new EventInvestorGroupRelationExample();
        example.createCriteria().andEventIdEqualTo(eventId);
        eventInvestorGroupRelationMapper.deleteByExample(example);
    }

    /**
     * 保存投资人分组信息
     *
     * @param investor_groups
     */
    private void reviseGroupSetting(List<InvestorGroupReq> investor_groups, Integer eventId) {
        if (investor_groups != null && !investor_groups.isEmpty()) {
            Map<Integer, Integer> map = new HashMap<>();
            EventInvestorGroupRelation relation;
            for (InvestorGroupReq i : investor_groups) {
                //防止添加的投资人分组重复
                if (!map.containsKey(i.investorGroupId)) {
                    InvestorGroupExample investorGroupExample = new InvestorGroupExample();
                    investorGroupExample.createCriteria().andIdEqualTo(i.investorGroupId).andStateEqualTo((byte) 0);
                    Long count = investorGroupMapper.countByExample(investorGroupExample);
                    if (count == 0) {
                        throw ExceptionUtil.createParamException("投资人分组不存在");
                    }
                    relation = new EventInvestorGroupRelation();
                    relation.setEventId(eventId);
                    relation.setInvestorGroupId(i.investorGroupId);
                    relation.setModifyTime(System.currentTimeMillis());
                    relation.setCreateTime(System.currentTimeMillis());
                    eventInvestorGroupRelationMapper.insertSelective(relation);
                    map.put(i.investorGroupId, i.investorGroupId);
                }
            }
        }
    }


    //判断自定义表单是否修改
    private boolean checkAdditionFormUpdate(List<AddtionalForm> additionalFormList, Integer eventId) {
        if (!StringUtil.isEmpty(additionalFormList)) {
            List<Integer> idList = new ArrayList<>();
            for (AddtionalForm form : additionalFormList) {
                if (form.id == null) {
                    return true;
                }
                idList.add(form.id);
            }

            Map<Integer, EventCustomField> map = customFormModuleService.getEventCustomFieldMap(idList);
            if (map.size() > 0) {
                for (AddtionalForm form : additionalFormList) {
                    EventCustomField eventCustomField = map.get(form.id);
                    logger.info("eventCustomField:title:" + eventCustomField.getTitle());
                    logger.info("form:title:" + form.label);
                    logger.info("eventCustomField:type:" + eventCustomField.getFieldType());
                    logger.info("form:type:" + form.type);
                    if (eventCustomField != null
                            && (eventCustomField.getFieldType().byteValue() != form.type
                            || !eventCustomField.getTitle().equals(form.label))) {
                        return true;
                    }
                }
                return false;
            } else {
                //数据库中没有自定义表单，编辑时添加自定义表单，判断为修改自定义表单
                return true;
            }
        }
        //先添加后删除的情况，也判断为修改自定义表单
        List<EventCustomField> customFieldList = (List<EventCustomField>) customFormModuleService.get(eventId);
        if (eventId != null && customFieldList != null && customFieldList.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 检验修改活动信息的报名人数限制时不能小于已经报名的人数
     *
     * @param eventReq
     */
    private void checkRecordCount(EventPara eventReq) {
        Integer eventId = eventReq.eventId;
        Integer limitNum = eventReq.projectModule.projectFreeCount == 0 ? Constant.NO_LIMIT_NUMBER : eventReq
                .projectModule.projectFreeCount;
        EventRecordExample example = new EventRecordExample();
        List<Byte> bytes = new ArrayList<>();
        bytes.add(SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getByteValue());
        bytes.add(SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY.getByteValue());
        example.createCriteria().andEventIdEqualTo(eventId).andStateIn(bytes);
        Integer count = (int) eventRecordMapper.countByExample(example);
        if (count > limitNum) {
            throw ExceptionUtil.createParamException("已经报名的人数超过报名限制人数");
        }

    }


    /************************************************************************************路演大赛
     * app相关接口************************************************************************************/


    /**
     * 收藏或者取消收藏活动
     */

    @Synchronized(argType = Synchronized.ArgType.ARG_BEAN, keyName = "uid")//锁定活动
    @Transactional
    public Object favoriteEvent(AppReq req) {
        AppResp appResp = new AppResp();

        EventUidFavoriteRelationExample favoriteRelationExample = new EventUidFavoriteRelationExample();
        favoriteRelationExample.createCriteria().andUidEqualTo(req.uid).andEventIdEqualTo(req.eventId);
        Long count = favoriteRelationMapper.countByExample(favoriteRelationExample);

        Event eventNow = eventMapper.selectByPrimaryKey(req.eventId);
        if (eventNow == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        //更新event表收藏数量
        Event event = new Event();
        event.setId(eventNow.getId());
        if (req.type == 0) {//取消收藏
            if (count == 0) {
                throw ExceptionUtil.createParamException("活动还没有被收藏");
            }
            favoriteRelationMapper.deleteByExample(favoriteRelationExample);
            event.setFavoriteCount(eventNow.getFavoriteCount() - 1);
        } else if (req.type == 1) {//收藏
            if (count > 0) {
                throw ExceptionUtil.createParamException("已经收藏过了");
            }
            EventUidFavoriteRelation record = new EventUidFavoriteRelation();
            record.setEventId(req.eventId);
            record.setUid(req.uid);
            favoriteRelationMapper.insertSelective(record);

            event.setFavoriteCount(eventNow.getFavoriteCount() + 1);
        }
        eventMapper.updateByPrimaryKeySelective(event);

        return appResp;
    }


    /**
     * app端调用-根据机构获得机构下的活动列表
     *
     * @param req
     * @return
     */

    public OrgAndEventListResp getOrgDetail(OrgReq req) {
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("orgId参数错误");
        }
        if (!NumberUtil.isValidId(req.getPage())) {
            throw ExceptionUtil.createParamException("page参数错误");
        }
        if (!NumberUtil.isValidId(req.getSize())) {
            throw ExceptionUtil.createParamException("size参数错误");
        }
        if (req.eventType == null || !req.eventType.equals(ParamEnum.EventType.TYPE_COMMON.getValue()) &&
                !req.eventType.equals(ParamEnum.EventType.TYPE_ROAD.getValue())) {
            throw ExceptionUtil.createParamException("eventType参数错误");
        }


        List<EventCityRelation> hotEvents = eventCityRelationModule.getHotOrRecommendEvents(ParamEnum.HotOrRcmType
                .TYPE_HOT, getEventIdsByOrgId(req.orgId));
        List<EventCityRelation> recommendEvents = eventCityRelationModule.getHotOrRecommendEvents(ParamEnum
                .HotOrRcmType.TYPE_RECOMMENT, getEventIdsByOrgId(req.orgId));
        List<Integer> reommendIds = new ArrayList<>();
        for (EventCityRelation relation : recommendEvents) {
            reommendIds.add(relation.getEventId());
        }
        req.withPage = true;
        List<Event> eventList = getValidEventList(req, reommendIds);
        OrgAndEventListResp response = new OrgAndEventListResp();
        Organization organization = OrgRemoteService.getOrgById(req.orgId);
        OrgInfoResp orgInfo = converseOrg(organization);
        response.organization = orgInfo;
        req.withPage = false;
        List<Event> events = getValidEventList(req, reommendIds);
        //拿到创业和路演活动的数量
        Integer eventType = req.eventType;
        req.eventType = SqlEnum.EventType.TYPE_EVENT_COMMON.getValue();
        response.organization.normalEventCount = getValidEventList(req, reommendIds).size();
        req.eventType = SqlEnum.EventType.TYPE_EVENT_ROADSHOW.getValue();
        response.organization.roadShowEventCount = getValidEventList(req, reommendIds).size();
        req.eventType = eventType;
        response.count = events.size();
        if (StringUtil.isEmpty(eventList)) {
            List<EventPara> list = new ArrayList<>();
            response.list = list;
            return response;
        }
        //获取发布方,放在map中
        List<Integer> eventIds = new ArrayList<>();
        for (Event event : eventList) {
            eventIds.add(event.getId());
        }

        Map<Integer, EventStateCustom> eventStateCustomMap = new HashMap<>();
        Map<Integer, EventStateProject> eventStateProjectMap = new HashMap<>();
        if (!StringUtil.isEmpty(eventIds)) {
            Map<Integer, List<Sponsor>> sponsorsMap = this.selectBatchSponsor(eventIds);
            //拿到recordNumMap和projectNumberStateMap
            if (req.eventType.equals(ParamEnum.EventType.TYPE_COMMON.getValue())) {
                eventStateCustomMap = this.selectBatchEventStateCustom(eventIds);
            } else if (req.eventType.equals(ParamEnum.EventType.TYPE_ROAD.getValue())) {
                eventStateProjectMap = this.selectBatchEventStateProject(eventIds);
            }

            //拿到linkCommonUrlMap
            Map<Integer, String> linkCommonUrlMap = selectBatchLinkCommonUrl(eventIds);

            List<EventPara> eventParaList = new ArrayList<>();
            if (!StringUtil.isEmpty(eventList)) {
                for (Event event : eventList) {
                    EventPara eventPara = new EventPara();
                    eventPara.id = event.getId();
                    eventPara.title = event.getTitle();
                    eventPara.lineType = event.getLineType();
                    CityReq city = new CityReq();
                    city.name = event.getCityName();
                    city.id = event.getCityId();
                    eventPara.city = city;
                    eventPara.startTime = event.getStartTime();
                    eventPara.startTimeStr = DateUtil.getWeekAndDate(eventPara.startTime);
                    eventPara.sortType = getSortType(event, hotEvents, recommendEvents, null);
                    eventPara.logo = cloudEventConfig.getImage_prefix() + event.getLogo();
                    eventPara.eventType = event.getTemplateId().byteValue();
                    if (linkCommonUrlMap.containsKey(event.getId())) {
                        eventPara.commonUrl = linkCommonUrlMap.get(event.getId());
                    }
                    if (sponsorsMap.containsKey(event.getId())) {
                        eventPara.sponsors = sponsorsMap.get(event.getId());
                    }

                    if (event.getTemplateId().equals(1)) {
                        EventStateCustom eventStateCustom = eventStateCustomMap.get(event.getId());
                        CustomModule customModule = new CustomModule();
                        customModule.numberStatus = eventStateCustom.getCustomNumberState().intValue();
                        eventPara.customModule = customModule;
                        if (eventStateCustom.getJoinedCount() != null) {
                            eventPara.recordNum = eventStateCustom.getJoinedCount();
                        }
                    } else {
                        EventStateProject eventStateProject = eventStateProjectMap.get(event.getId());
                        ProjectModule projectModule = new ProjectModule();
                        projectModule.numberStatus = eventStateProject.getProjectNumberState();
                        eventPara.projectModule = projectModule;

                        if (eventStateProject.getJoinedCount() != null) {
                            eventPara.recordNum = eventStateProject.getJoinedCount();
                        }
                    }
                    eventParaList.add(eventPara);
                }
            }

            response.list = eventParaList;
        }
        return response;
    }

    /**
     * 拿到有效的活动列表
     *
     * @param req
     * @return
     */
    public List<Event> getValidEventList(OrgReq req, List<Integer> recommendIds) {
        EventExample eventExample = new EventExample();
        EventExample.Criteria criteria = eventExample.createCriteria();
        List<Byte> notInState = new ArrayList<>();
        notInState.add(SqlEnum.EventStateType.TYPE_EVENT_DELETED.getByteValue());
        notInState.add(SqlEnum.EventStateType.TYPE_EVENT_PUBLISH_NOT.getByteValue());
        if (req.withPage) {
            eventExample.setLimit(req.getSize());
            eventExample.setOffset(PagingUtil.getStart(req.getPage(), req.getSize()));
        }
        eventExample.setOrderByClause("start_time desc");
        List<Integer> templateIds = new ArrayList<>();
        if (req.eventType == null) {
            templateIds.add(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue());
            templateIds.add(SqlEnum.EventType.TYPE_EVENT_ROADSHOW.getValue());
            templateIds.add(SqlEnum.EventType.TYPE_EVENT_FINANCE_DELIVERY.getValue());
        } else {
            switch (req.eventType) {
                case 1:
                    templateIds.add(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue());
                    break;
                case 2:
                    templateIds.add(SqlEnum.EventType.TYPE_EVENT_ROADSHOW.getValue());
                    templateIds.add(SqlEnum.EventType.TYPE_EVENT_FINANCE_DELIVERY.getValue());
                    break;
            }
        }

        criteria.andOrgIdEqualTo(req.orgId).andStateNotIn(notInState).andOpenExtensionEqualTo
                (SqlEnum.OpenExtensionType.TYPE_OPEN.getByteValue()).andTemplateIdIn(templateIds);
        if (!StringUtil.isEmpty(recommendIds)) {
            criteria.andIdIn(recommendIds);
        }

        return eventMapper.selectByExample(eventExample);
    }

    private OrgInfoResp converseOrg(Organization organization) {
        OrgInfoResp orgInfo = new OrgInfoResp();
        orgInfo.id = organization.orgId;
        orgInfo.name = organization.name;
        orgInfo.intro = organization.intro;
        OrgAuthResp orgAuthResp = new OrgAuthResp();
        if (organization.authInfo != null) {
            orgAuthResp.verifyStatus = organization.authInfo.verifyStatus;
        }
        orgInfo.auth = orgAuthResp;
        orgInfo.logo = organization.logo;
        return orgInfo;
    }

    private Map<Integer, List<Sponsor>> getSponsorsMap(List<EventSponsorManage> sponsorList) {
        Map<Integer, List<Sponsor>> sponsorsMap = new HashMap<>();
        for (EventSponsorManage eventSponsorManage : sponsorList) {
            List<Sponsor> lists;
            Sponsor sponsor = new Sponsor();
            if (!sponsorsMap.containsKey(eventSponsorManage.getEventId())) {
                lists = new ArrayList<>();
                sponsor.id = eventSponsorManage.getEventId();
                sponsor.name = eventSponsorManage.getSponsorName();
                lists.add(sponsor);
            } else {
                lists = sponsorsMap.get(eventSponsorManage.getEventId());
                sponsor.id = eventSponsorManage.getEventId();
                sponsor.name = eventSponsorManage.getSponsorName();
                lists.add(sponsor);
            }
            sponsorsMap.put(eventSponsorManage.getEventId(), lists);
        }
        return sponsorsMap;
    }

    private Map<Integer, List<Guest>> getGuestMap(List<EventGuest> guestList) {
        Map<Integer, List<Guest>> guestMap = new HashMap<>();
        for (EventGuest eventGuest : guestList) {
            List<Guest> lists;
            Guest guest = new Guest();
            if (!guestMap.containsKey(eventGuest.getEventId())) {
                lists = new ArrayList<>();
                guest.avatar = eventGuest.getAvatar();
                guest.name = eventGuest.getName();
                guest.position = eventGuest.getPosition();
                guest.company = eventGuest.getCompany();
                lists.add(guest);
            } else {
                lists = guestMap.get(eventGuest.getEventId());
                guest.avatar = eventGuest.getAvatar();
                guest.name = eventGuest.getName();
                guest.position = eventGuest.getPosition();
                guest.company = eventGuest.getCompany();
                lists.add(guest);
            }
            guestMap.put(eventGuest.getEventId(), lists);
        }
        return guestMap;
    }


    /**
     * 判断调用的标签
     *
     * @param event
     * @return
     */

    public Byte getSortType(Event event, List<EventCityRelation> hotEvents, List<EventCityRelation> recommendEvents,
                            Integer cityId) {
        Byte sortType = ParamEnum.EventSortType.DEGREE_NORMAL.getByteValue();
        Integer eventId = event.getId();
        List<Integer> hotEventIds = new ArrayList<>();
        Map<Integer, EventCityRelation> hotEventsMap = new HashMap<>();
        for (EventCityRelation relation : hotEvents) {
            hotEventIds.add(relation.getEventId());
            hotEventsMap.put(relation.getEventId(), relation);
        }
        List<Integer> recommendEventIds = new ArrayList<>();
        Map<Integer, EventCityRelation> recommendEventsMap = new HashMap<>();
        for (EventCityRelation relation : recommendEvents) {
            recommendEventIds.add(relation.getEventId());
            recommendEventsMap.put(relation.getEventId(), relation);
        }
        if (recommendEventIds.contains(eventId) &&
                (DateUtil.getNowDate().getTime() - recommendEventsMap.get(eventId).getRecommendTime() <= 24 * 3600 *
                        1000)) {
            sortType = ParamEnum.EventSortType.DEGREE_NEW.getByteValue();
        }
        if (hotEventIds.contains(eventId)) {
            EventCityRelation eventCityRelation = hotEventsMap.get(eventId);
            if (cityId == null) {
                cityId = 0;
            }
            if (cityId <= 0 || eventCityRelation.getCityId().equals(cityId)) {
                sortType = ParamEnum.EventSortType.DEGREE_HOT.getByteValue();
            }
        }
        if ((DateUtil.getNowDate().getTime() > event.getDeadlineTime())) {
            sortType = ParamEnum.EventSortType.DEGREE_DEADLINE.getByteValue();
        }
        if (DateUtil.getNowDate().getTime() > event.getEndTime()) {
            sortType = ParamEnum.EventSortType.DEGREE_FINISHED.getByteValue();
        }
        return sortType;
    }

    /**
     * 活动城市列表
     */

    public Object getEventCityList() {
        AppResp appRespR = new AppResp();
        appRespR.list = new ArrayList<>();
        String key = "json_citylist";

        String jsonR1 = redisService.getString(key);
        if (!StringUtil.isEmpty(jsonR1)) {
            appRespR = JSONUtil.json2Obj(jsonR1, AppResp.class);
            return appRespR;
        }

        //查询系统中所有创建过活动的城市id列表 (131,289,179,257,340,75,218) 默认添加到最前面
        List<Integer> cityIds = eventMapper.selectEventCityIdList();
        cityIds.add(0, 131);
        cityIds.add(1, 289);
        cityIds.add(2, 179);
        cityIds.add(3, 257);
        cityIds.add(4, 340);
        cityIds.add(5, 75);
        cityIds.add(6, 218);

        Map<Integer, String> allCities = cityRemoteService.getAllCityMap();
        for (Integer cityId : cityIds) {
            if (allCities.containsKey(cityId) == false) {
                continue;
            }
            AppResp appResp = new AppResp();
            appResp.id = cityId;
            appResp.name = allCities.get(cityId);
            appRespR.list.add(appResp);
        }

        String jsonR = JSONUtil.obj2Json(appRespR);
        redisService.putString(key, jsonR, 3600 * 24);

        return appRespR;
    }

    /**
     * 查看用户报名详细记录
     */

    public Object checkOrderStatus(AppReq req) {
        AppResp appRespR = new AppResp();

        Event event = getEvent(req.eventId);
        UserResp user = new UserResp();
        ProjectResp project = new ProjectResp();
        if (event == null) {
            throw ExceptionUtil.createParamException("活动参数错误");
        }
        //活动信息
        EventPara eventPara = conversePara(event, EventDataLevel.LEVEL1.getLevel());
        eventPara.type = event.getTemplateId().byteValue();

        EventRecordExample eventRecordAppExample = new EventRecordExample();
        eventRecordAppExample.createCriteria().andEventIdEqualTo(req.eventId).andUidEqualTo(req.uid);
        List<EventRecord> eventRecords = eventRecordMapper.selectByExample(eventRecordAppExample);
        if (!StringUtil.isEmpty(eventRecords)) {
            //如果不传，表示从来没有报过名
            eventPara.recordStatus = eventRecords.get(0).getState();
            //报名用户信息
            EventRecordUserExample recordUserExample = new EventRecordUserExample();
            recordUserExample.createCriteria().andEventRecordIdEqualTo(eventRecords.get(0).getId());
            List<EventRecordUser> eventRecordUsers = eventRecordUserMapper.selectByExample(recordUserExample);
            if (!StringUtil.isEmpty(eventRecordUsers)) {
                user.id = eventRecordUsers.get(0).getUid();
                user.name = eventRecordUsers.get(0).getName();
                user.mobile = eventRecordUsers.get(0).getPhone();
                user.company = eventRecordUsers.get(0).getCompany();
                user.position = eventRecordUsers.get(0).getPosition();
            }

            //项目信息
            ProjectBackupInfoExample projectExample = new ProjectBackupInfoExample();
            projectExample.createCriteria().andEventRecordIdEqualTo(eventRecords.get(0).getId());
            List<ProjectBackupInfo> projectBackupInfos = projectBackupInfoMapper.selectByExample(projectExample);
            if (!StringUtil.isEmpty(projectBackupInfos)) {
                project.id = projectBackupInfos.get(0).getId();
                project.name = projectBackupInfos.get(0).getName();
                project.logo = projectBackupInfos.get(0).getLogo();
                project.intro = projectBackupInfos.get(0).getIntro();
                BPResp bp = new BPResp();
                bp.bpId = projectBackupInfos.get(0).getProjectSignBpId();
                bp.bpName = projectBackupInfos.get(0).getProjectSignBpName();
                project.bp = bp;
            }
        }
        appRespR.event = eventPara;
        appRespR.user = user;
        appRespR.project = project;
        return appRespR;
    }

    /**
     * app端调用-根据活动获取我的票券相关信息
     *
     * @param req
     * @return
     */

    public TicketInfoResp getMyTicket(AppReq req) {
        if (!NumberUtil.isValidId(req.recordId)) {
            throw ExceptionUtil.createParamException("recordId参数错误");
        }
        EventRecord eventRecord = eventRecordMapper.selectByPrimaryKey(req.recordId);
        if (eventRecord == null) {
            throw ExceptionUtil.createParamException("活动报名记录不存在");
        }
        TicketInfoResp response = new TicketInfoResp();
        Event event = eventMapper.selectByPrimaryKey(eventRecord.getEventId());
        EventPara eventInfo = getEventDetailByLevel(event, EventDataLevel.LEVEL13.getLevel(), req.uid);
        //为了前端查看到清晰的数据，置空detail字段
        eventInfo.detail = null;
        if (eventInfo.city != null && eventInfo.city.name.length() > 0) {
            eventInfo.address = eventInfo.city.name + eventInfo.address;
        }
        response.event = eventInfo;
        response.event.recordStatus = eventRecord.getState();

        BaseResult<Order> orderBaseResult = commodityOrderClient.get(eventRecord.getTradeNo());
        if (orderBaseResult.getData() == null && !SqlEnum.IsImport.TYPE_IS_IMPORT.getValue().equals(eventRecord
                .getIsImport())) {
            throw ExceptionUtil.createParamException("订单异常");
        }


        EventRecordUserExample eventUserExample = new EventRecordUserExample();
        eventUserExample.createCriteria().andEventRecordIdEqualTo(req.recordId);
        EventRecordUser eventRecordUser = eventRecordUserMapper.selectByExample(eventUserExample).get(0);
        EventRecordUserResp userResp = new EventRecordUserResp();
        if (eventRecordUser != null) {
            userResp.company = eventRecordUser.getCompany();
            userResp.name = eventRecordUser.getName();
            userResp.phone = eventRecordUser.getPhone();
            userResp.position = eventRecordUser.getPosition();
            userResp.uid = eventRecordUser.getUid();
        }
        response.user = userResp;
        List<AddtionalForm> addtionalForm = getAddtionForm(event, req.recordId);
        response.event.additionalForm = addtionalForm;
        response.event.timeRange = DateUtil.converseTime(event.getStartTime(), event.getEndTime());
        response.event.eventId = event.getId();
        response.event.logo = cloudEventConfig.getImage_prefix() + event.getLogo();
        response.event.loc = new LocReq();
        response.event.loc.lat = event.getLatitude();
        response.event.loc.lng = event.getLongitude();
        //订单
        TradeResp tradeResp = new TradeResp();
        tradeResp.tradeNo = eventRecord.getTradeNo();
        tradeResp.expireTime = eventRecord.getTradeEndTime();
        tradeResp.signUpTime = eventRecord.getCreateTime();
        Order order;
        if (SqlEnum.IsImport.TYPE_IS_IMPORT.getValue().equals(eventRecord.getIsImport())) {
            order = new Order();
            //其他支付方式
            order.payType = 5;
            order.status = 5;
        } else {
            order = orderBaseResult.getData();
        }
        tradeResp.payType = order.payType;
        tradeResp.state = order.status;
        tradeResp.recordId = eventRecord.getId();
        response.trade = tradeResp;

        // 活动路演大赛类型才会有项目信息
        if (event.getTemplateId() != SqlEnum.EventType.TYPE_EVENT_COMMON.getValue()) {
            ProjectBackupInfoExample projectExample = new ProjectBackupInfoExample();
            projectExample.createCriteria().andEventRecordIdEqualTo(req.recordId);
            List<ProjectBackupInfo> eventRecordProjects = projectBackupInfoMapper.selectByExample(projectExample);
            ProjectResp projectResp = new ProjectResp();
            if (!StringUtil.isEmpty(eventRecordProjects)) {
                ProjectBackupInfo eventRecordProject = eventRecordProjects.get(0);
                projectResp = converseProject(eventRecordProject);
            }
            response.project = projectResp;
        } else {
            //订单状态 DELETED(99, "已删除"), TOPAY(0, "待支付"), PAID(1, "已支付"), TIMEOUT(2, "超时未付款"),
            // CANCELED(3, "已取消"), SERVER_CANCELED(4, "后台取消"), COMPLETED(5, "已完成"), CLOSED(6, "已关闭"),
            // REFUND(9, "已退款"), FAIL(10, "支付失败");
            if (order.status == 1 || order.status == 5) {
                //签到url
                response.event.signinUrl = cloudEventConfig.getLink_signup_prefix() + "u?t=" + eventRecord.getTradeNo();
                //票信息
                List<EventRecord> eventRecordList = new ArrayList<>();
                eventRecordList.add(eventRecord);
                response.ticketTypes = this.converseTicket(event, eventRecordList, req.ticketNo);
            } else {
                //票信息
                List<Ticket> ticketModules = commodityRemoteService.converseToTicket(order.orderDetails.get(0)
                        .commodityId);
                response.ticketTypes = this.converseTicketOrder(order, ticketModules, req.ticketNo);
            }
            response.canCancel = SqlEnum.IsImport.TYPE_IS_IMPORT.getValue().equals(eventRecord.getIsImport()) ?
                    ParamEnum.SignupCancel.CANCEL_CAN_NOT.getValue() : ParamEnum.SignupCancel.CANCEL_CAN.getValue();

        }
        //后端自己拼接uniquekey，形式为{"uniqueKey":"1987651818","aid":1623}后加密
        String uniqueKey = extensionLinkMapper.selectByPrimaryKey(eventRecord.getExtensionLinkId())
                .getUniqueKey();
        response.event.resultUrl = cloudEventConfig.getLink_jump_result_prefix() + Base64Utils.encode
                ((uniqueKey + "&" + event.getId()).getBytes());

        return response;
    }

    /**
     * 获取活动的自定义字段
     *
     * @param event
     * @return
     */
    private List<AddtionalForm> getAddtionForm(Event event, Integer eventRecordId) {
        List<AddtionalForm> addtionalForm = new ArrayList<>();
        EventCustomFieldExample customFieldExample = new EventCustomFieldExample();
        customFieldExample.createCriteria().andEventIdEqualTo(event.getId());
        List<EventCustomField> eventCustomFields = eventCustomFieldMapper.selectByExample(customFieldExample);
        List<Integer> collectionIds = new ArrayList<>();
        for (EventCustomField field : eventCustomFields) {
            collectionIds.add(field.getId());
        }
        List<EventRecordCollection> collections = new ArrayList<>();
        if (!StringUtil.isEmpty(collectionIds)) {
            EventRecordCollectionExample collectionExample = new EventRecordCollectionExample();
            collectionExample.createCriteria().andCollectionIdIn(collectionIds).andEventRecordIdEqualTo(eventRecordId);
            collections = eventRecordCollectionMapper.selectByExample(collectionExample);
        }
        Map<Integer, String> valueMap = new HashMap<>();
        for (EventRecordCollection collection : collections) {
            valueMap.put(collection.getCollectionId(), collection.getContent());
        }

        if (!StringUtil.isEmpty(eventCustomFields)) {
            for (EventCustomField field : eventCustomFields) {
                AddtionalForm form = new AddtionalForm();
                form.id = field.getId();
                form.label = field.getTitle();
                form.type = field.getFieldType();
                form.limit = field.getLimitCount();
                form.required = field.getRequired();
                form.value = valueMap.get(field.getId());
                addtionalForm.add(form);
            }
        }
        return addtionalForm;
    }

    private ProjectResp converseProject(ProjectBackupInfo eventRecordProject) {
        ProjectResp projectResp = new ProjectResp();
        if (eventRecordProject != null) {
            if (!StringUtil.isEmpty(cloudEventConfig.getImage_prefix())) {
                projectResp.logo = cloudEventConfig.getImage_prefix() + eventRecordProject.getLogo();
            }
            projectResp.pid = eventRecordProject.getPid();
            projectResp.name = eventRecordProject.getName();
            projectResp.intro = eventRecordProject.getIntro();
            BPResp bp = new BPResp();
            bp.bpId = eventRecordProject.getProjectSignBpId();
            bp.bpUrl = cloudEventConfig.getBp_prefix() + eventRecordProject.getProjectSignBpUrl();
            bp.bpName = eventRecordProject.getProjectSignBpName();
            projectResp.bp = bp;
        }
        return projectResp;
    }

    /**
     * 取用户创建 收藏 报名 活动列表
     */

    public Object userEventList(AppReq req) {

        EventPara eventParaR = new EventPara();
        eventParaR.list = new ArrayList<>();

        //1收藏的(event_collection,event)，2取报名(event_record,event)，3取创建(event)
        UserEventSearchPara eventSearchReq = new UserEventSearchPara();
        eventSearchReq.eventType = req.eventType;
        eventSearchReq.uid = req.uid;
        eventSearchReq.page = PagingUtil.getStart(req.page, req.size);
        eventSearchReq.size = req.size;


        List<Event> eventList = null;
        List<EventRecord> eventRecordList = null;
        List<Integer> eventIdList = new ArrayList<>();
        if (req.type == 1) { //1取收藏的活动列表，2取报名活动列表，3取创建的活动列表
            eventList = eventMapper.selectUserCollectionEventList(eventSearchReq);
        } else if (req.type == 2) {
            eventRecordList = eventRecordMapper.selectUserSignUpEventRecordList(eventSearchReq);
        } else if (req.type == 3) {
            eventList = eventMapper.selectUserCreateEventList(eventSearchReq);
        }

        if (!StringUtil.isEmpty(eventList)) {
            for (Event event : eventList) {
                eventIdList.add(event.getId());
            }
        } else if (!StringUtil.isEmpty(eventRecordList)) {
            for (EventRecord eventRecord : eventRecordList) {
                eventIdList.add(eventRecord.getEventId());
            }
        }

        List<EventCityRelation> hotEvents = eventCityRelationModule.getHotOrRecommendEvents(ParamEnum.HotOrRcmType
                .TYPE_HOT, eventIdList);
        List<EventCityRelation> recommendEvents = eventCityRelationModule.getHotOrRecommendEvents(ParamEnum
                .HotOrRcmType.TYPE_RECOMMENT, eventIdList);

        if (req.type == 2) {
            if (eventRecordList != null && eventRecordList.size() > 0) {
                List<Integer> eventIds = new ArrayList<>();
                List<EventPara> eventParaList = new ArrayList<>();
                for (EventRecord eventRecord : eventRecordList) {
                    eventIds.add(eventRecord.getEventId());

                    EventPara eventPara = new EventPara();
                    eventPara.eventId = eventRecord.getEventId();

                    // 0: 正常；   1 用户主动取消报名;  2: 主办方删除报名记录 ；  3 ：待审核； 4: 审核拒绝
                    if (eventRecord.getTicketLock() == 1 && eventRecord.getTradeEndTime() > System.currentTimeMillis
                            ()) {
                        eventPara.tradeStatus = 1; //1 待付款
                    } else if (eventRecord.getState() == 0 && eventRecord.getTicketLock() == 0) {
                        eventPara.tradeStatus = 4; //4 报名成功
                    } else if (eventRecord.getState() == 3) {
                        eventPara.tradeStatus = 2;// 2 审核中
                    } else if (eventRecord.getState() == 4) {
                        eventPara.tradeStatus = 3;// 3 审核未通过
                    }

                    eventPara.jumpUrl = cloudEventConfig.getLink_ticket_prefix() +
                            Base64Utils.encode(eventRecord.getId().toString().getBytes()).toString();
                    eventPara.jumpUrlCompatibility = cloudEventConfig.getLink_ticket_compatibility_prefix() +
                            Base64Utils.encode(eventRecord.getId().toString().getBytes()).toString();
                    eventParaList.add(eventPara);
                }

                if (eventIds == null || eventIds.size() == 0) {
                    return eventParaR;
                }

                Map<Integer, Event> eventMap = this.selectBatchEvent(eventIds);
                Map<Integer, EventStateCustom> eventStateCustomMap = this.selectBatchEventStateCustom(eventIds);
                Map<Integer, List<Sponsor>> sponsorMap = selectBatchSponsor(eventIds);
                Map<Integer, ExtensionLink> extensionLinkMap = this.selectBatchExtensionLink(eventIds);
                Map<Integer, EventStateProject> eventStateProjectMap = this.selectBatchEventStateProject(eventIds);
                Map<Integer, Boolean> eventFavoriteMap = this.selectEventFavorite(eventIds, req.uid);
                Map<Integer, EventRecord> recordsMap = this.selectEventRecord(eventIds, req.uid);
                for (EventPara eventPara : eventParaList) {
                    Event event = eventMap.get(eventPara.eventId);
                    EventPara eventR = converseParaSimple(event);
                    eventR.eventId = eventPara.eventId;
                    eventR.tradeStatus = eventPara.tradeStatus;
                    eventR.jumpUrl = eventPara.jumpUrl;
                    eventR.jumpUrlCompatibility = eventPara.jumpUrlCompatibility;
                    eventR = getAppBasicEventPara(eventR, recordsMap, event, eventFavoriteMap, eventStateProjectMap,
                            eventStateCustomMap,
                            extensionLinkMap, sponsorMap, hotEvents,
                            recommendEvents, null, null, 0);

                    eventParaR.list.add(eventR);
                }
            }
        } else {
            List<Integer> commodityIds = new ArrayList<>();
            List<Integer> eventIds = new ArrayList<>();
            for (Event event : eventList) {
                commodityIds.add(event.getCommodityId());
                eventIds.add(event.getId());
            }

            if (eventIds == null || eventIds.size() == 0) {
                return eventParaR;
            }

            Map<Integer, List<Ticket>> ticketsMap = commodityRemoteService.getTicketsMap(commodityIds);
            Map<Integer, EventStateCustom> eventStateCustomMap = selectBatchEventStateCustom(eventIds);
            Map<Integer, List<Sponsor>> sponsorMap = selectBatchSponsor(eventIds);
            Map<Integer, ExtensionLink> extensionLinkMap = selectBatchExtensionLink(eventIds);
            Map<Integer, EventStateProject> eventStateProjectMap = selectBatchEventStateProject(eventIds);
            Map<Integer, EventRecord> recordsMap = this.selectEventRecord(eventIds, req.uid);
            Map<Integer, Boolean> eventFavoriteMap = this.selectEventFavorite(eventIds, req.uid);
            if (eventList != null && eventList.size() > 0) {
                for (Event event : eventList) {
                    EventPara eventPara = converseParaSimple(event);
                    eventPara = getAppBasicEventPara(eventPara, recordsMap, event, eventFavoriteMap,
                            eventStateProjectMap,
                            eventStateCustomMap, extensionLinkMap, sponsorMap, hotEvents,
                            recommendEvents, ticketsMap, null, 1);

                    eventParaR.list.add(eventPara);
                }
            }
        }
        return eventParaR;
    }

    /**
     * app活动排序列表
     */

    public Object orderedList(AppReq req) {
        EventPara eventParaR = new EventPara();
        eventParaR.list = new ArrayList<>();

        UserEventSearchPara eventSearchReq = new UserEventSearchPara();
        eventSearchReq.page = PagingUtil.getStart(req.page, req.size);
        eventSearchReq.size = req.size;
        if (req.cityId == null) {
            req.cityId = -100001; //所有活动
        }
        eventSearchReq.cityId = req.cityId;
        eventSearchReq.ruleId = req.ruleId;
        eventSearchReq.nowTime = System.currentTimeMillis();
        eventSearchReq.eventType = ParamEnum.EventType.TYPE_ROAD.getValue();

        List<Event> eventList = new ArrayList<>();
        if (req.ruleId == 0) {
            eventList = eventMapper.selectOrderedeCommendList(eventSearchReq);
        } else if (req.ruleId == 1) {
            eventList = eventMapper.selectOrderedeNewList(eventSearchReq);
        } else if (req.ruleId == 2) {
            eventList = eventMapper.selectOrderedeHotList(eventSearchReq);
        }
        List<Integer> eventIdss = new ArrayList();
        for (Event event : eventList) {
            eventIdss.add(event.getId());
        }
        List<EventCityRelation> hotEvents = eventCityRelationModule.getHotOrRecommendEvents(ParamEnum.HotOrRcmType
                .TYPE_HOT, eventIdss);
        List<EventCityRelation> recommendEvents = eventCityRelationModule.getHotOrRecommendEvents(ParamEnum
                .HotOrRcmType.TYPE_RECOMMENT, eventIdss);

        if (eventList != null && eventList.size() > 0) {
            List<Integer> commodityIds = new ArrayList<>();
            List<Integer> eventIds = new ArrayList<>();
            for (Event event : eventList) {
                commodityIds.add(event.getCommodityId());
                eventIds.add(event.getId());
            }

            if (eventIds == null || eventIds.size() == 0) {
                return eventParaR;
            }

            Map<Integer, List<Ticket>> ticketsMap = commodityRemoteService.getTicketsMap(commodityIds);
            Map<Integer, List<Sponsor>> sponsorMap = selectBatchSponsor(eventIds);
            Map<Integer, ExtensionLink> extensionLinkMap = selectBatchExtensionLink(eventIds);
            Map<Integer, EventStateProject> eventStateProjectMap = selectBatchEventStateProject(eventIds);
            Map<Integer, EventRecord> recordsMap = this.selectEventRecord(eventIds, req.uid);
            Map<Integer, Boolean> eventFavoriteMap = this.selectEventFavorite(eventIds, req.uid);
            for (Event event : eventList) {
                EventPara eventPara = converseParaSimple(event);
                eventPara = getAppBasicEventPara(eventPara, recordsMap, event, eventFavoriteMap,
                        eventStateProjectMap, null,
                        extensionLinkMap, sponsorMap, hotEvents,
                        recommendEvents, ticketsMap, req.cityId, 1);

                eventParaR.list.add(eventPara);
            }
        }

        return eventParaR;
    }

    /**
     * 用户主动取消报名
     *
     * @param req
     * @return
     */

    public Object signUpCancel(AppReq req) {
        if (!NumberUtil.isValidId(req.from)) {
            throw ExceptionUtil.createParamException("from参数错误");
        }

        //如果来源于app
        if (req.from.equals(ParamEnum.FromSource.TYPE_APP.getValue())) {
            req.recordId = eventRecordService.getRecordByTradeNo(req.ticketNo,true).getId();
        }
        if (!NumberUtil.isValidId(req.recordId)) {
            throw ExceptionUtil.createParamException("recordId参数错误");
        }
        iEventRecordProjectInfo.signUpCancel(req.recordId);

        return null;
    }


    public Object task() {
        scheduledTask.taskSendMsgLastMoring10();
        return null;
    }

    /**
     * 修改活动状态信息
     *
     * @param eventPara
     * @return
     */

    public ExtensionLinkResp updateStatus(EventPara eventPara) {
        if (eventPara.type == null) {
            throw ExceptionUtil.createParamException("参数错误");
        }
        if (!NumberUtil.isValidId(eventPara.eventId)) {
            throw ExceptionUtil.createParamException("参数错误");
        }
        Event eventGet = getEvent(eventPara.eventId);
        if (eventGet == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        Event event = new Event();
        switch (eventPara.type) {
            case 0:
                //如果活动已结束，不能进行取消发布操作
                if (eventGet.getEndTime() < System.currentTimeMillis()) {
                    throw ExceptionUtil.createWarnException("活动已结束，无法取消发布");
                }
                //如果有报名记录，需要把所有的报名记录全部删除，才能取消发布
                if (eventRecordService.checkHasRecord(eventPara.eventId) || eventRecordService.checkHasToPayOrder
                        (eventPara.eventId)) {
                    throw ExceptionUtil.createWarnException("存在报名或者未付款记录，不能取消发布！");
                }
                event.setState(SqlEnum.EventStateType.TYPE_EVENT_PUBLISH_NOT.getByteValue());
                break;
            case 1:
                if (eventGet.getEndTime() < System.currentTimeMillis()) {
                    throw ExceptionUtil.createWarnException("活动已结束，请先编辑时间后发布");
                }
                if (eventGet.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue()) &&
                        stateCustomModuleService.get(eventGet.getId()).getCostType().equals(SqlEnum.CostType
                                .TYPE_CHARGE.getByteValue())) {
                    checkOrg(eventGet.getOrgId());
                }
                event.setState(SqlEnum.EventStateType.TYPE_EVENT_PUBLISHED.getByteValue());
                break;
            case 4:
                if (eventRecordService.checkHasRecord(eventPara.eventId) || eventRecordService.checkHasToPayOrder
                        (eventPara.eventId)) {
                    throw ExceptionUtil.createWarnException("存在报名或者未付款记录，不能删除！");
                }
                event.setState(SqlEnum.EventStateType.TYPE_EVENT_DELETED.getByteValue());
                //删除商品
                commodityRemoteService.deleteCommodity(eventGet.getCommodityId());
                break;
            default:
                throw ExceptionUtil.createParamException("参数错误");
        }
        event.setId(eventPara.eventId);
        event.setModifyTime(System.currentTimeMillis());
        updateEvent(event);
        ExtensionLinkResp resp = new ExtensionLinkResp();
        resp.commonUrl = extensionLinkService.getDefaultLinkByEventId(event.getId()).getLinkUrlCommon();
        return resp;
    }


    /**
     * 获取活动详情content
     */

    public Object getcontent(AppReq req) {
        Event event = eventMapper.selectByPrimaryKey(req.eventId);
        if (event == null) {
            throw ExceptionUtil.createParamException("未找到此活动");
        }

        return event.getDetail();
    }

    public String getRandomDigital() {
        Random ra = new Random();
        Integer n2 = 99999;
        Integer n1 = 10000;
        Double d = ra.nextDouble() * (n2 - n1) + n1;
        return String.valueOf(d.intValue());
    }

    private List<Ticket> converseTicket(Event event, List<EventRecord> eventRecordList, String ticketNo) {
        List<Ticket> ticketTypes = new ArrayList<>();

        List<Ticket> ticketModules = commodityRemoteService.converseToTicket(event.getCommodityId());
        if (ticketModules != null && ticketModules.size() > 0) {
            for (Ticket ticket : ticketModules) { //票种
                Ticket ticketR = new Ticket();
                ticketR.ticketList = new ArrayList<>();
                ticketR.title = ticket.title;
                ticketR.intro = ticket.intro;

                Integer size = 0;
                for (EventRecord eventRecord : eventRecordList) { //报名记录
                    EventTicketOrderExample eventTicketOrderExample = new EventTicketOrderExample();
                    eventTicketOrderExample.createCriteria().andEventIdEqualTo(event.getId()).
                            andEventRecordIdEqualTo(eventRecord.getId()).andTicketIdEqualTo(ticket.id);
                    List<EventTicketOrder> eventTicketOrders = eventTicketOrderMapper.selectByExample
                            (eventTicketOrderExample);

                    if (eventTicketOrders != null && eventTicketOrders.size() > 0) {
                        size = size + eventTicketOrders.size();
                        if (ticketNo != null) {
                            for (EventTicketOrder eventTicketOrder : eventTicketOrders) { //订单票
                                if (ticketNo.equals(eventTicketOrder.getTicketNo())) {
                                    TicketInfo ticketInfo = new TicketInfo();
                                    ticketInfo.id = eventTicketOrder.getCommodityDetailId();
                                    ticketInfo.ticketNo = eventTicketOrder.getTicketNo();
                                    ticketInfo.state = eventTicketOrder.getSignState();
                                    ticketInfo.recordId = eventRecord.getId();
                                    ticketR.ticketList.add(ticketInfo);
                                }
                            }
                        } else {
                            for (EventTicketOrder eventTicketOrder : eventTicketOrders) { //订单票
                                TicketInfo ticketInfo = new TicketInfo();
                                ticketInfo.id = eventTicketOrder.getCommodityDetailId();
                                ticketInfo.ticketNo = eventTicketOrder.getTicketNo();
                                ticketInfo.state = eventTicketOrder.getSignState();
                                ticketInfo.recordId = eventRecord.getId();
                                ticketR.ticketList.add(ticketInfo);
                            }
                        }
                    }
                }
                ticketR.price = ticket.price;
                ticketR.buyCount = size;

                if (ticketR.ticketList != null && ticketR.ticketList.size() > 0) {
                    ticketTypes.add(ticketR);
                }
            }
        }

        return ticketTypes;
    }

    public List<Ticket> converseTicketOrder(Order order, List<Ticket> ticketModules, String ticketNo) {

        if (order.orderDetails == null || order.orderDetails.size() == 0) {
            throw ExceptionUtil.createParamException("票信息错误");
        }

        List<Ticket> ticketTypes = new ArrayList<>();
        if (ticketModules != null && ticketModules.size() > 0) {
            for (Ticket ticket : ticketModules) { //票种
                Ticket ticketR = new Ticket();
                ticketR.ticketList = new ArrayList<>();
                ticketR.title = ticket.title;
                ticketR.intro = ticket.intro;

                Integer size = 0;

                if (ticketNo != null) {
                    for (OrderDetail orderDetail : order.orderDetails) { //报名记录
                        if (ticket.id.equals(orderDetail.standardId) && order.orderNo.equals(ticketNo)) {
                            size++;
                            TicketInfo ticketInfo = new TicketInfo();
                            ticketInfo.id = orderDetail.standardId;
                            ticketInfo.ticketNo = order.orderNo;
                            ticketR.ticketList.add(ticketInfo);
                        }
                    }
                } else {
                    for (OrderDetail orderDetail : order.orderDetails) { //报名记录
                        if (ticket.id.equals(orderDetail.standardId)) {
                            size++;
                            TicketInfo ticketInfo = new TicketInfo();
                            ticketInfo.id = orderDetail.standardId;
                            ticketInfo.ticketNo = order.orderNo;
                            ticketR.ticketList.add(ticketInfo);
                        }
                    }
                }
                ticketR.price = ticket.price;
                ticketR.buyCount = size;

                if (ticketR.ticketList != null && ticketR.ticketList.size() > 0) {
                    ticketTypes.add(ticketR);
                }
            }
        }
        return ticketTypes;
    }


    public RecordsUserResp getRecordsUser(EventPara req) {
        if (NumberUtil.isInValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("evnetId参数错误");
        }
        if (NumberUtil.isInValidId(req.getPage())) {
            throw ExceptionUtil.createParamException("page参数错误");
        }
        if (NumberUtil.isInValidId(req.getSize())) {
            throw ExceptionUtil.createParamException("size参数错误");
        }
        EventRecordExample recordExample = new EventRecordExample();
        List<Byte> validStates = new ArrayList();
        validStates.add(SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getByteValue());
        validStates.add(SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY.getByteValue());
        recordExample.setLimit(req.getSize());
        recordExample.setOffset(PagingUtil.getStart(req.getPage(), req.getSize()));
        recordExample.createCriteria().andTicketLockEqualTo(SqlEnum.TicketLockType.TYPE_NORMAL.getValue()).
                andEventIdEqualTo(req.eventId).andStateIn(validStates);
        List<EventRecord> eventRecords = eventRecordMapper.selectByExample(recordExample);
        RecordsUserResp response = new RecordsUserResp();
        List<UserResp> userResps = new ArrayList<>();
        if (StringUtil.isEmpty(eventRecords)) {
            response.list = userResps;
            response.count = 0;
            return response;
        }
        List<Integer> uids = new ArrayList<>();
        List<Integer> recordIds = new ArrayList<>();
        List<String> tradeNos = new ArrayList<>();
        //有一个Map<RecordId,tradeNo>的中间关系map
        Map<Integer, String> relationMap = new HashMap<>();
        for (EventRecord eventRecord : eventRecords) {
            uids.add(eventRecord.getUid());
            recordIds.add(eventRecord.getId());
            tradeNos.add(eventRecord.getTradeNo());
            relationMap.put(eventRecord.getId(), eventRecord.getTradeNo());
        }
        Map<Integer, User> userMap = userService.getUserInfoListByIds(uids);
        EventRecordUserExample userExample = new EventRecordUserExample();
        userExample.createCriteria().andEventRecordIdIn(recordIds);
        List<EventRecordUser> eventRecordUsers = eventRecordUserMapper.selectByExample(userExample);
        Map<String, Integer> ticketCountMaps = commodityRemoteService.getTicketCountByOrders(tradeNos);

        UserResp userResp;
        for (EventRecordUser user : eventRecordUsers) {
            userResp = new UserResp();
            userResp.uid = user.getUid();
            userResp.name = user.getName();
            userResp.company = user.getCompany();
            userResp.position = user.getPosition();
            userResp.avatar = userMap.get(user.getUid()) == null ? null : userMap.get(user.getUid()).avatar;
            userResp.ticketCount = ticketCountMaps.get(relationMap.get(user.getEventRecordId()));
            userResp.signUpTime = DateUtils.getTimeFormatText(user.getCreateTime());
            userResps.add(userResp);
        }
        response.list = userResps;
        response.count = commodityRemoteService.getCountByCommodity(getEvent(req.eventId).getCommodityId()).allBuyCount;
        return response;
    }


    public Object getkeweiEventList(EventSearchPara eventSearchPara) {
        EventPara eventParaR = new EventPara();
        eventParaR.list = new ArrayList<>();

        Long nowTime = System.currentTimeMillis();
        EventExample eventExample = new EventExample();
        eventExample.setLimit(eventSearchPara.size);
        eventExample.setOffset(PagingUtil.getStart(eventSearchPara.page, eventSearchPara.size));
        eventExample.setOrderByClause("start_time desc");

        EventExample.Criteria criteria = eventExample.createCriteria();
        if (StringUtil.isNotEmpty(eventSearchPara.eventIds)) {
            criteria.andIdIn(eventSearchPara.eventIds);
        } else if (StringUtil.isNotEmpty(eventSearchPara.uids)) {
            criteria.andPublishUidIn(eventSearchPara.uids);
        } else {
            throw ExceptionUtil.createParamException("参数错误");
        }
        if (eventSearchPara.startTime != null) {
            criteria.andStartTimeGreaterThan(eventSearchPara.startTime);
        }
        if (eventSearchPara.endTime != null) {
            criteria.andEndTimeLessThan(eventSearchPara.endTime);
        }
        if (eventSearchPara.status != null) {
            if (eventSearchPara.status == 0) {
                criteria.andStartTimeLessThan(nowTime);
            }
            if (eventSearchPara.status == 1) {
                criteria.andStartTimeGreaterThanOrEqualTo(nowTime).andEndTimeLessThanOrEqualTo(nowTime);
            }
            if (eventSearchPara.status == 2) {
                criteria.andEndTimeGreaterThan(nowTime);
            }
        }
        criteria.andTemplateIdEqualTo(1);
        List<Event> eventList = eventMapper.selectByExample(eventExample);

        List<Integer> eventIds = new ArrayList<>();
        List<Integer> commodityIds = new ArrayList<>();
        if (StringUtil.isNotEmpty(eventList)) {
            for (Event event : eventList) {
                EventPara eventPara = converseParaSimple(event);
                eventParaR.list.add(eventPara);
                eventIds.add(event.getId());
                commodityIds.add(event.getCommodityId());
            }
        }

        if (StringUtil.isEmpty(eventIds)) {
            return eventParaR;
        }

        Map<Integer, List<Guest>> eventGuestMap = selectBatchEventGuest(eventIds);
        Map<Integer, List<Sponsor>> sponsorMap = selectBatchSponsor(eventIds);
        Map<Integer, EventStateCustom> eventStateCustomMap = selectBatchEventStateCustom(eventIds);
        Map<Integer, List<Ticket>> ticketsMap = commodityRemoteService.getTicketsMap(commodityIds);
        if (eventParaR.list != null && eventParaR.list.size() > 0) {
            for (EventPara eventPara : eventParaR.list) {
                List<Guest> eventGuest = eventGuestMap.get(eventPara.eventId);
                List<Sponsor> sponsor = sponsorMap.get(eventPara.eventId);
                EventStateCustom eventStateCustom = eventStateCustomMap.get(eventPara.eventId);

                eventPara.sponsors = sponsor;
                eventPara.guests = eventGuest;
                eventPara.customModule = stateCustomModuleService.conversePara(eventStateCustom);
                eventPara.recordNum = eventStateCustom.getJoinedCount();
                //从商品服务拿票数
                if (ticketsMap.containsKey(eventPara.commodityId)) {
                    eventPara.customModule.freeCount = ticketsMap.get(eventPara.commodityId).get(0).count;
                } else {
                    eventPara.customModule.freeCount = 0;
                }
            }
        }


        return eventParaR;
    }

    /**
     * 获取创业活动列表
     *
     * @param req
     * @return
     */

    public Object getEventList(AppReq req) {
        EventPara eventParaR = new EventPara();
        eventParaR.list = new ArrayList<>();

        UserEventSearchPara eventSearchReq = new UserEventSearchPara();
        eventSearchReq.page = PagingUtil.getStart(req.page, req.size);
        eventSearchReq.size = req.size;
        eventSearchReq.cityId = req.cityId;
        eventSearchReq.costType = req.costType;
        eventSearchReq.date = req.date;
        if (req.date.equals(ParamEnum.EventDateType.TYPE_TODAY.getValue())) {
            eventSearchReq.startTime = DateUtil.getTimesmorning();
            eventSearchReq.endTime = DateUtil.getTimesnight();
        } else if (req.date.equals(ParamEnum.EventDateType.TYPE_TOMORROW.getValue())) {
            eventSearchReq.startTime = DateUtil.getPassDayBeginTime(1);
            eventSearchReq.endTime = DateUtil.getPassDayBeginTime(1) + DateUtil.ONE_DAY_LONG;
        } else if (req.date.equals(ParamEnum.EventDateType.YPE_LAST_WEEK.getValue())) {
            eventSearchReq.startTime = DateUtil.getTimesmorning();
            eventSearchReq.endTime = DateUtil.getPassDayBeginTime(7);
        } else if (req.date.equals(ParamEnum.EventDateType.TYPE_LAST_WEEKEND.getValue())) {
            eventSearchReq.startTime = DateUtil.getTimesWeeknight().getTime() - 2 * DateUtil.ONE_DAY_LONG;
            eventSearchReq.endTime = DateUtil.getTimesWeeknight().getTime();
        } else {
            //date条件没有
            eventSearchReq.date = null;
        }
        eventSearchReq.nowTime = System.currentTimeMillis();
        List<Event> eventList = eventMapper.selectCommonEventList(eventSearchReq);
        List<Integer> commodityIds = new ArrayList<>();
        List<Integer> eventIds = new ArrayList<>();
        for (Event event : eventList) {
            commodityIds.add(event.getCommodityId());
            eventIds.add(event.getId());
        }

        List<EventCityRelation> hotEvents = eventCityRelationModule.getCityHotEvent(req.cityId, ParamEnum.HotOrRcmType
                .TYPE_HOT);
        List<EventCityRelation> recommendEvents = eventCityRelationModule.getHotOrRecommendEvents(ParamEnum
                .HotOrRcmType.TYPE_RECOMMENT, eventIds);


        if (eventIds == null || eventIds.size() == 0) {
            return eventParaR;
        }

        Map<Integer, List<Ticket>> ticketsMap = commodityRemoteService.getTicketsMap(commodityIds);
        Map<Integer, EventStateCustom> eventStateCustomMap = selectBatchEventStateCustom(eventIds);
        Map<Integer, List<Sponsor>> sponsorMap = selectBatchSponsor(eventIds);
        Map<Integer, ExtensionLink> extensionLinkMap = selectBatchExtensionLink(eventIds);
        Map<Integer, EventRecord> recordsMap = this.selectEventRecord(eventIds, req.uid);
        Map<Integer, Boolean> eventFavoriteMap = this.selectEventFavorite(eventIds, req.uid);
        if (eventList != null && eventList.size() > 0) {
            for (Event event : eventList) {
                EventPara eventPara = converseParaSimple(event);
                eventPara = getAppBasicEventPara(eventPara, recordsMap, event, eventFavoriteMap, null,
                        eventStateCustomMap,
                        extensionLinkMap, sponsorMap, hotEvents,
                        recommendEvents, ticketsMap, req.cityId, 1);

                eventParaR.list.add(eventPara);
            }
        }
        return eventParaR;
    }

    /**
     * 获取当前活动的票的信息
     */

    public Object getEventTicket(AppReq req) {
        if (NumberUtil.isInValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("eventId参数错误");
        }
        Event event = getEvent(req.eventId);
        List<Ticket> tickets = commodityRemoteService.converseToTicket(event.getCommodityId());
        TicketResp ticketResp = new TicketResp();
        ticketResp.list = tickets;
        return ticketResp;
    }

    /**
     * 获取活动下所有报名记录信息
     */

    public Object getEventRecordsPage(AppReq req) {
        if (NumberUtil.isInValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("eventId参数异常");
        }
        if (NumberUtil.isInValidId(req.page)) {
            throw ExceptionUtil.createParamException("page参数异常");
        }
        if (NumberUtil.isInValidId(req.size)) {
            throw ExceptionUtil.createParamException("size参数异常");
        }

        // TODO: 2018/2/12 会存在老活动数据 (2845 - 60000) 这个区间老创业活动 会转换为新活动id
        if (req.eventId >= 2845 && req.eventId < 60000) {
            EventExample example = new EventExample();
            example.createCriteria().andTemplateIdEqualTo(1).andOldEventIdEqualTo(req.eventId);
            List<Event> events = eventMapper.selectByExample(example);

            if (events != null && events.size() != 0) {
                Event event1 = events.get(0);
                req.eventId = event1.getId();
            }
        }

        EventQuery query = new EventQuery();
        query.pageCustom = req.page;
        query.sizeCustom = req.size;
        query.eventId = req.eventId;
        List<Integer> recordIds = eventRecordService.getValidRecordIds(query);
        EventRecordUserExample eventRecordUserExample = new EventRecordUserExample();
        List<EventRecordUser> eventRecordUsers = new ArrayList<>();
        if (!StringUtil.isEmpty(recordIds)) {
            eventRecordUserExample.createCriteria().andEventRecordIdIn(recordIds);
            eventRecordUsers = eventRecordUserMapper.selectByExample(eventRecordUserExample);
        }
        Integer joinedCount = getEventJoinedCount(req.eventId);
        RecordsUserResp resp = new RecordsUserResp();
        List<UserResp> userResps = new ArrayList<>();
        resp.count = joinedCount;
        if (!StringUtil.isEmpty(eventRecordUsers)) {
            eventRecordUsers.forEach(eventRecordUser -> {
                UserResp user = new UserResp();
                user.uid = eventRecordUser.getUid();
                user.position = eventRecordUser.getPosition();
                user.company = eventRecordUser.getCompany();
                user.mobile = eventRecordUser.getPhone();
                user.name = eventRecordUser.getName();
                userResps.add(user);
            });
        }
        resp.list = userResps;
        return resp;
    }

    /**
     * 根据ticket_no(recordId) 找到票券信息
     */

    public Object getRecordInfo(AppReq req) {

        EventRecord record = eventRecordService.getRecordByTradeNo(req.ticketNo,true);
        RecordInfoResp response = new RecordInfoResp();
        UserResp user = new UserResp();
        EventRecordUserExample userExample = new EventRecordUserExample();
        userExample.createCriteria().andEventRecordIdEqualTo(record.getId());
        List<EventRecordUser> eventRecordUsers = eventRecordUserMapper.selectByExample(userExample);
        if (StringUtil.isEmpty(eventRecordUsers)) {
            throw ExceptionUtil.createParamException("找不到报名的用户信息");
        }
        EventRecordUser eventRecordUser = eventRecordUsers.get(0);
        user.name = eventRecordUser.getName();
        user.mobile = eventRecordUser.getPhone();
        user.company = eventRecordUser.getCompany();
        user.position = eventRecordUser.getPosition();
        user.uid = eventRecordUser.getUid();
        response.user = user;
        Event event = getEvent(record.getEventId());
        EventPara eventPara = conversePara(event, 1);
        eventPara.recordStatus = record.getState();
        response.event = eventPara;
        List<String> tradeNoLists = new ArrayList<>();
        tradeNoLists.add(record.getTradeNo());
        response.tickets = commodityRemoteService.getTicketsForApp(tradeNoLists, event.getCommodityId());
        response.resultUrl = cloudEventConfig.getLink_signup_prefix() + "u?t=" + record.getTradeNo();
        response.recordState = record.getState().intValue();
        //签到状态，有一张签到就算签到
        Map<String, Long> signInMap = ticketOrderService.getSignInForApp(record.getId());
        response.signInState = signInMap.get("signInState").intValue();
        if (response.signInState.equals(SqlEnum.SignInType.TYPE_SIGN_IN_YES.getValue())) {
            response.signInTime = signInMap.get("signInTime");
        }
        return response;
    }



    public Object watermark(EventSearchPara req) {
        Event record = new Event();
        record.setId(req.eventId);
        record.setLogo(req.logo);
        record.setDetail(req.detail);
        record.setModifyTime(System.currentTimeMillis());
        eventMapper.updateByPrimaryKeySelective(record);
        return null;
    }


    public Object getLinkByOldId(AppReq req) {
        Integer oldEventId;
        if (req.key.matches("[1-9]\\d*")) {
            oldEventId = Integer.parseInt(req.key);
            return getLinkByOldEventId(oldEventId, SqlEnum.EventType.TYPE_EVENT_COMMON);
        }
        //如果直接传入的是加密的活动id
        String uniqueKey = Base64Utils.decodeToString(req.key);
        if (uniqueKey.matches("[1-9]\\d*")) {
            oldEventId = Integer.parseInt(uniqueKey);
            return getLinkByOldEventId(oldEventId, SqlEnum.EventType.TYPE_EVENT_COMMON);
        }
        if (!JSONUtil.validate(uniqueKey)) {
            return extensionLinkService.getLinkByUniqueKey(uniqueKey.trim().split("&")[0]);
        }
        OldEventReq oldEventReq;
        try {
            oldEventReq = JSONUtil.json2Obj(uniqueKey, OldEventReq.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw ExceptionUtil.createParamException("解析错误");
        }
        if (oldEventReq == null) {
            throw ExceptionUtil.createParamException("链接错误");
        }
        //如果传入了唯一key
        if (NumberUtil.isValidId(oldEventReq.activeid)) {
            return getLinkByOldEventId(oldEventReq.activeid, SqlEnum.EventType.TYPE_EVENT_COMMON);
        } else if (NumberUtil.isValidId(oldEventReq.aid)) {
            return getLinkByOldEventId(oldEventReq.aid, SqlEnum.EventType.TYPE_EVENT_COMMON);
        } else {
            throw ExceptionUtil.createParamException("链接错误");
        }
    }


    public Object getFinancingEventLinkByOldId(AppReq req) {
        Integer oldEventId;
        if (req.key.matches("[1-9]\\d*")) {
            oldEventId = Integer.parseInt(req.key);
            return getLinkByOldEventId(oldEventId, SqlEnum.EventType.TYPE_EVENT_ROADSHOW);
        }
        //如果直接传入的是加密的活动id
        String uniqueKey = Base64Utils.decodeToString(req.key);
        if (uniqueKey.matches("[1-9]\\d*")) {
            oldEventId = Integer.parseInt(uniqueKey);
            return getLinkByOldEventId(oldEventId, SqlEnum.EventType.TYPE_EVENT_ROADSHOW);
        }
        OldEventReq oldEventReq;
        if (!JSONUtil.validate(uniqueKey)) {
            return extensionLinkService.getLinkByUniqueKey(uniqueKey.trim().split("&")[0]);
        }
        try {
            oldEventReq = JSONUtil.json2Obj(uniqueKey, OldEventReq.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw ExceptionUtil.createParamException("解析错误");
        }
        if (oldEventReq == null) {
            throw ExceptionUtil.createParamException("链接错误");
        }
        //如果传入了唯一key
        if (NumberUtil.isValidId(oldEventReq.activeid)) {
            return getLinkByOldEventId(oldEventReq.activeid, SqlEnum.EventType.TYPE_EVENT_ROADSHOW);
        } else if (NumberUtil.isValidId(oldEventReq.aid)) {
            return getLinkByOldEventId(oldEventReq.aid, SqlEnum.EventType.TYPE_EVENT_ROADSHOW);
        } else if (NumberUtil.isValidId(oldEventReq.fid)) {
            return getLinkByOldEventId(oldEventReq.fid, SqlEnum.EventType.TYPE_EVENT_ROADSHOW);
        } else {
            throw ExceptionUtil.createParamException("链接错误");
        }
    }


    public Map<Integer, UserResp> getAdminMapByIds(List<Integer> eventIds,Map<String,List<Event>> eventTemp) {
        Map<Integer, UserResp> responseMap = new HashMap<>();
        if (StringUtil.isEmpty(eventIds)) {
            return responseMap;
        }
        final List<Event> events = Optional.ofNullable(eventTemp).orElseGet(HashMap::new).computeIfAbsent(
                JSONUtil.obj2Json(eventIds),
                        e -> {
                    EventExample eventExample = new EventExample();
                    eventExample.createCriteria().andIdIn(eventIds);
                    return eventMapper.selectByExample(eventExample);
                });
        Set<Integer> orgIds = new HashSet<>();
        for (Event event : events) {
            orgIds.add(event.getOrgId());
        }
        List orgList = new ArrayList<>(orgIds);
        Map<Integer, User> userMap = orgService.getAdminsUser(orgList);
        UserResp userResp;
        for (Event event : events) {
            userResp = new UserResp();
            User user = userMap.get(event.getOrgId());
            if (user == null) {
                continue;
            }
            userResp.name = user.name;
            userResp.mobile = user.phone;
            responseMap.put(event.getId(), userResp);
        }
        return responseMap;
    }


    public Object getUserTicketInfo(EventPara eventPara) {

        Event event = getEvent(eventPara.eventId);
        EventUserSignUpResp response = new EventUserSignUpResp();

        if (event == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        response.list = new ArrayList<>();
        List<Byte> validStates = iEventRecordProjectInfo.getStatesByLevel(ParamEnum.ProjectRecordListGetType
                .TYPE_AUDITED_AND_WAIT_VERIFY_FAILED);
        EventRecordExample example = new EventRecordExample();
        example.createCriteria().andStateIn(validStates).andUidEqualTo(eventPara.uid).andEventIdEqualTo(event.getId());
        List<EventRecord> eventRecords = eventRecordMapper.selectByExample(example);
        List<EventRecord> eventRecordIds = new ArrayList<>();
        //取有效的eventRecordIds
        for (EventRecord eventRecord : eventRecords) {
            //取通过，待审核，已拒绝的
            if (eventRecord.getTicketLock().equals(SqlEnum.TicketLockType.TYPE_NORMAL.getValue())) {
                eventRecordIds.add(eventRecord);
            }
            //取待付款的。未付款的ticket_lock一定是1,如果订单时间没过，就是待支付
            else if (eventRecord.getTradeEndTime().longValue() >= System.currentTimeMillis()) {
                eventRecordIds.add(eventRecord);
            }
        }
        if (StringUtil.isEmpty(eventRecordIds)) {
            return response;
        }
        if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())) {
            response.list = commodityRemoteService.getUserTicketInfo(eventRecordIds);
        } else {
            response.list = projectService.getLocalSimpleProjectsInfo(eventRecordIds);
        }
        return sortBySignupTime(response);
    }


    public Object getRecommendHome(EventQuery query) {
        List<Integer> cityList = new ArrayList<>();
        cityList.add(-2);
        cityList.add(0);
        if (NumberUtil.isValidId(query.cityId)) {
            cityList.add(query.cityId);
        }
        EventCityRelationExample example = new EventCityRelationExample();
        EventCityRelationExample.Criteria criteria = example.createCriteria().andCityIdIn(cityList)
                .andRecommendStateEqualTo(EnumRecommendStatus.RECOMMENDED.getValue());

        query.type = query.type == null ? 1 : query.type;
        if (query.type == 1) {
            criteria.andRecommendHomeStatusEqualTo(EnumRecommendStatus.RECOMMENDED.getValue());
        } else {
            criteria.andRecommendFinancingStateEqualTo(EnumRecommendStatus.RECOMMENDED.getValue());
            example.setOrderByClause(" recommend_financing_time desc");
        }
        List<EventCityRelation> eventCityRelations = eventCityRelationMapper.selectByExample(example);
        if (StringUtil.isEmpty(eventCityRelations)) {
            return new ArrayList<>();
        }

        Set<Integer> eventIds = new LinkedHashSet<>();
        eventCityRelations.forEach(eventCityRelation -> eventIds.add(eventCityRelation.getEventId()));

//        EventExample eventExample = new EventExample();
//        eventExample.createCriteria().andIdIn(new ArrayList<>(eventIds)).andStateEqualTo((byte) 2)
//                .andDeadlineTimeGreaterThanOrEqualTo(redisService.getCurrentTime());
//        List<Event> events = eventMapper.selectByExample(eventExample);
        List<Event> events = eventMapper.selectByIds(new ArrayList<>(eventIds), (byte) 2, redisService.getCurrentTime(), query.getSize());
        if (StringUtil.isEmpty(events)) {
            return new ArrayList<>();
        }
        List<EventResp> retResp = new ArrayList<>();

        List<Integer> luckIndex = null;
        if (events.size() > query.getSize()) {
            luckIndex = Arrays.asList(getRandomArrayByIndex(query.getSize(), events.size()));
        }

        Map<Integer, ExtensionLink> extensionLinkMap = selectBatchExtensionLink(new ArrayList<>(eventIds));

        for (int i = 0; i < events.size(); i++) {
            if (luckIndex == null) {
                retResp.add(conventEventResp2Event(events.get(i), extensionLinkMap));
            } else {
                if (luckIndex.contains(i)) {
                    retResp.add(conventEventResp2Event(events.get(i), extensionLinkMap));
                }
            }
        }
        PageData pageData=new PageData();
        pageData.setList(retResp);
        return pageData;
    }


    public PortalStatistics portalStatistics() {
        PortalStatistics portalStatistics = new PortalStatistics();

        // 活动
        EventExample eventExample = new EventExample();
        eventExample.createCriteria().andStateEqualTo((SqlEnum.EventStateType.TYPE_EVENT_PUBLISHED.getByteValue()))
                .andTemplateIdEqualTo(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue());
        portalStatistics.eventCount = (int)eventMapper.countByExample(eventExample);

        // 报名人数
        EventRecordExample eventRecordExample = new EventRecordExample();
        eventRecordExample.createCriteria().andStateEqualTo((byte)0).andTicketLockEqualTo(0);
        portalStatistics.applyEventCount =  (int)eventRecordMapper.countByExample(eventRecordExample);

        // 城市覆盖
        portalStatistics.cityCount = eventMapper.countCoverCity();

        // 活跃主办方
        portalStatistics.sponsorCount = eventSponsorMapper.countSponsors();
        return portalStatistics;
    }

    /**
     * 对象转换
     *
     * @param event 活动
     * @return
     */
    private EventResp conventEventResp2Event(Event event, Map<Integer, ExtensionLink> extensionLinkMap) {
        if (event == null) {
            return null;
        }
        EventResp eventBaseResp = new EventResp();
        eventBaseResp.id = event.getId();
        eventBaseResp.cityName = event.getCityName();
        eventBaseResp.logo = cloudEventConfig.getImage_prefix() + event.getLogo();
        eventBaseResp.startTime = event.getStartTime();
        eventBaseResp.type = event.getTemplateId();
        eventBaseResp.title = event.getTitle();

        ExtensionLink extensionLink = extensionLinkMap.get(event.getId());
        String uniqueKey = extensionLink.getUniqueKey();
        eventBaseResp.jumpUrl = cloudEventConfig.getLink_common_prefix() + Base64Utils.encode
                ((uniqueKey + "&" + event.getId()).getBytes());
        return eventBaseResp;
    }


    /**
     * 统计机构下报名过关注过的活动和人数
     *
     * @param orgId
     * @return
     */

    public List<UserMangeResp> getSignupEvent(Integer orgId) {
        return eventMapper.selectSignupEvent(orgId, System.currentTimeMillis());
    }


    private EventUserSignUpResp sortBySignupTime(EventUserSignUpResp response) {
        if (StringUtil.isEmpty(response.list)) {
            return response;
        }
        Collections.sort(response.list, Comparator.comparingLong(data -> data.signUpTime));
        Collections.reverse(response.list);
        return response;
    }


    private Object getLinkByOldEventId(Integer oldEventId, SqlEnum.EventType eventType) {
        if (NumberUtil.isValidId(oldEventId)) {
            EventExample eventExample = new EventExample();
            eventExample.createCriteria().andOldEventIdEqualTo(oldEventId).andTemplateIdEqualTo(eventType.getValue());
            List<Event> events = eventMapper.selectByExample(eventExample);
            if (StringUtil.isEmpty(events)) {
                throw ExceptionUtil.createParamException("活动不存在");
            }
            ExtensionLink extensionLink = extensionLinkService.getDefaultLinkByEventId(events.get(0).getId());
            ExtensionLinkResp resp = new ExtensionLinkResp();
            resp.commonUrl = extensionLink.getLinkUrlCommon();
            return resp;
        } else {
            throw ExceptionUtil.createParamException("活动不存在");
        }
    }

    /**
     * 批量查询主办方
     *
     * @param
     * @return
     */
    public Map<Integer, List<Sponsor>> selectBatchSponsor(List<Integer> eventIds) {
        Map<Integer, List<Sponsor>> sponsorsMap = new HashMap<>();
        List<EventSponsorManage> manages = sponsorsModuleService.selectSponsorsByEventIds(eventIds);
        sponsorsMap = getSponsorsMap(manages);
        return sponsorsMap;
    }


    /**
     * 批量查询嘉宾
     *
     * @param
     * @return
     */
    public Map<Integer, List<Guest>> selectBatchEventGuest(List<Integer> eventIds) {
        Map<Integer, List<Guest>> eventGuestMap = new HashMap<>();
        List<EventGuest> guestList = (List) guestModuleService.selectBatchEventGuest(eventIds);
        eventGuestMap = getGuestMap(guestList);
        return eventGuestMap;
    }


    /**
     * 批量linkCommonUrl
     *
     * @param
     * @return
     */
    public Map<Integer, String> selectBatchLinkCommonUrl(List<Integer> eventIds) {
        Map<Integer, String> linkCommonUrlMap = new HashMap<>();
        ExtensionLinkExample extensionExample = new ExtensionLinkExample();
        extensionExample.createCriteria().andLinkTypeEqualTo(SqlEnum.LINKTYPE.TYPE_DEFAULT.getValue().byteValue())
                .andEventIdIn(eventIds);
        List<ExtensionLink> extensionLinkList = extensionLinkMapper.selectByExample(extensionExample);
        if (!StringUtil.isEmpty(extensionLinkList)) {
            for (ExtensionLink extensionLink : extensionLinkList) {
                linkCommonUrlMap.put(extensionLink.getEventId(), extensionLink.getLinkUrlCommon());
            }
        }
        return linkCommonUrlMap;
    }


    /**
     * 批量查询EventStateCustom
     *
     * @param
     * @return
     */
    public Map<Integer, EventStateCustom> selectBatchEventStateCustom(List<Integer> eventIds) {
        Map<Integer, EventStateCustom> eventStateCustomMap = new HashMap<>();
        EventStateCustomExample eventStateCustomExample = new EventStateCustomExample();
        eventStateCustomExample.setDistinct(true);
        eventStateCustomExample.createCriteria().andEventIdIn(eventIds);
        List<EventStateCustom> eventStateCustoms = eventStateCustomMapper.selectByExample
                (eventStateCustomExample);
        for (EventStateCustom eventStateCustom : eventStateCustoms) {
            eventStateCustomMap.put(eventStateCustom.getEventId(), eventStateCustom);
        }
        return eventStateCustomMap;
    }


    /**
     * 批量查询EventStateProject
     *
     * @param
     * @return
     */
    public Map<Integer, EventStateProject> selectBatchEventStateProject(List<Integer> eventIds) {
        Map<Integer, EventStateProject> eventStateProjectMap = new HashMap<>();
        EventStateProjectExample eventStateProjectExample = new EventStateProjectExample();
        eventStateProjectExample.setDistinct(true);
        eventStateProjectExample.createCriteria().andEventIdIn(eventIds);
        List<EventStateProject> eventStateProjects = eventStateProjectMapper.selectByExample
                (eventStateProjectExample);
        for (EventStateProject eventStateProject : eventStateProjects) {
            eventStateProjectMap.put(eventStateProject.getEventId(), eventStateProject);
        }
        return eventStateProjectMap;
    }

    /**
     * 批量查询默认推广链接
     *
     * @param
     * @return
     */
    public Map<Integer, ExtensionLink> selectBatchExtensionLink(List<Integer> eventIds) {
        Map<Integer, ExtensionLink> extensionLinkMap = new HashMap<>();
        List<ExtensionLink> extensionLinks = extensionLinkService.getDefaultLinkByEventIds(eventIds);
        for (ExtensionLink extensionLink : extensionLinks) {
            extensionLinkMap.put(extensionLink.getEventId(), extensionLink);
        }
        return extensionLinkMap;
    }


    /**
     * 批量查询活动
     * ()
     *
     * @param
     * @return
     */
    public Map<Integer, Event> selectBatchEvent(List<Integer> eventIds) {
        Map<Integer, Event> eventMap = new HashMap<>();

        EventExample example = new EventExample();
        example.setDistinct(true);
        example.createCriteria().andIdIn(eventIds);
        List<Event> events = eventMapper.selectByExample(example);
        for (Event event : events) {
            eventMap.put(event.getId(), event);
        }

        return eventMap;
    }


    /**
     * 批量查询活动是否收藏
     *
     * @param
     * @return
     */
    public Map<Integer, Boolean> selectEventFavorite(List<Integer> eventIds, Integer uid) {
        Map<Integer, Boolean> favoriteMap = new HashMap<>();
        if(NumberUtil.isInValidId(uid)){
            return favoriteMap;
        }
        EventUidFavoriteRelationExample favoriteRelationExample = new EventUidFavoriteRelationExample();
        favoriteRelationExample.createCriteria().andEventIdIn(eventIds).andUidEqualTo(uid);
        List<EventUidFavoriteRelation> favoriteRelations = favoriteRelationMapper.selectByExample
                (favoriteRelationExample);

        if (favoriteRelations != null && favoriteRelations.size() > 0) {
            for (EventUidFavoriteRelation favoriteRelation : favoriteRelations) {
                if (!favoriteMap.containsKey(favoriteRelation.getEventId())) {
                    favoriteMap.put(favoriteRelation.getEventId(), true);
                }
            }
        }

        return favoriteMap;
    }


    /**
     * 批量查询报名状态
     *
     * @param
     * @return
     */
    public Map<Integer, EventRecord> selectEventRecord(List<Integer> eventIds, Integer uid) {
        Map<Integer, EventRecord> eventRecordMap = new HashMap<>();
        if(NumberUtil.isInValidId(uid)){
            return eventRecordMap;
        }
        EventQuery query = new EventQuery();
        query.uid = uid;
        query.eventIds = eventIds;
        List<EventRecord> eventRecords = eventRecordService.getValidRecords(query);
        for (EventRecord eventRecord : eventRecords) {
            eventRecordMap.put(eventRecord.getEventId(), eventRecord);
        }
        return eventRecordMap;
    }

    public List<Integer> getEventIdsByOrgId(Integer orgId) {
        List<Integer> eventIds = new ArrayList<>();
        if (NumberUtil.isInValidId(orgId)) {
            return eventIds;
        }
        EventExample example = new EventExample();
        example.createCriteria().andOrgIdEqualTo(orgId);
        List<Event> events = eventMapper.selectByExample(example);
        for (Event event : events) {
            eventIds.add(event.getId());
        }
        return eventIds;
    }


    public AppResp recommend(AppReq req) {

        Event event = getEvent(req.eventId);
        if (event == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        EventCityRelationExample example = new EventCityRelationExample();
        example.createCriteria().andEventIdEqualTo(event.getId()).andCityIdEqualTo(event.getCityId());
        List<EventCityRelation> cityRelations = eventCityRelationMapper.selectByExample(example);
        EventCityRelation eventCityRelation = new EventCityRelation();
        //没有推荐
        if (StringUtil.isEmpty(cityRelations)) {
            example.clear();
            example.createCriteria().andEventIdEqualTo(event.getId());
            eventCityRelationMapper.deleteByExample(example);
            eventCityRelation.setEventId(req.eventId);
            eventCityRelation.setRecommendState(SqlEnum.RecommendType.TYPE_RECOMMEND.getValue());
            eventCityRelation.setRecommendTime(DateUtil.getNowDate().getTime());
            eventCityRelation.setCityId(event.getCityId());
            eventCityRelationMapper.insertSelective(eventCityRelation);
        } else {
            eventCityRelation.setRecommendState(SqlEnum.RecommendType.TYPE_RECOMMEND.getValue());
            eventCityRelation.setRecommendTime(DateUtil.getNowDate().getTime());
            eventCityRelationMapper.updateByExampleSelective(eventCityRelation, example);
        }
        AppResp resp = new AppResp();
        return resp;
    }

    /**
     * 置顶数组中去不重复的值
     *
     * @param num   要取的数量
     * @param scope 最大下标
     * @return
     */
    private static Integer[] getRandomArrayByIndex(int num, int scope) {
        //1.获取scope范围内的所有数值，并存到数组中
        int[] randomArray = new int[scope];
        for (int i = 0; i < randomArray.length; i++) {
            randomArray[i] = i;
        }

        //2.从数组random中取数据，取过后的数改为-1
        Integer[] numArray = new Integer[num];//存储num个随机数
        int i = 0;
        while (i < numArray.length) {
            int index = (int) (Math.random() * scope);
            if (randomArray[index] != -1) {
                numArray[i] = randomArray[index];
                randomArray[index] = -1;
                i++;
            }
        }

        return numArray;
    }

    public Map<Integer, Event> getEventsMap(List<Integer> eventIds) {
        Map<Integer, Event> returnMap = new HashMap<>();
        EventExample eventExample = new EventExample();
        eventExample.createCriteria().andIdIn(eventIds);
        List<Event> events = eventMapper.selectByExample(eventExample);
        for (Event event : events) {
            returnMap.put(event.getId(), event);
        }
        return returnMap;
    }
}
