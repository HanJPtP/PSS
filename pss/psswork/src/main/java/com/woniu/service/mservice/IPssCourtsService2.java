package com.woniu.service.mservice;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IPssCourtsService2 {
    /**
     *  查询所有的台区名称
     * @return
     */
    List<String> listAllCname();
}
