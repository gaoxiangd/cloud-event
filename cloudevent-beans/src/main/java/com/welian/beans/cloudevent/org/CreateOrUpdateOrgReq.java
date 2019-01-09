package com.welian.beans.cloudevent.org;

import com.welian.beans.cloudevent.CityReq;
import org.sean.framework.bean.BaseBean;

/**
 * Description: 创建或者修改机构接口的请求参数
 * 用于传递参数
 * Created by Sean.xie on 2017/2/16.
 */
public class CreateOrUpdateOrgReq extends BaseBean {

    /**
     * 机构的logo
     */
    public String logo;
    /**
     * 机构绑定的手机号码，当平台方自己修改时不传
     */
    public String mobile;
    /**
     * 机构的名称
     */
    public String name;

    /**
     * 机构的介绍
     */
    public String intro;

    /**
     * 运营后台添加机构的创建者
     */
    public Integer uidAdd;

    /**
     * 在创建或者修改平台方账号时使用，
     * 1表示运营后台创建或者修改平台方账号信息
     * 2表示平台方创建或者修改平台方账号信息
     */
    public Integer type;
    /**
     * 机构的网址
     */
    public String website;
    /**
     * 机构的网址
     */
    public String address;
    /**
     * 机构所在城市
     */
    public CityReq city;

    /**
     * 机构的id，不传或者传0时表示创建机构，非0时表示修改机构
     */
    public Integer orgId;

}
