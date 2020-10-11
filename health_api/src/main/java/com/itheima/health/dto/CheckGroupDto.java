package com.itheima.health.dto;


import com.itheima.health.pojo.CheckGroup;
import lombok.Data;

@Data
public class CheckGroupDTO extends CheckGroup {

    /**
     * 选择的检查项id列表
     * */
    private Integer[] checkItemIds;

}
