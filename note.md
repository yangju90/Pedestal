# Spring boot 事件机制Event入门
https://blog.csdn.net/weixin_42073629/article/details/106303089

## OAuth2理解

https://www.cnblogs.com/xiaofengwang/p/11376881.html    
https://lotabout.me/2020/OAuth-2-workflow/

## Rbac

https://blog.csdn.net/zwk626542417/article/details/4672





```java
public class DateConverter extends BaseTypeHandler<Date> {

    //将 java 类型，转换为 jdbc 类型
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Date date, JdbcType jdbcType) throws SQLException {
        preparedStatement.setLong(i, date.getTime());
    }

    //将 jdbc 类型转 java 类型
    @Override
    public Date getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return new Date(resultSet.getLong(s));
    }

    @Override
    public Date getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return new Date(resultSet.getLong(i));
    }

    @Override
    public Date getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return new Date(callableStatement.getLong(i));
    }


}
```


