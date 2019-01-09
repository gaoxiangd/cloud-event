package com.welian.beans.cloudevent.record;

import com.welian.beans.cloudevent.Ticket;

import java.util.List;

/**
 * created by GaoXiang on 2017/12/5
 */
public class CustomSignUpReq extends SignUpReq{


    /**
     * 接收票券信息
     */
    public List<Ticket> ticketList;


    public Integer eventId;

    /**
     * 导入失败用，失败原因
     */
    public String reason;

    /**
     * 是否有效
     */
    public Boolean validImportData;

    /**
     * 是否带例子
     */
    public Boolean hasExample;
    public Integer channel; //交易渠道。6-商品订单；7-PC；8-课程小程序；9-投融助手小程序；10-活动小程序
    public Integer costType;

}
