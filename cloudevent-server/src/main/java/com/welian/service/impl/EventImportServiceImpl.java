package com.welian.service.impl;

import com.welian.beans.cloudevent.Sponsor;
import com.welian.beans.cloudevent.event.EventListId;
import com.welian.beans.cloudevent.event.EventPara;
import com.welian.beans.cloudevent.event.SaveEventGuestImportReq;
import com.welian.beans.cloudevent.event.SaveEventImportReq;
import com.welian.beans.cloudevent.event.SaveEventResp;
import com.welian.beans.cloudevent.favorite.FavoriteImportReq;
import com.welian.config.CloudEventConfig;
import com.welian.config.Constant;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.mapper.EventGuestMapper;
import com.welian.mapper.EventMapper;
import com.welian.mapper.EventRecordMapper;
import com.welian.mapper.EventSignAuthMapper;
import com.welian.mapper.EventStateCustomMapper;
import com.welian.mapper.EventStateProjectMapper;
import com.welian.mapper.EventTicketOrderMapper;
import com.welian.mapper.OrgInfoMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventExample;
import com.welian.pojo.EventGuestExample;
import com.welian.pojo.EventRecordExample;
import com.welian.pojo.EventSignAuth;
import com.welian.pojo.EventSponsor;
import com.welian.pojo.EventStateCustom;
import com.welian.pojo.EventStateCustomExample;
import com.welian.pojo.EventStateProject;
import com.welian.pojo.EventStateProjectExample;
import com.welian.pojo.EventTicketOrderExample;
import com.welian.pojo.EventUidFavoriteRelation;
import com.welian.pojo.ExtensionLink;
import com.welian.service.CommodityRemoteService;
import com.welian.service.CustomFormModuleService;
import com.welian.service.EventCityRelationModule;
import com.welian.service.EventSolrService;
import com.welian.service.GuestModuleService;
import com.welian.service.SponsorsModuleService;
import com.welian.service.TagModuleService;
import org.sean.framework.util.Logger;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import com.welian.utils.Base64Utils;
import com.welian.utils.DateUtil;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dayangshu on 17/7/4.
 */
@Service("eventImportService")
public class EventImportServiceImpl {
    private static final Logger logger = Logger.newInstance(EventImportServiceImpl.class);
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private SponsorsModuleService sponsorsModuleService;
    @Autowired
    private GuestModuleService guestModuleService;
    @Autowired
    private StateProjectModuleServiceImpl stateProjectModuleService;
    @Autowired
    private StateCustomModuleService stateCustomModuleService;
    @Autowired
    private EventCityRelationModule eventCityRelationModule;
    @Autowired
    private CustomFormModuleService customFormModuleService;
    @Autowired
    private TagModuleService tagModuleService;
    @Autowired
    private OrgInfoMapper orgInfoMapper;
    @Autowired
    private CloudEventConfig cloudEventConfig;
    @Autowired
    private ExtensionLinkServiceImpl extensionLinkService;
    @Autowired
    private CommodityRemoteService commodityRemoteService;
    @Autowired
    private EventSignAuthMapper eventSignAuthMapper;
    @Autowired
    private EventGuestMapper eventGuestMapper;
    @Autowired
    private EventRecordMapper eventRecordMapper;
    @Autowired
    private EventStateCustomMapper eventStateCustomMapper;
    @Autowired
    private EventStateProjectMapper eventStateProjectMapper;
    @Autowired
    private EventSolrService eventSolrService;
    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private EventTicketOrderMapper eventTicketOrderMapper;


    @Transactional
    public Object saveFinancingEvent(SaveEventImportReq saveEventReq) {
        EventPara eventReq = saveEventReq.event;
        eventReq.eventType = SqlEnum.EventType.TYPE_EVENT_ROADSHOW.getByteValue();

        Integer count = orgInfoMapper.selectByNewId(saveEventReq.orgId);
        if (count == 0) {
            throw ExceptionUtil.createParamException("机构不存在");
        }
        Event eventOld = eventMapper.selectByPrimaryKey(eventReq.eventId);
        if (eventOld != null) {
            throw ExceptionUtil.createParamException(eventOld.getId() + "活动已存在");
        }
        Event event = new Event();
        event.setTemplateId(SqlEnum.EventType.TYPE_EVENT_ROADSHOW.getValue());//路演大赛
        event.setOrgId(saveEventReq.orgId);
        event.setPublishUid(saveEventReq.uid);
        event.setId(eventReq.eventId);//必须要加入
        event.setOldEventId(eventReq.eventId);
        event.setTitle(eventReq.title);
        event.setLineType(eventReq.lineType);
        event.setAddress(eventReq.address);
        event.setDeadlineTime(eventReq.deadlineTime);
        event.setStartTime(eventReq.startTime);
        event.setEndTime(eventReq.endTime);
        event.setLogo(eventReq.logo);
        event.setDetail(eventReq.detail);
        event.setOpenExtension(eventReq.isPromotion);
        event.setState(eventReq.state);
        event.setModifyTime(saveEventReq.modifyTime);
        event.setCreateTime(saveEventReq.createTime);
        if (eventReq.city != null) {
            event.setCityId(eventReq.city.id);
            if (StringUtil.isEmpty(eventReq.city.name)) {
                event.setCityName("");
            } else {
                event.setCityName(eventReq.city.name);
            }
        }
        //地址位置
        if (eventReq.loc != null) {
            event.setLongitude(eventReq.loc.lng);
            event.setLatitude(eventReq.loc.lat);
        }
        SaveEventResp resp = new SaveEventResp();
        int num = eventMapper.insertSelective2(event);
        if (!NumberUtil.isValidId(num)) {
            throw ExceptionUtil.createParamException("保存活动失败");
        }

        //给发布者加一个签到权限
        EventSignAuth eventSignAuth = new EventSignAuth();
        eventSignAuth.setEventId(event.getId());
        eventSignAuth.setUid(saveEventReq.uid);
        eventSignAuth.setStatus(SqlEnum.EventSignAuthStatus.AUTH.getByteValue());
        eventSignAuth.setCreateTime(DateUtil.getNowDate());
        eventSignAuth.setModifyTime(DateUtil.getNowDate());
        eventSignAuthMapper.insertSelective(eventSignAuth);

        //创建活动的url
        ExtensionLink extensionLink = extensionLinkService.createDefaultExtensionLinkChannelEventId(event.getId());
        if (extensionLink != null) {
            //方便前端处理，返回长链,加入pre代表预览，前端用来隐藏掉报名按钮
            resp.linkUrlCommon = cloudEventConfig.getLink_common_prefix() +
                    Base64Utils.encode(extensionLink.getUniqueKey().getBytes()).toString();
        }


        //导入的项目统一创建活动群聊
        if (eventReq.projectModule != null &&
                eventReq.projectModule.isGroupChat.byteValue() == Constant.GROUP_CHART_OPEN) {
            eventReq.projectModule.isGroupChat = Constant.GROUP_CHART_CLOSE;
        }
        //保存活动的项目报名的设置
        stateProjectModuleService.save(eventReq.eventId, eventReq.projectModule);
        //在推荐热门表生成一条数据，方便线上活动的update操作
        //manageService.insertDefaultHotAndRcm(eventReq.eventId);
        //创建商品

        //调商品服务生成商品号
        eventReq.endTime = System.currentTimeMillis() + 60 * 1000 * 60l * 24 * 7;
        Integer commodityId = commodityRemoteService.CreateCommodity(eventReq);
        if (NumberUtil.isInValidId(commodityId)) {
            throw ExceptionUtil.createParamException("commodityId参数异常");
        }
        eventSolrService.synchronousEventToSolr(eventReq);
        //
        Event event1 = new Event();
        event1.setId(event.getId());
        event1.setCommodityId(commodityId);
        eventMapper.updateByPrimaryKeySelective(event1);
        //保存活动的主办方
        sponsorsModuleService.save(eventReq.eventId, eventReq.sponsors);
        //保存活动的嘉宾
        guestModuleService.save(eventReq.eventId, eventReq.guests);
        //保存活动的自定义表单
        customFormModuleService.save(eventReq.eventId, eventReq.additionalForm);
        //保存活动的标签
        tagModuleService.save(eventReq.eventId, eventReq.tags);
        //保存推荐关系
        eventCityRelationModule.save(eventReq.eventId, eventReq.rmdAndHotImportReq);
        resp.eventId = eventReq.eventId;
        return resp;
    }


    public Object saveCommonEvent(SaveEventImportReq saveEventReq) {

        return null;
    }


    @Transactional
    public void batchInsertFavorite(List<FavoriteImportReq> favoriteImportReqs) {
//        EventUidFavoriteRelationExample
        List<EventUidFavoriteRelation> list = new ArrayList<>();
        EventUidFavoriteRelation eventUidFavoriteRelation;
        Integer count = 0;
        for (FavoriteImportReq req : favoriteImportReqs) {
            eventUidFavoriteRelation = new EventUidFavoriteRelation();
            eventUidFavoriteRelation.setEventId(req.pid);
            eventUidFavoriteRelation.setUid(req.uid);
            eventUidFavoriteRelation.setCreateTime(DateUtil.timestampToDate(req.createTime));
            eventUidFavoriteRelation.setModifyTime(DateUtil.timestampToDate(req.modifyTime));
            logger.info("eventId:" + req.pid + ",uid:" + req.uid + ",createTime:" + req.createTime + ",modifyTime:" +
                    req.modifyTime);
            list.add(eventUidFavoriteRelation);
            count++;
        }
        logger.info("一共" + count + "条数据");
//        eventUidFavoriteRelationMapper.batchInsert(list);

    }


    @Transactional
    public Object importSaveCommonEvent(SaveEventImportReq saveEventReq) {
        EventPara eventReq = saveEventReq.event;
        eventReq.eventType = SqlEnum.EventType.TYPE_EVENT_COMMON.getByteValue();

        Integer count = orgInfoMapper.selectByNewId(saveEventReq.orgId);
        if (count == 0) {
            throw ExceptionUtil.createParamException("机构不存在");
        }
        EventExample eventExample22 = new EventExample();
        eventExample22.createCriteria().andOldEventIdEqualTo(eventReq.eventId).andTemplateIdEqualTo(1);

        List<Event> eventOld = eventMapper.selectByExample(eventExample22);
        if (!StringUtil.isEmpty(eventOld)) {
            throw ExceptionUtil.createParamException(eventOld.get(0).getId() + "活动已存在");
        }
        Event event = new Event();
        event.setTemplateId(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue());//普通活动
        event.setOrgId(saveEventReq.orgId);
        event.setOldEventId(eventReq.eventId);
        event.setPublishUid(saveEventReq.uid);
        event.setTitle(eventReq.title);
        event.setLineType(eventReq.lineType);
        event.setAddress(eventReq.address);
        event.setDeadlineTime(eventReq.deadlineTime);
        event.setStartTime(eventReq.startTime);
        event.setEndTime(eventReq.endTime);
        event.setLogo(eventReq.logo);
        event.setDetail(eventReq.detail);
        event.setOpenExtension(eventReq.isPromotion);
        event.setState(eventReq.state);
        event.setModifyTime(saveEventReq.modifyTime);
        event.setCreateTime(saveEventReq.createTime);
        if (eventReq.city != null) {
            event.setCityId(eventReq.city.id);
            if (StringUtil.isEmpty(eventReq.city.name)) {
                event.setCityName("");
            } else {
                event.setCityName(eventReq.city.name);
            }
        }
        //地址位置
        if (eventReq.loc != null) {
            event.setLongitude(eventReq.loc.lng);
            event.setLatitude(eventReq.loc.lat);
        }
        SaveEventResp resp = new SaveEventResp();
        //
        int num = eventMapper.insertSelective(event);
        if (!NumberUtil.isValidId(num)) {
            throw ExceptionUtil.createParamException("保存活动失败");
        }
        eventReq.eventId = event.getId();

        //给发布者加一个签到权限
        EventSignAuth eventSignAuth = new EventSignAuth();
        eventSignAuth.setEventId(event.getId());
        eventSignAuth.setUid(saveEventReq.uid);
        eventSignAuth.setStatus(SqlEnum.EventSignAuthStatus.AUTH.getByteValue());
        eventSignAuth.setCreateTime(DateUtil.getNowDate());
        eventSignAuth.setModifyTime(DateUtil.getNowDate());
        eventSignAuthMapper.insertSelective(eventSignAuth);


        //创建活动的url
        ExtensionLink extensionLink = extensionLinkService.createDefaultExtensionLinkChannelEventId(event.getId());
        if (extensionLink != null) {
            //方便前端处理，返回长链,加入pre代表预览，前端用来隐藏掉报名按钮
            resp.linkUrlCommon = cloudEventConfig.getLink_common_prefix() +
                    Base64Utils.encode(extensionLink.getUniqueKey().getBytes()).toString();
        }

        //保存活动的项目报名的设置
        stateCustomModuleService.save(event.getId(), eventReq.customModule);
        //创建商品
        eventReq.endTime = System.currentTimeMillis() + 60 * 1000 * 60l * 24 * 7;
        Integer commodityId = commodityRemoteService.CreateCommodity(eventReq);
        if (NumberUtil.isInValidId(commodityId)) {
            throw ExceptionUtil.createParamException("commodityId参数异常");
        }
        eventSolrService.synchronousEventToSolr(eventReq);
        //
        Event event1 = new Event();
        event1.setId(event.getId());
        event1.setCommodityId(commodityId);
        eventMapper.updateByPrimaryKeySelective(event1);

        //保存活动的主办方
        sponsorsModuleService.save(event.getId(), eventReq.sponsors);
        //保存活动的嘉宾
        guestModuleService.save(event.getId(), eventReq.guests);
        //保存活动的自定义表单
        customFormModuleService.save(event.getId(), eventReq.additionalForm);
        //保存推荐关系
        eventCityRelationModule.save(event.getId(), eventReq.rmdAndHotImportReq);
        resp.eventId = event.getId();
        return resp;

    }


    public Object importSaveCommonEventGuest(SaveEventGuestImportReq req) {
        if (StringUtil.isEmpty(req.eventParas)) {
            throw ExceptionUtil.createParamException("list为空");
        }
        for (EventPara eventPara : req.eventParas) {
            EventExample eventExample22 = new EventExample();
            eventExample22.createCriteria().andOldEventIdEqualTo(eventPara.eventId).andTemplateIdEqualTo(1);
            List<Event> eventOld = eventMapper.selectByExample(eventExample22);
            if (!StringUtil.isEmpty(eventOld)) {
                Event event = eventOld.get(0);
                EventGuestExample eventGuestExample = new EventGuestExample();
                eventGuestExample.createCriteria().andEventIdEqualTo(event.getId());
                long count = eventGuestMapper.countByExample(eventGuestExample);
                if (count > 0) {
                    continue;
                }
                //保存活动的嘉宾
                guestModuleService.save(event.getId(), eventPara.guests);
            }
        }
        return null;
    }

    private static boolean PROJECT_FEEDBACK_BACKUP2 = false;//是否正在同步

    /**
     * 清洗活动报名数量
     */

    public void cleanActiveJoinCount() {
        // 如果项目同步正在进行,继续上次的同步,放弃本次同步
        if (PROJECT_FEEDBACK_BACKUP2)
            return;
        try {
            // 分页数据,用于获取需要同步的项目ids
            int page = 1, size = 1000;
            do {
                // 如果同步没有开始,标记为开始
                if (!PROJECT_FEEDBACK_BACKUP2) {
                    PROJECT_FEEDBACK_BACKUP2 = true;
                }
                //
                EventExample activeExample = new EventExample();
                activeExample.setLimit(size);
                activeExample.setOffset((page - 1) * size);
                List<Event> actives = eventMapper.selectByExample(activeExample);
                // 如果没有查找到相应的数据,结束循环
                if (StringUtil.isEmpty(actives)) {
                    PROJECT_FEEDBACK_BACKUP2 = false;
                } else {
                    for (Event active : actives) {
                        clearByEvent(active);
                    }

                    if (actives.size() < size) {
                        // 如果当前页的数据小于分页的数量,表示没有下一页了,结束同步
                        PROJECT_FEEDBACK_BACKUP2 = false;
                    } else {
                        // 否则,同步下一页的数据
                        page++;
                    }
                }
            } while (PROJECT_FEEDBACK_BACKUP2);
        } catch (Exception e) {
            e.printStackTrace();
            PROJECT_FEEDBACK_BACKUP2 = false;
        }
    }


    public void cleanActiveJoinCountByNewEventId(EventListId eventPara) {
        EventExample eventExample = new EventExample();
        eventExample.createCriteria().andIdIn(eventPara.eventIds);
        List<Event> events = eventMapper.selectByExample(eventExample);
        if (!StringUtil.isEmpty(events)) {
            for (Event event : events) {
                clearByEvent(event);
            }
        }
    }


    private static boolean PROJECT_FEEDBACK_BACKUP3 = false;//是否正在同步


    public Object updateEventSolr() {
        // 如果项目同步正在进行,继续上次的同步,放弃本次同步
        if (PROJECT_FEEDBACK_BACKUP3)
            return null;
        try {
            // 分页数据,用于获取需要同步的项目ids
            int page = 1, size = 1000;
            do {
                // 如果同步没有开始,标记为开始
                if (!PROJECT_FEEDBACK_BACKUP3) {
                    PROJECT_FEEDBACK_BACKUP3 = true;
                }
                //
                EventExample activeExample = new EventExample();
                activeExample.setLimit(size);
                activeExample.setOffset((page - 1) * size);
                List<Event> actives = eventMapper.selectByExample(activeExample);
                // 如果没有查找到相应的数据,结束循环
                if (StringUtil.isEmpty(actives)) {
                    PROJECT_FEEDBACK_BACKUP3 = false;
                } else {
                    for (Event active : actives) {
                        cleanSolrByEvent(active);
                    }

                    if (actives.size() < size) {
                        // 如果当前页的数据小于分页的数量,表示没有下一页了,结束同步
                        PROJECT_FEEDBACK_BACKUP3 = false;
                    } else {
                        // 否则,同步下一页的数据
                        page++;
                    }
                }
            } while (PROJECT_FEEDBACK_BACKUP3);
        } catch (Exception e) {
            e.printStackTrace();
            PROJECT_FEEDBACK_BACKUP3 = false;
        }
        return null;
    }


    public void updateEventSolrByNewEventId(EventListId eventPara) {
        EventExample eventExample = new EventExample();
        eventExample.createCriteria().andIdIn(eventPara.eventIds);
        List<Event> events = eventMapper.selectByExample(eventExample);
        if (!StringUtil.isEmpty(events)) {
            for (Event event : events) {
                cleanSolrByEvent(event);
            }
        }
    }

    private void cleanSolrByEvent(Event event) {
        EventPara eventPara = eventService.conversePara(event, 1);

        List<EventSponsor> sponsorList = (List) sponsorsModuleService.get(event.getId());
        List<Sponsor> sponsorReqList = sponsorsModuleService.converseParaList(sponsorList);
        eventPara.sponsors = sponsorReqList;

        if (eventPara.eventType == 1 && eventPara.customModule.isCharge.equals(SqlEnum.CostType.TYPE_CHARGE_NOT
                .getValue())) {
            eventPara.customModule.ticketList = commodityRemoteService.converseToTicket(event
                    .getCommodityId());
        }

        eventSolrService.dataSynchronousEventToSolr(eventPara, event);
    }

    private void clearByEvent(Event active) {
        EventRecordExample eventRecordExample = new EventRecordExample();
        List<Byte> state = new ArrayList<>();
        state.add(SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getByteValue());
        state.add(SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY.getByteValue());
        eventRecordExample.createCriteria().andEventIdEqualTo(active.getId())
                .andStateIn(state).andTicketLockEqualTo(SqlEnum.TicketLockType.TYPE_NORMAL.getValue());
        long count = eventRecordMapper.countByExample(eventRecordExample);
        //如果是创业活动
        if (active.getTemplateId() == 1) {
            EventStateCustomExample eventStateCustomExample = new EventStateCustomExample();
            eventStateCustomExample.createCriteria().andEventIdEqualTo(active.getId());
            List<EventStateCustom> eventStateCustoms = eventStateCustomMapper.selectByExample
                    (eventStateCustomExample);
            if (!StringUtil.isEmpty(eventStateCustoms)) {
                EventStateCustom eventStateCustom = eventStateCustoms.get(0);

                EventStateCustom eventStateCustomNew = new EventStateCustom();
                eventStateCustomNew.setId(eventStateCustom.getId());
                eventStateCustomNew.setJoinedCount((int) count);
                eventStateCustomMapper.updateByPrimaryKeySelective(eventStateCustomNew);
            }
        } else if (active.getTemplateId() == 2 || active.getTemplateId() == 3) {
            EventStateProjectExample eventStateProjectExample = new EventStateProjectExample();
            eventStateProjectExample.createCriteria().andEventIdEqualTo(active.getId());
            List<EventStateProject> eventStateProjects = eventStateProjectMapper.selectByExample
                    (eventStateProjectExample);
            if (!StringUtil.isEmpty(eventStateProjects)) {
                EventStateProject eventStateProject = eventStateProjects.get(0);
                //如果是免费活动，一人只能有一张票
                if (active.getCostType() == 0) {
                    EventStateProject eventStateCustomNew = new EventStateProject();
                    eventStateCustomNew.setId(eventStateProject.getId());
                    eventStateCustomNew.setJoinedCount((int) count);
                    eventStateProjectMapper.updateByPrimaryKeySelective(eventStateCustomNew);
                } else {
                    EventTicketOrderExample eventTicketOrderExample = new EventTicketOrderExample();
                    eventTicketOrderExample.createCriteria().andEventIdEqualTo(eventStateProject.getEventId());
                    long count1 = eventTicketOrderMapper.countByExample(eventTicketOrderExample);

                    EventStateProject eventStateCustomNew = new EventStateProject();
                    eventStateCustomNew.setId(eventStateProject.getId());
                    eventStateCustomNew.setJoinedCount((int) count1);
                    eventStateProjectMapper.updateByPrimaryKeySelective(eventStateCustomNew);
                }
            }
        }
    }

}
