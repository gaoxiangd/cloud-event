package com.welian.pojo;

public class ProjectReceivePerOrgOneDay {
    private Integer id;

    private Integer orgId;

    private Integer projectReceiveCount;

    private Long date;

    private Long createTime;

    private Long modifyTime;

    private Integer ticketReceiveCount;

    private Integer signInCount;

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

    public Integer getProjectReceiveCount() {
        return projectReceiveCount;
    }

    public void setProjectReceiveCount(Integer projectReceiveCount) {
        this.projectReceiveCount = projectReceiveCount;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
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