package com.welian.beans.cloudevent.user;

import org.sean.framework.bean.BaseBean;

import java.util.List;

/**
 * created by GaoXiang on 2018/3/21
 */
public class EventUserSignUpResp extends BaseBean{

    public List<EventUserSignUpResp> list;

    public String name;
    /**
     * 总数
     */
    public Integer totalCount;

    /**
     * 总价
     */
    public String totalAmount;

    public Long signUpTime;

    public Integer signUpState;

    public String url;

    /**
     * 是否已支付
     */
    public Integer payState;

}
