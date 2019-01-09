package com.welian.beans.cloudevent.http;

import com.welian.beans.cloudevent.BasePageReq;
import com.welian.beans.cloudevent.user.UserResp;

import java.util.List;

/**
 * Created by memorytale on 2017/5/4.
 */
public class HttpBatchOrderReq extends BasePageReq {

    public Integer pid;

    public Integer orgId;

    public List<UserResp> investors;

}
