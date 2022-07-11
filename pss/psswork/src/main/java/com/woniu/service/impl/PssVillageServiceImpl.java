package com.woniu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.dao.mysql.PssCircuitMapper;
import com.woniu.dao.mysql.PssCourtsMapper;
import com.woniu.dao.mysql.PssVillageMapper;
import com.woniu.fo.CircuitFo;
import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssVillage;
import com.woniu.service.IPssVillageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class PssVillageServiceImpl implements IPssVillageService {

    private final PssCircuitMapper pssCircuitMapper;
    private final PssVillageMapper pssVillageMapper;
    private final PssCourtsMapper pssCourtsMapper;

    public PssVillageServiceImpl(PssCircuitMapper pssCircuitMapper, PssVillageMapper pssVillageMapper, PssCourtsMapper pssCourtsMapper) {
        this.pssCircuitMapper = pssCircuitMapper;
        this.pssVillageMapper = pssVillageMapper;
        this.pssCourtsMapper = pssCourtsMapper;
    }

    /**
     * 多条件关联查询台区信息并分页
     * @param pageNo
     * @param pageSize
     * @param cname
     * @param circuit
     * @param village
     * @param username
     * @return
     */
    @Override
    public PageInfo<PssVillage> getVillageQuery(Integer pageNo, Integer pageSize, String cname, String circuit, String village, String username) {
        Page<PssVillage> page = new Page<>(pageNo, pageSize);
        IPage<PssVillage> iPage = pssVillageMapper.getVillageQuery(page, cname, circuit, village, username);
        PageInfo<PssVillage> pageInfo = new PageInfo<>(iPage);
        return pageInfo;
    }

    @Override
    public int saveVillage(List<CircuitFo> circuitItem) {

        List<String> cirList=null;
        int i1 = 0;
        for (int i = 0; i < circuitItem.size(); i++) {
            String[] villageItem = circuitItem.get(i).getVillage().split(",");
            List<PssVillage> pssVillageList = new ArrayList<>();
            Integer circuitid = pssCircuitMapper.getCircuitName(circuitItem.get(i).getCircuit());
            for (int j = 0; j < villageItem.length; j++) {
                PssVillage pssVillage = new PssVillage();
                pssVillage.setVillage(villageItem[j]);
                pssVillage.setCircuitid(circuitid);
                pssVillageList.add(pssVillage);
            }
            i1 = pssVillageMapper.saveVillage(pssVillageList);
        }
        return i1;
    }

    /**
     * 删除台区行政村
     * @param id
     * @return
     */
    @Override
    public int delVillage(Integer id) {
        return pssVillageMapper.delVillage(id);
    }

    @Override
    public int delCircuit(PssVillage pssVillage) {
        Integer circuitid = pssCircuitMapper.getCircuitName(pssVillage.getPssCircuit().getCircuit());
        int i = 0;
        if (circuitid !=null){
            pssVillageMapper.delCircuit(circuitid);
            i = pssCircuitMapper.delCircuitName(pssVillage.getPssCircuit().getCircuit());
        }
        return i;
    }

    @Override
    public int delCourts(PssVillage pssVillage) {
        Integer courtsid = pssCourtsMapper.getCourtsName(pssVillage.getPssCourts().getCname());
        int i = 0;
        if (courtsid != null){
            Integer circuitid = pssCircuitMapper.getCircuitName(pssVillage.getPssCircuit().getCircuit());
            if (circuitid !=null) {
                pssVillageMapper.delCircuit(circuitid);
            }
            pssCircuitMapper.delCircuitByCourtsid(courtsid);
            i = pssCourtsMapper.delCourtsName(pssVillage.getPssCourts().getCname());
        }
        return i;
    }
}
