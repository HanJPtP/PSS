package com.woniu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.dao.mysql.PssApprovalMapper;
import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssApproval;
import com.woniu.service.IPssApprovalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class PssApprovalServiceImpl implements IPssApprovalService {
    @Resource
    private PssApprovalMapper pssApprovalMapper;
    @Override
    public int addApproval(PssApproval approval) {
        return pssApprovalMapper.addApproval(approval);
    }

    @Override
    public PageInfo<PssApproval> getApprovalList(Integer approvalid, Integer pageNo, Integer pageSize) {
        Page<PssApproval> page = new Page<>(pageNo, pageSize);
        IPage<PssApproval> approvalList = pssApprovalMapper.getApprovalList(page,approvalid);
        PageInfo<PssApproval> pageInfo = new PageInfo<>(approvalList);
        return pageInfo;
    }

    @Override
    public int editApproval(PssApproval approval) {
        return pssApprovalMapper.editApproval(approval);
    }
}
