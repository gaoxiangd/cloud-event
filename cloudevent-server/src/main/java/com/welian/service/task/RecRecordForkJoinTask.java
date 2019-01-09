package com.welian.service.task;

import com.welian.beans.account.UserReq;
import com.welian.beans.cloudevent.AddtionalForm;
import com.welian.beans.cloudevent.Ticket;
import com.welian.beans.cloudevent.record.CustomSignUpReq;
import com.welian.enums.account.EnumReqType;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.mapper.EventRecordCollectionMapper;
import com.welian.mapper.EventRecordMapper;
import com.welian.mapper.EventTicketOrderMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventRecord;
import com.welian.pojo.EventRecordCollection;
import com.welian.pojo.EventTicketOrder;
import org.sean.framework.service.RedisService;
import com.welian.service.UserService;
import com.welian.service.record.impl.IEventRecordCustomInfoImpl;
import org.sean.framework.util.Logger;
import org.sean.framework.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

/**
 * created by GaoXiang on 2018/7/26
 */
public class RecRecordForkJoinTask extends RecursiveTask<List<Integer>> {
    private static final Logger logger = Logger.newInstance(RecRecordForkJoinTask.class);
    //每个小任务执行20个处理
    private static final int THREADSHOLD = 20;
    private List<String> phoneLists;
    private List<CustomSignUpReq> rightList;
    private List<CustomSignUpReq> wrongList;
    private int low;
    private int height;
    private Event event;

    private UserService userService;
    private EventRecordMapper eventRecordMapper;
    private EventRecordCollectionMapper eventRecordCollectionMapper;
    private EventTicketOrderMapper eventTicketOrderMapper;
    private RedisService redisService;
    private IEventRecordCustomInfoImpl iEventRecordCustomInfo;


    /**
     * @param phoneLists 已经报名的手机号
     * @param rightList   正确的报名入参
     * @param wrongList   错误的报名入参
     * @param low         最低开始位置
     * @param height      最高开始位置
     */
    public RecRecordForkJoinTask(List<String> phoneLists, List<CustomSignUpReq> rightList, List<CustomSignUpReq>
            wrongList, int low, int height, Event event, UserService userService, EventRecordMapper eventRecordMapper,
                                 EventRecordCollectionMapper eventRecordCollectionMapper, EventTicketOrderMapper
                                         eventTicketOrderMapper,
                                 RedisService redisService, IEventRecordCustomInfoImpl iEventRecordCustomInfo) {
        this.phoneLists = phoneLists;
        this.rightList = rightList;
        this.wrongList = wrongList;
        this.low = low;
        this.height = height;
        this.event = event;
        this.userService = userService;
        this.eventRecordMapper = eventRecordMapper;
        this.eventRecordCollectionMapper = eventRecordCollectionMapper;
        this.eventTicketOrderMapper = eventTicketOrderMapper;
        this.redisService = redisService;
        this.iEventRecordCustomInfo = iEventRecordCustomInfo;
    }



    protected List<Integer> compute() {
        List<Integer> recordIds=new ArrayList<>();
        if (height - low < THREADSHOLD) {
            for (int i = low; i < height; i++) {
                long time = System.currentTimeMillis();
                CustomSignUpReq req = rightList.get(i);
                logger.info("开始打印第{}个报名记录了啊啊啊啊啊啊",i);
                try {
                    if (phoneLists.contains(req.user.mobile) && SqlEnum.CostType.TYPE_CHARGE_NOT.getValue().
                            equals(req.costType)) {
                        req.validImportData = false;
                        req.reason = "重复报名";
                        wrongList.add(req);
                        logger.info("手机号为:{}的用户报名出错啦,原因是用户已经报名该活动。");
                        continue;
                    }
                    Long startTime=redisService.getCurrentTime();
                    UserReq userReq = new UserReq();
                    userReq.type = EnumReqType.PRE;
                    userReq.mobile = req.user.mobile;
                    userReq.position = req.user.position;
                    userReq.company = req.user.company;
                    userReq.name = req.user.name;
                    req.user.uid = userService.getUidByUser(userReq);
                    logger.info("调用预注册的时间{}毫秒,uid:{}",redisService.getCurrentTime()-startTime,
                            req.user.uid);
                    recordIds.add(saveRecordRelation(req, event));
                    phoneLists.add(req.user.mobile);
                } catch (Exception e) {
                    req.validImportData = false;
                    req.reason = "数据错误";
                    wrongList.add(req);
                    logger.info("手机号为:{}的用户报名出错啦,原因是{}。", req.user.mobile, e.getMessage());
                    e.printStackTrace();
                }
                logger.info("整体耗时：{}",System.currentTimeMillis()-time);
            }
            return recordIds;
        } else {
            int middle = (height + low) / 2;
            RecRecordForkJoinTask leftTask = new RecRecordForkJoinTask(phoneLists, rightList, wrongList, low,
                    middle, event, userService, eventRecordMapper, eventRecordCollectionMapper,
                    eventTicketOrderMapper, redisService, iEventRecordCustomInfo);
            RecRecordForkJoinTask rightTask = new RecRecordForkJoinTask(phoneLists, rightList, wrongList, middle,
                    height, event, userService, eventRecordMapper, eventRecordCollectionMapper,
                    eventTicketOrderMapper, redisService, iEventRecordCustomInfo);
            leftTask.fork();
            rightTask.fork();
            try {
                recordIds.addAll(leftTask.get());
                recordIds.addAll(rightTask.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return recordIds;
        }
    }

    /**
     * 报名相关数据入库
     *
     * @param req   报名入参
     * @param event 活动
     */
    private Integer saveRecordRelation(CustomSignUpReq req, Event event) {
        //event_record
        Long startTime1=redisService.getCurrentTime();
        EventRecord eventRecord = new EventRecord();
        eventRecord.setEventId(event.getId());
        eventRecord.setOrgId(event.getOrgId());
        eventRecord.setCreateTime(redisService.getCurrentTime());
        eventRecord.setExtensionLinkId(req.extensionLinkId);
        eventRecord.setSignUpType(SqlEnum.SignUpType.TYPE_EVENT_CUSTOM.getByteValue());//观众报名
        eventRecord.setModifyTime(System.currentTimeMillis());
        eventRecord.setSource(SqlEnum.EventRecordSource.TYPE_SOURCE_IMPORT.getByteValue());
        eventRecord.setTradeNo(redisService.getCurrentTime() + StringUtil.getRandomLetterUpCase(4));
        eventRecord.setUid(req.user.uid);
        eventRecord.setIsImport(req.isImportRecord);
        eventRecordMapper.insertSelective(eventRecord);
        logger.info("event_record入库时间{}毫秒",redisService.getCurrentTime()-startTime1);
        Integer recordId=eventRecord.getId();
        //event_record_user
        Long startTime2=redisService.getCurrentTime();
        iEventRecordCustomInfo.saveRecordUser(req.user, recordId);
        logger.info("event_record_user入库时间{}毫秒",redisService.getCurrentTime()-startTime2);
        //event_ticket_order
        Long startTime3=redisService.getCurrentTime();
        EventTicketOrder eventTicketOrder;
        List<EventTicketOrder> list = new ArrayList<>();
        for (Ticket ticket : req.ticketList) {
            for (int i = 0; i < ticket.buyCount; i++) {
                eventTicketOrder = new EventTicketOrder();
                eventTicketOrder.setEventRecordId(recordId);
                eventTicketOrder.setEventId(event.getId());
                eventTicketOrder.setSignState(SqlEnum.EventSignStatus.EVENT_SIGN_FALSE.getValue());
                eventTicketOrder.setTicketState(SqlEnum.EventTicketState.EVENT_TICKET_FALSE.getValue());
                eventTicketOrder.setCreateTime(redisService.getCurrentTime());
                eventTicketOrder.setModifyTime(redisService.getCurrentTime());
                //导入数据用当前时间戳+i+随机4位英文作为票号
                eventTicketOrder.setTicketNo(redisService.getCurrentTime() + StringUtil.getRandomLetterUpCase(4));
                eventTicketOrder.setTicketId(ticket.id);
                //commodityDetailId置为0可能有坑
                eventTicketOrder.setCommodityDetailId(0);
                list.add(eventTicketOrder);
            }
        }
        eventTicketOrderMapper.batchInsert(list);
        logger.info("event_ticket_order入库时间{}毫秒",redisService.getCurrentTime()-startTime3);
        //event_record_custom
        Long startTime4=redisService.getCurrentTime();
        List<EventRecordCollection> collectionList = new ArrayList<>();
        EventRecordCollection eventRecordCollection;
        for (AddtionalForm addtionalForm : req.additionalForm) {
            eventRecordCollection = new EventRecordCollection();
            eventRecordCollection.setCreateTime(redisService.getCurrentTime());
            eventRecordCollection.setModifyTime(redisService.getCurrentTime());
            eventRecordCollection.setEventRecordId(recordId);
            eventRecordCollection.setCollectionId(addtionalForm.id);
            eventRecordCollection.setContent(addtionalForm.value);
            collectionList.add(eventRecordCollection);
        }
        if(StringUtil.isNotEmpty(collectionList)) {
            eventRecordCollectionMapper.batchInsert(collectionList);
        }
        logger.info("event_record_custom入库时间{}毫秒",redisService.getCurrentTime()-startTime4);
        return recordId;
    }

}
