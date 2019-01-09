package com.welian.enums.cloudevent;

import com.welian.beans.cloudevent.app.OrderRulesResp;

import java.util.ArrayList;

/**
 * Created by zhaopu on 2017/10/16.
 */
public enum  OrderRulesEnum {

    COMPANY(0,"推荐排序"),
    MOTHER_FUND(1,"最新活动"),
    PERSONAL(2,"最热活动");

    /**
     * 值
     */
    private int value;
    /**
     * 名字
     */
    private String name;


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private OrderRulesEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String getName(Integer value) {
        OrderRulesEnum[] orderRulesEnum = OrderRulesEnum.values();
        for (OrderRulesEnum temp : orderRulesEnum) {
            if (temp.getValue() == value) {
                return temp.getName();
            }
        }

        return null;
    }

    public static Integer getId(String name) {
        OrderRulesEnum[] orderRulesEnum = OrderRulesEnum.values();
        for (OrderRulesEnum temp : orderRulesEnum) {
            if (temp.getName() == name) {
                return temp.getValue();
            }
        }
        return null;
    }

    public static OrderRulesResp getOrderRules() {
        OrderRulesEnum[] orderRulesEnum = OrderRulesEnum.values();
        OrderRulesResp resp = new OrderRulesResp();
        resp.list = new ArrayList<>();
        for (OrderRulesEnum temp : orderRulesEnum) {
            OrderRulesResp resp1 = new OrderRulesResp();
            resp1.ruleId = temp.getValue();
            resp1.ruleName = temp.getName();
            resp.list.add(resp1);
        }
        return resp;
    }


}
