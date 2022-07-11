package com.woniu.service.mservice.impl;

import com.woniu.dao.mysql.PssCourtsMapper;
import com.woniu.service.mservice.IPssCourtsService2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PssCourtsService2Impl implements IPssCourtsService2 {
    private final PssCourtsMapper pssCourtsMapper;
    /**
     *  查询所有的台区名称
     * @return
     */
    @Override
    public List<String> listAllCname() {
        return pssCourtsMapper.listAllCname();
    }
}
