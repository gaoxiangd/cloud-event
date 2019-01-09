package com.welian.beans.cloudevent.record;

import com.welian.beans.cloudevent.project.BPResp;
import com.welian.beans.cloudevent.project.ProjectBaseReq;
import com.welian.beans.cloudevent.user.UserReq;
import org.sean.framework.bean.BaseBean;

/**
 * Created by memorytale on 2017/5/3.
 */
public class ProjectImportReq extends BaseBean {
    /**
     */
    public Integer recordId;
    /**
     */
    public Integer eventId;
    /**
     * 活动报名来源，1表示PC，2表示H5（不包括微信和app） 3表示app
     */
    public Integer source;

    public ProjectBaseReq project;

    public BPResp bp;

    public Long createTime;

    public Long modifyTime;

    public UserReq user;

    /**
     * '0: 正常；   1 用户主动取消报名;  2: 主办方删除报名记录 ；  3 ：待审核； 4: 审核拒绝
     */
    public Integer recordType;

}
