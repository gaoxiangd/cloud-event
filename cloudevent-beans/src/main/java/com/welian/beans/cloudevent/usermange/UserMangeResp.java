package com.welian.beans.cloudevent.usermange;

import java.util.List;

/**
 * Created by zhaopu on 2018/6/22.
 */
public class UserMangeResp {

    public Long time; //推送时间

    public String object; //推送对象

    public String content; //推送内容


    public Integer eventId; //活动id

    public String eventTitle; //活动名字

    public Integer recordNum; //报名人数

    public Integer smsCount; //剩余条数

    public Integer price; //每一条价格 分

    public String tradeNo;

    public String form;

    public Long count;

    public List<UserMangeResp> list;

}
