package com.welian.beans.cloudevent.signup;

import com.welian.beans.cloudevent.AddtionalForm;
import com.welian.beans.cloudevent.Ticket;
import com.welian.beans.cloudevent.project.ProjectResp;
import com.welian.beans.cloudevent.user.UserResp;

import java.util.List;

/**
 * Created by dayangshu on 17/7/13.
 */
public class SearchSignupResp  {
    public String reason;

    //'0: 正常；   1 用户主动取消报名;  2: 主办方删除报名记录 ；  3 ：待审核； 4: 审核拒绝',
    public Byte isVerify;
    public UserResp user;
    public Integer signUpId;
    public Long signUpTime;
    public Integer source;
    public List<AddtionalForm> additionalForm;
    public ProjectResp project;

    public List<Ticket> ticketTypes;

    public List<Ticket> list;

    /**
     * 1.默认 2.手动导入
     */
    public Integer isImport;

}
