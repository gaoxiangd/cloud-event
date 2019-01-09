package com.welian.controller;

import com.welian.beans.cloudevent.admin.AdminReq;
import com.welian.service.AdminService;
import org.sean.framework.util.NumberUtil;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhaopu on 2018/1/9.
 */
@RestController
@RequestMapping("/admin")
public class ServerAdminController {


    @Autowired
    private AdminService adminService;

    /**
     * 财务系统-活动结算列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/event/settlementes", method = {RequestMethod.POST})
    public Object getEventSettlementes(@RequestBody AdminReq req) {

        if (!NumberUtil.isValidId(req.page)) {
            throw ExceptionUtil.createParamException("page参数错误");
        }

        if (!NumberUtil.isValidId(req.size)) {
            throw ExceptionUtil.createParamException("size参数错误");
        }

        return adminService.getEventSettlementes(req);
    }


}
