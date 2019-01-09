package com.welian.client.cloudevent.http;

import org.sean.framework.bean.BaseResult;
import com.welian.beans.cloudevent.investor.InvestorGroupListResultResp;
import com.welian.beans.cloudevent.investor.InvestorGroupReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author jeizas
 * @Date 2018 10/17/18 2:39 PM
 */
@FeignClient(name = "cloudevent")
public interface InvestorGroupClient {

    /**
     * 获取投资人分组信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/group/list", method = {RequestMethod.POST})
    BaseResult<InvestorGroupListResultResp> getInvestorGroupListByOrgId(@RequestBody InvestorGroupReq req);
}
