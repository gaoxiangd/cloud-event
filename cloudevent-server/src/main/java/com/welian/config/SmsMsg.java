package com.welian.config;

/**
 * created by GaoXiang on 2017/11/15
 */
public class SmsMsg {

    public static final String[] SIGNUP_CHECK_PASS = new String[]{",你的活动『", "』报名已通过审核,点击右侧链接查看电子票:"," 电子票为活动入场凭证，请妥善保存"};

    public static final String[] SIGNUP_CHECK_DELETE = new String[]{"很遗憾，你报名的『", "』活动被取消,查看活动: "};

    public static final String[] SIGNUP_CHECK_FAILED = new String[]{" 很遗憾，你报名的活动『", "』未通过审核, 原因 ", " 查看活动: "};

    public static final String[] SIGNUP_PASS = new String[]{",你已成功报名『", "』活动,点击右侧链接查看电子票:"," 电子票为活动入场凭证，请妥善保存"};

    public static final String[] SIGNUP_CHECKING = new String[]{",你报名的『", "』活动报名信息已提交，主办方正在审核中，请等待后续消息,点击右侧链接查看电子票: "};

    public static final String[] FIRST_SIGNUP = new String[]{",你发布的活动『", "』有用户报名了"};

    public static final String[] FIRST_SIGNUP_CHECK = new String[]{",你发布的活动『", "』有用户报名了，快去审核吧"};

    public static final String[] EVENT_RECOMMEND = new String[]{"恭喜你,你创建的活动『", "』已经被微链推荐，查看活动: "};

    public static final String[] EVENT_HOT = new String[]{"恭喜你,你创建的活动『", "』已经被选为热门，查看活动: "};


}
