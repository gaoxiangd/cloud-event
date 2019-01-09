package com.welian.enums.cloudevent;

import org.sean.framework.enums.EnumValue;

/**
 * Description: 课程上架状态
 * Created by jeizas on 2018/3/9.
 */
public enum EnumRecommendStatus implements EnumValue<Integer> {

    INIT(0, "默认"), RECOMMENDED(1, "推荐中");

    /**
     * 值
     */
    private Integer value;
    /**
     * 说明
     */
    private String desc;

    private EnumRecommendStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 获取值
     *
     * @return
     */
    public Integer getValue() {
        return value;
    }

    /**
     * 描述信息
     *
     * @return
     */
    public String getDesc() {
        return desc;
    }
}
