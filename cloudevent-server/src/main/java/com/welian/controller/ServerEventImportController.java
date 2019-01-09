package com.welian.controller;

import com.welian.beans.cloudevent.event.EventListId;
import com.welian.beans.cloudevent.event.SaveEventGuestImportReq;
import com.welian.beans.cloudevent.event.SaveEventImportReq;
import com.welian.beans.cloudevent.favorite.FavoriteImportReq;
import com.welian.enums.cloudevent.SqlEnum;
import com.welian.service.impl.EventImportServiceImpl;
import org.sean.framework.util.NumberUtil;
import org.sean.framework.util.StringUtil;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 项目导入时使用
 */
@RestController
@RequestMapping("/event")
public class ServerEventImportController {

    @Autowired
    private EventImportServiceImpl eventImportService;

    /**
     * 保存活动信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/import/financingActivity", method = {RequestMethod.POST})
    public Object importSaveFinancingEvent(@RequestBody SaveEventImportReq req) {
        checkParams(req);


        if (req.event.projectModule == null) {
            throw ExceptionUtil.createParamException("项目报名参数错误");
        }
        return eventImportService.saveFinancingEvent(req);
    }

    /**
     * 保存活动信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/import/commonActivity", method = {RequestMethod.POST})
    public Object importSaveCommonEvent(@RequestBody SaveEventImportReq req) {
        checkParams(req);
        if (req.event.customModule == null) {
            throw ExceptionUtil.createParamException("创业报名参数错误");
        }
        return eventImportService.importSaveCommonEvent(req);
    }


    /**
     * 保存活动信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "updateEventSolr", method = {RequestMethod.POST})
    public Object updateEventSolr(@RequestBody SaveEventImportReq req) {

        return eventImportService.updateEventSolr();
    }

    @RequestMapping(value = "/updateEventSolrByNewEventId", method = {RequestMethod.POST})
    public Object updateEventSolrByNewEventId(@RequestBody EventListId eventPara) {
        if (StringUtil.isEmpty(eventPara.eventIds)) {
            throw ExceptionUtil.createParamException("请填写活动ids");
        }
        eventImportService.updateEventSolrByNewEventId(eventPara);
        return null;
    }

    /**
     * 保存活动信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/import/commonActivityGuest", method = {RequestMethod.POST})
    public Object commonActivityGuest(@RequestBody SaveEventGuestImportReq req) {
        return eventImportService.importSaveCommonEventGuest(req);
    }

    private void checkParams(SaveEventImportReq req) {
        if (!NumberUtil.isValidId(req.uid)) {
            throw ExceptionUtil.createParamException("请登录");
        }
        if (!NumberUtil.isValidId(req.orgId)) {
            throw ExceptionUtil.createParamException("机构参数错误");
        }
        if (!NumberUtil.isValidId(req.createTime)) {
            throw ExceptionUtil.createParamException("createTime参数错误");
        }
        if (!NumberUtil.isValidId(req.modifyTime)) {
            throw ExceptionUtil.createParamException("modifyTime参数错误");
        }
        if (req.event == null) {
            throw ExceptionUtil.createParamException("请输入活动内容");
        }
        if (!NumberUtil.isValidId(req.event.eventId)) {
            throw ExceptionUtil.createParamException("请填写活动id");
        }
        if (StringUtil.isEmpty(req.event.title)) {
            throw ExceptionUtil.createParamException("请填写活动标题");
        }
        if (req.event.title.length() > 200 || req.event.title.length() < 2) {
            throw ExceptionUtil.createParamException("活动标题字数大于2字或者小于50字");
        }
        if (req.event.lineType == null) {
            throw ExceptionUtil.createParamException("请选择活动line类型");
        }
        //如果是线下活动，必须要填写举办城市和举办地以及百度经纬度坐标
        if (req.event.lineType == SqlEnum.EventLineType.TYPE_OFFLINE.getByteValue().byteValue()) {
            if (req.event.city == null) {
                throw ExceptionUtil.createParamException("请选择活动举办城市");
            }
            if (StringUtil.isEmpty(req.event.address)) {
                throw ExceptionUtil.createParamException("请填写活动举办地址");
            }
            if (req.event.loc == null) {
                throw ExceptionUtil.createParamException("请定位活动地图坐标");
            }
        }
        if (!NumberUtil.isValidId(req.event.startTime)) {
            throw ExceptionUtil.createParamException("请选择活动开始时间");
        }
        if (!NumberUtil.isValidId(req.event.endTime)) {
            throw ExceptionUtil.createParamException("请选择活动结束时间");
        }
        if (!NumberUtil.isValidId(req.event.deadlineTime)) {
            // throw ExceptionUtil.createParamException("请选择活动报名截止时间");
            req.event.deadlineTime = req.event.startTime;
        }
        if (req.event.startTime >= req.event.endTime) {
            throw ExceptionUtil.createParamException("活动开始时间必须小于活动结束时间");
        }
        if (req.event.deadlineTime > req.event.endTime) {
            throw ExceptionUtil.createParamException("活动报名截止时间必须小于活动结束时间");
        }
        if (StringUtil.isEmpty(req.event.logo)) {
            throw ExceptionUtil.createParamException("请上传活动海报");
        }
        if (StringUtil.isEmpty(req.event.detail)) {
            throw ExceptionUtil.createParamException("请填写活动详情");
        }
        if (req.event.sponsors == null || req.event.sponsors.size() == 0) {
            throw ExceptionUtil.createParamException("主办方参数错误");
        }
        if (req.event.city == null) {
            throw ExceptionUtil.createParamException("城市参数错误");
        }
    }

    @RequestMapping(value = "/batchinsertfavorite", method = {RequestMethod.POST})
    public Object batchInsertFavorite(@RequestBody List<FavoriteImportReq> favoriteImportReqs) {
        eventImportService.batchInsertFavorite(favoriteImportReqs);
        return null;
    }

    @RequestMapping(value = "/cleanActiveJoinCount", method = {RequestMethod.POST})
    public Object cleanActiveJoinCount() {
        eventImportService.cleanActiveJoinCount();
        return null;
    }

    @RequestMapping(value = "/cleanActiveJoinCountByNewEventId", method = {RequestMethod.POST})
    public Object cleanActiveJoinCountByNewEventId(@RequestBody EventListId eventPara) {
        if (StringUtil.isEmpty(eventPara.eventIds)) {
            throw ExceptionUtil.createParamException("请填写活动ids");
        }
        eventImportService.cleanActiveJoinCountByNewEventId(eventPara);
        return null;
    }
}
