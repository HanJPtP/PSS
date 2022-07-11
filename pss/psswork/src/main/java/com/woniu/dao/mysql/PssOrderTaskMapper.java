package com.woniu.dao.mysql;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.woniu.pojo.PssOrderTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.minidev.json.writer.UpdaterMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuqi
 * @since 2022-05-05
 */
public interface PssOrderTaskMapper extends BaseMapper<PssOrderTask> {


    /**
     *  派工单多条件查询
     * @param orderno   工单编号
     * @param ordertypeid   工单类型
     * @param userno    发起人工号
     * @param orderstatus   工单状态
     * @return  派工单对象集合
     */
    IPage<PssOrderTask> listOrderTask(IPage<PssOrderTask> page,@Param("orderno") String orderno,
                                      @Param("ordertypeid") Integer ordertypeid,
                                      @Param("userno") String userno,
                                      @Param("orderstatus") Integer orderstatus);



    /**
     *  添加派工单
     * @param pssOrderTask 派工单对象
     * @return 1表示成功，0表示失败
     */
    default int addOrderTask(PssOrderTask pssOrderTask){
        return this.insert(pssOrderTask);
    }

    /**
     *  根据id修改派工单
     * @param pssOrderTask 派工单对象
     * @return 1表示成功，0表示失败
     */
    default int updateOrderTask(PssOrderTask pssOrderTask){
        return this.updateById(pssOrderTask);
    }

    @Update("update pss_order_task set orderstatus=1 where orderno=#{pssOrderTask.orderno}")
    int updateOrderstatus(@Param("pssOrderTask") PssOrderTask pssOrderTask);

    /**
     *  根据orderno 查询 userno
     * @param orderno
     * @return
     */
    @Select("select userno from pss_order_task where orderno=#{orderno}")
    String getusernoByorderno(String orderno);

    /**
     *  更改状态
     * @param status
     * @param orderno
     * @return
     */
    @Update("update pss_order_task set orderstatus=#{status} where orderno=#{orderno}")
    int updateStatus(@Param("status") Integer status,@Param("orderno") String orderno);

    /**
     *  更改工单类型
     * @param ordertypeid
     * @param orderno
     * @return
     */
    @Update("update pss_order_task set ordertypeid=#{ordertypeid},worktask=#{worktask},orderno=#{neworderno} where orderno=#{orderno}")
    int updateOrdertypeid(@Param("ordertypeid") Integer ordertypeid,@Param("worktask") String worktask,@Param("neworderno") String neworderno,@Param("orderno") String orderno);


    /**
     *  根据工单号查询工单状态
     * @param orderno
     * @return
     */
    @Select("select orderstatus from pss_order_task where orderno=#{orderno}")
    Integer getOrderStatus(String orderno);

}
