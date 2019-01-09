package com.welian.config;

/**
 * created by GaoXiang on 2017/11/15
 */
public class CloudSysMsg {
    //列表
    public static final String[] SIMPLE_RECOMMEND=new String[]{"创建的活动『","』已经被微链推荐"};

    public static final String[] SIMPLE_HOT=new String[]{"创建的活动『","』已经被微链选为热门"};

    public static final String[] SIMPLE_ORG_PASS=new String[]{"的机构申请认证已通过"};

    public static final String[] SIMPLE_ORG_REFUSE=new String[]{"的机构申请认证未通过,点击查看原因"};

    public static final String[] SIMPLE_FIRST_SIGNUP=new String[]{"发布的活动『","』有用户报名了"};

    public static final String[] SIMPLE_FIRST_SIGNUP_CHECK=new String[]{"发布的活动『","』有用户报名了,快去审核吧"};

    //详细prependText拼接

    public static final String[] PREPEND_RECOMMEND=new String[]{"创建的活动"};

    public static final String[] PREPEND_HOT=new String[]{"创建的活动"};

    public static final String[] PREPEND_ORG_PASS=new String[]{"发起的机构申请认证已通过"};

    public static final String[] PREPEND_ORG_REFUSE=new String[]{"抱歉,由于\"","\"的原因,","发起的机构申请认证未通过"};

    public static final String[] PREPEND_FIRST_SIGNUP=new String[]{"发布的活动"};

    //详细appendText拼接

    public static final String[] APPEND_RECOMMEND=new String[]{"已经被微链推荐\r\n --"};

    public static final String[] APPEND_HOT=new String[]{"已经被微链选为热门\r\n --"};

    public static final String[] APPEND_FIRST_SIGNUP=new String[]{"有用户报名了\r\n --"};

    public static final String[] APPEND_FIRST_SIGNUP_CHECK=new String[]{"有用户报名了,快去审核吧\r\n --"};

    public static final String[] APPEND_OTHERS=new String[]{"\r\n --"};

}
