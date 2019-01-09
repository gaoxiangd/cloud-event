package com.welian.service.task;

import com.welian.config.MiniPush;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.mapper.EventMapper;
import com.welian.mapper.EventRecordMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventExample;
import com.welian.pojo.EventRecord;
import com.welian.pojo.EventRecordExample;
import com.welian.service.CloudRedisService;
import com.welian.service.impl.SmsRemoteServiceImpl;
import org.sean.framework.util.Logger;
import org.sean.framework.util.StringUtil;
import com.welian.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 消息推送定时器
 * created by GaoXiang on 2018/7/5
 */
@Component("smsPushTask")
public class SmsPushTask {

    private static final Logger logger = Logger.newInstance(SmsPushTask.class);
    @Autowired
    private CloudRedisService redisService;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private EventRecordMapper eventRecordMapper;
    @Autowired
    private SmsRemoteServiceImpl smsRemoteService;
    @Value("${cloudevent.wechat.miniporgram.template.id.signup_join_remind}")
    private String templateIdForJoin;
    @Value("${cloudevent.wechat.miniporgram.template.content.signup_join_remind}")
    private String templateContentForJoin;
    /**
     * 活动开始前一天早上17:00推送微信消息
     */
//    @Scheduled(cron = "0 1 17 * * ? ")//每天17点1分执行
    public void miniWechatPush(){
        Long lockTime = redisService.lockTimerTask(CloudRedisService.LOCK_WECHAT_PUSH_TASK_KEY);
        if (lockTime == null) {
            logger.error("定时任务[ 活动开始前一天早上17:00推送微信消息定时提醒]: 任务已在另一个容器执行");
            return;
        }
        //拿到明天0点到24点的时间戳
        Long startTime = DateUtil.getPassDayBeginTime(1) ;
        Long endTime = startTime + 3600 * 24 * 1000;
        EventExample example=new EventExample();
        example.createCriteria().andStartTimeBetween(startTime, endTime).andStateEqualTo(SqlEnum.EventStateType
                .TYPE_EVENT_PUBLISHED.getByteValue());
        List<Event> events = eventMapper.selectByExample(example);
        if (StringUtil.isEmpty(events)) {
            return;
        }
        EventRecordExample eventRecordExample=new EventRecordExample();
        events.forEach(event -> {
            eventRecordExample.clear();
            eventRecordExample.createCriteria().
                    andStateIn(Arrays.asList(SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getByteValue())).
                    andTicketLockEqualTo(SqlEnum.EventTicketState.EVENT_TICKET_FALSE.getValue()).
                    andEventIdEqualTo(event.getId());
            List<EventRecord> eventRecords=eventRecordMapper.selectByExample(eventRecordExample);
            String page="pages/home";
            List recordUids=new ArrayList();
            for(EventRecord eventRecord:eventRecords){
                if(recordUids.contains(eventRecord.getUid())){
                    continue;
                }else{
                smsRemoteService.sendWechatPush(eventRecord, MiniPush.spellWechatContent(
                        templateContentForJoin,event,MiniPush.TOMORROW_BEGIN_REMIND),7,templateIdForJoin,page);
                    recordUids.add(eventRecord.getUid());
                }}
        });
        redisService.unlockTimerTask(CloudRedisService.LOCK_WECHAT_PUSH_TASK_KEY, lockTime);
    }
}
