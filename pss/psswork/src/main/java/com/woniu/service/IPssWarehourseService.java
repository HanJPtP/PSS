package com.woniu.service;

import com.woniu.pojo.PssWarehouse;

import java.util.List;

public interface IPssWarehourseService {

    /**
     * 显示四库类型
     * @param
     * @return
     */
    List<PssWarehouse> listWhtype();


    /**
     *  根据id查询名字
     * @param id
     * @return
     */
    String getStyleName(Integer id);
}
