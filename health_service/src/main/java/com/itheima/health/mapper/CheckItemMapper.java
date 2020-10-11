package com.itheima.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.health.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CheckItemMapper extends BaseMapper<CheckItem> {
    @Select("select CHECKITEM_ID from t_checkgroup_checkitem where CHECKGROUP_ID = #{gid}")
    List<Integer> findCheckItemIdsByGroupId(@Param("gid") int gid);

    @Select("select * from `t_checkgroup_checkitem` where checkitem_id = #{id}")
    List<Object> getById(Integer id);
}
