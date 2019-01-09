package com.welian.beans.cloudevent.event;

import com.welian.beans.cloudevent.AddtionalForm;
import com.welian.beans.cloudevent.CityReq;
import com.welian.beans.cloudevent.CustomModule;
import com.welian.beans.cloudevent.Guest;
import com.welian.beans.cloudevent.LocReq;
import com.welian.beans.cloudevent.ProjectModule;
import com.welian.beans.cloudevent.PublisherResp;
import com.welian.beans.cloudevent.Sponsor;
import com.welian.beans.cloudevent.Tag;
import com.welian.beans.cloudevent.Ticket;
import com.welian.beans.cloudevent.manage.RmdAndHotImportReq;
import com.welian.beans.cloudevent.org.OrgResp;
import com.welian.beans.cloudevent.project.ProjectResp;
import com.welian.beans.cloudevent.user.EventServiceReq;
import org.sean.framework.bean.PageReq;

import java.util.List;

public class EventPara extends PageReq {

    public List<EventPara> list;

    public Integer eventId;

    public Byte eventType;

    public String title;

    public Byte lineType;

    public String address;

    public CityReq city;

    public LocReq loc;

    public Long startTime;

    public Long endTime;

    public Long deadlineTime;

    public String logo;

    public Byte state;

    public String detail;

    public List<Sponsor> sponsors;

    public List<Guest> guests;

    public Byte isPromotion;//是否开启推广，默认0表示不开启推广，1表示开启推广

    public List<AddtionalForm> additionalForm;

    public List<Tag> tags;

    public ProjectModule projectModule;

    public CustomModule customModule;

    public Integer level;

    public Byte type;

    public Integer orgId;

    /**
     * 活动报名人数
     * 1：运营后台取活动报名数量，包含待审核和审核通过以及审核拒绝的报名
     * 2：APP取活动报名数量，包含待审核和审核通过的报名
     */
    public Integer recordNum;

    /**
     * 活动的详情链接地址，
     * 1:给app-server使用
     * (1)获取活动详情的接口使用
     * (2)获取机构的活动列表时使用
     * 2:给H5分享时使用
     */
    public String commonUrl;

    public EventServiceReq financingService;

    public PublisherResp publisher;

    //3.3.0.1 app兼容新增字段
    public Integer uid;

    /**
     * 是否已经报名，0未报名，1已报名
     */
    public Integer isSignUp;

    /**
     * 查看全部活动详情的URL
     */
    public String url;
    /**
     * 是否收藏，0没收藏，1收藏
     */
    public Integer isFavorite;
    /**
     * 0-正常,1-用户主动取消报名,2-主办方删除报名记录,3-待审核,4-审核拒绝
     */
    public Byte recordStatus;
    /**
     * 也是event的Id，试用不同场景的出参
     */
    public Integer id;

    public OrgResp publishOrg;

    /**
     * 取5条报名信息
     */
    public List<ProjectResp> signUpList;
    /**
     * 票信息
     */
    public List<Ticket> tickets;
    /**
     * 0正常，1：new活动,2：hot热门活动 3 已结束  4 已截止
     */
    public Byte sortType;//

    public String startTimeStr;
    /**
     * app端调用的参数
     */
    public String timeRange;

    /**
     * 原始活动状态 1: 未发布 2: 已发布 3: 已结束 4:已删除
     */
    public Integer originalState;


    public RmdAndHotImportReq rmdAndHotImportReq;

    //报名人数限制
    public Integer countLimit;

    //签到URL
    public String signinUrl;

    public String resultUrl;

    public String priceMin; //金额

    public String jumpUrl;

    public String jumpUrlCompatibility;//兼容安卓3.3.1版本跳转不了票券问题 多穿一个兼容url 给安卓跳转用

    public Integer tradeStatus; //0: 正常；   1 用户主动取消报名;  2: 主办方删除报名记录 ；  3 ：待审核； 4: 审核拒绝

    public Integer commodityId;

    /**
     * A3.7.2 默认的活动链接id
     */
    public Integer defaultExtensionLinkId;

    /**
     * A3.7.2 是否收费
     */
    public Integer costType;

    public Integer count;
}
