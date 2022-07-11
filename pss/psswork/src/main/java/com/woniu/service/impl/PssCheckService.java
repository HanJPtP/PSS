package com.woniu.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.dao.mysql.PssWhCheckMapper;
import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssWhCheck;
import com.woniu.service.IPssCheckService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PssCheckService implements IPssCheckService {

    @Resource
    private PssWhCheckMapper pssWhCheckMapper;
    /**
     * 校验列表
     * @param pageNo
     * @param pageSize
     * @param account
     * @return
     */
    @Override
    public PageInfo<PssWhCheck> listCheck(Integer pageNo, Integer pageSize, String account) {
        Page<PssWhCheck> page = new Page<>(pageNo,pageSize);
        IPage<PssWhCheck> iPage = pssWhCheckMapper.listCheck(page,account);
        PageInfo<PssWhCheck> pageInfo = new PageInfo<>(iPage);
        return pageInfo;
    }


    /**
     * 校验详情
     * @param orderno
     * @return
     */
    @Override
    public List<PssWhCheck> checkInfor(String orderno) {
        return pssWhCheckMapper.psscheckInfor(orderno);
    }

    /**
     * 删除校验信息
     * @param id
     * @return
     */
    @Override
    public int checkDel(Integer id) {
        return pssWhCheckMapper.checkDel(id);
    }

    /**
     * 添加校验
     * @param pssWhCheck
     * @return
     */
    @Override
    public int addWhcheck(PssWhCheck pssWhCheck) {
        return pssWhCheckMapper.addWhcheck(pssWhCheck);
    }

    /**
     * 修改校验
     * @param pssWhCheck
     * @return
     */
    @Override
    public int updateWhcheck(PssWhCheck pssWhCheck) {
        return pssWhCheckMapper.updateWhcheck(pssWhCheck);
    }
}
