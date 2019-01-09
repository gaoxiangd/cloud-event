package com.welian.enums.cloudevent;

import org.sean.framework.enums.EnumValue;

/**
 * Enum 如果此类需要对外暴露, 需要创建一个bean module
 * Created by xielei on 2017/3/12.
 */
public enum EnumType implements EnumValue<Integer> {
    NORMAL(0, "普通类型");


    /**
     * 值
     */
    private int value;
    /**
     * 说明
     */
    private String desc;

    private EnumType(int value, String desc) {
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
