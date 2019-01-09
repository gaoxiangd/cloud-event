package com.welian.beans.cloudevent.signup;


import com.welian.beans.cloudevent.event.EventPara;

import java.util.List;

/**
 * Created by dayangshu on 17/7/13.
 */
public class SearchSignupListResp  {

    public List<SearchSignupResp> list;

    public Integer total;

    public Integer totalNoSearch;

    public EventPara event;

    public Integer checkPassCount;

    public Integer weixinCount;

    public Integer appCount;

    /**
     * 签到票数
     */
    public Integer checkinCount;


}
