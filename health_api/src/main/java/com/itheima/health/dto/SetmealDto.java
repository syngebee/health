package com.itheima.health.dto;

import com.itheima.health.pojo.Setmeal;
import lombok.Data;

import java.io.Serializable;

@Data
public class SetmealDTO extends Setmeal implements Serializable {
    private Long[] groupIds;
}
