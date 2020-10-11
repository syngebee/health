package com.itheima.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.health.mapper.OrderSettingMapper;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import com.itheima.health.utils.date.DateUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional
public class OrderSettingServiceImpl extends ServiceImpl<OrderSettingMapper,OrderSetting> implements OrderSettingService {
    @Override
    public void batchOrderSetting(List<String[]> list) {
        List<OrderSetting>  orderSettings = StringTOList(list);
        List<OrderSetting> orderSettingSave = new ArrayList<>();
        List<OrderSetting> orderSettingUpdate = new ArrayList<>();

        for (OrderSetting orderSetting : orderSettings) {
            Date orderDate = orderSetting.getOrderDate();
            QueryWrapper<OrderSetting> queryWrapper = new QueryWrapper<>();
            //根据日期
            queryWrapper.eq("ORDERDATE",orderDate);
            //查询单一记录
            OrderSetting orderSettingIsExist = baseMapper.selectOne(queryWrapper);
            //如果不存在加入新增集合,如果存在加入更新集合
            if (orderSettingIsExist==null){
                orderSettingSave.add(orderSetting);
            }else {
                orderSettingUpdate.add(orderSetting);
            }
        }
        saveBatch(orderSettingSave);
        updateBatchById(orderSettingUpdate);
    }

    @Override
    public Map findSettingData(String year, String month) {
        //目标格式: "2020-09-01":{number:200,reservations:200}
        String beginDate = year+"-"+month+"-"+"1";
        String endDate = year+"-"+month+"-"+"31";
        //封装了需要的,预约日期,可预约人数,已预约人数的List集合
        List<Map> mapList = baseMapper.findSettingData(beginDate,endDate);
        //大对象, 准备序列化给前端的单个对象
        Map<String, OrderSetting> mapForReturn = new HashMap<>();
        //遍历集合
        for (Map map : mapList) {
            //准备数据阶段
            //准备Key
            Date orderdate = (Date) map.get("ORDERDATE");
            String date = DateUtils.parseDate2String(orderdate, "yyyy-MM-dd");
            //准备Value
            Integer number = (Integer) map.get("NUMBER");
            Integer reservations = (Integer) map.get("RESERVATIONS");
            OrderSetting orderSetting = new OrderSetting();
            orderSetting.setNumber(number);
            orderSetting.setReservations(reservations);
            //把一条处理好的数据放入准备返回的大Map
            mapForReturn.put(date,orderSetting);
        }
        return mapForReturn;
    }

    @Override
    public void handleOrderSet(Map map) {
        //转格式
        String settingDay = (String) map.get("settingDay");
        Date date = DateUtils.parseString2Date(settingDay);
        Integer number =Integer.parseInt((String) map.get("number"));

        //条件查一条, 目的为了知道这条有没有设置过日期,有的话
        QueryWrapper<OrderSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ORDERDATE",date);
        OrderSetting orderSetting = baseMapper.selectOne(queryWrapper);
        if (orderSetting!=null){
            //更新
            orderSetting.setNumber(number);
            updateById(orderSetting);
        }else {
            //新增
            OrderSetting orderSettingNew = new OrderSetting();
            orderSettingNew.setNumber(number);
            orderSettingNew.setReservations(0);
            orderSettingNew.setOrderDate(date);
            save(orderSettingNew);
        }
    }

    private List<OrderSetting> StringTOList(List<String[]> list) {
        ArrayList<OrderSetting> orderSettings = new ArrayList<>();
        for (String[] strings : list) {
            OrderSetting orderSetting = new OrderSetting();
            Date date = DateUtils.parseString2Date(strings[0],"yyyy/MM/dd");
            orderSetting.setOrderDate(date);
            orderSetting.setNumber(Integer.parseInt(strings[1]));
            orderSettings.add(orderSetting);
        }
        return orderSettings;
    }

}
