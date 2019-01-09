package com.welian.beans.cloudevent.record;

import com.welian.commodity.beans.CouponRule;

import java.util.List;

/**
 * created by GaoXiang on 2017/12/8
 */
public class CustomSignUpResp  {

    public String tradeNo;

    public Integer recordId;
    /**
     * base64加密后的recordId字符串
     */
    public String recordIdStr;

    /*********app端用数据**********/

    /**
     * 0-正常,1-用户主动取消报名,2-主办方删除报名记录,3-待审核,4-审核拒绝
     */
    public Byte recordStatus;

    public Long startTime;

    public Long endTime;

    /**
     * A 3.8.0 优惠券相关信息
     */
    public List<CouponRule> coupons;

    /**
     * 用户报名以后返回的uid
     */
    public Integer uid;
}
