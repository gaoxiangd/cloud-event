package com.welian.beans.cloudevent.manage;

import com.welian.beans.cloudevent.CityReq;
import com.welian.beans.cloudevent.PublisherResp;
import com.welian.beans.cloudevent.Sponsor;
import com.welian.beans.cloudevent.Ticket;
import com.welian.enums.cloudevent.EnumRecommendStatus;
import org.sean.framework.bean.BaseBean;

import java.util.List;

/**
 * created by GaoXiang on 2017/10/10
 */
public class EventManageResp extends BaseBean{

    public String title;

    public Integer id;

    /**
     * 已报名数
     */
    public Integer signUpTotal;


    /**
     * 已报限制数
     */
    public String signUpLimitTotal;

    public Integer lineType;

    /**
     * 发布机构的名字
     */
    public PublishOrgResp publishOrg;

    /**
     * 是否公开 0 私密 1公开
     */
    public Integer privateType;

    public CityReq city;

    public Long createTime;

    public PublisherResp publishUser;

    public List<Sponsor> sponsors;

    public Long startTime;

    /**
     * 推荐的城市
     */
    public List<CityReq> recommendCities;

    /**
     * 热门的城市
     */
    public List<CityReq> hotCities;
    /**
     * 0:不热门 1:热门 线上活动热门位状态(当且仅当活动为线上活动时生效)
     */
    public Integer onlineHot;

    /**
     * 0:不推荐 1:推荐 线上活动推荐位状态(当且仅当活动为线上活动时生效)
     */
    public Integer onlineRecommend;

    /**
     * commonUrl
     */
    public String linkCommonUrl;

    public Integer state;

    /**
     * 3.4.0j加活动类型和票券是否免费
     */
    public Integer costType;

    public Integer eventType;

    public List<Ticket> ticketList;

    /**
     * 活动是否存在城市推荐到首页
     */
    public Integer recommendHomeStatus = EnumRecommendStatus.INIT.getValue();

    /**
     * 活动是否存在城市推荐到app投融资页面
     */
    public Integer recommendFinnacingStatus = EnumRecommendStatus.INIT.getValue();

    /**
     * 以下3.6.5加
     */
    public String logo;

    /**
     * 用来显示标签 0.普通 1.new 2.hot .3已结束 4.已截止
     */
    public Byte sortType;

    /**
     *0:不展示报名数量 1:展示报名数量
     */
    public Integer numberState;

    /**
     * 活动开始时间（格式：xx/xx 周x）
     */
    public String startTimeStr;

    /**
     * 活动报名数量
     */
    public Integer recordNum;

    /**
     * 优惠券状态（A 3.7.2）
     */
    public Integer couponStatus;

    public Integer eventId;


}
