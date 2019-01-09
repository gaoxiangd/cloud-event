package com.welian.service.impl;

import com.welian.beans.cloudevent.ProjectModule;
import com.welian.mapper.EventStateProjectMapper;
import com.welian.pojo.BaseModule;
import com.welian.pojo.EventStateProject;
import com.welian.pojo.EventStateProjectExample;
import com.welian.utils.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dayangshu on 17/7/7.
 */
@Service("stateProjectModuleService")
public class StateProjectModuleServiceImpl extends BaseModule<ProjectModule>{

    private static final Logger logger = LoggerFactory.getLogger(StateProjectModuleServiceImpl.class);
    @Autowired
    private EventStateProjectMapper eventStateProjectMapper;


    public void save(Integer eventId, ProjectModule projectModule) {
            EventStateProject eventStateProject = new EventStateProject();
            eventStateProject.setEventId(eventId);
            eventStateProject.setCostType(projectModule.isFree);
            eventStateProject.setGroupChatState(projectModule.isGroupChat);
            eventStateProject.setVerifyType(projectModule.isProjectVerify);
            eventStateProject.setOpenSignUpList(projectModule.isPublicity);
            eventStateProject.setProjectInputType(projectModule.projectType);
            eventStateProject.setGroupChat(projectModule.groupChatNumber);
            eventStateProject.setCreateTime(System.currentTimeMillis());
            eventStateProject.setModifyTime(System.currentTimeMillis());
            eventStateProject.setProjectNumberState(projectModule.numberStatus);
            eventStateProjectMapper.insertSelective(eventStateProject);
    }


    public EventStateProject get(Integer eventId) {
        EventStateProjectExample eventStateProjectExample = new EventStateProjectExample();
        eventStateProjectExample.createCriteria().andEventIdEqualTo(eventId);
        List<EventStateProject> eventStateProjects = eventStateProjectMapper.selectByExample(eventStateProjectExample);
        if (eventStateProjects == null || eventStateProjects.isEmpty()) {
            throw ExceptionUtil.createParamException("活动的项目模块异常，请重试");
        }
        EventStateProject eventStateProject = eventStateProjects.get(0);
        return eventStateProject;
    }


    public ProjectModule conversePara(Object object) {
        ProjectModule projectModuleReq = new ProjectModule();
        if (object != null) {
            EventStateProject eventStateProject = (EventStateProject) object;
            projectModuleReq.isFree = eventStateProject.getCostType();
            projectModuleReq.isGroupChat = eventStateProject.getGroupChatState();
            projectModuleReq.isProjectVerify = eventStateProject.getVerifyType();
            projectModuleReq.isPublicity = eventStateProject.getOpenSignUpList();
            projectModuleReq.projectFreeCount = 0;
            projectModuleReq.projectType = eventStateProject.getProjectInputType();
            projectModuleReq.numberStatus=eventStateProject.getProjectNumberState();
            projectModuleReq.signUpTotal=eventStateProject.getJoinedCount();
        }
        return projectModuleReq;
    }


    public void delete(Integer eventId) {
        EventStateProjectExample example = new EventStateProjectExample();
        example.createCriteria().andEventIdEqualTo(eventId);
        eventStateProjectMapper.deleteByExample(example);
    }


    public void updateRecordCount(Integer eventId, Integer count) {
        EventStateProject eventStateProject = (EventStateProject) get(eventId);
        updateRecordCount(eventStateProject, count);
    }


    public void updateRecordCount(EventStateProject eventStateProject, Integer count) {
        //数量不能小于0
        if(eventStateProject.getJoinedCount()+count<0){
            logger.info("报名数量不能小于0");
            return;
        }
        int result = eventStateProjectMapper.updateRecordCount(eventStateProject.getEventId(), count);
        if (result == 0) {
            throw ExceptionUtil.createParamException("更新报名数量失败");
        }
    }


    public void updateStateProject(ProjectModule projectModule, Integer eventId) {
        EventStateProjectExample example = new EventStateProjectExample();
        example.createCriteria().andEventIdEqualTo(eventId);
        EventStateProject eventStateProjectTmp = eventStateProjectMapper.selectByExample(example).get(0);
        if (eventStateProjectTmp != null && projectModule != null) {
            EventStateProject eventStateProject = new EventStateProject();
            eventStateProject.setId(eventStateProjectTmp.getId());
            eventStateProject.setCostType(projectModule.isFree);
            eventStateProject.setGroupChatState(projectModule.isGroupChat);
            eventStateProject.setVerifyType(projectModule.isProjectVerify);
            eventStateProject.setOpenSignUpList(projectModule.isPublicity);
            eventStateProject.setProjectInputType(projectModule.projectType);
            eventStateProject.setGroupChat(projectModule.groupChatNumber);
            eventStateProject.setProjectNumberState(projectModule.numberStatus);
            eventStateProject.setModifyTime(System.currentTimeMillis());
            eventStateProjectMapper.updateByPrimaryKeySelective(eventStateProject);
        }
    }
}