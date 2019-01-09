package com.welian.controller;

import com.welian.beans.cloudevent.project.ProjectBaseReq;
import com.welian.beans.cloudevent.record.CustomImportListReq;
import com.welian.beans.cloudevent.record.ProjectImportReq;
import com.welian.service.impl.EventRecordImportServiceImpl;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by memorytale on 2017/7/13.
 */

@RestController
@RequestMapping("/signup")
public class ServerRecordImortController {
    @Autowired
    private EventRecordImportServiceImpl eventRecordImportService;

    /**
     * 项目导入
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/recordImport", method = RequestMethod.POST)
    public Object recordImport(@RequestBody ProjectImportReq req) {
        if (!NumberUtil.isValidId(req.recordId)) {
            throw ExceptionUtil.createParamException("recordId参数异常");
        }
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("eventId参数异常");
        }
        if (!NumberUtil.isValidId(req.source)) {
            throw ExceptionUtil.createParamException("source参数异常");
        }
        if (req.recordType == null) {
            throw ExceptionUtil.createParamException("recordType参数异常");
        }
        if (!NumberUtil.isValidId(req.createTime)) {
            throw ExceptionUtil.createParamException("createTime参数异常");
        }
        if (!NumberUtil.isValidId(req.modifyTime)) {
            throw ExceptionUtil.createParamException("modifyTime参数异常");
        }
        if (req.user == null || !NumberUtil.isValidId(req.user.uid)) {
            throw ExceptionUtil.createParamException("参数异常");
        }
        if (req.project == null) {
            throw ExceptionUtil.createParamException("请填写项目信息");
        }
        ProjectBaseReq project = req.project;
        if (!NumberUtil.isValidId(project.pid)) {
            throw ExceptionUtil.createParamException("pid参数异常");
        }
        if (StringUtil.isEmpty(project.name)) {
            throw ExceptionUtil.createParamException("请填写项目名称");
        }
        if (StringUtil.isEmpty(project.intro)) {
            throw ExceptionUtil.createParamException("请填写项目一句话介绍");
        }
        return eventRecordImportService.projectSignUp(req);
    }


    /**
     * 创业活动报名数据导入
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/activeRecordImport", method = RequestMethod.POST)
    public Object activeRecordImport(@RequestBody CustomImportListReq req) {
        if (StringUtil.isEmpty(req.list)) {
            throw ExceptionUtil.createParamException("list参数异常");
        }
        return eventRecordImportService.customSignUp(req);
    }

}
