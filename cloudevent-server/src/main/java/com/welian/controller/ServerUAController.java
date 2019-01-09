package com.welian.controller;

import com.welian.beans.cloudevent.ua.UAReq;
import com.welian.service.impl.UAServiceImpl;
import org.sean.framework.util.NumberUtil;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by GaoXiang on 2017/8/23
 */
@RestController
@RequestMapping("/statistic")
public class ServerUAController {
    @Autowired
    private UAServiceImpl uaService;

    /**
     * 保存ua信息
     */
    @RequestMapping(value = "/ua", method = {RequestMethod.POST})
    public Object uaSave(@RequestBody UAReq req) {
        if (!NumberUtil.isValidId((int) req.resource)) {
            throw ExceptionUtil.createParamException("resource参数错误");
        }
        if (!NumberUtil.isValidId(req.uid)) {
            throw ExceptionUtil.createParamException("uid参数错误");
        }
        if (req.ua == null) {
            throw ExceptionUtil.createParamException("ua参数错误");
        }
        return uaService.uaSave(req);
    }

}
