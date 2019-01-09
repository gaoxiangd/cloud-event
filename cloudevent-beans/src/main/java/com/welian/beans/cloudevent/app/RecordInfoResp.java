package com.welian.beans.cloudevent.app;

import com.welian.beans.cloudevent.Ticket;
import com.welian.beans.cloudevent.event.EventPara;
import com.welian.beans.cloudevent.user.UserResp;

import java.util.List;

/**
 * created by GaoXiang on 2018/1/20
 */
public class RecordInfoResp {

    public UserResp user;

    public EventPara event;

    public List<Ticket> tickets;

    public String resultUrl;

    public Integer signInState;

    public Long signInTime;

    public Integer recordState;
}
