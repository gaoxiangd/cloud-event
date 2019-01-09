package com.welian.beans.cloudevent.org;

import java.util.List;

public class OrgAuthResp {
    /**
     * 1:未审核 2:审核中 3:已通过 4:未通过
     */
    public Integer verifyStatus;

    //证明材料
    public List<EvidencesResp> evidences;

    /**
     * 申请认证时间
     */
    public Long applyTime;

    public String reason;
}
