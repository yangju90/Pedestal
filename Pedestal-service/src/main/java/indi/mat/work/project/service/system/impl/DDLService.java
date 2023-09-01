package indi.mat.work.project.service.system.impl;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.toolkit.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
  *
  * PostgreSQL 查询数据库表结构，并生成DDL语句，建表
  */
@Service
public class DDLServiceImpl implements DDLService {

    @Autowired
    DynamicRoutingDataSource dynamicRoutingDataSource;

    @Autowired
    ZjkLogOperationService logOperationService;

    private final static Logger logger = LoggerFactory.getLogger(DDLServiceImpl.class);

    @Override
    public boolean createTable(String tableName, String newTableName, String ds) {
        DataSource dataSource = dynamicRoutingDataSource.getDataSource(ds);
        String schema = TableNameEnum.contextToSchema(ds);
        Statement st = null;
        StringBuilder ddl = null;
        boolean res = false;

        try {
            Connection conn = dataSource.getConnection();
            if (conn != null) {
                st = conn.createStatement();
                ddl = new StringBuilder("CREATE TABLE");
                ddl.append(" " + newTableName + "( ");
                ddl.append(getColumnsDDL(st, tableName, schema));

                String pkString = getPKfield(st, tableName, schema);
                if(StringUtils.isNotBlank(pkString)) {
                    ddl.append("PRIMARY KEY (").append(pkString).append(")");
                    ddl.append(",");
                }

                ddl = new StringBuilder(ddl.substring(0, ddl.length() - 1));
                ddl.append("); ");

                st.execute(ddl.toString());
                res = true;

                // index
                List<String> indexs = getTableIndexs(st, tableName, newTableName, schema);
                List<String> comments = getTableComment(st, tableName, newTableName, schema);
                for(String index :  indexs) ddl.append(index);
                for(String comment : comments) ddl.append(comment);
                for(String index :  indexs) st.execute(index);
                for(String comment : comments) st.execute(comment);
            }
            logOperationService.commonSetLog(buildLog("自动创建表成功, schema = " + schema, newTableName  , ddl.toString(), 0), 0);
        } catch (SQLException e) {
            logOperationService.commonSetLog(buildLog("自动创建表报错, schema = " + schema, newTableName  , ddl.toString(), 1), 0);
            logger.error("create table error, tablename " + newTableName, e);
            logger.error("DDL " + newTableName + " {" + ddl.toString() + "}");
        } finally {
            try {
                if (st != null) st.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return res;
    }


    private String getPKfield(Statement st, String tableName, String schema) throws SQLException {
        String queryPkString = "SELECT a.attname AS COLUMN_NAME FROM pg_constraint c JOIN pg_attribute a ON a.attnum = ANY(c.conkey) AND a.attrelid = c.conrelid JOIN pg_class r ON r.oid = c.conrelid JOIN pg_namespace n ON n.oid = r.relnamespace "
                + "WHERE c.contype = 'p' AND r.relname = '" + tableName + "' AND n.nspname = '" + schema +"'" ;
        ResultSet pkRs = st.executeQuery(queryPkString);
        StringJoiner pkString = new StringJoiner(",");

        while (pkRs.next()) {
            pkString.add(pkRs.getString("COLUMN_NAME"));
        }
        return pkString.toString();
    }

    private String getColumnsDDL(Statement st, String tableName, String schema) throws SQLException {
        String queryColumns = "SELECT * FROM information_schema.columns WHERE table_name = '" + tableName + "' AND table_schema = '" + schema + "'";
        ResultSet rs = st.executeQuery(queryColumns);
        StringBuilder ddl = new StringBuilder();
        while (rs.next()) {
            ddl.append(rs.getString("COLUMN_NAME"));
            ddl.append(" ").append(rs.getString("DATA_TYPE"));
            if (rs.getString("CHARACTER_MAXIMUM_LENGTH") != null) {
                ddl.append("(" + rs.getString("CHARACTER_MAXIMUM_LENGTH") + ")");
            }

            String nullable = rs.getString("IS_NULLABLE");
            if (nullable.equalsIgnoreCase("NO")) {
                ddl.append(" ").append("NOT NULL ");
            }

            if(rs.getString("COLUMN_DEFAULT") != null){
                ddl.append(" ").append("DEFAULT").append(" '" + rs.getString("COLUMN_DEFAULT") + "' ");
            }

            ddl.append(", ");
        }

        return ddl.toString();
    }

    private List<String> getTableIndexs(Statement st, String tableName, String newTableName, String schema) throws SQLException {
        String indexsQuerySQL = "SELECT indexname, indexdef FROM pg_indexes WHERE tablename = '" + tableName + "' AND schemaname = '" + schema +"'" ;

        List<String> ret = new ArrayList<>();
        if(st != null){
            ResultSet rs = st.executeQuery(indexsQuerySQL);
            int indexSeq = 0;
            while (rs.next()) {
                String indexName = rs.getString("indexname");
                if(indexName.contains("pkey")) continue;

                String indexDDL = rs.getString("indexdef");
                String newIndexName = newTableName + "_" + indexSeq;
                String nindexDDL = indexDDL.replaceAll(indexName, newIndexName)
                        .replaceAll(schema+ "." + tableName,  schema + "." +newTableName);

                ret.add(nindexDDL +  "; ");
                indexSeq++;
            }
        }

        return ret;
    }

    private List<String> getTableComment(Statement st, String tableName, String newTableName, String schema) throws SQLException {
        String commentQuerySQL = "SELECT DISTINCT cols.column_name, pg_catalog.col_description(c.oid, cols.ordinal_position::int) as comment FROM pg_catalog.pg_class c " +
                "JOIN information_schema.columns cols ON cols.table_name = c.relname WHERE c.relname = '" + tableName + "' AND table_schema = '" + schema + "'";

        List<String> ret = new ArrayList<>();
        if(st != null){
            ResultSet rs = st.executeQuery(commentQuerySQL);
            while (rs.next()) {
                StringBuilder temp = new StringBuilder();
                temp.append("COMMENT ON COLUMN");
                temp.append(" ").append(newTableName).append(".").append(rs.getString("column_name"));
                temp.append(" ").append("IS");
                temp.append(" ").append("'"+ rs.getString("comment") + "'");
                temp.append("; ");
                ret.add(temp.toString());
            }
        }

        return ret;
    }

    private ZjkLogOperationEntity buildLog(String msg, String tableName, String resultMessage, int i){
        ZjkLogOperationEntity entity = new ZjkLogOperationEntity();
        entity.setZjkNo(msg);
        entity.setZjkTableName(tableName);
        entity.setResultMessage(resultMessage);
        entity.setStatus(i);
        return entity;
    }

}
