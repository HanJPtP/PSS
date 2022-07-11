package com.woniu.vo;

import com.woniu.pojo.PssTestItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 单个题目带子选项
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemVo {
    //题目
    private PssTestItem pssTestItem;
    //选项
    private List<PssTestItem> pssTestItems;
}
