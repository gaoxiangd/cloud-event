package com.welian.beans.cloudevent.org;

import org.sean.framework.bean.BaseBean;

/**
 * created by GaoXiang on 2017/9/1
 */
public class PackageResp extends BaseBean {
    /**
     * 套餐id
     */
    public Integer id;
    /**
     * 套餐名称
     */
    public String name;
    /**
     * 套餐样式1表示基础版的样式，2表示高级版的样式
     */
    public Integer style;

    /**
     * 套餐的开通时间
     */
    public Long openTime;

}
