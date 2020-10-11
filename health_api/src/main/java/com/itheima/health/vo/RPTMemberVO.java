package com.itheima.health.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RPTMemberVO implements Serializable {
    List<Object> memberCounts;
    List<Object> yearAndMonth;
}
