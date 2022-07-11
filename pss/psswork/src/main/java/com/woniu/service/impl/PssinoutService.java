package com.woniu.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.dao.mysql.PssWhInoutMapper;
import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssWhInout;
import com.woniu.service.IPssWhinoutService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PssinoutService implements IPssWhinoutService {

    @Resource
    private PssWhInoutMapper pssWhInoutMapper;

    /**
     * 出入库列表
     * @param pageNo
     * @param pageSize
     * @param intime
     * @param outtime
     * @return
     */
    @Override
    public PageInfo<PssWhInout> ListWhinout(Integer pageNo, Integer pageSize, Date intime, Date outtime) {
        Page<PssWhInout> page = new Page<>(pageNo,pageSize);
        IPage<PssWhInout> iPage = pssWhInoutMapper.ListWhinout(page,intime,outtime);
        PageInfo<PssWhInout> pageInfo = new PageInfo<>(iPage);
        return pageInfo;
    }

    /**
     * 添加出入库
     * @param pssWhInout
     * @return
     */
    @Override
    public int addInout(PssWhInout pssWhInout) {
        return pssWhInoutMapper.addInout(pssWhInout);
    }

    /**
     * 修改出入库
     * @param pssWhInout
     * @return
     */
    @Override
    public int updateInout(PssWhInout pssWhInout) {
        return pssWhInoutMapper.updateInout(pssWhInout);
    }

    /**
     * 删除出入库
     * @param id
     * @return
     */
    @Override
    public int delInout(Integer id) {
        return pssWhInoutMapper.delInout(id);
    }

    @Override
    public List<PssWhInout> pssinoutInfor(String orderno) {
        return pssWhInoutMapper.pssinoutInfor(orderno);
    }
}
