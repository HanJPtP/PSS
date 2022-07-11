package com.woniu.service.impl;

import com.woniu.dao.mysql.PssLeavetypeMapper;
import com.woniu.pojo.PssLeavetype;
import com.woniu.service.IPssLeavetypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service
@Transactional
public class PssLeavetypeServiceImpl implements IPssLeavetypeService {
    @Resource
    private PssLeavetypeMapper pssLeavetypeMapper;
    @Override
    public List<PssLeavetype> leavetypeList() {
        return pssLeavetypeMapper.leavetypeList();
    }
}
