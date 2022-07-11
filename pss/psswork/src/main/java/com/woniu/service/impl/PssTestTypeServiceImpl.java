package com.woniu.service.impl;

import com.woniu.dao.mysql.PssTestTypeMapper;
import com.woniu.pojo.PssTestType;
import com.woniu.service.IPssTestTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class PssTestTypeServiceImpl implements IPssTestTypeService {

    @Resource
    private PssTestTypeMapper pssTestTypeMapper;

    @Override
    public List<PssTestType> listAllPssTestType() throws Exception {
        return pssTestTypeMapper.listAllPssTestType();
    }
}
