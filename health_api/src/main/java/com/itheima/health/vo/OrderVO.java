package com.itheima.health.vo;

import com.itheima.health.pojo.Order;
import lombok.Data;

@Data
public class OrderVO extends Order {
    private String member;
    private String setmeal;
}
