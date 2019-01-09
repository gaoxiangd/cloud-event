package com.welian.controller;

import com.welian.beans.cloudevent.statistical.StatisticReq;
import com.welian.service.impl.EventStatisticServiceImpl;
import org.sean.framework.util.NumberUtil;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhaopu on 2017/7/3.
 * 数据统计类接口
 */
@RestController
@RequestMapping("/statistic")
public class ServerEventStatisticController {

    @Autowired
    private EventStatisticServiceImpl eventStatisticService;

    /**
     * 首页-顶部数量统计
     */
    @RequestMapping(value = "/total", method = RequestMethod.POST)
    public Object getOrgStatisticTotal(@RequestBody StatisticReq req) {
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("机构参数错误");
        }
        return eventStatisticService.getOrgStatisticTotal(req);
    }

    /**
     * 报名图表统计
     */
    @RequestMapping(value = "/signupstatistic", method = RequestMethod.POST)
    public Object getSignUpStatistic(@RequestBody StatisticReq req) {
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("机构参数错误");
        }
        if (!NumberUtil.isValidId(req.days)) {
            throw ExceptionUtil.createParamException("day参数错误");
        }
        return eventStatisticService.getSignUpStatistic(req);
    }


    /**
     * 报名人员分布情况统计
     */
    @RequestMapping(value = "/areadistribution", method = RequestMethod.POST)
    public Object getAreaDistribution(@RequestBody StatisticReq req) {
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("机构参数错误");
        }
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("eventId参数错误");
        }
        return eventStatisticService.getAreaDistribution(req);
    }


    /**
     * 单个活动的概况信息
     */
    @RequestMapping(value = "/singleevent", method = RequestMethod.POST)
    public Object getSingleEvent(@RequestBody StatisticReq req) {
        if (!NumberUtil.isValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("eventId参数错误");
        }
        return eventStatisticService.getSingleEvent(req);
    }

    /**
     * 获取渠道统计接口
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getorgstatistics", method = {RequestMethod.POST})
    public Object getOrgStatistics(@RequestBody StatisticReq req) {
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("渠道参数错误");
        }
        if (!NumberUtil.isValidId(req.days) && (!NumberUtil.isValidId(req.startTime)
                || !NumberUtil.isValidId(req.endTime))) {
            throw ExceptionUtil.createParamException("时间参数错误");
        }
        return eventStatisticService.getOrgStatistics(req);
    }

    /**
     * 报名图表统计
     */
    @RequestMapping(value = "/task", method = RequestMethod.POST)
    public Object task() {
        return eventStatisticService.task();
    }



    /**
     *  科委项目
     *  查询近30天的每天报名数和签到数
     */
    @RequestMapping(value = "/keweievent", method = RequestMethod.POST)
    public Object getKeweiEvnet(@RequestBody StatisticReq req) {
        if (req.eventIds == null || req.eventIds.size() == 0) {
            throw ExceptionUtil.createParamException("参数错误");
        }
        if (req.days == null) {
            throw ExceptionUtil.createParamException("参数错误");
        }
        return eventStatisticService.getKeweiEvnet(req);
    }
    /**
     * 创业活动表单浏览数加一
     */
    @RequestMapping(value = "/formcont", method = RequestMethod.POST)
    public Object formAdd(@RequestBody StatisticReq req){
        return eventStatisticService.formAdd(req);
    }
}
