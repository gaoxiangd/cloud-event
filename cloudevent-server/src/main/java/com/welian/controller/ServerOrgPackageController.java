package com.welian.controller;

import com.welian.beans.cloudevent.org.OrgPackagePageReq;
import com.welian.service.impl.OrgServiceImpl;
import org.sean.framework.util.NumberUtil;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by GaoXiang on 2017/8/30
 * <p>
 * 机构套餐
 */
@RestController
@RequestMapping("/org/package")
public class ServerOrgPackageController {

    @Autowired
    private OrgServiceImpl orgService;

    /**
     * 套餐管理列表
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Object list(@RequestBody OrgPackagePageReq req) {

        if (!NumberUtil.isValidId(req.type)) {
            throw ExceptionUtil.createParamException("type参数错误");
        }
        if (!NumberUtil.isValidId(req.page)) {
            throw ExceptionUtil.createParamException("page参数错误");
        }
        if (!NumberUtil.isValidId(req.size)) {
            throw ExceptionUtil.createParamException("size参数错误");
        }
        return orgService.getPackageList(req);
    }

    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object updatePackage(@RequestBody OrgPackagePageReq req){
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("orgId参数错误");
        }
        if (!NumberUtil.isValidId(req.packageId)) {
            throw ExceptionUtil.createParamException("packageStatus参数错误");
        }
        return orgService.updatePackage(req);
    }
    /**
     * 获得所有套餐信息
     */
    @RequestMapping(value = "/packageinfo", method = {RequestMethod.POST})
    public Object getPackageInfo() {
        return orgService.getPackageInfo();
    }
}
