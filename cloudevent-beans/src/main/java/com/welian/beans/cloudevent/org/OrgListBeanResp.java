package com.welian.beans.cloudevent.org;

import com.welian.beans.cloudevent.user.UserResp;

/**
 * Created by welian_gyc on 2017/4/26.
 */
public class OrgListBeanResp  {
    public Integer orgId;//合作方id
    public String name;//合作方名称
    public String logo;//合作方logo
    public UserResp user;//合作方帐号管理员
    public Integer eventCount;//活动事件数量
    public Long createTime;//合作方帐号开通时间
}
