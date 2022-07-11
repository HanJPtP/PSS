package com.woniu.service.impl;

import com.woniu.dao.mysql.PssUnitMapper;
import com.woniu.pojo.PssUnit;
import com.woniu.service.IWhUnitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional
public class WhUnitService implements IWhUnitService {

    @Resource
    private PssUnitMapper pssUnitMapper;
    @Override
    public List<PssUnit> listUnit() {
        return pssUnitMapper.listUnit();
    }
}
