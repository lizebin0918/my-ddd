package com.lzb.infr.config.mybatis.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.lzb.component.utils.EnumUtils;
import com.lzb.component.utils.enums.EnumValue;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class EnumValueTypeHandler<E extends Enum<EnumValue<?>>, V> extends BaseTypeHandler<EnumValue<V>> {

    private final Class<E> enumClassType;

    public EnumValueTypeHandler(Class<E> enumClassType) {
        if (enumClassType == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.enumClassType = enumClassType;
        if (!EnumValue.class.isAssignableFrom(enumClassType)) {
            throw new IllegalArgumentException("Type argument must implement EnumValue");
        }
    }


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, EnumValue parameter, JdbcType jdbcType) throws SQLException {
        Object value = parameter.getValue();
        if (jdbcType == null) {
            ps.setObject(i, value);
        } else {
            // see r3589
            ps.setObject(i, value, jdbcType.TYPE_CODE);
        }
    }


    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Object value = rs.getObject(columnName);
        if (null == value || rs.wasNull()) {
            return null;
        }
        return this.valueOf(value);
    }

    private <V> E valueOf(V value) {
        Class<E> c = null;
        return EnumUtils.getByValue(c, value).orElse(null);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Object value = rs.getInt(columnIndex);
        return rs.wasNull() ? null : valueOf(value);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object value = cs.getInt(columnIndex);
        return cs.wasNull() ? null : valueOf(value);
    }

}
