package indi.mat.work.db.dao;

import indi.mat.work.db.util.JsonUtil;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class TestDao extends BaseDao {

    public void test() {
        int map = jdbc("mysql").queryForObject("SELECT 100", new HashMap<>(0), Integer.class);
        System.out.println(JsonUtil.toJsonString(map));
    }
}
