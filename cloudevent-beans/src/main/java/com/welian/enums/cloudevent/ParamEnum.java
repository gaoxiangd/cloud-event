package com.welian.enums.cloudevent;

/**
 * Created by memorytale on 2017/5/4.
 */
public class ParamEnum {

    public enum CouponType{

        TYPE_SIGN_IN_SUCCESS(1),
        TYPE_SIGN_UP_SUCCESS(2);

        Integer type;

        CouponType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue(){ return type.byteValue(); }
    }

    /**
     * 能取消报名吗 0不能取消报名 1可以取消报名
     */
    public enum SignupCancel {

        CANCEL_CAN(1),
        CANCEL_CAN_NOT(0);

        Integer type;

        SignupCancel(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue(){ return type.byteValue(); }
    }

    /**
     * 优惠券状态 0正常 1屏蔽
     */
    public enum CouponStatus {

        STATUS_SHIELD(1),
        STATUS_NORMAL(0);

        Integer type;

        CouponStatus(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue(){ return type.byteValue(); }
    }

    /**
     * 创业活动请求来源
     */
    public enum PayState {

        PAY_YES(1),
        PAY_NO(0);

        Integer type;

        PayState(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue(){ return type.byteValue(); }
    }
    /**
     * 创业活动请求来源
     */
    public enum FromSource {
        TYPE_CLOUD(1),
        TYPE_APP(2);

        Integer type;

        FromSource(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue(){ return type.byteValue(); }
    }
    /**
     * app端创业活动的时间筛选条件
     */
    public enum EventDateType {
        TYPE_TODAY(0),
        TYPE_TOMORROW(1),
        YPE_LAST_WEEK(7),
        TYPE_LAST_WEEKEND(-2);
        Integer type;

        EventDateType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue(){ return type.byteValue(); }
    }
    /**
     * 标识请求来源与运营后台还是云活动平台
     */
    public enum EventRequestSource {
        SOURCE_FROM_CLOUD(1),
        SOURCE_FROM_MANAGE(2);
        Integer type;

        EventRequestSource(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue(){ return type.byteValue(); }
    }

    /**
     * 前端要活动类型：1.创业活动 2.路演大赛
     */
    public enum EventType {
        TYPE_COMMON(1),
        TYPE_ROAD(2);
        Integer type;

        EventType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        public Byte getByteValue(){ return type.byteValue(); }
    }


    /**
     * 收藏关系
     */
    public enum EventFavorite {
        TYPE_ABNORMAL(-1),
        TYPE_FAVORITE(1),
        TYPE_FAVORITE_NOT(0);
        Integer type;

        EventFavorite(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }
    }

    /**
     *  是否有报名记录
     */
    public enum HasRrcord {
        TYPE_HAS_RECORD(1),
        TYPE_HAS_NOT_RECORD(0);

        Integer type;

        HasRrcord(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }
    }
    /**
     * 项目来自哪
     * 1表示PC端创建或修改项目，2表示H5创建或修改项目
     */
    public enum ChannelChooseProject {
        TYPE_PC(1),
        TYPE_H5(2);

        Integer type;

        ChannelChooseProject(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }
    }


    /**
     */
    public enum ChooseLinkListType {
        TYPE_CHOOSE_COMMON(1),//选择通用模板
        TYPE_CHOOSE_FORM(2),//选择活动表单
        TYPE_CHOOSE_CUSTOM(3);//选择专题官网

        Integer type;

        ChooseLinkListType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }
    }

    /**
     * 每天收到的项目的比较
     */
    public enum ProjectCountReceiveIncreaseState {
        TYPE_EQUAL(1),//持平
        TYPE_INCREASE(2),//增长
        TYPE_DROP(3);//下降

        Integer type;

        ProjectCountReceiveIncreaseState(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }
    }

    /**
     * 项目来自哪
     * 1表示可以报名，2表示该活动取消发布，3表示活动报名已截止,4表示报名已满,5表示活动已结束
     */
    public enum EventCanSignStateType {
        TYPE_CAN_SIGN_UP(1),
        TYPE_CANCEL(2),
        TYPE_DEADLINE(3),
        TYPE_TICKET_NO(4),
        TYPE_FINISHED(5),
        TYPE_ERROR(6);

        Integer type;

        EventCanSignStateType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }
    }


    /**
     * 取活动列表的接口
     */
    public enum ProjectRecordListGetType {
        TYPE_ALL(1),//取全部活动
        TYPE_AUDITED(2),//只取已通过的
        TYPE_AUDITED_AND_WAIT_VERIFY(3),//取审核通过和待审核的
        TYPE_AUDITED_AND_WAIT_VERIFY_FAILED(4);//取审核通过和待审核的和审核失败的
        Integer type;

        ProjectRecordListGetType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }
    }

    public enum RoleType {
        TYPE_ADMIN(1),
        TYPE_USER(2);

        Integer type;

        RoleType(Integer i) {
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
     * 运营后台搜索的机构类型
     * 1:根据机构名称查询，2根据超级管理员手机号查询，3根据用户名查询
     */
    public enum OrgType {
        TYPE_ORG_NAME(1),
        TYPE_PHONE(2),
        TYPE_USER_NAME(3);
        Integer type;

        OrgType(Integer i) {
            type = i;
        }

        public Integer getValue() {
            return type;
        }

        ;
    }
    public enum HotOrRcmType {
        TYPE_RECOMMENT(1),
        TYPE_HOT(2);

        Integer type;

        HotOrRcmType(Integer i) {
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
     * 区别请求来源
     * 1.运营后台 2.云活动平台
     */
    public enum RequestSource {
        TYPE_FROM_MANAGE(1),
        TYPE_FROM_CLOUD(2);

        Integer type;

        RequestSource(Integer i) {
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
     * 活动状态 1.未发布 2.已发布 3.已结束
     */
    public enum EventState {
        STATE_NOT_PUBLISH(1),
        STATE_PUBLISH(2),
        STATE_FINISHED(3);

        Integer type;

        EventState(Integer i) {
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
     * 用来显示标签 0.普通 1.new 2.hot 3.已结束 4.已截止
     */
    public enum EventSortType {
        DEGREE_NORMAL(0),
        DEGREE_NEW(1),
        DEGREE_HOT(2),
        DEGREE_FINISHED(3),
        DEGREE_DEADLINE(4);

        Integer type;

        EventSortType(Integer i) {
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
     * 0:不需要推广 1:需要推广
     */
    public enum Promotion {
        PROMOTION_NOT(0),
        PROMOTION_YES(1);

        Integer type;

        Promotion(Integer i) {
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
     * 活动签到信息状态
     */
    public enum EventSignInfoState {
        EVENT_SIGN_CANCEL_ORDER(2,"取消报名"),
        EVENT_SIGN_REFUSE(3,"你的报名被主办方拒绝了"),
        EVENT_SIGN_ACTIVE_OVER(4,"该活动已结束"),
        EVENT_SIGN_NO_ORDER(5,"未发现你报名该活动"),
        EVENT_SIGN_IS_WECHAT(6,"账号未绑定微信，给用户提醒是否微信报名的活动"),
        EVENT_SIGN_TICKET_ERROR(7,"电子票无效"),
        EVENT_SIGN_ACTIVE_ERROR (8,"活动不存在"),
        EVENT_SIGN_EXITS_WECHAT(9,"微信号已绑定"),
        EVENT_SIGN_WAIITING(10,"你的报名待审核"),
        EVENT_SIGN_NO_ORDER1(11,"没有可签到的票券");



        private Integer value;

        private String desc;

        private EventSignInfoState(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public Integer getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

    }


    public enum EventSignAuthState {
        WORKER_SIGN_AUTH_FALSAE(-1,"员工没有签到权限"),
        WORKER_SIGN_AUTH_TRUE(0,"员工有签到权限");

        private Integer value;

        private String desc;

        private EventSignAuthState(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public Integer getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

    }









    }
