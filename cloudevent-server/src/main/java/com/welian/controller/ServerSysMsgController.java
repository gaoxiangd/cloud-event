package com.welian.controller;

import com.welian.beans.cloudevent.org.OrgReq;
import com.welian.beans.cloudevent.org.OrgResp;
import com.welian.beans.cloudevent.sys.SysMessagesReq;
import com.welian.service.impl.SysMsgServiceImpl;
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
 * Created by gaoxiang on 2017/8/18.
 */
@RestController
@RequestMapping("/sysmsg")
public class ServerSysMsgController {
    @Autowired
    private SysMsgServiceImpl sysMsgService;

    /**
     * 获取系统消息列表
     *
     * @param req
     */
    @RequestMapping(value = "/getlist", method = {RequestMethod.POST})
    public Object getMsgList(@RequestBody OrgReq req) {
        Integer uid = RequestHolder.getVerifiedUid();
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("机构参数错误");
        }
        if (!NumberUtil.isValidId(req.getPage())) {
            throw ExceptionUtil.createParamException("page参数错误");
        }
        if (!NumberUtil.isValidId(req.getSize())) {
            throw ExceptionUtil.createParamException("size参数错误");
        }
        return sysMsgService.getMsgList(req,uid);
    }

    /**
     * 批量删除消息
     *
     * @param req
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object deleteMessages(@RequestBody SysMessagesReq req) {

        if (StringUtil.isEmpty(req.messageIds)) {
            throw ExceptionUtil.createParamException("参数错误");
        }
        sysMsgService.deleteMessages(req);
        return null;
    }

    /**
     * 获得消息详情
     *
     * @param req
     */
    @RequestMapping(value = "/getdetail", method = {RequestMethod.POST})
    public Object getMsgDetail(@RequestBody SysMessagesReq req) {
        Integer uid = RequestHolder.getVerifiedUid();

        if (!NumberUtil.isValidId(req.messageId)) {
            throw ExceptionUtil.createParamException("参数错误");
        }
        return sysMsgService.getMsgDetail(uid,req);
    }

    /**
     * 计数未读消息
     *
     * @param req
     */
    @RequestMapping(value = "/unreadmsg", method = {RequestMethod.POST})
    public Object countUnReadMsg(@RequestBody OrgResp req) {
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("参数错误");
        }
        return sysMsgService.countUnReadMsg(req);
    }

}
