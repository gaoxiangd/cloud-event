package com.welian.controller;

import com.welian.beans.cloudevent.checkIn.CheckInReq;
import com.welian.service.CheckInService;
import com.welian.utils.ExceptionUtil;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import org.sean.framework.web.RequestHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhaopu on 2017/12/4.
 */
@RestController
@RequestMapping("/checkin")
public class ServerCheckInController {

    @Autowired
    private CheckInService checkInService;

    /**
     * 签到-主办方签到-员工提交签到权限
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/authorize", method = {RequestMethod.POST})
    public Object authorize(@RequestBody CheckInReq req) {
        RequestHolder.getVerifiedUid();
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("活动id参数错误");
        }
        if (StringUtil.isEmpty(req.code)) {
            throw ExceptionUtil.createParamException("活动权限密码错误");
        }
        if (!NumberUtil.isValidId(req.uid)) {
            throw ExceptionUtil.createParamException("用户id参数错误");
        }
        return checkInService.authorize(req);
    }


    /**
     * 签到
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "", method = {RequestMethod.POST})
    public Object checkin(@RequestBody CheckInReq req) {

        if (StringUtil.isEmpty(req.tradeNo)) {
            throw ExceptionUtil.createParamException("订单号错误");
        }

        return checkInService.checkin(req);
    }



    /**
     * 签到-用户扫码
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "detectpermissionuser", method = {RequestMethod.POST})
    public Object detectPermissionUser(@RequestBody CheckInReq req) {
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("活动id错误");
        }

        return checkInService.detectPermissionUser(req);
    }



    /**
     * 签到-活动方扫码
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "detectpermissionsponsor", method = {RequestMethod.POST})
    public Object detectPermissionSponsor(@RequestBody CheckInReq req) {
        if (StringUtil.isEmpty(req.tradeNo)) {
            throw ExceptionUtil.createParamException("订单号错误");
        }

        return checkInService.detectPermissionSponsor(req);
    }


    /**
     * 签到-签到管理
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "manage", method = {RequestMethod.POST})
    public Object manage(@RequestBody CheckInReq req) {
        if (!NumberUtil.isValidId(req.eventId) ) {
            throw ExceptionUtil.createParamException("活动id错误");
        }
        return checkInService.manage(req);
    }


    /**
     * 根据时间获取有效的签到数据 (活动券那边需要)
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "getvalidachekin", method = {RequestMethod.POST})
    public Object getvalidactiverecord(@RequestBody CheckInReq req) {
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("活动id错误");
        }

        if (!NumberUtil.isValidId(req.startTime) ) {
            throw ExceptionUtil.createParamException("startTime错误");
        }

        if (!NumberUtil.isValidId(req.endTime) ) {
            throw ExceptionUtil.createParamException("endTime错误");
        }

        return checkInService.getvalidaChekIn(req);
    }



    /**
     * 签到-用户扫码
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "newdetectpermissionuser", method = {RequestMethod.POST})
    public Object newDetectPermissionUser(@RequestBody CheckInReq req) {
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("活动id错误");
        }

        return checkInService.newDetectPermissionUser(req);
    }





}
