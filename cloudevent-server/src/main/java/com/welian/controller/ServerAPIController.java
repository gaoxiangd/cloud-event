package com.welian.controller;

import com.welian.beans.cloudevent.ad.AppKeyReq;
import com.welian.service.APIService;
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
@RequestMapping("/api")
public class ServerAPIController {


    @Autowired
    private APIService apiService;


    /**
     * 获取
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/gettoken", method = {RequestMethod.POST})
    public Object getToken(@RequestBody AppKeyReq req) {
        if (StringUtil.isEmpty(req.appKey)) {
            throw ExceptionUtil.createParamException("appKey参数错误");
        }
        if (StringUtil.isEmpty(req.appSecret)) {
            throw ExceptionUtil.createParamException("appSecret参数错误");
        }
        return apiService.getToken(req);
    }


    /**
     * 根据token获取项目列表
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getproject", method = RequestMethod.POST)
    public Object getProjectListByAppKey(@RequestBody AppKeyReq req) {
        if (StringUtil.isEmpty(req.token)) {
            throw ExceptionUtil.createParamException("token参数错误");
        }
        if (!NumberUtil.isValidId(req.getPage())) {
            throw ExceptionUtil.createParamException("page参数错误");
        }
        if (!NumberUtil.isValidId(req.getSize())) {
            throw ExceptionUtil.createParamException("size参数错误");
        }
        return apiService.getProjectListByAppKey(req);
    }
}
