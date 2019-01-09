package com.welian.beans.cloudevent.checkIn;

import com.welian.beans.cloudevent.Ticket;
import com.welian.beans.cloudevent.event.EventPara;
import com.welian.beans.cloudevent.user.UserResp;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaopu on 2017/12/4.
 */
public class CheckInResp  {

    public Integer state;

    public String msg;

    public EventPara event;

    public UserResp user;

    public List<Ticket> ticketTypes;

    public Integer workerStatus; //-1  员工没有签到权限  0  员工有签到权限

    public String code;

    public String signinUrl;


    public Integer eventId;

    public Integer count;

    public Map<Integer,Integer> validaChekInMap;

    public Integer couponStatus; //是否屏蔽优惠券（0不屏蔽 1屏蔽）

}
