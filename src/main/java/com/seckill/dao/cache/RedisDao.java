package com.seckill.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.seckill.entity.Seckill;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
	private final JedisPool jedisPool;
	
	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    /**
     * @param ip
     * @param port
     * ���췽������ʼ��jedisPool����
     */
    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }
    
    /**
     * @param seckillId
     * @return
     * ��redis�з����л���ȡseckill����
     */
    public Seckill getSeckill(long seckillId){
    	//redis�����߼�
    	try {
    		
    		Jedis jedis = jedisPool.getResource();
    		try {
    			String key = "seckill:" + seckillId;
    			//jedis��û��ʵ�����л�����
                //�����Զ������л����Ѷ���ת���ɶ���������洢�����������л����ǰѶ���������ת���ɶ���
                //protostuff: pojo.
    			byte[] bytes = jedis.get(key.getBytes());
                //�����ػ�ȡ��
                if (bytes != null) {
                	//�ն���
                    Seckill seckill = schema.newMessage();
                    //seckill�������л�
                    ProtostuffIOUtil.mergeFrom(bytes,seckill,schema);
                    return seckill;
                }
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
    	return null;
    }
    
    /**
     * @param seckill
     * @return
     * ��seckill�������redis������
     */
    public String putSeckill(Seckill seckill){
    	
    	//set Object(Seckill) -> ���л� -> byte[]
    	try {
			
    		Jedis jedis = jedisPool.getResource();
    		try {
    			String key = "seckill:" + seckill.getSeckillId();
    			//���л�seckill����linkedBuffer�ǻ�����
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //��ʱ����
                int timeout = 60 * 60; //1Сʱ
                String result = jedis.setex(key.getBytes(),timeout,bytes);

                return result;
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
    	return null;
    }
}
