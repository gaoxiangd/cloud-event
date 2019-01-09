package com.welian.beans.cloudevent.org;


import java.util.List;
import java.util.Map;

/**
 * Created by welian_gyc on 2017/4/26.
 */
public class OrderChannelListResultResp  {
    /**
     * 分页的接入云活动列表
     */
    public List<OrgListBeanResp> list;

    /**
     * 分页的接入平台列表
     */
    public Map<Integer, OrgListBeanResp> map;

    /**
     * 接入平台帐号的个数
     */
    public Integer count;
}
