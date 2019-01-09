package com.welian.client.cloudevent.http;

import com.welian.beans.cloudevent.statistical.ProjectReceivePerChannelOneDayResultRes;
import com.welian.beans.cloudevent.statistical.StatisticReq;
import org.sean.framework.bean.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zhaopu on 2018/1/11.
 */
@FeignClient(name = "cloudevent")
@RequestMapping("/statistic")
public interface StatisticClient {

    /**
     * 科委项目
     * 查询近30天的每天报名数和签到数
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/keweievent", method = {RequestMethod.POST})
    BaseResult<ProjectReceivePerChannelOneDayResultRes> getKeweiEvnet(@RequestBody StatisticReq req);


}
