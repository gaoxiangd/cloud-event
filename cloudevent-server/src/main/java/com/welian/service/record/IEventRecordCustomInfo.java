package com.welian.service.record;

import com.welian.beans.cloudevent.record.CustomSignUpReq;
import com.welian.beans.cloudevent.record.CustomSignUpResp;

/**
 * created by GaoXiang on 2017/12/5
 */
public interface IEventRecordCustomInfo {

     CustomSignUpResp signUp(CustomSignUpReq req) ;
}
