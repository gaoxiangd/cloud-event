package com.welian.mybatis.handler;

import com.welian.enums.cloudevent.EnumStatus;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.sean.framework.mybatis.EnumValueHandler;

/**
 * demo.
 * Description: EnumStatus 转换器
 * Created by Sean.xie on 2017/3/15.
 */
@MappedJdbcTypes(JdbcType.TINYINT)
@MappedTypes(EnumStatus.class)
public class EnumStatusHandler extends EnumValueHandler<Integer, EnumStatus> {

    public EnumStatusHandler() {
        this(EnumStatus.class);
    }

    public EnumStatusHandler(Class<EnumStatus> type) {
        super(type, Integer.class);
    }
}