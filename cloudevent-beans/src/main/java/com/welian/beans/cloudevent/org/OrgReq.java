package com.welian.beans.cloudevent.org;

import org.sean.framework.bean.PageReq;

/**
 * Created by welian_gyc on 2017/4/26.
 */
public class OrgReq extends PageReq {

    /**
     * 合作方id
     */
    public Integer orgId;
    /**
     * 角色类型
     */
    public Byte roleType;

    /**
     * 活动类型 1.普通创业活动 2.路演大赛和融资活动
     */
    public Integer eventType;

    /**
     * 请求是否带分页参数
     */
    public boolean withPage;
}
