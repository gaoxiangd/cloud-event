package com.welian.beans.cloudevent.record;

public class EventRecordResp {

    public Integer id;

    public Integer orgId;

    public Integer eventId;

    public Integer extensionLinkId;

    public Byte signUpType;

    public Integer uid;

    public Integer ticketLock;

    public Byte source;

    public Byte state;

    public Long createTime;

    public Long modifyTime;

    public Integer ticketId;

    public String orderNumber;

    public String ticketNumber;

    public String reason;

    public Integer costType;

    /**
        * 订单号
     */
    public String tradeNo;

    /**
     * 是否是导入
     */
    public Integer isImport;

}