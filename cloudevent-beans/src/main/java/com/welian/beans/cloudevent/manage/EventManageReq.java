package com.welian.beans.cloudevent.manage;

import com.welian.beans.cloudevent.BasePageReq;

/**
 * Description: 创建或者修改机构接口的请求参数
 * 用于传递参数
 * Created by GaoXiang on 2017/10/10.
 */
public class EventManageReq extends BasePageReq {

    public Integer eventId;

    public String  eventName;

    public Integer cityId;

    /**
     * -2.全部 0.未推荐 1.已推荐
     */
    public Integer recommend;

    /**
     * -2.全部 0.普通  1.热门
     */
    public Integer hot;

    /**
     * -2.全部 0.普通  1.推荐创业圈
     */
    public Integer recommendHomeStatus;

    /**
     * -2.全部 0.普通  1.推荐投融页
     */
    public Integer recommendFinnacingStatus;

    /**
     * 时间筛选的类型 1.创建时间 2.开始时间 3.结束时间 4.截止时间
     */
    public Integer timeType;

    public String startTime;

    public String endTime;

    /**
     * 排序类型 1.按活动创建时间 2.按活动开始时间
     */
    public Integer orderType;


    /**
     *  0.线下 1.线上
     */
    public Integer lineType;

    /**
     * 运营后台使用-操作者的uid
     */
    public Integer adminUid;


    public Integer state;
    /**
     * 3.4.0加：1.普通活动  2.路演大赛
     */
    public Integer eventType;

    /**
     * 是否取截止日志之前
     */
    public Boolean beforeDeadLineTime;

    /**
     * 操作（0正常 1屏蔽）
     */
    public Integer operate;

}
