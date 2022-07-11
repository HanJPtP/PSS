package com.woniu.service.impl;

import com.woniu.dao.mysql.PssCircuitMapper;
import com.woniu.pojo.PssCircuit;
import com.woniu.service.IPssCircuitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class PssCircuitServiceImpl implements IPssCircuitService {

    private final PssCircuitMapper pssCircuitMapper;
    public PssCircuitServiceImpl(PssCircuitMapper pssCircuitMapper) {
        this.pssCircuitMapper = pssCircuitMapper;
    }

    /**
     * 循环添加台区路线
     * @param circuitList
     * @return
     */
    @Override
    public int saveCircuit(List<PssCircuit> circuitList) {
        int i = pssCircuitMapper.saveCircuit(circuitList);
        return i;
    }

    @Override
    public Integer getCircuitName(String circuit,Integer courtsid) {
       return pssCircuitMapper.getCircuitByName(circuit, courtsid);
    }
}
