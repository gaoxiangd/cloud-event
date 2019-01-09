package com.welian.service;

import org.sean.framework.bean.BaseResult;
import com.welian.beans.account.AppKeyResp;
import com.welian.beans.cloudevent.ad.AppKeyReq;
import com.welian.beans.cloudevent.org.OrderChannelReq;
import com.welian.beans.cloudevent.project.ProjectScreenResponseCondition;
import com.welian.client.account.AppKeyClient;
import com.welian.enums.account.EnumAppKeyIdType;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Created by Sean.xie on 2017/3/14.
 */
@Service
public class APIService {
    @Autowired
    private AppKeyClient appKeyClient;

    @Autowired
    private ProjectService projectService;


    public Object getToken(AppKeyReq req) {
        com.welian.beans.account.AppKeyReq appKeyReq = new com.welian.beans.account.AppKeyReq();
        appKeyReq.idType = EnumAppKeyIdType.CHANNEL_ID.getValue();
        appKeyReq.appKey = req.appKey;
        appKeyReq.appSecret = req.appSecret;
        BaseResult<AppKeyResp> baseResult = appKeyClient.getTokenByAppKeyAndSecret(appKeyReq);
        if (baseResult.isSuccess() && baseResult.getData() != null) {
            AppKeyResp resp = new AppKeyResp();
            resp.token = baseResult.getData().token;
            return resp;
        } else {
            throw ExceptionUtil.createParamException("请求token失败");
        }
    }

    public Object getProjectListByAppKey(AppKeyReq req) {
        com.welian.beans.account.AppKeyReq appKeyReq = new com.welian.beans.account.AppKeyReq();
        appKeyReq.token = req.token;
        BaseResult<AppKeyResp> baseResult = appKeyClient.getRelationIdByToken(appKeyReq);
        if (baseResult.isSuccess() && baseResult.getData() != null && baseResult.getData().relationId != null) {
            Integer relationId = baseResult.getData().relationId;
            OrderChannelReq resp = new OrderChannelReq();
            resp.orgId = relationId;
            resp.page = req.getPage();
            resp.size = req.getSize();
            resp.type = 1;

            ProjectScreenResponseCondition condition = new ProjectScreenResponseCondition();
            condition.needFeedbackCount = false;
            condition.needProjectCreateUserAvatar = false;
            condition.needStar = false;
            condition.needSignUpId = false;
            return projectService.getProjectList(resp, condition);
        } else {
            throw ExceptionUtil.createParamException("请求token失败");
        }
    }
}
