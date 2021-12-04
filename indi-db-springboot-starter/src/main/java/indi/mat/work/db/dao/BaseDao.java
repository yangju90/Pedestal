package indi.mat.work.db.dao;

import indi.mat.work.db.core.JdbcTemplateFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public abstract class BaseDao {
    public NamedParameterJdbcTemplate jdbc(String dbName){
        return  JdbcTemplateFactory.getJdbc(dbName);
    }
}
