package com.welian.beans.cloudevent;

import java.util.List;

/**
 * created by GaoXiang on 2017/11/30
 */
public class CustomModule {

    public Integer isCustomVerify;

    public Integer isCharge;

    public Integer isGroupChat;

    public String groupChatNumber;

    public Integer isPublicity;

    public Integer numberStatus;

    public List<Ticket> ticketList;

    /**
     *  签到授权码
     */
    public String code;

    /**
     * 免费活动时票的数量
     */
    public Integer freeCount;

    /**
     * 这里作为返回参数表示已经报名的人数
     */
    public Integer signUpTotal;




}
