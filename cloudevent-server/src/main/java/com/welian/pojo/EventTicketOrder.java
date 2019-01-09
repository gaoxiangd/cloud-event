package com.welian.pojo;

public class EventTicketOrder {
    private Integer id;

    private Integer eventRecordId;

    private Integer eventId;

    private String ticketNo;

    private Integer commodityDetailId;

    private Integer ticketState;

    private Integer signState;

    private Long signTime;

    private Long createTime;

    private Long modifyTime;

    private Integer ticketId;

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

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo == null ? null : ticketNo.trim();
    }

    public Integer getCommodityDetailId() {
        return commodityDetailId;
    }

    public void setCommodityDetailId(Integer commodityDetailId) {
        this.commodityDetailId = commodityDetailId;
    }

    public Integer getTicketState() {
        return ticketState;
    }

    public void setTicketState(Integer ticketState) {
        this.ticketState = ticketState;
    }

    public Integer getSignState() {
        return signState;
    }

    public void setSignState(Integer signState) {
        this.signState = signState;
    }

    public Long getSignTime() {
        return signTime;
    }

    public void setSignTime(Long signTime) {
        this.signTime = signTime;
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
}