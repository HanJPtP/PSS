package com.woniu.fo;

import com.woniu.pojo.PssTestItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemAddPaperFo {
    //题目
    private PssTestItem pssTestItem;
    //选项
    private List<PssTestItem> pssTestItems;
}
