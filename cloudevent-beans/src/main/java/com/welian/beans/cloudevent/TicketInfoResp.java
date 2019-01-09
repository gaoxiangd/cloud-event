package com.welian.beans.cloudevent;

import com.welian.beans.cloudevent.event.EventPara;
import com.welian.beans.cloudevent.project.ProjectResp;
import com.welian.beans.cloudevent.record.EventRecordUserResp;
import com.welian.beans.cloudevent.record.EventTicketResp;
import com.welian.beans.cloudevent.trade.TradeResp;
import org.sean.framework.bean.BaseBean;

import java.util.List;

/**
 * created by GaoXiang on 2017/10/18
 */
public class TicketInfoResp extends BaseBean{

    public EventTicketResp ticket;
    public EventRecordUserResp user;
    public EventPara event;
    public ProjectResp project;
    public List<Ticket> ticketTypes;
    public TradeResp trade;
    /**
     * A 3.7.8 是否可以取消报名，0不可以 1可以
     */
    public Integer canCancel;
}
