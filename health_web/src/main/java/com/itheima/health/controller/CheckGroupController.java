package com.itheima.health.controller;

import com.itheima.entity.PageResult;
import com.itheima.health.dto.CheckGroupDTO;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import com.itheima.health.utils.resources.MessageConstant;
import io.swagger.annotations.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api("传智健康检查组接口文档API")
@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;

    @PostMapping("/add")
    @ApiOperation(value = "新增或更新检查组的方法",notes = "新增或更新检查组,并且添加相关的检查项关联")
    public Result add(@ApiParam("封装了CheckGroup的基本信息以及关联的检查项id数组") @RequestBody CheckGroupDTO checkGroupDTO){
        boolean flag = (checkGroupDTO.getId()==null);
        try {
            checkGroupService.add(checkGroupDTO);
            String message = MessageConstant.EDIT_CHECKGROUP_SUCCESS;
            if (flag){
                message = MessageConstant.ADD_CHECKGROUP_SUCCESS;
            }
            return new Result(true,message);
        } catch (Exception e) {
            e.printStackTrace();
            String message = MessageConstant.EDIT_CHECKGROUP_FAIL;
            if (flag){
                message = MessageConstant.ADD_CHECKGROUP_FAIL;
            }
            return new Result(false,message);
        }
    }

    @PostMapping("/findPage")
    @ApiOperation(value = "检查组的分页查询",notes = "检查组的分页查询")
    public Result findPage(@ApiParam("封装了当前页数和每页显示条数")@RequestBody QueryPageBean queryPageBean){
        try {
            //需要返回的数据为: list数据,总记录数
            PageResult pageResult = checkGroupService.findPageByCondition(queryPageBean);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    @DeleteMapping("/deleteCheckGroupById")
    @ApiOperation(value = "根据id删除检查组",notes = "根据id删除检查组,逻辑删除")
    @ApiImplicitParams(@ApiImplicitParam(name="id",value="传入逻辑删除的GroupId"))
    public Result deleteCheckGroupById(@RequestParam("id")Integer id){
        try {
            CheckGroup checkGroup = new CheckGroup();
            checkGroup.setId(id);
            checkGroup.setIs_delete(1);
            Boolean flag = checkGroupService.deleteById(checkGroup);
            if (flag){
                return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
            }
            return new Result(false,MessageConstant.CHECKGROUP_IS_QUOTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    @GetMapping("/findAll")
    @ApiOperation(value = "查询所有有效组",notes = "调用返回所有is_delete为0的数据")
    public Result findAllGroup(){
        try {
            List<CheckGroup> list = checkGroupService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }


    @GetMapping("/findAllCheckGroupBySetMealId")
    @ApiOperation(value = "根据套餐ID查询所有已选的检查组",notes = "根据套餐ID查询所有已选的检查组")
    @ApiImplicitParams(@ApiImplicitParam(name="id",value = "套餐ID"))
    public Result findAllCheckGroupBySetMealId(Integer id){
        try {
            List<Integer> list= checkGroupService.findAllCheckGroupBySetMealId(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
}
