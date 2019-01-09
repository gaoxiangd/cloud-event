package com.welian.beans.cloudevent.manage;


import java.util.List;

/**
 *
 *运营后台-推荐或热门请求参数
 * created by GaoXiang on 2017/10/13
 */
public class RmdOrHotReq  {
    /**
     * 1.推荐 2.热门 3.推荐到首页
     */
    public Integer type;

    public Integer eventId;

    public List<Integer> cityList;

    public Integer adminUid;
    /**
     * 线上活动使用 0取消推荐 1推荐
     */
    public Integer action;


}
