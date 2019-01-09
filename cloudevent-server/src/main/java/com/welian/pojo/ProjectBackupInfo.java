package com.welian.pojo;

public class ProjectBackupInfo {
    private Integer id;

    private Integer pid;

    private String name;

    private String intro;

    private String logo;

    private Integer cityId;

    private String cityName;

    private Integer industryId;

    private String industryName;

    private Integer projectCreateUid;

    private Double projectShare;

    private Integer projectAmount;

    private Byte projectAmountUnit;

    private Integer projectStage;

    private Byte state;

    private Long createTime;

    private Long modifyTime;

    private Integer projectSignBpId;

    private String projectSignBpUrl;

    private String projectSignBpName;

    private String projectVersion;

    private Integer eventRecordId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
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

    public Integer getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName == null ? null : industryName.trim();
    }

    public Integer getProjectCreateUid() {
        return projectCreateUid;
    }

    public void setProjectCreateUid(Integer projectCreateUid) {
        this.projectCreateUid = projectCreateUid;
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

    public String getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion == null ? null : projectVersion.trim();
    }

    public Integer getEventRecordId() {
        return eventRecordId;
    }

    public void setEventRecordId(Integer eventRecordId) {
        this.eventRecordId = eventRecordId;
    }
}