package com.welian.client.cloudevent;

import org.sean.framework.bean.BaseResult;
import com.welian.config.FeignGsonConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Description: Feign Client
 * 将HTTP请求转化为Class 供调用方使用
 * Created by Sean.xie on 2017/2/13.
 */
@FeignClient(name = "${client.demo-server.name}", configuration = FeignGsonConfiguration.class)
//@FeignClient(name = "test", fallback = HystrixClientFallback.class)
@RequestMapping("/demo")
public interface DemoClient {
    @RequestMapping(method = RequestMethod.GET, value = "/get/{id}")
    BaseResult<DemoResp> get(@PathVariable("id") Long id);

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    BaseResult<DemoResp> update(DemoReq req);
}
