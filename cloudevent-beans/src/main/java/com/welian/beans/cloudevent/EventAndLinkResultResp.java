package com.welian.beans.cloudevent;

import com.welian.beans.cloudevent.ad.ADResp;
import com.welian.beans.cloudevent.org.OrgResp;
import com.welian.beans.cloudevent.record.EventDetailResp;

import java.util.List;

/**
 * Created by memorytale on 2017/4/26.
 */
public class EventAndLinkResultResp {

    public ExtensionLinkResp extensionLink;

    public EventDetailResp event;

    public OrgResp org;

    public List<ADResp> adGroups;

    public List<AddtionalForm> additionalForm;
}
