package com.welian.pojo;

public class EventRecordAdRelation {
    private Integer id;

    private Integer eventRecordId;

    private Integer adId;

    private Long createTime;

    private Long modifyTime;

    private Byte state;

    private Integer extensionLinkId;

    private Integer eventId;

    private Integer orgId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventRecordId() {
        return eventRecordId;
    }

    public void setEventRecordId(Integer eventRecordId) {
        this.eventRecordId = eventRecordId;
    }

    public Integer getAdId() {
        return adId;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Integer getExtensionLinkId() {
        return extensionLinkId;
    }

    public void setExtensionLinkId(Integer extensionLinkId) {
        this.extensionLinkId = extensionLinkId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
}