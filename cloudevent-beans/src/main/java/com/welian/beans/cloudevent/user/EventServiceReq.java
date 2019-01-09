package com.welian.beans.cloudevent.user;

import com.welian.beans.cloudevent.ad.ADResp;
import com.welian.beans.cloudevent.investor.InvestorGroupReq;
import org.sean.framework.bean.PageReq;

import java.util.List;

/**
 * Created by memorytale on 2017/4/25.
 */
public class EventServiceReq extends PageReq {

    /**
     * 活动的id
     */
    public Integer eventId;
    /**
     * 是否开启一键融资服务，默认0表示不开启，1表示开启
     */
    public Integer isOpenFinancingService;
    /**
     * 分组设置，0表示使用微链默认认证投资人，1表示使用自定义的投资人分组
     */
    public Integer groupSetting;
    /**
     * 投资人分组信息，只有在group_setting=1时进行校验
     */
    public List<InvestorGroupReq> investorGroups;
    /**
     * 匹配的投资人个数
     */
    public Integer investorMatchCount;
    /**
     * 广告列表
     */
    public List<ADResp> adGroups;

}
