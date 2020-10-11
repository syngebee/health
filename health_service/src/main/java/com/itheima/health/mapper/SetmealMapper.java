package com.itheima.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface SetmealMapper extends BaseMapper<Setmeal> {
    @Insert("INSERT INTO `t_setmeal_checkgroup` VALUES(#{mealId},#{groupId})")
    void addSetMealAndCheckGroupInfos(@Param("mealId") Integer mealId,@Param("groupId") Long groupId);

    @Delete("Delete from `t_setmeal_checkgroup` where `setmeal_id` = #{setmealId}")
    void relieveBySetmealId(Integer setmealId);

    @Select("SELECT CHECKGROUP_ID FROM t_setmeal_checkgroup WHERE SETMEAL_ID=#{id}")
    List<Integer> findCheckGroupsIdBySetmealId(Integer id);

    @Select("select count(tor.SETMEAL_ID) as value,ts.name " +
            "FROM t_setmeal ts , t_order tor " +
            "where ts.ID = tor.SETMEAL_ID " +
            "group by tor.SETMEAL_ID;")
    List<Map> getSetmealReport();
}
