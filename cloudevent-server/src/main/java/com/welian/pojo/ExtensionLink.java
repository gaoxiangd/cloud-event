package com.welian.pojo;

public class ExtensionLink {
    private Integer id;

    private Integer eventId;

    private String linkName;

    private String linkUrlForm;

    private String linkUrlCommon;

    private String linkUrlCustom;

    private Long createTime;

    private Long modifyTime;

    private Byte linkType;

    private String uniqueKey;

    private Byte state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName == null ? null : linkName.trim();
    }

    public String getLinkUrlForm() {
        return linkUrlForm;
    }

    public void setLinkUrlForm(String linkUrlForm) {
        this.linkUrlForm = linkUrlForm == null ? null : linkUrlForm.trim();
    }

    public String getLinkUrlCommon() {
        return linkUrlCommon;
    }

    public void setLinkUrlCommon(String linkUrlCommon) {
        this.linkUrlCommon = linkUrlCommon == null ? null : linkUrlCommon.trim();
    }

    public String getLinkUrlCustom() {
        return linkUrlCustom;
    }

    public void setLinkUrlCustom(String linkUrlCustom) {
        this.linkUrlCustom = linkUrlCustom == null ? null : linkUrlCustom.trim();
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

    public Byte getLinkType() {
        return linkType;
    }

    public void setLinkType(Byte linkType) {
        this.linkType = linkType;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey == null ? null : uniqueKey.trim();
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}