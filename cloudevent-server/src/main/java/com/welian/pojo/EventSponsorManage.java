package com.welian.pojo;

/**
 * created by GaoXiang on 2017/10/11
 */
public class EventSponsorManage {
    private Integer id;

    private Integer eventId;


    private String sponsorName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }
}
