package com.welian.beans.cloudevent.coupon;

import org.sean.framework.bean.PageReq;

/**
 * created by GaoXiang on 2018/8/9
 */
public class CouponReq extends PageReq{

    /**
     * 活动id
     */
    public Integer eventId;

    /**
     * 订单号
     */
    public String tradeNo;

    /**
     *部分渠道需要该字段获取活动id
     */
    public String uniqueKey;

    /**
     * 手机号
     */
    public String mobile;

    public Integer ruleId;

    /**
     * 1.签到成功 2.报名成功
     */
    public Integer type;

    public Integer uid;

}
