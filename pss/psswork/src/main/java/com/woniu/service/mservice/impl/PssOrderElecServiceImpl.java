package com.woniu.service.mservice.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.dao.mysql.PssOrderElecMapper;
import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssOrderElec;
import com.woniu.service.mservice.IPssOrderElecService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PssOrderElecServiceImpl implements IPssOrderElecService {

    private final PssOrderElecMapper pssOrderElecMapper;

    /**
     *  多条件查询
     * @param pageNo
     * @param pageSize
     * @param userno
     * @param areano
     * @param electricno
     * @return
     */
    @Override
    public PageInfo<PssOrderElec> listOrderElec(Integer pageNo, Integer pageSize, String userno, String areano, String electricno) {
        Page page = new Page(pageNo,pageSize);
        IPage iPage = pssOrderElecMapper.listOrderElec(page, userno, areano, electricno);

        PageInfo<PssOrderElec> pageInfo = new PageInfo<>(iPage);
        return pageInfo;
    }

    /**
     *  新增
     * @param pssOrderElec
     * @return
     */
    @Override
    public int addOrderElec(PssOrderElec pssOrderElec) {
        return pssOrderElecMapper.addOrderElec(pssOrderElec);
    }

    /**
     *  修改
     * @param pssOrderElec
     * @return
     */
    @Override
    public int updateOrderElec(PssOrderElec pssOrderElec) {
        return pssOrderElecMapper.updateOrderElec(pssOrderElec);
    }

    /**
     *  删除
     * @param id
     * @return
     */
    @Override
    public int deleteOrderElec(Integer id) {
        return pssOrderElecMapper.deleteOrderElec(id);
    }
}
