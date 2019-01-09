package com.welian.client.cloudevent.http;

import org.sean.framework.bean.BaseResult;
import org.sean.framework.bean.PageData;
import com.welian.beans.cloudevent.org.OrgInfoResp;
import com.welian.beans.cloudevent.org.OrgPackagePageReq;
import com.welian.beans.cloudevent.org.PackageInfoResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 机构套餐
 *
 * @Author jeizas
 * @Date 2018 10/10/18 3:04 PM
 */
@FeignClient(name = "cloudevent")
public interface PackageClient {

    /**
     * 套餐管理列表
     */
    @RequestMapping(value = "/org/package/list", method = {RequestMethod.POST})
    BaseResult<PageData<OrgInfoResp>> list(@RequestBody OrgPackagePageReq req);

    @RequestMapping(value = "/org/package/save", method = {RequestMethod.POST})
    BaseResult<Void> updatePackage(@RequestBody OrgPackagePageReq req);

    /**
     * 获得所有套餐信息
     */
    @RequestMapping(value = "/org/package/packageinfo", method = {RequestMethod.POST})
    BaseResult<PackageInfoResp> getPackageInfo();
}
