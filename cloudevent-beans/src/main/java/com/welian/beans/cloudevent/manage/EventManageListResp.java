package com.welian.beans.cloudevent.manage;

import org.sean.framework.bean.BaseBean;

import java.util.List;

/**
 * created by GaoXiang on 2017/10/10
 */
public class EventManageListResp extends BaseBean{

    public List<EventManageResp> list;

    public Integer totalCount;

    public Integer recommendCount;


}
