package com.welian.enums.cloudevent;

/**
 * Created by welian_gyc on 2017/4/28.
 */
public class SqlEnum {

    /**
     * 是否是导入
     */
    public enum IsImport{

        TYPE_IS_NOT_IMPORT(0),
        TYPE_IS_IMPORT(1),
        TYPE_ALL(-1);


        Integer type;

        IsImport(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue(){ return type.byteValue(); }
    }

    public enum RequestSource{

        SOURCE_EVENT(1),

        SOURCE_MINIPROGRAM(2);

        Integer type;

        RequestSource(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue(){ return type.byteValue(); }
    }

    public enum SignInType{
        TYPE_SIGN_IN_NO(0),
        TYPE_SIGN_IN_YES(1);

        Integer type;

        SignInType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue(){ return type.byteValue(); }
    }
    /**
     * 报名来源 1.pc 2.h5 3.app 4.微信 5.导入
     */
    public enum EventRecordSource{
        TYPE_SOURCE_PC(1),
        TYPE_SOURCE_H5(2),
        TYPE_SOURCE_APP(3),
        TYPE_SOURCE_WEIXIN(4),
        TYPE_SOURCE_IMPORT(5);

        Integer type;

        EventRecordSource(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue(){ return type.byteValue(); }
    }

    /**
     * 记录是否被锁定，1.锁定 0.正常
     */
    public enum TicketLockType{

        TYPE_LOCK(1),
        TYPE_NORMAL(0);

        Integer type;

        TicketLockType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }
    }
    /**
     * 创业活动免费收费
     */
    public enum CostType {
        TYPE_CHARGE(1), //收费
        TYPE_CHARGE_NOT(0); //免费

        Integer type;

        CostType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }
    }

    /**
     * 订单类型
     */
    public enum OrderType {
        TYPE_998(1),
        TYPE_EVENT(2);

        Integer type;

        OrderType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }
    }

    /**
     * 推广链接的类型
     * 推广链接的种类，0表示默认的推广链接，1表示导项目时创建的链接，2表示平台方创建的链接
     */
    public enum LINKTYPE {
        TYPE_DEFAULT(0),
        TYPE_IMPORT(1),
        TYPE_USER(2);

        Integer type;

        LINKTYPE(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }
    }

    /**
     * 创建的活动的类型
     * 0表示用户创建的活动，1表示导项目时创建的默认活动
     */
    public enum ActivityCreateType {
        TYPE_NORMAL(0),
        TYPE_DEFAULT(1);

        Integer type;

        ActivityCreateType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }
    }

    /**
     * 报名的项目来自哪
     * 0表示来自自己机构报名的，1表示导项目过来的
     */
    public enum ProjectReceiveFromType {
        TYPE_OWN(0),
        TYPE_OTHER(1);

        Integer type;

        ProjectReceiveFromType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }
    }

    /**
     * 项目的反馈状态，0表示待反馈，1表示已回复，2表示已约谈，3表示已过期
     */
    public enum ProjectFeedbackState {
        TYPE_DEFAULT(0),
        TYPE_ANSWER(1),
        TYPE_INTERVIEW(2),
        TYPE_EXPIRED(3);

        Integer type;

        ProjectFeedbackState(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }
    }

    /**
     * 项目统计表里保存的信息类型
     */
    public enum ProjectSaveType {
        TYPE_STAGE(1),
        TYPE_CITY(2),
        TYPE_INDUSTRY(3);

        Integer type;

        ProjectSaveType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }
    }

    /**
     * 活动的状态类型
     */
    public enum EventStateType {
        TYPE_EVENT_PUBLISH_NOT(1),//发布之后又取消发布和未发布
        TYPE_EVENT_PUBLISHED(2),//已发布状态
        TYPE_EVENT_FINISHED(3),//已结束状态
        TYPE_EVENT_DELETED(4);//已删除状态,数据库表中不会存储此值
        Integer type;

        EventStateType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }
    }

    /**
     * 项目统计表里保存的信息类型
     */
    public enum EventType {
        TYPE_EVENT_COMMON(1),//普通活动
        TYPE_EVENT_ROADSHOW(2),//路演大赛的活动
        TYPE_EVENT_FINANCE_DELIVERY(3);//融资投递的活动

        Integer type;

        EventType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }
    }

    /**
     * 项目统计表里保存的信息类型
     */
    public enum SignUpType {
        TYPE_EVENT_CUSTOM(0),//观众报名
        TYPE_EVENT_PROJECT(1);//项目报名

        Integer type;

        SignUpType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }
    }


    /**
     * 用户的报名状态
     */
    public enum EventRecordType {
        TYPE_EVENT_RECORD_SUCCESS(0),//报名成功
        TYPE_EVENT_USER_DELETED(1),//用户删除报名记录，取消报名
        TYPE_EVENT_PLATFORM_DELETED(2),//主办方删除报名记录，取消报名
        TYPE_EVENT_WAIT_VERIFY(3),//等待审核
        TYPE_EVENT_VERIFY_FAILED(4);//审核失败
        Integer type;

        EventRecordType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }
    }

    /**
     * 用户是否报名状态
     */
    public enum UserEventSignUpType {
        NO_SIGN_UP(0),//没有报名
        HAS_SIGN_UP(1);//已报名
        Integer type;

        UserEventSignUpType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }
    }

    /**
     * 报名的审核状态
     */
    public enum RecordVerifyType {
        TYPE_VERIFY_NO(0),//不审核
        TYPE_VERIFY_YES(1);//审核

        Integer type;

        RecordVerifyType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }
    }

    /**
     * 机构修改状态
     */
    public enum CanReviseType {
        CAN(0),//可以修改
        CANT(1);//不可以修改

        Integer type;

        CanReviseType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }
    }

    /**
     * 是否开启群聊，默认0表示不开启，1表示开启
     */
    public enum GroupChatOpenType {
        TYPE_OPEN(1),//开启群聊
        TYPE_NO_OPEN(0);//不开启群聊

        Integer type;

        GroupChatOpenType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }
    }

    /**
     * 活动中项目的模板类型
     */
    public enum ProjectModuleType {
        TYPE_NO(0),//无模板
        TYPE_DETAIL(1),//表示填写的项目信息是详细版的
        TYPE_SIMPLE(2);//表示填写的项目信息是简单版的

        Integer type;

        ProjectModuleType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }
    }

    /**
     *
     */
    public enum EventLineType {
        TYPE_OFFLINE(0),//线下
        TYPE_ONLINE(1);//线上

        Integer type;

        EventLineType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }
    }

    /**
     * 活动-系统消息是否已删除的状态
     */
    public enum SysMsgState {
        TYPE_NORMAL(0),//正常
        TYPE_DELETE(1);//以删除

        Integer type;

        SysMsgState(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }

    }

    /**
     * 活动-系统消息是否已经阅读的状态
     */
    public enum SysMsgRead {
        TYPE_HASNOTREAD(0),//未读
        TYPE_HASREAD(1);//已读

        Integer type;

        SysMsgRead(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }
    }

    /**
     * 系统消息状态
     */
    public enum ConfirmType {
        /*推荐成功*/
        TYPE_RECOMMEND_SUCCESS(1),
        /*推广上热门*/
        TYPE_RECOMMEND_HOT(2),
        /*认证通过*/
        TYPE_CHECK_PASS(3),
        /*审核失败*/
        TYPE_CHECK_FAIL(4),
        /**/
        TYPE_SIGNUP_FIRST(5);

        Integer type;

        ConfirmType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }
    }

    /**
     * ua来源
     * 1:云活动平台 2:报名表单 3:活动详情 4:主题官网
     */
    public enum Resource {

        TYPE_RESOURCE_PLATFORM(1),
        TYPE_RESOURCE_FORM(2),
        TYPE_RESOURCE_DETAIL(3),
        TYPE_RESOURCE_THEMEWEB(4);

        Integer type;

        Resource(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }
    }


    public enum PackageType {
        TYPE_BASE(1, "基础版", 1),//基础版id，名称，样式
        TYPE_ADVANCE(2, "高级版", 1);//高级版版id，名称，样式
        Integer id;
        String value;
        Integer style;

        PackageType(Integer id, String value, Integer style) {
            this.id = id;
            this.value = value;
            this.style = style;
        }

        public Integer getId() {
            return id;
        }

        public String getValue() {
            return value;
        }

        public Integer getStyle() {
            return style;
        }
    }

    /**
     * ua来源
     * 1:云活动平台 2:报名表单 3:活动详情 4:主题官网
     */
    public enum SmsType {

        //SMS
        TYPE_VCODE(100), //验证码短信
        TYPE_NORMAL(101),//普通营销短信
        TYPE_VCODE_TXT(102),//短信验证码
        TYPE_VCODE_VOICE(103),//语音验证码
        TYPE_BATCH(104),//批量发送
        TYPE_CUSTOM_SIGN(105),//自定义签名短信
        TYPE_CHANNEL_SHIYUAN(106),//示远通道
        TYPE_CHANNEL_2OFFICE(107),//二办通道
        TYPE_VCODE_2OFFICE(108),//二办通道发送验证码

        TYPE_UNKNOW(100000);//未知

        Integer type;

        SmsType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }
    }

    public enum SmsSource {

        //SMS
        TYPE_NOT_VERIFY(0), //不需要审核
        TYPE_WAIT_VERIFY(1),//等待审核
        TYPE_VERIFY_PASS(2),//审核通过
        TYPE_VERIFY_DELETE(3),//记录删除
        TYPE_VERIFY_REFUSE(4),//审核通过
        TYPE_VERIFY_MANAGE(5),//自定义签名短信
        TYPE_MANAGE_RECOMMEND(6),//运营后台推荐
        TYPE_RECORD_FIRST(7);//运营后台推荐


        Integer type;

        SmsSource(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }
    }

    public enum SmsFlag {

        //SMS
        TYPE_BATCH(0), //群发
        TYPE_SINGLE(1);//单发

        Integer type;

        SmsFlag(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }
    }


    /**
     * 是否开启一键融资服务，0表示不开启，默认1表示开启
     */
    public enum FinancingServiceType {
        TYPE_NO_OPEN(0),//不开启
        TYPE_OPEN(1);//开启

        Integer type;

        FinancingServiceType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }
    }


    /**
     * 表单字段报名时是否需要必填，0表示非必填，1表示必填
     */
    public enum CustomFieldRequiredType {
        TYPE_NO_MUST(0),//非必填
        TYPE_MUST(1);//必填

        Integer type;

        CustomFieldRequiredType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }
    }

    public enum RecommendType {
        /**
         * 未推荐
         */
        TYPE_NOT_RECOMMEND(0),
        /**
         * 推荐
         */
        TYPE_RECOMMEND(1);

        Integer type;

        RecommendType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

    }

    public enum HotType {
        TYPE_NOT_HOT(0, "普通推荐"),
        TYPE_HOT(1, "热门推荐"),
        TYPE_app_feed(3, "创业圈"),
        TYPE_app_financing(4, "投融页");

        private Integer type;
        private String desc;

        HotType(Integer i, String desc) {
            this.type = i;
            this.desc = desc;
        }

        public Integer getValue() {
            return type;
        }

        public String getDesc() {
            return desc;
        }

        public static String getDsc(Integer type) {
            for (HotType e : values()) {
                if (e.getValue().equals(type)) {
                    return e.getDesc();
                }
            }
            return "";
        }
    }

    public enum OpenExtensionType {
        /**
         * 推广
         */
        TYPE_OPEN(1),
        /**
         * 不推广
         */
        TYPE_NOT_OPEN(0);

        Integer type;

        OpenExtensionType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue(){ return type.byteValue(); }

    }

    /**
     * 机构审核状态 1:未审核 2:审核中 3:已通过 4:未通过
     */
    public enum OrgVerifyStatus {
        CHECK_NOT(1),
        CHECK_ING(2),
        CHECK_PASS(3),
        CHECK_REFUSE(4);

        Integer type;

        OrgVerifyStatus(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }


    }

    public enum EventVerifyType {

        TYPE_VERIFY_NO(0),//不需要审核
        TYPE_VERIFY_YES(1);//需要审核


        Integer type;

        EventVerifyType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }


    }


    /**
     * 活动签到授权状态
     * 状态，1：已授权；2：取消授权
     */
    public enum EventSignAuthStatus {
        AUTH(1),
        AUTH_OUT(2);

        Integer type;

        EventSignAuthStatus(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }


    }

    /**
     * 活动签到授权状态
     * 状态，0.未签到 1.已签到
     */
    public enum EventSignStatus {
        EVENT_SIGN_FALSE(0),
        EVENT_SIGN_TRUE(1);

        Integer type;

        EventSignStatus(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }

    }

    /**
     * 活动票状态
     * 状态，0.正常 1.异常
     */
    public enum EventTicketState {
        EVENT_TICKET_FALSE(0),
        EVENT_TICKET_TRUE(1);

        Integer type;

        EventTicketState(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }

    }


    /**
     * 短信充值
     * 状态，1.成功 2.失败 3.待支付
     */
    public enum SmsManageState {
        SUCCESS(1),
        FALSE(2),
        WAIT(3);

        Integer type;

        SmsManageState(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue() {
            return type.byteValue();
        }

    }

}
