package com.woniu.service;

import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssWhInout;

import java.util.Date;
import java.util.List;

public interface IPssWhinoutService {


    /**
     * 出入库列表
     * @param pageNo
     * @param pageSize
     * @param intime
     * @param outtime
     * @return
     */
    PageInfo<PssWhInout> ListWhinout(Integer pageNo, Integer pageSize, Date intime,Date outtime);

    /**
     * 添加出入库信息
     * @param pssWhInout
     * @return
     */
    int addInout(PssWhInout pssWhInout);

    /**
     * 根据id修改出入库数据
     * @param pssWhInout
     * @return
     */
    int updateInout(PssWhInout pssWhInout);
    /**
     * 删除出入库信息
     * @param id
     * @return
     */
    int delInout(Integer id);

    /**
     * 出入库详情
     * @param orderno
     * @return
     */
    List<PssWhInout> pssinoutInfor(String orderno);
}
