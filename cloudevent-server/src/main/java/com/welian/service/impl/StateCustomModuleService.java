package com.welian.service.impl;

import com.welian.beans.cloudevent.CustomModule;
import com.welian.mapper.EventStateCustomMapper;
import com.welian.pojo.BaseModule;
import com.welian.pojo.EventStateCustom;
import com.welian.pojo.EventStateCustomExample;
import org.sean.framework.util.StringUtil;
import com.welian.utils.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by GaoXiang on 2017/11/30
 */
@Service("stateCustomModuleService")
public class StateCustomModuleService extends BaseModule<CustomModule> {

    private static final Logger logger= LoggerFactory.getLogger(StateCustomModuleService.class);

    @Autowired
    private EventStateCustomMapper eventStateCustomMapper;


    public void delete(Integer eventId) {

    }


    public EventStateCustom get(Integer eventId) {
        EventStateCustomExample eventStateCustomExample = new EventStateCustomExample();
        eventStateCustomExample.createCriteria().andEventIdEqualTo(eventId);
        List<EventStateCustom> eventStateCustoms = eventStateCustomMapper.selectByExample(eventStateCustomExample);
        if (StringUtil.isEmpty(eventStateCustoms)) {
            throw ExceptionUtil.createParamException("活动的票券模块异常，请重试");
        }
        EventStateCustom eventStateCustom = eventStateCustoms.get(0);
        return eventStateCustom;
    }



    public CustomModule conversePara(Object object) {
        CustomModule customModule=new CustomModule();
        if (object != null) {
            EventStateCustom eventStateCustom = (EventStateCustom) object;
            customModule.isPublicity=eventStateCustom.getOpenSignUpList().intValue();
            customModule.isCharge=eventStateCustom.getCostType().intValue();
            customModule.isGroupChat=eventStateCustom.getGroupChatState().intValue();
            customModule.numberStatus=eventStateCustom.getCustomNumberState().intValue();
            customModule.isCustomVerify=eventStateCustom.getVerifyType().intValue();
            customModule.signUpTotal=eventStateCustom.getJoinedCount();
        }
        return customModule;
    }

    //新建时候调用
    public void save(Integer eventId, CustomModule customModule) {
        EventStateCustom eventStateCustom=new EventStateCustom();
        eventStateCustom.setEventId(eventId);
        eventStateCustom.setCostType(customModule.isCharge.byteValue());
        eventStateCustom.setCreateTime(System.currentTimeMillis());
        eventStateCustom.setModifyTime(System.currentTimeMillis());
        eventStateCustom.setGroupChatState(customModule.isGroupChat.byteValue());
        eventStateCustom.setGroupChat(customModule.groupChatNumber);
        eventStateCustom.setCustomNumberState(customModule.numberStatus.byteValue());
        //3.4.0版本不做审核功能，都插入默认不需要审核
        eventStateCustom.setVerifyType(customModule.isCustomVerify.byteValue());
        eventStateCustom.setOpenSignUpList(customModule.isPublicity.byteValue());
        eventStateCustom.setAuthPassword(customModule.code);
        eventStateCustomMapper.insertSelective(eventStateCustom);
    }


    public void updateStateCustom(CustomModule customModule, Integer eventId) {

        EventStateCustomExample example=new EventStateCustomExample();
        example.createCriteria().andEventIdEqualTo(eventId);
        EventStateCustom  eventStateCustom=eventStateCustomMapper.selectByExample(example).get(0);
        if(customModule.isCharge!=null) {
            eventStateCustom.setCostType(customModule.isCharge.byteValue());
        }
        eventStateCustom.setGroupChatState(customModule.isGroupChat.byteValue());
        eventStateCustom.setOpenSignUpList(customModule.isPublicity.byteValue());
        eventStateCustom.setCustomNumberState(customModule.numberStatus.byteValue());
        eventStateCustom.setVerifyType(customModule.isCustomVerify.byteValue());
        eventStateCustom.setGroupChat(customModule.groupChatNumber);
        eventStateCustomMapper.updateByPrimaryKeySelective(eventStateCustom);

    }

    public void updateRecordCount(EventStateCustom eventStateCustom,Integer totalCount) {
        //数量不能小于0
        if(eventStateCustom.getJoinedCount()+totalCount<0){
            logger.info("报名数量不能小于0");
            return;
        }
        int result = eventStateCustomMapper.updateRecordCount(eventStateCustom.getEventId(),totalCount);
        if (result == 0) {
            throw ExceptionUtil.createParamException("更新报名数量失败");
        }
    }
}
