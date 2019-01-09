package com.welian.beans.cloudevent.org;

import com.welian.beans.cloudevent.event.EventPara;

import java.util.List;

/**
 * Created by zhaopu on 2017/12/6.
 */
public class EventBillsResp {

    public EventPara event;

    public String totalAmount; //活动总收入

    public String receiptAmount; //实际到账金额

    public Integer state; //1 提交结算 2 结算中 3 结算完成

    public List<EventBillsResp> list;

    public String orgTotalAmount; //机构下活动总收入


    public String   reason;
}
