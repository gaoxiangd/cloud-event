package com.welian.controller;

import com.welian.beans.cloudevent.event.EventPara;
import com.welian.beans.cloudevent.event.SaveEventReq;
import com.welian.beans.cloudevent.manage.EventManageReq;
import com.welian.beans.cloudevent.manage.RmdOrHotReq;
import com.welian.service.impl.ManageServiceImpl;
import org.sean.framework.util.NumberUtil;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by GaoXiang on 2017/10/10
 */
@RestController
@RequestMapping("/manage")
public class ServerManageController {

    @Autowired
    ManageServiceImpl manageService;

    /**
     * 运营后台根据条件获得融资活动列表,3.4.0兼容创业活动
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public Object getFinanceEventList(@RequestBody EventManageReq req) {
        if (!NumberUtil.isValidId(req.size)) {
            throw ExceptionUtil.createParamException("size参数错误");
        }
        if (!NumberUtil.isValidId(req.page)) {
            throw ExceptionUtil.createParamException("page参数错误");
        }

        return manageService.list(req);
    }

    /**
     * 运营后台-保存活动信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object saveEvent(@RequestBody SaveEventReq req) {

        return manageService.saveEvent(req, req.adminUid);
    }

    /**
     * 运营后台-删除活动
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object deleteEvent(@RequestBody EventManageReq req) {

        return manageService.deleteEvent(req);
    }

    /**
     * 运营后台-获得活动详情
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/detail", method = {RequestMethod.POST})
    public Object getDetail(@RequestBody EventPara req) {

        return manageService.getEventDetail(req);
    }

    /**
     * 运营后台-推荐或者热门或者推荐首页
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/recommendorhot", method = {RequestMethod.POST})
    public Object recommend(@RequestBody RmdOrHotReq req) {

        return manageService.recommendOrHot(req);
    }

    /**
     * 3.6.5，h5页面优化获取更多（最多三条的活动）
     */
    @RequestMapping(value = "/more", method = {RequestMethod.POST})
    public Object more(@RequestBody EventManageReq req) {
        if (!NumberUtil.isValidId(req.size)) {
            throw ExceptionUtil.createParamException("size参数错误");
        }
        if (!NumberUtil.isValidId(req.page)) {
            throw ExceptionUtil.createParamException("page参数错误");
        }

        return manageService.more(req);
    }

    /**
     * 屏蔽/回复正常 优惠券
     * @param req 活动运营后台请求参数
     * @return
     */
    @RequestMapping(value = "/updatecoupon", method = {RequestMethod.POST})
    public Object updateCoupon(@RequestBody EventManageReq req) {

        return manageService.updateCoupon(req);

    }
}
