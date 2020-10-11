package com.itheima.health.controller;

import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.resources.MessageConstant;
import com.itheima.health.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmealmobile")
@Api("传智健康移动端套餐接口文档API")
public class SetmealMobileControler {
    @Reference
    private SetmealService setmealService;

    @GetMapping("/findAllSetmeal")
    @ApiOperation(value = "查询所有套餐",notes = "查询所有套餐")
    public Result findAllSetmeal(){
        try {
            List<Setmeal> list = setmealService.list();
            return new Result(true,MessageConstant.QUERY_SETMEALLIST_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

    @GetMapping("/findSetMealDetail")
    @ApiOperation(value = "根据ID查询单条套餐")
    @ApiImplicitParams(@ApiImplicitParam(name="id",value = "套餐ID"))
    public Result findSetMealDetail(Integer id){
        try {
            SetmealVO setmeal = setmealService.findSetMealDetail(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

}
