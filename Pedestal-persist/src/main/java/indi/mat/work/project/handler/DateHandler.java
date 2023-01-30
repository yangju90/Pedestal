package indi.mat.work.project.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DateHandler extends BaseTypeHandler<Date> {

    //将 java 类型，转换为 jdbc 类型
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Date date, JdbcType jdbcType) throws SQLException {
        preparedStatement.setLong(i, date.getTime());
    }

    //将 jdbc 类型转 java 类型
    @Override
    public Date getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Object object = resultSet.getObject(s);
        if(object instanceof Date) {
            return (Date) object;
        }
        return new Date(resultSet.getLong(s));
    }

    @Override
    public Date getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Object object = resultSet.getObject(i);
        if(object instanceof Date) {
            return (Date) object;
        }
        return new Date(resultSet.getLong(i));
    }

    @Override
    public Date getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return new Date(callableStatement.getLong(i));
    }


}
