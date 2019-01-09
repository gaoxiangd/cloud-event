package com.welian.service.task;

import org.sean.framework.bean.BaseResult;
import com.welian.beans.DeliveryOrder;
import com.welian.beans.GetOrderRequest;
import com.welian.beans.account.User;
import com.welian.client.DeliveryClient;
import com.welian.config.AppMsg;
import com.welian.config.CloudEventConfig;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.enums.notify.EnumIMType;
import com.welian.mapper.EventMapper;
import com.welian.mapper.EventRecordMapper;
import com.welian.mapper.EventSmsMapper;
import com.welian.mapper.EventStateCustomMapper;
import com.welian.mapper.EventStateProjectMapper;
import com.welian.mapper.EventTicketOrderMapper;
import com.welian.mapper.ProjectFeedbackBackupMapper;
import com.welian.mapper.ProjectReceivePerActivityOneDayMapper;
import com.welian.mapper.ProjectReceivePerLinkOneDayMapper;
import com.welian.mapper.ProjectReceivePerOrgOneDayMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventExample;
import com.welian.pojo.EventRecord;
import com.welian.pojo.EventRecordExample;
import com.welian.pojo.EventSms;
import com.welian.pojo.EventStateCustom;
import com.welian.pojo.EventStateCustomExample;
import com.welian.pojo.EventStateProject;
import com.welian.pojo.EventStateProjectExample;
import com.welian.pojo.EventTicketOrderExample;
import com.welian.pojo.ProjectFeedbackBackup;
import com.welian.pojo.ProjectFeedbackBackupExample;
import com.welian.pojo.ProjectReceivePerActivityOneDay;
import com.welian.pojo.ProjectReceivePerActivityOneDayExample;
import com.welian.pojo.ProjectReceivePerLinkOneDay;
import com.welian.pojo.ProjectReceivePerLinkOneDayExample;
import com.welian.pojo.ProjectReceivePerOrgOneDay;
import com.welian.pojo.ProjectReceivePerOrgOneDayExample;
import com.welian.service.CloudRedisService;
import com.welian.service.UserService;
import com.welian.service.impl.SmsRemoteServiceImpl;
import org.sean.framework.util.Logger;
import org.sean.framework.util.StringUtil;
import com.welian.utils.DateUtil;
import com.welian.utils.ExceptionUtil;
import com.welian.utils.PagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>活动数据统计定时任务
 * <p>每天0点10分执行
 */
@Component("scheduledTask")
public class ScheduledTask {
    private static final Logger logger = Logger.newInstance(ScheduledTask.class);

    private static boolean PROJECT_FEEDBACK_BACKUP = false;//是否正在同步

    @Autowired
    private CloudRedisService redisService;
    @Autowired
    private ProjectReceivePerOrgOneDayMapper projectReceivePerOrgOneDayMapper;
    @Autowired
    private ProjectReceivePerActivityOneDayMapper projectReceivePerActivityOneDayMapper;
    @Autowired
    private ProjectReceivePerLinkOneDayMapper projectReceivePerLinkOneDayMapper;
    @Autowired
    private EventRecordMapper eventRecordMapper;
    @Autowired
    private ProjectFeedbackBackupMapper projectFeedbackBackupMapper;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private EventSmsMapper eventSmsMapper;
    @Autowired
    private EventStateProjectMapper eventStateProjectMapper;
    @Autowired
    private CloudEventConfig cloudEventConfig;
    @Autowired
    private SmsRemoteServiceImpl smsRemoteService;
    @Autowired
    private UserService userService;
    @Autowired
    private DeliveryClient deliveryClient;
    @Autowired
    private EventStateCustomMapper eventStateCustomMapper;
    @Autowired
    private EventTicketOrderMapper eventTicketOrderMapper;


    /**
     * 做项目备份
     */
//    @Scheduled(cron = "0 0 1/6 * * ? ")//1小时之后,每6小时执行一次
    protected void doProjectFeedBackUp() {
        // 设置redis锁,保证同一时间只有一个服务处理记录
        Long lockTime = redisService.lockTimerTask(CloudRedisService.LOCK_ORDER_EXPIRE_TASK_KEY);
        if (lockTime == null) {
            logger.error("定时任务[项目备份]: 任务已在另一个容器执行");
            return;
        }
        // 如果项目同步正在进行,继续上次的同步,放弃本次同步
        if (PROJECT_FEEDBACK_BACKUP)
            return;
        try {
            // 分页数据,用于获取需要同步的项目ids
            int page = 1, size = 50;
            do {
                // 如果同步没有开始,标记为开始
                if (!PROJECT_FEEDBACK_BACKUP) {
                    PROJECT_FEEDBACK_BACKUP = true;
                }
                // 从project_backup_info表中分页查找到需要更新的项目ids
                List<Integer> orderIds = projectFeedbackBackupMapper.selectOrderIdByPage(PagingUtil.getStart(page,
                        size), size);
                // 如果没有查找到相应的数据,结束循环
                if (null == orderIds || 0 == orderIds.size()) {
                    PROJECT_FEEDBACK_BACKUP = false;
                } else {
                    GetOrderRequest request = new GetOrderRequest();
                    request.setOrderIds(orderIds);
                    BaseResult<DeliveryOrder> baseResult = deliveryClient.getByIds(request);
                    ProjectFeedbackBackup projectFeedbackBackup;
                    if (baseResult.isSuccess() && !StringUtil.isEmpty(baseResult.getList())) {
                        List<DeliveryOrder> projectFeebackResps = baseResult.getList();
                        ProjectFeedbackBackupExample example;
                        for (DeliveryOrder projectFeebackResp : projectFeebackResps) {
                            if (projectFeebackResp.getStatus() != null && projectFeebackResp.getStatus() !=
                                    SqlEnum.ProjectFeedbackState.TYPE_DEFAULT.getValue().intValue() &&
                                    projectFeebackResp.getId() != null) {
                                projectFeedbackBackup = new ProjectFeedbackBackup();
                                projectFeedbackBackup.setFeedbackTime(projectFeebackResp.getReplyTime());
                                projectFeedbackBackup.setState(projectFeebackResp.getStatus().intValue());
                                example = new ProjectFeedbackBackupExample();
                                example.createCriteria().andOrderIdEqualTo(projectFeebackResp.getId());
                                projectFeedbackBackupMapper.updateByExampleSelective(projectFeedbackBackup, example);
                            }
                        }
                    }

                    if (orderIds.size() < size) {
                        // 如果当前页的数据小于分页的数量,表示没有下一页了,结束同步
                        PROJECT_FEEDBACK_BACKUP = false;
                    } else {
                        // 否则,同步下一页的数据
                        page++;
                    }
                }

            } while (PROJECT_FEEDBACK_BACKUP);

            //执行完同步反馈信息之后，统计一下反馈的数量
            //释放redis锁
            redisService.unlockTimerTask(CloudRedisService.LOCK_ORDER_EXPIRE_TASK_KEY, lockTime);
        } catch (Exception e) {
            e.printStackTrace();
            // 出现异常,结束循环
            PROJECT_FEEDBACK_BACKUP = false;
            redisService.unlockTimerTask(CloudRedisService.LOCK_ORDER_EXPIRE_TASK_KEY, lockTime);
        }
    }

    public void taskDoProjectStatdays2() {
        // 设置redis锁,保证同一时间只有一个服务处理记录
        Long lockTime = redisService.lockTimerTask(CloudRedisService.LOCK_ORDER_EXPIRE_TASK_KEY);
        if (lockTime == null) {
            logger.error("定时任务[统计特定某一天收到的项目数量-测试]: 任务已在另一个容器执行");
            return;
        }
        for (int i = 60; i >= 0; i--) {
            syncProjectsForTheDay(getBeginOfDayBeforeToday(i), getEndOfDayBeforeToday(i));
        }
        redisService.unlockTimerTask(CloudRedisService.LOCK_ORDER_EXPIRE_TASK_KEY, lockTime);
    }

    public void taskDoFeedback() {
        doProjectFeedBackUp();
    }

    /**
     * 统计渠道,事件活动和推广链接昨天收到的项目数量
     * 每天凌晨12:00开始统计
     */
//    @Scheduled(cron = "0 1 0 * * ? ")//每天凌晨0点1分执行
    protected void taskDoProjectStatdays() {
        // 设置redis锁,保证同一时间只有一个服务处理记录
        Long lockTime = redisService.lockTimerTask(CloudRedisService.LOCK_ORDER_EXPIRE_TASK_KEY);
        if (lockTime == null) {
            logger.error("定时任务[统计特定某一天收到的项目数量]: 任务已在另一个容器执行");
            return;
        }
        int project_count_last_days = cloudEventConfig.getProject_count_last_days();
        System.out.println("project_count_last_days:" + project_count_last_days);
        String theDay = cloudEventConfig.getProject_count_the_day();
        System.out.println("project_count_theDay:" + theDay);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();//获取当前时间
        long beginOfTheDay = 0, endOfTheDay = 0;
        try {
            if (!StringUtil.isEmpty(theDay)) {
                c.setTime(simpleDateFormat.parse(theDay));
                DateUtil.clearCalendar(c, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar
                        .MILLISECOND);//清空时分秒毫秒
                beginOfTheDay = c.getTimeInMillis();
                c.add(Calendar.DAY_OF_MONTH, 1);
                endOfTheDay = c.getTimeInMillis();
            }
        } catch (ParseException e) {
            redisService.unlockTimerTask(CloudRedisService.LOCK_ORDER_EXPIRE_TASK_KEY, lockTime);
            e.printStackTrace();
        }
        if (0 < project_count_last_days) {
            for (int i = project_count_last_days; i > 0; i--) {
                syncProjectsForTheDay(getBeginOfDayBeforeToday(i), getEndOfDayBeforeToday(i));
            }
        } else if (!StringUtil.isEmpty(theDay) && 0 != beginOfTheDay && 0 != endOfTheDay) {
            syncProjectsForTheDay(beginOfTheDay, endOfTheDay);
        } else {
            syncProjectsForTheDay(getBeginOfDayBeforeToday(1), getEndOfDayBeforeToday(1));
        }
        redisService.unlockTimerTask(CloudRedisService.LOCK_ORDER_EXPIRE_TASK_KEY, lockTime);

    }

    /**
     * 统计特定某一天收到的项目数量,3.4.0后兼容创业活动，每天收到的报名数量
     *
     * @param endOfDay
     * @param beginOfDay
     */
    private void syncProjectsForTheDay(long beginOfDay, long endOfDay) {
        // ==============  统计推广链接昨天收到的项目数量  ====================
        syncLinkCountYesterday(endOfDay, beginOfDay);
        // ===================  统计事件活动昨天收到的项目数量  =======================
        syncActivityCountYesterday(beginOfDay);
        // ====================  统计渠道昨天收到的项目数量  =======================
        syncChannelCountYesterday(beginOfDay);
    }

    /**
     * 获取before天前的日期的结束时间戳
     *
     * @param before
     * @return
     */
    private long getEndOfDayBeforeToday(int before) {
        Calendar c = Calendar.getInstance();//获取当前时间
        DateUtil.clearCalendar(c, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND);
        //清空时分秒毫秒
        if (1 != before) {
            c.add(Calendar.DAY_OF_MONTH, 1 - before);
        }
        // 昨天结束的时间戳(精确到毫秒,使用了今天开始的时间戳)
        return c.getTimeInMillis();
    }

    /**
     * 获取before天前的日期的开始时间戳
     *
     * @param before
     * @return
     */
    private long getBeginOfDayBeforeToday(int before) {
        Calendar c = Calendar.getInstance();//获取当前时间
        DateUtil.clearCalendar(c, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND);
        //清空时分秒毫秒
        c.add(Calendar.DAY_OF_MONTH, 0 - before);//天数减一(来到昨天的0点)
        // 昨天开始的时间戳(精确到毫秒)
        return c.getTimeInMillis(); // 昨天最早时间,也是昨天的时间戳(精确到日)
    }

    /**
     * 统计推广链接昨天收到的项目数量,3.4.0后兼容创业活动，昨天收到的报名数量
     *
     * @param beginOfToday
     * @param beginOfYesterday
     */
    private void syncLinkCountYesterday(long beginOfToday, long beginOfYesterday) {
        EventRecordExample eventRecordExample = new EventRecordExample();
        eventRecordExample.createCriteria().andCreateTimeBetween(beginOfYesterday, beginOfToday).andTicketLockEqualTo
                (SqlEnum.EventTicketState.EVENT_TICKET_FALSE.getValue());
        //兼容创业活动
        //.andSignUpTypeEqualTo(SqlEnum.SignUpType.TYPE_EVENT_PROJECT.getByteValue());
        eventRecordExample.setGroupByClause("extension_link_id");
        eventRecordExample.setOrderByClause("id asc");
        // 获取所有需要被统计的extension_link_id推广链接ids
        List<EventRecord> eventRecords = eventRecordMapper.selectByExample(eventRecordExample);
        if (eventRecords == null || eventRecords.isEmpty()) {
            return;
        }
        List<Byte> integers = new ArrayList<>();
        integers.add(SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getValue().byteValue());
        integers.add(SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY.getValue().byteValue());
        for (EventRecord eventRecord : eventRecords) {
            if (eventRecord == null) {
                continue;
            }
            eventRecordExample.clear();
            eventRecordExample.createCriteria().andExtensionLinkIdEqualTo(eventRecord.getExtensionLinkId())
                    //兼容创业活动
//                    .andSignUpTypeEqualTo(SqlEnum.SignUpType.TYPE_EVENT_PROJECT.getByteValue())
                    .andCreateTimeBetween(beginOfYesterday, beginOfToday).andStateIn(integers)
                    .andTicketLockEqualTo(SqlEnum.EventTicketState.EVENT_TICKET_FALSE.getValue());
            // 昨天这个链接收到的项目数（报名数）
            long projectsPerLinkOfYesterday = eventRecordMapper.countByExample(eventRecordExample);
            List<EventRecord> records = eventRecordMapper.selectByExample(eventRecordExample);
            Integer ticketCount = 0;
            Integer signInCount = 0;
            if (!StringUtil.isEmpty(records)) {
                ticketCount = getTicketCount(records);
                signInCount = getSignInCount(records);
            }
            // 尝试更新
            ProjectReceivePerLinkOneDay linkOneDay = new ProjectReceivePerLinkOneDay();
            linkOneDay.setProjectReceiveCount((int) (long) projectsPerLinkOfYesterday);
            linkOneDay.setTicketReceiveCount(ticketCount);
            linkOneDay.setSignInCount(signInCount);
            linkOneDay.setModifyTime(System.currentTimeMillis());
            ProjectReceivePerLinkOneDayExample linkOneDayExample = new ProjectReceivePerLinkOneDayExample();
            linkOneDayExample.createCriteria().andExtensionLinkIdEqualTo(eventRecord.getExtensionLinkId())
                    .andDateEqualTo(beginOfYesterday);
            int updateCount = projectReceivePerLinkOneDayMapper.updateByExampleSelective(linkOneDay, linkOneDayExample);
            if (0 == updateCount) {
                // 如果没有找到和更新相应记录,那就是需要插入
                linkOneDay.setExtensionLinkId(eventRecord.getExtensionLinkId());
                linkOneDay.setCreateTime(System.currentTimeMillis());
                linkOneDay.setModifyTime(System.currentTimeMillis());
                linkOneDay.setDate(beginOfYesterday);
                int result = projectReceivePerLinkOneDayMapper.insertSelective(linkOneDay);
                if (result == 0) {
                    throw ExceptionUtil.createParamException("更新数量失败");
                }
            }
        }
    }

    /**
     * 拿到已经签到数量
     *
     * @param records
     * @return
     */
    private Integer getSignInCount(List<EventRecord> records) {
        List<Integer> recordIds = new ArrayList<>();
        for (EventRecord record : records) {
            recordIds.add(record.getId());
        }
        EventTicketOrderExample ex = new EventTicketOrderExample();
        ex.createCriteria().andEventRecordIdIn(recordIds).andSignStateEqualTo(SqlEnum.SignInType.TYPE_SIGN_IN_YES
                .getValue());
        Integer ticketCount = (int) eventTicketOrderMapper.countByExample(ex);
        return ticketCount;
    }

    /**
     * 拿到票数
     *
     * @return
     */
    private Integer getTicketCount(List<EventRecord> records) {
        List<Integer> recordIds = new ArrayList<>();
        for (EventRecord record : records) {
            recordIds.add(record.getId());
        }
        EventTicketOrderExample ex = new EventTicketOrderExample();
        ex.createCriteria().andEventRecordIdIn(recordIds);
        Integer ticketCount = (int) eventTicketOrderMapper.countByExample(ex);
        return ticketCount;
    }

    /**
     * 从日期纬度统计活动昨天收到的项目数量,3.4.0后兼容创业活动，昨天收到的报名数量
     *
     * @param beginOfYesterday
     */
    private void syncActivityCountYesterday(long beginOfYesterday) {
        // 需要同步收到项目数量的事件活动ids
        List<Integer> activityIdsNeededSync = projectReceivePerLinkOneDayMapper.selectActivityIdsNeededSync
                (beginOfYesterday);
        if (activityIdsNeededSync == null || activityIdsNeededSync.isEmpty()) {
            return;
        }
        for (Integer activityId : activityIdsNeededSync) {
            if (null == activityId)
                continue;
            // 获取每个事件活动id对应的收到项目数（报名记录数量）
            Integer activityProjectCount = projectReceivePerLinkOneDayMapper.sumActivityProjectsCount
                    (beginOfYesterday, activityId);
            if (activityProjectCount == null) {
                activityProjectCount = 0;
            }
            // 获取每个事件活动id对应的收到票数
            Integer ticketCount = projectReceivePerLinkOneDayMapper.sumActivityTicketsCount(beginOfYesterday,
                    activityId);
            if (ticketCount == null) {
                ticketCount = 0;
            }
            // 获取每个事件活动id对应的签到数
            Integer signInCount = projectReceivePerLinkOneDayMapper.sumActivitySignInCount(beginOfYesterday,
                    activityId);
            if (signInCount == null) {
                signInCount = 0;
            }
            // 尝试更新
            ProjectReceivePerActivityOneDay activityOneDay = new ProjectReceivePerActivityOneDay();
            activityOneDay.setProjectReceiveCount(activityProjectCount);
            activityOneDay.setTicketReceiveCount(ticketCount);
            activityOneDay.setSignInCount(signInCount);
            long now = System.currentTimeMillis();
            activityOneDay.setModifyTime(now);
            ProjectReceivePerActivityOneDayExample example = new ProjectReceivePerActivityOneDayExample();
            example.createCriteria().andEventIdEqualTo(activityId).andDateEqualTo(beginOfYesterday);
            // 尝试更新活动收到的项目数量
            int updateCount = projectReceivePerActivityOneDayMapper.updateByExampleSelective(activityOneDay, example);
            if (0 == updateCount) {
                // 如果更新条数为0,添加记录
                activityOneDay.setEventId(activityId);
                now = System.currentTimeMillis();
                activityOneDay.setCreateTime(now);
                activityOneDay.setModifyTime(now);
                activityOneDay.setDate(beginOfYesterday);
                projectReceivePerActivityOneDayMapper.insertSelective(activityOneDay);
            }
        }
    }

    /**
     * 统计渠道昨天收到的项目数量，,3.4.0后兼容创业活动，昨天收到的报名数量
     *
     * @param beginOfYesterday
     */
    private void syncChannelCountYesterday(long beginOfYesterday) {
        // 查找需要同步的机构ids
        List<Integer> channelIdsNeededSync = projectReceivePerActivityOneDayMapper.selectChannelIdsNeededSync
                (beginOfYesterday);
        if (channelIdsNeededSync == null || channelIdsNeededSync.isEmpty()) {
            return;
        }
        for (Integer channelId : channelIdsNeededSync) {
            if (null == channelId) {
                continue;
            }
            // 获取渠道id对应的昨天收到的项目数
            Integer channelProjectsCount = projectReceivePerActivityOneDayMapper.sumChannelProjectsCount
                    (beginOfYesterday, channelId);
            if (channelProjectsCount == null) {
                channelProjectsCount = 0;
            }
            // 获取每个事件活动id对应的收到票数
            Integer ticketCount = projectReceivePerActivityOneDayMapper.sumChannelTicketsCount(beginOfYesterday,
                    channelId);
            if (ticketCount == null) {
                ticketCount = 0;
            }
            // 获取每个事件活动id对应的签到数
            Integer signInCount = projectReceivePerActivityOneDayMapper.sumActivitySignInCount(beginOfYesterday,
                    channelId);
            if (signInCount == null) {
                signInCount = 0;
            }
            // 尝试更新
            ProjectReceivePerOrgOneDay channelOneDay = new ProjectReceivePerOrgOneDay();
            channelOneDay.setProjectReceiveCount(channelProjectsCount);
            channelOneDay.setTicketReceiveCount(ticketCount);
            channelOneDay.setSignInCount(signInCount);
            long now = System.currentTimeMillis();
            channelOneDay.setModifyTime(now);
            ProjectReceivePerOrgOneDayExample example = new ProjectReceivePerOrgOneDayExample();
            example.createCriteria().andOrgIdEqualTo(channelId).andDateEqualTo(beginOfYesterday);
            // 尝试更新渠道昨天收到的项目数量
            int updateCount = projectReceivePerOrgOneDayMapper.updateByExampleSelective(channelOneDay, example);
            if (0 == updateCount) {
                // 如果更新条数为0,添加记录
                channelOneDay.setOrgId(channelId);
                now = System.currentTimeMillis();
                channelOneDay.setCreateTime(now);
                channelOneDay.setModifyTime(now);
                channelOneDay.setDate(beginOfYesterday);
                int result = projectReceivePerOrgOneDayMapper.insertSelective(channelOneDay);
                if (result == 0) {
                    throw ExceptionUtil.createParamException("更新数量失败");
                }
            }
        }
    }

    /**
     * 活动开始前一天早上10:00（若报名数为0，则不推送）
     */
//    @Scheduled(cron = "0 1 10 * * ? ")//每天凌晨10点1分执行
    public void taskSendMsgLastMoring10() {

        // 设置redis锁,保证同一时间只有一个服务处理记录
        Long lockTime = redisService.lockTimerTask(CloudRedisService.LOCK_ORDER_EXPIRE_TASK_KEY);
        if (lockTime == null) {
            logger.error("定时任务[ 活动开始前一天早上10:00定时提醒]: 任务已在另一个容器执行");
            return;
        }
        EventExample example = new EventExample();
        //拿到明天0点到24点的时间戳
        Long startTime = DateUtil.getPassDayBeginTime(1) ;
        Long endTime = startTime + 3600 * 24 * 1000;
        example.createCriteria().andStartTimeBetween(startTime, endTime).andStateEqualTo(SqlEnum.EventStateType
                .TYPE_EVENT_PUBLISHED.getByteValue());
        List<Event> events = eventMapper.selectByExample(example);
        if (StringUtil.isEmpty(events)) {
            return;
        }
        List<Integer> eventIds = new ArrayList<>();
        Map<Integer, Event> eventMap = new HashMap<>();
        Map<Integer, Integer> joinedCountMap = new HashMap<>();
        //取报名数
        for (Event event : events) {
            eventIds.add(event.getId());
            eventMap.put(event.getId(), event);
        }
        //有些是项目报名有些是用户报名
        EventStateProjectExample projectExample = new EventStateProjectExample();
        projectExample.createCriteria().andEventIdIn(eventIds);
        List<EventStateProject> eventStateProjects = eventStateProjectMapper.selectByExample(projectExample);
        EventStateCustomExample customExample = new EventStateCustomExample();
        customExample.createCriteria().andEventIdIn(eventIds);
        List<EventStateCustom> eventStateCustoms = eventStateCustomMapper.selectByExample(customExample);
        //分别循环两种，拿到报名数不是0的活动的报名数
        for (EventStateProject eventStateProject : eventStateProjects) {
            if (eventStateProject.getJoinedCount().equals(0)) {
                eventIds.remove(eventStateProject.getEventId());
            } else {
                joinedCountMap.put(eventStateProject.getEventId(), eventStateProject.getJoinedCount());
            }
        }
        for (EventStateCustom eventStateCustom : eventStateCustoms) {
            if (eventStateCustom.getJoinedCount().equals(0)) {
                eventIds.remove(eventStateCustom.getEventId());
            } else {
                joinedCountMap.put(eventStateCustom.getEventId(), eventStateCustom.getJoinedCount());
            }
        }
        if (!StringUtil.isEmpty(eventIds)) {
            for (Integer eventId : eventIds) {
                Event event = eventMap.get(eventId);
                User profile = userService.getUserInfoById(event.getPublishUid());
                Integer fromUid = EnumIMType.ACTIVE.getValue();
                Integer toUid = event.getPublishUid();
                //发app消息
                String content = profile.name + AppMsg.EVENT_TEN_PUSH[0] + event.getTitle() + AppMsg
                        .EVENT_TEN_PUSH[1] + DateUtil.getStrTimeNotDay
                        (event.getStartTime()) + AppMsg.EVENT_TEN_PUSH[2] + joinedCountMap.get(eventId)
                        + AppMsg.EVENT_TEN_PUSH[3];
                smsRemoteService.sendAppMsg(content, fromUid, toUid);
                List<String> phoneList = new ArrayList<>();
                phoneList.add(profile.phone);
                //发短信
                smsRemoteService.sendSMS(phoneList, content);
                //记录到event_sms表中
                EventSms eventSms = new EventSms();
                eventSms.setEventId(event.getId());
                eventSms.setUid(event.getPublishUid());
                eventSms.setContent(content);
                eventSms.setCreateTime(System.currentTimeMillis());
                eventSms.setModifyTime(System.currentTimeMillis());
                eventSms.setSource(SqlEnum.SmsSource.TYPE_RECORD_FIRST.getValue());
                eventSms.setFlag(SqlEnum.SmsFlag.TYPE_SINGLE.getValue());
                eventSmsMapper.insertSelective(eventSms);
            }
        }
        redisService.unlockTimerTask(CloudRedisService.LOCK_ORDER_EXPIRE_TASK_KEY, lockTime);
    }
}
