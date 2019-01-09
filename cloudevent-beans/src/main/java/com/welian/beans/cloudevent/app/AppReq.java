package com.welian.beans.cloudevent.app;


/**
 * Created by zhaopu on 2017/10/16.
 */
public class AppReq  {

    public Integer eventId;
    public Integer type;//0取消收藏，1收藏  *1取收藏的融资活动列表，2取报名活动列表，3取创建的融资活动列表
    public Integer uid;
    public Integer recordId;
    public Integer page;
    public Integer size;

    public Integer eventType;//1:普通活动 2:路演大赛

    public Integer cityId;//城市id

    public Integer ruleId;
    /**
     * 请求来源 1.h5 2.app
     */
    public Integer from;

    public String content;

    public String key;

    /**
     * 代表一种时间状态：0 今天，1 明天，7 最近一周，-2 周末
     */
    public Integer date;
    /**
     * 是否收费 0 免费， 1 收费
     */
    public Integer costType;

    public String ticketNo;

}
