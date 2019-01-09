package com.welian.beans.cloudevent.checkIn;

import java.util.List;

/**
 * Created by zhaopu on 2017/12/4.
 */
public class CheckInReq  {

    public Integer eventId;

    public String code; //权限密码

    public Integer uid;

    /**
     * 此ticketId实际上是商品明细号（对应表的commodity_detail_id）
     */
    public Integer ticketId; //票id

    public String tradeNo; //订单号

    public String unionid;

    public String openid;


    /** 活动券平台兼容需要 **/
    public  Long startTime;

    public Long endTime;

    public List<Integer> eventIds;

    public List<Integer> uids;

    public Integer size;

    public String mobile; //手机号

    /**
     * A 3.7.8 票券号，用于找到导入的票券信息
     */
    public String ticketNo;


}
