package com.welian.pojo;

public class EventRecordJoinUser {

    private Integer id;

    private Integer orgId;

    private Integer eventId;

    private Integer extensionLinkId;

    private Byte signUpType;

    private Integer uid;

    private Byte source;

    private Byte state;

    private Long createTime;

    private Long modifyTime;

    private Integer ticketId;

    private String orderNumber;

    private String reason;

    private String name;

    private String company;

    private String position;

    private String phone;

    private String tradeNo;

    private Integer IsImport;

    public Integer getIsImport() {
        return IsImport;
    }

    public void setIsImport(Integer isImport) {
        IsImport = isImport;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}