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
     * 构造方法，初始化jedisPool对象
     */
    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }
    
    /**
     * @param seckillId
     * @return
     * 在redis中反序列化获取seckill对象
     */
    public Seckill getSeckill(long seckillId){
    	//redis操作逻辑
    	try {
    		
    		Jedis jedis = jedisPool.getResource();
    		try {
    			String key = "seckill:" + seckillId;
    			//jedis并没有实现序列化操作
                //采用自定义序列化，把对象转换成二进制数组存储起来，反序列化就是把二进制数组转换成对象
                //protostuff: pojo.
    			byte[] bytes = jedis.get(key.getBytes());
                //缓存重获取到
                if (bytes != null) {
                	//空对象
                    Seckill seckill = schema.newMessage();
                    //seckill被反序列化
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
     * 把seckill对象存入redis缓存中
     */
    public String putSeckill(Seckill seckill){
    	
    	//set Object(Seckill) -> 序列化 -> byte[]
    	try {
			
    		Jedis jedis = jedisPool.getResource();
    		try {
    			String key = "seckill:" + seckill.getSeckillId();
    			//序列化seckill对象，linkedBuffer是缓冲器
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //超时缓存
                int timeout = 60 * 60; //1小时
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
