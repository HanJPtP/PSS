package com.woniu.service;

import com.woniu.pojo.PssCourts;
import com.woniu.pojo.PssVillage;

import java.util.List;

public interface IPssCourtsService {
    //添加台区的方法
    int saveCourts(PssCourts pssCourts);
    // 根据台区名称查询台区的id
    Integer getCourtsName(String cname);
    // 修改台区的方法
    int updCourts(PssVillage pssVillage);
}
