package com.woniu.service.mservice.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.dao.mysql.PssDoorArearMapper;
import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssDoorArear;
import com.woniu.service.mservice.IPssDoorAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PssDoorAreaServiceImpl implements IPssDoorAreaService {
    private final PssDoorArearMapper pssDoorArearMapper;

    /**
     *  多条件查询
     * @param pageNo
     * @param pageSize
     * @param doorno
     * @return
     */
    @Override
    public PageInfo<PssDoorArear> listDoorArea(Integer pageNo, Integer pageSize, String doorno) {
        Page page = new Page(pageNo,pageSize);

        IPage ipage = pssDoorArearMapper.listDoorArea(page, doorno);

        PageInfo<PssDoorArear> pageInfo = new PageInfo<>(ipage);

        return pageInfo;
    }

    /**
     *  新增
     * @param pssDoorArear
     * @return
     */
    @Override
    public int addDoorArea(PssDoorArear pssDoorArear) {
        return pssDoorArearMapper.addDoorArea(pssDoorArear);
    }

    /**
     *  修改
     * @param pssDoorArear
     * @return
     */
    @Override
    public int updateDoorArea(PssDoorArear pssDoorArear) {
        return pssDoorArearMapper.updateDoorArea(pssDoorArear);
    }

    /**
     *  删除
     * @param id
     * @return
     */
    @Override
    public int deleteDoorArea(Integer id) {
        return pssDoorArearMapper.deleteDoorArea(id);
    }
}
