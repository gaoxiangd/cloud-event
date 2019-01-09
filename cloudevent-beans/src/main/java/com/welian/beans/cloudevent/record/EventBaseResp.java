package com.welian.beans.cloudevent.record;

import org.sean.framework.bean.BaseBean;

/**
 * Created by memorytale on 2017/7/13.
 */
public class EventBaseResp extends BaseBean {

    public Integer eventId;

    public String title;

    public String address;

    public String dateTimeRange;
    /**
     * 是线上活动还是线下活动,0:线下活动、1:线上活动
     */
    public Byte lineType;
    /**
     * 1表示可以报名，2表示该活动取消发布，3表示活动报名已截止
     */
    public Integer signUpType;
    /**
     * 1表示普通活动，2表示路演大赛活动，3表示融资投递活动
     */
    public Integer eventType;

    /**
     * 状态 1: 取消发布或者未发布 2: 已发布 3: 已结束 4:已删除,3基本用不到
     */
    public Byte state;

    public Integer commodityId;
}
