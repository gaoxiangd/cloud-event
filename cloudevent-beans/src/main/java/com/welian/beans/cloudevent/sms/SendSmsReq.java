package com.welian.beans.cloudevent.sms;

import org.sean.framework.bean.PageReq;

/**
 * Created by dayangshu on 17/7/19.
 */
public class SendSmsReq extends PageReq {
    public Integer eventId;
    public String content;
}
