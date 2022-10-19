package indi.mat.work.project.lock;

import indi.mat.work.project.exception.EmployeeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class DistributionLock {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public Connection lock() {
        Connection connection = null;
        try{
            connection = jdbcTemplate.getDataSource().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement pstmt2 = connection.prepareStatement("SELECT * FROM indi_lock WHERE lock_name='id1' FOR UPDATE");
            pstmt2.executeQuery();
            pstmt2.close();
            return connection;
        }catch (Exception e){
            throw new EmployeeException("db locked fail",e);
        }

    }


    public void unlock(Connection connection){
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new EmployeeException("db unlocked commit failed",e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new EmployeeException("db unlocked connection failed",e);
            }
        }
    }
}
