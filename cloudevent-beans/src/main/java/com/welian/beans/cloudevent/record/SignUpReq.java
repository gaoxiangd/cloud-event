package com.welian.beans.cloudevent.record;

import com.welian.beans.cloudevent.AddtionalForm;
import com.welian.beans.cloudevent.user.UserReq;
import org.sean.framework.bean.BaseBean;

import java.util.List;

/**
 * Created by memorytale on 2017/7/14.
 */
public class SignUpReq extends BaseBean {

    /**
     * 自定义字段
     */
    public List<AddtionalForm> additionalForm;
    /**
     * 1表示PC端创建项目，2表示H5创建项目 3表示app老接口兼容 4.Excel导入
     */
    public Integer type;

    public Integer extensionLinkId;

    public UserReq user;
    /**
     * 是否是导项目
     */
    public Integer isImportRecord;


}
