package com.itheima.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.health.dto.SetmealDTO;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;
import com.itheima.entity.PageResult;
import com.itheima.health.vo.SetmealVO;

import java.util.List;
import java.util.Map;

public interface SetmealService extends IService<Setmeal> {
    void add(SetmealDTO setmealDTO);

    PageResult findPage(QueryPageBean queryPageBean);

    SetmealVO findSetMealDetail(Integer id);

    Map getSetmealReport();
}
