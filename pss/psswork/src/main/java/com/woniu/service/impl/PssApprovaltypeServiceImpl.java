package com.woniu.service.impl;

import com.woniu.dao.mysql.PssApprovaltypeMapper;
import com.woniu.pojo.PssApprovaltype;
import com.woniu.service.IPssApprovaltypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service
@Transactional
public class PssApprovaltypeServiceImpl implements IPssApprovaltypeService {
    @Resource
    private PssApprovaltypeMapper pssApprovaltypeMapper;
    @Override
    public List<PssApprovaltype> getApprovalType() {
        return pssApprovaltypeMapper.getApprovalType();
    }
}
