package com.welian.service;

import com.welian.beans.cloudevent.coupon.CouponListResp;
import com.welian.beans.cloudevent.coupon.CouponReq;
import com.welian.client.commodity.CouponClient;
import com.welian.client.commodity.PlacementClient;
import com.welian.commodity.beans.Coupon;
import com.welian.commodity.beans.CouponQuery;
import com.welian.commodity.beans.CouponRule;
import com.welian.commodity.beans.CouponSender;
import com.welian.commodity.beans.PlacementQuery;
import com.welian.enums.cloudevent.ParamEnum;
import com.welian.mapper.EventMapper;
import com.welian.pojo.Event;
import org.sean.framework.bean.BaseResult;
import org.sean.framework.bean.PageData;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * created by GaoXiang on 2018/8/7
 */
@Service
public class CouponRemoteService {

    @Autowired
    private CouponClient couponClient;

    @Autowired
    private PlacementClient placementClient;
    @Autowired
    private EventMapper eventMapper;

    /**
     * 优惠券活动报名成功的code
     */
    private static final String EVENT_SIGN_UP_SUCCESS = "event_signup_success";
    private static final String EVENT_SIGN_IN_SUCCESS = "event_detail";

    /**
     * 获取优惠券
     *
     * @param req 优惠券入参
     */
    public CouponListResp getCoupons(CouponReq req) {

        Event event = eventMapper.selectByPrimaryKey(req.eventId);

        CouponListResp couponListResp = new CouponListResp();
        if (event == null) {
            return couponListResp;
        }
        if (event.getCouponStatus() == 0) {
            //获取优惠券
            PlacementQuery placementQuery = new PlacementQuery();
            placementQuery.code = req.type.equals(ParamEnum.CouponType.TYPE_SIGN_IN_SUCCESS.getValue()) ?
                    EVENT_SIGN_IN_SUCCESS : EVENT_SIGN_UP_SUCCESS;
            BaseResult<PageData<CouponRule>> pageDataBaseResult = placementClient.queryCoupons(placementQuery);
            couponListResp.list = pageDataBaseResult.getData().list;
            couponListResp.count = (int) pageDataBaseResult.getData().count;
            if (StringUtil.isNotEmpty(couponListResp.list) && NumberUtil.isValidId(req.uid)) {
                List<Coupon> myCountsByRuleId;
                CouponRule couponRule;
                //不是无限制且该uid领取数量达到(0是无限制)
                if ((couponRule = couponListResp.list.get(0)).eachOwn != 0 && couponRule.eachOwn.intValue()<=(StringUtil.isEmpty
                        (myCountsByRuleId = getMyCouponsByRule(couponRule.id, req.uid)) ? 0 : myCountsByRuleId.size()
                )) {
                    couponListResp.list = new ArrayList<>();
                    couponListResp.count = 0;
                }
            }
        }
        return couponListResp;
    }

    /**
     * 领取优惠券
     *
     * @param req
     * @return
     */
    public Coupon getCoupon(CouponReq req) {
        CouponSender couponSender = new CouponSender();
        couponSender.phone = req.mobile;
        couponSender.ruleId = req.ruleId;
        BaseResult<Coupon> couponResult = couponClient.businessByPhone(couponSender);
        return couponResult.getData();
    }

    /**
     * 获取我的某张优惠券信息
     */
    private List<Coupon> getMyCouponsByRule(Integer ruleId, Integer uid) {
        CouponQuery couponQuery = new CouponQuery();
        couponQuery.ruleId = ruleId;
        couponQuery.mine = 0;
        couponQuery.uid = uid;
        BaseResult<PageData<Coupon>> couponResult = couponClient.mine(couponQuery);
        if (couponResult.isSuccess() && couponResult.getData() != null
                && StringUtil.isNotEmpty(couponResult.getData().getList())) {
            return couponResult.getData().list;
        }
        return null;
    }
}
