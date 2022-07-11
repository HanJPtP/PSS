package com.woniu.service.mservice;

import com.woniu.pojo.PssOrderTask;
import com.woniu.util.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IPssOrderTaskService {
    /**
     *  派工单多条件查询
     * @param orderno   工单编号
     * @param ordertypeid   工单类型
     * @param userno    发起人工号
     * @param orderstatus   工单状态
     * @return  派工单对象集合
     */
    PageInfo<PssOrderTask> listOrderTask(Integer pageNo,Integer pageSize,
                                         String orderno,
                                         Integer ordertypeid,
                                         String userno,
                                         Integer orderstatus);

    /**
     *  添加派工单
     * @param pssOrderTask 派工单对象
     * @return 1表示成功，0表示失败
     */
   int addOrderTask(PssOrderTask pssOrderTask);

    /**
     *  根据id修改派工单
     * @param pssOrderTask 派工单对象
     * @return 1表示成功，0表示失败
     */
    int updateOrderTask(PssOrderTask pssOrderTask);


    /**
     *  根据orderno 查询 userno
     * @param orderno
     * @return
     */
    String getusernoByorderno(String orderno);

    /**
     *  更改状态
     * @param status
     * @param orderno
     * @return
     */
    int updateStatus(Integer status,String orderno);

    /**
     *  更改工单类型
     * @param ordertypeid
     * @param orderno
     * @return
     */
    int updateOrdertypeid(Integer ordertypeid,String worktask,String neworderno,String orderno);


    /**
     *  根据工单号查询工单状态
     * @param orderno
     * @return
     */
    Integer getOrderStatus(String orderno);
}
