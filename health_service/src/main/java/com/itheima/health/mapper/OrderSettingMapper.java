package com.itheima.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.health.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface OrderSettingMapper extends BaseMapper<OrderSetting> {
    @Select("SELECT ORDERDATE,NUMBER,RESERVATIONS FROM t_ordersetting WHERE ORDERDATE BETWEEN #{beginDate} AND #{endDate}")
    List<Map> findSettingData(@Param("beginDate") String beginDate,@Param("endDate") String endDate);

    @Update("UPDATE t_ordersetting SET RESERVATIONS=RESERVATIONS+1 WHERE ORDERDATE=#{date}")
    void updateReservationsByOrderDate(String date);

    @Select("SELECT COUNT(1) FROM t_ordersetting WHERE ORDERDATE=#{orderDate} AND NUMBER>RESERVATIONS")
    int isOrderOk(String orderDate);
}
