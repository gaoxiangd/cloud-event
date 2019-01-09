package com.welian.pojo;

import java.util.Date;

public class EventCityRelation {
    private Integer id;

    private Integer cityId;

    private Integer eventId;

    private Long hotTime;

    private Long recommendTime;

    private Integer hotState;

    private Integer recommendState;

    private Integer recommendFinancingState;

    private Long recommendFinancingTime;

    private Integer recommendHomeStatus;

    private Long recommendHomeTime;

    private Long unrecommendHomeTime;

    private Date createTime;

    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Long getHotTime() {
        return hotTime;
    }

    public void setHotTime(Long hotTime) {
        this.hotTime = hotTime;
    }

    public Long getRecommendTime() {
        return recommendTime;
    }

    public void setRecommendTime(Long recommendTime) {
        this.recommendTime = recommendTime;
    }

    public Integer getHotState() {
        return hotState;
    }

    public void setHotState(Integer hotState) {
        this.hotState = hotState;
    }

    public Integer getRecommendState() {
        return recommendState;
    }

    public void setRecommendState(Integer recommendState) {
        this.recommendState = recommendState;
    }

    public Integer getRecommendHomeStatus() {
        return recommendHomeStatus;
    }

    public void setRecommendHomeStatus(Integer recommendHomeStatus) {
        this.recommendHomeStatus = recommendHomeStatus;
    }

    public Long getRecommendHomeTime() {
        return recommendHomeTime;
    }

    public void setRecommendHomeTime(Long recommendHomeTime) {
        this.recommendHomeTime = recommendHomeTime;
    }

    public Long getUnrecommendHomeTime() {
        return unrecommendHomeTime;
    }

    public void setUnrecommendHomeTime(Long unrecommendHomeTime) {
        this.unrecommendHomeTime = unrecommendHomeTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getRecommendFinancingState() {
        return recommendFinancingState;
    }

    public void setRecommendFinancingState(Integer recommendFinancingState) {
        this.recommendFinancingState = recommendFinancingState;
    }

    public Long getRecommendFinancingTime() {
        return recommendFinancingTime;
    }

    public void setRecommendFinancingTime(Long recommendFinancingTime) {
        this.recommendFinancingTime = recommendFinancingTime;
    }
}