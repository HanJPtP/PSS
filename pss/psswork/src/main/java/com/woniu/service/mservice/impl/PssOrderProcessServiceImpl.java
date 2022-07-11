package com.woniu.service.mservice.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.dao.mysql.PssOrderProcessMapper;
import com.woniu.dao.mysql.PssOrderTaskMapper;
import com.woniu.dao.mysql.PssOrderTypeMapper;
import com.woniu.dao.mysql.PssUsersMapper;
import com.woniu.pojo.PssOrderProcess;
import com.woniu.pojo.PssOrderTask;
import com.woniu.pojo.PssUsers;
import com.woniu.service.mservice.IPssOrderProcessService;
import com.woniu.util.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PssOrderProcessServiceImpl implements IPssOrderProcessService {

    private final PssOrderProcessMapper pssOrderProcessMapper;
    private final PssOrderTaskMapper pssOrderTaskMapper;
    private final PssOrderTypeMapper pssOrderTypeMapper;
    private final PssUsersMapper pssUsersMapper;

    /**
     *  处理单多条件查询处理单
     * @param pageNo    当前页面
     * @param pageSize  当前页条数
     * @param orderno   处理单编号
     * @param handlerno    处理人编号
     * @param ordertypeid  工单类型
     * @param areano    区域编号
     * @param problemtype   问题类型
     * @return
     */
    @Override
    public PageInfo<PssOrderProcess> listPssOrderProcess(Integer pageNo, Integer pageSize, String orderno, String handlerno,Integer ordertypeid, String areano, Integer problemtype) {
        Page page = new Page(pageNo,pageSize);
        IPage iPage = pssOrderProcessMapper.listPssOrderProcess(page, orderno, handlerno,ordertypeid, areano, problemtype);

        PageInfo<PssOrderProcess> pageInfo = new PageInfo<>(iPage);
        return pageInfo;
    }

    /**
     *  新增 下达 任务处理单,修改派工单状态
     * @param pssOrderTask  派工单对象
     * @return  1成功，0失败
     */
    @Override
    public int taskAddProcess(PssOrderTask pssOrderTask) {
        //判断任务是否已经下达了
        List<String> allOrderno = pssOrderProcessMapper.listAllOrderno();
        for (String orderno :
                allOrderno) {
            if(pssOrderTask.getOrderno().equals(orderno)){
                //编号已存在，任务已下达
                return 2;
            }
        }
        //修改派工单状态 未发放改为未完成
        int rs1 = pssOrderTaskMapper.updateOrderstatus(pssOrderTask);
        int rs2 = pssOrderProcessMapper.taskAddProcess(pssOrderTask);
        if(rs1 > 0 && rs2 > 0){
            return 1;
        }else{
            return 0;
        }
    }

    /**
     *  得到所有状态为'y'的普通用户
     * @return
     */
    @Override
    public List<PssUsers> getOrderUsers() {
        return pssUsersMapper.getOrderUsers();
    }

    /**
     *  更新处理单
     * @param pssOrderProcess
     * @return
     */
    @Override
    public int updateProcess(PssOrderProcess pssOrderProcess) {
        return pssOrderProcessMapper.updateProcess(pssOrderProcess);
    }
}
