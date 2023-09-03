package com.lzb.infr.config;

import java.lang.reflect.ParameterizedType;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import com.lzb.component.utils.json.JsonUtils;
import lombok.NonNull;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;

public abstract class JsonbTypeHandler<T> extends BaseTypeHandler<T> {

    @NonNull
    @SuppressWarnings("unchecked")
    protected Class<T> getType(){
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        if (ps != null) {
            final PGobject pgObject = new PGobject();
            pgObject.setType("jsonb");
            pgObject.setValue(JsonUtils.toJSONString(parameter));
            ps.setObject(i, pgObject);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        final PGobject pgObj = rs.getObject(columnName, PGobject.class);
        if (Objects.isNull(pgObj) || Objects.isNull(pgObj.getValue())) {
            return null;
        }
        return JsonUtils.json2JavaBean(pgObj.toString(), getType());
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        final PGobject pgObj = rs.getObject(columnIndex, PGobject.class);
        if (Objects.isNull(pgObj) || Objects.isNull(pgObj.getValue())) {
            return null;
        }
        return JsonUtils.json2JavaBean(pgObj.toString(), getType());
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        final PGobject pgObj = cs.getObject(columnIndex, PGobject.class);
        if (Objects.isNull(pgObj) || Objects.isNull(pgObj.getValue())) {
            return null;
        }
        return JsonUtils.json2JavaBean(pgObj.toString(), getType());
    }

}
