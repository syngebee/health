package com.itheima.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.health.pojo.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface OrderMapper extends BaseMapper<Order> {
    @Select("SELECT COUNT(1) FROM t_order WHERE MEMBER_ID=#{id} AND SETMEAL_ID=#{setmealId} AND ORDERDATE=#{orderDate}")
    int findOrderIsExistByOrderDateAndSetmealIdAndMemberId(@Param("id") Integer id,@Param("setmealId") int setmealId,@Param("orderDate") String orderDate);
}
