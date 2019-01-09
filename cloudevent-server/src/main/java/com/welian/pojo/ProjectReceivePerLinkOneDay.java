package com.welian.pojo;

public class ProjectReceivePerLinkOneDay {
    private Integer id;

    private Integer extensionLinkId;

    private Long date;

    private Integer projectReceiveCount;

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

    public Integer getExtensionLinkId() {
        return extensionLinkId;
    }

    public void setExtensionLinkId(Integer extensionLinkId) {
        this.extensionLinkId = extensionLinkId;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Integer getProjectReceiveCount() {
        return projectReceiveCount;
    }

    public void setProjectReceiveCount(Integer projectReceiveCount) {
        this.projectReceiveCount = projectReceiveCount;
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