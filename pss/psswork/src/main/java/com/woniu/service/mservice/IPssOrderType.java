package com.woniu.service.mservice;

import com.woniu.pojo.PssOrderType;

import java.util.List;

public interface IPssOrderType {
    /**
     *  查询所有订单类型
     * @return
     */
    List<PssOrderType> listPssOrderType();

    /**
     *  根据id查询
     * @param ordertypeid
     * @return
     */
    PssOrderType getByordertypeid(Integer ordertypeid);
}
