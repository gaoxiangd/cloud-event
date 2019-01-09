package com.welian.beans.cloudevent.record;

import com.welian.beans.cloudevent.AddtionalForm;
import com.welian.beans.cloudevent.CustomModule;
import com.welian.beans.cloudevent.ExtensionLinkResp;
import com.welian.beans.cloudevent.Guest;
import com.welian.beans.cloudevent.ProjectModule;
import com.welian.beans.cloudevent.Sponsor;
import com.welian.beans.cloudevent.Tag;
import com.welian.beans.cloudevent.org.CityResp;
import com.welian.beans.cloudevent.org.OrgResp;

import java.util.List;

/**
 * web获取活动详情
 * Created by memorytale on 2017/7/13.
 */
public class EventDetailResp extends EventBaseResp {

    public String logo;

    public String detail;

    public List<Sponsor> sponsors;

    public List<Guest> guests;

    public Double longitude;

    public Double latitude;

    public String formUrl;

    public String commonUrl;

    public String customUrl;

    public ProjectModule projectModule;

    public CustomModule customModule;

    public OrgResp org ;

    public List<Tag> tags;

    public Long startTime;

    public Long endTime;

    public Integer isFavorite;

    public ExtensionLinkResp extensionLink;

    public String resultUrl;

    public String ticketUrl;

    public List<AddtionalForm> additionalForm;

    /**
     * 3.6.0加，如果用户有一张票券，返回改票券地址
     */
    public List<String> ticketUrls;

    /**
     * 3.6.5加，h5页面优化需要id
     */
    public CityResp city;
}
