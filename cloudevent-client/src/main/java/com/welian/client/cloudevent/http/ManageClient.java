package com.welian.client.cloudevent.http;

import com.welian.beans.cloudevent.admin.AdminReq;
import com.welian.beans.cloudevent.admin.AdminResp;
import com.welian.beans.cloudevent.event.EventPara;
import com.welian.beans.cloudevent.event.EventResp;
import com.welian.beans.cloudevent.event.SaveEventReq;
import com.welian.beans.cloudevent.event.SaveEventResp;
import com.welian.beans.cloudevent.manage.EventManageListResp;
import com.welian.beans.cloudevent.manage.EventManageReq;
import com.welian.beans.cloudevent.manage.RmdOrHotReq;
import org.sean.framework.bean.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * created by GaoXiang on 2018/5/16
 */
@FeignClient(name = "cloudevent")
public interface ManageClient {

    @RequestMapping(value = "/manage/list", method = {RequestMethod.POST})
    BaseResult<EventManageListResp> list(@RequestBody EventManageReq req );

    @RequestMapping(value = "/manage/more", method = {RequestMethod.POST})
    BaseResult<EventManageListResp> more(@RequestBody EventManageReq req );

    /**
     * 财务系统结算
     */
    @PostMapping(value = "/admin/event/settlementes")
    BaseResult<AdminResp> getEventSettlementes(@RequestBody AdminReq req);

    /**
     * 运营后台-保存活动信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/manage/save", method = {RequestMethod.POST})
    BaseResult<SaveEventResp> saveEvent(@RequestBody SaveEventReq req);

    /**
     * 运营后台-删除活动
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/manage/delete", method = {RequestMethod.POST})
    BaseResult<Void> deleteEvent(@RequestBody EventManageReq req);

    /**
     * 运营后台-获得活动详情
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/manage/detail", method = {RequestMethod.POST})
    BaseResult<EventResp> getDetail(@RequestBody EventPara req);

    /**
     * 运营后台-推荐或者热门或者推荐首页
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/manage/recommendorhot", method = {RequestMethod.POST})
    BaseResult<Void> recommend(@RequestBody RmdOrHotReq req);


    /**
     * 屏蔽/回复正常 优惠券
     * @param req 活动运营后台请求参数
     * @return
     */
    @RequestMapping(value = "/manage/updatecoupon", method = {RequestMethod.POST})
    BaseResult<Void> updateCoupon(@RequestBody EventManageReq req);
}
