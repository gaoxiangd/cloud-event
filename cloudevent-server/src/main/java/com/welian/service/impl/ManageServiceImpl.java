package com.welian.service.impl;

import com.welian.beans.account.User;
import com.welian.beans.cloudevent.CityReq;
import com.welian.beans.cloudevent.PublisherResp;
import com.welian.beans.cloudevent.Sponsor;
import com.welian.beans.cloudevent.Ticket;
import com.welian.beans.cloudevent.event.EventInfo;
import com.welian.beans.cloudevent.event.EventPara;
import com.welian.beans.cloudevent.event.EventResp;
import com.welian.beans.cloudevent.event.SaveEventReq;
import com.welian.beans.cloudevent.event.SaveEventResp;
import com.welian.beans.cloudevent.manage.EventManageListResp;
import com.welian.beans.cloudevent.manage.EventManageReq;
import com.welian.beans.cloudevent.manage.EventManageResp;
import com.welian.beans.cloudevent.manage.PublishOrgResp;
import com.welian.beans.cloudevent.manage.RmdOrHotReq;
import com.welian.beans.cloudevent.query.EventQuery;
import com.welian.beans.cloudevent.sys.SysUserRelationReq;
import com.welian.commodity.base.PageResult;
import com.welian.config.AppMsg;
import com.welian.config.CloudEventConfig;
import com.welian.config.Constant;
import com.welian.config.SmsMsg;
import com.welian.enums.cloudevent.EnumRecommendStatus;
import com.welian.enums.cloudevent.ParamEnum;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.enums.notify.EnumIMType;
import com.welian.mapper.EventCityRelationMapper;
import com.welian.mapper.EventListMapper;
import com.welian.mapper.EventMapper;
import com.welian.mapper.EventSmsMapper;
import com.welian.mapper.EventSponsorManageMapper;
import com.welian.mapper.EventStateCustomMapper;
import com.welian.mapper.EventStateProjectMapper;
import com.welian.mapper.EventSysMessageMapper;
import com.welian.mapper.ExtensionLinkMapper;
import com.welian.mapper.OrgInfoMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventCityRelation;
import com.welian.pojo.EventCityRelationExample;
import com.welian.pojo.EventExample;
import com.welian.pojo.EventManage;
import com.welian.pojo.EventSms;
import com.welian.pojo.EventSponsorManage;
import com.welian.pojo.EventStateCustom;
import com.welian.pojo.EventStateCustomExample;
import com.welian.pojo.EventStateProject;
import com.welian.pojo.EventStateProjectExample;
import com.welian.pojo.EventSysMessage;
import com.welian.pojo.ExtensionLink;
import com.welian.pojo.ExtensionLinkExample;
import com.welian.pojo.OrgInfo;
import com.welian.pojo.OrgInfoExample;
import com.welian.service.CommodityRemoteService;
import com.welian.service.EventCityRelationModule;
import com.welian.service.EventSolrService;
import com.welian.service.SolrService;
import com.welian.service.UserService;
import com.welian.utils.DateUtil;
import com.welian.utils.ExceptionUtil;
import com.welian.utils.PagingUtil;
import org.sean.framework.annotation.Synchronized;
import org.sean.framework.service.RedisService;
import org.sean.framework.util.Logger;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * created by GaoXiang on 2017/10/10
 */
@Service("manageService")
public class ManageServiceImpl {

    private static final Logger logger = Logger.newInstance(ManageServiceImpl.class);
    @Autowired
    private CityRemoteServiceImpl cityRemoteService;
    @Autowired
    private UserService userService;
    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private EventRecordServiceImpl eventRecordService;
    @Autowired
    private SmsRemoteServiceImpl smsRemoteService;

    @Autowired
    private EventListMapper eventListMapper;
    @Autowired
    private EventCityRelationMapper eventCityRelationMapper;
    @Autowired
    private EventSysMessageMapper eventSysMessageMapper;
    @Autowired
    private ExtensionLinkMapper extensionLinkMapper;
    @Autowired
    private EventSponsorManageMapper eventSponsorManageMapper;
    @Autowired
    private OrgInfoMapper orgInfoMapper;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private EventSmsMapper eventSmsMapper;
    @Autowired
    private SolrService solrService;
    @Autowired
    private SysMsgServiceImpl sysMsgService;
    @Autowired
    private CommodityRemoteService commodityRemoteService;
    @Autowired
    private EventSolrService eventSolrService;
    @Autowired
    private EventStateProjectMapper eventStateProjectMapper;
    @Autowired
    private EventStateCustomMapper eventStateCustomMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private EventCityRelationModule eventCityRelationModule;
    @Autowired
    private CloudEventConfig cloudEventConfig;


    public EventManageListResp list(EventManageReq req) {

        Integer limit = req.size;
        Integer offset = PagingUtil.getStart(req.page, req.size);
        List<EventManage> eventList;
        String startTime = getStartTime(req.startTime);
        String endTime = getEndTime(req.endTime);
        eventList = eventListMapper.searchForPage(req.beforeDeadLineTime, req.eventId, req.eventType, req.state, req
                .recommend, req.hot, req.timeType, startTime, endTime, req.orderType, req.cityId, req.eventName, req
                .lineType, offset, limit, redisService.getCurrentTime(), req.recommendHomeStatus, req.recommendFinnacingStatus);

        //把拿到的值组装
        EventManageListResp response = new EventManageListResp();
        response.totalCount = eventListMapper.countValidEvent(req.beforeDeadLineTime, req.eventId, req.eventType, req
                .state, req.recommend, req.hot, req.timeType, startTime, endTime, req.orderType, req.cityId, req
                .eventName, req.lineType, redisService.getCurrentTime(), req.recommendHomeStatus, req.recommendFinnacingStatus);
        //只有筛选条件为未推荐才不拿推荐活动总数
        Integer recommendState = SqlEnum.RecommendType.TYPE_RECOMMEND.getValue();
        if (req.recommend != null && req.recommend.equals(SqlEnum.RecommendType.TYPE_NOT_RECOMMEND.getValue())) {
            recommendState = SqlEnum.RecommendType.TYPE_NOT_RECOMMEND.getValue();
        }
        response.recommendCount = eventListMapper.countValidEvent(req.beforeDeadLineTime, req.eventId, req.eventType,
                req.state, recommendState, req.hot, req.timeType, startTime, endTime, req.orderType, req.cityId, req
                        .eventName, req.lineType, redisService.getCurrentTime(), req.recommendHomeStatus, req.recommendFinnacingStatus);
        if (!StringUtil.isEmpty(eventList)) {
            List<Integer> eventIds = new ArrayList<>();
            List<Integer> commodityIds = new ArrayList<>();
            for (EventManage eventManage : eventList) {
                eventIds.add(eventManage.eventId);
                if (eventManage.commodityId != null && !eventManage.commodityId.equals(0)) {
                    commodityIds.add(eventManage.commodityId);
                }
            }
            EventExample eventExample = new EventExample();
            eventExample.createCriteria().andIdIn(eventIds);
            List<Event> events = eventMapper.selectByExample(eventExample);
            Map<Integer, Event> eventMap = new HashMap<>();
            for (Event event : events) {
                eventMap.put(event.getId(), event);
            }
            //根据发布者的uid获取profile，放在map中
            List<Integer> uids = new ArrayList<>();
            Map<Integer, Integer> mapIntger = new HashMap<>();
            for (EventManage event : eventList) {
                mapIntger.put(event.getPublishUid(), event.getPublishUid());
            }
            for (Map.Entry<Integer, Integer> entry : mapIntger.entrySet()) {
                uids.add(entry.getValue());
            }
            Map<Integer, User> publishMap = userService.getUserInfoListByIds(uids);
            //获取发布方,放在map中
            List<EventSponsorManage> sponsorList = eventSponsorManageMapper.selectSponsorsByEventIds(eventIds);
            Map<Integer, List<EventSponsorManage>> sponsorMap = getSponsorsManageMap(sponsorList);
            //拿到recommendMap
            EventCityRelationExample cityExample = new EventCityRelationExample();
            cityExample.createCriteria().andEventIdIn(eventIds).andRecommendStateEqualTo(SqlEnum.RecommendType
                    .TYPE_RECOMMEND.getValue());
            List<EventCityRelation> recommendCitys = eventCityRelationMapper.selectByExample(cityExample);
            Map<Integer, List<CityReq>> recommendCitysMap = getCityMap(recommendCitys);
            //拿到hotMap
            cityExample.clear();
            cityExample.createCriteria().andEventIdIn(eventIds).andHotStateEqualTo(SqlEnum.HotType.TYPE_HOT.getValue());
            List<EventCityRelation> hotCitys = eventCityRelationMapper.selectByExample(cityExample);

            Map<Integer, List<CityReq>> hotCitysMap = getCityMap(hotCitys);
            Map<Integer, String> orgMap = getOrgMap(eventList);
            Map<Integer, Integer> signUpTotalMap = getTicketMap(commodityIds);
            Map<Integer, List<Ticket>> ticketsMap = getTicketsMap(commodityIds);
            Map<Integer, EventCityRelation> onLineHotAndRcmMap = getOnLineHotAndRcmMap(eventList);
            Map<Integer, String> linkCommonUrlMap = getLinkCommonUrlMap(eventList);
            Map<Integer, EventInfo> costAndcountMap = getCostAndCountMap(eventList);
            List<EventManageResp> list = new ArrayList<>();
            long startTimes = System.currentTimeMillis();
            for (EventManage eventManage : eventList) {
                EventManageResp eventManageResp = conversePara(costAndcountMap, eventManage, eventMap, publishMap,
                        sponsorMap,
                        recommendCitysMap,
                        hotCitysMap, orgMap, signUpTotalMap, onLineHotAndRcmMap, linkCommonUrlMap, ticketsMap);
                list.add(eventManageResp);
            }
            float excTime = (float) (System.currentTimeMillis() - startTimes) / 1000;
            logger.info("方法执行时间：" + excTime + "s");
            response.list = list;
        }
        return response;
    }

    private Map<Integer, EventInfo> getCostAndCountMap(List<EventManage> eventList) {
        Map<Integer, EventInfo> costAndCountMap = new HashMap<>();
        List<Integer> eventIds = new ArrayList<>();
        for (EventManage eventManage : eventList) {
            eventIds.add(eventManage.getEventId());
        }

        if (!StringUtil.isEmpty(eventIds)) {
            EventStateProjectExample projectExample = new EventStateProjectExample();
            projectExample.createCriteria().andEventIdIn(eventIds);
            List<EventStateProject> eventStateProjects = eventStateProjectMapper.selectByExample(projectExample);
            EventInfo eventInfo;
            for (EventStateProject eventStateProject : eventStateProjects) {
                eventInfo = new EventInfo();
                eventInfo.costType = eventStateProject.getCostType();
                eventInfo.joinedCount = eventStateProject.getJoinedCount();
                costAndCountMap.put(eventStateProject.getEventId(), eventInfo);
            }
            EventStateCustomExample customExample = new EventStateCustomExample();
            customExample.createCriteria().andEventIdIn(eventIds);
            List<EventStateCustom> eventStateCustoms = eventStateCustomMapper.selectByExample(customExample);
            for (EventStateCustom eventStateCustom : eventStateCustoms) {
                eventInfo = new EventInfo();
                eventInfo.costType = eventStateCustom.getCostType();
                eventInfo.joinedCount = eventStateCustom.getJoinedCount();
                costAndCountMap.put(eventStateCustom.getEventId(), eventInfo);
            }
        }
        return costAndCountMap;
    }

    private Map<Integer, List<Ticket>> getTicketsMap(List<Integer> commodityIds) {

        return commodityRemoteService.getTicketsMap(commodityIds);
    }


    public Object saveEvent(SaveEventReq req, Integer uid) {
        if (!NumberUtil.isValidId(req.adminUid)) {
            throw ExceptionUtil.createParamException("adminUid参数错误");
        }
        //兼容运营后台不传票务信息和isCharge字段的情况
        Integer sourceFrom = ParamEnum.EventRequestSource.SOURCE_FROM_MANAGE.getValue();
        Integer eventId = req.event.eventId;
        SaveEventResp response = eventService.saveEvent(req, uid, sourceFrom);

        //修改活动
        if (NumberUtil.isValidId(eventId)) {
            //TODO: 2018/1/16 更新solr 
            eventSolrService.updataEventToSolr(response.eventReq);
        } else {
            //TODO: 2018/1/16 同步solr
            eventSolrService.synchronousEventToSolr(response.eventReq);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("YY-mm-dd HH-mm-ss");
        logger.info("adminUid:" + req.adminUid + ",于 " + sdf.format(new Date()) + " 执行了保存eventId为" + req.event
                .eventId + "的操作");
        return response;
    }


    public Object deleteEvent(EventManageReq req) {

        if (!NumberUtil.isValidId(req.adminUid)) {
            throw ExceptionUtil.createParamException("adminUid参数错误");
        }
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("eventId参数错误");
        }
        if (eventRecordService.checkHasRecord(req.eventId) || eventRecordService.checkHasToPayOrder(req.eventId)) {
            throw ExceptionUtil.createWarnException("存在报名或者未付款记录，不能删除！");
        }
        Event event = eventMapper.selectByPrimaryKey(req.eventId);
        event.setState(SqlEnum.EventStateType.TYPE_EVENT_DELETED.getByteValue());
        event.setModifyTime(System.currentTimeMillis());
        eventService.updateEvent(event);

        SimpleDateFormat sdf = new SimpleDateFormat("YY-mm-dd HH-mm-ss");
        logger.info("adminUid:" + req.adminUid + ",于 " + sdf.format(new Date()) + " 执行了删除eventId为" + req.eventId +
                "的操作");

        return null;
    }


    public EventResp getEventDetail(EventPara req) {

        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("eventId参数错误");
        }
        EventQuery query = new EventQuery();
        query.eventId = req.eventId;
        Integer recordNum = eventRecordService.getValidRecords(query).size();
        req.level = 0;
        EventResp response = eventService.getEventDetail(req);
        response.eventPara.recordNum = recordNum;
        Event event = eventService.getEvent(req.eventId);
        if (event != null) {
            User profile = userService.getUserInfoById(event.getPublishUid());
            if (profile != null) {
                PublisherResp publisher = new PublisherResp();
                publisher.id = event.getPublishUid();
                publisher.name = profile.name;
                response.eventPara.publisher = publisher;
            }
        }
        return response;
    }


    @Transactional

    @Synchronized(argType = Synchronized.ArgType.ARG_BEAN, keyName = "eventId")//所动活动id
    public Object recommendOrHot(RmdOrHotReq req) {
//        EventSolrBean solrBean = new EventSolrBean();
        if (!NumberUtil.isValidId(req.adminUid)) {
            throw ExceptionUtil.createParamException("adminUid参数错误");
        }
        if (!NumberUtil.isValidId(req.type)) {
            throw ExceptionUtil.createParamException("type参数错误");
        }
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("eventId参数错误");
        }

        if (new Integer(3).equals(req.type) || new Integer(4).equals(req.type)) {
            return recommendHome(req);
        }


        // 推荐、热门推荐
        Event event = eventService.getEvent(req.eventId);
        if (event == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }

        if (event.getLineType() != null && event.getLineType() == SqlEnum.EventLineType.TYPE_ONLINE.getByteValue()) {
            onlineRcmOrHot(req);
        } else if (event.getLineType() != null && event.getLineType() == SqlEnum.EventLineType.TYPE_OFFLINE
                .getByteValue()) {
            offlineRcmOrHot(req);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("YY-mm-dd HH-mm-ss");
        logger.info("adminUid:" + req.adminUid + ",于 " + sdf.format(new Date()) + " 执行了推荐或热门相关操作");
        return null;
    }


    /**
     * ================================内部方法分割线====================================
     */

    private EventManageResp conversePara(Map<Integer, EventInfo> costAndcountMap, EventManage eventManage,
                                         Map<Integer, Event> eventMap, Map<Integer, User> publishMap,
                                         Map<Integer, List<EventSponsorManage>> sponsorMap, Map<Integer, List<CityReq>>
                                                 recommendCitysMap,
                                         Map<Integer, List<CityReq>> hotCitysMap, Map<Integer, String> orgMap,
                                         Map<Integer, Integer> signUpTotalMap,
                                         Map<Integer, EventCityRelation> onLineHotAndRcmMap, Map<Integer, String>
                                                 linkCommonUrlMap, Map<Integer, List<Ticket>> ticketsMap) {

        EventManageResp eventManageResp = new EventManageResp();
        eventManageResp.createTime = eventManage.createTime;
        eventManageResp.id = eventManage.eventId;
        eventManageResp.title = eventManage.title;
        eventManageResp.lineType = eventManage.lineType;
        eventManageResp.privateType = eventManage.openExtension;
        eventManageResp.costType = costAndcountMap.get(eventManage.eventId).costType.intValue();
        eventManageResp.eventType = eventManage.templateId.equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue()) ?
                ParamEnum.EventType.TYPE_COMMON.getValue() : ParamEnum.EventType.TYPE_ROAD.getValue();
        eventManageResp.couponStatus = eventManage.couponStatus;
        PublishOrgResp org = new PublishOrgResp();
        org.id = eventManage.getOrgId();
        org.name = orgMap.get(eventManage.getOrgId());
        eventManageResp.publishOrg = org;
        //票券总数信息从商品服务获取，由CommodityId这个字段决定
        Integer upTotal = signUpTotalMap.get(eventManage.getCommodityId());
        if (upTotal != null) {
            eventManageResp.signUpLimitTotal = upTotal.equals(Constant.NO_LIMIT_NUMBER) ? "不限" : upTotal.toString();
        } else {
            eventManageResp.signUpLimitTotal = Constant.NO_LIMIT_NUMBER + "";
        }
        eventManageResp.startTime = eventManage.startTime;
        eventManageResp.signUpTotal = costAndcountMap.get(eventManage.eventId).joinedCount;
        eventManageResp.state = eventManage.state;
        eventManageResp.ticketList = ticketsMap.get(eventManage.commodityId);
        if (eventMap.get(eventManage.eventId).getEndTime() < DateUtil.getNowDate().getTime()) {
            eventManageResp.state = ParamEnum.EventState.STATE_FINISHED.getValue();
        }
        eventManageResp.linkCommonUrl = linkCommonUrlMap.get(eventManage.getEventId());
        //设置onlineHot 0:不热门 1:热门 线上活动热门位状态(当且仅当活动为线上活动时生效)
        if (eventManage.lineType == SqlEnum.EventLineType.TYPE_ONLINE.getValue()) {
            if (onLineHotAndRcmMap.containsKey(eventManage.getEventId())) {
                if (onLineHotAndRcmMap.get(eventManage.getEventId()).getHotState() == SqlEnum.HotType
                        .TYPE_HOT.getValue()) {
                    eventManageResp.onlineHot = SqlEnum.HotType.TYPE_HOT.getValue();
                } else {
                    eventManageResp.onlineHot = SqlEnum.HotType.TYPE_NOT_HOT.getValue();
                }
            }
        }
        //设置onlineRecommand 0:不推荐 1:推荐 线上活动推荐位状态(当且仅当活动为线上活动时生效)
        if (eventManage.lineType == SqlEnum.EventLineType.TYPE_ONLINE.getValue()) {
            if (onLineHotAndRcmMap.containsKey(eventManage.getEventId())) {
                if (onLineHotAndRcmMap.get(eventManage.getEventId()).getRecommendState() == SqlEnum
                        .RecommendType.TYPE_RECOMMEND.getValue()) {
                    eventManageResp.onlineRecommend = SqlEnum.RecommendType.TYPE_RECOMMEND.getValue();
                } else {
                    eventManageResp.onlineRecommend = SqlEnum.RecommendType.TYPE_NOT_RECOMMEND.getValue();
                }
            }
        }
        CityReq cityResp = new CityReq();
        cityResp.name = eventManage.cityName;
        cityResp.id = eventManage.cityId;
        eventManageResp.city = cityResp;
        if (publishMap.get(eventManage.publishUid) != null) {
            PublisherResp publisher = new PublisherResp();
            publisher.id = eventManage.publishUid;
            publisher.name = publishMap.get(eventManage.publishUid).name;
            eventManageResp.publishUser = publisher;
        }
        if (!StringUtil.isEmpty(sponsorMap.get(eventManage.getEventId()))) {
            List<Sponsor> SponsorLists = new ArrayList<>();
            for (EventSponsorManage eventSponsorManage : sponsorMap.get(eventManage.getEventId())) {
                SponsorLists.add(eventSponsorManage2Sponsor(eventSponsorManage));
            }
            eventManageResp.sponsors = SponsorLists;
        }
        if (recommendCitysMap.get(eventManage.eventId) != null) {
            List<CityReq> recommendCity = recommendCitysMap.get(eventManage.eventId);
            eventManageResp.recommendCities = recommendCity;
            for (CityReq cityReq : recommendCity) {
                if (EnumRecommendStatus.RECOMMENDED.getValue().equals(cityReq.recommendHomeStatus)
                        && !EnumRecommendStatus.RECOMMENDED.getValue().equals(eventManageResp.recommendHomeStatus)) {
                    eventManageResp.recommendHomeStatus = EnumRecommendStatus.RECOMMENDED.getValue();
                }
                if (EnumRecommendStatus.RECOMMENDED.getValue().equals(cityReq.recommendFinancingStatus)
                        && !EnumRecommendStatus.RECOMMENDED.getValue().equals(eventManageResp.recommendFinnacingStatus)) {
                    eventManageResp.recommendFinnacingStatus = EnumRecommendStatus.RECOMMENDED.getValue();
                }
            }
        }

        if (hotCitysMap.get(eventManage.eventId) != null) {
            List<CityReq> hotCity = hotCitysMap.get(eventManage.eventId);
            eventManageResp.hotCities = hotCity;
        }
        return eventManageResp;
    }

    /**
     * EventSponsorManage->Sponsor
     *
     * @param eventSponsorManage
     * @return
     */
    public Sponsor eventSponsorManage2Sponsor(EventSponsorManage eventSponsorManage) {
        Sponsor sponsor = new Sponsor();
        sponsor.id = eventSponsorManage.getId();
        sponsor.name = eventSponsorManage.getSponsorName();
        return sponsor;
    }

    /**
     * 线上活动的热门或者推荐
     */
    public void onlineRcmOrHot(RmdOrHotReq req) {
        Event event = eventService.getEvent(req.eventId);
        //推荐
        if (req.type == ParamEnum.HotOrRcmType.TYPE_RECOMMENT.getValue()) {
            EventCityRelationExample example = new EventCityRelationExample();
            example.createCriteria().andEventIdEqualTo(req.eventId);
            //取消推荐
            EventCityRelation eventCityRelation = new EventCityRelation();
            if (req.action != null && req.action.equals(0)) {
                checkHotEvent(req.eventId);
                checkHomeEvent(req.eventId);
                checkFinancingEvent(req.eventId);
                eventCityRelation.setRecommendState(SqlEnum.RecommendType.TYPE_NOT_RECOMMEND.getValue());
                eventCityRelationMapper.updateByExampleSelective(eventCityRelation, example);

                solrService.updateSolrInfoById(event.getId().toString(), "recommend_status", 0);
                solrService.updateSolrInfoById(event.getId().toString(), "recommend_time", 0);
                //推荐或者热门
            } else if (req.action != null && req.action.equals(1)) {
                checkState(event.getState());
                //已经推荐，由于网络原因可能多次进入线上推荐流程，为了不发多条信息，需要检测是否推荐
                checkHasRecommend(event.getId());
                eventCityRelation.setRecommendState(SqlEnum.RecommendType.TYPE_RECOMMEND.getValue());
                eventCityRelation.setRecommendTime(DateUtil.getNowDate().getTime());
                eventCityRelationMapper.updateByExampleSelective(eventCityRelation, example);
                saveSystemSms(req);
                saveAppSms(req);
                sendsms(req);

                solrService.updateSolrInfoById(event.getId().toString(), "recommend_status", 1);
                solrService.updateSolrInfoById(event.getId().toString(), "recommend_time", DateUtil.getNowDate
                        ().getTime());
            }

        } else if (req.type == ParamEnum.HotOrRcmType.TYPE_HOT.getValue()) {
            //热门
            EventCityRelationExample example = new EventCityRelationExample();
            example.createCriteria().andEventIdEqualTo(req.eventId);
            //取消热门
            EventCityRelation eventCityRelation = new EventCityRelation();
            if (req.action != null && req.action.equals(0)) {
                eventCityRelation.setHotState(SqlEnum.HotType.TYPE_NOT_HOT.getValue());
                solrService.updateSolrInfoById(event.getId().toString(), "sort_type", ParamEnum.EventSortType
                        .DEGREE_NORMAL.getValue());
                //推荐或者热门
            } else if (req.action != null && req.action.equals(1)) {
                checkState(event.getState());
                checkHasHot(event.getId());
                checkRecommendEvent(req.eventId);
                eventCityRelation.setHotState(SqlEnum.HotType.TYPE_HOT.getValue());
                eventCityRelation.setHotTime(DateUtil.getNowDate().getTime());
                saveSystemSms(req);
                saveAppSms(req);
                sendsms(req);
                solrService.updateSolrInfoById(event.getId().toString(), "sort_type", ParamEnum.EventSortType
                        .DEGREE_HOT.getValue());
            }
            eventCityRelationMapper.updateByExampleSelective(eventCityRelation, example);
        }
    }


    public Object more(EventManageReq req) {

        Integer limit = req.size;
        Integer offset = PagingUtil.getStart(req.page, req.size);
        List<EventManage> eventList;
        String startTime = getStartTime(req.startTime);
        String endTime = getEndTime(req.endTime);
        eventList = eventListMapper.searchForPage(req.beforeDeadLineTime, req.eventId, req.eventType, req.state, req
                .recommend, req.hot, req.timeType, startTime, endTime, req.orderType, req.cityId, req.eventName, req
                .lineType, offset, limit, redisService.getCurrentTime(), req.recommendHomeStatus, req.recommendFinnacingStatus);
        if (StringUtil.isEmpty(eventList)) {
            return new PageResult<>(req.page, req.size, 0, new ArrayList<>());
        }
        List<Integer> eventIds = new ArrayList<>();
        eventList.forEach(resp -> eventIds.add(resp.eventId));

        Map<Integer, Integer> numberStatesMap = new HashMap<>();
        Map<Integer, Integer> recordNumsMap = new HashMap<>();
        EventStateCustomExample customExample = new EventStateCustomExample();
        customExample.createCriteria().andEventIdIn(eventIds);
        List<EventStateCustom> customs = eventStateCustomMapper.selectByExample(customExample);
        for (EventStateCustom custom : customs) {
            recordNumsMap.put(custom.getEventId(), custom.getJoinedCount());
            numberStatesMap.put(custom.getEventId(), custom.getCustomNumberState().intValue());
        }
        EventStateProjectExample projectExample = new EventStateProjectExample();
        projectExample.createCriteria().andEventIdIn(eventIds);
        List<EventStateProject> projects = eventStateProjectMapper.selectByExample(projectExample);
        for (EventStateProject project : projects) {
            recordNumsMap.put(project.getEventId(), project.getJoinedCount());
            numberStatesMap.put(project.getEventId(), project.getProjectNumberState().intValue());
        }
        List<EventCityRelation> hotEvents = eventCityRelationModule.getHotOrRecommendEvents(ParamEnum.HotOrRcmType
                .TYPE_HOT, eventIds);
        List<EventCityRelation> recommendEvents = eventCityRelationModule.getHotOrRecommendEvents(ParamEnum
                .HotOrRcmType.TYPE_RECOMMENT, eventIds);
        List<EventManageResp> responseList = new ArrayList<>();
        Map<Integer, String> linkCommonUrlMap = getLinkCommonUrlMap(eventList);
        List<EventSponsorManage> sponsorList = eventSponsorManageMapper.selectSponsorsByEventIds(eventIds);
        Map<Integer, List<Sponsor>> sponsorMap = getSponsorsMap(sponsorList);
        Map<Integer, Event> eventMap = eventService.getEventsMap(eventIds);
        eventList.forEach(resp -> {
                    EventManageResp manageResp = eventManage2ManageResp(resp);
                    manageResp.eventId=resp.eventId;
                    manageResp.sponsors=sponsorMap.get(resp.eventId);
                    manageResp.logo=cloudEventConfig.getImage_prefix() +eventMap.get(resp.eventId).getLogo();
                    manageResp.linkCommonUrl=linkCommonUrlMap.get(resp.eventId);
                    manageResp.startTimeStr = DateUtil.getWeekAndDate(resp.startTime);
                    manageResp.numberState = numberStatesMap.get(resp.eventId);
                    manageResp.recordNum = recordNumsMap.get(resp.eventId);
                    manageResp.sortType = eventService.getSortType(eventService.getEvent(resp.eventId), hotEvents,
                            recommendEvents, null);
                    responseList.add(manageResp);
                }
        );
        return new PageResult<>(req.page, req.size, responseList.size(), responseList);
    }


    /**
     * EventManage->EventManageResp
     *
     * @param resp
     * @return
     */
    public EventManageResp eventManage2ManageResp(EventManage resp) {
        EventManageResp manageResp = new EventManageResp();
        manageResp.city = new CityReq();
        manageResp.city.id = resp.cityId;
        manageResp.city.name = resp.cityName;
        manageResp.title = resp.title;
        manageResp.lineType = resp.lineType;
        manageResp.startTime = resp.startTime;
        return manageResp;
    }

    /**
     * 线下活动的热门或者推荐
     */
    public void offlineRcmOrHot(RmdOrHotReq req) {
        Event event = eventService.getEvent(req.eventId);

        if (req.type == ParamEnum.HotOrRcmType.TYPE_RECOMMENT.getValue()) {

            EventCityRelation eventCityRelation = new EventCityRelation();
            //取消推荐
            if (StringUtil.isEmpty(req.cityList)) {
                checkHotEvent(event.getId());
                checkHomeEvent(event.getId());
                checkFinancingEvent(event.getId());
                EventCityRelationExample example = new EventCityRelationExample();
                example.createCriteria().andEventIdEqualTo(req.eventId);
                eventCityRelationMapper.deleteByExample(example);
                eventCityRelation.setEventId(req.eventId);
                eventCityRelation.setCityId(0);
                eventCityRelationMapper.insertSelective(eventCityRelation);
                solrService.updateSolrInfoById(event.getId().toString(), "recommend_status", 0);
                solrService.updateSolrInfoById(event.getId().toString(), "recommend_time", 0);

            } else {
                //如果推荐城市没有主办方，报错
                if (!req.cityList.contains(event.getCityId()) && !req.cityList.contains(-2)) {
                    throw ExceptionUtil.createParamException("推荐必须包含主办城市");
                }
                //推荐或者取消部分推荐
                EventCityRelationExample example = new EventCityRelationExample();
                //取消部分推荐
                if (cancelRecommend(event.getId())) {
                    List<Integer> deleteCityIds = checkHotEventForOffLine(req.eventId, req.cityList);
                    checkHomeEventForOffLine(req.eventId, req.cityList);
                    //删除部分推荐
                    if (!StringUtil.isEmpty(deleteCityIds)) {
                        example.createCriteria().andEventIdEqualTo(event.getId()).andCityIdIn(deleteCityIds);
                        eventCityRelationMapper.deleteByExample(example);
                    }
                    return;
                }
                checkHasRecommend(event.getId());
                checkState(event.getState());
                example.clear();
                example.createCriteria().andEventIdEqualTo(req.eventId);
                eventCityRelationMapper.deleteByExample(example);
                List<EventCityRelation> eventCityList = new ArrayList<>();

                for (Integer cityId : req.cityList) {
                    EventCityRelation eventCity = new EventCityRelation();
                    eventCity.setEventId(req.eventId);
                    eventCity.setRecommendState(SqlEnum.RecommendType.TYPE_RECOMMEND.getValue());
                    eventCity.setRecommendTime(DateUtil.getNowDate().getTime());
                    eventCity.setCityId(cityId);
                    eventCity.setHotState(SqlEnum.HotType.TYPE_NOT_HOT.getValue());
                    eventCity.setCreateTime(DateUtil.getNowDate());
                    eventCityList.add(eventCity);
                }
                eventCityRelationMapper.insertByBatchSelective(eventCityList);
                saveSystemSms(req);
                saveAppSms(req);
                sendsms(req);
                solrService.updateSolrInfoById(event.getId().toString(), "recommend_status", 1);
                solrService.updateSolrInfoById(event.getId().toString(), "recommend_time", DateUtil.getNowDate()
                        .getTime());
            }

        } else if (req.type == ParamEnum.HotOrRcmType.TYPE_HOT.getValue()) {
            //热门或者取消热门
            checkRecommendEvent(req.eventId);
            EventCityRelationExample example = new EventCityRelationExample();
            example.createCriteria().andEventIdEqualTo(req.eventId).andHotStateEqualTo(SqlEnum.HotType.TYPE_HOT
                    .getValue());
            List<EventCityRelation> hots = eventCityRelationMapper.selectByExample(example);
            example.clear();
            example.createCriteria().andEventIdEqualTo(req.eventId);
            EventCityRelation eventCity = new EventCityRelation();
            eventCity.setHotState(SqlEnum.HotType.TYPE_NOT_HOT.getValue());
            eventCityRelationMapper.updateByExampleSelective(eventCity, example);
            solrService.updateSolrInfoById(event.getId().toString(), "sort_type", ParamEnum.EventSortType
                    .DEGREE_NORMAL.getValue());
            if (!StringUtil.isEmpty(req.cityList)) {
                checkHasHot(event.getId());
                checkState(event.getState());
                //热门
                if (!req.cityList.contains(event.getCityId()) && !req.cityList.contains(-2)) {
                    throw ExceptionUtil.createParamException("热门必须包含主办城市");
                }
                EventCityRelation eventCityHot = new EventCityRelation();
                eventCityHot.setHotState(SqlEnum.HotType.TYPE_HOT.getValue());
                eventCityHot.setHotTime(DateUtil.getNowDate().getTime());
                example.clear();
                example.createCriteria().andEventIdEqualTo(req.eventId).andCityIdIn(req.cityList);
                eventCityRelationMapper.updateByExampleSelective(eventCityHot, example);
                //如果原来热门城市为空，才是热门操作，进行短信发送
                if (StringUtil.isEmpty(hots)) {
                    saveSystemSms(req);
                    saveAppSms(req);
                    sendsms(req);
                }
                solrService.updateSolrInfoById(event.getId().toString(), "sort_type", ParamEnum.EventSortType
                        .DEGREE_HOT.getValue());
            }
        }
    }

    /**
     * 判断状态，是否是取消部分推荐
     *
     * @param eventId
     * @return
     */
    private boolean cancelRecommend(Integer eventId) {
        Boolean flag = false;
        EventCityRelationExample example = new EventCityRelationExample();
        example.createCriteria().andEventIdEqualTo(eventId).andRecommendStateEqualTo(SqlEnum.RecommendType
                .TYPE_RECOMMEND.getValue());
        List<EventCityRelation> eventCityRelations = eventCityRelationMapper.selectByExample(example);
        for (EventCityRelation relation : eventCityRelations) {
            if (relation.getCityId() != 0) {
                flag = true;
                break;
            }
        }

        return flag;
    }



    public void insertDefaultHotAndRcm(Integer eventId) {
        EventCityRelation eventCityRelation = new EventCityRelation();
        eventCityRelation.setEventId(eventId);
        eventCityRelation.setHotState(SqlEnum.HotType.TYPE_NOT_HOT.getValue());
        eventCityRelation.setCityId(0);
        eventCityRelation.setRecommendState(SqlEnum.RecommendType.TYPE_NOT_RECOMMEND.getValue());
        eventCityRelation.setCreateTime(DateUtil.getNowDate());
        eventCityRelationMapper.insertSelective(eventCityRelation);
    }

    /**
     * 活动推荐到创业圈/投融资页面
     *
     * @param req 活动id
     * @return
     */
    public Object recommendHome(RmdOrHotReq req) {
        Event event = Optional.ofNullable(eventMapper.selectByPrimaryKey(req.eventId))
                .orElseThrow(() -> ExceptionUtil.createParamException("该活动不存在"));

        if (!new Byte((byte) 2).equals(event.getState())) {
            throw ExceptionUtil.createParamException("该活动未上线或者已删除");
        }

        // 推荐状态
        EventCityRelationExample example = new EventCityRelationExample();
        example.createCriteria().andEventIdEqualTo(req.eventId)
                .andRecommendStateEqualTo(EnumRecommendStatus.RECOMMENDED.getValue());
        List<EventCityRelation> eventCityRelations = eventCityRelationMapper.selectByExample(example);
        if (StringUtil.isEmpty(eventCityRelations)) {
            throw ExceptionUtil.createParamException("该活动没有推荐的城市");
        }
        Set<Integer> curRecommendCityIds = new HashSet<>();
        Set<Integer> curRecommendHomeCityIds = new HashSet<>();
        Set<Integer> curUnrecommendHomeCityIds = new HashSet<>();
        eventCityRelations.forEach(eventCityRelation -> {
            curRecommendCityIds.add(eventCityRelation.getCityId());
            Integer status = eventCityRelation.getRecommendHomeStatus();
            if (new Integer(4).equals(req.type)) {
                status = eventCityRelation.getRecommendFinancingState();
            }
            if (EnumRecommendStatus.RECOMMENDED.getValue().equals(status)) {
                curRecommendHomeCityIds.add(eventCityRelation.getCityId());
            } else {
                curUnrecommendHomeCityIds.add(eventCityRelation.getCityId());
            }
        });

        // 线上活动
        if (SqlEnum.EventLineType.TYPE_ONLINE.getValue().equals((int) event.getLineType())) {
            if (StringUtil.isNotEmpty(req.cityList)) {
                throw ExceptionUtil.createParamException("线上活动城市错误");
            }
            if (!EnumRecommendStatus.RECOMMENDED.getValue().equals(eventCityRelations.get(0).getRecommendState())) {
                throw ExceptionUtil.createParamException("操作失败，只有被推荐的活动才允许推至" + SqlEnum.HotType.getDsc(req.type));
            }
            Integer curRecommend = eventCityRelations.get(0).getRecommendHomeStatus();
            if (new Integer(4).equals(req.type)) {
                curRecommend = eventCityRelations.get(0).getRecommendFinancingState();
            }
            if (EnumRecommendStatus.RECOMMENDED.getValue().equals(curRecommend) && new Integer(1).equals(req.action)) {
                throw ExceptionUtil.createParamException("操作失败，请刷新后重试");
            }
            if (EnumRecommendStatus.INIT.getValue().equals(curRecommend) && new Integer(0).equals(req.action)) {
                throw ExceptionUtil.createParamException("操作失败，请刷新后重试");
            }
            updateRecommendStatus(eventCityRelations.get(0), req.action, req.type);
        } else {
            req.cityList = Optional.ofNullable(req.cityList).orElse(new ArrayList<>());
            // 取消的城市，客户端做了反选
            List<Integer> operCity = new ArrayList<>();
            if (new Integer(0).equals(req.action)) {
                for (Integer cityId : curRecommendHomeCityIds) {
                    if (!req.cityList.contains(cityId)) {
                        operCity.add(cityId);
                    }
                }
            } else {
                operCity = req.cityList;
            }
            if (StringUtil.isEmpty(operCity)) {
                throw ExceptionUtil.createParamException("操作失败，请刷新后重试");
            }
            for (EventCityRelation eventCityRelation : eventCityRelations) {
                for (Integer cityId : operCity) {
                    if (!curRecommendCityIds.contains(cityId)) {
                        throw ExceptionUtil.createParamException("操作失败，只有被推荐的城市才允许推至" + SqlEnum.HotType.getDsc(req.type));
                    }
                    if (curRecommendHomeCityIds.contains(cityId) && new Integer(1).equals(req.action)) {
                        throw ExceptionUtil.createParamException("操作失败，请刷新后重试");
                    }
                    if (curUnrecommendHomeCityIds.contains(cityId) && new Integer(0).equals(req.action)) {
                        throw ExceptionUtil.createParamException("操作失败，请刷新后重试");
                    }
                }
                if (!operCity.contains(event.getCityId()) && new Integer(1).equals(req.action)
                        && !operCity.contains(-2)) {
                    throw ExceptionUtil.createParamException("操作失败，活动推至"+ SqlEnum.HotType.getDsc(req.type) +"必须包含所属城市");
                }

                if (operCity.contains(event.getCityId()) && operCity.size() != curRecommendHomeCityIds.size()
                        && new Integer(0).equals(req.action) && !operCity.contains(-2)) {
                    throw ExceptionUtil.createParamException("操作失败，活动推至"+ SqlEnum.HotType.getDsc(req.type) +"必须包含所属城市");
                }

                if (operCity.contains(eventCityRelation.getCityId())) {
                    if (new Integer(1).equals(req.action)) {
                        updateRecommendStatus(eventCityRelation, EnumRecommendStatus.RECOMMENDED.getValue(), req.type);
                    } else {
                        updateRecommendStatus(eventCityRelation, EnumRecommendStatus.INIT.getValue(), req.type);
                    }
                }
            }
        }
        return true;
    }

    /**
     * 修改推荐首页状态
     *
     * @param newRecommendStatus 修改之后的推荐id
     * @param eventCityRelation  数据库记录
     */
    private void updateRecommendStatus(EventCityRelation eventCityRelation, Integer newRecommendStatus, Integer type) {
        if (eventCityRelation == null || !EnumRecommendStatus.RECOMMENDED.getValue().equals(eventCityRelation.getRecommendState())) {
            throw ExceptionUtil.createParamException("存城市未被推荐");
        }
        EventCityRelation eventCityRelationNew = new EventCityRelation();
        eventCityRelationNew.setId(eventCityRelation.getId());
        if (new Integer(3).equals(type)) {
            eventCityRelationNew.setRecommendHomeStatus(newRecommendStatus);
            if (EnumRecommendStatus.RECOMMENDED.getValue().equals(newRecommendStatus)) {
                eventCityRelationNew.setRecommendHomeTime(redisService.getCurrentTime());
            } else {
                eventCityRelationNew.setUnrecommendHomeTime(redisService.getCurrentTime());
            }
        } else {
            eventCityRelationNew.setRecommendFinancingState(newRecommendStatus);
            eventCityRelationNew.setRecommendFinancingTime(redisService.getCurrentTime());
        }
        if (eventCityRelationMapper.updateByPrimaryKeySelective(eventCityRelationNew) <= 0) {
            throw ExceptionUtil.createParamException("数据库更新异常");
        }
    }

    /**
     * 已经推荐，由于网络原因可能多次进入线上推荐流程，为了不发多条信息，需要检测是否推荐
     *
     * @param eventId
     */
    private void checkHasRecommend(Integer eventId) {
        EventCityRelationExample example = new EventCityRelationExample();
        example.createCriteria().andEventIdEqualTo(eventId).andRecommendStateEqualTo(SqlEnum.RecommendType.
                TYPE_RECOMMEND.getValue());
        List<EventCityRelation> recommends = eventCityRelationMapper.selectByExample(example);
        if (!StringUtil.isEmpty(recommends)) {
            throw ExceptionUtil.createParamException("该活动已经推荐");
        }
    }

    private void checkHasHot(Integer eventId) {
        EventCityRelationExample example = new EventCityRelationExample();
        example.createCriteria().andEventIdEqualTo(eventId).andHotStateEqualTo(SqlEnum.HotType.
                TYPE_HOT.getValue());
        List<EventCityRelation> hots = eventCityRelationMapper.selectByExample(example);
        if (!StringUtil.isEmpty(hots)) {
            throw ExceptionUtil.createParamException("该活动已经热门");
        }
    }

    private String getEndTime(String endTime) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (!StringUtil.isEmpty(endTime)) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(simpleDateFormat.parse(endTime));
                cal.add(Calendar.DATE, 1);  //加1天
                endTime = cal.getTimeInMillis() + "";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return endTime;
    }

    private String getStartTime(String startTime) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (!StringUtil.isEmpty(startTime)) {
                startTime = simpleDateFormat.parse(startTime).getTime() + "";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startTime;
    }

    /**
     * 拿到sponsorsMap
     *
     * @param sponsorList
     * @return
     */
    public Map<Integer, List<EventSponsorManage>> getSponsorsManageMap(List<EventSponsorManage> sponsorList) {
        Map<Integer, List<EventSponsorManage>> sponsorManageMap = new HashMap<>();
        for (EventSponsorManage eventSponsorManage : sponsorList) {
            //不存在key，才新建
            List<EventSponsorManage> lists;
            if (!sponsorManageMap.containsKey(eventSponsorManage.getEventId())) {
                lists = new ArrayList<>();
                lists.add(eventSponsorManage);
            }
            //存在，add
            else {
                lists = sponsorManageMap.get(eventSponsorManage.getEventId());
                lists.add(eventSponsorManage);
            }
            sponsorManageMap.put(eventSponsorManage.getEventId(), lists);

        }
        return sponsorManageMap;
    }

    public Map<Integer, List<Sponsor>> getSponsorsMap(List<EventSponsorManage> sponsorList) {
        Map<Integer, List<Sponsor>> sponsorMap = new HashMap<>();
        for (EventSponsorManage eventSponsorManage : sponsorList) {
            //不存在key，才新建
            List<Sponsor> lists;
            if (!sponsorMap.containsKey(eventSponsorManage.getEventId())) {
                lists = new ArrayList<>();
                lists.add(eventSponsorManage2Sponsor(eventSponsorManage));
            }
            //存在，add
            else {
                lists = sponsorMap.get(eventSponsorManage.getEventId());
                lists.add(eventSponsorManage2Sponsor(eventSponsorManage));
            }
            sponsorMap.put(eventSponsorManage.getEventId(), lists);

        }
        return sponsorMap;
    }

    /**
     * 拿到linkCommonUrlMap
     *
     * @return
     */
    private Map<Integer, String> getLinkCommonUrlMap(List<EventManage> eventList) {
        Map<Integer, String> linkCommonUrlMap = new HashMap<>();
        if (!StringUtil.isEmpty(eventList)) {
            List<Integer> eventIds = new ArrayList<>();
            for (EventManage eventManage : eventList) {
                eventIds.add(eventManage.getEventId());
            }
            ExtensionLinkExample extensionExample = new ExtensionLinkExample();
            extensionExample.createCriteria().andLinkTypeEqualTo(SqlEnum.LINKTYPE.TYPE_DEFAULT.getValue().byteValue())
                    .andEventIdIn(eventIds);
            List<ExtensionLink> extensionLinkList = extensionLinkMapper.selectByExample(extensionExample);
            if (!StringUtil.isEmpty(extensionLinkList)) {
                for (ExtensionLink extensionLink : extensionLinkList) {
                    linkCommonUrlMap.put(extensionLink.getEventId(), extensionLink.getLinkUrlCommon());
                }
            }
        }
        return linkCommonUrlMap;
    }

    /**
     * 拿到onLineHotAndRcmMap
     *
     * @param eventList
     * @return
     */
    private Map<Integer, EventCityRelation> getOnLineHotAndRcmMap(List<EventManage> eventList) {
        Map<Integer, EventCityRelation> onLineHotAndRcmMap = new HashMap<>();
        List<Integer> onlineEventIds = new ArrayList<>();
        for (EventManage eventManage : eventList) {
            if (eventManage.lineType == SqlEnum.EventLineType.TYPE_ONLINE.getValue()) {
                onlineEventIds.add(eventManage.eventId);
            }
        }
        if (!StringUtil.isEmpty(onlineEventIds)) {
            EventCityRelationExample cityRelationExample = new EventCityRelationExample();
            cityRelationExample.createCriteria().andEventIdIn(onlineEventIds);
            List<EventCityRelation> hotAndRcmList = eventCityRelationMapper.selectByExample(cityRelationExample);
            for (EventCityRelation eventCityRelation : hotAndRcmList) {
                onLineHotAndRcmMap.put(eventCityRelation.getEventId(), eventCityRelation);
            }
        }
        return onLineHotAndRcmMap;
    }

    /**
     * 拿到signUpTotalMap
     *
     * @param commodityIds
     * @return
     */
    private Map<Integer, Integer> getTicketMap(List<Integer> commodityIds) {
        Map<Integer, Integer> countMap = commodityRemoteService.getFreeTicketsCountMap(commodityIds);
        return countMap;
    }

    /**
     * 拿到orgMap
     *
     * @param eventList
     * @return
     */
    private Map<Integer, String> getOrgMap(List<EventManage> eventList) {
        Map<Integer, String> orgMap = new HashMap<>();
        List<Integer> orgIds = new ArrayList<>();
        for (EventManage eventManage : eventList) {
            orgIds.add(eventManage.orgId);
        }
        OrgInfoExample orgInfoExample = new OrgInfoExample();
        orgInfoExample.createCriteria().andNewIdIn(orgIds);
        List<OrgInfo> orgInfos = orgInfoMapper.selectByExample(orgInfoExample);
        for (OrgInfo orgInfo : orgInfos) {
            orgMap.put(orgInfo.getNewId(), orgInfo.getName());
        }
        return orgMap;
    }

    private Map<Integer, List<CityReq>> getCityMap(List<EventCityRelation> citys) {
        Map<Integer, String> allCities = cityRemoteService.getAllCityMap();
        Map<Integer, List<CityReq>> cityMap = new HashMap<>();
        for (EventCityRelation eventCityRelation : citys) {
            List<CityReq> cityList;
            CityReq cityReq = new CityReq();
            //线上改成线下，cityid为0，过滤0
            if (eventCityRelation.getCityId() == null) {// || eventCityRelation.getCityId().equals(0)
                continue;
            }
            if (!cityMap.containsKey(eventCityRelation.getEventId())) {
                cityList = new ArrayList<>();
                cityReq.id = eventCityRelation.getCityId();
                cityReq.name = allCities.get(eventCityRelation.getCityId());
                cityReq.recommendHomeStatus = eventCityRelation.getRecommendHomeStatus();
                cityReq.recommendFinancingStatus = eventCityRelation.getRecommendFinancingState();
                cityReq.hotStatus = eventCityRelation.getHotState();
                cityList.add(cityReq);
            } else {
                cityList = cityMap.get(eventCityRelation.getEventId());
                cityReq.id = eventCityRelation.getCityId();
                cityReq.name = allCities.get(eventCityRelation.getCityId());
                cityReq.recommendHomeStatus = eventCityRelation.getRecommendHomeStatus();
                cityReq.recommendFinancingStatus = eventCityRelation.getRecommendFinancingState();
                cityReq.hotStatus = eventCityRelation.getHotState();
                cityList.add(cityReq);
            }
            cityMap.put(eventCityRelation.getEventId(), cityList);
        }
        return cityMap;
    }

    /**
     * 检验是否存在热门城市，如果存在不能取消推荐
     * true通过检测，
     *
     * @param eventId
     * @return
     */
    private void checkHotEvent(Integer eventId) {

        EventCityRelationExample example = new EventCityRelationExample();
        example.createCriteria().andEventIdEqualTo(eventId).andHotStateEqualTo(SqlEnum.HotType.TYPE_HOT.getValue());
        List<EventCityRelation> list = eventCityRelationMapper.selectByExample(example);
        if (!StringUtil.isEmpty(list)) {
            throw ExceptionUtil.createParamException("请先取消热门");
        }
    }

    /**
     * 检验是否存在热门城市，如果存在不能取消推荐
     * true通过检测，
     *
     * @param eventId
     * @return
     */
    private void checkHomeEvent(Integer eventId) {

        EventCityRelationExample example = new EventCityRelationExample();
        example.createCriteria().andEventIdEqualTo(eventId).andRecommendHomeStatusEqualTo(SqlEnum.HotType.TYPE_HOT.getValue());
        List<EventCityRelation> list = eventCityRelationMapper.selectByExample(example);
        if (!StringUtil.isEmpty(list)) {
            throw ExceptionUtil.createParamException("请先取消推荐创业圈");
        }
    }

    /**
     * 检验是否存在热门城市，如果存在不能取消推荐
     * true通过检测，
     *
     * @param eventId
     * @return
     */
    private void checkFinancingEvent(Integer eventId) {
        EventCityRelationExample example = new EventCityRelationExample();
        example.createCriteria().andEventIdEqualTo(eventId).andRecommendFinancingStateEqualTo(SqlEnum.HotType.TYPE_HOT.getValue());
        List<EventCityRelation> list = eventCityRelationMapper.selectByExample(example);
        if (!StringUtil.isEmpty(list)) {
            throw ExceptionUtil.createParamException("请先取消推荐投融页");
        }
    }

    /**
     * 取消推荐时判断是否在热门城市里有，没有才可以取消，把需要删除的cityId传回
     *
     * @param eventId
     * @param cityList
     */
    private List<Integer> checkHotEventForOffLine(Integer eventId, List<Integer> cityList) {
        EventCityRelationExample example = new EventCityRelationExample();
        example.createCriteria().andEventIdEqualTo(eventId).andHotStateEqualTo(SqlEnum.HotType.TYPE_HOT.getValue());
        List<EventCityRelation> hotRelations = eventCityRelationMapper.selectByExample(example);
        List<Integer> hotCityIds = new ArrayList<>();
        for (EventCityRelation relation : hotRelations) {
            hotCityIds.add(relation.getCityId());
        }
        if (!cityList.containsAll(hotCityIds)) {
            throw ExceptionUtil.createParamException("请先取消热门");
        }
        example.clear();
        example.createCriteria().andEventIdEqualTo(eventId).andRecommendStateEqualTo(SqlEnum.RecommendType
                .TYPE_RECOMMEND.getValue());
        List<EventCityRelation> rcmrRelations = eventCityRelationMapper.selectByExample(example);
        List<Integer> recomendCitys = new ArrayList<>();
        for (EventCityRelation relation : rcmrRelations) {
            recomendCitys.add(relation.getCityId());
        }
        recomendCitys.removeAll(cityList);
        return recomendCitys;
    }

    /**
     * 取消推荐时判断是否在热门城市里有，没有才可以取消，把需要删除的cityId传回
     *
     * @param eventId
     * @param cityList
     */
    private void checkHomeEventForOffLine(Integer eventId, List<Integer> cityList) {
        EventCityRelationExample example = new EventCityRelationExample();
        example.createCriteria().andEventIdEqualTo(eventId).andRecommendHomeStatusEqualTo(SqlEnum.HotType.TYPE_HOT.getValue());
        List<EventCityRelation> homeRelations = eventCityRelationMapper.selectByExample(example);
        List<Integer> homeCityIds = new ArrayList<>();
        for (EventCityRelation relation : homeRelations) {
            homeCityIds.add(relation.getCityId());
        }
        if (!cityList.containsAll(homeCityIds)) {
            throw ExceptionUtil.createParamException("请先取消推荐首页");
        }
    }

    /**
     * 检验是否存在推荐城市，如果不存在不能设置为热门
     * true通过检测，
     *
     * @param eventId
     * @return
     */
    private void checkRecommendEvent(Integer eventId) {

        EventCityRelationExample example = new EventCityRelationExample();
        example.createCriteria().andEventIdEqualTo(eventId).andRecommendStateEqualTo(SqlEnum.RecommendType
                .TYPE_RECOMMEND.getValue());
        List<EventCityRelation> list = eventCityRelationMapper.selectByExample(example);
        if (StringUtil.isEmpty(list)) {
            throw ExceptionUtil.createParamException("请先推荐活动");
        }

    }

    /**
     * 检验是否是发布，只有发布的活动可以推荐
     *
     * @param state
     */
    private void checkState(Byte state) {
        if (!state.equals(SqlEnum.EventStateType.TYPE_EVENT_PUBLISHED.getByteValue())) {
            throw ExceptionUtil.createParamException("只有发布的活动能推荐或热门");
        }
    }

    /**
     * 保存云活动系统消息
     *
     * @param req
     */
    @Transactional
    public void saveSystemSms(RmdOrHotReq req) {

        EventSysMessage sysMessage = new EventSysMessage();
        Event event = eventMapper.selectByPrimaryKey(req.eventId);
        sysMessage.setEventId(event.getId());
        sysMessage.setCreateTime(DateUtil.getNowDate().getTime());
        sysMessage.setModifyTime(DateUtil.getNowDate().getTime());
        sysMessage.setOperatorUid(req.adminUid);
        sysMessage.setIsread(SqlEnum.SysMsgRead.TYPE_HASNOTREAD.getByteValue());
        sysMessage.setState(SqlEnum.SysMsgState.TYPE_NORMAL.getByteValue());
        sysMessage.setOrgId(event.getOrgId());
        sysMessage.setSendUid(event.getPublishUid());
        sysMessage.setConfirmType(req.type.byteValue());
        eventSysMessageMapper.insertSelective(sysMessage);
        SysUserRelationReq relationReq = new SysUserRelationReq();
        relationReq.eventId = req.eventId;
        relationReq.messageId = sysMessage.getId();
        sysMsgService.insertRelation(relationReq);


    }

    /**
     * 发送短信,记录到event_sms表中
     */
    private void sendsms(RmdOrHotReq req) {
        Event event = eventService.getEvent(req.eventId);
        ExtensionLinkExample example = new ExtensionLinkExample();
        example.createCriteria().andEventIdEqualTo(event.getId()).andLinkTypeEqualTo(SqlEnum.LINKTYPE.TYPE_DEFAULT
                .getValue().byteValue());
        List<ExtensionLink> extensionList = extensionLinkMapper.selectByExample(example);
        ExtensionLink extensionLink = new ExtensionLink();
        if (!StringUtil.isEmpty(extensionList)) {
            extensionLink = extensionList.get(0);
        }
        String content = "";
        if (req.type == ParamEnum.HotOrRcmType.TYPE_RECOMMENT.getValue()) {
            content = SmsMsg.EVENT_RECOMMEND[0] + event.getTitle() + SmsMsg.EVENT_RECOMMEND[1] + extensionLink
                    .getLinkUrlCommon();
        } else if (req.type == ParamEnum.HotOrRcmType.TYPE_HOT.getValue()) {
            content = SmsMsg.EVENT_HOT[0] + event.getTitle() + SmsMsg.EVENT_HOT[1] + extensionLink.getLinkUrlCommon();
        }
        User profile = userService.getUserInfoById(event.getPublishUid());
        List<String> phoneList = new ArrayList<>();
        phoneList.add(profile.phone);
        smsRemoteService.sendSMS(phoneList, content);
        //记录到event_sms表中
        EventSms eventSms = new EventSms();
        eventSms.setEventId(event.getId());
        eventSms.setUid(event.getPublishUid());
        eventSms.setContent(content);
        eventSms.setCreateTime(System.currentTimeMillis());
        eventSms.setModifyTime(System.currentTimeMillis());
        eventSms.setSource(SqlEnum.SmsSource.TYPE_MANAGE_RECOMMEND.getValue());
        eventSms.setFlag(SqlEnum.SmsFlag.TYPE_SINGLE.getValue());
        eventSmsMapper.insertSelective(eventSms);
    }

    /**
     * 发送消息到app
     *
     * @param req
     */
    private void saveAppSms(RmdOrHotReq req) {
        Event event = eventMapper.selectByPrimaryKey(req.eventId);
        Integer fromUid = EnumIMType.ACTIVE.getValue();
        Integer toUid = event.getPublishUid();
        String content = "";
        if (req.type == ParamEnum.HotOrRcmType.TYPE_RECOMMENT.getValue()) {
            content = AppMsg.RECOMMEND[0] + event.getTitle() + AppMsg.RECOMMEND[1];
        } else if (req.type == ParamEnum.HotOrRcmType.TYPE_HOT.getValue()) {
            content = AppMsg.HOT[0] + event.getTitle() + AppMsg.HOT[1];
        }
        smsRemoteService.sendAppMsg(content, fromUid, toUid);

    }


    /**
     * 屏蔽/回复屏蔽 优惠券
     *
     * @param req 活动运营后台请求参数
     * @return
     */
    public Object updateCoupon(EventManageReq req) {

        if (NumberUtil.isInValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("eventId参数异常");
        }
        if (!ParamEnum.CouponStatus.STATUS_NORMAL.getValue().equals(req.operate) &&
                !ParamEnum.CouponStatus.STATUS_SHIELD.getValue().equals(req.operate)) {
            throw ExceptionUtil.createParamException("operation参数异常");
        }
        Event event = eventService.getEvent(req.eventId);
        event.setCouponStatus(req.operate);
        eventMapper.updateByPrimaryKeySelective(event);
        return null;
    }

    public static  void main(String[] args) {
        System.out.println(SqlEnum.HotType.getDsc(3));
    }
}
