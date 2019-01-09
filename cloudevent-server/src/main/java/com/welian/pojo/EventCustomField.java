package com.welian.pojo;

public class EventCustomField {
    private Integer id;

    private Integer eventId;

    private Byte fieldType;

    private String title;

    private Byte state;

    private Long createTime;

    private Long modifyTime;

    private Byte required;

    private Integer limitCount;

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

    public Byte getFieldType() {
        return fieldType;
    }

    public void setFieldType(Byte fieldType) {
        this.fieldType = fieldType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
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

    public Byte getRequired() {
        return required;
    }

    public void setRequired(Byte required) {
        this.required = required;
    }

    public Integer getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }
}