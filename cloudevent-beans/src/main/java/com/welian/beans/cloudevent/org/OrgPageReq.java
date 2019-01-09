package com.welian.beans.cloudevent.org;

import com.welian.beans.cloudevent.BasePageReq;

import java.util.List;

/**
 * created by GaoXiang on 2017/8/30
 */
public class OrgPageReq extends BasePageReq {

    //keyword类型
    public Integer type;
    //关键词
    public String keyword;
    //审核状态
    public Integer verifyStatus;
    //未通过理由
    public String reason;

    public Integer orgId;

    public List<EvidencesResp> evidences;
    /**
     *运营后台要传的操作者uid
     */
    public Integer adminUid ;

    public Boolean isFull;

}
