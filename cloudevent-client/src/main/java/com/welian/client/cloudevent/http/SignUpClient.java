package com.welian.client.cloudevent.http;

import com.welian.beans.cloudevent.Base64KeyReq;
import com.welian.beans.cloudevent.ExtensionLinkReq;
import com.welian.beans.cloudevent.UpdateSignUpBPResp;
import com.welian.beans.cloudevent.app.AppReq;
import com.welian.beans.cloudevent.coupon.CouponListResp;
import com.welian.beans.cloudevent.coupon.CouponReq;
import com.welian.beans.cloudevent.record.CustomImportListReq;
import com.welian.beans.cloudevent.record.CustomSignUpReq;
import com.welian.beans.cloudevent.record.CustomSignUpResp;
import com.welian.beans.cloudevent.record.EventDetailResp;
import com.welian.beans.cloudevent.record.EventRecordReq;
import com.welian.beans.cloudevent.record.EventRecordResp;
import com.welian.beans.cloudevent.record.ProjectImportReq;
import com.welian.beans.cloudevent.record.ProjectSignUpReq;
import com.welian.beans.cloudevent.record.ProjectSignUpResp;
import com.welian.beans.cloudevent.signup.SearchSignupListResp;
import com.welian.beans.cloudevent.signup.SearchSignupReq;
import com.welian.beans.cloudevent.user.UserReq;
import com.welian.beans.cloudevent.user.UserResp;
import com.welian.commodity.beans.Coupon;
import org.sean.framework.bean.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Description: Feign Client
 * 将HTTP请求转化为Class 供调用方使用
 * Created by Sean.xie on 2017/2/13.
 */
@FeignClient(name = "cloudevent")
@RequestMapping("/signup")
public interface SignUpClient {

    /**
     * 项目导入
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/recordImport", method = RequestMethod.POST)
    BaseResult<ProjectSignUpResp> recordImport(@RequestBody ProjectImportReq req);

    /**
     * 创业活动报名数据导入
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/activeRecordImport", method = RequestMethod.POST)
    BaseResult<ProjectSignUpResp> activeRecordImport(@RequestBody CustomImportListReq req);

    /**
     * app端补上bp
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/updateSignUpBPByRecordId", method = RequestMethod.POST)
    BaseResult<UpdateSignUpBPResp> updateSignUpBPByRecordId(@RequestBody ExtensionLinkReq req);


    /**
     * 云活动平台-搜索报名记录列表
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    BaseResult<SearchSignupListResp> searchRecord(@RequestBody SearchSignupReq req);


    /**
     * 创业活动报名接口
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/customsign", method = {RequestMethod.POST})
    BaseResult<CustomSignUpResp> customSignUp(@RequestBody CustomSignUpReq req);


    /**
     * 路演大赛报名接口
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/projectsign", method = {RequestMethod.POST})
    BaseResult<ProjectSignUpResp> projectsign(@RequestBody ProjectSignUpReq req);


    /**
     * 取消报名
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/cancel", method = {RequestMethod.POST})
    BaseResult<CustomSignUpResp> signUpCancel(@RequestBody AppReq req);


    /**
     * 是否报过名
     */
    @RequestMapping(value="/existrecord",method = {RequestMethod.POST})
    BaseResult<Integer> existRecord(@RequestBody AppReq req);


    @RequestMapping(value = "/miniprogram/event", method = RequestMethod.POST)
    BaseResult<EventDetailResp> getMiniEvent(@RequestBody Base64KeyReq req) ;


    /**
     * 微信根据unionId获取用户信息
     */
    @RequestMapping(value="/unionuser",method = {RequestMethod.POST})
    BaseResult<UserResp> getUserByUnionId(@RequestBody UserReq req);

    @RequestMapping(value="/getdetailbykey",method = {RequestMethod.POST})
    BaseResult<EventDetailResp> getDetailByKey(@RequestBody Base64KeyReq req);

    @RequestMapping(value="/coupons",method = {RequestMethod.POST})
    BaseResult<CouponListResp> coupons(@RequestBody CouponReq req);

    @RequestMapping(value="/coupon/get",method = {RequestMethod.POST})
    BaseResult<Coupon> getCoupon(@RequestBody CouponReq req);

    @RequestMapping(value="/recordInfo",method = {RequestMethod.POST})
    BaseResult<EventRecordResp> getRecordInfo(@RequestBody EventRecordReq req);
}
