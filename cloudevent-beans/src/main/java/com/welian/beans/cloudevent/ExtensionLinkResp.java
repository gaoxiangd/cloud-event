package com.welian.beans.cloudevent;


/**
 * Created by memorytale on 2017/4/21.
 */
public class ExtensionLinkResp  {

    public Integer extensionLinkId;
    /**
     * 推广链接的种类，0表示默认的推广链接，1表示导项目时创建的链接，2表示平台方创建的链接
     */
    public Integer linkType;

    public String uniqueKey;

    /**
     * web端传入
     */
    public Integer aid;

    public String extensionLinkName;

    public String extensionLinkUrl;


    public String commonUrl;
    public String formUrl;
    public String customUrl;

}
