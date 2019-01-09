package com.welian.beans.cloudevent.investor;

import org.sean.framework.bean.BaseBean;

/**
 * Description: Request Bean
 * 用于传递参数
 * Created by Sean.xie on 2017/2/16.
 */
public class InvestorGroupListBeanResp extends BaseBean {

    /**
     * 此投资人分组属于哪个机构下的
     */
    public Integer orgId;

    public Integer investorGroupId;
    /**
     * 投资人分组的名称
     */
    public String investorGroupName;
    /**
     * 分组投资人个数
     */
    public Integer investorCount;

    public Long createTime;


}
