package com.welian.beans.cloudevent;

/**
 * Created by zhaopu on 2017/12/5.
 */
public class TicketInfo {
    public Integer id;

    public String ticketNo;

    public Integer state; //状态 0.未签到 1.已签到  2.报名成功 3.审核中 4.审核失败

    public Long signTime;

    public String tradeNo;

    public Long recordTime; //报名时间

    public Integer recordId;

    public Integer sort;//排序字段
}
