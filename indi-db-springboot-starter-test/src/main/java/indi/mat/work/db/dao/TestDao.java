package indi.mat.work.db.dao;

import indi.mat.work.db.Student;
import indi.mat.work.db.util.JsonUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Repository
public class TestDao extends BaseDao {

    public void test() {
        int map = jdbc("mysql").queryForObject("SELECT 100", new HashMap<>(0), Integer.class);
        System.out.println(JsonUtil.toJsonString(map));
    }

    public void test1(){
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = jdbc("mysql");

        String sql = "SELECT * FROM student WHERE id < :@COUNT";

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("@COUNT", 100);

        List<Student> list =  namedParameterJdbcTemplate.query(sql, sqlParameterSource, new BeanPropertyRowMapper<>(Student.class));

        System.out.println(JsonUtil.toJsonString(list));

        List<Student> list1 =  namedParameterJdbcTemplate.query(sql, sqlParameterSource, new StudentPropertyRowMapper());

        System.out.println(JsonUtil.toJsonString(list1));
    }


    class StudentPropertyRowMapper implements RowMapper<Student> {

        @Override
        public Student mapRow(ResultSet resultSet, int i) throws SQLException {
            Student student =new Student();
            student.setId(resultSet.getInt("id"));
            student.setName(resultSet.getString("name"));
            student.setScore(resultSet.getInt("score"));
            student.setSubject(resultSet.getString("subject"));
            return student;
        }
    }
}
