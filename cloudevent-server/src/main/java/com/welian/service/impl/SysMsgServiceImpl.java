package com.welian.service.impl;

import com.welian.beans.account.User;
import com.welian.beans.cloudevent.org.OrgPageReq;
import com.welian.beans.cloudevent.org.OrgReq;
import com.welian.beans.cloudevent.org.OrgResp;
import com.welian.beans.cloudevent.sys.SysMessagesReq;
import com.welian.beans.cloudevent.sys.SysMsgListResp;
import com.welian.beans.cloudevent.sys.SysMsgReadResp;
import com.welian.beans.cloudevent.sys.SysMsgResp;
import com.welian.beans.cloudevent.sys.SysUserRelationReq;
import com.welian.config.CloudEventConfig;
import com.welian.config.CloudSysMsg;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.mapper.EventMapper;
import com.welian.mapper.EventStateCustomMapper;
import com.welian.mapper.EventStateProjectMapper;
import com.welian.mapper.EventSysMessageMapper;
import com.welian.mapper.SysMessageUserRelationMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventExample;
import com.welian.pojo.EventStateCustom;
import com.welian.pojo.EventStateCustomExample;
import com.welian.pojo.EventStateProject;
import com.welian.pojo.EventStateProjectExample;
import com.welian.pojo.EventSysMessage;
import com.welian.pojo.EventSysMessageExample;
import com.welian.pojo.SysMessageUserRelation;
import com.welian.pojo.SysMessageUserRelationExample;
import com.welian.service.UserService;
import com.welian.utils.ExceptionUtil;
import com.welian.utils.PagingUtil;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import org.sean.framework.web.RequestHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by GaoXiang on 2017/8/18
 */
@Service("sysMsgService")
public class SysMsgServiceImpl {

    @Autowired
    private EventSysMessageMapper eventSysMessageMapper;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private CloudEventConfig cloudEventConfig;
    @Autowired
    private UserService userService;
    @Autowired
    private OrgPrivilegeRemoteServiceImpl orgPrivilegeRemoteService;
    @Autowired
    private SysMessageUserRelationMapper sysMessageUserRelationMapper;
    @Autowired
    private EventStateProjectMapper eventStateProjectMapper;
    @Autowired
    private EventStateCustomMapper eventStateCustomMapper;
    @Autowired
    private StateProjectModuleServiceImpl stateProjectModuleService;
    @Autowired
    private StateCustomModuleService stateCustomModuleService;
    @Autowired
    private EventServiceImpl eventService;



    public SysMsgListResp getMsgList(OrgReq req, Integer uid) {
        // 获得角色信息
        req.roleType = orgPrivilegeRemoteService.getRoleTypeByUid(req.orgId, uid).byteValue();
        EventSysMessageExample example = new EventSysMessageExample();
        example.createCriteria().andOrgIdEqualTo(req.orgId).andStateEqualTo(SqlEnum.SysMsgState.TYPE_NORMAL
                .getByteValue());
        example.setOrderByClause("create_time desc");
        if (NumberUtil.isValidId(req.getSize())) {
            example.setLimit(req.getSize());
            if (NumberUtil.isValidId(req.getPage())) {
                example.setOffset(PagingUtil.getStart(req.getPage(), req.getSize()));
            }
        }
        List<EventSysMessage> list = eventSysMessageMapper.selectByExample(example);
        List<SysMsgResp> sysMsgResps = new ArrayList<>();
        SysMsgListResp sysMsgListResp = new SysMsgListResp();
        if (StringUtil.isEmpty(list)) {
            sysMsgListResp.count = 0;
            sysMsgListResp.list = new ArrayList<>();
            return sysMsgListResp;
        }
        List<Integer> uidList = new ArrayList<>();
        for (EventSysMessage eventSysMessage : list) {
            uidList.add(eventSysMessage.getSendUid());
        }
        //拿到该机构下所有的message
        Map<Integer, SysMessageUserRelation> messagesMap = new HashMap<>();
        SysMessageUserRelationExample relationExample = new SysMessageUserRelationExample();
        relationExample.createCriteria().andOrgIdEqualTo(req.orgId).andUidEqualTo(uid);
        List<SysMessageUserRelation> msgs = sysMessageUserRelationMapper.selectByExample(relationExample);
        if (!StringUtil.isEmpty(msgs)) {
            for (SysMessageUserRelation msg : msgs) {
                messagesMap.put(msg.getMessageId(), msg);
            }
        }
        //拿到机构下所有活动的活动状态
        Map<Integer, Byte> eventStateMap = getStateMapsByOrgId(req.orgId);
        for (EventSysMessage eventSysMessage : list) {
            SysMsgResp sysMsgResp = new SysMsgResp();
            sysMsgResp.sendName = cloudEventConfig.getSys_message_send_name();
            sysMsgResp.logo = cloudEventConfig.getSys_message_send_logo();
            sysMsgResp.read = getRead(messagesMap, eventSysMessage);
            sysMsgResp.createTime = eventSysMessage.getCreateTime();
            sysMsgResp.messageId = eventSysMessage.getId();
            sysMsgResp.type = eventSysMessage.getConfirmType();
            Byte type = eventSysMessage.getConfirmType();
            //区分机构信息eventId=0的情况
            if (type.equals(SqlEnum.ConfirmType.TYPE_RECOMMEND_HOT.getByteValue()) ||
                    type.equals(SqlEnum.ConfirmType.TYPE_RECOMMEND_SUCCESS.getByteValue())) {
                Event event = eventMapper.selectByPrimaryKey(eventSysMessage.getEventId());
                sysMsgResp.eventId = event.getId();

            }
            String title = getTitleSimple(eventSysMessage, uidList, eventStateMap);
            sysMsgResp.title = title;
            sysMsgResps.add(sysMsgResp);

        }

        sysMsgListResp.list = sysMsgResps;
        EventSysMessageExample example2 = new EventSysMessageExample();
        example2.createCriteria().andOrgIdEqualTo(req.orgId).andStateEqualTo(SqlEnum.SysMsgState.TYPE_NORMAL
                .getByteValue().byteValue());
        sysMsgListResp.count = (int) eventSysMessageMapper.countByExample(example2);
        /*获取详情完成后把所有消息设为已读*/
        //eventSysMessageMapper.updateRead(SqlEnum.SysMsgRead.TYPE_HASREAD.getByteValue().byteValue(), req.orgId);
        return sysMsgListResp;
    }

    private Map<Integer, Byte> getStateMapsByOrgId(Integer orgId) {
        Map<Integer, Byte> maps = new HashMap<>();
        EventExample eventExample = new EventExample();
        eventExample.createCriteria().andOrgIdEqualTo(orgId);
        List<Integer> eventIds = new ArrayList<>();
        List<Event> events = eventMapper.selectByExample(eventExample);
        if (!StringUtil.isEmpty(events)) {
            for (Event event : events) {
                eventIds.add(event.getId());
            }
            EventStateProjectExample example = new EventStateProjectExample();
            example.createCriteria().andEventIdIn(eventIds);
            List<EventStateProject> eventStateProjects = eventStateProjectMapper.selectByExample(example);
            for (EventStateProject eventStateProject : eventStateProjects) {
                maps.put(eventStateProject.getEventId(), eventStateProject.getVerifyType());
            }
            EventStateCustomExample customExample = new EventStateCustomExample();
            customExample.createCriteria().andEventIdIn(eventIds);
            List<EventStateCustom> eventStateCustoms = eventStateCustomMapper.selectByExample(customExample);
            for (EventStateCustom eventStateCustom : eventStateCustoms) {
                maps.put(eventStateCustom.getEventId(), eventStateCustom.getVerifyType());
            }
        }
        return maps;

    }

    private Byte getRead(Map<Integer, SysMessageUserRelation> messagesMap, EventSysMessage eventSysMessage) {
        //默认已读
        Byte read = SqlEnum.SysMsgRead.TYPE_HASREAD.getByteValue();
        if (messagesMap.containsKey(eventSysMessage.getId()) &&
                messagesMap.get(eventSysMessage.getId()).getIsread() == SqlEnum.SysMsgRead.TYPE_HASNOTREAD.getValue()) {
            read = SqlEnum.SysMsgRead.TYPE_HASNOTREAD.getByteValue();
        }
        return read;
    }


    public void deleteMessages(SysMessagesReq req) {
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < req.messageIds.size(); i++) {
            if (i != req.messageIds.size() - 1) {
                sbf.append(req.messageIds.get(i)).append(",");
            } else {
                sbf.append(req.messageIds.get(i));
            }
        }
        if (sbf.length() > 0) {
            eventSysMessageMapper.batchUpdate(SqlEnum.SysMsgState.TYPE_DELETE.getByteValue().byteValue(), sbf
                    .toString());
            //删除所有的sys_message_user_relation里的数据
            SysMessageUserRelationExample example = new SysMessageUserRelationExample();
            example.createCriteria().andMessageIdIn(req.messageIds);
            sysMessageUserRelationMapper.deleteByExample(example);
        }

    }


    public SysMsgResp getMsgDetail(Integer uid, SysMessagesReq req) {
        EventSysMessage eventSysMessage = eventSysMessageMapper.selectByPrimaryKey(req.messageId);
        if (eventSysMessage == null || eventSysMessage.getState() == SqlEnum.SysMsgState.TYPE_DELETE.getByteValue()
                .byteValue()) {
            throw ExceptionUtil.createParamException("该消息已删除");
        }
        SysMsgResp sysMsgResp = new SysMsgResp();
        sysMsgResp.createTime = eventSysMessage.getCreateTime();
        sysMsgResp.messageId = eventSysMessage.getId();
        // 获得角色信息
        req.roleType = orgPrivilegeRemoteService.getRoleTypeByUid(eventSysMessage.getOrgId(), uid).byteValue();
        /*发送消息人的信息*/
        sysMsgResp.sendName = cloudEventConfig.getSys_message_send_name();
        sysMsgResp.logo = cloudEventConfig.getSys_message_send_logo();
        Byte type = eventSysMessage.getConfirmType();
        //区分机构信息eventId=0的情况
        if (type.equals(SqlEnum.ConfirmType.TYPE_RECOMMEND_HOT.getByteValue()) ||
                type.equals(SqlEnum.ConfirmType.TYPE_RECOMMEND_SUCCESS.getByteValue()) ||
                type.equals(SqlEnum.ConfirmType.TYPE_SIGNUP_FIRST.getByteValue())) {
            Event event = eventMapper.selectByPrimaryKey(eventSysMessage.getEventId());
            sysMsgResp.eventId = event.getId();
            sysMsgResp.title = event.getTitle();
        }
        /*活动信息*/
        User profile = userService.getUserInfoById(eventSysMessage.getSendUid());
        String prependText = getTitleDetailPrepend(profile, eventSysMessage);
        sysMsgResp.prependText = prependText;
        String appendText = getTitleDetailAppend(cloudEventConfig.getSys_message_send_name(), eventSysMessage);
        sysMsgResp.appendText = appendText;
        //把这条消息设为已读
        updateRead(req, uid);
        return sysMsgResp;
    }

    private void updateRead(SysMessagesReq req, Integer uid) {
        SysMessageUserRelationExample example = new SysMessageUserRelationExample();
        example.createCriteria().andUidEqualTo(uid).andMessageIdEqualTo(req.messageId);
        if (sysMessageUserRelationMapper.selectByExample(example) != null) {
            SysMessageUserRelation relation = new SysMessageUserRelation();
            relation.setIsread(SqlEnum.SysMsgRead.TYPE_HASREAD.getValue());
            sysMessageUserRelationMapper.updateByExampleSelective(relation, example);
        }
    }


    public SysMsgReadResp countUnReadMsg(OrgResp req) {
        Integer uid = RequestHolder.getVerifiedUid();
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("orgId参数错误");
        }
        SysMessageUserRelationExample example = new SysMessageUserRelationExample();
        example.createCriteria().andOrgIdEqualTo(req.orgId).andIsreadEqualTo(SqlEnum.SysMsgRead.TYPE_HASNOTREAD
                .getValue())
                .andUidEqualTo(uid);
        Integer count = (int) sysMessageUserRelationMapper.countByExample(example);
        SysMsgReadResp sysMsgReadResp = new SysMsgReadResp();
        sysMsgReadResp.count = count;
        return sysMsgReadResp;
    }


    public void insertRelation(SysUserRelationReq req) {
        Integer orgId = req.orgId;
        if (orgId == null) {
            if (req.eventId == null) {
                throw ExceptionUtil.createParamException("参数异常");
            } else {
                orgId = eventMapper.selectByPrimaryKey(req.eventId).getOrgId();
            }
        }
        OrgPageReq orgPageReq = new OrgPageReq();
        orgPageReq.orgId = orgId;
        orgPageReq.page = 1;
        orgPageReq.size = 100;
        List<User> users = orgPrivilegeRemoteService.getOrgMemberList(orgPageReq).getList();
        List<SysMessageUserRelation> sysMessageUserRelations = new ArrayList<>();
        for (User user : users) {
            SysMessageUserRelation relation = new SysMessageUserRelation();
            relation.setIsread(SqlEnum.SysMsgRead.TYPE_HASNOTREAD.getValue());
            relation.setMessageId(req.messageId);
            relation.setUid(user.uid == null ? user.id : user.uid);
            relation.setOrgId(orgId);
            sysMessageUserRelations.add(relation);
        }
        sysMessageUserRelationMapper.batchInsert(sysMessageUserRelations);

    }

    /**
     * 列表titile拼接
     */
    public String getTitleSimple(EventSysMessage eventSysMessage, List<Integer> uidList, Map<Integer, Byte>
            eventStateMap) {
        Event event = eventMapper.selectByPrimaryKey(eventSysMessage.getEventId());
        StringBuffer sbf = new StringBuffer();
        Map<Integer, User> map = userService.getUserInfoListByIds(uidList);
        String sendName = map.get(eventSysMessage.getSendUid()).name;
        //别人查看
        if (eventSysMessage.getConfirmType() == SqlEnum.ConfirmType.TYPE_RECOMMEND_SUCCESS.getByteValue().byteValue()) {
            sbf.append(sendName + CloudSysMsg.SIMPLE_RECOMMEND[0] + event.getTitle() + CloudSysMsg
                    .SIMPLE_RECOMMEND[1]);
        } else if (eventSysMessage.getConfirmType() == SqlEnum.ConfirmType.TYPE_RECOMMEND_HOT.getByteValue()
                .byteValue()) {
            sbf.append(sendName + CloudSysMsg.SIMPLE_HOT[0] + event.getTitle() + CloudSysMsg
                    .SIMPLE_HOT[1]);
        } else if (eventSysMessage.getConfirmType() == SqlEnum.ConfirmType.TYPE_CHECK_PASS.getByteValue().byteValue()) {
            sbf.append(sendName + CloudSysMsg.SIMPLE_ORG_PASS[0]);
        } else if (eventSysMessage.getConfirmType() == SqlEnum.ConfirmType.TYPE_CHECK_FAIL.getByteValue().byteValue()) {
            sbf.append(sendName + CloudSysMsg.SIMPLE_ORG_REFUSE[0]);
        } else if (eventSysMessage.getConfirmType() == SqlEnum.ConfirmType.TYPE_SIGNUP_FIRST.getByteValue().byteValue
                ()) {
            if (eventStateMap.get(eventSysMessage.getEventId()).equals(SqlEnum.EventVerifyType
                    .TYPE_VERIFY_NO.getByteValue())) {
                sbf.append(sendName + CloudSysMsg.SIMPLE_FIRST_SIGNUP[0] + event.getTitle() + CloudSysMsg
                        .SIMPLE_FIRST_SIGNUP[1]);
            }
            if (eventStateMap.get(eventSysMessage.getEventId()).equals( SqlEnum.EventVerifyType
                    .TYPE_VERIFY_YES.getByteValue())) {
                sbf.append(sendName + CloudSysMsg.SIMPLE_FIRST_SIGNUP_CHECK[0] + event.getTitle() +
                        CloudSysMsg.SIMPLE_FIRST_SIGNUP_CHECK[1]);
            }
        }
        return sbf.toString();
    }

    /**
     * 详细prependText拼接
     */
    public String getTitleDetailPrepend(User profile, EventSysMessage eventSysMessage) {
        StringBuffer sbf = new StringBuffer();
        String name = profile.name;
        if (eventSysMessage.getConfirmType() == SqlEnum.ConfirmType.TYPE_RECOMMEND_SUCCESS.getByteValue().byteValue()) {
            sbf.append(name + CloudSysMsg.PREPEND_RECOMMEND[0]);
        } else if (eventSysMessage.getConfirmType() == SqlEnum.ConfirmType.TYPE_RECOMMEND_HOT.getByteValue()
                .byteValue()) {
            sbf.append(name + CloudSysMsg.PREPEND_HOT[0]);
        } else if (eventSysMessage.getConfirmType() == SqlEnum.ConfirmType.TYPE_CHECK_PASS.getByteValue().byteValue()) {
            sbf.append(name + CloudSysMsg.PREPEND_ORG_PASS[0]);
        } else if (eventSysMessage.getConfirmType() == SqlEnum.ConfirmType.TYPE_CHECK_FAIL.getByteValue().byteValue()) {
            sbf.append(CloudSysMsg.PREPEND_ORG_REFUSE[0] + eventSysMessage.getReason() + CloudSysMsg
                    .PREPEND_ORG_REFUSE[1] + name + CloudSysMsg.PREPEND_ORG_REFUSE[2]);
        } else if (eventSysMessage.getConfirmType() == SqlEnum.ConfirmType.TYPE_SIGNUP_FIRST.getByteValue().byteValue
                ()) {
            sbf.append(name + CloudSysMsg.PREPEND_FIRST_SIGNUP[0]);
        }

        return sbf.toString();
    }

    /**
     * 详细appendText拼接
     */
    public String getTitleDetailAppend(String sendName, EventSysMessage eventSysMessage) {
        Byte verifyType = SqlEnum.RecordVerifyType.TYPE_VERIFY_NO.getByteValue();
        if (eventSysMessage.getEventId() != null && !eventSysMessage.getEventId().equals(0)) {
            Event event = eventService.getEvent(eventSysMessage.getEventId());
            if (event.getTemplateId().equals(SqlEnum.EventType.TYPE_EVENT_COMMON.getValue())) {
                verifyType = stateCustomModuleService.get(event.getId()).getVerifyType();
            } else {
                verifyType = stateProjectModuleService.get(event.getId()).getVerifyType();
            }
        }
        StringBuffer sbf = new StringBuffer();
        if (eventSysMessage.getConfirmType() == SqlEnum.ConfirmType.TYPE_RECOMMEND_SUCCESS.getByteValue().byteValue()) {
            sbf.append(CloudSysMsg.APPEND_RECOMMEND[0] + sendName);
        } else if (eventSysMessage.getConfirmType() == SqlEnum.ConfirmType.TYPE_RECOMMEND_HOT.getByteValue()
                .byteValue()) {
            sbf.append(CloudSysMsg.APPEND_HOT[0] + sendName);
        } else if (eventSysMessage.getConfirmType() == SqlEnum.ConfirmType.TYPE_SIGNUP_FIRST.getByteValue().byteValue
                ()) {
            if (verifyType.equals(SqlEnum.EventVerifyType.TYPE_VERIFY_NO.getByteValue())) {
                sbf.append(CloudSysMsg.APPEND_FIRST_SIGNUP[0] + sendName);
            }
            if (verifyType.equals(SqlEnum.EventVerifyType.TYPE_VERIFY_YES.getByteValue())) {
                sbf.append(CloudSysMsg.APPEND_FIRST_SIGNUP_CHECK[0] + sendName);
            }
        } else {
            sbf.append(CloudSysMsg.APPEND_OTHERS[0] + sendName);
        }

        return sbf.toString();
    }
}
