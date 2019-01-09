package com.welian.pojo;

public class OrgInfo {
    private Integer id;

    private String name;

    private String logo;

    private Long createTime;

    private Long modifyTime;

    private Integer uidAdd;

    private Integer eventCount;

    private Integer projectReceiveCount;

    private Byte state;

    private Long userModifyTime;

    private String website;

    private Integer cityId;

    private String cityName;

    private String address;

    private String intro;

    private Integer newId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
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

    public Integer getUidAdd() {
        return uidAdd;
    }

    public void setUidAdd(Integer uidAdd) {
        this.uidAdd = uidAdd;
    }

    public Integer getEventCount() {
        return eventCount;
    }

    public void setEventCount(Integer eventCount) {
        this.eventCount = eventCount;
    }

    public Integer getProjectReceiveCount() {
        return projectReceiveCount;
    }

    public void setProjectReceiveCount(Integer projectReceiveCount) {
        this.projectReceiveCount = projectReceiveCount;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Long getUserModifyTime() {
        return userModifyTime;
    }

    public void setUserModifyTime(Long userModifyTime) {
        this.userModifyTime = userModifyTime;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    public Integer getNewId() {
        return newId;
    }

    public void setNewId(Integer newId) {
        this.newId = newId;
    }
}