package com.itheima.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.mapper.CheckItemMapper;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.itheima.entity.PageResult;

@Service
@Transactional
public class CheckItemServiceImpl extends ServiceImpl<CheckItemMapper,CheckItem> implements CheckItemService {
    @Override
    public PageResult findPageByContion(QueryPageBean queryPageBean) {
        //条件构造器,下面要用☆
        LambdaQueryWrapper<CheckItem> lambda = new QueryWrapper<CheckItem>().lambda();
        //获取查询条件
        String queryString = queryPageBean.getQueryString();
        //判断条件
        //非空则加模糊查询条件
        if (!StringUtils.isEmpty(queryString)){
            lambda.and(f->f
                    .like(CheckItem::getName,queryString)
                    .or()
                    .eq(CheckItem::getCode,queryString));
        }

        //查询未逻辑删除的有效数据
        lambda.eq(CheckItem::getIs_delete,"0");

        //使用构造好的条件☆
        Page<CheckItem> page =page(new Page<>(queryPageBean.getCurrentPage()
                ,queryPageBean.getPageSize()),lambda);
        //list数据get
        List<CheckItem> checkItemList = page.getRecords();
        //总记录数get
        long total = page.getTotal();
        //返回数据
        return new PageResult(total,checkItemList);
    }

    @Override
    public List<CheckItem> findAll() {
        //条件构造器,下面要用☆
        QueryWrapper<CheckItem> queryWrapper = new QueryWrapper<>();
        //只要有效数据
        queryWrapper.eq("IS_DELETE","0");
        return list(queryWrapper);
    }

    @Override
    public List<Integer> findCheckItemIdsByGroupId(Integer gid) {
         return baseMapper.findCheckItemIdsByGroupId(gid);
    }

    @Override
    public Boolean deleteCheckItemById(CheckItem checkItem) {
        Integer id = checkItem.getId();
        List<Object> list = baseMapper.getById(id);
        if (list.size() != 0){
            return false;
        }
        baseMapper.updateById(checkItem);
        return true;
    }
}
