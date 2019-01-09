package com.welian.pojo;

public class SmsHistory {
    private Integer id;

    private Integer orgId;

    private String eventIds;

    private String eventTitles;

    private Integer pushSmsCount;

    private String content;

    private Long createTime;

    private Long modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getEventIds() {
        return eventIds;
    }

    public void setEventIds(String eventIds) {
        this.eventIds = eventIds == null ? null : eventIds.trim();
    }

    public String getEventTitles() {
        return eventTitles;
    }

    public void setEventTitles(String eventTitles) {
        this.eventTitles = eventTitles == null ? null : eventTitles.trim();
    }

    public Integer getPushSmsCount() {
        return pushSmsCount;
    }

    public void setPushSmsCount(Integer pushSmsCount) {
        this.pushSmsCount = pushSmsCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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
}