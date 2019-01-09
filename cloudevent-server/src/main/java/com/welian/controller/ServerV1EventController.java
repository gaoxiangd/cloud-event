package com.welian.controller;

import com.welian.beans.cloudevent.EventPlacardReq;
import com.welian.beans.cloudevent.app.AppReq;
import com.welian.service.V1EventService;
import com.welian.utils.DateUtil;
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
 * Created by zhaopu on 2018/7/4.
 */
@RestController
@RequestMapping("/v1/event")
public class ServerV1EventController {


    @Autowired
    private V1EventService v1EventService;

    /**
     * 获取小程序活动列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/events", method = {RequestMethod.POST})
    public Object getV1EventList(@RequestBody AppReq req) {
        req.uid = RequestHolder.getUid();

        if (NumberUtil.isInValidId(req.page)) {
            req.page = 1;
        }
        if (NumberUtil.isInValidId(req.size)) {
            req.size = 10;
        }


        return v1EventService.getV1EventList(req);
    }

    /**
     * 小程序搜索活动列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/search", method = {RequestMethod.POST})
    public Object getV1EventSearchList(@RequestBody AppReq req) {

        req.uid = RequestHolder.getUid();

        if (NumberUtil.isInValidId(req.page)) {
            req.page = 1;
        }
        if (NumberUtil.isInValidId(req.size)) {
            req.size = 10;
        }

        if (StringUtil.isEmpty(req.content)) {
            throw ExceptionUtil.createParamException("content参数错误");
        }

        return v1EventService.getV1EventSearchList(req);
    }


    /**
     * 我的活动列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/records", method = {RequestMethod.POST})
    public Object getV1MyEventList(@RequestBody AppReq req) {
        req.uid = RequestHolder.getVerifiedUid();

        if (NumberUtil.isInValidId(req.page)) {
            req.page = 1;
        }
        if (NumberUtil.isInValidId(req.size)) {
            req.size = 10;
        }
        return v1EventService.getV1MyEventList(req);
    }


    /**
     * 我的票券列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/tickets", method = {RequestMethod.POST})
    public Object getV1MyEventTickets(@RequestBody AppReq req) {

        req.uid = RequestHolder.getVerifiedUid();

        if (NumberUtil.isInValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("eventId参数错误");
        }


        return v1EventService.getV1MyEventTickets(req);
    }

    @RequestMapping(value = "/placard/address", method = {RequestMethod.POST})
    public Object placardAddr(@RequestBody EventPlacardReq req){

        return v1EventService.placardAddr(req);

    }


    /**
     * 活动自动结算(提供测试手动调用(当天的),本来是定时任务)
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/auth/settlement", method = {RequestMethod.POST})
    public Object auth(@RequestBody AppReq req) {
        return v1EventService.authSettlement(DateUtil.getTimesmorning());
    }

}
