package com.woniu.service.impl;

import com.woniu.dao.mysql.PssCheckworkMapper;
import com.woniu.pojo.PssCheckwork;
import com.woniu.service.IPssCheckworkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service
@Transactional
public class PssCheckworkServiceImpl implements IPssCheckworkService {
    @Resource
    private PssCheckworkMapper pssCheckworkMapper;
    @Override
    public List<PssCheckwork> getCheckworkByUserid(Integer userid) {
        return pssCheckworkMapper.getCheckworkByUserid(userid);
    }
}
