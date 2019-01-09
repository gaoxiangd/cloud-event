package com.welian.pojo;

public class EventStateProject {
    private Integer id;

    private Integer eventId;

    private Byte costType;

    private Byte verifyType;

    private Byte openSignUpList;

    private Integer joinedCount;

    private String groupChat;

    private Byte groupChatState;

    private Long createTime;

    private Long modifyTime;

    private Byte isOpenFinancing;

    private Integer investorMatchCount;

    private Byte groupSetting;

    private Byte projectInputType;

    private Byte projectNumberState;

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

    public Byte getCostType() {
        return costType;
    }

    public void setCostType(Byte costType) {
        this.costType = costType;
    }

    public Byte getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(Byte verifyType) {
        this.verifyType = verifyType;
    }

    public Byte getOpenSignUpList() {
        return openSignUpList;
    }

    public void setOpenSignUpList(Byte openSignUpList) {
        this.openSignUpList = openSignUpList;
    }

    public Integer getJoinedCount() {
        return joinedCount;
    }

    public void setJoinedCount(Integer joinedCount) {
        this.joinedCount = joinedCount;
    }

    public String getGroupChat() {
        return groupChat;
    }

    public void setGroupChat(String groupChat) {
        this.groupChat = groupChat == null ? null : groupChat.trim();
    }

    public Byte getGroupChatState() {
        return groupChatState;
    }

    public void setGroupChatState(Byte groupChatState) {
        this.groupChatState = groupChatState;
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

    public Byte getIsOpenFinancing() {
        return isOpenFinancing;
    }

    public void setIsOpenFinancing(Byte isOpenFinancing) {
        this.isOpenFinancing = isOpenFinancing;
    }

    public Integer getInvestorMatchCount() {
        return investorMatchCount;
    }

    public void setInvestorMatchCount(Integer investorMatchCount) {
        this.investorMatchCount = investorMatchCount;
    }

    public Byte getGroupSetting() {
        return groupSetting;
    }

    public void setGroupSetting(Byte groupSetting) {
        this.groupSetting = groupSetting;
    }

    public Byte getProjectInputType() {
        return projectInputType;
    }

    public void setProjectInputType(Byte projectInputType) {
        this.projectInputType = projectInputType;
    }

    public Byte getProjectNumberState() {
        return projectNumberState;
    }

    public void setProjectNumberState(Byte projectNumberState) {
        this.projectNumberState = projectNumberState;
    }
}