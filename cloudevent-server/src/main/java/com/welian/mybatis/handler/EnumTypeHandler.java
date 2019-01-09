package com.welian.mybatis.handler;

import com.welian.enums.cloudevent.EnumType;
import org.sean.framework.mybatis.EnumValueHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;


/**
 * Description: EnumStatus 转换器
 * Created by Sean.xie on 2017/3/15.
 */
@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes(EnumType.class)
public class EnumTypeHandler extends EnumValueHandler<Integer, EnumType> {

    public EnumTypeHandler() {
        this(EnumType.class);
    }

    public EnumTypeHandler(Class<EnumType> type) {
        super(type, Integer.class);
    }
}