package com.welian.controller;

import com.welian.beans.cloudevent.ExtensionLinkReq;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.service.impl.ExtensionLinkServiceImpl;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by memorytale on 2017/7/10.
 */

@RestController
@RequestMapping("/event")
public class ServerExtensionLinkController {
    @Autowired
    private ExtensionLinkServiceImpl extensionLinkService;

    /**
     * 对事件活动添加推广链接
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/addlink", method = {RequestMethod.POST})
    public Object addLink(@RequestBody ExtensionLinkReq req) {
        if (StringUtil.isEmpty(req.extensionLinkName)) {
            throw ExceptionUtil.createParamException("请填写链接名称");
        }
        if (req.extensionLinkName.trim().length() > 10) {
            throw ExceptionUtil.createParamException("链接名称名称不能超过10字");
        }
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("事件活动参数错误");
        }
        req.linkType = SqlEnum.LINKTYPE.TYPE_USER.getValue();
        return extensionLinkService.addLinkJson(req);
    }

    /**
     * 对事件活动的推广链接进行修改
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/reviselink", method = {RequestMethod.POST})
    public Object reviseLink(@RequestBody ExtensionLinkReq req) {
        if (StringUtil.isEmpty(req.extensionLinkName)) {
            throw ExceptionUtil.createParamException("请填写链接名称");
        }
        if (req.extensionLinkName.trim().length() > 10) {
            throw ExceptionUtil.createParamException("链接名称名称不能超过10字");
        }
        if (!NumberUtil.isValidId(req.extensionLinkId)) {
            throw ExceptionUtil.createParamException("推广链接id参数错误");
        }
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("事件活动参数错误");
        }
        return extensionLinkService.reviseLinkJson(req);
    }

    /**
     * 获取事件活动的列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getlinklist", method = {RequestMethod.POST})
    public Object getLinkList(@RequestBody ExtensionLinkReq req) {
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("活动id参数错误");
        }
        if (!NumberUtil.isValidId(req.linkListType)) {
            throw ExceptionUtil.createParamException("linkListType参数错误");
        }
        if (!NumberUtil.isValidId(req.page)) {
            throw ExceptionUtil.createParamException("page参数错误");
        }
        if (!NumberUtil.isValidId(req.size)) {
            throw ExceptionUtil.createParamException("size参数错误");
        }
        return extensionLinkService.getLinkListByEventIdJson(req);
    }

    /**
     * 获取某个活动的默认的渠道信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getdefaultlink", method = {RequestMethod.POST})
    public Object getDefaultLink(@RequestBody ExtensionLinkReq req) {
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("活动id参数错误");
        }
        return extensionLinkService.getDefaultLinkJson(req);
    }
    /**
     * 根据uniqueKey获得eventId，清洗url调用方法
     *
     * @param uniqueKeys
     * @return
     */
    @RequestMapping(value = "/getunikeymap", method = {RequestMethod.POST})
    public Object getEventIdByUniquekeys(@RequestBody List<String> uniqueKeys) {

        return extensionLinkService.getEventIdByUniquekeys(uniqueKeys);
    }

    /**
     * 根据uniqueKey和tradeNo，获得跳转url地址
     */
    @RequestMapping(value = "/resultUrl", method = {RequestMethod.POST})
    public Object getResultUrl(@RequestBody ExtensionLinkReq req) {

        return extensionLinkService.getResultUrl(req);
    }

}
