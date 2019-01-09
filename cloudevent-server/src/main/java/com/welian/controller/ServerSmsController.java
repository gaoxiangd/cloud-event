package com.welian.controller;

import com.welian.beans.cloudevent.sms.SendSmsReq;
import com.welian.beans.notify.TemplateReq;
import com.welian.service.impl.EventSmsServiceImpl;
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
@RequestMapping("/sms")
public class ServerSmsController {
    @Autowired
    private EventSmsServiceImpl eventSmsService;

    /**
     * 给报名用户群发短信
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/sendsms", method = RequestMethod.POST)
    public Object sendSms(@RequestBody SendSmsReq req) {
        if (StringUtil.isEmpty(req.content)) {
            throw ExceptionUtil.createParamException("参数错误");
        }
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("参数错误");
        }
        if((req.content=req.content.trim()).length()>150){
            throw  ExceptionUtil.createParamException("短信字数超过了150个字,发送失败");
        }
        eventSmsService.sendSms(req.content, req.eventId);
        return null;
    }

    /**
     * 短信管理-取发送短信的列表
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object list(@RequestBody SendSmsReq req) {
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("eventId参数错误");
        }
        if (!NumberUtil.isValidId(req.getPage())) {
            throw ExceptionUtil.createParamException("page参数错误");
        }
        if (!NumberUtil.isValidId(req.getSize())) {
            throw ExceptionUtil.createParamException("size参数错误");
        }
        return eventSmsService.getSendSmsLog(req);
    }

    /**
     * 发送模板短信
     * @param req
     * @return
     */
    @RequestMapping(value = "/template/send",method = RequestMethod.POST)
    public Object templateSend(@RequestBody TemplateReq req){
         eventSmsService.templateSend(req);
         return null;
    }

}
