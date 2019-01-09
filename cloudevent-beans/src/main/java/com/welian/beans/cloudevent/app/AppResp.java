package com.welian.beans.cloudevent.app;

import com.welian.beans.cloudevent.EvenBaseReq;
import com.welian.beans.cloudevent.event.EventPara;
import com.welian.beans.cloudevent.project.ProjectResp;
import com.welian.beans.cloudevent.user.UserResp;

import java.util.List;

/**
 * Created by zhaopu on 2017/10/16.
 */
public class AppResp extends EvenBaseReq {

    public List<AppResp> list;

    public EventPara event;

    public UserResp user;

    public ProjectResp project;

}
