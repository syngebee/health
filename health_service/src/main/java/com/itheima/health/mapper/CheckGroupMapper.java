package com.itheima.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CheckGroupMapper extends BaseMapper<CheckGroup> {
    @Insert("INSERT INTO `t_checkgroup_checkitem` VALUES(#{groupId},#{itemId})")
    void addCheckGroupAndCheckItemIds(@Param("groupId")Integer groupId,@Param("itemId")Integer itemId);

    @Delete("delete from t_checkgroup_checkitem where CHECKGROUP_ID = #{groupId}")
    void relieveByGroupId(@Param("groupId") Integer groupId);

    @Select("select * from t_setmeal_checkgroup where CHECKGROUP_ID = #{groupId}")
    List<Object> getById(@Param("groupId") Integer groupId);

    @Select("select CHECKGROUP_ID from t_setmeal_checkgroup where SETMEAL_ID = #{setMealId}")
    List<Integer> findAllCheckGroupBySetMealId(Integer setMealId);

    @Select("SELECT CHECKITEM_ID from t_checkgroup_checkitem where CHECKGROUP_ID = #{checkGroupId}")
    List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId);
}
