package com.welian.beans.cloudevent.org;

import com.welian.beans.account.Role;
import com.welian.beans.cloudevent.user.UserResp;

/**
 * created by GaoXiang on 2017/8/31
 */
public class OrgInfoResp {
    /**
     * 修改时间字符串
     */
    public String reviseTimeTextTip;
    /**
     *
     */
    public CityResp city;

    public String logo;

    public String name;

    public String website;

    public String intro;

    public String address;

    public Integer id;

    /**
     * 套餐信息
     */
    public PackageResp _package;

    /**
     * 套餐信息
     */
    public OrgAuthResp auth;

    /**
     * 用户信息
     */

    public UserResp user;

    /**
     * 机构创建活动的数量(运营后台用)
     */
    public Integer eventCount;
    /**
     *  机构下创业活动数量(被推荐，且设置为“公开”)
     */
    public Integer normalEventCount;
    /**
     *  机构下路演活动数量(被推荐，且设置为“公开”)
     */
    public Integer roadShowEventCount;

    /**
     * 0表示可以修改，1表示不可以修改
     */
    public Integer canRevise;

    /**
     * 登录用户的角色
     */
    public Role userRole;




}
