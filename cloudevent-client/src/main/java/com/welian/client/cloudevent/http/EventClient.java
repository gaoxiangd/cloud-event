package com.welian.client.cloudevent.http;

import com.welian.beans.cloudevent.EventResp;
import com.welian.beans.cloudevent.EventServiceResp;
import com.welian.beans.cloudevent.ExtensionLinkReq;
import com.welian.beans.cloudevent.ExtensionLinkResp;
import com.welian.beans.cloudevent.OrgAndEventListResp;
import com.welian.beans.cloudevent.TicketInfoResp;
import com.welian.beans.cloudevent.TicketResp;
import com.welian.beans.cloudevent.ad.ReviseADReq;
import com.welian.beans.cloudevent.app.AppReq;
import com.welian.beans.cloudevent.app.AppResp;
import com.welian.beans.cloudevent.app.OrderRulesResp;
import com.welian.beans.cloudevent.app.RecordInfoResp;
import com.welian.beans.cloudevent.event.EventInfo;
import com.welian.beans.cloudevent.event.EventListResp;
import com.welian.beans.cloudevent.event.EventPara;
import com.welian.beans.cloudevent.event.EventSearchPara;
import com.welian.beans.cloudevent.event.OptionResp;
import com.welian.beans.cloudevent.event.PortalStatistics;
import com.welian.beans.cloudevent.event.SaveEventGuestImportReq;
import com.welian.beans.cloudevent.event.SaveEventImportReq;
import com.welian.beans.cloudevent.event.SaveEventReq;
import com.welian.beans.cloudevent.event.SaveEventResp;
import com.welian.beans.cloudevent.event.SponsorListResp;
import com.welian.beans.cloudevent.favorite.FavoriteImportReq;
import com.welian.beans.cloudevent.org.OrgReq;
import com.welian.beans.cloudevent.query.EventQuery;
import com.welian.beans.cloudevent.record.EventRecordListResp;
import com.welian.beans.cloudevent.record.EventRecordResp;
import com.welian.beans.cloudevent.record.RecordsUserResp;
import com.welian.beans.cloudevent.user.EventServiceReq;
import com.welian.beans.cloudevent.user.EventUserSignUpResp;
import org.sean.framework.bean.BaseResult;
import org.sean.framework.bean.PageData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * Description: Feign Client
 * 将HTTP请求转化为Class 供调用方使用
 * Created by Sean.xie on 2017/2/13.
 */
@FeignClient(name = "cloudevent")
@RequestMapping("/event")
public interface EventClient {

    @RequestMapping(value = "/import/financingActivity", method = {RequestMethod.POST})
    BaseResult<SaveEventResp> importSave(@RequestBody SaveEventImportReq req);

    @RequestMapping(value = "/import/commonActivity", method = {RequestMethod.POST})
    BaseResult<SaveEventResp> importSaveCommonEvent(@RequestBody SaveEventImportReq req);

    /**
     * 保存活动信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/import/commonActivityGuest", method = {RequestMethod.POST})
    BaseResult<SaveEventResp> commonActivityGuest(@RequestBody SaveEventGuestImportReq req);

    @RequestMapping(value = "/getEventById", method = {RequestMethod.POST})
    BaseResult<EventInfo> getEventById(@RequestBody Integer eventId);

    @RequestMapping(value = "/getunikeymap", method = {RequestMethod.POST})
    BaseResult<Map<String, Integer>> getEventIdByUniquekeys(@RequestBody List<String> uniqueKeys);

    @RequestMapping(value = "/batchinsertfavorite", method = {RequestMethod.POST})
    BaseResult<Map<String, Integer>> batchInsertFavorite(@RequestBody List<FavoriteImportReq> favoriteImportReqs);

    /**
     * 获取活动列表
     */
    @RequestMapping(value = "/simplelist", method = {RequestMethod.POST})
    BaseResult<List<EventPara>> getAllSimpleEventList(@RequestBody EventSearchPara eventSearchPara);

    /**
     * 搜索主办方
     *
     * @param eventSearchPara
     * @return
     */
    @RequestMapping(value = "/searchsponsor", method = {RequestMethod.POST})
    BaseResult<SponsorListResp> searchsponsor(@RequestBody EventSearchPara eventSearchPara);

    /**
     * 保存活动信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    BaseResult<SaveEventResp> saveEvent(@RequestBody SaveEventReq req);

    /**
     * 科委项目
     * 活动list
     */
    @RequestMapping(value = "/keweieventlist", method = {RequestMethod.POST})
    BaseResult<EventPara> keweiEventList(@RequestBody EventSearchPara req);


    /**
     * 科委项目
     * 给定活动，对其活动Logo打水印操作
     */
    @RequestMapping(value = "/watermark", method = {RequestMethod.POST})
    BaseResult<EventPara> watermark(@RequestBody EventSearchPara req);


    /**
     * 取活动详情h5
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/geteventh5", method = {RequestMethod.POST})
    BaseResult<EventPara> geteventh5(@RequestBody AppReq req);

    /**
     * 小程序获取票券信息和活动部分信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/tickets", method = {RequestMethod.POST})
    BaseResult<TicketResp> getTickets(@RequestBody EventPara req);

    /**
     * 推荐首页的活动
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/recomment/home", method = {RequestMethod.POST})
    BaseResult<PageData<EventResp>> geEventsForRecommendHome(@RequestBody EventQuery query);

    /**
     * 官网数据统计
     * @return
     */
    @RequestMapping(value = "/portal/statistics", method = RequestMethod.GET)
    BaseResult<PortalStatistics> portalStatistics();

    // ----------- - - -----------

    /**
     * 获取活动详细信息
     *
     * @param eventPara
     * @return
     */
    @RequestMapping(value = "/getdetail", method = {RequestMethod.POST})
    BaseResult<com.welian.beans.cloudevent.event.EventResp> getEventDetail(@RequestBody EventPara eventPara);

    /**
     * 获取活动下所有通过审核的报名记录的用户信息
     *
     * @param eventPara
     * @return
     */
    @RequestMapping(value = "/recordsuser", method = {RequestMethod.POST})
    BaseResult<RecordsUserResp> getRecordsUser(@RequestBody EventPara eventPara);


    /**
     * 获取活动列表信息
     *
     * @param eventSearchPara
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    BaseResult<EventListResp> getEventList(@RequestBody EventSearchPara eventSearchPara);

    /**
     * 修改活动状态信息
     *
     * @param eventPara
     * @return
     */
    @RequestMapping(value = "/updatestatus", method = {RequestMethod.POST})
    BaseResult<ExtensionLinkResp> updateStatus(@RequestBody EventPara eventPara);

    /**
     * 获取活动配置信息
     *
     * @return
     */
    @RequestMapping(value = "/options", method = {RequestMethod.POST})
    BaseResult<OptionResp> getOptions();

    /**
     * 编辑活动的关联的广告
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/revisead", method = {RequestMethod.POST})
    BaseResult<Void> reviseAd(@RequestBody ReviseADReq req);

    /**
     * 获取活动相关的一键融资服务
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getservice", method = {RequestMethod.POST})
    BaseResult<EventServiceResp> getService(@RequestBody EventPara req);

    /**
     * 修改活动相关的一键融资服务
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/reviseservice", method = {RequestMethod.POST})
    BaseResult<Void> reviseService(@RequestBody EventServiceReq req) ;

    /**
     * 收藏或者取消收藏活动
     */
    @RequestMapping(value = "/favorite", method = {RequestMethod.POST})
    BaseResult<AppResp> favorite(@RequestBody AppReq req);

    /**
     * 活动城市列表
     */
    @RequestMapping(value = "/citylist", method = {RequestMethod.POST})
    BaseResult<AppResp> getEventCityList();


    /**
     * 查看用户报名详细记录
     */
    @RequestMapping(value = "/checkorderstatus", method = {RequestMethod.POST})
    BaseResult<AppResp> checkOrderStatus(@RequestBody AppReq req);

    /**
     * 取用户创建 收藏 报名 活动列表
     */
    @RequestMapping(value = "/usereventlist", method = {RequestMethod.POST})
    BaseResult<EventPara> userEventList(@RequestBody AppReq req);

    /**
     * 获取活动详情content
     */
    @RequestMapping(value = "/getcontent", method = {RequestMethod.POST})
    BaseResult<String> getContent(@RequestBody AppReq req);

    /**
     * 根据老的活动id获取新的活动详情链接,要做此接口的原因是：原来的老的创业活动数据导入到新的云活动平台之后，活动的id会变化，为兼容此问题，
     * 在新的云活动平台的event表中有个old_event_id,通过此id获取新的活动链接
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getLinkByOldId", method = {RequestMethod.POST})
    BaseResult<ExtensionLinkResp> getLinkByOldId(@RequestBody AppReq req);

    /**
     * 用户在X活动下的的报名记录
     *
     * @param event
     * @return
     */
    @RequestMapping(value = "/ticket/info", method = {RequestMethod.POST})
    BaseResult<EventUserSignUpResp> getUserTicketInfo(@RequestBody EventPara event);

}
