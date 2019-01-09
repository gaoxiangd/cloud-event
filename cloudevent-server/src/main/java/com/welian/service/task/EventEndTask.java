package com.welian.service.task;
import com.welian.service.V1EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



/**
 * <p>活动定时任务
 * <p>每天0点执行统计前一天的活动结束任务
 */
@Component("eventEndTask")
public class EventEndTask {

    @Autowired
    private V1EventService eventService;


//    @Scheduled(cron = "0 1 8 * * ? ")//每天凌晨8点1分执行
    @Transactional
    protected void taskDoEventEndDays() {
        eventService.authSettlement(null);
    }

}
