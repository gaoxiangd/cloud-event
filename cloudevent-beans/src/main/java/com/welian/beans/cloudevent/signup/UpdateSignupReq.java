package com.welian.beans.cloudevent.signup;

import java.util.List;

/**
 * Created by dayangshu on 17/7/13.
 */
public class UpdateSignupReq  {
    /**
     * 活动id
     */
    public Integer eventId;
    /**
     * 0:正常,1:用户主动取消报名,2:主办方删除报名记录,3:待审核,4:审核拒绝
     */
    public Byte state;
    /**
     *
     */
    public List<Integer> signUpList;

    /**
     * 审核拒绝的理由
     */
    public String reason;
}
