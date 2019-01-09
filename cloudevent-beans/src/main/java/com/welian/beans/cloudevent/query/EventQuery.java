package com.welian.beans.cloudevent.query;

import java.util.List;

/**
 * created by GaoXiang on 2018/1/19
 */
public class EventQuery extends BaseQuery{
    public Integer uid;
    public Integer eventId;
    public Integer orgId;
    public Long startTime;
    public Long endTime;
    public Integer pageCustom;
    public Integer sizeCustom;
    public List<Integer> eventIds;
    public Integer isImport;

    public Integer cityId;
    public Integer type;
}
