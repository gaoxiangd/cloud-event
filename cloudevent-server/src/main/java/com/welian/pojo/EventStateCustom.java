package com.welian.pojo;

public class EventStateCustom {
    private Integer id;

    private Integer eventId;

    private Byte costType;

    private Byte verifyType;

    private Byte openSignUpList;

    private Integer signCount;

    private Integer joinedCount;

    private String authPassword;

    private Byte groupChatState;

    private Byte financeStatus;

    private Long modifyTime;

    private Long createTime;

    private String groupChat;

    private Byte customNumberState;

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

    public Integer getSignCount() {
        return signCount;
    }

    public void setSignCount(Integer signCount) {
        this.signCount = signCount;
    }

    public Integer getJoinedCount() {
        return joinedCount;
    }

    public void setJoinedCount(Integer joinedCount) {
        this.joinedCount = joinedCount;
    }

    public String getAuthPassword() {
        return authPassword;
    }

    public void setAuthPassword(String authPassword) {
        this.authPassword = authPassword == null ? null : authPassword.trim();
    }

    public Byte getGroupChatState() {
        return groupChatState;
    }

    public void setGroupChatState(Byte groupChatState) {
        this.groupChatState = groupChatState;
    }

    public Byte getFinanceStatus() {
        return financeStatus;
    }

    public void setFinanceStatus(Byte financeStatus) {
        this.financeStatus = financeStatus;
    }

    public Long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getGroupChat() {
        return groupChat;
    }

    public void setGroupChat(String groupChat) {
        this.groupChat = groupChat == null ? null : groupChat.trim();
    }

    public Byte getCustomNumberState() {
        return customNumberState;
    }

    public void setCustomNumberState(Byte customNumberState) {
        this.customNumberState = customNumberState;
    }
}