package com.welian.client.cloudevent.http;

import com.welian.beans.cloudevent.checkIn.CheckInReq;
import com.welian.beans.cloudevent.checkIn.CheckInResp;
import org.sean.framework.bean.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zhaopu on 2017/12/14.
 */
@FeignClient(name = "cloudevent")
@RequestMapping("/checkin")
public interface CheckinClient {

    /**
     * 签到-活动方扫码
     *
     * @param req
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/detectpermissionsponsor")
    BaseResult<CheckInResp> detectpermissionsponsor(CheckInReq req);


    /**
     * 签到-主办方签到-员工提交签到权限
     *
     * @param req
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/authorize")
    BaseResult<CheckInResp> authorize(CheckInReq req);


    /**
     * 签到-用户扫码
     *
     * @param req
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/detectpermissionuser")
    BaseResult<CheckInResp> detectpermissionuser(CheckInReq req);


    /**
     * 根据时间获取有效的签到数据 (活动券那边需要)
     *
     * @param req
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/getvalidachekin")
    BaseResult<CheckInResp> getvalidactiverecord(CheckInReq req);


    /**
     * 签到-新用户扫码
     *
     * @param req
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/newdetectpermissionuser")
    BaseResult<CheckInResp> newdetectpermissionuser(CheckInReq req);



    /**
     * 签到
     *
     * @param req
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "")
    BaseResult<CheckInResp> checkin(CheckInReq req);

}
