package com.woniu.service.impl;

import com.woniu.dao.mysql.PssCircuitMapper;
import com.woniu.dao.mysql.PssCourtsMapper;
import com.woniu.dao.mysql.PssVillageMapper;
import com.woniu.pojo.PssCircuit;
import com.woniu.pojo.PssCourts;
import com.woniu.pojo.PssVillage;
import com.woniu.service.IPssCourtsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PssCourtsServiceImpl implements IPssCourtsService {
    private final PssCircuitMapper pssCircuitMapper;
    private final PssVillageMapper pssVillageMapper;
    private final PssCourtsMapper pssCourtsMapper;
    public PssCourtsServiceImpl(PssCircuitMapper pssCircuitMapper, PssVillageMapper pssVillageMapper, PssCourtsMapper pssCourtsMapper) {
        this.pssCircuitMapper = pssCircuitMapper;
        this.pssVillageMapper = pssVillageMapper;
        this.pssCourtsMapper = pssCourtsMapper;
    }

    /**
     * 添加台区
     * @param pssCourts
     * @return
     */
    @Override
    public int saveCourts(PssCourts pssCourts) {
        return pssCourtsMapper.saveCourts(pssCourts);
    }

    /**
     * 根据台区名称查询id的方法
     * @param cname
     * @return
     */
    @Override
    public Integer getCourtsName(String cname) {
        return pssCourtsMapper.getCourtsName(cname);
    }

    /**
     * 修改台区信息
     * @param pssVillage
     * @return
     */
    @Override
    public int updCourts(PssVillage pssVillage) {
        PssCourts pssCourts = pssVillage.getPssCourts();
        PssCircuit pssCircuit = pssVillage.getPssCircuit();
        // 修改台区信息
        int i = pssCourtsMapper.updCourts(pssCourts);
        int j = pssCircuitMapper.updCircuit(pssCircuit);
        int k = pssVillageMapper.updVillage(pssVillage);
        // 根据台区路线名查询挑起
        return i;
    }
}
