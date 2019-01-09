package com.welian.pojo;

public class ProjectReceivePerActivityOneDay {
    private Integer id;

    private Integer projectReceiveCount;

    private Integer eventId;

    private Long createTime;

    private Long modifyTime;

    private Long date;

    private Integer ticketReceiveCount;

    private Integer signInCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectReceiveCount() {
        return projectReceiveCount;
    }

    public void setProjectReceiveCount(Integer projectReceiveCount) {
        this.projectReceiveCount = projectReceiveCount;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
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

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Integer getTicketReceiveCount() {
        return ticketReceiveCount;
    }

    public void setTicketReceiveCount(Integer ticketReceiveCount) {
        this.ticketReceiveCount = ticketReceiveCount;
    }

    public Integer getSignInCount() {
        return signInCount;
    }

    public void setSignInCount(Integer signInCount) {
        this.signInCount = signInCount;
    }
}