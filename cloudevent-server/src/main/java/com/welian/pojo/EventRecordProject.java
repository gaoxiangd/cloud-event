package com.welian.pojo;

public class EventRecordProject {
    private Integer id;

    private Integer eventRecordId;

    private Integer pid;

    private String remark;

    private Byte starLevel;

    private Long createTime;

    private Long signUpTime;

    private Integer deliveryCount;

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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(Byte starLevel) {
        this.starLevel = starLevel;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getSignUpTime() {
        return signUpTime;
    }

    public void setSignUpTime(Long signUpTime) {
        this.signUpTime = signUpTime;
    }

    public Integer getDeliveryCount() {
        return deliveryCount;
    }

    public void setDeliveryCount(Integer deliveryCount) {
        this.deliveryCount = deliveryCount;
    }
}