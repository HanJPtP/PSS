package com.woniu.service.impl;

import com.woniu.dao.mysql.PssBusinessMapper;
import com.woniu.pojo.PssBusiness;
import com.woniu.service.IPssBusinessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class PssBusinessServiceImpl implements IPssBusinessService {
    private final PssBusinessMapper pssBusinessMapper;

    public PssBusinessServiceImpl(PssBusinessMapper pssBusinessMapper) {
        this.pssBusinessMapper = pssBusinessMapper;
    }

    /**
     * 新型业务类型列表
     * @return
     */
    @Override
    public List<PssBusiness> queryAllBusiness() {
        List<PssBusiness> pssBusinesses = pssBusinessMapper.queryAllBusiness();
        return pssBusinesses;
    }
}
