package indi.mat.work.db.filter;

import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.proxy.jdbc.*;
import indi.mat.work.db.util.ThreadLocalUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;


public class CustomDbFilter extends StatFilter {
    private static final Logger logger = LoggerFactory.getLogger(CustomDbFilter.class);

    public CustomDbFilter(){}

    private void log(StatementProxy statement){
        StringBuilder param = new StringBuilder("[");
        Map<Integer, JdbcParameter> parameterMap = statement.getParameters();
        for (Integer i: parameterMap.keySet()) {
            param.append(parameterMap.get(i).getValue()+",");
        }
        param.append("]");

        String url = statement.getConnectionProxy().getDirectDataSource().getUrl();
        String instanceName = getInstance(url);
        String userName = statement.getConnectionProxy().getProperties().get("user")+"";

        String sql = statement.getLastExecuteSql().replace("\n","");
        Throwable e = statement.getSqlStat().getExecuteErrorLast();
        long cost = statement.getLastExecuteTimeNano()/1000000L;
        String methodName = ThreadLocalUtils.getMethodName();
        if(e != null){
            logger.error("methodName [{}] instanceName [{}] userName [{}] SqlText [{}] params {}  cost [{}]  exception [{}]",methodName, instanceName,userName, sql, param, cost+"ms",e.getMessage());
        }else{
            logger.info("methodName [{}] instanceName [{}] userName [{}] SqlText [{}] params {}  cost [{}]",methodName, instanceName, userName, sql, param,cost+"ms");
        }
    }

    private String getInstance(String url){
        String instanceName = url.substring(url.indexOf("//")+2);
        if(instanceName.contains(";")){
            instanceName = instanceName.substring(0,instanceName.indexOf(";"));
        }
        if(instanceName.contains("?")){
            instanceName = instanceName.substring(0,instanceName.indexOf("?"));
        }
        return instanceName;
    }

    @Override
    public void statementPrepareCallAfter(CallableStatementProxy statement) {
        super.statementPrepareCallAfter(statement);
    }

    @Override
    public void statement_close(FilterChain chain, StatementProxy statement) throws SQLException {
        if(statement.getLastExecuteSql().equals("SELECT 1")){
            return;
        }
        log(statement);
        super.statement_close(chain, statement);
    }


    @Override
    public ConnectionProxy connection_connect(FilterChain chain, Properties info) throws SQLException {
        try{
            return super.connection_connect(chain, info);
        }catch (Exception e){
            String instanceName =  getInstance(chain.getDataSource().getUrl());
            String userName = info.get("user")+"";
            logger.error("connect error, instanceName: [{}] username: [{}] ", instanceName,userName);
            logger.error("error exception info, message: [{}] stackTrace: [{}]", e.getMessage(), ExceptionUtils.getStackTrace(e));
            throw  e;
        }
    }

    @Override
    protected void statementExecuteUpdateBefore(StatementProxy statement, String sql) {
        log(statement);
        super.statementExecuteUpdateBefore(statement, sql);
    }

    @Override
    protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount) {
        log(statement);
        super.statementExecuteUpdateAfter(statement, sql, updateCount);
    }

    @Override
    protected void statementExecuteQueryBefore(StatementProxy statement, String sql) {
        log(statement);
        super.statementExecuteQueryBefore(statement, sql);
    }

    @Override
    protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
        log(statement);
        super.statementExecuteQueryAfter(statement, sql, resultSet);
    }


    @Override
    protected void statementExecuteBatchBefore(StatementProxy statement) {
        log(statement);
        super.statementExecuteBatchBefore(statement);
    }

    @Override
    protected void statementExecuteBatchAfter(StatementProxy statement, int[] result) {
        log(statement);
        super.statementExecuteBatchAfter(statement, result);

    }

    @Override
    protected void statement_executeErrorAfter(StatementProxy statement, String sql, Throwable error) {
        log(statement);
        super.statement_executeErrorAfter(statement, sql, error);
    }


}
