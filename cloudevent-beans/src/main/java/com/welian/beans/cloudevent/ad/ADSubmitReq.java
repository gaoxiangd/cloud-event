package com.welian.beans.cloudevent.ad;


import java.util.List;

/**
 * Created by memorytale on 2017/4/21.
 */
public class ADSubmitReq  {

    /**
     * 报名的id
     */
    public Integer signUpId;

    public Integer extensionLinkId;

    public List<ADReq> list;

    /**
     *加密的报名id
     */
    public String recordIdStr;

}
