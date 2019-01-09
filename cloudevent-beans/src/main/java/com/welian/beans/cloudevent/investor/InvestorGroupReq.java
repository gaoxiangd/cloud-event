package com.welian.beans.cloudevent.investor;

import org.sean.framework.bean.PageReq;

/**
 * Created by memorytale on 2017/4/21.
 */
public class InvestorGroupReq extends PageReq {
    /**
     * 投资人分组id
     */
    public Integer investorGroupId;

    public String investorGroupName;

    public Integer orgId;

    public Integer uid;
    /**
     * 投资人分组与投资人的关联id
     */
    public Integer investorGroupRelationId;


    public Integer investorCount;
}
