package com.woniu.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestPaperVo {
    //试卷名字
    private String testName;
    //考试试卷编号
    private int testid;
    //试卷题目数量
    private int counts;
    //状态
    private String  status;
    //试卷对应的题目
    private List<ItemVo> itemVos;

    public int getCounts(){
        this.counts=0;
        if(this.itemVos.size()>0){
            for (int i = 0; i < this.itemVos.size(); i++) {
                this.counts++;
            }
            return this.counts;
        }else{
            return this.counts;
        }
    }
}
