package indi.mat.work.db.service;

import indi.mat.work.db.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao dao;

    @Override
    public void test() {
        dao.test();
    }

    @Override
    public void test1() {
        dao.test1();
    }
}
