package com.itheima.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService extends IService<OrderSetting> {
    void batchOrderSetting(List<String[]> list);

    Map findSettingData(String year, String month);

    void handleOrderSet(Map map);
}
