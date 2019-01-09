package com.welian.beans.cloudevent;

import java.util.List;

/**
 * 票种
 */
public class Ticket  {
    public Integer id;

    public Byte signUpType;

    public String title;//票名

    public String intro;//票介绍

    public Double price;//单价

    public Double totalAmount;//总价

    public Integer count;//总量

    //剩余票数量
    public Integer remaindCount;

    //0.没有报名记录 1.有报名记录
    public Integer hasRecord;

    //购票的数量
    public Integer buyCount;

    //待审核的数量
    public Integer waitCount;

    public List<TicketInfo> ticketList;

    public TicketInfo ticketInfo;



}
