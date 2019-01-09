package com.welian.beans.cloudevent.org;

import com.welian.beans.cloudevent.BasePageReq;

/**
 */
public class OrderChannelReq extends BasePageReq {

    public Integer orgId;//合作方id

    public String keyword;

    public String remark;

    public Integer star;

    public Integer signUpId;

    /**
     * 取项目管理列表时，
     * 1表示keyword根据项目名称进行筛选
     * 2表示keyword根据创业者名称进行筛选
     * 3表示keyword根据备注进行筛选
     */
    public Integer type;


    public Integer industryId;

    public Integer cityId;

    public Integer starId;

    public Integer stageId;

    public Integer days;

    //前段是否需要角色信息
    public Boolean withRoles;

}
