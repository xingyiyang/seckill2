package com.seckill.dao;

import com.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

//junit����ʱ����SpringIOC����
@RunWith(SpringJUnit4ClassRunner.class)
//����junitȥ����Spring�������ļ�
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
    //ע��Daoʵ��������
    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void testQueryById() throws Exception {
        long id = 1001;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void testQueryAll() throws Exception {
        List<Seckill> seckills = seckillDao.queryAll(0, 100);
        for (Seckill s : seckills) {
            System.out.println(s);
        }

    }

    @Test
    public void testReduceNumber() throws Exception {
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(1000L,killTime);
        System.out.println("updateCount="+updateCount);
    }
}