package com.woniu.service.impl;

import com.woniu.dao.mysql.PssPunchMapper;
import com.woniu.pojo.PssPunch;
import com.woniu.service.IPssPunchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class PssPunchServiceImpl implements IPssPunchService {
    @Resource
    private PssPunchMapper pssPunchMapper;
    @Override
    public int changePunch(PssPunch pssPunch) {
        return pssPunchMapper.changePunch(pssPunch);
    }
}
