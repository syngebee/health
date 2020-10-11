package com.itheima.health.controller;

import com.itheima.entity.PageResult;
import com.itheima.health.dto.SetmealDTO;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.aliyunoss.AliyunUtils;
import com.itheima.health.utils.resources.MessageConstant;
import com.itheima.health.utils.resources.RedisConstant;
import com.itheima.health.utils.resources.UploadUtils;
import io.swagger.annotations.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/setmeal")
@Api("传智健康套餐设置接口文档API")
public class SetmealController {
    @Reference
    private SetmealService setmealService;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @PostMapping("/upload")
    @ApiOperation(value = "上传文件的方法",notes = "上传图片,不可超过2M")
    public Result upload(@ApiParam("不用我解释了吧") @RequestParam("imgFile") MultipartFile file){
        try {
            System.out.println("---------------收到上传了---");
            String originalFilename = file.getOriginalFilename();
            if (!file.isEmpty()){
                String uuidFilename = UploadUtils.generateRandonFileName(originalFilename);
                AliyunUtils.uploadMultiPartFileToAliyun(file.getBytes(),uuidFilename);
                redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_RESOURCES,uuidFilename);
                return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,uuidFilename);
            }else {
                return new Result(true,MessageConstant.PIC_UPLOAD_FAIL);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增或更新套餐的方法",notes = "新增或更新套餐的方法")
    public Result add(@ApiParam("Setmeal实体+选项数组")@RequestBody SetmealDTO setmealDTO){
        boolean flag = setmealDTO.getId()==null;
        try {
            setmealService.add(setmealDTO);
            String message = MessageConstant.EDIT_SETMEAL_SUCCESS;
            if(flag){
                message = MessageConstant.ADD_SETMEAL_SUCCESS;
            }
            return new Result(true,message);
        } catch (Exception e) {
            e.printStackTrace();
            String message = MessageConstant.EDIT_SETMEAL_FAIL;
            if(flag){
                message = MessageConstant.ADD_SETMEAL_FAIL;
            }
            return new Result(false,message);
        }
    }

    @PostMapping("/findPage")
    @ApiOperation(value = "分页查询套餐",notes = "分页查询套餐")
    public Result findPage(@ApiParam("分页对象")@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = setmealService.findPage(queryPageBean);
            return new Result(true,MessageConstant.QUERY_SETMEALLIST_SUCCESS,pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

    @DeleteMapping("/deleteSetmealById")
    @ApiOperation(value = "根据ID逻辑删除",notes = "根据ID逻辑删除")
    @ApiImplicitParams(@ApiImplicitParam(name="id",value="套餐的ID"))
    public Result deleteSetmealById(Integer id){
        try {
            Setmeal setmeal = new Setmeal();
            setmeal.setId(id);
            setmeal.setIs_delete(1);
            setmealService.updateById(setmeal);
            return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_SETMEAL_FAIL);
        }
    }

}
