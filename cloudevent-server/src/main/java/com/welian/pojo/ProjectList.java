package com.welian.pojo;

public class ProjectList {
    private Integer pid;

    private String name;

    private String intro;

    private String cityName;

    private String industryName;

    private Double projectShare;

    private Integer projectAmount;

    private Byte projectAmountUnit;

    private Integer projectStage;

    private Integer uid;

    private Integer extensionLinkId;

    private Long signUpTime;

    private Integer eventRecordId;

    private Byte starLevel;

    private String remark;

    private Integer cityId;

    private Integer industryId;

    private Integer projectSignBpId;

    private String projectSignBpUrl;

    private String projectSignBpName;

    private Integer deliveryCount;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName == null ? null : industryName.trim();
    }

    public Double getProjectShare() {
        return projectShare;
    }

    public void setProjectShare(Double projectShare) {
        this.projectShare = projectShare;
    }

    public Integer getProjectAmount() {
        return projectAmount;
    }

    public void setProjectAmount(Integer projectAmount) {
        this.projectAmount = projectAmount;
    }

    public Byte getProjectAmountUnit() {
        return projectAmountUnit;
    }

    public void setProjectAmountUnit(Byte projectAmountUnit) {
        this.projectAmountUnit = projectAmountUnit;
    }

    public Integer getProjectStage() {
        return projectStage;
    }

    public void setProjectStage(Integer projectStage) {
        this.projectStage = projectStage;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getExtensionLinkId() {
        return extensionLinkId;
    }

    public void setExtensionLinkId(Integer extensionLinkId) {
        this.extensionLinkId = extensionLinkId;
    }

    public Long getSignUpTime() {
        return signUpTime;
    }

    public void setSignUpTime(Long signUpTime) {
        this.signUpTime = signUpTime;
    }

    public Integer getEventRecordId() {
        return eventRecordId;
    }

    public void setEventRecordId(Integer eventRecordId) {
        this.eventRecordId = eventRecordId;
    }

    public Byte getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(Byte starLevel) {
        this.starLevel = starLevel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }

    public Integer getProjectSignBpId() {
        return projectSignBpId;
    }

    public void setProjectSignBpId(Integer projectSignBpId) {
        this.projectSignBpId = projectSignBpId;
    }

    public String getProjectSignBpUrl() {
        return projectSignBpUrl;
    }

    public void setProjectSignBpUrl(String projectSignBpUrl) {
        this.projectSignBpUrl = projectSignBpUrl == null ? null : projectSignBpUrl.trim();
    }

    public String getProjectSignBpName() {
        return projectSignBpName;
    }

    public void setProjectSignBpName(String projectSignBpName) {
        this.projectSignBpName = projectSignBpName == null ? null : projectSignBpName.trim();
    }

    public Integer getDeliveryCount() {
        return deliveryCount;
    }

    public void setDeliveryCount(Integer deliveryCount) {
        this.deliveryCount = deliveryCount;
    }
}