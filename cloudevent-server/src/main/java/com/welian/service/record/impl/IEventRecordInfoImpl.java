package com.welian.service.record.impl;

import com.welian.beans.account.User;
import com.welian.beans.cloudevent.AddtionalForm;
import com.welian.beans.cloudevent.ExtensionLinkResp;
import com.welian.beans.cloudevent.record.EventDetailResp;
import com.welian.beans.cloudevent.sys.SysUserRelationReq;
import com.welian.beans.cloudevent.user.UserReq;
import com.welian.beans.urls.Url;
import com.welian.beans.urls.UrlReq;
import com.welian.beans.urls.UrlType;
import com.welian.client.urls.UrlClient;
import com.welian.config.AppMsg;
import com.welian.config.CloudEventConfig;
import com.welian.config.SmsMsg;
import com.welian.enums.cloudevent.ParamEnum;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.enums.notify.EnumIMType;
import com.welian.mapper.EventCustomFieldMapper;
import com.welian.mapper.EventMapper;
import com.welian.mapper.EventRecordCollectionMapper;
import com.welian.mapper.EventRecordMapper;
import com.welian.mapper.EventRecordUserMapper;
import com.welian.mapper.EventSmsMapper;
import com.welian.mapper.EventSysMessageMapper;
import com.welian.mapper.ExtensionLinkMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventCustomField;
import com.welian.pojo.EventCustomFieldExample;
import com.welian.pojo.EventRecord;
import com.welian.pojo.EventRecordCollection;
import com.welian.pojo.EventRecordExample;
import com.welian.pojo.EventRecordUser;
import com.welian.pojo.EventSms;
import com.welian.pojo.EventSysMessage;
import com.welian.pojo.ExtensionLink;
import com.welian.service.CustomFormModuleService;
import com.welian.service.impl.EventServiceImpl;
import com.welian.service.impl.ExtensionLinkServiceImpl;
import com.welian.service.impl.SmsRemoteServiceImpl;
import com.welian.service.impl.SysMsgServiceImpl;
import com.welian.utils.Base64Utils;
import com.welian.utils.DateUtil;
import com.welian.utils.ExceptionUtil;
import org.sean.framework.bean.BaseResult;
import org.sean.framework.util.JSONUtil;
import org.sean.framework.util.Logger;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by memorytale on 2017/7/13.
 */
@Component("IEventRecordInfo")
public abstract class IEventRecordInfoImpl {

    private static final Logger logger = Logger.newInstance(IEventRecordInfoImpl.class);
    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private EventRecordMapper eventRecordMapper;
    @Autowired
    private EventRecordCollectionMapper eventRecordCollectionMapper;
    @Autowired
    private EventCustomFieldMapper eventCustomFieldMapper;
    @Autowired
    private EventRecordUserMapper eventRecordUserMapper;
    @Autowired
    private SmsRemoteServiceImpl smsRemoteService;
    @Autowired
    private EventSmsMapper eventSmsMapper;
    @Autowired
    private EventSysMessageMapper eventSysMessageMapper;
    @Autowired
    private SysMsgServiceImpl sysMsgService;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private ExtensionLinkMapper extensionLinkMapper;
    @Autowired
    private ExtensionLinkServiceImpl extensionLinkService;
    @Autowired
    private CloudEventConfig cloudEventConfig;
    @Autowired
    private UrlClient urlClient;
    @Autowired
    private CustomFormModuleService customFormModuleService;

    public Object getEventDetailInfo(Integer eventId) {
        EventDetailResp eventDetailResp = (EventDetailResp) getEventInfoForFromPage(eventId);
        Event event = eventService.getEvent(eventId);
        if (event == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        //因为此接口在平台方预览的时候也有调用，因此草稿状态也可以进行查看，前端会隐藏报名的入口
//        if (event.getState() ==
//                SqlEnum.EventStateType.TYPE_EVENT_DRAFT.getValue().intValue()) {
//            throw ExceptionUtil.createParamException("活动是草稿状态，无法查看");
//        }
//        if (event.getState() ==
//                SqlEnum.EventStateType.TYPE_EVENT_PUBLISH_CANCEL.getValue().intValue()) {
//            throw ExceptionUtil.createParamException("活动已取消发布");
//        }
        if (event.getState() ==
                SqlEnum.EventStateType.TYPE_EVENT_DELETED.getValue().intValue()) {
            throw ExceptionUtil.createParamException("活动已被删除");
        }
        eventDetailResp.latitude = event.getLatitude();
        eventDetailResp.longitude = event.getLongitude();
        eventDetailResp.detail = event.getDetail();

        return eventDetailResp;
    }

    public Object getEventInfoForFromPage(Integer eventId) {
        Event event = eventService.getEvent(eventId);
        if (event == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
        if (event.getState() ==
                SqlEnum.EventStateType.TYPE_EVENT_PUBLISH_NOT.getValue().intValue()) {
            throw ExceptionUtil.createParamException("活动是未发布状态，无法查看");
        }
        if (event.getState() ==
                SqlEnum.EventStateType.TYPE_EVENT_DELETED.getValue().intValue()) {
            throw ExceptionUtil.createParamException("活动已被删除");
        }
        EventDetailResp eventDetailResp = new EventDetailResp();
        eventDetailResp.title = event.getTitle();
        eventDetailResp.eventId = event.getId();
        eventDetailResp.eventType = event.getTemplateId();
        eventDetailResp.lineType = event.getLineType();
        eventDetailResp.commodityId=event.getCommodityId();
        eventDetailResp.state = event.getState();//此字段给前端的原因是前端要根据此字段是否是草稿状态隐藏或者显示报名入口
        eventDetailResp.logo = cloudEventConfig.getImage_prefix() + event.getLogo();
        StringBuilder builder = new StringBuilder();
        if (event.getLineType() == SqlEnum.EventLineType.TYPE_ONLINE.getValue().byteValue()) {
            builder.append("线上活动");
        } else {
            //带有城市的地址，如果没有城市，则不加，用点分割开
            if (!StringUtil.isEmpty(event.getCityName())) {
                builder.append(event.getCityName());
            }
            if (!StringUtil.isEmpty(event.getAddress())) {
                if (builder.length() > 0) {
                    builder.append(" · ");
                }
                builder.append(event.getAddress());
            }
        }
        eventDetailResp.address = builder.toString();
        //活动时间段
        eventDetailResp.dateTimeRange = DateUtil.converseTime(event.getStartTime(), event.getEndTime());
        return eventDetailResp;
    }

    public void saveRecordCustomField(List<AddtionalForm> additionalForms, Integer eventRecordId,Integer eventId) {
        //原来没有自定义字段，报名时创建了新的自定义字段
        if (additionalForms == null || additionalForms.isEmpty()) {
            if(StringUtil.isNotEmpty((List)customFormModuleService.get(eventId))){
                throw ExceptionUtil.createParamException("活动表单已更新，请刷新页面重新报名");
            }
            return;
        }
        //检测是否自定义字段进行了增删改查
        checkCustomFieldChange(additionalForms.stream().map(af -> af.id).collect(Collectors.toList()),
                eventRecordMapper.selectByPrimaryKey(eventRecordId).getEventId());
        EventRecordCollection eventRecordCollection;
        for (AddtionalForm addtionalForm : additionalForms) {
            eventRecordCollection = new EventRecordCollection();
            eventRecordCollection.setCreateTime(System.currentTimeMillis());
            eventRecordCollection.setModifyTime(System.currentTimeMillis());
            eventRecordCollection.setEventRecordId(eventRecordId);
            eventRecordCollection.setCollectionId(addtionalForm.id);
            eventRecordCollection.setContent(addtionalForm.value);

            EventCustomField eventCustomField = eventCustomFieldMapper.selectByPrimaryKey(addtionalForm.id);
            if (eventCustomField == null) {
                throw ExceptionUtil.createParamException("活动表单已更新，请刷新页面重新报名");
            }
            if (StringUtil.isEmpty(addtionalForm.value)) {
                if (eventCustomField.getRequired() != null &&
                        eventCustomField.getRequired().equals(SqlEnum.CustomFieldRequiredType.TYPE_MUST.getByteValue()
                        )) {
                    throw ExceptionUtil.createParamException("请填写自定义字段");
                }
            } else {
                if (addtionalForm.value.length() > 100) {
                    throw ExceptionUtil.createParamException(addtionalForm.label + "字段已超过100字，无法报名");
                }
            }
            eventRecordCollectionMapper.insertSelective(eventRecordCollection);
        }
    }

    /**
     * 检测自定义字段是否有改变
     * @param collectIds  前端传来的自定义id集合
     * @param eventId  活动id
     */
    public void checkCustomFieldChange(List<Integer> collectIds, Integer eventId) {
        EventCustomFieldExample example=new EventCustomFieldExample();
        example.createCriteria().andEventIdEqualTo(eventId);
        List<EventCustomField> eventCustomFields=eventCustomFieldMapper.selectByExample(example);
        if(StringUtil.isEmpty(eventCustomFields)){
            return ;
        }
        List<Integer> eventCids=eventCustomFields.stream().map(cf->cf.getId()).collect(Collectors.toList());
        if(!(collectIds.containsAll(eventCids)&&eventCids.containsAll(collectIds))){
            throw ExceptionUtil.createParamException("活动表单已更新，请刷新页面重新报名");
        }
    }

    public void saveRecordUser(UserReq userReq, Integer eventRecordId) {
        if (userReq == null) {
            return;
        }
        EventRecordUser eventRecordUser = new EventRecordUser();
        eventRecordUser.setCompany(userReq.company);
        eventRecordUser.setPosition(userReq.position);
        eventRecordUser.setName(userReq.name);
        eventRecordUser.setPhone(userReq.mobile);
        eventRecordUser.setUid(userReq.uid);
        eventRecordUser.setEventRecordId(eventRecordId);
        eventRecordUser.setCreateTime(System.currentTimeMillis());
        eventRecordUser.setModifyTime(System.currentTimeMillis());
        eventRecordUser.setUnionId(userReq.unionId);
        eventRecordUserMapper.insertSelective(eventRecordUser);
    }

    /**
     * 判断该记录是活动第一个报名
     *
     * @param event
     * @return
     */
    public boolean checkFirstRecord(Event event) {
        Boolean flag = false;
        EventRecordExample example = new EventRecordExample();
        List<Byte> validStates = new ArrayList<>();
        validStates.add(SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getByteValue());
        validStates.add(SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY.getByteValue());
        example.createCriteria().andEventIdEqualTo(event.getId()).andStateIn(validStates)
                .andTicketLockEqualTo(SqlEnum.TicketLockType.TYPE_NORMAL.getValue());
        if (eventRecordMapper.countByExample(example) == 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 给app发消息,报名
     *
     * @param user
     * @param event
     * @param verifyType
     */
    public void sendAppMsgForSignUp(UserReq user, Event event, Integer verifyType) {
        Integer fromUid = EnumIMType.ACTIVE.getValue();
        Integer toUid = user.uid;
        String content = "";
        if (verifyType == SqlEnum.RecordVerifyType.TYPE_VERIFY_NO.getValue().intValue()) {
            content = user.name + AppMsg.EVENT_SIGNUP_SUCCESS[0] + event.getTitle() + AppMsg.EVENT_SIGNUP_SUCCESS[1];
        } else if (verifyType == SqlEnum.RecordVerifyType.TYPE_VERIFY_YES.getValue().intValue()) {
            content = user.name + AppMsg.EVENT_SIGNUP_CHECKING[0] + event.getTitle() + AppMsg.EVENT_SIGNUP_CHECKING[1];
        }
        smsRemoteService.sendAppMsg(content, fromUid, toUid);
    }

    /**
     * 给发布者发短信,记录到event_sms表中
     *
     * @param event
     */
    public void sendSms(Event event, Byte verifyType, User profile) {
        String content = "";
        if (verifyType == SqlEnum.RecordVerifyType.TYPE_VERIFY_YES.getByteValue()) {
            content = profile.name + SmsMsg.FIRST_SIGNUP_CHECK[0] + event.getTitle() + SmsMsg.FIRST_SIGNUP_CHECK[1];
        } else if (verifyType == SqlEnum.RecordVerifyType.TYPE_VERIFY_NO.getByteValue()) {
            content = profile.name + SmsMsg.FIRST_SIGNUP[0] + event.getTitle() + SmsMsg.FIRST_SIGNUP[1];
        }
        List<String> phoneList = new ArrayList<>();
        phoneList.add(profile.phone);
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

    /**
     * 第一个报名时，给发布者的app发消息
     *
     * @param event
     */
    public void sendAppMsg(Event event, Byte verifyType, User profile) {
        Integer fromUid = EnumIMType.ACTIVE.getValue();
        Integer toUid = event.getPublishUid();
        String content = "";
        //是否需要审核
        if (verifyType == SqlEnum.RecordVerifyType.TYPE_VERIFY_YES.getByteValue()) {
            content = profile.name + AppMsg.FIRST_SIGNUP_CHECK[0] + event.getTitle() + AppMsg.FIRST_SIGNUP_CHECK[1];
        } else if (verifyType == SqlEnum.RecordVerifyType.TYPE_VERIFY_NO.getByteValue()) {
            content = profile.name + AppMsg.FIRST_SIGNUP[0] + event.getTitle() + AppMsg.FIRST_SIGNUP[1];
        }
        smsRemoteService.sendAppMsg(content, fromUid, toUid);
    }

    public void saveSysMsg(Event event) {
        EventSysMessage message = new EventSysMessage();
        message.setEventId(event.getId());
        message.setCreateTime(DateUtil.getNowDate().getTime());
        message.setModifyTime(DateUtil.getNowDate().getTime());
        message.setOperatorUid(0);
        message.setIsread(SqlEnum.SysMsgRead.TYPE_HASNOTREAD.getByteValue());
        message.setState(SqlEnum.SysMsgState.TYPE_NORMAL.getByteValue());
        message.setOrgId(eventMapper.selectByPrimaryKey(event.getId()).getOrgId());
        message.setSendUid(event.getPublishUid());
        message.setConfirmType(SqlEnum.ConfirmType.TYPE_SIGNUP_FIRST.getByteValue());
        eventSysMessageMapper.insertSelective(message);
        SysUserRelationReq relationReq = new SysUserRelationReq();
        relationReq.eventId = event.getId();
        relationReq.messageId = message.getId();
        sysMsgService.insertRelation(relationReq);
    }

    public ExtensionLink getExtensionLink(String uniqueKey1) {
        //如果直接传入的就是活动的id，且没有加密
        Integer eventId;
        if (uniqueKey1.matches("[0-9]\\d*")) {
            eventId = Integer.parseInt(uniqueKey1);
            if (NumberUtil.isValidId(eventId)) {
                return extensionLinkService.getDefaultLinkByEventId(eventId);
            }
        }
        //如果直接传入的是加密的活动id
        String uniqueKey = Base64Utils.decodeToString(uniqueKey1);
        if (uniqueKey.matches("[0-9]\\d*")) {
            eventId = Integer.parseInt(uniqueKey);
            if (NumberUtil.isValidId(eventId)) {
                return extensionLinkService.getDefaultLinkByEventId(eventId);
            }
        }
        ExtensionLinkResp extensionLinkResp;
        if (!JSONUtil.validate(uniqueKey)) {
            return extensionLinkService.getLinkByUniqueKey(uniqueKey.trim().split("&")[0]);
        }
        try {
            extensionLinkResp = JSONUtil.json2Obj(uniqueKey, ExtensionLinkResp.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw ExceptionUtil.createParamException("解析错误");
        }
        if (extensionLinkResp == null) {
            throw ExceptionUtil.createParamException("链接错误");
        }
        //如果传入了唯一key
        if (!StringUtil.isEmpty(extensionLinkResp.uniqueKey)) {

            return extensionLinkService.getLinkByUniqueKey(extensionLinkResp.uniqueKey);

        } else if (NumberUtil.isValidId(extensionLinkResp.aid)) {
            return extensionLinkService.getDefaultLinkByEventId(extensionLinkResp.aid);
        } else {
            throw ExceptionUtil.createParamException("链接错误");
        }
    }

    public long getRecordCountByEventIdAndLevel(Integer eventId, ParamEnum.ProjectRecordListGetType level) {
        List<Byte> integers = getStatesByLevel(level);
        EventRecordExample eventRecordExample = new EventRecordExample();
        eventRecordExample.createCriteria().andEventIdEqualTo(eventId).andStateIn(integers).
                andTicketLockEqualTo(SqlEnum.TicketLockType.TYPE_NORMAL.getValue());
        return eventRecordMapper.countByExample(eventRecordExample);
    }

    public List<Byte> getStatesByLevel(ParamEnum.ProjectRecordListGetType level) {
        List<Byte> integers = new ArrayList<>();
        switch (level) {
            case TYPE_ALL:
                integers.add(SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getValue().byteValue());
                integers.add(SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY.getValue().byteValue());
                integers.add(SqlEnum.EventRecordType.TYPE_EVENT_VERIFY_FAILED.getValue().byteValue());
                integers.add(SqlEnum.EventRecordType.TYPE_EVENT_USER_DELETED.getValue().byteValue());
                integers.add(SqlEnum.EventRecordType.TYPE_EVENT_PLATFORM_DELETED.getValue().byteValue());
                break;
            case TYPE_AUDITED:
                integers.add(SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getValue().byteValue());
                break;
            case TYPE_AUDITED_AND_WAIT_VERIFY:
                integers.add(SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getValue().byteValue());
                integers.add(SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY.getValue().byteValue());
                break;
            case TYPE_AUDITED_AND_WAIT_VERIFY_FAILED:
                integers.add(SqlEnum.EventRecordType.TYPE_EVENT_RECORD_SUCCESS.getValue().byteValue());
                integers.add(SqlEnum.EventRecordType.TYPE_EVENT_WAIT_VERIFY.getValue().byteValue());
                integers.add(SqlEnum.EventRecordType.TYPE_EVENT_VERIFY_FAILED.getValue().byteValue());
                break;
        }
        return integers;
    }

    /**
     * 拿到recordId之后加密生成短连接
     */
    public void saveTicketRecordUrl(Integer recordId) {
        UrlReq urlReq = new UrlReq();
        urlReq.type = UrlType.NORMAL;
        urlReq.source = cloudEventConfig.getUrl_req_source();
        urlReq.url = cloudEventConfig.getLink_ticket_prefix() +
                Base64Utils.encode(recordId.toString().getBytes()).toString();
        BaseResult<Url> baseResult = urlClient.get(urlReq);
        if (!baseResult.isSuccess()) {
            logger.debug("baseResult", baseResult.getErrormsg());
            throw ExceptionUtil.createParamException("创建短链失败");
        }
        EventRecord eventRecordReq = new EventRecord();
        eventRecordReq.setId(recordId);
        eventRecordReq.setTicketUrl(baseResult.getData().shortUrl);
        eventRecordMapper.updateByPrimaryKeySelective(eventRecordReq);
    }
}
