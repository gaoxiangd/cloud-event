package com.welian.config;

import com.welian.enums.cloudevent.SqlEnum;
import com.welian.pojo.Event;
import com.welian.utils.DateUtil;

/**
 * created by GaoXiang on 2018/7/4
 */
public class MiniPush {

    public static String COMMON_SIGNUP_SUCCESS_NEED_CHECK_NOT = "你已成功报名活动，点击查看报名详情。";

    public static String COMMON_SIGNUP_SUCCESS_NEED_CHECK = "你已成功提交活动报名信息，主办方正在审核中，请等待后续消息。";

    public static String[] ROAD_SIGNUP_SUCCESS_NO_BP = {"你已报名活动，请在电脑上打开key.welian.com，输入", "，完成商业计划书上传"};

    public static String RECORD_CHECK_PASS="你的活动报名已经通过审核，点击查看报名详情。";

    public static String RECORD_CHECK_REFUSE="抱歉，你的活动报名审核未通过，点击重新报名";

    public static String TOMORROW_BEGIN_REMIND="你报名的活动将在明天开始，点击查看详情";

    /**
     * 拼接微信推送模板内容
     * @param format 格式
     * @param event 活动
     * @param content 最后一句内容
     * @return
     */
    public static String spellWechatContent(String format,Event event, String content) {

        return   String.format(format, event.getTitle(), DateUtil.getRangeFormatDate(event
                .getStartTime(), event.getEndTime()), SqlEnum.EventLineType.TYPE_ONLINE.getByteValue().equals(event
                .getLineType()) ? "线上活动" : event.getCityName()+event.getAddress(), content);

    }
}
