package com.welian.service.impl;

import com.welian.beans.cloudevent.ua.UAReq;
import com.welian.beans.cloudevent.ua.UAResp;
import com.welian.beans.cloudevent.ua.Ua;
import com.welian.mapper.EventUaInfoMapper;
import com.welian.pojo.EventUaInfo;
import com.welian.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * created by GaoXiang on 2017/8/23
 */
@Service("UAService")
public class UAServiceImpl{
    @Autowired
    private EventUaInfoMapper eventUaInfoMapper;

    public UAResp uaSave(UAReq req) {
        EventUaInfo eventUaInfo = new EventUaInfo();
        Ua ua = req.ua;
        eventUaInfo.setResource(req.resource);
        eventUaInfo.setUid(req.uid);
        if (ua != null) {
            eventUaInfo.setUa(ua.ua);
            if (ua.browser != null) {
                eventUaInfo.setBrowserName(ua.browser.name);
                eventUaInfo.setBrowserVersion(ua.browser.version);
                eventUaInfo.setBrowserMajor(ua.browser.major);
            }
            if (ua.engine != null) {
                eventUaInfo.setEngineName(ua.engine.name);
                eventUaInfo.setEngineVersion(ua.engine.version);
            }
            if (ua.os != null) {
                eventUaInfo.setOsName(ua.os.name);
                eventUaInfo.setOsVersion(ua.os.version);
            }
            if (ua.device != null) {
                eventUaInfo.setDeviceModel(ua.device.model);
                eventUaInfo.setDeviceType(ua.device.type);
                eventUaInfo.setDeviceVendor(ua.device.vendor);
            }
            if (ua.cpu != null) {
                eventUaInfo.setCpuArchitecture(ua.cpu.architecture);
            }
        }
        eventUaInfo.setCreateTime(new Date().getTime());
        int result = eventUaInfoMapper.insertSelective(eventUaInfo);
        if (result == 0) {
            throw ExceptionUtil.createParamException("添加失败");
        }
        return null;
    }
}
