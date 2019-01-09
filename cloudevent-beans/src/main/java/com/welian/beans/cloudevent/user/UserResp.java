package com.welian.beans.cloudevent.user;

/**
 * Description: Response Bean
 * 用于转化响应Json,供调用这直接使用
 * Created by Sean.xie on 2017/2/16.
 */
public class UserResp {

    public Integer id;

    public Integer uid;

    public String name;

    public String avatar;

    public Integer investorAuth;

    public String company;

    public String position;

    public String mobile;

    public Integer investorGroupRelationId;

    public Integer ticketCount;
    /**
     * 3.4.0加，报名的时间
     */
    public String signUpTime;

    public String phone;
}
