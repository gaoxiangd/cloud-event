package com.welian.beans.cloudevent.record;


import com.welian.commodity.beans.CouponRule;

import java.util.List;

/**
 * Created by memorytale on 2017/5/3.
 */
public class ProjectSignUpResp  {

    public Integer signUpId;

    public Integer pid;

    /**
     * 0-正常,1-用户主动取消报名,2-主办方删除报名记录,3-待审核,4-审核拒绝
     */
    public Byte recordStatus;

    public Long startTime;

    public Long endTime;

    public KeyResp upload;

    public Integer eventId;

    public Integer recordId;

    /**
     * 订单号
     */
    public String tradeNo;

    /**
     * A 3.8.0 优惠券相关信息
     */
    public List<CouponRule> coupons;

    /**
     * 用户报名后返回uid
     */
    public Integer uid;
}
