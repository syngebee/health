package com.itheima.xxl.job;

import com.itheima.health.utils.aliyunoss.AliyunUtils;
import com.itheima.health.utils.resources.RedisConstant;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@JobHandler(value = "heima.setmeal.clean.img.job") //  web配置JobHandler的名称
public class MyJob extends IJobHandler {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ReturnT<String> execute(String s) throws Exception {

        System.out.print("SETMEAL_PIC_RESOURCES:");
        System.out.println(redisTemplate.opsForSet().members(RedisConstant.SETMEAL_PIC_RESOURCES).size());
        System.out.print("SETMEAL_PIC_DB_RESOURCES:");
        System.out.println(redisTemplate.opsForSet().members(RedisConstant.SETMEAL_PIC_DB_RESOURCES).size());
        Set<String> sdiff = redisTemplate.opsForSet().difference(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        System.out.println(sdiff.size());
        if (sdiff.size()>0){
            for (String filename : sdiff) {
                AliyunUtils.deleteFile(filename);
            }
            redisTemplate.delete(RedisConstant.SETMEAL_PIC_RESOURCES);
            redisTemplate.delete(RedisConstant.SETMEAL_PIC_DB_RESOURCES);
            System.out.println("-setmeal--is-scanning---one-more");
        }
        System.out.println("-xxl-job--is-running----");
        return SUCCESS;
    }
}
