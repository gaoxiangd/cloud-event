package com.welian.pojo;

public class EventInvestorGroupRelation {
    private Integer id;

    private Integer eventId;

    private Integer investorGroupId;

    private Long createTime;

    private Long modifyTime;

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

    public Integer getInvestorGroupId() {
        return investorGroupId;
    }

    public void setInvestorGroupId(Integer investorGroupId) {
        this.investorGroupId = investorGroupId;
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