package com.welian.controller;

import com.welian.beans.cloudevent.CloudEventKafkaKey;
import com.welian.beans.cloudevent.ExtensionLinkReq;
import com.welian.beans.cloudevent.app.AppReq;
import com.welian.beans.cloudevent.event.EventPara;
import com.welian.beans.cloudevent.event.EventResp;
import com.welian.beans.cloudevent.event.EventSearchPara;
import com.welian.beans.cloudevent.event.OptionResp;
import com.welian.beans.cloudevent.event.SaveEventReq;
import com.welian.beans.cloudevent.event.SaveEventResp;
import com.welian.beans.cloudevent.event.SponsorListResp;
import com.welian.beans.cloudevent.org.OrgReq;
import com.welian.beans.cloudevent.query.EventQuery;
import com.welian.beans.cloudevent.record.RecordsUserResp;
import com.welian.beans.cloudevent.user.EventServiceReq;
import com.welian.enums.cloudevent.OrderRulesEnum;
import com.welian.enums.cloudevent.ParamEnum;
import com.welian.service.EventSolrService;
import com.welian.service.SponsorsModuleService;
import com.welian.service.TagModuleService;
import com.welian.service.impl.EventRecordServiceImpl;
import com.welian.service.impl.EventServiceImpl;
import org.sean.framework.web.RequestHolder;
import com.welian.utils.ExceptionUtil;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import org.sean.framework.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dayangshu on 17/7/4.
 */

@RestController
@RequestMapping("/event")
public class ServerEventController {

    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private SponsorsModuleService sponsorsModuleService;
    @Autowired
    private TagModuleService tagModuleService;
    @Autowired
    private EventRecordServiceImpl eventRecordService;
    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private EventSolrService eventSolrService;

    /**
     * 根据eventId获得event基础信息，供其他服务调用
     *
     * @param eventId
     * @return
     */
    @RequestMapping(value = "/getEventById", method = {RequestMethod.POST})
    public Object getEventByEventId(@RequestBody Integer eventId) {
        return eventService.getEventByEventId(eventId);
    }

    /**
     * 保存活动信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object saveEvent(@RequestBody SaveEventReq req) throws Exception {
        Integer uid = RequestHolder.getVerifiedUid();
        Integer sourceFrom = ParamEnum.EventRequestSource.SOURCE_FROM_CLOUD.getValue();
        Integer eventId = req.event.eventId;
        SaveEventResp resp = eventService.saveEvent(req, uid, sourceFrom);

        //修改活动
        if (NumberUtil.isValidId(eventId)) {
            //TODO: 2018/1/16 更新solr 
            eventSolrService.updataEventToSolr(resp.eventReq);
        } else {
            //TODO: 2018/1/16 同步solr
            eventSolrService.synchronousEventToSolr(resp.eventReq);
        }

        kafkaProducer.send(CloudEventKafkaKey.CLOUD_EVENT_SAVE_OR_EDIT_KEY, resp.eventId + "");
        return resp;
    }

    /**
     * 获取活动详细信息
     *
     * @param eventPara
     * @return
     */
    @RequestMapping(value = "/getdetail", method = {RequestMethod.POST})
    public Object getEventDetail(@RequestBody EventPara eventPara) {
        EventResp response = eventService.getEventDetail(eventPara);
        return response;
    }

    /**
     * 获取活动下所有通过审核的报名记录的用户信息
     *
     * @param eventPara
     * @return
     */
    @RequestMapping(value = "/recordsuser", method = {RequestMethod.POST})
    public Object getRecordsUser(@RequestBody EventPara eventPara) {
        RecordsUserResp response = eventService.getRecordsUser(eventPara);
        return response;
    }


    /**
     * 获取活动列表信息
     *
     * @param eventSearchPara
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Object getEventList(@RequestBody EventSearchPara eventSearchPara) {
        if (!NumberUtil.isValidId(eventSearchPara.orgId)) {
            throw ExceptionUtil.createParamException("请输入机构参数");
        }
        if (!NumberUtil.isValidId(eventSearchPara.page)) {
            throw ExceptionUtil.createParamException("请输入page参数");
        }
        if (!NumberUtil.isValidId(eventSearchPara.size)) {
            throw ExceptionUtil.createParamException("请输入size参数");
        }
        if (eventSearchPara.level == null) {
            throw ExceptionUtil.createParamException("请输入level参数");
        }
        return eventService.searchEventList(eventSearchPara);
    }

    /**
     * 获取全部活动列表信息
     *
     * @param eventSearchPara
     * @return
     */
    @RequestMapping(value = "/simplelist", method = {RequestMethod.POST})
    public Object getAllSimpleEventList(@RequestBody EventSearchPara eventSearchPara) {

        if (!NumberUtil.isValidId(eventSearchPara.page)) {
            throw ExceptionUtil.createParamException("请输入page参数");
        }
        if (!NumberUtil.isValidId(eventSearchPara.size)) {
            throw ExceptionUtil.createParamException("请输入size参数");
        }

        return eventService.getAllSimpleEventList(eventSearchPara);
    }

    /**
     * 修改活动状态信息
     *
     * @param eventPara
     * @return
     */
    @RequestMapping(value = "/updatestatus", method = {RequestMethod.POST})
    public Object updateStatus(@RequestBody EventPara eventPara) {

        return eventService.updateStatus(eventPara);

    }


    /**
     * 获取活动配置信息
     *
     * @return
     */
    @RequestMapping(value = "/options", method = {RequestMethod.POST})
    public Object getOptions() {
        OptionResp optionResp = new OptionResp();
        optionResp.tags = tagModuleService.getDefaultTag();
        return optionResp;
    }

    /**
     * 搜索主办方
     *
     * @param eventSearchPara
     * @return
     */
    @RequestMapping(value = "/searchsponsor", method = {RequestMethod.POST})
    public Object searchSponsor(@RequestBody EventSearchPara eventSearchPara) {
        if (eventSearchPara.keyword == null) {
            throw ExceptionUtil.createParamException("参数错误");
        }

        SponsorListResp sponsorListResp = new SponsorListResp();
        sponsorListResp.list = sponsorsModuleService.converseParaList(sponsorsModuleService.searchSponsor
                (eventSearchPara.keyword));
        return sponsorListResp;
    }





    /**
     * 获取活动相关的一键融资服务
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getservice", method = {RequestMethod.POST})
    public Object getService(@RequestBody EventPara req) {
        return eventService.getService(req.eventId);
    }


    /**
     * 修改活动相关的一键融资服务
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/reviseservice", method = {RequestMethod.POST})
    public Object reviseService(@RequestBody EventServiceReq req) {
        return eventService.reviseService(req);
    }

    /**
     * 根据推广链接的id获取事件活动详情
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getbylinkid", method = {RequestMethod.POST})
    public Object getEventActivityInfoByLinkId(@RequestBody ExtensionLinkReq req) {
        if (!NumberUtil.isValidId(req.extensionLinkId)) {
            throw ExceptionUtil.createParamException("推广链接参数错误");
        }
        if (!NumberUtil.isValidId(req.pid)) {
            throw ExceptionUtil.createParamException("pid参数错误");
        }
        return eventService.getEventInfoByLinkIdJson(req);
    }

    @RequestMapping(value = "/tickets", method = {RequestMethod.POST})
    public Object getTickets(@RequestBody EventPara req) {
        return eventService.getTickets(req);
    }
    /************************************************************************************路演大赛
     * app相关接口************************************************************************************/

    /**
     * 排序规则的名词和ID列表给客户端.
     * 推荐排序、最新活动、最热活动.
     */
    @RequestMapping(value = "/orderrules", method = {RequestMethod.POST})
    public Object getOrderRules() {
        return OrderRulesEnum.getOrderRules();
    }


    /**
     * 收藏或者取消收藏活动
     */
    @RequestMapping(value = "/favorite", method = {RequestMethod.POST})
    public Object favorite(@RequestBody AppReq req) {
        if (NumberUtil.isInValidId(req.eventId) || req.type == null || NumberUtil.isInValidId(req
                .uid)) {
            throw ExceptionUtil.createParamException("参数错误");
        }
        return eventService.favoriteEvent(req);
    }

    /**
     * app端调用-根据机构获得机构下的活动列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getorgdetail", method = {RequestMethod.POST})
    public Object getOrgDetail(@RequestBody OrgReq req) {
        return eventService.getOrgDetail(req);

    }

    /**
     * app端调用-根据活动获取我的票券相关信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/ticket", method = {RequestMethod.POST})
    public Object getMyTickets(@RequestBody AppReq req) {
        return eventService.getMyTicket(req);

    }

    /**
     * 活动城市列表
     */
    @RequestMapping(value = "/citylist", method = {RequestMethod.POST})
    public Object getEventCityList() {
        return eventService.getEventCityList();
    }


    /**
     * 查看用户报名详细记录
     */
    @RequestMapping(value = "/checkorderstatus", method = {RequestMethod.POST})
    public Object checkOrderStatus(@RequestBody AppReq req) {
        if (NumberUtil.isInValidId(req.eventId) || NumberUtil.isInValidId(req.uid)) {
            throw ExceptionUtil.createParamException("参数错误");
        }
        return eventService.checkOrderStatus(req);
    }

    /**
     * 取用户创建 收藏 报名 活动列表
     */
    @RequestMapping(value = "/usereventlist", method = {RequestMethod.POST})
    public Object userEventList(@RequestBody AppReq req) {
        if (req.type == null || NumberUtil.isInValidId(req.page) || NumberUtil.isInValidId(req.size)
                || NumberUtil.isInValidId(req.uid) || req.eventType == null) {
            throw ExceptionUtil.createParamException("参数错误");
        }
        return eventService.userEventList(req);
    }

    /**
     * app活动排序列表
     */
    @RequestMapping(value = "/orderedlist", method = {RequestMethod.POST})
    public Object orderedList(@RequestBody AppReq req) {
        if (NumberUtil.isInValidId(req.page)) {
            throw ExceptionUtil.createParamException("page参数错误");
        }
        if (NumberUtil.isInValidId(req.size)) {
            throw ExceptionUtil.createParamException("size参数错误");
        }
        req.ruleId = (req.ruleId == null) ? 0 : req.ruleId;
        if (req.ruleId != 0 && req.ruleId != 1 && req.ruleId != 2) {
            req.ruleId = 0;
        }

        return eventService.orderedList(req);
    }

    /**
     * 获取活动详情content
     */
    @RequestMapping(value = "/getcontent", method = {RequestMethod.POST})
    public Object getContent(@RequestBody AppReq req) {
        if (NumberUtil.isInValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("参数错误");
        }
        return eventService.getcontent(req);
    }


    /**
     * 根据报名id获取活动报名信息
     */
    @RequestMapping(value = "/getEventRecord", method = {RequestMethod.POST})
    public Object getEventRecord(@RequestBody AppReq req) {
        if (NumberUtil.isInValidId(req.recordId)) {
            throw ExceptionUtil.createParamException("参数错误");
        }
        return eventRecordService.getEventRecord(req);
    }

    /************************************************************************************路演大赛
     * app相关接口end************************************************************************************/
    /**
     * 用户主动取消报名
     */
    @RequestMapping(value = "/cancel", method = {RequestMethod.POST})
    public Object signUpCancel(@RequestBody AppReq req) {
        return eventService.signUpCancel(req);
    }

    /**
     * 通过调用接口执行定时器 活动开始前一天早上10:00（若报名数为0，则不推送）
     */
    @RequestMapping(value = "/task", method = RequestMethod.POST)
    public Object task() {
        return eventService.task();
    }


    /**
     * 科委项目
     * 活动list
     */
    @RequestMapping(value = "/keweieventlist", method = {RequestMethod.POST})
    public Object keweiEventList(@RequestBody EventSearchPara req) {
        if (NumberUtil.isInValidId(req.page)) {
            throw ExceptionUtil.createParamException("page参数错误");
        }
        if (NumberUtil.isInValidId(req.size)) {
            throw ExceptionUtil.createParamException("size参数错误");
        }

        return eventService.getkeweiEventList(req);
    }

    /**
     * 科委项目
     * 给定活动，对其活动Logo打水印操作
     */
    @RequestMapping(value = "/watermark", method = {RequestMethod.POST})
    public Object watermark(@RequestBody EventSearchPara req) {
        if (NumberUtil.isInValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("page参数错误");
        }
        if (StringUtil.isEmpty(req.detail)) {
            throw ExceptionUtil.createParamException("detail参数错误");
        }
        if (StringUtil.isEmpty(req.logo)) {
            throw ExceptionUtil.createParamException("logo参数错误");
        }

        return eventService.watermark(req);
    }


    /**********************************************3.4.0创业活动相关接口************************************************/
    /**
     * 获取创业活动列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/eventList", method = {RequestMethod.POST})
    public Object getEventList(@RequestBody AppReq req) {
        if (NumberUtil.isInValidId(req.page)) {
            throw ExceptionUtil.createParamException("page参数错误");
        }
        if (NumberUtil.isInValidId(req.size)) {
            throw ExceptionUtil.createParamException("size参数错误");
        }
        return eventService.getEventList(req);
    }

    /**
     * 获取当前活动的票的信息
     */
    @RequestMapping(value = "/getTicket", method = {RequestMethod.POST})
    public Object getEventTicket(@RequestBody AppReq req) {

        return eventService.getEventTicket(req);
    }

    /**
     * 获取活动下所有报名记录信息
     */
    @RequestMapping(value = "/getEventRecords", method = {RequestMethod.POST})
    public Object getEventRecordsPage(@RequestBody AppReq req) {

        return eventService.getEventRecordsPage(req);
    }

    /**
     * 获取某用户某活动下的票券信息
     */
    @RequestMapping(value = "/buyerticket", method = {RequestMethod.POST})
    public Object getBuyerTicket(@RequestBody AppReq req) {

        return eventService.getBuyerTicket(req);
    }

    /**
     * 获取某用户某活动下的订单信息
     */
    @RequestMapping(value = "/ticketOrder", method = {RequestMethod.POST})
    public Object getTicketOrder(@RequestBody AppReq req) {

        return eventService.getTicketOrder(req);
    }

    /**
     * 根据ticket_no(recordId) 找到票券信息
     */
    @RequestMapping(value = "/recordInfo", method = {RequestMethod.POST})
    public Object getRecordInfo(@RequestBody AppReq req) {

        return eventService.getRecordInfo(req);
    }

    /**
     * 根据老的活动id获取新的活动详情链接,要做此接口的原因是：原来的老的创业活动数据导入到新的云活动平台之后，活动的id会变化，为兼容此问题，
     * 在新的云活动平台的event表中有个old_event_id,通过此id获取新的活动链接
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getLinkByOldId", method = {RequestMethod.POST})
    public Object getLinkByOldId(@RequestBody AppReq req) {
        if (StringUtil.isEmpty(req.key)) {
            throw ExceptionUtil.createParamException("活动解析错误");
        }
        return eventService.getLinkByOldId(req);
    }

    /**
     * 根据老的活动id获取新的活动详情链接,要做此接口的原因是：原来的老的融资活动数据导入到新的云活动平台之后，活动的id会变化，为兼容此问题，
     * 在新的云活动平台的event表中有个old_event_id,通过此id获取新的活动链接
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getFinancingEventLinkByOldId", method = {RequestMethod.POST})
    public Object getFinancingEventLinkByOldId(@RequestBody AppReq req) {
        if (StringUtil.isEmpty(req.key)) {
            throw ExceptionUtil.createParamException("活动解析错误");
        }
        return eventService.getFinancingEventLinkByOldId(req);
    }

    /**
     * 取活动详情h5
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/geteventh5", method = {RequestMethod.POST})
    public Object getEventH5(@RequestBody AppReq req) {
        if (NumberUtil.isInValidId(req.eventId)) {
            throw ExceptionUtil.createParamException("活动id错误");
        }

        return eventService.getEventH5(req);
    }

    /**
     * 推荐活动
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/recommend", method = {RequestMethod.POST})
    public Object recommend(@RequestBody AppReq req) {
        return eventService.recommend(req);
    }

    /**
     * 用户在X活动下的的报名记录
     *
     * @param event
     * @return
     */
    @RequestMapping(value = "/ticket/info", method = {RequestMethod.POST})
    public Object getUserTicketInfo(@RequestBody EventPara event) {
        if (NumberUtil.isInValidId(event.uid)) {
            throw ExceptionUtil.createParamException("uid参数异常");
        }
        if (NumberUtil.isInValidId(event.eventId)) {
            throw ExceptionUtil.createParamException("eventId参数异常");
        }
        return eventService.getUserTicketInfo(event);
    }

    /**
     * 推荐首页的活动
     */
    @RequestMapping(value = "/recomment/home", method = RequestMethod.POST)
    public Object geEventsForRecommendHome(@RequestBody EventQuery query) {
        return eventService.getRecommendHome(query);
    }

    /**
     * 首页数据
     *
     * @return
     */
    @RequestMapping(value = "/portal/statistics", method = RequestMethod.GET)
    public Object portalStatistics() {
        return eventService.portalStatistics();
    }


}
