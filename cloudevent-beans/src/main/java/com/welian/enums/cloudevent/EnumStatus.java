package com.welian.enums.cloudevent;

import org.sean.framework.enums.EnumValue;

/**
 * Description:
 * Created by Sean.xie on 2017/3/14.
 */
public enum EnumStatus implements EnumValue<Integer> {
    UNDEFINE(0, "未定义"), DELETE(1, "删除");

    /**
     * 值
     */
    private Integer value;
    /**
     * 说明
     */
    private String desc;

    private EnumStatus(int value, String desc) {
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
