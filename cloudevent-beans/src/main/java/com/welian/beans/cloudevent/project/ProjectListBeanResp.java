package com.welian.beans.cloudevent.project;

import com.welian.beans.cloudevent.user.UserResp;

/**
 * Created by memorytale on 2017/5/2.
 */
public class ProjectListBeanResp {

    public ProjectResp project;

    public UserResp user;

    public Long activitySignUpTime;

    public String sourceActivityName;

    public String sourceLinkName;

    public Integer star;

    public String remark;

    public Integer investorFeedbackCount;

    public Integer investorReceiveCount;

    public Integer investorInterviewCount;

    public Integer orgId;

    public Integer signUpId;

    public Integer isOpenFinancingService;

    /**
     * 1表示普通活动，2表示路演大赛活动，3表示融资投递活动,
     * 此字段传给前端的原因是让前端判断活动的的类型来隐藏或者显示投资人投递反馈列表以及个数
     */
    public Integer eventType;

}
