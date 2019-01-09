package com.welian.beans.cloudevent.event;


/**
 * Description: 活动对象
 * Created by dayangshu on 2017/7/4.
 */
public class SaveEventImportReq  {
    /**
     * 机构id，导活动时使用
     */
    public Integer orgId;
    /**
     * 用户id，导活动时使用
     */
    public Integer uid;

    public EventPara event;

    public Long createTime;

    public Long modifyTime;
}
