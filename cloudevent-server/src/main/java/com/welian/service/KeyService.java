package com.welian.service;

import org.sean.framework.bean.BaseResult;
import com.welian.beans.account.AppKeyResp;
import com.welian.beans.cloudevent.ad.AppKeyReq;
import com.welian.beans.cloudevent.org.OrderChannelReq;
import com.welian.client.account.AppKeyClient;
import com.welian.enums.account.EnumAppKeyIdType;
import com.welian.mapper.OrgInfoMapper;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Created by Sean.xie on 2017/3/14.
 */
@Service
public class KeyService {

    @Autowired
    private AppKeyClient appKeyClient;

    @Autowired
    private OrgInfoMapper orgInfoMapper;

    public Object get(OrderChannelReq req) {
        Integer count = orgInfoMapper.selectByNewId(req.orgId);
        if (count == 0) {
            throw ExceptionUtil.createParamException("机构不存在");
        }
        com.welian.beans.account.AppKeyReq appKeyReq = new com.welian.beans.account.AppKeyReq();
        appKeyReq.idType = EnumAppKeyIdType.CHANNEL_ID.getValue();
        appKeyReq.relationId = req.orgId;
        BaseResult<AppKeyResp> baseResult = appKeyClient.getKeyByRelationId(appKeyReq);
        if (baseResult.isSuccess()) {
            if (baseResult.getData() != null) {
                AppKeyResp resp = new AppKeyResp();
                resp.appSecret = baseResult.getData().appSecret;
                resp.appKey = baseResult.getData().appKey;
                return resp;
            } else {
                throw ExceptionUtil.createParamException("请求key信息失败");
            }
        } else {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
    }

    public Object getSecret(AppKeyReq req) {
        com.welian.beans.account.AppKeyReq appKeyReq = new com.welian.beans.account.AppKeyReq();
        appKeyReq.idType = EnumAppKeyIdType.CHANNEL_ID.getValue();
        appKeyReq.relationId = req.orgId;
        appKeyReq.appKey = req.appKey;
        BaseResult<AppKeyResp> baseResult = appKeyClient.getSecretByAppKey(appKeyReq);
        if (baseResult.isSuccess()) {
            if (baseResult.getData() != null) {
                AppKeyResp resp = new AppKeyResp();
                resp.appSecret = baseResult.getData().appSecret;
                return resp;
            } else {
                throw ExceptionUtil.createParamException("请求key信息失败");
            }
        } else {
            throw ExceptionUtil.createParamException(baseResult.getErrormsg());
        }
    }
}
