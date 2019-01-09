package com.welian.beans.cloudevent;

import com.welian.beans.cloudevent.user.UserResp;

import java.util.List;

/**
 * Created by memorytale on 2017/4/21.
 */
public class ExtensionLinkReq extends BasePageReq {

    public String extensionLinkName;

    public Integer extensionLinkId;

    public Integer eventId;

    /**
     * 选择展示的url类型
     * TYPE_CHOOSE_COMMON(1),//选择通用模板
     * TYPE_CHOOSE_FORM(2),//选择活动表单
     * TYPE_CHOOSE_CUSTOM(3);//选择专题官网
     */
    public Integer linkListType;

    /**
     * 推广链接的唯一的key标示
     */
    public String uniqueKey;

    /**
     * 此条推广链接渠道的类型，0表示默认的推广链接，1表示导项目时创建的链接，2表示平台方创建的链接
     */
    public Integer linkType;

    public Integer pid;

    public List<UserResp> investors;

    public String bpUrl;
    public String bpName;
    public Integer bpId;
    public Integer recordId;

    /**
     * 获取创业活动跳转地址的入参
     */
    public String tradeNo;

}
