package com.welian.service.impl;

import com.welian.beans.account.Organization;
import com.welian.beans.cloudevent.customweb.CustomWebResp;
import com.welian.beans.cloudevent.org.OrgInfoResp;
import com.welian.beans.cloudevent.record.EventDetailResp;
import com.welian.mapper.EventCustomWebMapper;
import com.welian.mapper.EventMapper;
import com.welian.pojo.Event;
import com.welian.pojo.EventCustomWeb;
import com.welian.pojo.EventCustomWebExample;
import com.welian.pojo.ExtensionLink;
import com.welian.service.record.impl.IEventRecordProjectInfoImpl;
import org.sean.framework.util.StringUtil;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("eventCustomWebService")
public class EventCustomWebServiceImpl{

    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private ExtensionLinkServiceImpl extensionLinkService;
    @Autowired
    private OrgPrivilegeRemoteServiceImpl orgPrivilegeRemoteService;
    @Autowired
    private EventCustomWebMapper eventCustomWebMapper;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private IEventRecordProjectInfoImpl iEventRecordProjectInfo;


    public void save(String content, Integer eventId) {
        Event event = eventService.getEvent(eventId);
        if (event == null) {
            throw ExceptionUtil.createParamException("活动不存在");
        }
//        if (event.getState() ==
//                SqlEnum.EventStateType.TYPE_EVENT_DELETED.getValue().intValue()) {
//            throw ExceptionUtil.createParamException("活动已被删除");
//        }
        EventCustomWebExample eventCustomWebExample = new EventCustomWebExample();
        eventCustomWebExample.createCriteria().andEventIdEqualTo(eventId).andStateEqualTo((byte) 0);
        EventCustomWeb eventCustomWeb = new EventCustomWeb();
        eventCustomWeb.setContent(content);
        eventCustomWeb.setModifyTime(System.currentTimeMillis());
        int result = eventCustomWebMapper.updateByExampleSelective(eventCustomWeb, eventCustomWebExample);
        if (result == 0) {
            eventCustomWeb.setCreateTime(System.currentTimeMillis());
            eventCustomWeb.setEventId(eventId);
            eventCustomWeb.setState((byte) 0);
            int result1 = eventCustomWebMapper.insertSelective(eventCustomWeb);
            if (result1 == 0) {
                throw ExceptionUtil.createParamException("保存失败");
            }
        }
    }


    public Object getByEventId(Integer eventId) {
        CustomWebResp customWebResp = new CustomWebResp();
        EventCustomWebExample eventCustomWebExample = new EventCustomWebExample();
        eventCustomWebExample.createCriteria().andEventIdEqualTo(eventId).andStateEqualTo((byte) 0);
        List<EventCustomWeb> eventCustomWebs = eventCustomWebMapper.selectByExampleWithBLOBs(eventCustomWebExample);
        //如果平台方没有设置专题官网，会传给前端活动的详情信息，用户渲染默认的专题官网
        ExtensionLink extensionLink = extensionLinkService.getDefaultLinkByEventId(eventId);
        EventDetailResp eventDetailResp = (EventDetailResp) iEventRecordProjectInfo.getEventProjectInfoForDetail(extensionLink);
        eventDetailResp.detail = null;//detail不用传给前端，没有用途，而且很大
        customWebResp.event = eventDetailResp;
        Event event=eventMapper.selectByPrimaryKey(eventId);
        if(event==null){
            throw ExceptionUtil.createParamException("活动不存在");
        }
        Organization organization=orgPrivilegeRemoteService.getOrgById(event.getOrgId());
        OrgInfoResp org=new OrgInfoResp();
        org.logo=organization.logo;
        customWebResp.org=org;
        if (!StringUtil.isEmpty(eventCustomWebs)) {
            customWebResp.content = eventCustomWebs.get(0).getContent();
        }
        return customWebResp;
    }


    public Object getByUniqueKey(String uniqueKey) {
        ExtensionLink extensionLink = iEventRecordProjectInfo.getExtensionLink(uniqueKey);
        return getByEventId(extensionLink.getEventId());
    }

}
