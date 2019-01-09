package com.welian.beans.cloudevent.usermange;

import java.util.List;

/**
 * Created by zhaopu on 2018/6/22.
 */
public class UserMangeReq  {
    public Integer orgId; //机构id

    public Integer page; //页数

    public Integer size; //数量

    public String content; //短信内容

    public List<Integer> eventIds; //活动id

    public Integer smsCount; //短信条数

    public Integer uid; //用户id

    public Integer payType; //

    public String returnUrl;//回调地址

    public String phone; //手机号

    public String name; //姓名

}

