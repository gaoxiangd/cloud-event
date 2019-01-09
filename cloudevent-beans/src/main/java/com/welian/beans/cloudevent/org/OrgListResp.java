package com.welian.beans.cloudevent.org;

import org.sean.framework.bean.BaseBean;

import java.util.List;

/**
 * created by GaoXiang on 2017/8/31
 */
public class OrgListResp extends BaseBean {

    //列表总数量
    public Integer count;

    public List<OrgInfoResp> list;

    //待审核总条数
    public Integer waitVerifyCount;

    //审核通过总条数
    public Integer verifySuccessCount;

    //审核失败总条数
    public Integer verifyErrorCount;

    //总条数
    public Integer totalCount;


}
