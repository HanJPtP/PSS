package com.woniu.fo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 *  每个工单号对应的工具信息详情集合
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tools implements Serializable {
    private String toolno;  //工具编号
    private String toolname;    //工具名
    private String style; //种类
    private String standard; //规格
    private Integer nums; //借用数量
    private String userno;  //领用人工号
    private String username; //领用人姓名
    private Date borrowtime;    //借用工具时间
    private Date returntime;    //归还工具时间
    private Integer status;     //借用情况


}
