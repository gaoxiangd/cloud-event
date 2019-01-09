package com.welian.service.task;

import com.welian.beans.cloudevent.Ticket;
import com.welian.beans.cloudevent.project.ProjectListBeanResp;
import com.welian.beans.cloudevent.signup.SearchSignupResp;
import com.welian.commodity.beans.Order;
import com.welian.pojo.Event;
import com.welian.pojo.EventRecordCollection;
import com.welian.pojo.EventRecordJoinUser;
import com.welian.service.impl.EventRecordCollectionServiceImpl;
import com.welian.service.impl.EventRecordServiceImpl;
import org.sean.framework.util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

/**
 * created by GaoXiang on 2018/8/21
 */
public class RecRTForkJoinTask extends RecursiveTask<List<SearchSignupResp>> {

    private static final Logger logger = Logger.newInstance(RecRTForkJoinTask.class);
    //每个小任务执行20个处理
    private static final int THREADSHOLD = 20;
    private int low;
    private int height;
    private List<EventRecordJoinUser> eventRecordList;
    private Map<String, Order> tradeMap;
    private Event event;
    private List<Ticket> ticketModules;
    private Map<Integer, ProjectListBeanResp> projectMap;
    private EventRecordServiceImpl eventRecordService;
    private EventRecordCollectionServiceImpl eventRecordCollectionService;


    public RecRTForkJoinTask(List<EventRecordJoinUser> eventRecordList, Map<String, Order> tradeMap, Event event,
                             List<Ticket> ticketModules, Map<Integer, ProjectListBeanResp> projectMap,
                             EventRecordServiceImpl eventRecordService, EventRecordCollectionServiceImpl
                                     eventRecordCollectionService, int low, int height) {
        this.eventRecordList = eventRecordList;
        this.tradeMap = tradeMap;
        this.event = event;
        this.ticketModules = ticketModules;
        this.projectMap = projectMap;
        this.eventRecordService = eventRecordService;
        this.low = low;
        this.height = height;
        this.eventRecordCollectionService = eventRecordCollectionService;
    }

    @Override
    protected List<SearchSignupResp> compute() {
        List<SearchSignupResp> responseList = new ArrayList<>();
        if (height - low < THREADSHOLD) {
            for (int i = low; i < height; i++) {
                Long startTime = System.currentTimeMillis();
                SearchSignupResp resp = eventRecordService.converseData(eventRecordList.get(i), tradeMap, event,
                        ticketModules, projectMap);
                logger.info("recordId为{}的报名数据converse时间{}毫秒" , eventRecordList.get(i).getId(), System
                        .currentTimeMillis() - startTime);
                Long collectStartTime=System.currentTimeMillis();
                List<EventRecordCollection> eventRecordCollectionList = eventRecordCollectionService.
                        getEventRecordCollectionList(resp.signUpId);
                resp.additionalForm = eventRecordCollectionService.
                        converseDataList(eventRecordCollectionList);
                logger.info("自定义字段转换时间是{}毫秒",System.currentTimeMillis()-collectStartTime);
                responseList.add(resp);
            }
            return responseList;
        } else {
            int middle = (height + low) / 2;
            RecRTForkJoinTask leftTask = new RecRTForkJoinTask(eventRecordList, tradeMap, event, ticketModules,
                    projectMap, eventRecordService, eventRecordCollectionService, low, middle);
            RecRTForkJoinTask rightTask = new RecRTForkJoinTask(eventRecordList, tradeMap, event, ticketModules,
                    projectMap, eventRecordService, eventRecordCollectionService, middle, height);
            leftTask.fork();
            rightTask.fork();
            try {
                responseList.addAll(leftTask.get());
                responseList.addAll(rightTask.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return responseList;
        }
    }
}
