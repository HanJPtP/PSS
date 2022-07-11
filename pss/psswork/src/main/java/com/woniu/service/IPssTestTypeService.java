package com.woniu.service;

import com.woniu.pojo.PssTestType;

import java.util.List;

public interface IPssTestTypeService {

    /**
     * 查询所有的题目类型
     * @return
     */
    List<PssTestType> listAllPssTestType() throws Exception;
}
