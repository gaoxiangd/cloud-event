package com.welian.client.cloudevent.http;

import com.welian.beans.cloudevent.EventPlacardReq;
import com.welian.beans.cloudevent.app.AppReq;
import com.welian.beans.cloudevent.checkIn.CheckInResp;
import com.welian.beans.cloudevent.event.EventPara;
import org.sean.framework.bean.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zhaopu on 2018/7/9.
 */
@FeignClient(name = "cloudevent")
public interface V1EventClient {


    /**
     * 获取小程序活动列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/v1/event/events", method = {RequestMethod.POST})
    BaseResult<EventPara> getV1EventList(@RequestBody AppReq req);

    /**
     * 小程序搜索活动列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/v1/event/search", method = {RequestMethod.POST})
    BaseResult<EventPara> getV1EventSearchList(@RequestBody AppReq req);



    /**
     * 我的活动列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/v1/event/records", method = {RequestMethod.POST})
    BaseResult<EventPara> getV1MyEventList(@RequestBody AppReq req);


    /**
     * 我的票券列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/v1/event/tickets", method = {RequestMethod.POST})
    BaseResult<CheckInResp> getV1MyEventTickets(@RequestBody AppReq req);

    /**
     * 云活动海报
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/v1/event/placard/address", method = {RequestMethod.POST})
    BaseResult<String> placardAddr(@RequestBody EventPlacardReq req);


}
