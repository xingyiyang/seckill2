package com.seckill.dao.cache;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seckill.dao.SeckillDao;
import com.seckill.entity.Seckill;

//junit启动时加载SpringIOC容器
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit去哪找Spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {

	private long id = 1002;
	
	@Autowired
    private RedisDao redisDao;
	
	@Autowired
    private SeckillDao seckillDao;
	
	@Test
	public void testSeckill() throws Exception {
		
		//getSeckill and putSeckill
		Seckill seckill = redisDao.getSeckill(id);
		if(seckill == null){
			seckill = seckillDao.queryById(id);
			if(seckill != null){
				String result = redisDao.putSeckill(seckill);
				System.out.println(result);
				seckill = redisDao.getSeckill(id);
				System.out.println(seckill);
			}
		}else{
			System.out.println(seckill);
		}
	}

}
