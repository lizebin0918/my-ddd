package com.lzb.infr.config.mybatis.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lzb.component.utils.EnumUtils;
import com.lzb.component.utils.enums.EnumValue;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class EnumValueTypeHandler<V, E extends Enum<?> & EnumValue<V>> extends BaseTypeHandler<EnumValue<V>> {

    private Class<E> type;

    public EnumValueTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
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

    private E valueOf(V value) {
        return null;
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Object code = rs.getInt(columnIndex);
        return rs.wasNull() ? null : valueOf(code);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object code = cs.getInt(columnIndex);
        return cs.wasNull() ? null : valueOf(code);
    }

}
