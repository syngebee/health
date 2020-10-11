package com.itheima.health.vo;


import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import lombok.Data;

import java.util.List;

@Data
public class CheckGroupVO extends CheckGroup {

    private List<CheckItem> checkItems;

}
