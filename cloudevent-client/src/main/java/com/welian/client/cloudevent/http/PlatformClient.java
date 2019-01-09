package com.welian.client.cloudevent.http;

import com.welian.beans.cloudevent.org.OrderChannelListReq;
import com.welian.beans.cloudevent.org.OrderChannelListResultResp;
import com.welian.beans.cloudevent.org.OrgInfoResp;
import com.welian.beans.cloudevent.user.UserReq;
import org.sean.framework.bean.BaseResult;
import org.sean.framework.bean.PageData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Description: Feign Client
 * 将HTTP请求转化为Class 供调用方使用
 * Created by Sean.xie on 2017/2/13.
 */
@FeignClient(name = "cloudevent")
@RequestMapping("/org")
public interface PlatformClient {
    /**
     * 批量获取平台方信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getlistbyids", method = {RequestMethod.POST})
    BaseResult<OrderChannelListResultResp> getInfoListByIds(@RequestBody OrderChannelListReq req);

    /**
     * 根据用户的uid获取当前此用户绑定的机构列表，
     * 如果没有，为用户创建一个默认的机构
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getlistbyuid2", method = {RequestMethod.POST})
    BaseResult<PageData<OrgInfoResp>> getListByUid2(@RequestBody UserReq userReq);
}
