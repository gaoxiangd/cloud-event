package com.welian.beans.cloudevent.admin;

import com.welian.beans.cloudevent.user.UserResp;

import java.util.List;

/**
 * Created by zhaopu on 2018/1/9.
 */
public class AdminResp  {

    public Integer eventId;

    public String eventTitle;

    /**
     * 说明
     */
    public String intro;

    /**
     * 金额
     */
    public Long price;

    /**
     * 结算时间
     */
    public Long createTime;

    /**
     * 结算状态
     */
    public Integer status;

    /**
     * 拒绝原因
     */
    public String reason;

    /**
     * 批次号(交易单号)
     */
    public String batchNum;

    /**
     * 服务费
     */
    public Long serviceAmount;

    public Long count;

    /**
     * 待结算金额
     */
    public Long waitAmount;

    /**
     * 待结算笔数
     */
    public Long waitTotal;

    public List<AdminResp> list;

    /**
     * 结算账号
     */
    public UserResp user;

    public Integer eventType;

}
