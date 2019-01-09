package com.welian.beans.cloudevent.event;

import java.util.List;

/**
 * Created by dayangshu on 17/7/10.
 */
public class EventSearchPara  {
    public Integer orgId;
    public String keyword;
    public Integer page;
    public Integer size;
    public ParameterReq parameter;
    public Byte level;

    /**活动券用的 **/
    public Integer status;
    public Long startTime;
    public Long endTime;
    public List<Integer> uids;
    public List<Integer> eventIds;
    public Integer eventId;
    public String detail;
    public String logo;
    /**
     * 外部调用
     */
    public Long modifyTime;

    /**
     * 城市id
     */
    public Integer cityId;

}
