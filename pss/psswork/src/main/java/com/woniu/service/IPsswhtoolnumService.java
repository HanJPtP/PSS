package com.woniu.service;

import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssToolsNums;

import java.util.List;

public interface IPsswhtoolnumService {

    /**
     * 工单申请列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfo<PssToolsNums> ListWhtoolnum(Integer pageNo, Integer pageSize);


    /**
     * 工单详情
     * @param orderno
     * @return
     */
    List<PssToolsNums> toolnumInfor(String orderno);
}
