package com.woniu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.dao.mysql.PssToolsNumsMapper;
import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssToolsNums;
import com.woniu.service.IPsswhtoolnumService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional
public class PsswhtoolnumService implements IPsswhtoolnumService {

    @Resource
    private PssToolsNumsMapper pssToolsNumsMapper;
    @Override
    public PageInfo<PssToolsNums> ListWhtoolnum(Integer pageNo, Integer pageSize) {
        Page<PssToolsNums> page = new Page<>(pageNo,pageSize);
        IPage<PssToolsNums> iPage = pssToolsNumsMapper.ListWhtoolnum(page);
        PageInfo<PssToolsNums> pageInfo = new PageInfo<>(iPage);
        return pageInfo;
    }

    /**
     * 工单详情
     * @param orderno
     * @return
     */
    @Override
    public List<PssToolsNums> toolnumInfor(String orderno) {
        return pssToolsNumsMapper.toolnumInfor(orderno);
    }


}
