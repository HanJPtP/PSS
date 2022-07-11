package com.woniu.service.mservice;

import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssOrderElec;


public interface IPssOrderElecService {

     /**
     *  多条件查询
     * @param pageNo
     * @param pageSize
     * @param userno
     * @param areano
     * @param electricno
     * @return
     */
     PageInfo<PssOrderElec> listOrderElec(Integer pageNo, Integer pageSize, String userno,
                                         String areano,
                                         String electricno);

     /**
      *  新增
      * @param pssOrderElec
      * @return
      */
     int addOrderElec(PssOrderElec pssOrderElec);

     /**
      *  修改
      */
     int updateOrderElec(PssOrderElec pssOrderElec);

     /**
      *  删除
      */
     int deleteOrderElec(Integer id);
}
