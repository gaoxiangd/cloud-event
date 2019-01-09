package com.welian.pojo;

public class EventRecord {
    private Integer id;

    private Integer orgId;

    private Integer eventId;

    private Integer extensionLinkId;

    private Byte signUpType;

    private Integer uid;

    private Integer ticketLock;

    private Byte source;

    private Byte state;

    private Long createTime;

    private Long modifyTime;

    private Integer ticketId;

    private String orderNumber;

    private String reason;

    private String ticketNumber;

    private String tradeNo;

    private Long tradeEndTime;

    private String ticketUrl;

    private Integer oldRecordId;

    private Integer isImport;

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

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getExtensionLinkId() {
        return extensionLinkId;
    }

    public void setExtensionLinkId(Integer extensionLinkId) {
        this.extensionLinkId = extensionLinkId;
    }

    public Byte getSignUpType() {
        return signUpType;
    }

    public void setSignUpType(Byte signUpType) {
        this.signUpType = signUpType;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getTicketLock() {
        return ticketLock;
    }

    public void setTicketLock(Integer ticketLock) {
        this.ticketLock = ticketLock;
    }

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
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

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber == null ? null : ticketNumber.trim();
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }

    public Long getTradeEndTime() {
        return tradeEndTime;
    }

    public void setTradeEndTime(Long tradeEndTime) {
        this.tradeEndTime = tradeEndTime;
    }

    public String getTicketUrl() {
        return ticketUrl;
    }

    public void setTicketUrl(String ticketUrl) {
        this.ticketUrl = ticketUrl == null ? null : ticketUrl.trim();
    }

    public Integer getOldRecordId() {
        return oldRecordId;
    }

    public void setOldRecordId(Integer oldRecordId) {
        this.oldRecordId = oldRecordId;
    }

    public Integer getIsImport() {
        return isImport;
    }

    public void setIsImport(Integer isImport) {
        this.isImport = isImport;
    }
}