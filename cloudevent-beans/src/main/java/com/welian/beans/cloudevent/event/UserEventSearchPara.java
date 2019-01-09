package com.welian.beans.cloudevent.event;

import java.util.List;

/**
 * Created by zhaopu on 2017/10/19.
 */
public class UserEventSearchPara  {
    public Integer type; //1取收藏的活动列表，2取报名活动列表，3取创建的活动列表

    public Integer page;

    public Integer size;

    public Integer uid;

    public Integer eventType;//1:普通活动 2:路演大赛

    public Integer cityId;

    public Integer ruleId;// 0 推荐排序 1 最新活动 2 最热活动

    public Integer orgId;

    public Long nowTime;// 当前时间


    public List<Integer> uids;

    public List<Integer> eventIds;

    public Long startTime;

    public Long endTime;

    public Integer status;

    public Integer costType;

    public Integer date;

    public String phone; //手机号

    public String name; //姓名

}
