package com.welian.beans.cloudevent;

import com.welian.beans.cloudevent.event.EventPara;
import com.welian.beans.cloudevent.org.OrgInfoResp;
import org.sean.framework.bean.BaseBean;

import java.util.List;

/**
 * created by GaoXiang on 2017/10/16
 */
public class OrgAndEventListResp extends BaseBean{

    public OrgInfoResp organization;

    public List<EventPara> list;

    public Integer count;

}
