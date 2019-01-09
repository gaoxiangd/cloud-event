package com.welian.beans.cloudevent.statistical;


import java.util.List;

/**
 * Created by zhaopu on 2017/7/3.
 */
public class StatisticReq  {

    public Integer orgId; //机构id

    public Integer eventId; //活动id

    public Integer days; //天数

    public Long startTime;//请求过去多少天的数据

    public Long endTime;//请求过去多少天的数据

    public List<Integer> eventIds;//活动ids
}
