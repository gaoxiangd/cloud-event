package com.welian.pojo;

public class Event {
    private Integer id;

    private Integer publishUid;

    private Integer orgId;

    private Integer templateId;

    private String title;

    private Byte lineType;

    private String address;

    private Integer cityId;

    private String cityName;

    private Double latitude;

    private Double longitude;

    private Long startTime;

    private Long endTime;

    private Long deadlineTime;

    private String logo;

    private Byte openExtension;

    private Integer favoriteCount;

    private Byte state;

    private Long createTime;

    private Long modifyTime;

    private Byte recommendStatus;

    private Integer linkUrlCount;

    private Integer adCount;

    private Integer detailBrowseCount;

    private Integer formBrowseCount;

    private Integer commodityId;

    private Integer oldEventId;

    private Integer couponStatus;

    private String detail;

    private Integer joinedCount;

    private Byte costType;

    public Integer getJoinedCount() {
        return joinedCount;
    }

    public void setJoinedCount(Integer joinedCount) {
        this.joinedCount = joinedCount;
    }

    public Byte getCostType() {
        return costType;
    }

    public void setCostType(Byte costType) {
        this.costType = costType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPublishUid() {
        return publishUid;
    }

    public void setPublishUid(Integer publishUid) {
        this.publishUid = publishUid;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Byte getLineType() {
        return lineType;
    }

    public void setLineType(Byte lineType) {
        this.lineType = lineType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(Long deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public Byte getOpenExtension() {
        return openExtension;
    }

    public void setOpenExtension(Byte openExtension) {
        this.openExtension = openExtension;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
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

    public Byte getRecommendStatus() {
        return recommendStatus;
    }

    public void setRecommendStatus(Byte recommendStatus) {
        this.recommendStatus = recommendStatus;
    }

    public Integer getLinkUrlCount() {
        return linkUrlCount;
    }

    public void setLinkUrlCount(Integer linkUrlCount) {
        this.linkUrlCount = linkUrlCount;
    }

    public Integer getAdCount() {
        return adCount;
    }

    public void setAdCount(Integer adCount) {
        this.adCount = adCount;
    }

    public Integer getDetailBrowseCount() {
        return detailBrowseCount;
    }

    public void setDetailBrowseCount(Integer detailBrowseCount) {
        this.detailBrowseCount = detailBrowseCount;
    }

    public Integer getFormBrowseCount() {
        return formBrowseCount;
    }

    public void setFormBrowseCount(Integer formBrowseCount) {
        this.formBrowseCount = formBrowseCount;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public Integer getOldEventId() {
        return oldEventId;
    }

    public void setOldEventId(Integer oldEventId) {
        this.oldEventId = oldEventId;
    }

    public Integer getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(Integer couponStatus) {
        this.couponStatus = couponStatus;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }
}