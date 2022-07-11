package com.woniu.dao.mysql;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.woniu.pojo.PssOrderProcess;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniu.pojo.PssOrderTask;
import org.apache.ibatis.annotations.Insert;
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
public interface PssOrderProcessMapper extends BaseMapper<PssOrderProcess> {

    /**
     *  多条件查询处理单
     * @param page
     * @param orderno   处理单编号
     * @param handlerno    处理人编号
     * @param areano    区域编号
     * @param problemtype   问题类型
     * @return
     */
    IPage<PssOrderProcess> listPssOrderProcess(IPage<PssOrderProcess> page, @Param("orderno") String orderno,
                                               @Param("handlerno") String handlerno,
                                               @Param("ordertypeid") Integer ordertypeid,
                                               @Param("areano") String areano,
                                               @Param("problemtype") Integer problemtype);

    /**
     *  查询所有工单编号
     * @return
     */
    @Select("select orderno from pss_order_process")
    List<String> listAllOrderno();

    /**
     *  新增处理单的 工单编号，工单类型，工单任务，开始时间，结束时间
     * @param pssOrderTask
     * @return
     */
    @Insert("insert into pss_order_process(orderno,ordertypeid,ordertitle,starttime,endtime,problemtype)" +
            " values(#{pssOrderTask.orderno},#{pssOrderTask.ordertypeid},#{pssOrderTask.worktask},#{pssOrderTask.starttime},#{pssOrderTask.endtime},3)")
    int taskAddProcess(@Param("pssOrderTask") PssOrderTask pssOrderTask);

    /**
     *  更新处理单
     * @param pssOrderProcess
     * @return
     */
    default int updateProcess(PssOrderProcess pssOrderProcess){
        return this.updateById(pssOrderProcess);
    }

}
