package com.welian.beans.cloudevent.signup;

/**
 * Created by dayangshu on 17/7/13.
 */
public class SearchSignupReq  {
    /**
     *
     */
    public Integer eventId;
    /**
     *
     */
    public String keyword;
    /**
     *
     */
    public Integer page;
    /**
     *
     */
    public Integer size;
    /**
     *
     */
    public Byte type;
    /**
     *  以下参数用于运营后台报名列表
     */
    public String name;

    public String mobile;
    /**
     * 0: 通过； 3 ：待审核； 4: 审核拒绝
     */
    public Integer state;

    /**
     * A 3.8.6 是否返回数量信息
     */
    public Boolean withCount;
}
