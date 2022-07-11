package com.woniu.service.mservice;

import com.woniu.pojo.PssToolsNums;


import java.util.List;

public interface IPssToolsNumsService {
    /**
     *  根据工单编号查询 对象集合
     * @param orderno   工单编号
     * @return
     */
    List<PssToolsNums> listToolsNums(String orderno);

    /**
     *  新增对象数据
     * @param pssToolsNums
     * @return
     */
    int addToolsNums(PssToolsNums pssToolsNums);

    /**
     *  修改状态为已归还
     * @param orderno
     * @return
     */
    int updateStatus(String orderno);

    /**
     *  查看归还状态
     * @param orderno
     * @return
     */
    int getStatus(String orderno);
}
