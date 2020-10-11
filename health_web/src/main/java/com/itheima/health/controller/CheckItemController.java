package com.itheima.health.controller;

import com.itheima.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import com.itheima.health.utils.resources.MessageConstant;
import io.swagger.annotations.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/checkItem")
@Api("传智健康检查项接口文档API")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;

    @GetMapping("/findAll")
    @PreAuthorize("hasAnyAuthority('CHECKITEM_QUERY')")
    @ApiOperation(value = "查询所有有效数据",notes = "调用返回所有is_delete为0的数据")
    public Result findAll(){
        try {
            List<CheckItem> list = checkItemService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    @PostMapping("/saveCheckItem")
    @ApiOperation(value = "新增检查项",notes = "检查项")
    public Result saveCheckItem(@ApiParam("CheckItem检查项实体 新增id可以不带") @RequestBody CheckItem checkItem){
        try {
            checkItemService.save(checkItem);
            return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    @PostMapping("/findPage")
    @ApiOperation(value = "检查项的分页查询",notes = "检查项的分页查询")
    public Result findPage(@ApiParam("分页查询条件,携带当前页码,每页显示条数及查询条件") @RequestBody QueryPageBean queryPageBean){
        //需要返回的数据为: list数据,总记录数
        try {
            //用当前页码和每页显示条数 查询出page对象结果集
            PageResult map = checkItemService.findPageByContion(queryPageBean);
            //返回数据
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    @DeleteMapping("/deleteCheckItemById")
    @ApiOperation("根据id进行逻辑删除的方法,如果该检查项被检查组应用则不允许删除")
    @ApiImplicitParams(@ApiImplicitParam(name="id",value="传入逻辑删除的Id"))
    public Result deleteCheckItemById(@RequestParam("id") Integer id){
        try {
            CheckItem checkItem = new CheckItem();
            checkItem.setId(id);
            checkItem.setIs_delete(1);
            Boolean flag = checkItemService.deleteCheckItemById(checkItem);
            if (flag){
                return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
            }
            return new Result(false,MessageConstant.CHECKITEM_IS_QUOTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    @PostMapping("/add")
    @ApiOperation("该方法如果id存在则更新,id不存在则新增")
    public Result add(@ApiParam("CheckItem检查项实体") @RequestBody CheckItem checkItem){
        boolean flag = (checkItem.getId()==null);
        try {
            checkItemService.saveOrUpdate(checkItem);
            String message = MessageConstant.UPDATE_CHECKITEM_SUCCESS;
            if (flag){
                message = MessageConstant.ADD_CHECKITEM_SUCCESS;
            }
            return new Result(true,message);
        } catch (Exception e) {
            e.printStackTrace();
            String message = MessageConstant.UPDATE_CHECKITEM_FAIL;
            if (flag){
                message = MessageConstant.ADD_CHECKITEM_FAIL;
            }
            return new Result(true,message);
        }
    }

    @GetMapping("/findCheckItemIdsByGroupId")
    @ApiOperation("该方法根据检查组ID查询对应的多个已选id")
    @ApiImplicitParams(@ApiImplicitParam(name="id",value ="检查组的主键ID"))
    public Result findCheckItemIdsByGroupId(@RequestParam("id") Integer id){
        try {
            List<Integer> list = checkItemService.findCheckItemIdsByGroupId(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

}
