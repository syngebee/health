package com.itheima.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.health.dto.CheckGroupDTO;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.entity.PageResult;

import java.util.List;

public interface CheckGroupService extends IService<CheckGroup> {

    void add(CheckGroupDTO checkGroupDTO);

    PageResult findPageByCondition(QueryPageBean queryPageBean);

    Boolean deleteById(CheckGroup checkGroup);

    List<CheckGroup> findAll();

    List<Integer> findAllCheckGroupBySetMealId(Integer setMealId);
}
