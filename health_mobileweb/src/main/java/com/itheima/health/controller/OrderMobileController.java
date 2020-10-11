package com.itheima.health.controller;

import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Order;
import com.itheima.health.service.OrderService;
import com.itheima.health.utils.resources.MessageConstant;
import com.itheima.health.utils.resources.RedisMessageConstant;
import com.itheima.health.utils.sms.SMSUtils;
import com.itheima.health.utils.sms.ValidateCodeUtils;
import com.itheima.health.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/order")
@Api("传智健康移动端套餐接口文档API")
public class OrderMobileController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Reference
    private OrderService orderService;

    @PostMapping("/add")
    @ApiOperation("提交预约业务")
    public Result add(@RequestBody Map map){
        try {
            String validateCode = (String) map.get("validateCode");
            if(StringUtils.isNotBlank(validateCode)){
                String telephone = (String) map.get("telephone");
                String key = RedisMessageConstant.SENDTYPE_ORDER+telephone; //redis的key
                Integer redisValue = (Integer) redisTemplate.opsForValue().get(key);//拿Value
                System.out.println("redisValue = " + redisValue);
                if (redisValue==null){
                    return new Result(false,MessageConstant.SEND_VALIDATECODE_TIMEOUT);
                }
                if(!validateCode.equals(redisValue+"")){
                    return new Result(false,MessageConstant.VALIDATECODE_ERROR);
                }
                //到此时验证码正确, 业务层结束
                Order order = orderService.add(map);
                //可以发送短信给客户,告知成功
                SMSUtils.appointment_Is_Ok(telephone,"");
                return new Result(true,MessageConstant.ADD_ORDER_SUCCESS,order);
            }else {
                return new Result(false,"数据非法");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }
    }

    @PostMapping("/sendValidateCode")
    @ApiOperation("发送验证码的方法")
    public Result sendValidateCode(@RequestParam("phone") String phone){
        try {
            //生成随机验证码
            Integer code = ValidateCodeUtils.generateValidateCode(4);
            //发送!
            SMSUtils.registerSendCode(phone,code+"");
            //存入redis,5分钟有效期
            redisTemplate.opsForValue().set(RedisMessageConstant.SENDTYPE_ORDER+phone,code,5,TimeUnit.MINUTES);
            return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    @GetMapping("/findOrderById")
    @ApiOperation("根据ID查询订单")
    public Result findOrderById(@RequestParam("id") String id){
        try {
            OrderVO order = orderService.findOrderVOById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,order);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }

    }

}
