package com.welian.pojo;

/**
 * created by GaoXiang on 2017/10/10
 */
public class EventManage {

    public Integer eventId;

    /**
     * 是否开启推广，默认0表示开启推广，1表示不开启
     */
    public Integer openExtension;

    public Integer lineType;

    public String title;

    public Integer publishUid;

    public String cityName;

    public Integer cityId;

    public Long startTime;

    public Long createTime;

    public Integer orgId;

    public Integer hotState;

    public Integer recommendState;

    public Integer state;

    public Integer commodityId;

    public Integer templateId;

    public Integer couponStatus;

    public Integer getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(Integer couponStatus) {
        this.couponStatus = couponStatus;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getOpenExtension() {
        return openExtension;
    }

    public void setOpenExtension(Integer openExtension) {
        this.openExtension = openExtension;
    }

    public Integer getLineType() {
        return lineType;
    }

    public void setLineType(Integer lineType) {
        this.lineType = lineType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPublishUid() {
        return publishUid;
    }

    public void setPublishUid(Integer publishUid) {
        this.publishUid = publishUid;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
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
}
