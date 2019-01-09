package com.welian.client.cloudevent.http;

import com.welian.beans.cloudevent.OrgAndEventListResp;
import com.welian.beans.cloudevent.TicketInfoResp;
import com.welian.beans.cloudevent.TicketResp;
import com.welian.beans.cloudevent.app.AppReq;
import com.welian.beans.cloudevent.app.AppResp;
import com.welian.beans.cloudevent.app.OrderRulesResp;
import com.welian.beans.cloudevent.app.RecordInfoResp;
import com.welian.beans.cloudevent.event.EventPara;
import com.welian.beans.cloudevent.event.EventResp;
import com.welian.beans.cloudevent.org.OrgReq;
import com.welian.beans.cloudevent.record.EventRecordListResp;
import com.welian.beans.cloudevent.record.EventRecordResp;
import com.welian.beans.cloudevent.record.RecordsUserResp;
import org.sean.framework.bean.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zhaopu on 2017/10/16.
 */
@FeignClient(name = "cloudevent")
public interface AppClient {
    /**
     * 排序规则的名词和ID列表给客户端
     */
    @RequestMapping(method = RequestMethod.POST, value = "/event/orderrules")
    BaseResult<OrderRulesResp> getOrderRules();

    /**
     * 收藏或者取消收藏活动
     */
    @RequestMapping(method = RequestMethod.POST, value = "/event/favorite")
    BaseResult<AppResp> sendFavorite(@RequestBody AppReq req);

    /**
     * 活动城市列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "/event/citylist")
    BaseResult<AppResp> sendEventCityList();

    /**
     * 获取活动详细信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/event/getdetail")
    BaseResult<EventResp> sendGetDetail(@RequestBody EventPara eventPara);


    /**
     * 查看用户报名详细记录
     */
    @RequestMapping(method = RequestMethod.POST, value = "/event/checkorderstatus")
    BaseResult<AppResp> sendCheckOrderStatus(@RequestBody AppReq req);

    /**
     * 取用户创建 收藏 报名 活动列表
     */
    @RequestMapping(method = RequestMethod.POST, value = "/event/usereventlist")
    BaseResult<EventPara> sendUserEventList(@RequestBody AppReq req);

    /**
     * app端调用-根据机构获得机构下的活动列表
     *
     * @param req/event
     * @return
     */
    @RequestMapping(value = "/event/eventgetorgdetail", method = {RequestMethod.POST})
    BaseResult<OrgAndEventListResp> getOrgDetail(@RequestBody OrgReq req);

    /**
     * app端调用-根据活动获取我的票券相关信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/event/ticket", method = {RequestMethod.POST})
    BaseResult<TicketInfoResp> getMyTickets(@RequestBody AppReq req);

    /**
     * app活动排序列表
     */
    @RequestMapping(value = "/event/orderedlist", method = {RequestMethod.POST})
    BaseResult<EventPara> orderedList(@RequestBody AppReq req);

    /**
     * 根据报名id获取活动报名信息
     */
    @RequestMapping(value = "/event/getEventRecord", method = {RequestMethod.POST})
    BaseResult<EventRecordResp> getEventRecord(@RequestBody AppReq req);


    /**********************************************3.4.0创业活动相关接口************************************************/
    /**
     * 获取创业活动列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/event/eventList", method = {RequestMethod.POST})
    BaseResult<EventPara> getEventList(@RequestBody AppReq req);

    /**
     * 获取当前活动的票的信息
     */
    @RequestMapping(value = "/event/getTicket", method = {RequestMethod.POST})
    BaseResult<TicketResp> getEventTicket(@RequestBody AppReq req);

    /**
     * 获取活动下所有报名记录信息
     */
    @RequestMapping(value = "/event/getEventRecords", method = {RequestMethod.POST})
    BaseResult<RecordsUserResp> getEventRecordsPage(@RequestBody AppReq req);

    /**
     * 获取某用户某活动下的票券信息
     */
    @RequestMapping(value = "/event/buyerticket", method = {RequestMethod.POST})
    BaseResult<TicketResp> getBuyerTicket(@RequestBody AppReq req);

    /**
     * 获取某用户某活动下的订单信息
     */
    @RequestMapping(value = "/event/ticketOrder", method = {RequestMethod.POST})
    BaseResult<EventRecordListResp> getTicketOrder(@RequestBody AppReq req);

    /**
     * 根据ticket_no(recordId) 找到票券信息
     */
    @RequestMapping(value="/event/recordInfo",method = {RequestMethod.POST})
    BaseResult<RecordInfoResp> getRecordInfo(@RequestBody AppReq req);


    /**
     * 推荐活动
     */
    @RequestMapping(method = RequestMethod.POST, value = "/event/recommend")
    BaseResult<AppResp> recommend(@RequestBody AppReq req);

}
