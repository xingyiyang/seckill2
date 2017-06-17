package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.List;

/**
 * @Author XingJun Qi
 * @MyBlog www.qixingjun.tech
 * @Version 1.0.0
 * @Date 2017/2/13
 * @Description
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml",
})
public class SeckillServiceTest {

    //����slf4j������־
    private final Logger logger = LoggerFactory.getLogger("this.getClass()");

    @Autowired
    private SeckillService seckillService;

    @Test
    public void testGetSeckillList() throws Exception {
        List<Seckill> seckillList = seckillService.getSeckillList();
        logger.info("seckillList={}", seckillList);
    }

    @Test
    public void testGetSeckillbyId() throws Exception {
        long seckillId = 1000;
        Seckill seckill = seckillService.getSeckillbyId(seckillId);
        logger.info("seckill={}", seckill);
    }

//    @Test
//    public void testExportSeckillUrl() throws Exception {
//        long seckillId = 1001;
//        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
//        logger.info(exposer.toString());
//    }
//
//    @Test
//    public void testExecuteSeckill() throws Exception {
//        long seckillId = 1001;
//        long userPhone = 18862141551L;
//        String md5="3f3024481ae6d1c45b890130b4ff0943";
//        try{
//            SeckillExecution executionException = seckillService.executeSeckill(seckillId,userPhone,md5);
//            logger.info("executionException result={}",executionException);
//        }catch (RepeatKillException e){
//            logger.error(e.getMessage());
//        }catch (SeckillCloseException e){
//            logger.error(e.getMessage());
//        }
//    }

    /**
     * Ϊ�˼��ɲ��Ե������ԣ����Խ�testExportSeckillUrl()��testExecuteSeckill()������������
     * ���ԣ������ſ��������Ĳ���������ɱ����
     * �����Կ����ظ�ִ��
     * @throws Exception
     */
    @Test
    public void testSeckillLogic() throws Exception {
        long seckillId = 1003;
        long userPhone = 18862141551L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()) {
            logger.info("exposer={}", exposer.toString());
            String md5 = exposer.getMd5() ;
            try {
                SeckillExecution executionException = seckillService.executeSeckill(seckillId, userPhone, md5);
                logger.info("executionException result={}", executionException);
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            } catch (SeckillCloseException e) {
                logger.error(e.getMessage());
            }
        }else{
            //��ɱδ����
            logger.warn("exposer={}",exposer);
        }
    }
    
    @Test
    public void testexecuteSeckillProcedure() throws Exception{
    	
    	long seckillId = 1002;
    	long phone = 1000842728;
    	Exposer exposer = seckillService.exportSeckillUrl(seckillId);
    	if(exposer.isExposed()){
    		String md5 = exposer.getMd5();
    		SeckillExecution seckillExecution = seckillService.executeSeckillProcedure(seckillId, phone, md5);
    		logger.info(seckillExecution.getStateInfo());
    	}
    	
    }
}