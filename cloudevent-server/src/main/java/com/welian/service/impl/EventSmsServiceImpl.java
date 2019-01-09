package com.welian.service.impl;

import com.welian.beans.account.User;
import com.welian.beans.cloudevent.sms.EventSmsInfoResp;
import com.welian.beans.cloudevent.sms.EventSmsResp;
import com.welian.beans.cloudevent.sms.SendSmsReq;
import com.welian.beans.cloudevent.user.UserResp;
import com.welian.beans.notify.TemplateReq;
import com.welian.enums.cloudevent.ParamEnum;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.mapper.EventRecordUserMapper;
import com.welian.mapper.EventSmsMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventRecord;
import com.welian.pojo.EventRecordUser;
import com.welian.pojo.EventRecordUserExample;
import com.welian.pojo.EventSms;
import com.welian.pojo.EventSmsExample;
import com.welian.service.UserService;
import com.welian.service.record.impl.IEventRecordProjectInfoImpl;
import com.welian.utils.ExceptionUtil;
import com.welian.utils.PagingUtil;
import org.sean.framework.text.WordFilter;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dayangshu on 17/7/19.
 */
@Service("eventSmsService")
public class EventSmsServiceImpl {
    @Autowired
    private IEventRecordProjectInfoImpl iEventRecordProjectInfo;
    @Autowired
    private UserService userService;
    @Autowired
    private EventSmsMapper eventSmsMapper;
    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private SmsRemoteServiceImpl smsRemoteService;
    @Autowired
    private EventRecordUserMapper eventRecordUserMapper;


    public void saveSendSmsLog(Integer eventId, List<Integer> uidList, String content) {
        List<EventSms> eventSmsList = new ArrayList<>();
        for (Integer uid : uidList) {
            EventSms eventSms = new EventSms();
            eventSms.setEventId(eventId);
            eventSms.setUid(uid);
            eventSms.setContent(content);
            eventSms.setCreateTime(System.currentTimeMillis());
            eventSms.setModifyTime(System.currentTimeMillis());
            eventSms.setSource(SqlEnum.SmsSource.TYPE_VERIFY_MANAGE.getValue());
            eventSmsList.add(eventSms);
        }
        eventSmsMapper.batchInsert(eventSmsList);
    }


    public EventSmsInfoResp getSendSmsLog(SendSmsReq req) {
        Event event = eventService.getEvent(req.eventId);
        if (event == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }

        EventSmsExample example = new EventSmsExample();
        example.setLimit(req.getSize());
        example.setOffset(PagingUtil.getStart(req.getPage(), req.getSize()));
        example.setOrderByClause("id desc");
        example.createCriteria().andEventIdEqualTo(req.eventId).
                andSourceEqualTo(SqlEnum.SmsSource.TYPE_VERIFY_MANAGE.getValue());

        List<EventSms> eventSmsList = eventSmsMapper.selectByExample(example);

        EventSmsInfoResp eventSmsInfoResp = new EventSmsInfoResp();
        eventSmsInfoResp.count = (int) eventSmsMapper.countByExample(example);

        List<EventSmsResp> list = new ArrayList<>();
        List<Integer> uids = new ArrayList<>();

        for (EventSms eventSms : eventSmsList) {
            uids.add(eventSms.getUid());
        }
        if(StringUtil.isEmpty((uids))){
            eventSmsInfoResp.list = list;
            return eventSmsInfoResp;
        }
        Map<Integer, User> profileMap = userService.getUserInfoListByIds(uids);
        for (EventSms eventSms : eventSmsList) {
            EventSmsResp eventSmsResp = new EventSmsResp();
            eventSmsResp.createTime = eventSms.getCreateTime();
            eventSmsResp.content = eventSms.getContent();
            UserResp userResp = new UserResp();
            userResp.uid = eventSms.getUid();
            User profile = new User();
            if (profileMap.containsKey(eventSms.getUid())) {
                profile = profileMap.get(eventSms.getUid());
            }
            if (profile != null) {
                userResp.name = profile.name;
                userResp.company = profile.company;
                userResp.position = profile.position;
                userResp.mobile = profile.phone;
            }
            eventSmsResp.user = userResp;
            list.add(eventSmsResp);
        }
        eventSmsInfoResp.list = list;
        return eventSmsInfoResp;
    }


    public void sendSms(String content, Integer eventId) {

        int contentR1 = content.indexOf("【");
        int contentR2 = content.indexOf("】");
        if(contentR1 != -1 || contentR2 != -1){
            throw ExceptionUtil.createParamException("短信中不得包含特殊字符【】");
        }

        Event event = eventService.getEvent(eventId);
        if (event == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        if (event.getState() == SqlEnum.EventStateType.TYPE_EVENT_PUBLISH_NOT.getValue().intValue()) {
            throw ExceptionUtil.createParamException("活动未发布，短信功能无法使用");
        }
        if (event.getState() == SqlEnum.EventStateType.TYPE_EVENT_DELETED.getValue().intValue()) {
            throw ExceptionUtil.createParamException("活动已删除，短信功能无法使用");
        }
        //判断活动时间是否有效
        if (event.getEndTime() != null) {
            if (event.getEndTime() < System.currentTimeMillis()) {
                throw ExceptionUtil.createParamException("活动已结束，短信功能无法使用");
            }
        }
        //老的活动需求要求半个小时之内只能发送一次
        //取最新发送的一条短信，判断创建时间是否在半个小时之内，如果是，提示
        EventSmsExample example = new EventSmsExample();
        example.setOrderByClause("id desc");
        example.setLimit(1);
        example.createCriteria().andSourceEqualTo(SqlEnum.SmsSource.TYPE_VERIFY_MANAGE.getValue())
                .andEventIdEqualTo(eventId);
        List<EventSms> eventSmsList = eventSmsMapper.selectByExample(example);
        if (!StringUtil.isEmpty(eventSmsList)) {
            EventSms eventSms = eventSmsList.get(0);
            if (eventSms.getCreateTime() != null && System.currentTimeMillis() -
                    eventSms.getCreateTime() < 30 * 60 * 1000L) {
                throw ExceptionUtil.createParamException("30分钟内只能群发一次短信");
            }
        }
        //只取审核通过的的报名列表
        List<EventRecord> eventRecordList = iEventRecordProjectInfo.
                getRecordListByEventIdAndLevel(eventId, 0, 0, null,
                        ParamEnum.ProjectRecordListGetType.TYPE_AUDITED);
        if (StringUtil.isEmpty(eventRecordList)) {
            throw ExceptionUtil.createParamException("没有已通过的报名记录，无法发送短信");
        }


        //获取需要发送的电话号码
        Set<String> phones = new HashSet<>();
        //同一个用户用多个项目报名同一活动只发一条短信
        Set<Integer> uids=new HashSet<>();
        List<Integer> recordIds=new ArrayList<>();
        for(EventRecord eventRecord:eventRecordList){
            if(NumberUtil.isInValidId(eventRecord.getUid())){
                continue;
            }
            recordIds.add(eventRecord.getId());
            uids.add(eventRecord.getUid());
        }
        List<Integer> logUidList=new ArrayList<>(uids);
        EventRecordUserExample recordUserExample=new EventRecordUserExample();
        recordUserExample.createCriteria().andEventRecordIdIn(recordIds);
        List<EventRecordUser> recordUsers=eventRecordUserMapper.selectByExample(recordUserExample);
        for(EventRecordUser recordUser:recordUsers){
            phones.add(recordUser.getPhone()) ;
        }
        List<String> phoneList=new ArrayList<>(phones);
        WordFilter.FilterResult result = WordFilter.filterWords(content);
        if (result.hasBlackWord) {
            throw ExceptionUtil.createParamException("发送的短信含有敏感词'" + getDistinctString(result.blackWords) + "'，请重新编辑");
        }
        smsRemoteService.sendSMS(phoneList, content);
        //保存短信发送内容
        saveSendSmsLog(eventId, logUidList, content);
    }


    /**
     * 短信管理-发送短信 并只发关注过机构的
     *
     * @param content
     * @return
     */
    public void sendSms(String content, List<String> phoneList , List<Integer> logUidList ,List<Integer> eventIds) {

        WordFilter.FilterResult result = WordFilter.filterWords(content);
        if (result.hasBlackWord) {
            throw ExceptionUtil.createParamException("由于运营商政策的限制,短信中不允许出现敏感词" + "\"" + getDistinctString(result.blackWords) + "\"");
        }
        smsRemoteService.sendSMS1(phoneList, content);

        //保存短信发送内容
        for (Integer eventId : eventIds){
            saveSendSmsLog(eventId, logUidList, content);
        }

    }

    /**
     * 发送模板短信
     * @param req
     * @return
     */
    public void templateSend(TemplateReq req) {
         smsRemoteService.templateSend(req);
    }

    private String getDistinctString(String blackWords) {
        if(blackWords==null||StringUtil.isEmpty(blackWords.trim())){
            return blackWords;
        }
        Set setResult=new HashSet(Arrays.asList(blackWords.trim().split(",")));
        List listResult=new ArrayList<>(setResult);
        return  String.join(",",listResult);
    }
}
