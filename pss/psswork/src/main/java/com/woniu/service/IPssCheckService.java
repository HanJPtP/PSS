package com.woniu.service;

import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssWhCheck;

import java.util.Date;
import java.util.List;

public interface IPssCheckService {



    /**
     * 检验列表
     * @param pageNo
     * @param pageSize
     * @param account
     * @return
     */
    PageInfo<PssWhCheck> listCheck(Integer pageNo, Integer pageSize, String account);


    /**
     * 校验详情
     * @param orderno
     * @return
     */
    List<PssWhCheck> checkInfor(String orderno);

    /**
     * 删除校验信息
     * @param id
     * @return
     */
    int checkDel(Integer id);



    /**
     * 添加校验
     * @param pssWhCheck
     * @return
     */
    int addWhcheck(PssWhCheck pssWhCheck);

    /**
     * 根据id修改校验数据
     * @param pssWhCheck
     * @return
     */
    int updateWhcheck(PssWhCheck pssWhCheck);
}
