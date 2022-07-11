package com.woniu.service.mservice;


import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssDoorArear;


public interface IPssDoorAreaService {

    /**
     *  多条件查询
     * @param pageNo
     * @param pageSize
     * @param doorno
     * @return
     */
    PageInfo<PssDoorArear> listDoorArea(Integer pageNo, Integer pageSize, String doorno);

    /**
     *  新增
     * @param pssDoorArear
     * @return
     */
    int addDoorArea(PssDoorArear pssDoorArear);

    /**
     *  修改
     * @param pssDoorArear
     * @return
     */
    int updateDoorArea(PssDoorArear pssDoorArear);

    /**
     *  删除
     * @param id
     * @return
     */
    int deleteDoorArea(Integer id);
}
