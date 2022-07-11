package com.woniu.fo;

import com.woniu.pojo.PssTestItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 修改题目的对应vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemUpdateFo {

    //题目
    private PssTestItem pssTestItem;
    //选项
    private List<PssTestItem> pssTestItems;


}
