package com.welian.beans.cloudevent.event;

import com.welian.beans.cloudevent.CityReq;
import com.welian.beans.cloudevent.user.UserResp;

/**
 * Created by dayangshu on 17/7/11.
 */
public class EventList  {
    public Byte state;
    public Byte isFree;
    public Byte type;
    public String logo;
    public String title;
    public String timeStr;
    public String address;
    public CityReq city;
    public Integer collectionTotal;
    public Integer signUpTotal;
    public Integer checkInTotal;
    public Integer eventId;
    public Byte lineType;
    public Byte recommendState;

    public UserResp publishUser;

}
