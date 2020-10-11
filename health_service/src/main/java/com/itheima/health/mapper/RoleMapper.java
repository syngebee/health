package com.itheima.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.health.pojo.Role;
import org.apache.ibatis.annotations.Select;

public interface RoleMapper extends BaseMapper<Role> {

    @Select("SELECT `permission_id` FROM `t_role_permission` WHERE `role_id` = #{id}")
    int[] findPermissionsIdsByRoleId(Integer id);

    @Select("SELECT `role_id` FROM `t_user_role` WHERE `user_id` = #{id}")
    int[] findRoleIdsByUserId(Integer id);
}
