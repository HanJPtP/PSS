package com.woniu.service.mservice.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.dao.mysql.PssOrderProcessMapper;
import com.woniu.dao.mysql.PssOrderTaskMapper;
import com.woniu.pojo.PssOrderTask;
import com.woniu.service.mservice.IPssOrderTaskService;
import com.woniu.util.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class PssOrderTaskServiceImpl implements IPssOrderTaskService {
    @Resource
    private PssOrderTaskMapper pssOrderTaskMapper;



    /**
     *  实现多条件分页查询
     * @param pageNo    当前页码
     * @param pageSize  当前页数据条数
     * @param orderno   工单编号
     * @param ordertypeid   工单类型
     * @param userno    发起人工号
     * @param orderstatus   工单状态
     * @return
     */
    @Override
    public PageInfo<PssOrderTask> listOrderTask(Integer pageNo, Integer pageSize, String orderno, Integer ordertypeid, String userno, Integer orderstatus) {
        //创建page存入当前页和当前页总条数变量
        Page page = new Page(pageNo, pageSize);
        //调用dao,得到ipage对象
        IPage<PssOrderTask> iPage = pssOrderTaskMapper.listOrderTask(page, orderno, ordertypeid, userno, orderstatus);
        //将结果存入自定义的pageInfo对象
        PageInfo<PssOrderTask> pageInfo = new PageInfo<>(iPage);
        return pageInfo;
    }

    /**
     *  添加派工单
     * @param pssOrderTask 派工单对象
     * @return 1表示成功，0表示失败
     */
    @Override
    public int addOrderTask(PssOrderTask pssOrderTask) {
        //新增派工单
        return pssOrderTaskMapper.addOrderTask(pssOrderTask);
    }

    /**
     *  根据id修改派工单
     * @param pssOrderTask 派工单对象
     * @return 1表示成功，0表示失败
     */
    @Override
    public int updateOrderTask(PssOrderTask pssOrderTask) {
        return pssOrderTaskMapper.updateOrderTask(pssOrderTask);
    }


    /**
     *  根据orderno 查询 userno
     * @param orderno
     * @return
     */
    @Override
    public String getusernoByorderno(String orderno) {
        return pssOrderTaskMapper.getusernoByorderno(orderno);
    }

    @Override
    public int updateStatus(Integer status, String orderno) {
        return pssOrderTaskMapper.updateStatus(status, orderno);
    }

    @Override
    public int updateOrdertypeid(Integer ordertypeid,String worktask,String neworderno, String orderno) {
        return pssOrderTaskMapper.updateOrdertypeid(ordertypeid,worktask,neworderno, orderno);
    }

    /**
     *  根据工单号查询工单状态
     * @param orderno
     * @return
     */
    @Override
    public Integer getOrderStatus(String orderno) {
        return pssOrderTaskMapper.getOrderStatus(orderno);
    }

}
