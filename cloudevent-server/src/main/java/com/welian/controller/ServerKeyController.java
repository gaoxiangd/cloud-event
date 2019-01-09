package com.welian.controller;

import com.welian.beans.cloudevent.ad.AppKeyReq;
import com.welian.beans.cloudevent.org.OrderChannelReq;
import com.welian.service.KeyService;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: Controller
 * 写法示例,为了方法快速开发实现业务,定义了格式标准
 * Created by Sean.xie on 2017/2/10.
 */
@RestController
@RequestMapping("/org")
public class ServerKeyController {


    @Autowired
    private KeyService keyService;


    /**
     * 获取
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getkeysecret", method = {RequestMethod.POST})
    public Object get(@RequestBody OrderChannelReq req) {
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("orgId参数错误");
        }
        return keyService.get(req);
    }

    /**
     * 获取广告分组列表信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getsecret", method = {RequestMethod.POST})
    public Object getSecret(@RequestBody AppKeyReq req) {
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("机构id参数错误");
        }
        if (StringUtil.isEmpty(req.appKey)) {
            throw ExceptionUtil.createParamException("appkey参数错误");
        }
        return keyService.getSecret(req);
    }


}
