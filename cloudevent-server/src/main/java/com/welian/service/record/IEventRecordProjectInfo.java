package com.welian.service.record;

import com.welian.beans.cloudevent.Base64KeyReq;
import com.welian.beans.cloudevent.ExtensionLinkReq;
import com.welian.beans.cloudevent.http.HttpProjectOrderReturnResultResp;
import com.welian.beans.cloudevent.record.ProjectSignUpReq;
import com.welian.beans.cloudevent.record.ProjectSignUpResp;
import com.welian.beans.cloudevent.user.EventServiceReq;
import com.welian.enums.cloudevent.ParamEnum;
import com.welian.pojo.Event;
import com.welian.pojo.EventRecord;
import com.welian.pojo.ExtensionLink;

import java.util.List;

/**
 * Created by memorytale on 2017/7/13.
 */
public interface IEventRecordProjectInfo {

    /**
     * @param req
     * @return
     */
    Object getEventProjectInfoForDetail(ExtensionLink req);

    /**
     * @param req
     * @return
     */
    Object getEventProjectInfoForForm(Base64KeyReq req);

    /**
     * 获取活动报名的项目列表
     *
     * @param req
     * @return
     */
    Object getEventProjectList(EventServiceReq req);

    /**
     * 批量投递项目给投资人
     *
     * @param req
     * @return
     */
    HttpProjectOrderReturnResultResp batchOrderFromChannel(ExtensionLinkReq req);

    /**
     * 更新报名的项目的bp信息，此接口是应用场景
     *
     * @param req
     * @return
     */
    Object updateSignUpBP(ExtensionLinkReq req);

    /**
     * 更新报名的项目的bp信息，此接口是应用场景
     *
     * @param req
     * @return
     */
    Object updateSignUpBPByRecordId(ExtensionLinkReq req);

    /**
     * @param uniqueKey1
     * @return
     */
    ExtensionLink getExtensionLink(String uniqueKey1);

    long getRecordCountByEventIdAndLevel(Integer eventId, ParamEnum.ProjectRecordListGetType typeAuditedAndWaitVerify);

    ProjectSignUpResp signUp(ProjectSignUpReq req, Event event, Integer moduleType);

    ProjectSignUpResp saveProjectAndSignUp(ProjectSignUpReq req, Event event, Integer moduleType);

    void signUpCancel(Integer recordId);

    List<EventRecord> getRecordListByEventIdAndLevel(Integer eventId, Integer page,
                                                     Integer size, String orderClause, ParamEnum
                                                             .ProjectRecordListGetType level);
}
