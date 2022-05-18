package indi.mat.work.mq.filter;

import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.proxy.jdbc.*;
import indi.mat.work.mq.core.MqMessageSender;
import indi.mat.work.mq.core.MqSqlParameterBean;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class MqDruidFilter extends StatFilter {

    protected static final Logger logger = LoggerFactory.getLogger(MqDruidFilter.class);


    private MqMessageSender mqMessageSender;


    public MqDruidFilter(MqMessageSender mqMessageSender) {
        this.mqMessageSender = mqMessageSender;
    }

    @Override
    public void statementPrepareCallAfter(CallableStatementProxy statement) {
        super.statementPrepareCallAfter(statement);
    }

    @Override
    public void statement_close(FilterChain chain, StatementProxy statement) throws SQLException {
        if( statement.getLastExecuteSql().equals("SELECT 1")){
            return;
        }
        log(statement);
        super.statement_close(chain, statement);
    }
    private void log(StatementProxy statement){
        StringBuilder param = new StringBuilder("[");
        Map<Integer, JdbcParameter> parameterMap =  statement.getParameters();
        for (Integer i: parameterMap.keySet()) {
            param.append(parameterMap.get(i).getValue()+",");
        }
        param.append("]");
        String sql = statement.getLastExecuteSql().replace("\n","");
        Throwable e = statement.getSqlStat().getExecuteErrorLast();
        long cost = statement.getLastExecuteTimeNano()/1000000L;
        if(e != null){
            logger.error("SqlText [{}] params {}  cost [{}]",sql,param,cost+"ms",e);
        }else{
            logger.info("SqlText [{}] params {}  cost [{}]",sql,param,cost+"ms");
        }
    }

    @Override
    public ConnectionProxy connection_connect(FilterChain chain, Properties info) throws SQLException {
        try{
            return super.connection_connect(chain, info);
        }catch (Exception e){
            String instanceName =  getInstance(chain.getDataSource().getUrl());
            String userName = info.get("user")+"";
            mqMessageSender.sendStartMsg(instanceName,userName,"connection_connect",null);
            mqMessageSender.sendEndMsg(false,e.getMessage(), ExceptionUtils.getStackTrace(e));
            logger.error("get connect error",e);
            throw  e;
        }
    }

    @Override
    public void connection_connectAfter(ConnectionProxy connection) {
        super.connection_connectAfter(connection);
    }

    @Override
    protected void statementExecuteUpdateBefore(StatementProxy statement, String sql) {
        sendBefore(statement);
        super.statementExecuteUpdateBefore(statement, sql);
    }

    @Override
    protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount) {
        sendAfter(statement);
        super.statementExecuteUpdateAfter(statement, sql, updateCount);
    }

    @Override
    protected void statementExecuteQueryBefore(StatementProxy statement, String sql) {
        sendBefore(statement);
        super.statementExecuteQueryBefore(statement, sql);
    }

    @Override
    protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
        sendAfter(statement);
        super.statementExecuteQueryAfter(statement, sql, resultSet);
    }


    @Override
    protected void statementExecuteBatchBefore(StatementProxy statement) {
        sendBefore(statement);
        super.statementExecuteBatchBefore(statement);
    }

    @Override
    protected void statementExecuteBatchAfter(StatementProxy statement, int[] result) {
        sendAfter(statement);
        super.statementExecuteBatchAfter(statement, result);

    }

    @Override
    protected void statement_executeErrorAfter(StatementProxy statement, String sql, Throwable error) {
        sendAfter(statement,error);
        super.statement_executeErrorAfter(statement, sql, error);
    }

    private void sendBefore(StatementProxy statement){
        String sql = statement.getLastExecuteSql();
        Map<Integer, JdbcParameter> parameterMap =  statement.getParameters();
        List<MqSqlParameterBean> parameterBeans = new ArrayList<>();
        for (Integer i: parameterMap.keySet()) {
            JdbcParameter jdbcParameter = parameterMap.get(i);
            parameterBeans.add(new MqSqlParameterBean("VARCHAR",i+"",jdbcParameter.getValue()+""));
        }

        String url = statement.getConnectionProxy().getDirectDataSource().getUrl();
        String instanceName = getInstance(url);
        String userName = statement.getConnectionProxy().getProperties().get("user")+"";
        mqMessageSender.sendStartMsg(instanceName,userName,sql,parameterBeans);
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


    private void sendAfter(StatementProxy statement,Throwable e){
        mqMessageSender.sendEndMsg(false,e.getMessage(), ExceptionUtils.getStackTrace(e));
    }

    private void sendAfter(StatementProxy statement){
        mqMessageSender.sendEndMsg(true,null,null);
    }


    @Override
    public boolean connection_isValid(FilterChain chain, ConnectionProxy connection, int timeout) throws SQLException {
        return super.connection_isValid(chain, connection, timeout);
    }
}
