package com.welian.controller;

import com.welian.beans.cloudevent.customweb.CustomWebReq;
import com.welian.service.impl.EventCustomWebServiceImpl;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dayangshu on 17/7/19.
 */

@RestController
@RequestMapping("/event/official")
public class ServerCustomWebController {

    @Autowired
    private EventCustomWebServiceImpl eventCustomWebService;

    /**
     * 保存专题官网
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(@RequestBody CustomWebReq req) {
        if (StringUtil.isEmpty(req.content)) {
            throw ExceptionUtil.createParamException("请填写专题官网内容");
        }
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("请填写eventId信息");
        }
        eventCustomWebService.save(req.content, req.eventId);
        return null;
    }

    /**
     * 获取专题官网详情接口eventId和uniqueKey必须传一个，
     * 如果平台方没有设置专题官网，会传给前端活动的详情信息，用户渲染默认的专题官网
     * 用途：
     * 1：当传入eventId时，作用在平台方单个活动-分享-专题官网时用到，用于活动方编辑专题官网时使用
     * 2：当传入uniqueKey时，作用在专题官网展示给报名用户时使用，用于渲染页面
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Object get(@RequestBody CustomWebReq req) {
        if (NumberUtil.isValidId(req.eventId)) {
            return eventCustomWebService.getByEventId(req.eventId);
        } else if (!StringUtil.isEmpty(req.uniqueKey)) {
            return eventCustomWebService.getByUniqueKey(req.uniqueKey);
        } else {
            throw ExceptionUtil.createParamException("参数错误");
        }
    }

}
