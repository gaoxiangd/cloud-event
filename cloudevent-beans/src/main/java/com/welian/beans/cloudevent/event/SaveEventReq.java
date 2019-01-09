package com.welian.beans.cloudevent.event;


/**
 * Description: 活动对象
 * Created by dayangshu on 2017/7/4.
 */
public class SaveEventReq  {

    public Integer orgId;

    /**
     * 用户id，导活动时使用
     */

    public EventPara event;

    /**
     * 运营后台使用-操作者的uid
     */
    public Integer adminUid;

    /**
     * 区别请求来源
     * 1.运营后台 2.云活动平台
     */
    public Integer requestSource;


}
