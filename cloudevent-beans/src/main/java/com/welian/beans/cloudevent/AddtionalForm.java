package com.welian.beans.cloudevent;

/**
 * Created by dayangshu on 17/7/4.
 */
public class AddtionalForm  {
    public byte type;
    public Integer id;
    public String label;
    public String value;
    /**
     * 表单字段报名时是否需要必填，0表示非必填，1表示必填
     */
    public byte required;
    /**
     * 限制字数，为空后台默认100
     */
    public Integer limit;
}
