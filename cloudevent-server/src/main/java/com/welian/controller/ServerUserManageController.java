package com.welian.controller;

import com.welian.beans.cloudevent.usermange.UserMangeReq;
import com.welian.service.UserManageService;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhaopu on 2018/6/22.
 */
@RestController
@RequestMapping("/user/manage")
public class ServerUserManageController {

    @Autowired
    private UserManageService userManageService;

    /**
     * 群发历史
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/sms/history", method = RequestMethod.POST)
    public Object smsHistory(@RequestBody UserMangeReq req) {

        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("org参数错误");
        }

        if (!NumberUtil.isValidId(req.page)) {
            req.page = 1;
        }

        if (!NumberUtil.isValidId(req.size)) {
            req.size = 3;
        }

        return userManageService.smsHistory(req);
    }


    /**
     * 获取机构下报名过的活动列表
     *
     * @param orgId
     * @return
     */
    @RequestMapping(value = "/signup/event/{orgId}", method = RequestMethod.GET)
    public Object signupEvent(@PathVariable("orgId") Integer orgId) {

        if (!NumberUtil.isValidId(orgId)) {
            throw ExceptionUtil.createParamException("org参数错误");
        }

        return userManageService.signupEvent(orgId);
    }


    /**
     * 推送
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/push", method = RequestMethod.POST)
    public Object push(@RequestBody UserMangeReq req) {
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("org参数错误");
        }
        if (StringUtil.isEmpty(req.eventIds)) {
            throw ExceptionUtil.createParamException("eventIds参数错误");
        }
        if (StringUtil.isEmpty(req.content)) {
            throw ExceptionUtil.createParamException("content参数错误");
        }

        if (req.content.length() > 130) {
            throw ExceptionUtil.createParamException("短信内容不能超过130字符");
        }

        return userManageService.push(req);
    }

    /**
     * 推送条数
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/push/count", method = RequestMethod.POST)
    public Object pushCount(@RequestBody UserMangeReq req) {
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("org参数错误");
        }
        if (req.eventIds == null || req.eventIds.size() == 0) {
            throw ExceptionUtil.createParamException("eventIds参数错误");
        }

        return userManageService.pushCount(req);
    }




    /**
     * 短信剩余条数
     *
     * @param orgId
     * @return
     */
    @RequestMapping(value = "/sms/{orgId}", method = RequestMethod.GET)
    public Object sms(@PathVariable("orgId") Integer orgId) {
        if (!NumberUtil.isValidId(orgId)) {
            throw ExceptionUtil.createParamException("org参数错误");
        }

        return userManageService.sms(orgId);
    }

    /**
     * 获取用户管理列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object userManage(@RequestBody UserMangeReq req) {

        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("org参数错误");
        }

        if (!NumberUtil.isValidId(req.page)) {
            req.page = 1;
        }

        if (!NumberUtil.isValidId(req.size)) {
            req.size = 10;
        }

        return userManageService.userManage(req);
    }


    /**
     * 查看报名记录-用户
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/record", method = RequestMethod.POST)
    public Object record(@RequestBody UserMangeReq req) {

        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("org参数错误");
        }

        if (!NumberUtil.isValidId(req.uid)) {
            throw ExceptionUtil.createParamException("uid参数错误");
        }

        if (!NumberUtil.isValidId(req.page)) {
            req.page = 1;
        }

        if (!NumberUtil.isValidId(req.size)) {
            req.size = 10;
        }

        return userManageService.record(req);
    }


    /**
     * 短信充值
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/smspay", method = RequestMethod.POST)
    public Object smspay(@RequestBody UserMangeReq req) {

        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("org参数错误");
        }

        if (!NumberUtil.isValidId(req.smsCount)) {
            throw ExceptionUtil.createParamException("smsCount参数错误");
        }

        if (!NumberUtil.isValidId(req.uid)) {
            throw ExceptionUtil.createParamException("uid参数错误");
        }

        if (!NumberUtil.isValidId(req.payType)) {
            throw ExceptionUtil.createParamException("payType参数错误");
        }

        return userManageService.smspay(req);
    }

}
