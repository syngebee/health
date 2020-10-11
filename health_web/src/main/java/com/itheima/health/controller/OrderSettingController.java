package com.itheima.health.controller;

import com.itheima.health.entity.Result;
import com.itheima.health.service.OrderSettingService;
import com.itheima.health.utils.poi.POIUtils;
import com.itheima.health.utils.resources.MessageConstant;
import io.swagger.annotations.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
@Api("传智健康预约管理接口文档API")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    @PostMapping("/importOrderSettings")
    @ApiOperation(value = "上传文件批量设置可预约人数",notes = "上传excel文件,批量导入数据,设置可预约人数")
    public Result importOrderSettings(@ApiParam("excel文件") @RequestParam("excelFile") MultipartFile file){
        try {
            List<String[]> list = POIUtils.readExcel(file);
            orderSettingService.batchOrderSetting(list);
            return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    @GetMapping("/findSettingData/{year}/{month}")
    @ApiOperation(value = "获取已设置的可预约人数",notes = "根据年月获取已设置的可预约人数")
    @ApiImplicitParams({@ApiImplicitParam(name="year",value="年份"),@ApiImplicitParam(name= "month",value = "月份")})
    public Result findSettingData(@PathVariable("year") String year, @PathVariable("month") String month){
        try {
            Map orderSettings = orderSettingService.findSettingData(year,month);
            return new Result(true,MessageConstant.QUERY_ORDERSETTING_SUCCESS,orderSettings);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDERSETTING_FAIL);
        }
    }

    @PostMapping("/handleOrderSet")
    @ApiOperation(value = "单条设置可预约人数",notes = "单条设置可预约人数")
    public Result handleOrderSet(@ApiParam("预约日期和设置的数量") @RequestBody Map map){
        try {
            orderSettingService.handleOrderSet(map);
            return new Result(true,MessageConstant.EDIT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_ORDERSETTING_FAIL);
        }
    }
}
