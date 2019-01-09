package com.welian.controller;

import com.welian.beans.cloudevent.ad.AppKeyReq;
import com.welian.beans.cloudevent.org.OrderChannelReq;
import com.welian.beans.cloudevent.project.ProjectScreenResponseCondition;
import com.welian.service.IndustryService;
import com.welian.service.ProjectService;
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
@RequestMapping("/project")
public class ServerProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private IndustryService industryService;

    /**
     * 根据渠道id获取项目列表
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getprojectfromapi", method = RequestMethod.POST)
    public Object getProjectListByAppKey(@RequestBody AppKeyReq req) {
        return projectService.getProjectListByAppKey(req);
    }

    /**
     * 根据机构id获取项目列表
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object getChannelProjectList(@RequestBody OrderChannelReq req) {
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("机构参数错误");
        }
        if (!NumberUtil.isValidId(req.page)) {
            throw ExceptionUtil.createParamException("page参数错误");
        }
        if (!NumberUtil.isValidId(req.size)) {
            throw ExceptionUtil.createParamException("size参数错误");
        }
        if (!NumberUtil.isValidId(req.type)) {
            throw ExceptionUtil.createParamException("type参数错误");
        }
        return projectService.getProjectList(req, new ProjectScreenResponseCondition());
    }


    /**
     * 对报名项目进行评星和备注
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/operatetoproject", method = RequestMethod.POST)
    public Object operateToProject(@RequestBody OrderChannelReq req) {
        if (!NumberUtil.isValidId(req.signUpId)) {
            throw ExceptionUtil.createParamException("报名id参数错误");
        }
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("orgId参数错误");
        }
        if (!NumberUtil.isValidId(req.star) && StringUtil.isEmpty(req.remark)) {
            throw ExceptionUtil.createParamException("参数错误");
        }
        return projectService.operateToProject(req);
    }

    /**
     * 获取对项目的反馈列表
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getfeedbacklist", method = RequestMethod.POST)
    public Object getFeedbackList(@RequestBody OrderChannelReq req) {
        if (!NumberUtil.isValidId(req.signUpId)) {
            throw ExceptionUtil.createParamException("报名id参数错误");
        }
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("orgId参数错误");
        }
        if (!NumberUtil.isValidId(req.page)) {
            throw ExceptionUtil.createParamException("page参数错误");
        }
        if (!NumberUtil.isValidId(req.size)) {
            throw ExceptionUtil.createParamException("size参数错误");
        }
        return projectService.getFeedbackList(req);
    }

    /**
     * 获取对项目的反馈列表
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getthreeproject", method = RequestMethod.POST)
    public Object getThreeProject(@RequestBody OrderChannelReq req) {
        if (!NumberUtil.isValidId(req.signUpId)) {
            throw ExceptionUtil.createParamException("报名id参数错误");
        }
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("orgId参数错误");
        }
        return projectService.getThreeProject(req);
    }

    /**
     * 获取项目筛选的参数
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getparams", method = RequestMethod.POST)
    public Object getProjectParams() {
        return projectService.getProjectScreenParams();
    }


    /**
     * 定时任务
     */
    @RequestMapping(value = "/task", method = RequestMethod.POST)
    public Object task() {
        return projectService.task();
    }


    /**
     * 定时任务
     */
    @RequestMapping(value = "/cleanIndustry", method = RequestMethod.POST)
    public Object cleanIndustry() {
        industryService.cleanBpIndustryData();
        return null;
    }

}
