package com.welian.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.welian.beans.account.User;
import com.welian.beans.cloudevent.CityReq;
import com.welian.beans.cloudevent.EventPlacardReq;
import com.welian.beans.cloudevent.Sponsor;
import com.welian.beans.cloudevent.Ticket;
import com.welian.beans.cloudevent.TicketInfo;
import com.welian.beans.cloudevent.app.AppReq;
import com.welian.beans.cloudevent.checkIn.CheckInResp;
import com.welian.beans.cloudevent.event.EventPara;
import com.welian.beans.cloudevent.event.UserEventSearchPara;
import com.welian.beans.cloudevent.user.UserResp;
import com.welian.beans.distribution.PlacardBean;
import com.welian.client.account.AccountWechatClient;
import com.welian.client.commodity.CommodityOrderClient;
import com.welian.client.distribution.PlacardClient;
import com.welian.commodity.beans.Goods;
import com.welian.commodity.beans.Liquidation;
import com.welian.commodity.beans.OrderDetailQuery;
import com.welian.config.CloudEventConfig;
import com.welian.enums.cloudevent.ParamEnum;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.mapper.EventMapper;
import com.welian.mapper.EventOrderMapper;
import com.welian.mapper.EventRecordMapper;
import com.welian.mapper.EventTicketOrderMapper;
import com.welian.mapper.ProjectBackupInfoMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventCityRelation;
import com.welian.pojo.EventExample;
import com.welian.pojo.EventOrder;
import com.welian.pojo.EventOrderExample;
import com.welian.pojo.EventRecord;
import com.welian.pojo.EventRecordExample;
import com.welian.pojo.EventStateCustom;
import com.welian.pojo.EventStateProject;
import com.welian.pojo.EventTicketOrder;
import com.welian.pojo.EventTicketOrderExample;
import com.welian.pojo.ExtensionLink;
import com.welian.pojo.ProjectBackupInfo;
import com.welian.pojo.ProjectBackupInfoExample;
import com.welian.service.impl.EventServiceImpl;
import com.welian.service.impl.OrgServiceImpl;
import com.welian.service.impl.SmsRemoteServiceImpl;
import com.welian.utils.DateUtil;
import com.welian.utils.ExceptionUtil;
import com.welian.utils.HttpTool;
import com.welian.utils.PagingUtil;
import org.sean.framework.bean.BaseResult;
import org.sean.framework.code.ResultCodes;
import org.sean.framework.exception.CodeI18NException;
import org.sean.framework.service.RedisService;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import org.sean.framework.web.RequestHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by zhaopu on 2018/7/4.
 */
@Service
public class V1EventService {

    Logger logger = LoggerFactory.getLogger(V1EventService.class);
    @Autowired
    private CloudEventConfig cloudEventConfig;

    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private EventCityRelationModule eventCityRelationModule;
    @Autowired
    private CommodityRemoteService commodityRemoteService;
    @Autowired
    private SolrService solrService;
    @Autowired
    private EventRecordMapper eventRecordMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private EventTicketOrderMapper eventTicketOrderMapper;
    @Autowired
    private ProjectBackupInfoMapper projectBackupInfoMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private PlacardClient placardClient;
    @Autowired
    private AccountWechatClient accountWechatClient;
    @Value("${cloudevent.node.server}")
    private String nodeServer;

    @Autowired
    private CommodityOrderClient commodityOrderClient;
    @Autowired
    private EventOrderMapper eventOrderMapper;
    @Autowired
    private OrgServiceImpl orgService;
    @Autowired
    private SmsRemoteServiceImpl smsRemoteService;

    /**
     * 获取小程序活动列表
     *
     * @param req
     * @return
     */
    public Object getV1EventList(AppReq req) {

        EventPara eventParaR = new EventPara();
        eventParaR.list = new ArrayList<>();

        UserEventSearchPara eventSearchReq = new UserEventSearchPara();
        eventSearchReq.page = PagingUtil.getStart(req.page, req.size);
        eventSearchReq.size = req.size;
        if (req.cityId == null) {
            req.cityId = -100001; //所有活动
        }
        eventSearchReq.cityId = req.cityId;
        eventSearchReq.eventType = req.eventType;
        if (req.date != null) {
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
        }
        eventSearchReq.nowTime = System.currentTimeMillis();
        List<Event> eventList = eventMapper.selectEventMiniList(eventSearchReq);

        List<Integer> commodityIds = new ArrayList<>();
        List<Integer> eventIds = new ArrayList();
        for (Event event : eventList) {
            eventIds.add(event.getId());
            commodityIds.add(event.getCommodityId());
        }
        List<EventCityRelation> hotEvents = eventCityRelationModule.getHotOrRecommendEvents(ParamEnum.HotOrRcmType
                .TYPE_HOT, eventIds);
        List<EventCityRelation> recommendEvents = eventCityRelationModule.getHotOrRecommendEvents(ParamEnum
                .HotOrRcmType.TYPE_RECOMMENT, eventIds);

        if (eventList != null && eventList.size() > 0) {
            if (eventIds == null || eventIds.size() == 0) {
                return eventParaR;
            }

            Map<Integer, List<Ticket>> ticketsMap = commodityRemoteService.getTicketsMap(commodityIds);
            Map<Integer, List<Sponsor>> sponsorMap = eventService.selectBatchSponsor(eventIds);
            Map<Integer, EventRecord> recordsMap = eventService.selectEventRecord(eventIds, req.uid);
            Map<Integer, Boolean> eventFavoriteMap = eventService.selectEventFavorite(eventIds, req.uid);
            Map<Integer, EventStateCustom> eventStateCustomMap = eventService.selectBatchEventStateCustom(eventIds);
            Map<Integer, EventStateProject> eventStateProjectMap = eventService.selectBatchEventStateProject(eventIds);
            for (Event event : eventList) {
                EventPara eventPara = converseParaSimple(event);
                eventPara = eventService.getAppBasicEventPara(eventPara, recordsMap, event, eventFavoriteMap,
                        eventStateProjectMap, eventStateCustomMap,
                        null, sponsorMap, hotEvents,
                        recommendEvents, ticketsMap,req.cityId, 1);
                eventPara.customModule = null;
                eventPara.projectModule = null;
                eventPara.startTimeStr = DateUtil.getWeekAndDate(eventPara.startTime);
                eventParaR.list.add(eventPara);
            }
        }
        Long startTime=redisService.getCurrentTime();
        eventParaR.count=eventMapper.countEventMiniList(eventSearchReq);
        logger.info("count用时{}毫秒",redisService.getCurrentTime()-startTime);
        return eventParaR;
    }

    /**
     * 小程序搜索活动列表
     *
     * @param req
     * @return
     */
    public Object getV1EventSearchList(AppReq req) {
        EventPara eventParaR = new EventPara();
        eventParaR.list = new ArrayList<>();

        JSONObject result = solrService.pageActive(req.content, req.page, req.size);
        if (result == null) {
            return eventParaR;
        }
        JSONArray tmp = result.getJSONArray("data");

        List<Integer> eventIds = new ArrayList();
        List<Integer> commodityIds = new ArrayList<>();
        for (int i = 0; i < tmp.size(); i++) {
            JSONObject dataTmp = tmp.getJSONObject(i);
            if (dataTmp.containsKey("id")) {
                Integer eventId = dataTmp.getInteger("id");
                eventIds.add(eventId);
            }
        }

        EventExample example = new EventExample();
        example.createCriteria().andIdIn(eventIds);
        example.setOrderByClause("start_time desc");
        List<Event> eventList = eventMapper.selectByExample(example);

        List<EventCityRelation> hotEvents = eventCityRelationModule.getHotOrRecommendEvents(ParamEnum.HotOrRcmType
                .TYPE_HOT, eventIds);
        List<EventCityRelation> recommendEvents = eventCityRelationModule.getHotOrRecommendEvents(ParamEnum
                .HotOrRcmType.TYPE_RECOMMENT, eventIds);

        if (eventList != null && eventList.size() > 0) {
            if (eventIds == null || eventIds.size() == 0) {
                return eventParaR;
            }

            for (Event event : eventList) {
                commodityIds.add(event.getCommodityId());
            }

            Map<Integer, List<Ticket>> ticketsMap = commodityRemoteService.getTicketsMap(commodityIds);
            Map<Integer, List<Sponsor>> sponsorMap = eventService.selectBatchSponsor(eventIds);
            Map<Integer, EventStateProject> eventStateProjectMap = eventService.selectBatchEventStateProject(eventIds);
            Map<Integer, EventRecord> recordsMap = eventService.selectEventRecord(eventIds, req.uid);
            Map<Integer, Boolean> eventFavoriteMap = eventService.selectEventFavorite(eventIds, req.uid);
            Map<Integer, EventStateCustom> eventStateCustomMap = eventService.selectBatchEventStateCustom(eventIds);
            for (Event event : eventList) {
                EventPara eventPara = converseParaSimple(event);
                eventPara = eventService.getAppBasicEventPara(eventPara, recordsMap, event, eventFavoriteMap,
                        eventStateProjectMap, eventStateCustomMap,
                        null, sponsorMap, hotEvents,
                        recommendEvents, ticketsMap, null,1);
                eventPara.startTimeStr = DateUtil.getWeekAndDate(eventPara.startTime);
                eventPara.customModule = null;
                eventPara.projectModule = null;
                eventParaR.list.add(eventPara);
            }
        }

        return eventParaR;
    }

    /**
     * 我的活动列表
     *
     * @param req
     * @return
     */
    public Object getV1MyEventList(AppReq req) {
        EventPara eventParaR = new EventPara();
        eventParaR.list = new ArrayList<>();

        UserEventSearchPara eventSearchReq = new UserEventSearchPara();
        eventSearchReq.uid = req.uid;
        eventSearchReq.page = PagingUtil.getStart(req.page, req.size);
        eventSearchReq.size = req.size;

        List<Integer> eventIdList = eventRecordMapper.selectUserSignUpEventList(eventSearchReq);

        if (eventIdList != null && eventIdList.size() > 0) {
            Map<Integer, Event> eventMap = eventService.selectBatchEvent(eventIdList);
            Map<Integer, EventStateCustom> eventStateCustomMap = eventService.selectBatchEventStateCustom(eventIdList);
            Map<Integer, List<Sponsor>> sponsorMap = eventService.selectBatchSponsor(eventIdList);
            Map<Integer, ExtensionLink> extensionLinkMap = eventService.selectBatchExtensionLink(eventIdList);
            Map<Integer, EventStateProject> eventStateProjectMap = eventService.selectBatchEventStateProject
                    (eventIdList);
            Map<Integer, Boolean> eventFavoriteMap = eventService.selectEventFavorite(eventIdList, req.uid);
            Map<Integer, EventRecord> recordsMap = eventService.selectEventRecord(eventIdList, req.uid);
            for (Integer eventId : eventIdList) {
                Event event = eventMap.get(eventId);
                EventPara eventR = converseParaSimple(event);
                eventR.eventId = eventId;
                eventR = eventService.getAppBasicEventPara(eventR, recordsMap, event, eventFavoriteMap,
                        eventStateProjectMap,
                        eventStateCustomMap,
                        extensionLinkMap, sponsorMap, null,
                        null, null, null,0);
                eventR.startTimeStr = DateUtil.getWeekAndDate(eventR.startTime);
                eventR.customModule = null;
                eventR.projectModule = null;
                eventParaR.list.add(eventR);
            }
        }


        return eventParaR;
    }

    /**
     * 我的票券列表
     *
     * @param req
     * @return
     */
    public Object getV1MyEventTickets(AppReq req) {
        CheckInResp resp = new CheckInResp();


        Event event = eventService.getEvent(req.eventId);
        if (event == null) {
            throw ExceptionUtil.createParamException("活动信息异常，请重试");
        }
        EventPara eventPara = new EventPara();
        eventPara.id = event.getId();
        eventPara.title = event.getTitle();
        eventPara.startTime = event.getStartTime();
        eventPara.startTimeStr = DateUtil.getWeekAndDate(eventPara.startTime);
        eventPara.eventType = event.getTemplateId().byteValue();
        eventPara.lineType = event.getLineType();
        if (event.getCityId() != 0) {
            eventPara.city = new CityReq();
            eventPara.city.id = event.getCityId();
            eventPara.city.name = event.getCityName();
        }
        //event
        resp.event = eventPara;

        //user
        User user = userService.getUserInfoById(req.uid);
        if (user == null) {
            throw ExceptionUtil.createParamException("用户信息异常，请重试");
        }
        UserResp userResp = new UserResp();
        userResp.uid = user.id;
        userResp.name = user.name;
        userResp.position = user.position;
        userResp.company = user.company;
        userResp.mobile = user.phone;
        userResp.company = user.company;
        userResp.avatar = user.avatar;
        resp.user = userResp;


        EventRecordExample eventRecordExample = new EventRecordExample();
        eventRecordExample.createCriteria().andEventIdEqualTo(req.eventId).andUidEqualTo(req.uid).
                andTicketLockEqualTo(SqlEnum.TicketLockType.TYPE_NORMAL.getValue());
        eventRecordExample.setOrderByClause("create_time desc");
        List<EventRecord> eventRecordList = eventRecordMapper.selectByExample(eventRecordExample);

        //ticket
        resp.ticketTypes = converseTicket(event, eventRecordList);


        return resp;
    }


    private List<Ticket> converseTicket(Event event, List<EventRecord> eventRecordList) {
        List<Ticket> ticketTypes = new ArrayList<>();

        Map<Integer,Ticket> ticketMap = new HashMap<>();
        List<Ticket> ticketModules = commodityRemoteService.converseToTicket(event.getCommodityId());
        if (ticketModules != null && ticketModules.size() > 0) {
            for (Ticket ticket : ticketModules) { //票种
                ticketMap.put(ticket.id,ticket);
             }


            for (EventRecord eventRecord : eventRecordList) { //报名记录

                EventTicketOrderExample eventTicketOrderExample = new EventTicketOrderExample();
                eventTicketOrderExample.createCriteria().andEventIdEqualTo(event.getId()).
                        andEventRecordIdEqualTo(eventRecord.getId());
                List<EventTicketOrder> eventTicketOrders = eventTicketOrderMapper.selectByExample
                        (eventTicketOrderExample);

                if (eventTicketOrders != null && eventTicketOrders.size() > 0) {
                    for (EventTicketOrder eventTicketOrder : eventTicketOrders) { //订单票
                        Ticket ticket = ticketMap.get(eventTicketOrder.getTicketId());
                        Ticket ticketR = new Ticket();
                        ticketR.intro = ticket.intro;
                        if (event.getTemplateId().byteValue() == SqlEnum.EventType.TYPE_EVENT_COMMON.getValue()) {
                            ticketR.title = ticket.title;
                        } else {
                            ProjectBackupInfoExample projectBackupInfoExample = new ProjectBackupInfoExample();
                            projectBackupInfoExample.createCriteria().andEventRecordIdEqualTo(eventRecord.getId());
                            List<ProjectBackupInfo> projectBackupInfos = projectBackupInfoMapper.selectByExample
                                    (projectBackupInfoExample);
                            ticketR.title = "";
                            if (projectBackupInfos != null && projectBackupInfos.size() > 0) {
                                ticketR.title = projectBackupInfos.get(0).getName();
                            }
                            ticketR.intro = "";
                        }

                        ticketR.price = ticket.price;
                        TicketInfo ticketInfo = new TicketInfo();
                        ticketInfo.id = eventTicketOrder.getCommodityDetailId();
                        ticketInfo.ticketNo = eventTicketOrder.getTicketNo();
                        ticketInfo.signTime = eventTicketOrder.getSignTime() == null ? 0 : eventTicketOrder
                                .getSignTime();
                        ticketInfo.tradeNo = eventRecord.getTradeNo();
                        ticketInfo.recordTime = eventRecord.getCreateTime();
                        ticketInfo.recordId = eventRecord.getId();
                        ticketInfo.sort = 1;
                        //状态 0.未签到 1.已签到  2.报名成功 3.审核中 4.审核失败
                        if (eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS
                                .getByteValue()) {
                            if (eventTicketOrder.getSignState() == SqlEnum.EventSignStatus.EVENT_SIGN_TRUE
                                    .getValue()) {
                                ticketInfo.state = eventTicketOrder.getSignState();
                            } else {
                                ticketInfo.state = 2;
                                ticketInfo.sort = 2;
                            }
                        } else if (eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY
                                .getByteValue()) {
                            ticketInfo.state = 3;
                        } else if (eventRecord.getState() == SqlEnum.EventRecordType.TYPE_EVENT_VERIFY_FAILED
                                .getByteValue()) {
                            ticketInfo.state = 4;
                        }
                        ticketR.ticketInfo = ticketInfo;
                        ticketTypes.add(ticketR);
                    }
                }
            }
        }

        Collections.sort(ticketTypes ,  new Comparator<Ticket>() {

            public int compare(Ticket o1, Ticket o2) {
                // 返回值为int类型，大于0表示正序，小于0表示逆序
                return o2.ticketInfo.sort - o1.ticketInfo.sort;
            }
        });


        return ticketTypes;
    }


    private EventPara converseParaSimple(Event event) {
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
            eventPara.deadlineTime = event.getDeadlineTime();
            eventPara.logo = cloudEventConfig.getImage_prefix() + event.getLogo();
            eventPara.eventId = event.getId();
            eventPara.id = event.getId();

            return eventPara;
        }
        return null;
    }

    public Object placardAddr(EventPlacardReq req) {
        long startTime = redisService.getCurrentTime();

        req.uid = NumberUtil.isValidId(req.uid) ? req.uid : RequestHolder.getUid();
        if (NumberUtil.isInValidId(req.uid)) {
            throw new CodeI18NException(ResultCodes.Code.COMMON_ERROR_PARAMS, "error_uid");
        }

        if (NumberUtil.isInValidId(req.eventId) || NumberUtil.isInValidId(req.placardId)) {
            throw new CodeI18NException(ResultCodes.Code.COMMON_ERROR_PARAMS);
        }

        Event event = Optional.ofNullable(eventService.getEvent(req.eventId)).orElseThrow(
                () -> new CodeI18NException(ResultCodes.Code.COMMON_ERROR_NOT_EXIST, "error_event_not_exist"));

        // distribution info
        BaseResult<PlacardBean> result = placardClient.get(req.placardId);
        if (!result.isSuccess()) {
            throw new CodeI18NException(ResultCodes.Code.COMMON_ERROR_BIZ, result.getErrormsg());
        }

        // 海报列表null
        if (result.getData() == null) {
            throw new CodeI18NException(ResultCodes.Code.COMMON_ERROR_NOT_EXIST, "error_placard_info_not_exist");
        }

        PlacardBean placardBean = result.getData();

        if (StringUtil.isEmpty(placardBean.configs)) {
            throw new CodeI18NException(ResultCodes.Code.COMMON_ERROR_NOT_EXIST, "error_placard_config_not_exist");
        }

        // 用户
        User user = userService.getUserInfoById(req.uid);

        Map<String, Object> retMap = new HashMap<>();
        retMap.put("background", placardBean.background);

        JSONArray array = new JSONArray();
        placardBean.configs.forEach(placardConfigBean -> {
            JSONObject item = new JSONObject();
            item.put("key", placardConfigBean.key);
            item.put("value", placardConfigBean.value == null ? null : JSONObject.parseObject(placardConfigBean.value));
            switch (placardConfigBean.key) {
                case "name":
                    item.put("content", user.profile.name);
                    break;
                case "title":
                    item.put("content", event.getTitle());
                    break;
                case "datetime":
                    SimpleDateFormat format = new SimpleDateFormat(" yyyy-MM-dd HH:mm ");
                    String d = format.format(event.getStartTime());
                    item.put("content", d);
                    break;
                case "address":
                    item.put("content", event.getAddress());
                    break;
                case "qrcode":
                    BaseResult<String> atResult = accountWechatClient.getAccesstoken();
                    if (!atResult.isSuccess()) {
                        throw new CodeI18NException(ResultCodes.Code.COMMON_ERROR_BIZ, atResult.getErrormsg());
                    }
                    String welianAddress = cloudEventConfig.isRelease() ? cloudEventConfig.getWelian_address() :
                            cloudEventConfig.getTest_welian_address();
                    String welianH5Address = cloudEventConfig.isRelease() ? cloudEventConfig.getWelian_h5_address() :
                            cloudEventConfig.getTest_welian_h5_address();
                    String url = welianAddress + "index/qrcode?url=" + URLEncoder.encode((welianH5Address +
                            "event/i?id=" + event.getId()).toString()+"&from=poster");
                    item.put("content", url);
                    break;
                default:
                    logger.info("config key not fund...");
            }
            array.add(item);
        });
        retMap.put("configs", array);

        JSONObject param = JSONObject.parseObject(JSONObject.toJSONString(retMap));
        logger.info("request node server, url={}, param={}", nodeServer, param);

        long requestNodeTime = redisService.getCurrentTime();
        String nodeResult = HttpTool.doPost(nodeServer, param);
        logger.info("组装数据用时：time={}ms, 画海报的用时：time={}ms",
                (requestNodeTime - startTime),
                (redisService.getCurrentTime() - requestNodeTime));

        if (StringUtil.isEmpty(nodeResult)) {
            throw new CodeI18NException(ResultCodes.Code.COMMON_ERROR_NET, "error_node_server");
        }
        JSONObject object = JSONObject.parseObject(nodeResult);
        JSONObject urlJson = object.getJSONObject("data");
        if (StringUtil.isEmpty(urlJson)) {
            throw new CodeI18NException(ResultCodes.Code.COMMON_ERROR_NET, object.getString("errorMsg"));
        }
        return urlJson.getString("url");
    }

    /**
     * 活动自动结算
     *
     * @param zeroTime != null 当天0零点
     * @return
     */
    public Object authSettlement(Long zeroTime) {

        if (zeroTime == null) {
            //前一天0点的时间
            zeroTime = DateUtil.getPassDayBeginTime(-1);
        }

        //查询前一天结束的活动
        List<Event> eventList = eventMapper.selectYesterdayEndEvent(zeroTime, zeroTime + 86400000);
        if (eventList == null || eventList.size() == 0) {
            return null;
        }
        List commodityIds = new ArrayList();
        for (Event event : eventList) {
            commodityIds.add(event.getCommodityId());
        }
        logger.info("活动自动结算查询结束活动商品列表    " + commodityIds.toString());
        //手动对账
        OrderDetailQuery query = new OrderDetailQuery();
        query.commodityIds = commodityIds;
        BaseResult<Object> baseResult = commodityOrderClient.manualReconciliation(query);
        if(!baseResult.isSuccess()){
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }


        //看本地是否有结算记录
        Map<Integer, String> orderMap = new HashMap<>();
        EventOrderExample eventOrderEm = new EventOrderExample();
        eventOrderEm.createCriteria().andCommodityIdIn(commodityIds);
        List<EventOrder> eventOrders = eventOrderMapper.selectByExample(eventOrderEm);
        for (EventOrder eventOrder : eventOrders) {
            if (eventOrder.getBatchnum() != null) {
                orderMap.put(eventOrder.getCommodityId(), eventOrder.getBatchnum());
            }
        }

        //结算
        for (Event event : eventList) {
            User user =  orgService.getAdminUser(event.getOrgId());
            if (user == null || user.id == null) {
                continue;
            }
            List<String> phoneList = new ArrayList<>();
            phoneList.add(user.phone);
            String eventTitle = "";
            if(event.getTitle().length() > 10){
                eventTitle = event.getTitle().substring(0,10) + "...";
            }else{
                eventTitle = event.getTitle();
            }

            Goods goods = commodityRemoteService.getCommodityGood(event.getCommodityId());
            if (goods.income <= 100) {
                smsRemoteService.sendSMS(phoneList,user.name + "," + "「" + eventTitle + "」" + "活动结算异常，请登录open.welian.com进行手动结算或联系客服");
                continue;
            }
            boolean contains = orderMap.containsKey(event.getCommodityId());
            String batchnum = "";
            if (contains) {
                //有批次号
                Liquidation liquidation = commodityRemoteService.getLiquidations(orderMap.get(event.getCommodityId()));
                if (liquidation == null) {
                    BaseResult<String> settlementResult = commodityRemoteService.settlementapply(event.getCommodityId(), user.id, user.id,
                            goods.income , "自动结算");
                    if (!settlementResult.isSuccess()) {
                        smsRemoteService.sendSMS(phoneList,user.name + "," + "「" + eventTitle + "」" + "活动结算异常，请登录open.welian.com进行手动结算或联系客服");
                        continue;
                    }
                    batchnum = settlementResult.getData();
                    smsRemoteService.sendSMS(phoneList,user.name + "," + "「" + eventTitle + "」" + "活动已提交结算，将在4个工作日内打入你的微链钱包");
                }
            } else {
                BaseResult<String> settlementResult  = commodityRemoteService.settlementapply(event.getCommodityId(), user.id, user.id,
                        goods.income , "自动结算");
                if (!settlementResult.isSuccess()) {
                    smsRemoteService.sendSMS(phoneList,user.name + "," + "「" + eventTitle + "」" + "活动结算异常，请登录open.welian.com进行手动结算或联系客服");
                    continue;
                }
                batchnum = settlementResult.getData();
                smsRemoteService.sendSMS(phoneList,user.name + "," + "「" + eventTitle + "」" + "活动已提交结算，将在4个工作日内打入你的微链钱包");
            }

            if (batchnum.length() > 0) {
                EventOrder eventOrder = new EventOrder();
                eventOrder.setBatchnum(batchnum);
                eventOrder.setEventId(event.getId());
                eventOrder.setCommodityId(event.getCommodityId());
                eventOrder.setCreateTime(System.currentTimeMillis());
                eventOrder.setModifyTime(System.currentTimeMillis());
                eventOrderMapper.insertSelective(eventOrder);
            }
        }
        return null;
    }
}