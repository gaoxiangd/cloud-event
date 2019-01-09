package com.welian.beans.cloudevent.record;

import com.welian.beans.cloudevent.Ticket;
import com.welian.beans.cloudevent.user.UserReq;
import org.sean.framework.bean.BaseBean;

import java.util.List;

/**
 * Created by memorytale on 2017/5/3.
 */
public class CustomImportReq extends BaseBean {
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

    public Integer ticket_lock;//0正常 1锁定

    public Integer signin;//未签到（1） 已签到（1）

    public Long createTime;

    public Long modifyTime;

    public Long signTime;

    public UserReq user;

    public List<String> additionalForms;

    public List<Ticket> ticketList;
    /**
     * '0: 正常；   1 用户主动取消报名;  2: 主办方删除报名记录 ；  3 ：待审核； 4: 审核拒绝
     */
    public Integer recordType;

}
