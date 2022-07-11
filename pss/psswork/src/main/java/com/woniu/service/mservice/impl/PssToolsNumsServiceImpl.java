package com.woniu.service.mservice.impl;

import com.woniu.dao.mysql.PssToolsNumsMapper;
import com.woniu.pojo.PssToolsNums;
import com.woniu.service.mservice.IPssToolsNumsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PssToolsNumsServiceImpl implements IPssToolsNumsService {
    private final PssToolsNumsMapper pssToolsNumsMapper;

    /**
     *  根据工单编号查询 对象集合
     * @param orderno   工单编号
     * @return
     */
    @Override
    public List<PssToolsNums> listToolsNums(String orderno) {
        return pssToolsNumsMapper.listToolsNums(orderno);
    }

    /**
     *  新增对象数据
     * @param pssToolsNums
     * @return
     */
    @Override
    public int addToolsNums(PssToolsNums pssToolsNums) {
        return pssToolsNumsMapper.addToolsNums(pssToolsNums);
    }

    /**
     *  修改状态为已归还
     * @param orderno
     * @return
     */
    @Override
    public int updateStatus(String orderno) {
        return pssToolsNumsMapper.updateStatus(orderno);
    }

    /**
     *  查看归还状态
     * @param orderno
     * @return
     */
    @Override
    public int getStatus(String orderno) {
        return pssToolsNumsMapper.getStatus(orderno);
    }
}
