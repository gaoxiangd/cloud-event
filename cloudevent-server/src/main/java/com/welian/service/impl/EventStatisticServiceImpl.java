package com.welian.service.impl;

import com.welian.beans.cloudevent.ExtensionLinkReq;
import com.welian.beans.cloudevent.query.EventQuery;
import com.welian.beans.cloudevent.record.EventRecorderCity;
import com.welian.beans.cloudevent.record.EventRecorderPosition;
import com.welian.beans.cloudevent.statistical.ChannelStatisticsLinkBeanResp;
import com.welian.beans.cloudevent.statistical.ChannelStatisticsListBeanResp;
import com.welian.beans.cloudevent.statistical.ChannelStatisticsResultResp;
import com.welian.beans.cloudevent.statistical.ProjectReceivePerChannelOneDayResp;
import com.welian.beans.cloudevent.statistical.ProjectReceivePerChannelOneDayResultRes;
import com.welian.beans.cloudevent.statistical.StatisticParamResp;
import com.welian.beans.cloudevent.statistical.StatisticReq;
import com.welian.beans.cloudevent.statistical.StatisticResp;
import com.welian.beans.cloudevent.statistical.StatisticsNumber;
import com.welian.config.Constant;
import com.welian.enums.cloudevent.ParamEnum;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.mapper.EventMapper;
import com.welian.mapper.EventRecordMapper;
import com.welian.mapper.EventRecordUserMapper;
import com.welian.mapper.EventTicketOrderMapper;
import com.welian.mapper.ExtensionLinkMapper;
import com.welian.mapper.OrgInfoMapper;
import com.welian.mapper.ProjectReceivePerActivityOneDayMapper;
import com.welian.mapper.ProjectReceivePerLinkOneDayMapper;
import com.welian.mapper.ProjectReceivePerOrgOneDayMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventRecord;
import com.welian.pojo.EventRecordUser;
import com.welian.pojo.EventRecordUserExample;
import com.welian.pojo.EventStateCustom;
import com.welian.pojo.EventStateProject;
import com.welian.pojo.EventTicketOrder;
import com.welian.pojo.EventTicketOrderExample;
import com.welian.pojo.ExtensionLink;
import com.welian.pojo.OrgInfo;
import com.welian.pojo.OrgInfoExample;
import com.welian.pojo.ProjectReceivePerActivityOneDay;
import com.welian.pojo.ProjectReceivePerActivityOneDayExample;
import com.welian.pojo.ProjectReceivePerLinkOneDay;
import com.welian.pojo.ProjectReceivePerLinkOneDayExample;
import com.welian.pojo.ProjectReceivePerOrgOneDay;
import com.welian.pojo.ProjectReceivePerOrgOneDayExample;
import com.welian.service.record.impl.IEventRecordProjectInfoImpl;
import com.welian.service.task.ScheduledTask;
import org.sean.framework.util.JSONUtil;
import org.sean.framework.util.Logger;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import com.welian.utils.DateUtil;
import com.welian.utils.ExceptionUtil;
import com.welian.utils.MobileUtil;
import com.welian.utils.PositionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaopu on 2017/7/3.
 */
@Service("eventStatisticService")
public class EventStatisticServiceImpl{
    private static final Logger logger = Logger.newInstance(EventStatisticServiceImpl.class);

    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private StateCustomModuleService stateCustomModuleService;
    @Autowired
    private ProjectReceivePerOrgOneDayMapper projectReceivePerOrgOneDayMapper;
    @Autowired
    private ProjectReceivePerActivityOneDayMapper projectReceivePerActivityOneDayMapper;
    @Autowired
    private ProjectReceivePerLinkOneDayMapper projectReceivePerLinkOneDayMapper;
    @Autowired
    private EventRecordMapper eventRecordMapper;
    @Autowired
    private EventRecordUserMapper eventRecordUserMapper;
    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private ExtensionLinkServiceImpl extensionLinkService;
    @Autowired
    private StateProjectModuleServiceImpl stateProjectModuleService;
    @Autowired
    private IEventRecordProjectInfoImpl iEventRecordProjectInfo;
    @Autowired
    private ExtensionLinkMapper extensionLinkMapper;
    @Autowired
    private OrgInfoMapper orgInfoMapper;
    @Autowired
    private ScheduledTask scheduledTask;
    @Autowired
    private EventTicketOrderMapper eventTicketOrderMapper;
    @Autowired
    private EventRecordServiceImpl eventRecordService;
    /**
     * 后台首页-顶部数量统计
     */

    public Object getOrgStatisticTotal(StatisticReq req) {
        StatisticResp resp = new StatisticResp();
        //累计创建活动数
        resp.eventTotal = (int) eventService.getAllEventCountByOrgId(req.orgId);
        //累计报名人数,3.4.0版本后改为已售票数
        EventQuery query =new EventQuery();
        query.orgId=req.orgId;
        List<EventRecord> eventRecords=eventRecordService.getValidRecords(query);
        resp.signUpTotal=getCountByExample(eventRecords);

        //今日报名人数,3.4.0版本后改为今日已售票数
        Long time = System.currentTimeMillis();
        query.startTime=DateUtil.convert2long(DateUtil.convert2String(time, DateUtil.DATE_FORMAT), DateUtil.DATE_FORMAT);
        query.endTime=time;
        eventRecords=eventRecordService.getValidRecords(query);
        resp.signUpToday =getCountByExample(eventRecords);

        //昨日报名人数,3.4.0版本后改为昨日已售票数
        Long timeYesterday = DateUtil.convert2long(DateUtil.convert2String
                (time, DateUtil.DATE_FORMAT), DateUtil.DATE_FORMAT);
        query.startTime=timeYesterday - Constant.DAY_LONG;
        query.endTime=timeYesterday - 1;
        eventRecords=eventRecordService.getValidRecords(query);
        resp.signUpYesterday = getCountByExample(eventRecords);

        //前日报名人数,3.4.0版本后改为前日已售票数
        query.startTime=timeYesterday - 2 * Constant.DAY_LONG;
        query.endTime=timeYesterday - Constant.DAY_LONG - 1l;
        eventRecords=eventRecordService.getValidRecords(query);

        long signUpBeforeYesterday =  getCountByExample(eventRecords);
        //今天的报名数量与昨日的报名数量的对比
        if (resp.signUpToday - resp.signUpYesterday == 0) {
            resp.todayState = ParamEnum.ProjectCountReceiveIncreaseState.TYPE_EQUAL.getValue();
        } else if (resp.signUpToday - resp.signUpYesterday > 0) {
            resp.todayState = ParamEnum.ProjectCountReceiveIncreaseState.TYPE_INCREASE.getValue();
        } else {
            resp.todayState = ParamEnum.ProjectCountReceiveIncreaseState.TYPE_DROP.getValue();
        }
        //昨日的报名数量与前天的报名数量的对比
        if (resp.signUpYesterday - signUpBeforeYesterday == 0) {
            resp.yesterdayState = ParamEnum.ProjectCountReceiveIncreaseState.TYPE_EQUAL.getValue();
        } else if (resp.signUpYesterday - signUpBeforeYesterday > 0) {
            resp.yesterdayState = ParamEnum.ProjectCountReceiveIncreaseState.TYPE_INCREASE.getValue();
        } else {
            resp.yesterdayState = ParamEnum.ProjectCountReceiveIncreaseState.TYPE_DROP.getValue();
        }

        return resp;
    }

    /**
     * 根据example里的条件拿到总数
     * @param eventRecords
     * @return
     */
    private Integer getCountByExample(List<EventRecord> eventRecords) {
        if(StringUtil.isEmpty(eventRecords)){
            return 0;
        }
        List<Integer> recordIds=new ArrayList<>();
        for(EventRecord eventRecord:eventRecords){
            recordIds.add(eventRecord.getId());
        }
        EventTicketOrderExample ticketOrderExample=new EventTicketOrderExample();
        ticketOrderExample.createCriteria().andEventRecordIdIn(recordIds);
        return (int)eventTicketOrderMapper.countByExample(ticketOrderExample);
    }


    /**
     * 报名图表统计
     */

    public Object getSignUpStatistic(StatisticReq req) {
        ProjectReceivePerChannelOneDayResultRes resultRes = new ProjectReceivePerChannelOneDayResultRes();
        Long nowTime = System.currentTimeMillis();
        Long endTime = nowTime - req.days * Constant.DAY_LONG;
        if (NumberUtil.isValidId(req.eventId)) {
            ProjectReceivePerActivityOneDayExample projectReceivePerActivityOneDayExample = new
                    ProjectReceivePerActivityOneDayExample();
            projectReceivePerActivityOneDayExample.createCriteria().andEventIdEqualTo(req.eventId).andDateBetween
                    (endTime, nowTime);
            projectReceivePerActivityOneDayExample.setOrderByClause("date asc");
            List<ProjectReceivePerActivityOneDay> projectReceivePerActivityOneDays =
                    projectReceivePerActivityOneDayMapper.selectByExample(projectReceivePerActivityOneDayExample);
            if (projectReceivePerActivityOneDays == null) {
                projectReceivePerActivityOneDays = new ArrayList<>();
            }
            List<Long> times = getTime(req);
            if (times == null || times.isEmpty()) {
                return null;
            }
            List<ProjectReceivePerChannelOneDayResp> perChannelOneDayResps = new ArrayList<>();
            ProjectReceivePerChannelOneDayResp oneDayResp;
            for (Long time : times) {
                oneDayResp = new ProjectReceivePerChannelOneDayResp();
                oneDayResp.time = DateUtil.convert2String(time, DateUtil.DATE_FORMAT);
                oneDayResp.week = DateUtil.getWeekOfDate(new Date(time));
                oneDayResp.count = 0;
                oneDayResp.ticketCount = 0;
                for (ProjectReceivePerActivityOneDay oneDay : projectReceivePerActivityOneDays) {
                    if (time.longValue() == oneDay.getDate()) {
                        oneDayResp.count = oneDay.getProjectReceiveCount();
                        oneDayResp.ticketCount = oneDay.getTicketReceiveCount();
                    }
                }
                perChannelOneDayResps.add(oneDayResp);
            }
            resultRes.list = perChannelOneDayResps;
        } else {
            ProjectReceivePerOrgOneDayExample projectReceivePerChannelOneDayExample = new
                    ProjectReceivePerOrgOneDayExample();
            projectReceivePerChannelOneDayExample.createCriteria().andOrgIdEqualTo(req.orgId).andDateBetween(endTime,
                    nowTime);
            projectReceivePerChannelOneDayExample.setOrderByClause("date asc");
            List<ProjectReceivePerOrgOneDay> projectReceivePerChannelOneDays = projectReceivePerOrgOneDayMapper
                    .selectByExample(projectReceivePerChannelOneDayExample);
            if (projectReceivePerChannelOneDays == null) {
                projectReceivePerChannelOneDays = new ArrayList<>();
            }
            List<Long> times = getTime(req);
            if (times == null || times.isEmpty()) {
                return null;
            }
            List<ProjectReceivePerChannelOneDayResp> perChannelOneDayResps = new
                    ArrayList<ProjectReceivePerChannelOneDayResp>();
            ProjectReceivePerChannelOneDayResp oneDayResp;
            for (Long time : times) {
                oneDayResp = new ProjectReceivePerChannelOneDayResp();
                oneDayResp.time = DateUtil.convert2String(time, DateUtil.DATE_FORMAT);
                oneDayResp.week = DateUtil.getWeekOfDate(new Date(time));
                oneDayResp.count = 0;
                oneDayResp.ticketCount = 0;
                for (ProjectReceivePerOrgOneDay oneDay : projectReceivePerChannelOneDays) {
                    if (time.longValue() == oneDay.getDate()) {
                        oneDayResp.count = oneDay.getProjectReceiveCount();
                        oneDayResp.ticketCount = oneDay.getTicketReceiveCount();
                    }
                }
                perChannelOneDayResps.add(oneDayResp);
            }
            resultRes.list = perChannelOneDayResps;
        }
        return resultRes;
    }


    /**
     * 报名人员分布情况统计
     */

    public Object getAreaDistribution(StatisticReq req) {
        StatisticResp resp = new StatisticResp();
        resp.cityList = new ArrayList<>();
        resp.industryList = new ArrayList<>();
        //查询所有报名记录
        List<EventRecord> eventRecordList = iEventRecordProjectInfo.
                getRecordListByEventIdAndLevel(req.eventId,
                        0, 0, null,
                        ParamEnum.ProjectRecordListGetType.TYPE_AUDITED_AND_WAIT_VERIFY_FAILED);
        if (StringUtil.isEmpty(eventRecordList)) {
            return resp;
        }
        List<Integer> records = new ArrayList<>();
        for (EventRecord eventRecord : eventRecordList) {
            records.add(eventRecord.getId());
        }
        EventRecordUserExample eventRecordUserExample = new EventRecordUserExample();
        eventRecordUserExample.createCriteria().andEventRecordIdIn(records);
        List<EventRecordUser> eventRecordUsers = eventRecordUserMapper.selectByExample(eventRecordUserExample);
        if (StringUtil.isEmpty(eventRecordUsers)) {
            return resp;
        }
        Map<String, Integer> cityMap = new HashMap<>();
        Map<String, Integer> positionMap = new HashMap<>();

        for (EventRecordUser eventRecordUser : eventRecordUsers) {
            String position = eventRecordUser.getPosition();
            //针对预注册账号的Profile表中记录的 cityid不准的情况，取报名记录中的手机号归属地进行分析
            String cityName = MobileUtil.getCity(eventRecordUser.getPhone());
            if (StringUtil.isEmpty(cityName)) {
                cityName = "其他";
            }
            //城市计数
            if (cityMap.containsKey(cityName)) {
                cityMap.put(cityName, cityMap.get(cityName) + 1);
            } else {
                cityMap.put(cityName, 1);
            }
            //职位计数
            String p = PositionUtil.getFaceMark(position, position.trim().toUpperCase());
            if (StringUtil.isEmpty(p)) {
                p = "其他";
            }
            if (positionMap.containsKey(p)) {
                positionMap.put(p, positionMap.get(p) + 1);
            } else {
                positionMap.put(p, 1);
            }
        }

        List<EventRecorderCity> eventRecorderCities = new ArrayList<>();
        for (String key : cityMap.keySet()) {
            EventRecorderCity eventRecorderCity = new EventRecorderCity();
            eventRecorderCity.cityName = key;
            eventRecorderCity.count = cityMap.get(key);
            eventRecorderCities.add(eventRecorderCity);
        }
        eventRecorderCities.sort(new EventRecorderCitySort());

        int otherSize = 0;
        for (EventRecorderCity eventRecorderCity : eventRecorderCities) {
            if (resp.cityList.size() < 4 && !"其他".equals(eventRecorderCity.cityName)) {
                StatisticParamResp cityResp = new StatisticParamResp();
                cityResp.name = eventRecorderCity.cityName;
                cityResp.count = eventRecorderCity.count;
                resp.cityList.add(cityResp);
            } else {
                otherSize += eventRecorderCity.count;
            }
        }
        if (otherSize > 0) {
            StatisticParamResp cityResp = new StatisticParamResp();
            cityResp.name = "其他";
            cityResp.count = otherSize;
            resp.cityList.add(cityResp);
        }

        List<EventRecorderPosition> eventRecorderPositions = new ArrayList<>();
        for (String key : positionMap.keySet()) {
            EventRecorderPosition eventRecorderPosition = new EventRecorderPosition();
            eventRecorderPosition.position = key;
            eventRecorderPosition.count = positionMap.get(key);
            eventRecorderPositions.add(eventRecorderPosition);
        }
        eventRecorderPositions.sort(new EventRecorderPositionSort());
        logger.info("用户职位列表" + JSONUtil.obj2Json(eventRecorderPositions));

        otherSize = 0;
        //职位分析取前6,其他的算作其他
        for (EventRecorderPosition eventRecorderPosition : eventRecorderPositions) {
            if (resp.industryList.size() < 6 && !"其他".equals(eventRecorderPosition.position)) {
                StatisticParamResp indusrtyResp = new StatisticParamResp();
                indusrtyResp.name = eventRecorderPosition.position;
                indusrtyResp.count = eventRecorderPosition.count;
                resp.industryList.add(indusrtyResp);
            } else {
                otherSize += eventRecorderPosition.count;
            }
        }
        if (otherSize > 0) {
            StatisticParamResp indusrtyResp = new StatisticParamResp();
            indusrtyResp.name = "其他";
            indusrtyResp.count = otherSize;
            resp.industryList.add(indusrtyResp);
        }
        return resp;
    }


    /**
     * 单个活动的概况信息
     */

    public Object getSingleEvent(StatisticReq req) {
        StatisticResp resp = new StatisticResp();
        Event event = eventMapper.selectByPrimaryKey(req.eventId);
        if (event == null || event.getState() == SqlEnum.EventStateType.TYPE_EVENT_DELETED.getValue().intValue()) {
            throw ExceptionUtil.createParamException("活动不存在或者已删除");
        }
        //此活动详情页面浏览数
        resp.detailViewCount = event.getDetailBrowseCount();
        //此活动表单页面浏览数
        resp.formViewCount = event.getFormBrowseCount();
        //此活动总报名数
        if (event.getTemplateId() == SqlEnum.EventType.TYPE_EVENT_COMMON.getValue()) { //创业活动
            EventStateCustom eventStateCustom = stateCustomModuleService.get(event.getId());
            resp.signUpTotal = eventStateCustom.getJoinedCount();
        } else {
            EventStateProject eventStateProject = stateProjectModuleService.get(event.getId());
            resp.signUpTotal = eventStateProject.getJoinedCount();
        }
        EventQuery query=new EventQuery();
        query.eventId=req.eventId;
        List<Integer> recordIds=eventRecordService.getValidRecordIds(query);
        Integer todaySell=0;
        Integer yesterdaySell=0;
        //此活动今日报名数，3.4.0以后统计event_ticket_order表数据来统计(考虑到免费活动还有退款或取消流程，过滤报名不是成功或待审核的报名记录)
        Long time = System.currentTimeMillis();
        EventTicketOrderExample example = new EventTicketOrderExample();
        EventTicketOrderExample.Criteria criteria=example.createCriteria();
        criteria.andEventIdEqualTo(req.eventId).andTicketStateEqualTo(SqlEnum.EventTicketState
                .EVENT_TICKET_FALSE.getValue()).andCreateTimeBetween(DateUtil.convert2long(DateUtil.convert2String
                (time, DateUtil.DATE_FORMAT), DateUtil.DATE_FORMAT), time);
        if(!StringUtil.isEmpty(recordIds)){
            criteria.andEventRecordIdIn(recordIds);
            todaySell=(int) eventTicketOrderMapper.countByExample(example);
        }
        resp.signUpToday = todaySell;

        //此活动昨日报名数,3.4.0以后统计event_ticket_order表数据来统计
        Long timeYesterday = DateUtil.convert2long(DateUtil.convert2String
                (time, DateUtil.DATE_FORMAT), DateUtil.DATE_FORMAT);
        EventTicketOrderExample eventOrder = new EventTicketOrderExample();
        criteria=eventOrder.createCriteria();
        criteria.andEventIdEqualTo(req.eventId).andCreateTimeBetween(timeYesterday - Constant
                .DAY_LONG, timeYesterday - 1).andTicketStateEqualTo(SqlEnum.EventTicketState.EVENT_TICKET_FALSE
                .getValue());
        if(!StringUtil.isEmpty(recordIds)){
            criteria.andEventRecordIdIn(recordIds);
            yesterdaySell=(int) eventTicketOrderMapper.countByExample(eventOrder);
        }
        resp.signUpYesterday = yesterdaySell;
        return resp;
    }




    class EventRecorderCitySort implements Comparator<EventRecorderCity> {

        public int compare(EventRecorderCity activeRecorderCity, EventRecorderCity activeRecorderCity2) {
            if (activeRecorderCity.count > activeRecorderCity2.count) {
                return -1;
            } else if ((activeRecorderCity.count == activeRecorderCity2.count)) {
                return 0;
            } else {
                return 1;
            }
        }
    }


    class EventRecorderPositionSort implements Comparator<EventRecorderPosition> {

        public int compare(EventRecorderPosition eventRecorderPosition1, EventRecorderPosition eventRecorderPosition2) {
            if (eventRecorderPosition1.count > eventRecorderPosition2.count) {
                return -1;
            } else if ((eventRecorderPosition1.count == eventRecorderPosition2.count)) {
                return 0;
            } else {
                return 1;
            }
        }
    }



    public Object getOrgStatistics(StatisticReq req) {
        //计算天数
        List<Long> times = getTime(req);
        if (times == null || times.isEmpty()) {
            throw ExceptionUtil.createParamException("时间参数错误");
        }
        ChannelStatisticsResultResp channelStatisticsResultResp = new ChannelStatisticsResultResp();
        List<Integer> totalCounts = new ArrayList<>();
        ArrayList<ChannelStatisticsListBeanResp> result = new ArrayList<>();
        ChannelStatisticsListBeanResp resp;
        //如果有效，则表示查询的是某个活动的统计，无效表示查询的是某个渠道的数据统计
        if (NumberUtil.isValidId(req.eventId)) {
            ExtensionLinkReq extensionLinkReq = new ExtensionLinkReq();
            extensionLinkReq.eventId = req.eventId;
            List<ExtensionLink> extensionLinks = extensionLinkService.getLinkByEventId(extensionLinkReq);
            if (extensionLinks == null || extensionLinks.isEmpty()) {
                throw ExceptionUtil.createParamException("链接不存在");
            }
            Map<Integer, Integer> map = new HashMap();
            ProjectReceivePerLinkOneDay oneDay;
            for (Long time : times) {
                resp = new ChannelStatisticsListBeanResp();
                List<ChannelStatisticsLinkBeanResp> channelStatisticsLinkBeanResps = new ArrayList<>();
                ChannelStatisticsLinkBeanResp channelStatisticsLinkBeanResp;
                int totalCount = 0;
                int totalTicketCount=0;
                for (ExtensionLink extensionLink : extensionLinks) {
                    channelStatisticsLinkBeanResp = new ChannelStatisticsLinkBeanResp();
                    oneDay = getChannelStatisticsInfoById(extensionLink.getId(), time);
                    channelStatisticsLinkBeanResp.id = extensionLink.getId();
                    channelStatisticsLinkBeanResp.name = extensionLink.getLinkName();
                    if (oneDay != null) {
                        channelStatisticsLinkBeanResp.count = oneDay.getProjectReceiveCount();
                        channelStatisticsLinkBeanResp.ticketCount = oneDay.getTicketReceiveCount();
                        totalCount = totalCount + oneDay.getProjectReceiveCount();
                        totalTicketCount=totalTicketCount+oneDay.getTicketReceiveCount();
                        if (map.containsKey(extensionLink.getId())) {
                            int count = map.get(extensionLink.getId());
                            map.put(extensionLink.getId(), count + oneDay.getProjectReceiveCount());
                        } else {
                            map.put(extensionLink.getId(), oneDay.getProjectReceiveCount());
                        }
                    } else {
                        channelStatisticsLinkBeanResp.count = 0;
                        channelStatisticsLinkBeanResp.ticketCount = 0;
                        map.put(extensionLink.getId(), 0);
                    }
                    channelStatisticsLinkBeanResps.add(channelStatisticsLinkBeanResp);
                }
                resp.firstColumnName = DateUtil.convert2String(time, DateUtil.DATE_FORMAT);
                resp.totalCount = totalCount;
                resp.totalTicketCount=totalTicketCount;
                resp.statistics = channelStatisticsLinkBeanResps;
                result.add(resp);
            }
            for (ExtensionLink extensionLink : extensionLinks) {
                totalCounts.add(map.getOrDefault(extensionLink.getId(), 0));
            }
            channelStatisticsResultResp.totalCounts = totalCounts;
            channelStatisticsResultResp.list = result;
            return channelStatisticsResultResp;
        } else {
            //取所有已发布的活动
            List<Event> eventActivities = eventService.
                    getAllEventListByOrgId(req.orgId);
            //如果取出去的活动数量为0，直接返回
            if (eventActivities == null || eventActivities.isEmpty()) {
                return channelStatisticsResultResp;
            }
            Map<Integer, Integer> map = new HashMap<>();
            Map<Integer, ProjectReceivePerActivityOneDay> oneDay;
            for (Long time : times) {
                resp = new ChannelStatisticsListBeanResp();
                List<ChannelStatisticsLinkBeanResp> channelStatisticsLinkBeanResps = new ArrayList<>();
                List<Integer> integers = new ArrayList<>();
                for (Event eventActivity : eventActivities) {
                    integers.add(eventActivity.getId());
                }
                //获取所有的活动一个日期下的活动数量
                oneDay = getChannelStatisticsInfoByEventActivityId(integers, time);
                int totalCount = 0;
                ChannelStatisticsLinkBeanResp channelStatisticsLinkBeanResp;
                for (Event eventActivity : eventActivities) {
                    channelStatisticsLinkBeanResp = new ChannelStatisticsLinkBeanResp();
                    channelStatisticsLinkBeanResp.id = eventActivity.getId();
                    channelStatisticsLinkBeanResp.name = eventActivity.getTitle();
                    if (oneDay.containsKey(eventActivity.getId())) {
                        channelStatisticsLinkBeanResp.count = oneDay.get(eventActivity.getId())
                                .getProjectReceiveCount();
                        totalCount = totalCount + oneDay.get(eventActivity.getId()).getProjectReceiveCount();
                        if (map.containsKey(eventActivity.getId())) {
                            int count = map.get(eventActivity.getId());
                            map.put(eventActivity.getId(), count + oneDay.get(eventActivity.getId())
                                    .getProjectReceiveCount());
                        } else {
                            map.put(eventActivity.getId(), oneDay.get(eventActivity.getId()).getProjectReceiveCount());
                        }
                    } else {
                        channelStatisticsLinkBeanResp.count = 0;
                        map.put(eventActivity.getId(), 0);
                    }
                    channelStatisticsLinkBeanResps.add(channelStatisticsLinkBeanResp);
                }
                resp.firstColumnName = DateUtil.convert2String(time, DateUtil.DATE_FORMAT);
                resp.totalCount = totalCount;
                resp.statistics = channelStatisticsLinkBeanResps;
                result.add(resp);
            }
            for (Event eventActivity : eventActivities) {
                totalCounts.add(map.getOrDefault(eventActivity.getId(), 0));
            }
            channelStatisticsResultResp.totalCounts = totalCounts;
            channelStatisticsResultResp.list = result;
            return channelStatisticsResultResp;
        }
    }


    public Object task() {
        scheduledTask.taskDoProjectStatdays2();
        return null;
    }

    /**
     *  科委项目
     *  查询近30天的每天报名数和签到数
     */

    public Object getKeweiEvnet(StatisticReq req) {

        ProjectReceivePerChannelOneDayResultRes resultRes = new ProjectReceivePerChannelOneDayResultRes();
        Long nowTime = System.currentTimeMillis();
        Long endTime = nowTime - req.days * Constant.DAY_LONG;

        List<StatisticsNumber> statisticsNumbers = projectReceivePerActivityOneDayMapper.selectEventIdsSum(nowTime,endTime,req.eventIds);

        if (statisticsNumbers == null) {
            statisticsNumbers = new ArrayList<>();
        }
        List<Long> times = getTime(req);
        if (times == null || times.isEmpty()) {
            return null;
        }
        List<ProjectReceivePerChannelOneDayResp> perChannelOneDayResps = new ArrayList<>();
        ProjectReceivePerChannelOneDayResp oneDayResp;
        for (Long time : times) {
            oneDayResp = new ProjectReceivePerChannelOneDayResp();
            oneDayResp.time = DateUtil.convert2String(time, DateUtil.DATE_FORMAT);
            oneDayResp.week = DateUtil.getWeekOfDate(new Date(time));
            oneDayResp.count = 0;
            oneDayResp.ticketCount = 0;
            oneDayResp.signinCount = 0;
            for (StatisticsNumber oneDay : statisticsNumbers) {
                if (time.longValue() == oneDay.date) {
                    oneDayResp.count = oneDay.projectReceiveCountTotal;
                    oneDayResp.ticketCount = oneDay.ticketReceiveCountTotal;
                    oneDayResp.signinCount = oneDay.signInCountTotal;
                }
            }
            perChannelOneDayResps.add(oneDayResp);
        }
        resultRes.list = perChannelOneDayResps;

        return resultRes;
    }

    /**
     * 创业活动表单浏览数加一
     */

    public Object formAdd(StatisticReq req) {
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("eventId参数错误");
        }
        Event event=eventService.getEvent(req.eventId);
        if(event==null){
            throw  ExceptionUtil.createParamException("活动不存在");
        }
        Event saveEvent=new Event();
        saveEvent.setId(event.getId());
        saveEvent.setFormBrowseCount(event.getFormBrowseCount()+1);
        eventMapper.updateByPrimaryKeySelective(saveEvent);
        return null;
    }

    private List<Long> getTime(StatisticReq req) {
        List<Long> time = new ArrayList<>();
        if (NumberUtil.isValidId(req.days)) {
            long nowTime = System.currentTimeMillis();
            long standardDate = DateUtil.convert2long(
                    DateUtil.convert2String(nowTime, DateUtil.DATE_FORMAT), DateUtil.DATE_FORMAT);
            for (int i = 0; i < req.days; i++) {
                long date = standardDate - i * Constant.DAY_LONG;
                time.add(0, date);
            }
            return time;
        } else if (NumberUtil.isValidId(req.startTime) && NumberUtil.isValidId(req.endTime)) {
            long standardStartDate = DateUtil.convert2long(
                    DateUtil.convert2String(req.startTime, DateUtil.DATE_FORMAT), DateUtil.DATE_FORMAT);
            long standardEndDate = DateUtil.convert2long(
                    DateUtil.convert2String(req.endTime, DateUtil.DATE_FORMAT), DateUtil.DATE_FORMAT);
            time.add(standardStartDate);
            long date = standardStartDate;
            while (date < standardEndDate) {
                date = date + Constant.DAY_LONG;
                time.add(date);
            }
            time.add(standardEndDate);
            return time;
        }
        return null;
    }

    private Map<Integer, ProjectReceivePerActivityOneDay> getChannelStatisticsInfoByEventActivityId(List<Integer>
                                                                                                            eventActivityIds, Long time) {
        Map<Integer, ProjectReceivePerActivityOneDay> map = new HashMap<>();
        if (eventActivityIds == null || eventActivityIds.isEmpty()) {
            return map;
        }
        ProjectReceivePerActivityOneDayExample example = new ProjectReceivePerActivityOneDayExample();
        example.createCriteria().andEventIdIn(eventActivityIds).andDateEqualTo(time);
        List<ProjectReceivePerActivityOneDay> oneDays = projectReceivePerActivityOneDayMapper.selectByExample(example);
        if (oneDays != null && !oneDays.isEmpty()) {
            for (ProjectReceivePerActivityOneDay oneDay : oneDays) {
                map.put(oneDay.getEventId(), oneDay);
            }
        }
        return map;
    }

    private ProjectReceivePerLinkOneDay getChannelStatisticsInfoById(Integer extensionLinkId, Long time) {
        ProjectReceivePerLinkOneDayExample example = new ProjectReceivePerLinkOneDayExample();
        example.createCriteria().andExtensionLinkIdEqualTo(extensionLinkId).andDateEqualTo(time);
        List<ProjectReceivePerLinkOneDay> oneDays = projectReceivePerLinkOneDayMapper.selectByExample(example);
        if (oneDays == null || oneDays.isEmpty()) {
            return null;
        }
        return oneDays.get(0);
    }

    /**
     * 项目报名，报名记录的更新操作之后，对于报名数量进行更新,3.4.0后兼容创业活动统计
     *
     * @param record          报名记录
     * @param count           数量
     * @param ticketCount     票数
     */
    @Transactional
    public void countChangeAfterProjectsJoin(EventRecord record, Integer count, Integer ticketCount) {
        Integer extensionLinkId =record.getExtensionLinkId();
        if (count == null || count == 0) {
            throw ExceptionUtil.createParamException("更新数量不能为0");
        }
        ExtensionLink extensionLink = extensionLinkMapper.selectByPrimaryKey(extensionLinkId);
        if (extensionLink == null) {
            throw ExceptionUtil.createParamException("活动链接不存在");
        }
        Event event = eventMapper.selectByPrimaryKey(extensionLink.getEventId());
        if (event == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        OrgInfoExample orgInfoExample = new OrgInfoExample();
        orgInfoExample.createCriteria().andNewIdEqualTo(event.getOrgId()).andStateEqualTo((byte) 0);
        List<OrgInfo> orgInfos = orgInfoMapper.selectByExample(orgInfoExample);
        if (orgInfos == null || orgInfos.isEmpty()) {
            throw ExceptionUtil.createParamException("活动所属机构不存在");
        }
        OrgInfo orgInfo = orgInfos.get(0);

        //更新渠道表中的收到项目的数量
        OrgInfo orderChannelNew = new OrgInfo();
        orderChannelNew.setId(orgInfo.getId());
        orderChannelNew.setModifyTime(System.currentTimeMillis());
        orderChannelNew.setProjectReceiveCount(orgInfo.getProjectReceiveCount() + count);
        Integer result = orgInfoMapper.updateByPrimaryKeySelective(orderChannelNew);
        if (result == 0) {
            throw ExceptionUtil.createParamException("更新渠道收到的项目数量失败");
        }
        //不统计签到
        //更新渠道统计表下每天收到的项目数量
        increaseProjectCountForOrg(record.getCreateTime(),event.getOrgId(), count, ticketCount,0);
        //更新活动统计表下每天收到的项目数量
        increaseProjectCountForActivity(record.getCreateTime(),event.getId(), count, ticketCount,0);
        //更新推广链接统计表下每天收到的项目数
        increaseProjectCountForLink(record.getCreateTime(),extensionLinkId, count, ticketCount,0);

    }

    /**
     * 签到后统计相关数据库表操作
     * @param  ticketNo 票号
     * @param ticketId 票id
     * @param count 统计增加数量
     */
    public void countChangeAfterSignIn(String ticketNo,Integer ticketId, Integer count) {

        if (count == null || count == 0) {
            throw ExceptionUtil.createParamException("更新数量不能为0");
        }
        EventTicketOrderExample example=new EventTicketOrderExample();
        if(NumberUtil.isValidId(ticketId)) {
            example.createCriteria().andCommodityDetailIdEqualTo(ticketId);
        }
        if(StringUtil.isNotEmpty(ticketNo)){
            example.createCriteria().andTicketNoEqualTo(ticketNo);
        }
        EventTicketOrder eventTicketOrder=eventTicketOrderMapper.selectByExample(example).get(0);
        EventRecord record=eventRecordMapper.selectByPrimaryKey(eventTicketOrder.getEventRecordId());
        if(record==null){
            throw ExceptionUtil.createParamException(" 报名记录不存在");
        }
        Event event = eventMapper.selectByPrimaryKey(record.getEventId());
        if (event == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        //只统计签到
        //更新渠道统计表下每天收到的项目数量
        increaseProjectCountForOrg(record.getCreateTime(),event.getOrgId(), 0, 0,count);
        //更新活动统计表下每天收到的项目数量
        increaseProjectCountForActivity(record.getCreateTime(),event.getId(), 0, 0,count);
        //更新推广链接统计表下每天收到的项目数
        increaseProjectCountForLink(record.getCreateTime(),record.getExtensionLinkId(), 0, 0,count);

    }

    /**
     * 以推广链接维度为维度的统计表表每天收到的项目数量更新
     *  @param recordCreateTime 修改某个时间的数量，要传入时间
     */
    private void increaseProjectCountForLink(Long recordCreateTime,Integer extensionLinkId, Integer count, Integer ticketCount,Integer signInCount) {
        Long nowTime = System.currentTimeMillis();
        Long time = DateUtil.convert2long(DateUtil.convert2String(recordCreateTime, DateUtil.DATE_FORMAT), DateUtil.DATE_FORMAT);
        ProjectReceivePerLinkOneDayExample example = new ProjectReceivePerLinkOneDayExample();
        example.createCriteria().andDateEqualTo(time).andExtensionLinkIdEqualTo(extensionLinkId);
        List<ProjectReceivePerLinkOneDay> list=projectReceivePerLinkOneDayMapper.selectByExample(example);
        //修改
        if(!StringUtil.isEmpty(list)){
            ProjectReceivePerLinkOneDay linkOneDay=list.get(0);
            ProjectReceivePerLinkOneDay day = new ProjectReceivePerLinkOneDay();
            day.setProjectReceiveCount(getValidCount(linkOneDay.getProjectReceiveCount(),count));//需要增加的收到的项目数量
            day.setTicketReceiveCount(getValidCount(linkOneDay.getTicketReceiveCount(),ticketCount));//需要增加的收到的票的数量（路演大赛票数和项目数一一对应）
            day.setSignInCount(getValidCount(linkOneDay.getSignInCount(),signInCount));
            day.setModifyTime(nowTime);
            projectReceivePerLinkOneDayMapper.updateProjectCount(day,example);
        }else {
            ProjectReceivePerLinkOneDay insertDay = new ProjectReceivePerLinkOneDay();
            insertDay.setExtensionLinkId(extensionLinkId);
            insertDay.setCreateTime(nowTime);
            insertDay.setModifyTime(nowTime);
            insertDay.setDate(time);
            insertDay.setProjectReceiveCount(count>0?count:0);
            insertDay.setTicketReceiveCount(ticketCount>0?ticketCount:0);
            insertDay.setSignInCount(signInCount>0?signInCount:0);
            int result = projectReceivePerLinkOneDayMapper.insertSelective(insertDay);
            if (result == 0) {
                throw ExceptionUtil.createParamException("更新推广链接收到的项目数量失败");
            }
        }
    }

    /**
     * 避免出现负数，统计时的判断方法(结果如果小于0，把结果补成0)
     * @param receiveCount
     * @param count
     * @return
     */
    private Integer getValidCount(Integer receiveCount, Integer count) {

       return  receiveCount+count<0?-receiveCount:count;

    }

    /**
     * 以活动为维度的统计表表每天收到的项目数量更新
     */
    private void increaseProjectCountForActivity(Long recordCreateTime,Integer eventId, Integer count, Integer ticketCount,Integer signInCount) {
        Long nowTime = System.currentTimeMillis();
        Long time = DateUtil.convert2long(DateUtil.convert2String(recordCreateTime, DateUtil.DATE_FORMAT), DateUtil.DATE_FORMAT);
        ProjectReceivePerActivityOneDayExample example = new ProjectReceivePerActivityOneDayExample();
        example.createCriteria().andDateEqualTo(time).andEventIdEqualTo(eventId);
        List<ProjectReceivePerActivityOneDay> list=projectReceivePerActivityOneDayMapper.selectByExample(example);
        //修改项目的数量
        if(!StringUtil.isEmpty(list)){
            ProjectReceivePerActivityOneDay oneDay=list.get(0);
            ProjectReceivePerActivityOneDay day = new ProjectReceivePerActivityOneDay();
            day.setProjectReceiveCount(getValidCount(oneDay.getProjectReceiveCount(),count));//需要增加的收到的项目数量
            day.setTicketReceiveCount(getValidCount(oneDay.getTicketReceiveCount(),ticketCount));//需要增加的收到的票的数量（路演大赛票数和项目数一一对应）
            day.setSignInCount(getValidCount(oneDay.getSignInCount(),signInCount));
            day.setModifyTime(nowTime);
            projectReceivePerActivityOneDayMapper.updateProjectCount(day, example);
        }else{
            ProjectReceivePerActivityOneDay insertDay = new ProjectReceivePerActivityOneDay();
            insertDay.setEventId(eventId);
            insertDay.setCreateTime(nowTime);
            insertDay.setModifyTime(nowTime);
            insertDay.setDate(time);
            insertDay.setProjectReceiveCount(count>0?count:0);
            insertDay.setTicketReceiveCount(ticketCount>0?ticketCount:0);
            insertDay.setSignInCount(signInCount>0?signInCount:0);
            int result = projectReceivePerActivityOneDayMapper.insertSelective(insertDay);
            if (result == 0) {
                throw ExceptionUtil.createParamException("更新项目收到的项目数量失败");
            }
        }
    }

    /**
     * 以机构为维度的统计表表每天收到的项目数量更新
     *
     * @param orgId 机构id
     * @param count 数量
     */
    private void increaseProjectCountForOrg(Long recordCreateTime,Integer orgId, Integer count, Integer ticketCount,Integer signInCount) {
        //每天收到的项目表里更新收到的项目的数量
        Long nowTime = System.currentTimeMillis();
        Long time = DateUtil.convert2long(DateUtil.convert2String(recordCreateTime, DateUtil.DATE_FORMAT), DateUtil.DATE_FORMAT);
        ProjectReceivePerOrgOneDayExample example = new ProjectReceivePerOrgOneDayExample();
        example.createCriteria().andDateEqualTo(time).andOrgIdEqualTo(orgId);
        List<ProjectReceivePerOrgOneDay> list=projectReceivePerOrgOneDayMapper.selectByExample(example);
        //修改项目的数量
        if(!StringUtil.isEmpty(list)){
            ProjectReceivePerOrgOneDay oneDay=list.get(0);
            ProjectReceivePerOrgOneDay day = new ProjectReceivePerOrgOneDay();
            day.setProjectReceiveCount(getValidCount(oneDay.getProjectReceiveCount(),count));//需要增加的收到的项目数量
            day.setTicketReceiveCount(getValidCount(oneDay.getTicketReceiveCount(),ticketCount));//需要增加的收到的票的数量（路演大赛票数和项目数一一对应）
            day.setSignInCount(getValidCount(oneDay.getSignInCount(),signInCount));
            day.setModifyTime(nowTime);
            projectReceivePerOrgOneDayMapper.updateProjectCount(day, example);
        }else{
            ProjectReceivePerOrgOneDay insertDay = new ProjectReceivePerOrgOneDay();
            insertDay.setOrgId(orgId);
            insertDay.setCreateTime(nowTime);
            insertDay.setModifyTime(nowTime);
            insertDay.setDate(time);
            insertDay.setProjectReceiveCount(count>0?count:0);
            insertDay.setTicketReceiveCount(ticketCount>0?ticketCount:0);
            insertDay.setSignInCount(signInCount>0?signInCount:0);
            int result = projectReceivePerOrgOneDayMapper.insertSelective(insertDay);
            if (result == 0) {
                throw ExceptionUtil.createParamException("更新机构收到的项目数量失败");
            }
        }
    }
}
