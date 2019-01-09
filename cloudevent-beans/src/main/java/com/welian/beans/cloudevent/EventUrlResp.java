package com.welian.beans.cloudevent;

import com.welian.beans.cloudevent.ad.ADResp;

import java.util.List;

/**
 * created by GaoXiang on 2017/12/13
 */
public class EventUrlResp  {

    public ExtensionLinkResp extensionLink;

    public String ticketUrl;

    /**
     * 广告列表
     */
    public List<ADResp> adGroups;

    /**
     * recordId的base64加密，用于前端拼接链接
     */
    public String recordIdStr;

    public Integer recordId;

    /**
     * 返回给前端要展示的字符串
     */
    public String resultStr;

    public Integer eventId;

}
