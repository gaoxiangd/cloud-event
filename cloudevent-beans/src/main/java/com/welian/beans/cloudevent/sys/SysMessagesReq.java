package com.welian.beans.cloudevent.sys;

import org.sean.framework.bean.BaseBean;

import java.util.List;

/**
 * created by GaoXiang on 2017/8/18
 */
public class SysMessagesReq extends BaseBean{

    /**
     *  用于群删,多个消息Id
     */

    public List<Integer> messageIds;

    /**
     *  消息Id
     */
    public Integer messageId;
    /**
     * 角色类型
     */
    public Byte roleType;

}
