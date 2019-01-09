package com.welian.pojo;

public class ProjectFeedbackBackup {
    private Integer id;

    private Integer eventRecordId;

    private Integer investorId;

    private Integer state;

    private String lastestComment;

    private Long createTime;

    private Long modifyTime;

    private Long feedbackTime;

    private Integer orderId;

    private Long orderTime;

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

    public Integer getInvestorId() {
        return investorId;
    }

    public void setInvestorId(Integer investorId) {
        this.investorId = investorId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getLastestComment() {
        return lastestComment;
    }

    public void setLastestComment(String lastestComment) {
        this.lastestComment = lastestComment == null ? null : lastestComment.trim();
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

    public Long getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(Long feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Long orderTime) {
        this.orderTime = orderTime;
    }
}