package com.welian.beans.cloudevent.record;

import com.welian.beans.cloudevent.project.BPResp;
import com.welian.beans.cloudevent.project.ProjectBaseReq;
import com.welian.beans.cloudevent.project.ProjectFinancingInfoReq;

/**
 * Created by memorytale on 2017/5/3.
 */
public class ProjectSignUpReq extends SignUpReq {

    /**
     * 前端传入当前的项目模板类型，2表示简单版，1表示详细版的，只有在路演大赛活动时必传
     */
    public Integer projectType;

    public ProjectBaseReq project;

    public ProjectFinancingInfoReq financingInfo;

    public BPResp bp;

    //app 报名使用参数
    public Integer eventId;

    public Integer pid;

    /**
     * 是否需要电脑上传的的key值
     */
    public Integer needKey;
}
