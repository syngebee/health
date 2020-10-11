package com.itheima.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.entity.PageResult;
import com.itheima.health.dto.CheckGroupDTO;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.mapper.CheckGroupMapper;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CheckGroupServiceImpl extends ServiceImpl<CheckGroupMapper,CheckGroup> implements CheckGroupService  {

    @Override
    public void add(CheckGroupDTO checkGroupDTO) {
        //获取返回的主键id, mp已做好了
        Integer groupId = checkGroupDTO.getId();
        //删除中间表关系
        if (groupId!=null){
            baseMapper.relieveByGroupId(groupId);
        }
        //检查组基本信息保存,更新或新增
        saveOrUpdate(checkGroupDTO);
        groupId=checkGroupDTO.getId();

        //获取所选的所有选项
        Integer[] itemIds = checkGroupDTO.getCheckItemIds();
        //添加中间表
        if(itemIds!=null&&itemIds.length!=0){
            for (Integer itemId : itemIds) {
                baseMapper.addCheckGroupAndCheckItemIds(groupId,itemId);
            }
        }
    }

    @Override
    public PageResult findPageByCondition(QueryPageBean queryPageBean) {
        //条件构造器
        LambdaQueryWrapper<CheckGroup> lambda = new QueryWrapper<CheckGroup>().lambda();
        //查询条件
        String queryString = queryPageBean.getQueryString();
        if (!StringUtils.isEmpty(queryString)){
            lambda.and(f->f
                    .like(CheckGroup::getName,queryString)
                    .or()
                    .eq(CheckGroup::getCode,queryString)
                    .or()
                    .eq(CheckGroup::getHelpCode,queryString)
            );
        }
        //只查有效数据
        lambda.eq(CheckGroup::getIs_delete,0);
        //借助分页插件完成查询
        Page<CheckGroup> page = page(new Page<>(queryPageBean.getCurrentPage(), queryPageBean.getPageSize()), lambda);
        //封装Map
        List<CheckGroup> list = page.getRecords();
        long total = page.getTotal();
        return new PageResult(total,list);
    }

    @Override
    public Boolean deleteById(CheckGroup checkGroup) {
        List<Object> list = baseMapper.getById(checkGroup.getId());
        if (list.size() != 0){
            return false;
        }
        baseMapper.updateById(checkGroup);
        return true;
    }

    @Override
    public List<CheckGroup> findAll() {
        QueryWrapper<CheckGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("IS_DELETE",0);
        return list(queryWrapper);
    }

    @Override
    public List<Integer> findAllCheckGroupBySetMealId(Integer setMealId) {
        return baseMapper.findAllCheckGroupBySetMealId(setMealId);
    }


}
