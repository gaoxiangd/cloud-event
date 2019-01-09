package com.welian.beans.cloudevent.http;

import com.welian.beans.cloudevent.project.BPResp;
import com.welian.beans.cloudevent.project.ProjectBaseReq;
import com.welian.beans.cloudevent.project.ProjectFinancingInfoReq;
import com.welian.beans.cloudevent.user.UserReq;

/**
 * Created by memorytale on 2017/5/4.
 */
public class HttpProjectCreateOrUpdateReq {

    public Integer extensionLinkId;
    /**
     * 1表示PC端创建项目，2表示H5创建项目
     */
    public Integer type;

    public ProjectBaseReq project;

    public ProjectFinancingInfoReq financingInfo;

    public BPResp bp;

    public UserReq user;

}
