package com.woniu.service.mservice;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.woniu.pojo.PssOrderProcess;
import com.woniu.pojo.PssOrderTask;
import com.woniu.pojo.PssUsers;
import com.woniu.util.PageInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IPssOrderProcessService {

    /**
     *  多条件查询处理单
     * @param pageNo    当前页面
     * @param pageSize  当前页条数
     * @param orderno   处理单编号
     * @param handelerno    处理人编号
     * @param areano    区域编号
     * @param problemtype   问题类型
     * @return
     */
    PageInfo<PssOrderProcess> listPssOrderProcess(Integer pageNo, Integer pageSize, String orderno,
                                                  String handelerno,
                                                  Integer ordertypeid,
                                                  String areano,
                                                  Integer problemtype);

    /**
     *  新增 下达 任务处理单
     * @param pssOrderTask  派工单对象
     * @return
     */
    int taskAddProcess(PssOrderTask pssOrderTask);

    /**
     *  得到所有状态为'y'的普通用户
     * @return
     */
    List<PssUsers> getOrderUsers();

    /**
     *  更新处理单
     * @param pssOrderProcess
     * @return
     */
    int updateProcess(PssOrderProcess pssOrderProcess);
}
