package com.welian.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.sean.framework.bean.BaseResult;
import com.welian.beans.notify.IMReq;
import com.welian.beans.notify.SMS;
import com.welian.beans.notify.TemplatePojo;
import com.welian.beans.notify.TemplateReq;
import com.welian.beans.notify.WechatReq;
import com.welian.beans.notify.messages.TxtMessage;
import com.welian.client.notify.IMClient;
import com.welian.client.notify.SMSClient;
import com.welian.client.notify.TemplateClient;
import com.welian.client.notify.WechatClient;
import com.welian.config.CloudEventConfig;
import com.welian.config.Constant;
import com.welian.enums.notify.EnumIMType;
import com.welian.enums.notify.SMSType;
import com.welian.mapper.EventRecordUserMapper;
import com.welian.pojo.EventRecord;
import com.welian.util.Logger;
import com.welian.pojo.EventRecordUser;
import com.welian.pojo.EventRecordUserExample;
import org.sean.framework.util.StringUtil;
import com.welian.utils.CharUtil;
import com.welian.utils.ExceptionUtil;
import com.welian.utils.MobileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by GaoXiang on 2017/9/11
 */
@Service("smsRemoteService")
public class SmsRemoteServiceImpl {

    private static final Logger logger = Logger.newInstance(SmsRemoteServiceImpl.class);

    @Autowired
    private CloudEventConfig cloudEventConfig;
    @Autowired
    private SMSClient smsClient;
    @Autowired
    private IMClient imClient;
    @Autowired
    private WechatClient wechatClient;
    @Autowired
    private TemplateClient templateClient;

    @Autowired
    private EventRecordUserMapper eventRecordUserMapper;


    public void sendSMS(List<String> phoneList, String content) {
        phoneList= MobileUtil.filterPhoneList(phoneList);
        String newContent = CharUtil.wordToXX(content);
        if (StringUtil.isEmpty(newContent)) {
            return;
        }
        if (StringUtil.isEmpty(phoneList)) {
            return;
        }
        SMS param = new SMS();
        param.msg = newContent;
        param.phone = org.apache.commons.lang.StringUtils.join(phoneList,",");
        param.type = SMSType.NORMAL;
        if (cloudEventConfig.getSms_send_flag() == Constant.SMS_SEND_OPEN) {
            BaseResult baseResult = smsClient.sendMsg(param);
            if (!baseResult.isSuccess()) {
                if(baseResult.getErrormsg().contains("包含敏感词")) {
                    logger.info("由于包含敏感词，发送短信失败");
                }else{
                    logger.info(baseResult.getErrormsg());
                }
            }
        }
    }


    public void sendSMS1(List<String> phoneList, String content) {
        String newContent = CharUtil.wordToXX(content);
        if (StringUtil.isEmpty(newContent)) {
            return;
        }
        if (StringUtil.isEmpty(phoneList)) {
            return;
        }
        SMS param = new SMS();
        param.msg = newContent;
        param.phone = org.apache.commons.lang.StringUtils.join(phoneList,",");
        param.type = SMSType.NORMAL;
        if (cloudEventConfig.getSms_send_flag() == Constant.SMS_SEND_OPEN) {
            BaseResult baseResult = smsClient.sendMsg(param);
            if (!baseResult.isSuccess()) {
                if(baseResult.getErrormsg().contains("包含敏感词")) {
                    throw ExceptionUtil.createParamException("由于包含敏感词，发送短信失败");
                }else{
                    throw ExceptionUtil.createParamException(baseResult.getErrormsg());
                }
            }
        }
    }

    /**
     * 发送模板短信
     * @param req
     * @return
     */
    public void templateSend(TemplateReq req) {
        //根据recordId获取手机号码
        List<Integer> recordIds=new ArrayList<>();
        //recordId->pojo
        Map<Integer,TemplatePojo> maps=new HashMap<>();
        req.records.forEach(record->{
            recordIds.add(record.recordId);
            maps.put(record.recordId,record);
        });
        if(StringUtil.isEmpty(recordIds)){
            logger.info("recordId不存在");
            return;
        }
        EventRecordUserExample example=new EventRecordUserExample();
        example.createCriteria().andEventRecordIdIn(recordIds);
        List<EventRecordUser> users=eventRecordUserMapper.selectByExample(example);
        users.forEach(eventRecordUser -> {
            if(maps.containsKey(eventRecordUser.getEventRecordId())){
                maps.get(eventRecordUser.getEventRecordId()).phone=eventRecordUser.getPhone();
            }
        });
        SMS sms=new SMS();
        sms.template=req;
        templateClient.send(sms);
    }


    /**
     * 过滤不符合规范的手机号码
     * @param phoneList
     * @return
     */
    public List<String> filterPhoneList(List<String> phoneList) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147))\\d{8}$";
        List<String> newPhoneList=new ArrayList<>();
        for(String phone:phoneList){
            if(phone.matches(regExp)){
                newPhoneList.add(phone);
            }
        }
        return newPhoneList;
    }
    
    /**
     * 发送消息到app
     *
     * @param content,fromUid,toUid
     */
    public void sendAppMsg(String content, Integer fromUid, Integer toUid) {

        TxtMessage txtMessage = new TxtMessage(content);
        IMReq request = new IMReq();
        request.type = EnumIMType.P2P;
        request.messageType = txtMessage.getMessageType();
        request.content = txtMessage.toString();
        request.fromId = Integer.toString(fromUid);
        request.toId = Integer.toString(toUid);
        imClient.send(request);
    }

    /**
     * 发送微信消息
     * @param eventRecord 报名记录信息
     * @param content 发送内容-主体
     * @param category 类型 4.报名成功 5.审核成功 6.审核失败 7.每天定时提醒
     * @param templateId 模板id
     * @param page 跳转页面
     */
    public void sendWechatPush(EventRecord eventRecord, String content,Integer category,String templateId,String page) {
        WechatReq req=new WechatReq();
        req.touid = eventRecord.getUid();
        req.templateId = templateId;
        req.type = 3;
        req.category = category;
        req.content = content;
        req.page=page;
        BaseResult<String> result=wechatClient.pushMessage(req);
        if (result == null || !result.isSuccess()) {
            logger.error("活动消息推送异常，param={}, response={}",
                    JSONObject.toJSONString(req), result);
        } else {
            logger.info("活动消息推送成功，param={}, response={}",
                    JSONObject.toJSONString(req), result);
        }

    }

}
