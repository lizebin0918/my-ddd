package com.lzb.infr.config;

import java.lang.reflect.ParameterizedType;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.google.common.collect.Lists;
import com.lzb.component.utils.json.JsonUtils;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;

public abstract class JsonbListTypeHandler<T> extends BaseTypeHandler<List<T>> {

    @NonNull
    @SuppressWarnings("unchecked")
    protected Class<T> getType(){
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<T> parameter, JdbcType jdbcType) throws SQLException {
        if (ps != null) {
            final PGobject pgObject = new PGobject();
            pgObject.setType("jsonb");
            pgObject.setValue(JsonUtils.toJSONString(parameter));
            ps.setObject(i, pgObject);
        }
    }

    @Override
    public List<T> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        final PGobject pgObj = rs.getObject(columnName, PGobject.class);
        if (StringUtils.isEmpty(pgObj.getValue())) {
            return Lists.newArrayList();
        }
        return JsonUtils.json2Array(pgObj.toString(), getType());
    }

    @Override
    public List<T> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        final PGobject pgObj = rs.getObject(columnIndex, PGobject.class);
        if (StringUtils.isEmpty(pgObj.getValue())) {
            return Lists.newArrayList();
        }
        return JsonUtils.json2Array(pgObj.toString(), getType());
    }

    @Override
    public List<T> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        final PGobject pgObj = cs.getObject(columnIndex, PGobject.class);
        if (StringUtils.isEmpty(pgObj.getValue())) {
            return Lists.newArrayList();
        }
        return JsonUtils.json2Array(pgObj.toString(), getType());
    }

}
