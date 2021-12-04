package indi.mat.work.db.dao;

import indi.mat.work.db.util.JsonUtil;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class TestDao extends BaseDao {

    public void test() {
        HashMap map = (HashMap) jdbc("mysql").queryForMap("SELECT 100", new HashMap());
        System.out.println(JsonUtil.toJsonString(map));
    }
}
