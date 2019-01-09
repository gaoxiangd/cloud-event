package com.welian.beans.cloudevent.coupon;

import com.welian.commodity.beans.CouponRule;
import org.sean.framework.bean.BaseBean;

import java.util.List;

/**
 * created by GaoXiang on 2018/8/9
 */
public class CouponListResp extends BaseBean {
    /**
     * 票券列表
     */
    public List<CouponRule> list;
    /**
     * 票券数量
     */
    public Integer count;

}
