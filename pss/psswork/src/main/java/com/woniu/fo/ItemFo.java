package com.woniu.fo;

import com.woniu.pojo.PssTestItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 接收页面传递过来的数据用来新增题目
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemFo {
    //存题目
    private PssTestItem pssTestItem;
    //存选项(没有选项判定为判断题)
    private List<PssTestItem> pssTestItemList;
}
