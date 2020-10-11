package com.itheima.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;

import java.util.List;
import com.itheima.entity.PageResult;

public interface CheckItemService extends IService<CheckItem> {
    PageResult findPageByContion(QueryPageBean queryPageBean);

    List<CheckItem> findAll();

    List<Integer> findCheckItemIdsByGroupId(Integer setMealId);

    Boolean deleteCheckItemById(CheckItem checkItem);
}
